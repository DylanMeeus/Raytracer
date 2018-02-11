package net.itca.geometry;

import net.itca.Point3;
import org.jetbrains.annotations.NotNull;

/**
 * Created by dylan on 09.02.18.
 */
public class Sphere implements Renderable {

    @NotNull
    private Point3 centre;
    private double radius;

    public Sphere(@NotNull Point3 centre, double radius) {
        this.centre = centre;
        this.radius = radius;
    }

    @NotNull
    public Point3 getCentre(){
        return centre;
    }

    public double getRadius(){
        return radius;
    }

}
