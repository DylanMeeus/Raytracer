package net.itca.scene;

import net.itca.datastructure.Point3;
import net.itca.datastructure.Vector3;
import net.itca.ray.Ray;
import net.itca.util.DiffuseUtil;
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

    private final double lensRadius;


    private final Vector3 w;
    private final Vector3 u;
    private final Vector3 v;

    public Camera(@NotNull Point3 from,
                  @NotNull Vector3 at,
                  @NotNull Vector3 vup,
                  double verticalFov, // fov in degrees
                  double aspect,
                  double aperture,
                  double focusDistance) {

        lensRadius = aperture / 2;


        double theta = verticalFov * (Math.PI / 180);
        double halfHeight = Math.tan(theta / 2);
        double halfWidth = aspect * halfHeight;

        w = from.subPoint3(at).getUnitVector();
        u = vup.cross(w).getUnitVector();
        v = w.cross(u);


        this.origin = from;
        Vector3 uwidth = u.scalarMultiply(halfWidth * focusDistance);
        Vector3 vheight = v.scalarMultiply(halfHeight * focusDistance);

        this.lowerLeftCorner = origin.subPoint3(uwidth)
                .subVector(vheight)
                .subVector(w.scalarMultiply(focusDistance));

        this.horizontal = u.scalarMultiply(halfWidth * 2 * focusDistance);
        this.vertical = v.scalarMultiply(2 * halfHeight * focusDistance);
    }

    @NotNull
    public Ray getRay(double horizontalOffset, double verticalOffset) {
        Vector3 rd = DiffuseUtil.randomUnitSphereVector().scalarMultiply(lensRadius);
        Vector3 offset = u.scalarMultiply(rd.getA()).addVector(v.scalarMultiply(rd.getB()));

        Vector3 horizontalModifier = horizontal.scalarMultiply(horizontalOffset);
        Vector3 verticalModifier = vertical.scalarMultiply(verticalOffset);
        // ray from origin to 'lower left + horizontal + vertical' vectors

        // ray from origin - offset ---> lowerleft - horizontalMod * verticalMod - origin - offset
        return new Ray(new Point3(new Vector3(origin).addVector(offset)),
                lowerLeftCorner.addVector(horizontalModifier)
                        .addVector(verticalModifier)
                        .subVector(origin)
                        .subVector(offset));
    }
}
