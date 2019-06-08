package org.ontal.imgutil.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.ontal.imgutil.OImage;
import org.ontal.imgutil.exceptions.SettingsException;
import org.ontal.imgutil.settings.ImageRatio;
import org.ontal.imgutil.settings.Settings;
import org.ontal.imgutil.settings.Strategy;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleOp;

/**
 * {@link OImage} implementation class oriented to process and transform image
 * according to "adjust" processing strategy ({@link Strategy#ADJUST})
 * provided in the {@link Settings} parameter during the constructor call.
 * <br><br>
 * The particular transformation algorithm consists in fitting and centering
 * the uncut input image onto the targets canvas with its original ratio
 * conserved leaving unocupied canvas space filled with provided background
 * color. Useful when a user needs to standardize the output dimmensions and at
 * the same time maintain the original image uncut.
 * <br><br>
 * There is no {@link Settings} constructor provided with background color
 * parameter, so user is encouraged to set it manually. If left unspecified
 * the default beckground color is black.
 *
 * @author akaine
 * @since May 2012
 */
public class AdjustedImage implements OImage {

    private BufferedImage inputImage;
    private BufferedImage outputImage;
    private Settings settings;

    // Constructors ===============================================================================

    /**
     * Default constructor. The user is encouraged to use the extended version
     * instead since it's not recommended to change this implementation local
     * fields posterior its initialization.
     */
    public AdjustedImage() {
    }

    /**
     * Specific constructor. Recommended way of implementation initialization.
     *
     * @param inputImage input image
     * @param settings transformation setting to be applied to the input image
     */
    public AdjustedImage(final BufferedImage inputImage, final Settings settings) {
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

        BufferedImage tempImage;
        int tempWidth, tempHeight, startX, startY;

        final float origRatio = (float)origWidth / origHeight;
        if(settings.getRatio() == null || settings.getRatio().getCoefficient() == 0.0) {
            settings.setRatio(new ImageRatio.Ratio(targetWidth, targetHeight));
            System.out.println(settings.getRatio().getCoefficient());
        }

        if(origRatio > settings.getRatio().getCoefficient()) {
            tempHeight = (int)Math.floor(targetWidth / origRatio);

            final ResampleOp resampleOp = new ResampleOp(targetWidth, tempHeight);
            resampleOp.setUnsharpenMask(targetHeight <= 200 ? AdvancedResizeOp.UnsharpenMask.Soft : AdvancedResizeOp.UnsharpenMask.None);
            tempImage = resampleOp.filter(inputImage, null);

            outputImage = new BufferedImage(targetWidth, targetHeight, correctImageType(tempImage));
            final Graphics2D graphics = outputImage.createGraphics();
            graphics.setPaint(new Color(settings.getBackgroundRGB()[0], settings.getBackgroundRGB()[1], settings.getBackgroundRGB()[2]));
            graphics.fillRect(0, 0, targetWidth, targetHeight);

            startY = (int)Math.floor((targetHeight - tempImage.getHeight()) / 2);
            graphics.drawImage(tempImage, null, 0, startY);
            graphics.dispose();
        }
        else {
            tempWidth = (int)Math.floor(targetHeight * origRatio);

            final ResampleOp resampleOp = new ResampleOp(tempWidth, targetHeight);
            resampleOp.setUnsharpenMask(targetWidth <= 200 ? AdvancedResizeOp.UnsharpenMask.Soft : AdvancedResizeOp.UnsharpenMask.None);
            tempImage = resampleOp.filter(inputImage, null);

            outputImage = new BufferedImage(targetWidth, targetHeight, correctImageType(tempImage));
            final Graphics2D graphics = outputImage.createGraphics();
            graphics.setPaint(new Color(settings.getBackgroundRGB()[0], settings.getBackgroundRGB()[1], settings.getBackgroundRGB()[2]));
            graphics.fillRect(0, 0, targetWidth, targetHeight);

            startX = (int)Math.floor((targetWidth - tempImage.getWidth()) / 2);
            graphics.drawImage(tempImage, null, startX, 0);
            graphics.dispose();
        }

        return outputImage;
    }

    private int correctImageType(final BufferedImage tempImage) {
        return tempImage.getType() == 0 ? 5 : tempImage.getType();
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
            throw new SettingsException("Bad output configuration (ADJUST): at least one: width or height is required", new Throwable());
        }
        if(settings.getRatio() == null) {
            throw new SettingsException("Bad output configuration (ADJUST): target ratio is not set", new Throwable());
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
            throw new SettingsException("Bad output configuration (ADJUST): target dimensions are not set", new Throwable());
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
