package org.ontal.imgutil.scaling;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.ontal.imgutil.Dimension;
import org.ontal.imgutil.ImageFormat;

/**
 * This utility offers quick image scaling transformations based on initial
 * parameters and selected via method transformation type.
 * <br><br>
 * There are several scaling transofmations available: Fit, Expand, Adjust,
 * Expand, Limit and Tile.
 * <br><br>
 * After the transformation is done, user can call any of the available for
 * convience methods to obtain/save the resulting image.
 * <br><br>
 * Usage example:
 * <pre>
 * // get scaled BufferedImage
 * new ImageScaler(Paths.get("/tmp/originalImage.jpg"))
 *         .adjust(200, 200)
 *         .getImage();
 *
 * // save scaled image into local file system
 * new ImageScaler(Paths.get("/tmp/originalImage.jpg"))
 *         .fit(200, 200, new int[] { 192, 205, 224 })
 *         .saveAs(ImageFormat.JPG, Paths.get("/tmp"), "scaledImage");
 * </pre>
 *
 * @author akaine
 * @since May 2012
 */
public class ImageScaler {

    private final static Logger log = Logger.getLogger(ImageScaler.class.getName());

    private final BufferedImage inputImage;
    private BufferedImage outputImage;

    /**
     * Creates image scaler instance reading the input image bytes.
     *
     * @param imageBytes image bytes
     * @throws IOException if an error occurs while reading
     */
    public ImageScaler(final byte[] imageBytes) throws IOException {
        try(final ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes)) {
            inputImage = ImageIO.read(bais);
        }
        catch(final IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Creates image scaler instance reading the input image from the provided
     * input stream.
     *
     * @param imageInputStream image input stream
     * @throws IOException if an error occurs while reading
     */
    public ImageScaler(final InputStream imageInputStream) throws IOException {
        try(final InputStream is = imageInputStream) {
            inputImage = ImageIO.read(is);
        }
        catch(final IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Creates image scaler instance reading the input image from the provided
     * file path.
     *
     * @param imagePath image file path
     * @throws IOException if an error occurs while reading
     */
    public ImageScaler(final Path imagePath) throws IOException {
        inputImage = ImageIO.read(imagePath.toAbsolutePath().toFile());
    }

    public ImageScaler(final BufferedImage image) {
        inputImage = image;
    }

    /**
     * Scales image fitting and centering it onto the output canvas with its
     * original ratio conserved leaving unocupied canvas space filled with
     * the default background color (white). This can be particularly useful
     * when the user needs to standardize the output dimmensions and at the
     * same time maintain the original image uncut.
     *
     * @param width output width in pixels
     * @param height output height in pixels
     * @return scaled image
     */
    public ImageScaler fit(final int width, final int height) {
        return fit(width, height, null);
    }

    /**
     * Scales image fitting and centering it onto the output canvas with its
     * original ratio conserved leaving unocupied canvas space filled with
     * the background color specified via the <code>rgb</code> argument. This
     * can be particularly useful when the user needs to standardize the output
     * dimmensions and at the same time maintain the original image uncut.
     *
     * @param width output width in pixels
     * @param height output height in pixels
     * @param rgb excess area background color
     * @return scaled image
     */
    public ImageScaler fit(final int width, final int height, final int[] rgb) {
        final TransformImage transformer = new FitImage(inputImage);
        outputImage = transformer.transform(width, height, rgb);
        return this;
    }

    /**
     * Scales image to the target dimensions. When target ratio is different
     * from the original one an image distortion may occur. Useful when the
     * user just needs to scale the image without maintaining its ratio.
     *
     * @param width output width in pixels
     * @param height output height in pixels
     * @return scaled image
     */
    public ImageScaler expand(final int width, final int height) {
        final TransformImage transformer = new ExpandImage(inputImage);
        outputImage = transformer.transform(width, height);
        return this;
    }

    /**
     * Scales image to the max target dimension while maintaining its original
     * ratio. In case output ratio is different from the original one the image
     * is centered and cropped. Useful when the user needs to standardize the
     * output dimmensions without having monotone borders.
     *
     * @param width output width in pixels
     * @param height output height in pixels
     * @return scaled image
     */
    public ImageScaler adjust(final int width, final int height) {
        final TransformImage transformer = new AdjustImage(inputImage);
        outputImage = transformer.transform(width, height);
        return this;
    }

    /**
     * Scales image fitting it within the limit defined in pixels along the
     * specified dimension. Useful for setting a dimmensional limit for images
     * without loosing their ratio.
     *
     * @param dimension limit dimension
     * @param size dimension size in pixels
     * @return scaled image
     */
    public ImageScaler limit(final Dimension dimension, final int size) {
        final TransformImage transformer = new LimitImage(inputImage);
        outputImage = transformer.transform(dimension, size);
        return this;
    }

    /**
     * Uses input image as a tile filling the output image with the original
     * image copies as they fit. Isn't particularly useful but what the
     * hell =).
     *
     * @param width output width in pixels
     * @param height output height in pixels
     * @return tiled image
     */
    public ImageScaler tile(final int width, final int height) {
        final TransformImage transformer = new TileImage(inputImage);
        outputImage = transformer.transform(width, height);
        return this;
    }

    /**
     * Converts the produced image into a byte array.
     *
     * @param imageFormat image format
     * @return image bytes
     * @throws IOException if an error occurs while writing
     */
    public byte[] toByteArray(final ImageFormat imageFormat) throws IOException {
        try(final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(outputImage, imageFormat.getName(), baos);
            baos.flush();
            return baos.toByteArray();
        }
        catch(final IOException e) {
            throw e;
        }
    }

    /**
     * Saves the prodiced image in local file system using the provided output
     * directory path and name.
     *
     * @param imageFormat image format
     * @param outputDirectoryPath output directory path
     * @param name image file name without extension
     * @throws IOException if an error occurs while writing
     */
    public void saveAs(final ImageFormat imageFormat, final Path outputDirectoryPath, final String name) throws IOException {
        ImageIO.write(outputImage, imageFormat.getFileExtension(),
                Paths.get(outputDirectoryPath.toAbsolutePath().toString(), name + "." + imageFormat.getFileExtension()).toFile());
    }

    /**
     * Obtains the produced image.
     *
     * @return image
     */
    public BufferedImage getImage() {
        return outputImage;
    }
}
