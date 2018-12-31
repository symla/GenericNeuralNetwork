package dontknow.hedgehog.peter.genericneuralnetwork.core;

import com.sun.deploy.util.StringUtils;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import dontknow.hedgehog.peter.genericneuralnetwork.core.components.NeuralNetwork;
import dontknow.hedgehog.peter.genericneuralnetwork.core.components.weights.MinMaxWeightRandomizer;
import dontknow.hedgehog.peter.genericneuralnetwork.core.components.weights.NodeAmountBasedWeightRandomizer;
import dontknow.hedgehog.peter.genericneuralnetwork.core.configs.ConnectorConfig;
import dontknow.hedgehog.peter.genericneuralnetwork.core.configs.LayerConfig;
import dontknow.hedgehog.peter.genericneuralnetwork.core.configs.NeuralNetworkConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.SocketHandler;

public class Main {

    public static int[][] trainingImages;
    public static int[][] testImages;

    public static int[] trainingLabels;
    public static int[] testLabels;


    public static double[][] double_trainingImages;
    public static double[][] double_testImages;

    public static double[][] double_trainingLabels;
    public static double[][] double_testLabels;

    public static void main(String arg[]) {

        try {
            trainingImages = readMNISTImageFile("train-images-idx3-ubyte");
            testImages = readMNISTImageFile("t10k-images-idx3-ubyte");

            trainingLabels = readMNISTLabelFile("train-labels-idx1-ubyte");
            testLabels = readMNISTLabelFile("t10k-labels-idx1-ubyte");

/*
            printImage(28, 28, trainingImages[1]);
            System.out.println("Label is: "+trainingLabels[1]);
            System.out.println("");

            printImage(28, 28, testImages[1]);
            System.out.println("Label is: "+testLabels[1]);
            System.out.println("");


            */



            System.out.println("Converting images to neural network double input...");
            double_trainingImages = intImagesToDoubleImages(trainingImages);
            double_testImages = intImagesToDoubleImages(testImages);
            System.out.println("Finished.");
            System.out.println("");

            System.out.println("Converting labels to neural network double input...");
            double_trainingLabels = intLabelsToDoubleLabels(trainingLabels);
            double_testLabels = intLabelsToDoubleLabels(testLabels);
            System.out.println("Finished.");
            System.out.println("");

        } catch ( IOException e ) {
            throw new RuntimeException("Error reading test images.", e);
        }


        final NeuralNetworkConfig neuralNetworkConfig = new NeuralNetworkConfig(
                new ConnectorConfig(
                        //1216: 97.67% at 20 iterations and 150 nodes
                        //1218: 97.71% at 20 iterations and 150 nodes
                        //1218: 97.61% at 30 iterations and 150 nodes

                        //1218: 96.22% at 15 iterations and 80 nodes

                        //1218: 97.09% at 15 15 iterations and 150 nodes
                        new NodeAmountBasedWeightRandomizer(1218)
                        //new MinMaxWeightRandomizer(1320, -0.2, 0.2)
                ),
                0.01,
                new LayerConfig(28*28),
                new LayerConfig(10),
                //new LayerConfig(150),
                new LayerConfig(150)
        );

        final NeuralNetwork neuralNetwork = new NeuralNetwork(neuralNetworkConfig);


        /*
        System.out.println();

        System.out.println(Arrays.toString(trainingImages[0]));
        System.out.println(Arrays.toString(double_trainingImages[0]));

        System.out.println();

        System.out.println(Arrays.toString(testImages[0]));
        System.out.println(Arrays.toString(double_testImages[0]));

        System.out.println();
        */

        //if ( true ) return;

        final List<Integer> indicies = new ArrayList<>();
        for ( int i = 0; i < double_trainingImages.length; i++ ) {
            indicies.add(i);
        }

        final Random shuffleRandom = new Random(122);


        System.out.println("Starting training session...");
        for ( int round = 0; round < 15; round++ ) {

            System.out.println("\tRound: "+round);

            Collections.shuffle(indicies, shuffleRandom);

            for ( int i = 0; i < double_trainingImages.length; i++ ) {
                final int currentIndex = indicies.get(i);

                final double[] result = neuralNetwork.process(double_trainingImages[currentIndex]);
                neuralNetwork.processOutputExpectationSquared(double_trainingLabels[currentIndex]);

                if ( i % 999 == 0 ) System.out.println("\t\tProgress: "+i);
            }
        }

        System.out.println("Finished training session...");


        // Fertig trainiert

        /*
        printImage(28, 28, testImages[0]);
        System.out.println("Label is: "+testLabels[0]);

        final double[] testResult = neuralNetwork.process(double_testImages[0]);
        System.out.println("Result: "+Arrays.toString(double_testLabels[0]));
        System.out.println("Result: "+Arrays.toString(testResult));
        System.out.println("Result: "+maximumIndexOfArray(testResult));
        */


        // Testdaten

        System.out.println("Performing the test data set on the neural network ...");

        int hits = 0;
        int misses = 0;
        for ( int i = 0; i < double_testImages.length; i++ ) {
            final double[] testImageResult = neuralNetwork.process(double_testImages[i]);
            final int resultNumber = maximumIndexOfArray(testImageResult);
            if ( resultNumber == testLabels[i] ) hits++;
            else misses++;

            if ( i % 999 == 0 ) System.out.println("\tProgress: "+i);
        }
        System.out.println("Finished test data set. Results:");
        System.out.println("\tTotal test images:  "+(hits+misses));
        System.out.println("\t\tHits:      "+hits);
        System.out.println("\t\tMisses:    "+misses);
        System.out.println("");
        System.out.println("\t\tHit quote: "+((float)hits/(float)(hits+misses))*100.0f+"%");





    }

