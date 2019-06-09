package org.ontal.imgutil.scaling;

import java.awt.image.BufferedImage;

import org.ontal.imgutil.Dimension;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleOp;

/**
 * Limit scaling transformation implementation class.
 *
 * @author akaine
 * @since May 2012
 */
public class LimitImage extends TransformImage {

    protected LimitImage(final BufferedImage inputImage) {
        super(inputImage);
    }

    @Override
    protected void validateArguments(final Object... args) {
        final Dimension dimension = (Dimension)args[0];
        final int size = (int)args[1];

        if(dimension == null) {
            throw new IllegalArgumentException("Dimension argument cannot be null");
        }
        if(size <= 0) {
            throw new IllegalArgumentException("Invalid dimension size argument");
        }
    }

    @Override
    protected int calculateOutputWidth(final Object... args) {
        final Dimension dimension = (Dimension)args[0];
        final int size = (int)args[1];

        if(Dimension.WIDTH.equals(dimension)) {
            return size;
        }
        else {
            return (int)Math.floor((double)size / inputRatio);
        }
    }

    @Override
    protected int calculateOutputHeight(final Object... args) {
        final Dimension dimension = (Dimension)args[0];
        final int size = (int)args[1];

        if(Dimension.WIDTH.equals(dimension)) {
            return (int)Math.floor((double)size / inputRatio);
        }
        else {
            return size;
        }
    }

    @Override
    protected BufferedImage process() {

        // straight scale the image sharpening it if it's too small
        final ResampleOp resampleOp = new ResampleOp(outputWidth, outputHeight);
        resampleOp.setUnsharpenMask(requiresUnsharpening(outputWidth, outputHeight) ? 
                AdvancedResizeOp.UnsharpenMask.Soft : AdvancedResizeOp.UnsharpenMask.None);
        return resampleOp.filter(inputImage, null);
    }

}
