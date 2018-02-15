package net.itca.util;

import net.itca.datastructure.Vector3;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * Created by dylan on 11.02.18.
 * Utility class for creating diffuse materials (Matte)
 */
public interface DiffuseUtil {

    /**
     * Create a random vector inside a unit sphere
     * This can be used to get a random vector from the hitpoint to somewhere else
     * @return
     */
    @NotNull
    public static Vector3 randomUnitSphereVector(){
        // take a random vector, if it lies outside the "unit square" we try again
        Vector3 randVector = null;
        double vectorLength = Double.MAX_VALUE;
        final Random rand = new Random();
        while (vectorLength >= 1) {
            randVector = new Vector3(rand.nextDouble(), rand.nextDouble(), rand.nextDouble());
            Vector3 unitVector = new Vector3(1, 1, 1);
            // multiply the random vector (so some values are above 1.0) and remove the unit vector
            randVector = randVector.scalarMultiply(2).subVector(unitVector);
            // take the length squared. (Because magnitudes are square roots which we don't want)
            vectorLength = randVector.length() * randVector.length();
        }
        return randVector;
    }

}
