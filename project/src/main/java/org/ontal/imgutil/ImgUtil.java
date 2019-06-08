package org.ontal.imgutil;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.ontal.imgutil.exceptions.SettingsException;
import org.ontal.imgutil.impl.AdjustedImage;
import org.ontal.imgutil.impl.ExpandedImage;
import org.ontal.imgutil.impl.FitImage;
import org.ontal.imgutil.impl.LimitedImage;
import org.ontal.imgutil.impl.TiledImage;
import org.ontal.imgutil.settings.ImageFormat;
import org.ontal.imgutil.settings.ImageRatio;
import org.ontal.imgutil.settings.ImageSize;
import org.ontal.imgutil.settings.Settings;
import org.ontal.imgutil.settings.Strategy;

/**
 * This utility allows advanced image transformations based on initial
 * parameters and selected transformation strategy.
 * <br><br>
 * There are several transofmation strategies available: Fit, Adjust, Expand,
 * Tile and Limit. Each strategy requires certain minimal parameters set via
 * {@link Settings} class while others are deduced during the image processing.
 * <br><br><br>
 *
 * <b>Adjust strategy</b>
 * <br><br>
 * In case target ratio is different from the original scales the image adding
 * borders. Borders background color is configured separately via
 * {@link Settings#setBackgroundRGB(int[])}.
 * <br><br>
 * <i>Useful for standardizing output images.</i>
 * <br><br>
 * Required {@link Settings} parameters:
 * <ul>
 * <li>{@link ImageSize} - if has only one dimmension defined then the other is
 * calculated from the provided ratio, otherwise Ratio parameter can be omitted
 * or set to AUTO</li>
 * <li>{@link ImageRatio} - target ratio</li>
 * <li>{@link ImageFormat} - output format</li>
 * <li>{@link Strategy#ADJUST}</li>
 * </ul>
 * <br>
 * Example1:
 * <code>new Settings(new ImageSize(200, null), ImageRatio.X1Y1, ImageFormat.PNG, Strategy.ADJUST);</code><br>
 * Example2:
 * <code>new Settings(new ImageSize(300, 200), ImageFormat.PNG, Strategy.ADJUST);</code><br>
 * <br><br><br>
 *
 * <b>Limit strategy</b>
 * <br><br>
 * The image is scaled to fit into Y or X limit defined in pixels while
 * maintaining its original ratio. If the original corresponding dimmension is
 * smaller than the defined limit no changes are applied.
 * <br><br>
 * <i>Useful for setting a dimmensional limit for big images.</i>
 * <br><br>
 * Required {@link Settings} parameters:
 * <ul>
 * <li>{@link ImageSize} that defines one dimmension only in pixels to fit to</li>
 * <li>{@link ImageFormat} - output format</li>
 * <li>{@link Strategy#LIMIT}</li>
 * </ul>
 * <br>
 * Example:
 * <code>new Settings(new ImageSize(200, null), ImageFormat.PNG, Strategy.LIMIT);</code>
 * <br><br><br>
 *
 * @author akaine
 * @since May 2012
 */
public final class ImgUtil {
    /**
     * Reads image from the local file system, transforms it acording to the
     * configuration defined in {@link Settings}  and saves its transformed
     * version in the local file system.
     *
     * @param inputPath input image absolute file path
     * @param outputDir output directory path (without separator at the end)
     * @param outputName output image file name (without extension)
     * @param settings image transformations configuration
     * @throws SettingsException in case of invalid settings
     * @throws IOException in case of an I/O error
     */
    public static void transformAndWrite(
            final String inputPath,
            final String outputDir,
            final String outputName,
            final Settings settings)
            throws SettingsException, IOException {

        validateSettings(settings);

        final BufferedImage inputImage = ImageIO.read(new File(inputPath));
        final BufferedImage outputImage = processTransform(inputImage, settings);

        ImageIO.write(outputImage, settings.getFormat().getFileExtension(),
                new File(outputDir + "/" + outputName + "." + settings.getFormat().getFileExtension()));
    }

