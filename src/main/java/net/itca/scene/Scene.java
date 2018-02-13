package net.itca.scene;

import net.itca.Colour;
import net.itca.datastructure.Point3;
import net.itca.datastructure.Tuple;
import net.itca.datastructure.Vector3;
import net.itca.geometry.Renderable;
import net.itca.geometry.Sphere;
import net.itca.geometry.material.ScatterData;
import net.itca.ray.HitData;
import net.itca.ray.Ray;
import net.itca.ray.SphereIntersectionChecker;
import net.itca.util.DiffuseUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by dylan on 11.02.18.
 * Represents a scene with renderable objects
 */
public class Scene {

    @NotNull
    private static SphereIntersectionChecker sphereChecker = new SphereIntersectionChecker(0.001, Double.MAX_VALUE);

    @NotNull
    private final List<Renderable> renderables;

    public Scene(@Nullable List<Renderable> renderables){
        this.renderables = renderables == null ? Collections.<Renderable>emptyList() : renderables;
    }

    @NotNull
    public List<Renderable> getRenderables(){
        return renderables;
    }

    public Colour castRay(@NotNull Ray ray) {
        return castRay(ray, 0);
    }

    /**
     * Cast a ray through the scene
     * @param ray
     * @return
     */
    @NotNull
    private Colour castRay(@NotNull Ray ray, int depth){
        Tuple<Renderable, HitData> closestHit = getClosestHit(ray);
        HitData data = closestHit.getB();
        Renderable renderable = closestHit.getA();
        if (data != null) {
            return getRenderableColour(ray, renderable, data, depth);
        }
        return createBackgroundGradient(ray);
    }

    @NotNull
    private Colour getRenderableColour(@NotNull Ray ray,
                                       @NotNull Renderable renderable,
                                       @NotNull HitData data,
                                       int depth){
        double hitpoint = data.getHitpoint();
        if (hitpoint > 0d) {
            Objects.requireNonNull(data.getP());
            Colour attenuation = new Colour(0, 0, 0); // an initual attenuation
            ScatterData scatterData = renderable.getMaterial().scatter(ray, attenuation, data);

            if (depth < 50 && scatterData.isReflected()) {
                attenuation = scatterData.getAttenuation();
                Ray scatteredRay = scatterData.getScatteredRay();
                Colour colour = castRay(scatteredRay, depth + 1);
                return new Colour(
                        colour.getR() * attenuation.getR(),
                        colour.getG() * attenuation.getG(),
                        colour.getB() * attenuation.getB()
                );
            } else {
                return new Colour(0, 0, 0);
            }
        }
        // else our hitpoint does not lie in the range, and we just return the background gradient
        return createBackgroundGradient(ray);
    }

    @NotNull
    private Colour createBackgroundGradient(@NotNull Ray ray){
        Vector3 direction = ray.getDirection();
        Vector3 unitDirection = direction.getUnitVector();
        double t = 0.5d * (unitDirection.getB() + 1d);
        Vector3 first = new Vector3(1, 1, 1).scalarMultiply(1 - t);
        Vector3 snd = new Vector3(0.5, 0.7, 1).scalarMultiply(t);
        Vector3 res = first.addVector(snd);
        return new Colour(res.getA(), res.getB(), res.getC());
    }

    @Nullable
    private Tuple<Renderable, HitData> getClosestHit(Ray ray) {
        HitData hitData = null;
        Sphere sphere = null;
        for (Renderable renderable : getRenderables()) {
            Sphere currentSphere = (Sphere) renderable;
            HitData hit = sphereChecker.getIntersectionHitData(currentSphere, ray);
            if (hit.isHit()) {
                if (hitData == null || hit.getHitpoint() < hitData.getHitpoint()) {
                    hitData = hit;
                    sphere = currentSphere;
                }
            }
        }
        return new Tuple<>(sphere, hitData);
    }
}
