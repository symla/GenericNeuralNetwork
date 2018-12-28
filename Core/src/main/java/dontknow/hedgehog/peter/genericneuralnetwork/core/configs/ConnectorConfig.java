package dontknow.hedgehog.peter.genericneuralnetwork.core.configs;

import dontknow.hedgehog.peter.genericneuralnetwork.core.components.weights.WeightRandomizer;

import java.util.Objects;

public class ConnectorConfig {

    private final WeightRandomizer weightRandomizer;

    public ConnectorConfig(final WeightRandomizer weightRandomizer) {
        if ( weightRandomizer == null ) throw new NullPointerException("WeightRandomizer must not be null.");

        this.weightRandomizer = weightRandomizer;
    }

    public WeightRandomizer getWeightRandomizer() {
        return weightRandomizer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectorConfig that = (ConnectorConfig) o;
        return Objects.equals(getWeightRandomizer(), that.getWeightRandomizer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWeightRandomizer());
    }

    @Override
    public String toString() {
        return "ConnectorConfig{" +
                "weightRandomizer=" + weightRandomizer +
                '}';
    }
}
