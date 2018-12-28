package dontknow.hedgehog.peter.genericneuralnetwork.core;

public class Util {

    public static double sigmoid(final double x) {
        if( x < -10 )       return 0;
        else if( x > 10 )   return 1;
        else                return 1 / (1 + Math.exp(-x));
    }

}
