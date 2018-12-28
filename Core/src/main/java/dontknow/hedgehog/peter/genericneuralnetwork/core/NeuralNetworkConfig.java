package dontknow.hedgehog.peter.genericneuralnetwork.core;

import java.util.Arrays;
import java.util.Objects;

public class NeuralNetworkConfig {

    private final LayerConfig inputLayerConfig;

    private final LayerConfig outputLayerConfig;

    private final LayerConfig[] hiddenLayerConfigs;

    public NeuralNetworkConfig(final LayerConfig inputLayerConfig,
                               final LayerConfig outputLayerConfig,
                               final LayerConfig ...hiddenLayerConfigs) {

        if ( inputLayerConfig == null ) throw new NullPointerException("InputLayerConfig must not be null.");
        if ( outputLayerConfig == null ) throw new NullPointerException("OutputLayerConfig must not be null.");

        this.inputLayerConfig = inputLayerConfig;
        this.outputLayerConfig = outputLayerConfig;
        this.hiddenLayerConfigs = hiddenLayerConfigs;
    }

    public LayerConfig getInputLayerConfig() {
        return inputLayerConfig;
    }

    public LayerConfig getOutputLayerConfig() {
        return outputLayerConfig;
    }

    public LayerConfig[] getHiddenLayerConfigs() {
        return hiddenLayerConfigs;
    }

    public int getHiddenLayerAmount() {
        return this.hiddenLayerConfigs.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NeuralNetworkConfig that = (NeuralNetworkConfig) o;
        return Objects.equals(getInputLayerConfig(), that.getInputLayerConfig()) &&
                Objects.equals(getOutputLayerConfig(), that.getOutputLayerConfig()) &&
                Arrays.equals(getHiddenLayerConfigs(), that.getHiddenLayerConfigs());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getInputLayerConfig(), getOutputLayerConfig());
        result = 31 * result + Arrays.hashCode(getHiddenLayerConfigs());
        return result;
    }

    @Override
    public String toString() {
        return "NeuralNetworkConfig{" +
                "inputLayerConfig=" + inputLayerConfig +
                ", outputLayerConfig=" + outputLayerConfig +
                ", hiddenLayerConfigs=" + Arrays.toString(hiddenLayerConfigs) +
                '}';
    }
}
