package net.itca;

import org.jetbrains.annotations.NotNull;

/**
 * Created by dylan on 09.02.18.
 * Vector in 3d space
 */
public class Vector3 {

    private double a;
    private double b;
    private double c;

    public Vector3(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getA(){
        return a;
    }

    public double getB(){
        return b;
    }

    public double getC(){
        return c;
    }

    public double length(){
        return Math.sqrt(a * a + b * b + c * c);
    }

    @NotNull
    public Vector3 getUnitVector(){
        double k = 1d / length();
        return new Vector3(a * k, b * k, c * k);
    }

    @NotNull
    public Vector3 addVector(@NotNull Vector3 vector){
        return new Vector3(a + vector.getA(), b + vector.getB(), c + vector.getC());
    }

    @NotNull
    public Vector3 subVector(@NotNull Vector3 vector){
        return new Vector3(a - vector.getA(), b - vector.getB(), c - vector.getC());
    }

    @NotNull
    public Vector3 vectorMult(@NotNull Vector3 vector) {
        return new Vector3(a * vector.getA(), b * vector.getB(), c * vector.getC());
    }

    @NotNull
    public Vector3 vectorDiv(@NotNull Vector3 vector) {
        return new Vector3(a / vector.getA(), b / vector.getB(), c / vector.getC());
    }

    @NotNull
    public Vector3 scalarMultiply(double scalar) {
        return new Vector3(a * scalar, b * scalar, c * scalar);
    }

    @NotNull
    public Vector3 scalarDiv(double scalar) {
        return new Vector3(a / scalar, b / scalar, c / scalar);
    }

    /**
     * Return the dot product of this vector and another one
     * @param vector3
     * @return
     */
    public double dot(@NotNull Vector3 vector) {
        return a * vector.getA() + b * vector.getB() + c * vector.getC();
    }

    /**
     * Return the cross product of this vector and another one
     * @param vector3
     * @return
     */
    @NotNull
    public Vector3 cross(@NotNull Vector3 vector) {
        double _a = b * vector.getC() - c * vector.getB();
        double _b = (a * vector.getC() - c * vector.getA()) * -1;
        double _c = a * vector.getB() - b * vector.getA();
        return new Vector3(_a, _b, _c);
    }

}
