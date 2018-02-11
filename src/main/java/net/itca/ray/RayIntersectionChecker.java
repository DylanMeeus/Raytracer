package net.itca.ray;

import net.itca.geometry.Renderable;
import org.jetbrains.annotations.NotNull;

/**
 * Created by dylan on 11.02.18.
 */
public abstract class RayIntersectionChecker<T extends Renderable> {

    public abstract boolean insersects(@NotNull T renderable, @NotNull Ray ray);

    @NotNull
    public abstract HitData getIntersectionHitData(@NotNull T renderable, @NotNull Ray ray);
}
