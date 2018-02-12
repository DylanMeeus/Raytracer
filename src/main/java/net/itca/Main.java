package net.itca;

import net.itca.datastructure.Point3;
import net.itca.datastructure.Vector3;
import net.itca.geometry.Renderable;
import net.itca.geometry.Sphere;
import net.itca.geometry.material.Lambertian;
import net.itca.geometry.material.Metal;
import net.itca.geometry.material.ScatterData;
import net.itca.ray.HitData;
import net.itca.ray.Ray;
import net.itca.ray.SphereIntersectionChecker;
import net.itca.scene.Camera;
import net.itca.scene.Scene;
import net.itca.util.DiffuseUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    // check rays right in front of the camera. But we add 0.001 to avoid 'shadow acne' (floating point approximation errors)
    private static final SphereIntersectionChecker sphereChecker = new SphereIntersectionChecker(0.01, Double.MAX_VALUE);

    public static void main(String[] args) {
        Scene scene = setupScene();
        Camera camera = setupCamera();
        //region <Raytrace>
        StringBuilder builder = new StringBuilder();
        builder.append("P3\n");

        final int xs = 200;
        final int ys = 100;
        final int as = 100; // anti=aliasing factor

        // for anti-aliasing, apply a small random factor to each offset!
        Random rand = new Random();

        builder.append(String.format("%d %d 255\n", xs, ys));

        for(int y = ys-1; y >= 0; y--) {
            for (int x = 0; x < xs; x++) {
                // avoid having "1" as random value (because we'd be off a pixel!)
                Colour baseColour = new Colour(0, 0, 0);
                for (int aliasingLoop = 0; aliasingLoop < as; aliasingLoop++) {
                    double u = (x + rand.nextDouble() - 0.11111d) / xs;
                    double v = (y + rand.nextDouble()- 0.1111d) / ys;
                    Ray ray = camera.getRay(u, v);
                    Colour hitColour = colourTest(ray,scene,0);
                    baseColour = new Colour(baseColour.getR() + hitColour.getR(),
                            baseColour.getG() + hitColour.getG(),
                            baseColour.getB() + hitColour.getB());
                }
                // take the average of the aliasing loops
                Colour colour = new Colour(baseColour.getR() / as, baseColour.getG() / as, baseColour.getB() / as);
                // modify for each pixel the actual direction of the vector slightly
                double[] rgb = colour.getRGBGammaCorrection();
                builder.append(String.format("%d %d %d\n", (int) (rgb[0] * 255.99), (int) (rgb[1] * 255.99), (int) (rgb[2] * 255.99)));
            }
        }
        String ppmData = builder.toString();
        writeImage(ppmData);
        //endregion
    }

    @NotNull
    private static Camera setupCamera(){
        final Vector3 lowerLeftCorner = new Vector3(-2, -1, -1);
        final Vector3 horizontal = new Vector3(4, 0, 0);
        final Vector3 vertical = new Vector3(0, 2, 0);
        final Point3 origin = new Point3(0, 0, 0);
        return new Camera(lowerLeftCorner, horizontal, vertical, origin);
    }

    @NotNull
    private static Scene setupScene(){
        List<Renderable> objects = new ArrayList<>();
        final Sphere sphere1 = new Sphere(new Point3(0, 0, -1), 0.5, new Lambertian(new Colour(0.8, 0.3, 0.3)));
        final Sphere sphere2 = new Sphere(new Point3(0, -100.5, -1), 100, new Lambertian(new Colour(0.8, 0.8, 0)));
        final Sphere sphere3 = new Sphere(new Point3(1, 0, -1), 0.5, new Metal(new Colour(0.8, 0.6, 0.2)));
        final Sphere sphere4 = new Sphere(new Point3(-1, 0, -1), 0.5, new Metal(new Colour(0.8, 0.8, 0.8)));
        objects.addAll(Arrays.<Renderable>asList(sphere1, sphere2,sphere3,sphere4));
        return new Scene(objects);
    }


    public static Colour colourTest(Ray ray, Scene scene, int depth) {
        return scene.castRay(ray);
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
            System.out.println("Wrote image to: " + path.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
