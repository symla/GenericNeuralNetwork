package dontknow.hedgehog.peter.genericneuralnetwork.core.components.weights;

import dontknow.hedgehog.peter.genericneuralnetwork.core.configs.LayerConfig;

public class NodeAmountBasedWeightRandomizer extends WeightRandomizer {

    public NodeAmountBasedWeightRandomizer(long seed) {
        super(seed);
    }

    @Override
    public double getNextRandomWeight(final LayerConfig fromConfig,
                                      final LayerConfig toConfig) {

        if ( fromConfig == null ) throw new NullPointerException("FromConfig must not be null.");
        if ( toConfig ==null ) throw new NullPointerException("ToConfig must not be null.");

        final double max = 1.0/Math.sqrt(fromConfig.getNodeAmount());
        final double min = -1.0*max;
        final double amount = max-min;

        return this.random.nextDouble()*amount+min;
    }
}
