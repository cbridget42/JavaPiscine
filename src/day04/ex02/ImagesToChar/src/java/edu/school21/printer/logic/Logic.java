package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Logic {
    private static final String IMAGE_PATH = "/resources/it.bmp";

    public static boolean[][] BMPToArray() throws IOException {
        BufferedImage image = ImageIO.read(Logic.class.getResource(IMAGE_PATH));
        boolean[][] result = new boolean[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                result[i][j] = (image.getRGB(j, i) == Color.BLACK.getRGB()) ? true : false;
            }
        }
        return result;
    }
}
