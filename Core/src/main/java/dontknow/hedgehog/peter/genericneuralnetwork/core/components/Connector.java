package dontknow.hedgehog.peter.genericneuralnetwork.core.components;

import dontknow.hedgehog.peter.genericneuralnetwork.core.components.weights.WeightRandomizer;

import java.util.Arrays;
import java.util.Objects;

public class Connector {

    private final Layer from;

    private final Layer to;

    private final double[][] weights;

    public Connector(final Layer from,
                     final Layer to) {
        if ( from == null ) throw new NullPointerException("From layer must not be null.");
        if ( to == null ) throw new NullPointerException("To layer must not be null.");

        this.from = from;
        this.to = to;

        this.weights = new double[from.getLayerConfig().getNodeAmount()][to.getLayerConfig().getNodeAmount()];
    }

    public void randomizeWeights(final WeightRandomizer weightRandomizer) {
        if ( weightRandomizer == null ) throw new NullPointerException("WeightRandomizer must not be null.");

        for ( int i = 0; i < weights.length; i++ ) {
            for ( int j = 0; j < weights[i].length; j++ ) {
                weights[i][j] = weightRandomizer.getNextRandomWeight(to.getLayerConfig(), from.getLayerConfig());
            }
        }
    }

    public Layer getFrom() {
        return from;
    }

    public Layer getTo() {
        return to;
    }

    public double[][] getWeights() {
        return weights;
    }

    public double getWeight(final int fromIndex, final int toIndex) {
        return this.weights[fromIndex][toIndex];
    }

    public void setWeight(final int fromIndex, final int toIndex, final double weight) {
        this.weights[fromIndex][toIndex] = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connector connector = (Connector) o;
        return Objects.equals(getFrom(), connector.getFrom()) &&
                Objects.equals(getTo(), connector.getTo()) &&
                Arrays.equals(getWeights(), connector.getWeights());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getFrom(), getTo());
        result = 31 * result + Arrays.hashCode(getWeights());
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for ( int i = 0; i < weights.length; i++ ) {
            if ( i > 0 && i < weights.length-1 ) sb.append(", ");
            sb.append("from ");
            sb.append(i);
            sb.append(": ");
            sb.append(Arrays.toString(this.weights[i]));
        }

        return "Connector{" +
                "from=" + from +
                ", to=" + to +
                ", weights=[" + sb.toString() +"]"+
                '}';
    }
}
