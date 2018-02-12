package net.itca.geometry.material;

import net.itca.Colour;
import net.itca.datastructure.Vector3;
import net.itca.ray.Ray;
import org.jetbrains.annotations.NotNull;

/**
 * Created by dylan on 12.02.18.
 */
public class ScatterData {

    // Is the scattered data reflected (e.g, it's not pointing _inside_ the surface
    private final boolean isReflected;
    @NotNull
    private final Ray scatteredRay;
    @NotNull
    private final Colour attenuation;

    public ScatterData(boolean isReflected,
                       @NotNull Ray scatteredRay,
                       @NotNull Colour attenuation) {
        this.isReflected = isReflected;
        this.scatteredRay = scatteredRay;
        this.attenuation = attenuation;
    }

    @NotNull
    public Ray getScatteredRay(){
        return scatteredRay;
    }

    @NotNull
    public Colour getAttenuation(){
        return attenuation;
    }

    public boolean isReflected(){
        return isReflected;
    }
}
