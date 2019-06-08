package org.ontal.imgutil.impl;

import java.awt.image.BufferedImage;

import org.ontal.imgutil.OImage;
import org.ontal.imgutil.exceptions.SettingsException;
import org.ontal.imgutil.settings.Settings;
import org.ontal.imgutil.settings.Strategy;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleOp;

/**
 * {@link OImage} implementation class oriented to process and transform image
 * according to "limit" processing strategy ({@link Strategy#LIMIT}) provided
 * in the {@link Settings} parameter during the constructor call.
 * <br><br>
 * The particular transformation algorithm consists in fitting the original
 * within the limit defined in pixels along X or Y axis. If the original image
 * is smaller than the defined limit no changes are applied.
 * <br><br>
 * There is no {@link Settings} constructor provided with background color
 * parameter, so user is encouraged to set it manually. If left unspecified
 * the default beckground color is black.
 *
 * @author akaine
 * @since May 2012
 */
public class LimitedImage implements OImage {

    private BufferedImage inputImage;
    private BufferedImage outputImage;
    private Settings settings;

    /**
     * Default constructor. The user is encouraged to use the extended version
     * instead  since it's not recommended to change this implementation local
     * fields posterior its initialization.
     */
    public LimitedImage() {
    }

    /**
     * Specific constructor. Recommended way of implementation initialization.
     *
     * @param inputImage input image
     * @param settings transformation setting to be applied to the input image
     */
    public LimitedImage(final BufferedImage inputImage, final Settings settings) {
        this.inputImage = inputImage;
        this.settings = settings;
    }

    // Implementations ============================================================================

    //uneused
    @Override
    public void processByRatio() throws SettingsException {
        settingsValidatorByDimmensions(settings);
    }

    @Override
    public void processByDimmensions() throws SettingsException {
        settingsValidatorByDimmensions(settings);

        final int origWidth = inputImage.getWidth();
        final int origHeight = inputImage.getHeight();
        final Integer targetWidth = settings.getImagesize().getWidth();
        final Integer targetHeight = settings.getImagesize().getHeight();

        outputImage = process(inputImage, settings, origWidth, origHeight, targetWidth, targetHeight);
    }

    // Local support ==============================================================================

    /**
     * Validates {@link Settings} configuration consistency for
     * {@link #processByDimmensions()} method.
     *
     * @param settings transformation configurations
     * @throws SettingsException
     */
    private void settingsValidatorByDimmensions(final Settings settings) throws SettingsException {
        if(settings.getImagesize().getWidth() == null && settings.getImagesize().getHeight() == null) {
            throw new SettingsException(
                    "Bad output configuration (LIMIT): Neither of target dimensions are set. Limit strategy requires X or Y dimmension defined.",
                    new Throwable());
        }
        else if(settings.getImagesize().getWidth() != null && settings.getImagesize().getHeight() != null) {
            throw new SettingsException(
                    "Bad output configuration (LIMIT): Both target dimensions are set. Limit strategy requires only X or Y dimmension defined, not both.",
                    new Throwable());
        }
    }

    // Local support =============================================================================0

    /**
     * Applies actual transformations producing a resulted image copy.
     *
     * @param inputImage original image instance
     * @param settings transformation configurations
     * @param origWidth original image width
     * @param origHeight original image height
     * @param targetWidth target width
     * @param targetHeight target height
     * @return transformed image
     */
    private BufferedImage process(
            final BufferedImage inputImage,
            final Settings settings,
            final int origWidth,
            final int origHeight,
            final Integer targetWidth,
            final Integer targetHeight) {

        BufferedImage tempImage;
        int tempWidth, tempHeight;

        final float origRatio = (float)origWidth / origHeight;

        if(targetWidth != null) {
            if(origWidth <= targetWidth.intValue()) {
                outputImage = inputImage;
            }
            else {
                tempHeight = (int)Math.floor(targetWidth / origRatio);

                final ResampleOp resampleOp = new ResampleOp(targetWidth, tempHeight);
                resampleOp.setUnsharpenMask(targetWidth <= 200 ? AdvancedResizeOp.UnsharpenMask.Soft : AdvancedResizeOp.UnsharpenMask.None);
                tempImage = resampleOp.filter(inputImage, null);
                outputImage = tempImage;
            }
        }
        else {
            if(origHeight <= targetHeight.intValue()) {
                outputImage = inputImage;
            }
            else {
                tempWidth = (int)Math.floor(targetHeight * origRatio);

                final ResampleOp resampleOp = new ResampleOp(tempWidth, targetHeight);
                resampleOp.setUnsharpenMask(targetHeight <= 200 ? AdvancedResizeOp.UnsharpenMask.Soft : AdvancedResizeOp.UnsharpenMask.None);
                tempImage = resampleOp.filter(inputImage, null);
                outputImage = tempImage;
            }
        }

        return outputImage;
    }

    // Accessors ==================================================================================

    @Override
    public BufferedImage getOutputImage() {
        return outputImage;
    }
}
