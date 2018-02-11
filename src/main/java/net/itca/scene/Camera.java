package net.itca.scene;

import net.itca.datastructure.Point3;
import net.itca.datastructure.Vector3;
import net.itca.ray.Ray;
import org.jetbrains.annotations.NotNull;

/**
 * Created by dylan on 11.02.18.
 */
public class Camera {

    @NotNull
    private final Vector3 lowerLeftCorner;

    @NotNull
    private final Vector3 horizontal;

    @NotNull
    private final Vector3 vertical;

    @NotNull
    private final Point3 origin;

    public Camera(@NotNull Vector3 lowerLeftCorner,
                  @NotNull Vector3 horizontal,
                  @NotNull Vector3 vertical,
                  @NotNull Point3 origin) {
        this.lowerLeftCorner = lowerLeftCorner;
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.origin = origin;
    }

    @NotNull
    public Ray getRay(double horizontalOffset, double verticalOffset) {
        Vector3 horizontalModifier = horizontal.scalarMultiply(horizontalOffset);
        Vector3 verticalModifier = vertical.scalarMultiply(verticalOffset);
        // ray from origin to 'lower left + horizontal + vertical' vectors
        return new Ray(origin, lowerLeftCorner.addVector(horizontalModifier).addVector(verticalModifier));
    }
}
