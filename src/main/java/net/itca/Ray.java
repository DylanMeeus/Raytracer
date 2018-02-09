package net.itca;

import org.jetbrains.annotations.NotNull;

/**
 * Created by dylan on 09.02.18.
 * Representing a ray (origin, direction)
 */
public class Ray {

    @NotNull
    private Point3 origin;
    @NotNull
    private Vector3 direction;

    public Ray(@NotNull Point3 origin, @NotNull Vector3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    @NotNull
    public Point3 getOrigin(){
        return origin;
    }

    @NotNull
    public Vector3 getDirection(){
        return direction;
    }

    /**
     * Point at distance _t_ across the ray
     * @param t
     * @return
     */
    public Point3 pointAtPosition(double t) {
        Vector3 scalarVector = direction.scalarMultiply(t);
        Vector3 vector = scalarVector.addVector(origin);
        return new Point3(vector.getA(), vector.getB(), vector.getC());
    }
}
