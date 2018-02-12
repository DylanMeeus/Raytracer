package net.itca.geometry.material;

import net.itca.Colour;
import net.itca.ray.HitData;
import net.itca.ray.Ray;
import org.jetbrains.annotations.NotNull;

/**
 * Created by dylan on 11.02.18.
 */
public interface Material {

    // decides how the material deals with scattering rays
    public ScatterData scatter(@NotNull Ray ray,
                               @NotNull Colour attenuation,
                               @NotNull HitData originalHitData);

}
