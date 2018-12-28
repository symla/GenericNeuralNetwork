package dontknow.hedgehog.peter.genericneuralnetwork.core;

public class Layer {

    private final LayerConfig layerConfig;

    private final double[] outputs;

    public Layer(final LayerConfig layerConfig) {
        if ( layerConfig == null ) throw new NullPointerException("LayerConfig must not be null.");

        this.layerConfig = layerConfig;
        this.outputs = new double[layerConfig.getNodeAmount()];
    }

    public LayerConfig getLayerConfig() {
        return layerConfig;
    }

    public double[] getOutputs() {
        return outputs;
    }

    /**
     * Convenience method for {@code getOutputs[index]}
     * @param index Index of the node.
     * @return Returns output of the node.
     */
    public double getNodeOutput(final int index) {
        return this.outputs[index];
    }

}
