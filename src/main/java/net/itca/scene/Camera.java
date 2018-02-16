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

    public Camera(@NotNull Point3 from,
                  @NotNull Vector3 at,
                  @NotNull Vector3 vup,
                  double verticalFov, // fov in degrees
                  double aspect) {

        double theta = verticalFov * (Math.PI / 180);
        double halfHeight = Math.tan(theta / 2);
        double halfWidth = aspect * halfHeight;

        Vector3 w = from.subPoint3(at).getUnitVector();
        Vector3 u = vup.cross(w).getUnitVector();
        Vector3 v = w.cross(u);


        this.origin = from;
        Vector3 uwidth = u.scalarMultiply(halfWidth);
        Vector3 vheight = v.scalarMultiply(halfHeight);

        this.lowerLeftCorner = origin.subPoint3(uwidth)
                .subVector(vheight)
                .subVector(w);

        this.horizontal = u.scalarMultiply(halfWidth * 2);
        this.vertical = v.scalarMultiply(2 * halfHeight);
    }

    @NotNull
    public Ray getRay(double horizontalOffset, double verticalOffset) {
        Vector3 horizontalModifier = horizontal.scalarMultiply(horizontalOffset);
        Vector3 verticalModifier = vertical.scalarMultiply(verticalOffset);
        // ray from origin to 'lower left + horizontal + vertical' vectors
        return new Ray(origin, lowerLeftCorner.addVector(horizontalModifier).addVector(verticalModifier).subVector(origin));
    }
}
