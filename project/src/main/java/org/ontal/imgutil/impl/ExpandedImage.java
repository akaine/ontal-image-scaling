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
 * according to "expand" processing strategy ({@link Strategy#EXPAND}) provided
 * in the {@link Settings} parameter during the constructor call.
 * <br><br>
 * The particular transformation algorithm consists in scaling the original
 * image to the target dimensions. When target ratio is different from the
 * original one an image distortion may occur. Useful when a user just needs to
 * scale the image.
 *
 * @author akaine
 * @since May 2012
 */
public class ExpandedImage implements OImage {

    private BufferedImage inputImage;
    private BufferedImage outputImage;
    private Settings settings;

    // Constructors ===============================================================================

    /**
     * Default constructor. The user is encouraged to use the extended version
     * instead since it's not recommended to change this implementation local
     * fields posterior its initialization.
     */
    public ExpandedImage() {
    }

    /**
     * Specific constructor. Recommended way of implementation initialization.
     *
     * @param inputImage input image
     * @param settings transformation setting to be applied to the input image
     */
    public ExpandedImage(final BufferedImage inputImage, final Settings settings) {
        this.inputImage = inputImage;
        this.settings = settings;
    }

    // Implementations ============================================================================

    @Override
    public void processByRatio() throws SettingsException {
        settingsValidatorByRatio(settings);

        final int[] targetDims = Utils.calcTargetDims(settings);
        final int targetWidth = targetDims[0];
        final int targetHeight = targetDims[1];

        outputImage = process(inputImage, settings, targetWidth, targetHeight);
    }

    @Override
    public void processByDimmensions() throws SettingsException {
        settingsValidatorByDimmensions(settings);

        final int targetWidth = settings.getImagesize().getWidth();
        final int targetHeight = settings.getImagesize().getHeight();

        outputImage = process(inputImage, settings, targetWidth, targetHeight);
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
            final int targetWidth,
            final int targetHeight) {

        final ResampleOp resampleOp = new ResampleOp(targetWidth, targetHeight);
        resampleOp.setUnsharpenMask(targetWidth < 200 && targetHeight < 200 ? AdvancedResizeOp.UnsharpenMask.Soft : AdvancedResizeOp.UnsharpenMask.None);
        outputImage = resampleOp.filter(inputImage, null);
        return outputImage;
    }

    /**
     * Validates {@link Settings} configuration consistency for
     * {@link #processByRatio()} method.
     *
     * @param settings transformation configurations
     * @throws SettingsException
     */
    private void settingsValidatorByRatio(final Settings settings) throws SettingsException {
        if(settings.getImagesize().getWidth() == null && settings.getImagesize().getHeight() == null) {
            throw new SettingsException("Bad output configuration: at least one: width or height is required", new Throwable());
        }
        if(settings.getRatio() == null) {
            throw new SettingsException("Bad output configuration: target ratio is not set", new Throwable());
        }
    }

    /**
     * Validates {@link Settings} configuration consistency for
     * {@link #processByDimmensions()} method.
     *
     * @param settings transformation configurations
     * @throws SettingsException
     */
    private void settingsValidatorByDimmensions(final Settings settings) throws SettingsException {
        if(settings.getImagesize().getWidth() == null && settings.getImagesize().getHeight() == null) {
            throw new SettingsException("Bad output configuration: target dimensions are not set", new Throwable());
        }
    }

    // Accessors ==================================================================================

    /**
     * {@link #inputImage} property getter.
     *
     * @return inputImage property value
     */
    public BufferedImage getInputImage() {
        return inputImage;
    }

    /**
     * {@link #inputImage} property setter.
     *
     * @param inputImage property value
     */
    public void setInputImage(final BufferedImage inputImage) {
        this.inputImage = inputImage;
    }

    @Override
    public BufferedImage getOutputImage() {
        return outputImage;
    }

    /**
     * {@link #outputImage} property setter.
     *
     * @param outputImage property value
     */
    public void setOutputImage(final BufferedImage outputImage) {
        this.outputImage = outputImage;
    }

    /**
     * {@link #settings} property getter.
     *
     * @return settings property value
     */
    public Settings getSettings() {
        return settings;
    }

    /**
     * {@link #settings} property setter.
     *
     * @param settings property value
     */
    public void setSettings(final Settings settings) {
        this.settings = settings;
    }
}
