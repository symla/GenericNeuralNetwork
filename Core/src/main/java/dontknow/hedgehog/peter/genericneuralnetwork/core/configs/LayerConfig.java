package dontknow.hedgehog.peter.genericneuralnetwork.core.configs;

import java.util.Objects;

public class LayerConfig {

    private final int nodeAmount;

    public LayerConfig(final int nodeAmount) {
        if ( nodeAmount < 1 ) throw new IllegalArgumentException("NodeAmount must be at least 1.");

        this.nodeAmount = nodeAmount;
    }

    public int getNodeAmount() {
        return nodeAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LayerConfig that = (LayerConfig) o;
        return getNodeAmount() == that.getNodeAmount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNodeAmount());
    }

    @Override
    public String toString() {
        return "LayerConfig{" +
                "nodeAmount=" + nodeAmount +
                '}';
    }
}
