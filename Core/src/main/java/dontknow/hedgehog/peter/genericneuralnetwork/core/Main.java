package dontknow.hedgehog.peter.genericneuralnetwork.core;

import dontknow.hedgehog.peter.genericneuralnetwork.core.components.NeuralNetwork;
import dontknow.hedgehog.peter.genericneuralnetwork.core.components.weights.NodeAmountBasedWeightRandomizer;
import dontknow.hedgehog.peter.genericneuralnetwork.core.components.weights.WeightRandomizer;
import dontknow.hedgehog.peter.genericneuralnetwork.core.configs.ConnectorConfig;
import dontknow.hedgehog.peter.genericneuralnetwork.core.configs.LayerConfig;
import dontknow.hedgehog.peter.genericneuralnetwork.core.configs.NeuralNetworkConfig;

import java.util.Arrays;

public class Main {

    public static void main(String arg[]) {

        final NeuralNetworkConfig neuralNetworkConfig = new NeuralNetworkConfig(
                new ConnectorConfig(
                        new NodeAmountBasedWeightRandomizer(123663)
                ),
                new LayerConfig(10),
                new LayerConfig(10),
                new LayerConfig(10)
        );

        final NeuralNetwork neuralNetwork = new NeuralNetwork(neuralNetworkConfig);

        final double[] testData = new double[]{
                1.0,
                0.5,
                0.25,
                0.00001,
                0.444,
                0.3,
                0.222,
                0.1,
                0.7,
                0.65
        };

        final double[] output = neuralNetwork.process(testData);


        System.out.println("Output: \n\t"+ Arrays.toString(output));

    }
}
