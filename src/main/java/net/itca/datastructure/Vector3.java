package net.itca.datastructure;

import org.jetbrains.annotations.NotNull;

/**
 * Created by dylan on 09.02.18.
 * Vector in 3d space
 */
public class Vector3 implements Triple<Double> {

    private double a;
    private double b;
    private double c;

    public Vector3(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Vector3(@NotNull Triple<Double> triple) {
        this.a = triple.getA();
        this.b = triple.getB();
        this.c = triple.getC();
    }

    @Override
    public Double getA(){
        return a;
    }

    @Override
    public Double getB(){
        return b;
    }

    @Override
    public Double getC(){
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
    public Vector3 addVector(@NotNull Triple<Double> vector){
        return new Vector3(a + vector.getA(), b + vector.getB(), c + vector.getC());
    }

    @NotNull
    public Vector3 subVector(@NotNull Triple<Double> vector){
        return new Vector3(a - vector.getA(), b - vector.getB(), c - vector.getC());
    }

    @NotNull
    public Vector3 vectorMult(@NotNull Triple<Double> vector) {
        return new Vector3(a * vector.getA(), b * vector.getB(), c * vector.getC());
    }

    @NotNull
    public Vector3 vectorDiv(@NotNull Triple<Double> vector) {
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
    public double dot(@NotNull Triple<Double> vector) {
        return a * vector.getA() + b * vector.getB() + c * vector.getC();
    }

    /**
     * Return the cross product of this vector and another one
     * @param vector3
     * @return
     */
    @NotNull
    public Vector3 cross(@NotNull Triple<Double> vector) {
        double _a = b * vector.getC() - c * vector.getB();
        double _b = (a * vector.getC() - c * vector.getA()) * -1;
        double _c = a * vector.getB() - b * vector.getA();
        return new Vector3(_a, _b, _c);
    }

}
