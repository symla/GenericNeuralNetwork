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
                        new NodeAmountBasedWeightRandomizer(1236634445)
                ),
                0.1,
                new LayerConfig(3),
                new LayerConfig(3),
                new LayerConfig(3)
        );

        final NeuralNetwork neuralNetwork = new NeuralNetwork(neuralNetworkConfig);

        final double[] testData = new double[]{
                0.1,
                0.5,
                0.25
        };

        final double[] expectedOutput = new double[]{
                0.9,
                0.001,
                0.02
        };

        double[] output = null;


        int n = 1000000;
        for ( int i = 0; i < n; i++ ) {
            output = neuralNetwork.process(testData);
            neuralNetwork.processOutputExpectation(expectedOutput);

            if ( i >= n-10 )
                System.out.println("Output: \n\t"+ Arrays.toString(output));
        }



    }
}
