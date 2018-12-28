package dontknow.hedgehog.peter.genericneuralnetwork.core;

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
        return "Connector{" +
                "from=" + from +
                ", to=" + to +
                ", weights=" + Arrays.toString(weights) +
                '}';
    }
}
