package org.ontal.imgutil.scaling;

import java.awt.image.BufferedImage;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleOp;

/**
 * Adjust scaling transformation implementation class.
 *
 * @author akaine
 * @since May 2012
 */
public class AdjustImage extends TransformImage {

    public AdjustImage(final BufferedImage inputImage) {
        super(inputImage);
    }

    @Override
    protected BufferedImage process() {

        int width, height;
        if(inputRatio > outputRatio) {
            // output image is thinner
            width = (int)Math.floor(outputHeight * inputRatio);
            height = outputHeight;
        }
        else {
            // output image is wider
            width = outputWidth;
            height = (int)Math.floor(outputWidth / inputRatio);
        }

        // scale input image maintaining its ratio, sharpen it if it's becoming too small
        final ResampleOp resampleOp = new ResampleOp(width, height);
        resampleOp.setUnsharpenMask(requiresUnsharpening(width, height) ? 
                AdvancedResizeOp.UnsharpenMask.Soft : AdvancedResizeOp.UnsharpenMask.None);
        final BufferedImage image = resampleOp.filter(inputImage, null);

        // crop image to fit into output dimensions
        final int x = (int)Math.floor((double)(width - outputWidth) / 2);
        final int y = (int)Math.floor((double)(height - outputHeight) / 2);
        return image.getSubimage(x, y, outputWidth, outputHeight);
    }

}
