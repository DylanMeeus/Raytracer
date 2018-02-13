package net.itca.geometry.material;

import net.itca.Colour;
import net.itca.datastructure.Vector3;
import net.itca.ray.HitData;
import net.itca.ray.Ray;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Created by dylan on 13.02.18.
 * Dielectric materials (like glass, water) refract rays whenever possible
 */
public class Dielectric implements Material {

    private final double refrationIndex;

    public Dielectric(double refrationIndex) {
        this.refrationIndex = refrationIndex;
    }

    @Override
    public ScatterData scatter(@NotNull Ray ray, @NotNull Colour attenuation, @NotNull HitData originalHitData) {
        Vector3 reflected = reflect(ray, originalHitData.getNormal());
        double niOverNt;
        attenuation = new Colour(1, 1, 1);
        Vector3 outwardNormal = null;
        if (ray.getDirection().dot(originalHitData.getNormal()) > 0) {
            // we were pointing inside, so turn it around
            outwardNormal = originalHitData.getNormal().scalarMultiply(-1);
            niOverNt = refrationIndex;
        } else {
            outwardNormal = originalHitData.getNormal();
            niOverNt = 1 / refrationIndex;
        }

        Vector3 refraction = refract(ray, outwardNormal, niOverNt);
        Ray scatteredRay = null;
        Objects.requireNonNull(originalHitData.getP());
        boolean shouldReflectMore = false;
        if (refraction != null) {
            scatteredRay = new Ray(originalHitData.getP(), refraction);
            shouldReflectMore = true;
        } else {
            scatteredRay = new Ray(originalHitData.getP(), reflected);
        }
        ScatterData scatterData = new ScatterData(shouldReflectMore, scatteredRay, attenuation);
        return scatterData;
    }

    @Nullable
    private Vector3 refract(@NotNull Ray ray,
                            @NotNull Vector3 normal,
                            double niOverNt) {
        Vector3 unitDirection = ray.getDirection().getUnitVector();
        double dot = unitDirection.dot(normal);
        double discriminant = 1 - (Math.pow(niOverNt, 2) * (1 - Math.pow(dot, 2)));
        if (discriminant > 0) {
            Vector3 scaledNormal = normal.scalarMultiply(dot);
            Vector3 refractionDirection = unitDirection.subVector(scaledNormal);
            Vector3 scaledRefDir = refractionDirection.scalarMultiply(niOverNt);
            Vector3 normalDisc = normal.scalarMultiply(Math.sqrt(discriminant));
            Vector3 refracted = scaledRefDir.subVector(normalDisc);
            return refracted;
        }
        return null;
    }

    @NotNull
    private Vector3 reflect(@NotNull Ray ray, @NotNull Vector3 normal) {
        Vector3 unitDirection = ray.getDirection().getUnitVector();
        double dot = unitDirection.dot(normal);
        normal = normal.scalarMultiply(dot).scalarMultiply(2);
        return unitDirection.subVector(normal);
    }
}
