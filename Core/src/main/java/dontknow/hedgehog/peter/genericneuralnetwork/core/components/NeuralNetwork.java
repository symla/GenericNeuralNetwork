package dontknow.hedgehog.peter.genericneuralnetwork.core.components;

import dontknow.hedgehog.peter.genericneuralnetwork.core.Util;
import dontknow.hedgehog.peter.genericneuralnetwork.core.configs.NeuralNetworkConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class NeuralNetwork {

    private final NeuralNetworkConfig neuralNetworkConfig;

    private final Layer inputLayer;

    private final Layer outputLayer;

    private final Layer[] hiddenLayers;

    private final Connector[] connectors;

    public NeuralNetwork(final NeuralNetworkConfig neuralNetworkConfig) {
        if ( neuralNetworkConfig == null ) throw new NullPointerException("NeuralNetworkConfig must not be null.");

        this.neuralNetworkConfig = neuralNetworkConfig;

        this.inputLayer = new Layer(neuralNetworkConfig.getInputLayerConfig());
        this.outputLayer = new Layer(neuralNetworkConfig.getOutputLayerConfig());

        this.hiddenLayers = new Layer[neuralNetworkConfig.getHiddenLayerAmount()];
        for ( int i = 0; i < this.hiddenLayers.length; i++ ) {
            this.hiddenLayers[i] = new Layer(neuralNetworkConfig.getHiddenLayerConfigs()[i]);
        }

        //One between input and output and/or for each hidden layer one more.
        this.connectors = new Connector[hiddenLayers.length+1];

        final List<Layer> tmpLayerList = new ArrayList<>();

        tmpLayerList.add(inputLayer);
        for ( final Layer hiddenLayer : this.hiddenLayers ) {
            tmpLayerList.add(hiddenLayer);
        }
        tmpLayerList.add(outputLayer);

        for ( int i = 0; i < tmpLayerList.size()-1; i++ ) {
            this.connectors[i] = new Connector(tmpLayerList.get(i), tmpLayerList.get(i+1));

            //Initialize weights
            this.connectors[i].randomizeWeights(this.neuralNetworkConfig.getConnectorConfig().getWeightRandomizer());
        }
    }

    public double[] process(final double[] input) {
        if ( input.length != this.inputLayer.getLayerConfig().getNodeAmount() ) {
            throw new IllegalArgumentException("Expected input with "+this.inputLayer.getLayerConfig().getNodeAmount()+
                    " values (amount input layer's nodes), but were "+input.length+".");
        }

        //Copy input to input layers output
        for ( int i = 0; i < input.length; i++ ) {
            this.inputLayer.setOutput(i, input[i]);
        }


        for ( final Connector connector : connectors ) {
            processConnector(connector);
        }

        //Copy output of output layer as result
        return this.outputLayer.getOutputs().clone();
    }

    private void processConnector(final Connector connector) {
        if ( connector == null ) throw new NullPointerException("Connector must not be null.");

        final Layer from = connector.getFrom();
        final Layer to = connector.getTo();

        final int from_n = from.getLayerConfig().getNodeAmount();
        final int to_n = to.getLayerConfig().getNodeAmount();

        //Calculate sgmoid(sum) for each node.
        for ( int to_index = 0; to_index < to_n; to_index++ ) {
            double sum = 0.0;

            for ( int from_index = 0; from_index < from_n; from_index++ ) {
                //System.out.println("w: "+connector.getWeight(j, i));
                sum += from.getNodeOutput(from_index)*connector.getWeight(from_index, to_index);

            }

            to.setOutput(to_index, Util.sigmoid(sum));
        }
    }


    public void processOutputExpectationSquared(final double[] expectation) {
        if ( expectation.length != this.outputLayer.getLayerConfig().getNodeAmount() ) {
            throw new IllegalArgumentException("Expected "+this.outputLayer.getLayerConfig().getNodeAmount() +
                    " expectation values (amount of output layer's nodes), but were "+expectation.length+".");
        }

        final double[] errors = new double[expectation.length];
        for ( int i = 0; i < errors.length; i++ ) {
            errors[i] = expectation[i]-outputLayer.getNodeOutput(i);
        }

        processOutputError(errors);
    }

    public void processOutputError(final double[] errors) {
        if ( errors.length != this.outputLayer.getLayerConfig().getNodeAmount() ) {
            throw new IllegalArgumentException("Expected "+this.outputLayer.getLayerConfig().getNodeAmount() +
                    " error values (amount of output layer's nodes), but were "+errors.length+".");
        }

        this.outputLayer.setErrors(errors.clone());
        for ( int i = this.connectors.length-1; i >= 0; i-- ) {
            processOutputErrorConnector(connectors[i]);
        }
    }

    private void processOutputErrorConnector(final Connector connector) {
        if ( connector == null ) throw new NullPointerException("Connector must not be null.");

        final Layer from = connector.getFrom();
        final Layer to = connector.getTo();

        from.setErrors(new double[from.getLayerConfig().getNodeAmount()]);

        //Nodes of to
        for ( int to_index = 0; to_index < to.getLayerConfig().getNodeAmount(); to_index++ ) {

            final double delta =
                    this.neuralNetworkConfig.getLearnRate() *
                    to.getNodeError(to_index) *
                    to.getNodeOutput(to_index) *
                    (1 - to.getNodeOutput(to_index));

            //Nodes of from
            for ( int from_index = 0; from_index < from.getLayerConfig().getNodeAmount(); from_index++ ) {

                connector.setWeight(from_index, to_index,
                             connector.getWeight(from_index, to_index) + (delta * from.getNodeOutput(from_index)));

                from.setError(from_index,from.getNodeError(from_index) + connector.getWeight(from_index, to_index) * to.getNodeError(to_index));

                /*
                final double deltaError =
                        1.0 * to.getErrors()[i] * Util.sigmoid(to.getNodeOutput(i)) *
                        Util.sigmoid(1.0 - to.getNodeOutput(i)) * from.getNodeOutput(j) *
                        this.neuralNetworkConfig.getLearnRate();

                connector.setWeight(j, i, connector.getWeight(j, i)+deltaError);
                */

                //from.setError(j, from.getNodeError(j)+(connector.getWeight(j, i)*to.getNodeError(i)*-0.0));



                /*
                final double deltaError =
                        -1.0 * to.getErrors()[i] * to.getNodeOutput(i) *
                                (1.0 - to.getNodeOutput(i)) * from.getNodeOutput(j) *
                                this.neuralNetworkConfig.getLearnRate();
                */

                //connector.setWeight(j, i, connector.getWeight(j, i)+deltaError);

                //from.setError(j, from.getNodeError(j)+(connector.getWeight(j, i)*to.getNodeError(i)));
            }
        }

    }



    public NeuralNetworkConfig getNeuralNetworkConfig() {
        return neuralNetworkConfig;
    }

    public Layer getInputLayer() {
        return inputLayer;
    }

    public Layer getOutputLayer() {
        return outputLayer;
    }

    public Layer[] getHiddenLayers() {
        return hiddenLayers;
    }

    public Connector[] getConnectors() {
        return connectors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NeuralNetwork that = (NeuralNetwork) o;
        return Objects.equals(getNeuralNetworkConfig(), that.getNeuralNetworkConfig()) &&
                Objects.equals(getInputLayer(), that.getInputLayer()) &&
                Objects.equals(getOutputLayer(), that.getOutputLayer()) &&
                Arrays.equals(getHiddenLayers(), that.getHiddenLayers()) &&
                Arrays.equals(getConnectors(), that.getConnectors());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getNeuralNetworkConfig(), getInputLayer(), getOutputLayer());
        result = 31 * result + Arrays.hashCode(getHiddenLayers());
        result = 31 * result + Arrays.hashCode(getConnectors());
        return result;
    }

    @Override
    public String toString() {
        return "NeuralNetwork{\n" +
                "\tneuralNetworkConfig=" + neuralNetworkConfig + "\n" +
                "\tinputLayer=" + inputLayer + "\n" +
                "\toutputLayer=" + outputLayer + "\n" +
                "\thiddenLayers=" + Arrays.toString(hiddenLayers) + "\n" +
                "\tconnectors=" + Arrays.toString(connectors) + "\n" +
                '}';
    }

}
