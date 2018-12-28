package dontknow.hedgehog.peter.genericneuralnetwork.core.components;

import dontknow.hedgehog.peter.genericneuralnetwork.core.configs.LayerConfig;

import java.util.Arrays;
import java.util.Objects;

public class Layer {

    private final LayerConfig layerConfig;

    private final double[] outputs;

    private double[] errors;

    public Layer(final LayerConfig layerConfig) {
        if ( layerConfig == null ) throw new NullPointerException("LayerConfig must not be null.");

        this.layerConfig = layerConfig;
        this.outputs = new double[layerConfig.getNodeAmount()];
        this.errors = new double[layerConfig.getNodeAmount()];
    }

    public LayerConfig getLayerConfig() {
        return layerConfig;
    }

    public double[] getOutputs() {
        return outputs;
    }

    public void setOutput(final int index, final double output) {
        this.outputs[index] = output;
    }

    public double[] getErrors() {
        return errors;
    }

    public void setErrors(double[] errors) {
        this.errors = errors;
    }

    public void setError(final int index, final double error) {
        this.errors[index] = error;
    }

    public double getNodeError(final int index) {
        return this.errors[index];
    }

    /**
     * Convenience method for {@code getOutputs[index]}
     * @param index Index of the node.
     * @return Returns output of the node.
     */
    public double getNodeOutput(final int index) {
        return this.outputs[index];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Layer layer = (Layer) o;
        return Objects.equals(getLayerConfig(), layer.getLayerConfig()) &&
                Arrays.equals(getOutputs(), layer.getOutputs()) &&
                Arrays.equals(getErrors(), layer.getErrors());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getLayerConfig());
        result = 31 * result + Arrays.hashCode(getOutputs());
        result = 31 * result + Arrays.hashCode(getErrors());
        return result;
    }

    @Override
    public String toString() {
        return "Layer{" +
                "layerConfig=" + layerConfig +
                ", outputs=" + Arrays.toString(outputs) +
                ", errors=" + Arrays.toString(errors) +
                '}';
    }
}
