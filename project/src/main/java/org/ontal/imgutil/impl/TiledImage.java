package org.ontal.imgutil.impl;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.ontal.imgutil.OImage;
import org.ontal.imgutil.exceptions.SettingsException;
import org.ontal.imgutil.settings.Settings;
import org.ontal.imgutil.settings.Strategy;

/**
 * {@link OImage} implementation class oriented to process and transform image
 * according to "tile" processing strategy ({@link Strategy#TILE}) provided in
 * the {@link Settings} parameter during the constructor call.
 * <br><br>
 * The particular transformation algorithm consists in tiling the target
 * canvas with the original image copies. Isn't particularly useful but what
 * the hell =).
 *
 * @author akaine
 * @since May 2012
 */
public class TiledImage implements OImage {

    private BufferedImage inputImage;
    private BufferedImage outputImage;
    private Settings settings;

    // Constructors ===============================================================================

    /**
     * Default constructor. The user is encouraged to use the extended version
     * instead since it's not recommended to change this implementation local
     * fields posterior its initialization.
     */
    public TiledImage() {
    }

    /**
     * Specific constructor. Recommended way of implementation initialization.
     *
     * @param inputImage input image
     * @param settings transformation setting to be applied to the input image
     */
    public TiledImage(final BufferedImage inputImage, final Settings settings) {
        this.inputImage = inputImage;
        this.settings = settings;
    }

    // Implementations ============================================================================

    @Override
    public void processByRatio() throws SettingsException {
        settingsValidatorByRatio(settings);

        final int origWidth = inputImage.getWidth();
        final int origHeight = inputImage.getHeight();

        final int[] targetDims = Utils.calcTargetDims(settings);
        final int targetWidth = targetDims[0];
        final int targetHeight = targetDims[1];

        outputImage = process(inputImage, settings, origWidth, origHeight, targetWidth, targetHeight);
    }

    @Override
    public void processByDimmensions() throws SettingsException {
        settingsValidatorByDimmensions(settings);

        final int origWidth = inputImage.getWidth();
        final int origHeight = inputImage.getHeight();
        final int targetWidth = settings.getImagesize().getWidth();
        final int targetHeight = settings.getImagesize().getHeight();

        outputImage = process(inputImage, settings, origWidth, origHeight, targetWidth, targetHeight);
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
            final int targetWidth,
            final int targetHeight) {

        outputImage = new BufferedImage(targetWidth, targetHeight, inputImage.getType());
        final Graphics2D graphics = outputImage.createGraphics();

        final int timesX = targetWidth / origWidth + 1;
        final int timesY = targetHeight / origHeight + 1;
        final int startX = 0, startY = 0;

        for(int y = 0; y < timesY; y++) {
            for(int x = 0; x < timesX; x++) {
                graphics.drawImage(inputImage, null, startX + origWidth * x, startY + origHeight * y);
            }
        }
        graphics.dispose();

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
