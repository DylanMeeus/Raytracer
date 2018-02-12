package net.itca.geometry.material;

import net.itca.Colour;
import net.itca.datastructure.Vector3;
import net.itca.ray.HitData;
import net.itca.ray.Ray;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Created by dylan on 12.02.18.
 */
public class Metal implements Material {

    @NotNull
    private final Colour albedo;

    public Metal(@NotNull Colour albedo) {
        this.albedo = albedo;
    }

    @Override
    public ScatterData scatter(@NotNull Ray ray, @NotNull Colour attenuation, @NotNull HitData originalHitData) {
        Objects.requireNonNull(originalHitData.getNormal());
        Vector3 unitVector = ray.getDirection().getUnitVector();
        Vector3 reflection = reflect(unitVector, originalHitData.getNormal());
        Ray scattered = new Ray(originalHitData.getP(), reflection);
        double dot = scattered.getDirection().dot(originalHitData.getNormal());
        attenuation = albedo;
        return new ScatterData(dot > 0, scattered, attenuation);
    }

    @NotNull
    private Vector3 reflect(@NotNull Vector3 ray, @NotNull Vector3 normal) {
        double dot = ray.dot(normal);
        normal = normal.scalarMultiply(dot).scalarMultiply(2);
        return ray.subVector(normal);
    }
}
