package net.itca;

import net.itca.datastructure.Point3;
import net.itca.datastructure.Vector3;
import net.itca.geometry.Renderable;
import net.itca.geometry.Sphere;
import net.itca.geometry.material.Dielectric;
import net.itca.geometry.material.Lambertian;
import net.itca.geometry.material.Metal;
import net.itca.ray.Ray;
import net.itca.scene.Camera;
import net.itca.scene.Scene;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by dylan on 13.02.18.
 * Class representing our image that we want to render
 */
public class World {


    private final int xs = 200; // x-axis pixels (width)
    private final int ys = 100; // y-axis pixels (height)
    private final int as = 100; // anti-aliasing loops

    @NotNull
    private final Camera camera;
    @NotNull
    private final Scene scene;

    public World(){
        this.camera = setupCamera();
        this.scene = setupScene();
    }

    @NotNull
    private Camera setupCamera(){
        final Vector3 lowerLeftCorner = new Vector3(-2, -1, -1);
        final Vector3 horizontal = new Vector3(4, 0, 0);
        final Vector3 vertical = new Vector3(0, 2, 0);
        final Point3 origin = new Point3(0, 0, 0);
        return new Camera(lowerLeftCorner, horizontal, vertical, origin);
    }

    @NotNull
    private Scene setupScene(){
        List<Renderable> objects = new ArrayList<>();
        final Sphere sphere1 = new Sphere(new Point3(0, 0, -1), 0.5, new Lambertian(new Colour(0.1, 0.2, 0.5)));
        final Sphere sphere2 = new Sphere(new Point3(0, -100.5, -1), 100, new Lambertian(new Colour(0.8, 0.8, 0)));
        final Sphere sphere3 = new Sphere(new Point3(1, 0, -1), 0.5, new Metal(new Colour(0.8, 0.6, 0.2),0));
        final Sphere sphere4 = new Sphere(new Point3(-1, 0, -1), 0.5, new Dielectric(1.5));
        objects.addAll(Arrays.<Renderable>asList(sphere1, sphere2,sphere3,sphere4));
        return new Scene(objects);
    }

    /**
     * Return a PPM-encoded representation of our world
     * @return
     */
    @NotNull
    public String renderAsPPM(){
        StringBuilder builder = new StringBuilder();
        final Random rand = new Random();
        builder.append("P3\n");
        builder.append(String.format("%d %d 255\n", xs, ys));
        for(int y = ys-1; y >= 0; y--) {
            for (int x = 0; x < xs; x++) {
                // avoid having "1" as random value (because we'd be off a pixel!)
                Colour baseColour = new Colour(0, 0, 0);
                for (int aliasingLoop = 0; aliasingLoop < as; aliasingLoop++) {
                    double u = (x + rand.nextDouble() - 0.111_110d) / xs;
                    double v = (y + rand.nextDouble()- 0.111_110d) / ys;
                    Ray ray = camera.getRay(u, v);
                    Colour hitColour = scene.castRay(ray);
                    baseColour = new Colour(baseColour.getR() + hitColour.getR(),
                            baseColour.getG() + hitColour.getG(),
                            baseColour.getB() + hitColour.getB());
                }
                // take the average of the aliasing loops
                final Colour colour = new Colour(baseColour.getR() / as, baseColour.getG() / as, baseColour.getB() / as);
                // modify for each pixel the actual direction of the vector slightly
                double[] rgb = colour.getRGBGammaCorrection();
                builder.append(String.format("%d %d %d\n", (int) (rgb[0] * 255.99), (int) (rgb[1] * 255.99), (int) (rgb[2] * 255.99)));
            }
        }
        return builder.toString();
    }

}
