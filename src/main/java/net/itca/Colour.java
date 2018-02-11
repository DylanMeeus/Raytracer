package net.itca;

/**
 * Created by dylan on 09.02.18.
 */
public class Colour {

    private double r;
    private double g;
    private double b;

    public Colour(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public double getR(){
        return r;
    }

    public double getG(){
        return g;
    }

    public double getB(){
        return b;
    }

    public double[] getRGB(){
        return new double[]{r, g, b};
    }

    /**
     * Return the RGB values after applying gamma correction to each
     * @return
     */
    public double[] getRGBGammaCorrection(){
        double[] normalValues = getRGB();
        for (int i = 0; i < normalValues.length; i++) {
            normalValues[i] = Math.sqrt(normalValues[i]);
        }
        return normalValues;
    }

}
