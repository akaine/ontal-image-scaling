package org.ontal.imgutil.scaling;

import java.awt.image.BufferedImage;

/**
 * Image scaling processor base class.
 *
 * @author akaine
 * @since May 2012
 */
public abstract class TransformImage {

    protected final BufferedImage inputImage;
    protected final int inputWidth;
    protected final int inputHeight;
    protected final float inputRatio;
    protected final int imageType;

    protected int outputWidth;
    protected int outputHeight;
    protected float outputRatio;

    private BufferedImage outputImage;

    /**
     * Default base constructor. Initializes base input image parameters like
     * image dimensions and ratio, as well as its bitmap type.
     *
     * @param inputImage input image
     */
    protected TransformImage(final BufferedImage inputImage) {
        this.inputImage = inputImage;
        inputWidth = inputImage.getWidth();
        inputHeight = inputImage.getHeight();
        inputRatio = (float)inputWidth / inputHeight;
        imageType = inputImage.getType() == 0 ? 1 : inputImage.getType();
    }

    /**
     * Main transformation entry point. Obtains all additional parameters
     * required for image particular processing.
     *
     * @param args transformation arguments
     * @return scaled image
     */
    public BufferedImage transform(final Object... args) {
        validateArguments(args);
        outputWidth = calculateOutputWidth(args);
        outputHeight = calculateOutputHeight(args);
        outputRatio = (float)outputWidth / outputHeight;

        outputImage = process();
        return outputImage;
    }

    /**
     * Validates transformation arguments and throws
     * an {@link IllegalArgumentException} if any of the validations fail.
     *
     * @param args transformation arguments
     */
    protected void validateArguments(final Object... args) {
        final int outputWidth = (int)args[0];
        final int outputHeight = (int)args[1];

        if(outputWidth <= 0) {
            throw new IllegalArgumentException("Invalid output width argument");
        }
        if(outputHeight <= 0) {
            throw new IllegalArgumentException("Invalid output height argument");
        }
    }

    /**
     * Main image processing method that implements the algorithm required
     * to achieve a particular scaling transformation.
     *
     * @return scaled image
     */
    protected abstract BufferedImage process();

    /**
     * Obtains output image width based on the provided arguments.
     *
     * @param args transformation arguments
     * @return output image width
     */
    protected int calculateOutputWidth(final Object... args) {
        return (int)args[0];
    }

    /**
     * Obtains output image height based on the provided arguments.
     *
     * @param args transformation arguments
     * @return image height
     */
    protected int calculateOutputHeight(final Object... args) {
        return (int)args[1];
    }

    /**
     * Helps to determine if the output image requires additional unsharpening.
     * This is useful for small images that get too sharp after downscaling.
     *
     * @param width image width
     * @param height image width
     * @return <code>true</code> if the image requires unsharpening, otherwise <code>false</code>
     */
    protected boolean requiresUnsharpening(final int width, final int height) {
        if(width < inputWidth && width < 200 || height < inputHeight && height < 200) {
            return true;
        }
        return false;
    }
}