    private static int maximumIndexOfArray(final double[] array) {
        int maxIndex = 0;
        for ( int i = 1; i < array.length; i++ ) {
            if ( array[i] > array[maxIndex] ) maxIndex = i;
        }
        return maxIndex;
    }

    private static double[][] intLabelsToDoubleLabels(final int[] labels) {
        final double[][] doubleLabels = new double[labels.length][10];
        for ( int i = 0; i < labels.length; i++ ) {
            final int currentLabel = labels[i];
            for ( int j = 0; j < 10; j++ ) {
                if ( j == currentLabel ) doubleLabels[i][j] = 0.99;
                else doubleLabels[i][j] = 0.01;
            }
        }

        return doubleLabels;
    }

    private static double[][] intImagesToDoubleImages(final int[][] images) {
        final double[][] doubleImages = new double[images.length][];
        for ( int i = 0; i < images.length; i++ ) {
            doubleImages[i] = new double[images[i].length];
            for ( int j = 0; j < images[i].length; j++ ) {
                doubleImages[i][j] = imageIntToDouble(images[i][j]);
            }
        }

        return doubleImages;
    }

    private static double imageIntToDouble(final int value) {
        return ((double)value/255.0d)*0.90+0.01;
    }

    private static int[] readMNISTLabelFile(final String file) throws IOException {
        final File imagesFile = new File(ClassLoader.getSystemClassLoader().getResource(file).getFile());
        final InputStream in = new FileInputStream(imagesFile);

        final int magicNumber = readNextInt(in);
        final int numberOfLabels = readNextInt(in);

        System.out.println("Reading labels for file '"+file+"':");
        System.out.println("\tMagic Number:      "+magicNumber);
        System.out.println("\tNumber of labels:  "+numberOfLabels);


        final int[] labels = readNextLabels(numberOfLabels, in);

        System.out.println("Finished reading labels for file '"+file+"'.\n");

        return labels;
    }

    private static int[][] readMNISTImageFile(final String file) throws IOException {
        final File imagesFile = new File(ClassLoader.getSystemClassLoader().getResource(file).getFile());
        final InputStream in = new FileInputStream(imagesFile);

        final int magicNumber = readNextInt(in);
        final int numberOfImages = readNextInt(in);
        final int rows = readNextInt(in);
        final int columns = readNextInt(in);

        System.out.println("Reading images for file '"+file+"':");
        System.out.println("\tMagic Number:      "+magicNumber);
        System.out.println("\tNumber of images:  "+numberOfImages);
        System.out.println("\tRows:              "+rows);
        System.out.println("\tColumns:           "+columns);

        final int images[][] = new int[numberOfImages][rows*columns];
        for ( int i = 0; i < numberOfImages; i++ ) {
            /*for ( int j = 0; j < rows*columns; j++ ) {
                testImages[i][j] = in.read();
            }*/
            images[i] = readNextImage(rows, columns, in);
            if ( i % 1000 == 999 ) System.out.println("\t\tProgress: "+i+" images");
        }

        System.out.println("Finished reading images for file '"+file+"'.\n");

        return images;
    }

    private static int[] readNextLabels(final int amount, final InputStream in) throws IOException {
        final int[] labels = new int[amount];
        final byte buffer[] = new byte[amount];

        in.read(buffer);
        for ( int i = 0; i < buffer.length; i++ ) {
            labels[i] = Byte.toUnsignedInt(buffer[i]);
        }
        return labels;
    }

    private static int[] readNextImage(final int rows, final int columns, final InputStream in) throws IOException {
        final int[] image = new int[rows*columns];
        final byte buffer[] = new byte[rows*columns];

        in.read(buffer);
        for ( int i = 0; i < buffer.length; i++ ) {
            image[i] = Byte.toUnsignedInt(buffer[i]);
        }
        return image;
    }

    private static int readNextInt(final InputStream in) throws IOException {
        return (in.read() << 24) | (in.read() << 16) | (in.read() << 8) | (in.read() << 0);
    }

    private static void printImage(final int rows, final int columns, final int images[]) {
        final StringBuilder sb1 = new StringBuilder();
        sb1.append("#");
        for ( int i = 0; i < columns; i++ ) sb1.append("-");
        sb1.append("#");

        System.out.println(sb1.toString());

        for ( int y = 0; y < columns; y++ ) {
            final StringBuilder sb = new StringBuilder();
            sb.append("|");
            for ( int x = 0; x < columns; x++ ) {
                final int value = images[y*columns+x];
                if ( value > 127 ) sb.append(" ");
                else if ( value > 40 ) sb.append(":");
                else sb.append("#");
            }
            sb.append("|");
            System.out.println(sb.toString());
        }

        System.out.println(sb1.toString());
    }
}
