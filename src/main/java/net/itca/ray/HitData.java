package net.itca.ray;

import net.itca.Vector3;
import org.jetbrains.annotations.NotNull;

public class HitData{
    private final double hitpoint;
    private final Vector3 normal;
    private final Vector3 p;

    public HitData(double hitPoint,
                   @NotNull Vector3 normal,
                   @NotNull Vector3 p){
        this.hitpoint = hitPoint;
        this.normal = normal;
        this.p = p;
    }

    public double getHitpoint(){
        return hitpoint;
    }

    @NotNull
    public Vector3 getNormal(){
        return normal;
    }

    @NotNull
    public Vector3 getP(){
        return p;
    }
}
