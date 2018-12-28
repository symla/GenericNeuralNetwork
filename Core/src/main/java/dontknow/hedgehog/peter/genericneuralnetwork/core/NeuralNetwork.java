package dontknow.hedgehog.peter.genericneuralnetwork.core;

public class NeuralNetwork {

    private final NeuralNetworkConfig neuralNetworkConfig;

    private final Layer inputLayer;

    private final Layer outputLayer;

    private final Layer[] hiddenLayers;

    public NeuralNetwork(final NeuralNetworkConfig neuralNetworkConfig) {
        if ( neuralNetworkConfig == null ) throw new NullPointerException("NeuralNetworkConfig must not be null.");

        this.neuralNetworkConfig = neuralNetworkConfig;

        this.inputLayer = new Layer(neuralNetworkConfig.getInputLayerConfig());
        this.outputLayer = new Layer(neuralNetworkConfig.getOutputLayerConfig());

        this.hiddenLayers = new Layer[neuralNetworkConfig.getHiddenLayerAmount()];
        for ( int i = 0; i < neuralNetworkConfig.getHiddenLayerAmount(); i++ ) {
            this.hiddenLayers[i] = new Layer(neuralNetworkConfig.getHiddenLayerConfigs()[i]);
        }
    }


}
