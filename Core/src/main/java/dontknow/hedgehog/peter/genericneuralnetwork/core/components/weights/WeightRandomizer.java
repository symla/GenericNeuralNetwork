package dontknow.hedgehog.peter.genericneuralnetwork.core.components.weights;

import dontknow.hedgehog.peter.genericneuralnetwork.core.components.Layer;
import dontknow.hedgehog.peter.genericneuralnetwork.core.configs.LayerConfig;

import java.util.Objects;
import java.util.Random;

public abstract class WeightRandomizer {

    private final long seed;

    protected Random random;

    public WeightRandomizer(final long seed) {
        this.seed = seed;
        reset();
    }

    public void reset() {
        this.random = new Random(this.seed);
    }

    public abstract double getNextRandomWeight(final LayerConfig fromConfig,
                                               final LayerConfig toConfig);

    public long getSeed() {
        return seed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeightRandomizer that = (WeightRandomizer) o;
        return getSeed() == that.getSeed();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSeed());
    }

    @Override
    public String toString() {
        return "WeightRandomizer{" +
                "seed=" + seed +
                '}';
    }
}
