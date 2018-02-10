package net.itca.ray;

import net.itca.Point3;
import net.itca.Vector3;
import net.itca.geometry.Sphere;

/**
 * Created by dylan on 09.02.18.
 */
public class RayIntersectionChecker {

    /**
     * To find out an intersection between a sphere and a ray, the equation
     * (p-c) . (p-c) = R*R needs to be satisfied.
     * Where (p-c) is the vector of the centre (c) to point (p)
     * @param ray
     * @param sphere
     * @return
     */
    public static boolean insersectsSphere(Ray ray, Sphere sphere) {
        return sphereHitPoint(ray, sphere) > -1;
    }

    public static double sphereHitPoint(Ray ray, Sphere sphere) {
        Point3 origin = ray.getOrigin();
        Vector3 oc = origin.subPoint3(sphere.getCentre());
        double a = ray.getDirection().dot(ray.getDirection());
        double b = 2d * ray.getDirection().dot(oc);
        double c = oc.dot(oc) - sphere.getRadius() * sphere.getRadius();
        double discriminant = (b * b) - (4 * a * c);
        if (discriminant < 0) {
            return -1;
        } else {
            return ((b*-1) - Math.sqrt(discriminant)) / (2 * a);
        }

    }
}
