package net.itca.geometry.material;

import net.itca.Colour;
import net.itca.datastructure.Point3;
import net.itca.datastructure.Vector3;
import net.itca.ray.HitData;
import net.itca.ray.Ray;
import net.itca.util.DiffuseUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Created by dylan on 11.02.18.
 * An ideal matte (diffusely reflecting) material
 */
public class Lambertian implements Material{

    @NotNull
    private final Colour albedo;

    /**
     * albedo is a 'default colour for attenuation'
     * @param albedo
     */
    public Lambertian(@NotNull Colour albedo) {
        this.albedo = albedo;
    }

    @Override
    public ScatterData scatter(@NotNull Ray ray,
                               @NotNull Colour attenuation,
                               @NotNull HitData originalHitData) {
        Point3 originalP = originalHitData.getP();
        double hitPoint = originalHitData.getHitpoint();
        Vector3 normal = originalHitData.getNormal();

        Vector3 target = new Vector3(originalP).addVector(normal).addVector(DiffuseUtil.randomUnitSphereVector());
        Ray scattered = new Ray(originalP, target.subVector(originalP));
        attenuation = albedo;
        return new ScatterData(true, scattered, attenuation);
    }
}
