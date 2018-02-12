package net.itca.geometry;

import net.itca.datastructure.Point3;
import net.itca.geometry.material.Material;
import org.jetbrains.annotations.NotNull;

/**
 * Created by dylan on 09.02.18.
 */
public class Sphere implements Renderable {

    @NotNull
    private final Point3 centre;
    private final double radius;

    @NotNull
    private final Material material;

    public Sphere(@NotNull Point3 centre,
                  double radius,
                  @NotNull Material material) {
        this.centre = centre;
        this.radius = radius;
        this.material = material;
    }

    @NotNull
    public Point3 getCentre(){
        return centre;
    }

    public double getRadius(){
        return radius;
    }

    @Override
    @NotNull
    public Material getMaterial() {
        return material;
    }
}