    /**
     * Transforms the input image acording to the configuration defined in
     * {@link Settings} and saves its transformed version in the local file
     * system.
     *
     * @param inputImage input image instance
     * @param outputDir output directory path (without separator at the end)
     * @param outputName output image file name (without extension)
     * @param settings image transformations configuration
     * @throws SettingsException in case of invalid settings
     * @throws IOException in case of an I/O error
     */
    public static void transformAndWrite(
            final BufferedImage inputImage,
            final String outputDir,
            final String outputName,
            final Settings settings)
            throws SettingsException, IOException {

        validateSettings(settings);

        final BufferedImage outputImage = processTransform(inputImage, settings);

        ImageIO.write(outputImage, settings.getFormat().getFileExtension(),
                new File(outputDir + "/" + outputName + "." + settings.getFormat().getFileExtension()));
    }

    /**
     * Transforms the input image acording to the configuration defined in
     * {@link Settings} and returns its transformed version.
     *
     * @param inputImage input image instance
     * @param settings image transformations configuration
     * @return transformed version of the input image as {@link BufferedImage}
     * @throws SettingsException in case of invalid settings
     */
    public static BufferedImage transform(
            final BufferedImage inputImage,
            final Settings settings) throws SettingsException {

        validateSettings(settings);

        return processTransform(inputImage, settings);
    }

    /**
     * Transforms the input image acording to the configuration defined in
     * {@link Settings} and returns its transformed version.
     *
     * @param bytes image data in byte array
     * @param settings image transformations configuration
     * @return transformed version of the input image as byte array
     * @throws SettingsException in case of invalid settings
     * @throws IOException in case of an I/O error
     */
    public static byte[] transform(
            final byte[] bytes,
            final Settings settings) throws SettingsException, IOException {

        validateSettings(settings);

        byte[] outputBytes = null;
        try(
                final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                final ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {

            ImageIO.write(processTransform(ImageIO.read(bais), settings), settings.getFormat().getFileExtension(), baos);
            baos.flush();
            outputBytes = baos.toByteArray();

        }
        catch(final IOException e) {
            throw e;
        }

        return outputBytes;
    }

    // Support methods ============================================================================

    /**
     * Executes image processing implementation depending on specified
     * processing strategy.
     *
     * @param inputImage original image instance
     * @param settings transformation configurations
     * @return transformed image
     * @throws SettingsException in case of invalid settings
     */
    private static BufferedImage processTransform(
            final BufferedImage inputImage,
            final Settings settings) throws SettingsException {

        OImage oImage;

        final boolean isRatioBased = settings.getImagesize().getHeight() == null ^ settings.getImagesize().getWidth() == null;

        switch(settings.getStrategy()) {
            case FIT:
                oImage = new FitImage(inputImage, settings);
                if(isRatioBased) {
                    oImage.processByRatio();
                }
                else {
                    oImage.processByDimmensions();
                }
                break;
            case ADJUST:
                oImage = new AdjustedImage(inputImage, settings);
                if(isRatioBased) {
                    oImage.processByRatio();
                }
                else {
                    oImage.processByDimmensions();
                }
                break;
            case EXPAND:
                oImage = new ExpandedImage(inputImage, settings);
                if(isRatioBased) {
                    oImage.processByRatio();
                }
                else {
                    oImage.processByDimmensions();
                }
                break;
            case TILE:
                oImage = new TiledImage(inputImage, settings);
                if(isRatioBased) {
                    oImage.processByRatio();
                }
                else {
                    oImage.processByDimmensions();
                }
                break;
            case LIMIT:
                oImage = new LimitedImage(inputImage, settings);
                oImage.processByDimmensions();
                break;
            default:
                oImage = new ExpandedImage(inputImage, settings);
                if(isRatioBased) {
                    oImage.processByRatio();
                }
                else {
                    oImage.processByDimmensions();
                }
                break;
        }

        return oImage.getOutputImage();
    }

    /**
     * Validates basic {@link Settings} configuration consistency.
     *
     * @param settings transformation configurations
     * @throws SettingsException in case of invalid settings
     */
    private static void validateSettings(final Settings settings) throws SettingsException {

        if(settings.getStrategy() == null) {
            throw new SettingsException("Bad output configuration: image processing strategy is not set", new Throwable());
        }
    }
}
