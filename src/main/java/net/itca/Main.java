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
        final World world = new World();
        final String ppm = world.renderAsPPM();
        writeImage(ppm);
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
