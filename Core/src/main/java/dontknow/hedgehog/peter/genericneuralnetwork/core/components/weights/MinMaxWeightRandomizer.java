package dontknow.hedgehog.peter.genericneuralnetwork.core.components.weights;

import dontknow.hedgehog.peter.genericneuralnetwork.core.configs.LayerConfig;

public class MinMaxWeightRandomizer extends WeightRandomizer {

    private final double min;

    private final double max;

    private final double amount;

    public MinMaxWeightRandomizer(final long seed,
                                  final double min,
                                  final double max) {
        super(seed);
        this.min = min;
        this.max = max;
        this.amount = this.max-this.min;
    }

    public double getNextRandomWeight(final LayerConfig fromConfig,
                                      final LayerConfig toConfig) {

            return this.random.nextDouble()*this.amount+this.min;
    }


}
