package net.itca.scene;

import net.itca.Colour;
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
        for (Renderable renderable : getRenderables()) {
            Sphere sphere = (Sphere) renderable;
            // todo:: Pass in the world instead of the Sphere because we want the _world_ to be checked!!!
            // otherwise we just override ones we found
            HitData data = sphereChecker.getIntersectionHitData(sphere, ray);
            if (data.isHit()) {
                double hitpoint = data.getHitpoint();
                if (hitpoint > 0d) {
                    Objects.requireNonNull(data.getP());

                    // apply diffuse lighting to our colour vector
                    @NotNull Vector3 colourVector = new Vector3(data.getP()).addVector(data.getNormal()).addVector(DiffuseUtil.randomUnitSphereVector());
                    // we bounce the lightwave off


                    Colour attenuation = new Colour(0,0,0); // an initual attenuation
                    ScatterData scatterData = sphere.getMaterial().scatter(ray, attenuation, data);

                    if (depth < 50 && scatterData.isReflected()) {
                        attenuation = scatterData.getAttenuation();
                        Ray scatteredRay = scatterData.getScatteredRay();
                        Colour colour = castRay(scatteredRay , depth + 1);
                        return new Colour(
                                colour.getR() * attenuation.getR(),
                                colour.getG() * attenuation.getG(),
                                colour.getB() + attenuation.getB()
                        );
                    } else {
                        return new Colour(0, 0, 0);
                    }
                }
            }
        }
        //endregion

        // else colour the background gradiant..
        //region <Background gradient>
        Vector3 direction = ray.getDirection();
        Vector3 unitDirection = direction.getUnitVector();
        double t = 0.5d * (unitDirection.getB() + 1d);
        Vector3 first = new Vector3(1, 1, 1).scalarMultiply(1 - t);
        Vector3 snd = new Vector3(0.5, 0.7, 1).scalarMultiply(t);
        Vector3 res = first.addVector(snd);
        return new Colour(res.getA(), res.getB(), res.getC());
        //endregion
    }

    @NotNull
    private HitData getClosestHit(Ray ray) {
        return new HitData(false);
    }
}
