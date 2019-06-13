package org.ontal.imgutil.scaling;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Tile transformation implementation class.
 *
 * @author akaine
 * @since May 2012
 */
public class TileImage extends TransformImage {

    public TileImage(final BufferedImage inputImage) {
        super(inputImage);
    }

    @Override
    protected BufferedImage process() {

        // create canvas
        final BufferedImage image = new BufferedImage(outputWidth, outputHeight, imageType);

        // fill canvas with input images
        final Graphics2D graphics = image.createGraphics();
        final int timesX = outputWidth / inputWidth + 1;
        final int timesY = outputHeight / inputHeight + 1;

        for(int y = 0; y < timesY; y++) {
            for(int x = 0; x < timesX; x++) {
                graphics.drawImage(inputImage, null, inputWidth * x, inputHeight * y);
            }
        }
        graphics.dispose();

        return image;
    }

}
