package net.itca;

import java.nio.file.attribute.PosixFilePermission;

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
}
