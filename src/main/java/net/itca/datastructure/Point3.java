package net.itca.datastructure;

import org.jetbrains.annotations.NotNull;

/**
 * Created by dylan on 09.02.18.
 * A point in 3d space
 */
public class Point3 implements Triple<Double> {

    private double a;
    private double b;
    private double c;

    public Point3(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Double getA() {
        return a;
    }

    public Double getB() {
        return b;
    }

    public Double getC() {
        return c;
    }

    /**
     * Subtracting a point from a point returns a vector from A->B
     * @param point
     * @return
     */
    @NotNull
    public Vector3 subPoint3(@NotNull Triple<Double> point){
        return new Vector3(a - point.getA(), b - point.getB(), c - point.getC());
    }

    /**
     * Return the dot product of this point and another one
     * @param vector3
     * @return
     */
    public double dot(@NotNull Triple<Double> point) {
        return a * point.getA() + b * point.getB() + c * point.getC();
    }
}
