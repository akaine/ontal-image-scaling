package org.ontal.imgutil.scaling;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleOp;

/**
 * Fit scaling transformation implementation class.
 *
 * @author akaine
 * @since May 2012
 */
public class FitImage extends TransformImage {

    private int[] rgb = { 255, 255, 255 };

    protected FitImage(final BufferedImage inputImage) {
        super(inputImage);
    }

    @Override
    protected void validateArguments(final Object... args) {
        super.validateArguments(args);

        final int[] rgb = (int[])args[2];
        if(rgb != null) {
            if(rgb.length != 3) {
                throw new IllegalArgumentException("Invalid output rgb argument");
            }
            for(int i = 0; i < 3; i++) {
                if(rgb[i] < 0 || rgb[i] > 255) {
                    throw new IllegalArgumentException("Invalid output rgb color index");
                }
            }

            this.rgb = rgb;
        }
    }

    @Override
    protected BufferedImage process() {

        int width, height;
        if(inputRatio > outputRatio) {
            // output image is thinner
            width = outputWidth;
            height = (int)Math.floor(outputWidth / inputRatio);

        }
        else {
            // output image is wider
            width = (int)Math.floor(outputHeight * inputRatio);
            height = outputHeight;
        }

        // scale input image maintaining its ratio, sharpen it if it's becoming too small
        final ResampleOp resampleOp = new ResampleOp(width, height);
        resampleOp.setUnsharpenMask(requiresUnsharpening(width, height) ? AdvancedResizeOp.UnsharpenMask.Soft : AdvancedResizeOp.UnsharpenMask.None);
        final BufferedImage image = resampleOp.filter(inputImage, null);

        // create output image canvas and fill it with bg color
        final BufferedImage outputImage = new BufferedImage(outputWidth, outputHeight, imageType);
        final Graphics2D graphics = outputImage.createGraphics();
        graphics.setPaint(new Color(rgb[0], rgb[1], rgb[2]));
        graphics.fillRect(0, 0, outputWidth, outputHeight);

        // put scaled image on canvas
        final int x = (int)Math.floor((double)(outputWidth - width) / 2);
        final int y = (int)Math.floor((double)(outputHeight - height) / 2);
        graphics.drawImage(image, null, x, y);
        graphics.dispose();

        return outputImage;
    }
}
