package net.itca.ray;

import net.itca.datastructure.Point3;
import net.itca.datastructure.Vector3;
import net.itca.geometry.Sphere;
import org.jetbrains.annotations.NotNull;

/**
 * Created by dylan on 09.02.18.
 */
public class SphereIntersectionChecker extends RayIntersectionChecker<Sphere> {

    private final double tMin;
    private final double tMax;

    /**
     * Checks if a sphere and ray intersects, in a certain range along the path (tmin, tmax)
     * @param tMin
     * @param tMax
     */
    public SphereIntersectionChecker(double tMin, double tMax) {
        this.tMin = tMin;
        this.tMax = tMax;
    }

    /**
     * To find out an intersection between a sphere and a ray, the equation
     * (p-c) . (p-c) = R*R needs to be satisfied.
     * Where (p-c) is the vector of the centre (c) to point (p)
     * @param ray
     * @param sphere
     * @return
     */
    @Override
    @NotNull
    public HitData getIntersectionHitData(@NotNull Sphere sphere, @NotNull Ray ray) {
        // todo: don't ignore tmin, tmax
        Point3 origin = ray.getOrigin();
        Vector3 oc = origin.subPoint3(sphere.getCentre());
        double a = ray.getDirection().dot(ray.getDirection());
        double b = 2d * ray.getDirection().dot(oc);
        double c = oc.dot(oc) - sphere.getRadius() * sphere.getRadius();
        double discriminant = (b * b) - (4 * a * c);

        if (discriminant > 0) {

            // first of two Discriminant solves
            double firstRoot = ((b*-1) - Math.sqrt(discriminant)) / (2 * a);
            double secondRoot = ((b*-1) + Math.sqrt(discriminant)) / (2 * a);
            Double hitPoint = null;

            // determine if we have hit a root in our range
            if (firstRoot < tMax && firstRoot > tMin) {
                hitPoint = firstRoot;
            } else if (secondRoot < tMax && secondRoot > tMin) {
                hitPoint = secondRoot;
            }

            if (hitPoint != null) {
                Vector3 normal = ray.pointAtPosition(hitPoint).subPoint3(sphere.getCentre());
                return new HitData(true, hitPoint, normal, ray.pointAtPosition(hitPoint));
            }
        }
        return new HitData(false);
    }
}
