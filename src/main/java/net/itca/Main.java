package net.itca;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

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

                Vector3 horizontalModifier = horizontal.scalarMultiply(u);
                Vector3 verticalModifier = vertical.scalarMultiply(v);
                Ray ray = new Ray(origin, lowerLeftCorner.addVector(horizontalModifier).addVector(verticalModifier));
                Colour colour = colourTest(ray);
                double[] rgb = colour.getRGB();
                builder.append(String.format("%d %d %d\n", (int) (rgb[0] * 255.99), (int) (rgb[1] * 255.99), (int) (rgb[2] * 255.99)));
            }
        }
        String ppmData = builder.toString();
        System.out.println(ppmData);
        writeImage(ppmData);
    }


    public static Colour colourTest(Ray ray) {
        Vector3 direction = ray.getDirection();
        Vector3 unitDirection = direction.getUnitVector();
        double t = 0.5d * (unitDirection.getB() + 1d);
        Vector3 first = new Vector3(1, 1, 1).scalarMultiply(1 - t);
        Vector3 snd = new Vector3(0.5, 0.7, 1).scalarMultiply(t);
        Vector3 res = first.addVector(snd);
        return new Colour(res.getA(), res.getB(), res.getC());
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
