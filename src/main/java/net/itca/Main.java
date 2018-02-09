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

        builder.append(String.format("%d %d 255\n", xs, ys));

        for(int y = ys-1; y >= 0; y--) {
            for (int x = 0; x < xs; x++) {
                double r = ((double) x) / xs;
                double g = ((double) y) / ys;
                double b = 0.2d;
                // convert to int representation
                int ir = (int) (r * 255.99);
                int ig = (int) (g * 255.99);
                int ib = (int) (b * 255.99);
                builder.append(String.format("%d %d %d\n",ir,ig,ib));
            }
        }
        String ppmData = builder.toString();
        System.out.println(ppmData);
        writeImage(ppmData);
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
