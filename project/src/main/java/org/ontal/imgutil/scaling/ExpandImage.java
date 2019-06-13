package org.ontal.imgutil.scaling;

import java.awt.image.BufferedImage;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleOp;

/**
 * Expand scaling transformation implementation class.
 *
 * @author akaine
 * @since May 2012
 */
public class ExpandImage extends TransformImage {

    public ExpandImage(final BufferedImage inputImage) {
        super(inputImage);
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
