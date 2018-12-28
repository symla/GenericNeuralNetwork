package dontknow.hedgehog.peter.genericneuralnetwork.core.configs;

import java.util.Arrays;
import java.util.Objects;

public class NeuralNetworkConfig {

    private final ConnectorConfig connectorConfig;

    private final double learnRate;

    private final LayerConfig inputLayerConfig;

    private final LayerConfig outputLayerConfig;

    private final LayerConfig[] hiddenLayerConfigs;


    public NeuralNetworkConfig(final ConnectorConfig connectorConfig,
                               final double learnRate,
                               final LayerConfig inputLayerConfig,
                               final LayerConfig outputLayerConfig,
                               final LayerConfig ...hiddenLayerConfigs) {

        if ( connectorConfig == null ) throw new NullPointerException("ConnectorConfig must not be null.");
        if ( inputLayerConfig == null ) throw new NullPointerException("InputLayerConfig must not be null.");
        if ( outputLayerConfig == null ) throw new NullPointerException("OutputLayerConfig must not be null.");

        this.connectorConfig = connectorConfig;
        this.learnRate = learnRate;
        this.inputLayerConfig = inputLayerConfig;
        this.outputLayerConfig = outputLayerConfig;
        this.hiddenLayerConfigs = hiddenLayerConfigs;
    }

    public ConnectorConfig getConnectorConfig() {
        return connectorConfig;
    }

    public double getLearnRate() {
        return learnRate;
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
        return Double.compare(that.getLearnRate(), getLearnRate()) == 0 &&
                Objects.equals(getConnectorConfig(), that.getConnectorConfig()) &&
                Objects.equals(getInputLayerConfig(), that.getInputLayerConfig()) &&
                Objects.equals(getOutputLayerConfig(), that.getOutputLayerConfig()) &&
                Arrays.equals(getHiddenLayerConfigs(), that.getHiddenLayerConfigs());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getConnectorConfig(), getLearnRate(), getInputLayerConfig(), getOutputLayerConfig());
        result = 31 * result + Arrays.hashCode(getHiddenLayerConfigs());
        return result;
    }

    @Override
    public String toString() {
        return "NeuralNetworkConfig{" +
                "connectorConfig=" + connectorConfig +
                ", learnRate=" + learnRate +
                ", inputLayerConfig=" + inputLayerConfig +
                ", outputLayerConfig=" + outputLayerConfig +
                ", hiddenLayerConfigs=" + Arrays.toString(hiddenLayerConfigs) +
                '}';
    }
}
