package net.itca;

import net.itca.geometry.Renderable;
import net.itca.geometry.Sphere;
import net.itca.ray.HitData;
import net.itca.ray.Ray;
import net.itca.ray.SphereIntersectionChecker;
import net.itca.scene.Scene;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final SphereIntersectionChecker sphereChecker = new SphereIntersectionChecker(Double.MIN_VALUE, Double.MAX_VALUE);

    public static void main(String[] args) {
        Scene scene = setupScene();
        //region <Raytrace>
        StringBuilder builder = new StringBuilder();
        builder.append("P3\n");
        int xs = 200;
        int ys = 100;

        Vector3 lowerLeftCorner = new Vector3(-2, -1, -1);
        Vector3 horizontal = new Vector3(4, 0, 0);
        Vector3 vertical = new Vector3(0, 2, 0);
        Point3 origin = new Point3(0, 0, 0);

        builder.append(String.format("%d %d 255\n", xs, ys));

        for(int y = ys-1; y >= 0; y--) {
            for (int x = 0; x < xs; x++) {
                double u = ((double) x) / xs;
                double v = ((double) y) / ys;

                // modify for each pixel the actual direction of the vector slightly
                Vector3 horizontalModifier = horizontal.scalarMultiply(u);
                Vector3 verticalModifier = vertical.scalarMultiply(v);
                Ray ray = new Ray(origin, lowerLeftCorner.addVector(horizontalModifier).addVector(verticalModifier));
                Colour colour = colourTest(ray,scene);
                double[] rgb = colour.getRGB();
                builder.append(String.format("%d %d %d\n", (int) (rgb[0] * 255.99), (int) (rgb[1] * 255.99), (int) (rgb[2] * 255.99)));
            }
        }
        String ppmData = builder.toString();
        writeImage(ppmData);
        //endregion
    }

    @NotNull
    private static Scene setupScene(){
        List<Renderable> objects = new ArrayList<>();
        final Sphere sphere1 = new Sphere(new Point3(0, 0, -1), 0.5);
        final Sphere sphere2 = new Sphere(new Point3(0, -100.5, -1), 100);
        objects.addAll(Arrays.<Renderable>asList(sphere1, sphere2));
        return new Scene(objects);
    }


    public static Colour colourTest(Ray ray, Scene scene) {
        //region <Sphere>
        for (Renderable renderable : scene.getRenderables()) {
            Sphere sphere = (Sphere) renderable;
            HitData data = sphereChecker.getIntersectionHitData(sphere, ray);
            if (data.isHit()) {
                double hitpoint = data.getHitpoint();
                if (hitpoint > 0d) {
                    // vector from the centre of the sphere to the hitpoint -> This is the normal to the surface
                    Vector3 centreToHitpoint = ray.pointAtPosition(hitpoint).subPoint3(sphere.getCentre());
                    Vector3 unitVectorCentreHitpoint = centreToHitpoint.getUnitVector();
                    Vector3 modifier = new Vector3(
                            unitVectorCentreHitpoint.getA() + 1,
                            unitVectorCentreHitpoint.getB() + 1,
                            unitVectorCentreHitpoint.getC() + 1
                    );
                    Vector3 colourVector = modifier.scalarMultiply(0.5);
                    return new Colour(colourVector.getA(), colourVector.getB(), colourVector.getC());
                }
            }
        }
        //endregion

        // else colour the background gradiant..
        //region <Background gradient>
        Vector3 direction = ray.getDirection();
        Vector3 unitDirection = direction.getUnitVector();
        double t = 0.5d * (unitDirection.getB() + 1d);
        Vector3 first = new Vector3(1, 1, 1).scalarMultiply(1 - t);
        Vector3 snd = new Vector3(0.5, 0.7, 1).scalarMultiply(t);
        Vector3 res = first.addVector(snd);
        return new Colour(res.getA(), res.getB(), res.getC());
        //endregion
    }


    /**
     * write an ppm image based on the incoming data
     * @param data
     */
    public static void writeImage(@NotNull String data){
        Path path = Paths.get("/home/dylan/Development/Code/java/Raytracer/src/main/resources/image.ppm");
        try {
            String[] lines = data.split("\n");
            Files.write(path, Arrays.asList(lines), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
