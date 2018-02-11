package net.itca.ray;

import net.itca.datastructure.Vector3;
import org.jetbrains.annotations.Nullable;

public class HitData{

    // is our hit 'acceptable', with other words, should we render it because of the hit?
    private final boolean isHit;
    private final Double hitpoint;
    private final Vector3 normal;
    private final Vector3 p;

    public HitData(boolean isHit){
        this(isHit, null, null, null);
    }

    public HitData(boolean isHit,
                   @Nullable Double hitPoint,
                   @Nullable Vector3 normal,
                   @Nullable Vector3 p){
        this.isHit = isHit;
        this.hitpoint = hitPoint;
        this.normal = normal;
        this.p = p;
    }

    public boolean isHit(){
        return isHit;
    }

    public double getHitpoint(){
        return hitpoint;
    }

    @Nullable
    public Vector3 getNormal(){
        return normal;
    }

    @Nullable
    public Vector3 getP(){
        return p;
    }
}
