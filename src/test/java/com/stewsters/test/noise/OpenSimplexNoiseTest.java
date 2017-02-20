package com.stewsters.test.noise;

import com.stewsters.util.noise.OpenSimplexNoise;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OpenSimplexNoiseTest {
    private static final int WIDTH = 512;
    private static final int HEIGHT = 512;
    private static final double FEATURE_SIZE = 24;

    @Test
    public void testNoiseGen3d() {

        OpenSimplexNoise noise = new OpenSimplexNoise();

        double value = noise.eval(2 / FEATURE_SIZE, 3 / FEATURE_SIZE, 0.0);
        assert value >= -1 && value <= 1;
    }

    @Test
    public void testNoiseGen4d() {

        OpenSimplexNoise noise = new OpenSimplexNoise();

        double value = noise.eval(2 / FEATURE_SIZE, 3 / FEATURE_SIZE, 0.0, 21);
        assert value >= -1 && value <= 1;
    }

    private void printImage(double[][] noise) throws IOException {

        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {

                int rgb = 0x010101 * (int) ((noise[x][y] + 1) * 127.5);
                image.setRGB(x, y, rgb);
            }
        }
        ImageIO.write(image, "png", new File("noise.png"));
    }
}
