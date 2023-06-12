package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Logic {
    public static char[][] BMPToArray(char white, char black, String BMPPath) throws IOException {
        BufferedImage image = ImageIO.read(new File(BMPPath));
        char[][] result = new char[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                result[i][j] = (image.getRGB(j, i) == Color.BLACK.getRGB()) ? black : white;
            }
        }
        return result;
    }
}
