package net.itca.geometry;

import net.itca.geometry.material.Material;
import org.jetbrains.annotations.NotNull;

/**
 * Created by dylan on 11.02.18.
 */
public interface Renderable {

    @NotNull
    public Material getMaterial();

}
