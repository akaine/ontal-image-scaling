package org.ontal.imgutil.settings;

/**
 * Image transformation settings container. Contains all the settings required
 * to perform image transformation.
 *
 * @author akaine
 * @since May 2012
 */
public class Settings {

    // Output properties ==========================================================================

    /**
     * Main dimension absolute size in pixels.
     */
    private ImageSize imagesize;

    /**
     * Image ratio.
     */
    private ImageRatio.Ratio ratio;

    /**
     * Image orientation.
     */
    private int orientation;

    /**
     * Target format.
     */
    private ImageFormat format;

    /**
     * Image processing strategy.
     */
    private Strategy strategy;

    /**
     * Excess area background color for {@link Strategy#ADJUST} transformation
     * type. Default value is {0,0,0} - black.
     */
    private int[] backgroundRGB = { 0, 0, 0 };

    // Constructors ===============================================================================

    /**
     * Default constructor.
     */
    public Settings() {
    }

    /**
     * Partial constructor. Designed to quickly configure desired output
     * parameters based on tagret image long side length and ratio.
     *
     * @param imagesize long side length in pixels
     * @param ratio image ratio
     * @param format target format
     * @param strategy image processing strategy
     * @param backgroundRGB excess area background color
     */
    public Settings(final ImageSize imagesize, final ImageRatio.Ratio ratio, final ImageFormat format, final Strategy strategy, final int[] backgroundRGB) {
        this.imagesize = imagesize;
        this.ratio = ratio;
        this.format = format;
        this.strategy = strategy;
        this.backgroundRGB = backgroundRGB;
    }

    /**
     * Partial constructor. Designed to quickly configure desired output
     * parameters based on tagret image long side length and ratio.
     *
     * @param imagesize long side length in pixels
     * @param ratio image ratio
     * @param format target format
     * @param strategy image processing strategy
     */
    public Settings(final ImageSize imagesize, final ImageRatio.Ratio ratio, final ImageFormat format, final Strategy strategy) {
        this.imagesize = imagesize;
        this.ratio = ratio;
        this.format = format;
        this.strategy = strategy;
    }

    /**
     * Partial constructor. Designed to quickly configure desired output
     * parameters based on target image dimesions.
     *
     * @param imagesize image dimensions in pixels
     * @param format target format
     * @param strategy image processing strategy
     * @param backgroundRGB excess area background color
     */
    public Settings(final ImageSize imagesize, final ImageFormat format, final Strategy strategy, final int[] backgroundRGB) {
        this.imagesize = imagesize;
        this.format = format;
        this.strategy = strategy;
        this.backgroundRGB = backgroundRGB;
    }

    /**
     * Partial constructor. Designed to quickly configure desired output
     * parameters based on target image dimesions.
     *
     * @param imagesize image dimensions in pixels
     * @param format target format
     * @param strategy image processing strategy
     */
    public Settings(final ImageSize imagesize, final ImageFormat format, final Strategy strategy) {
        this.imagesize = imagesize;
        this.format = format;
        this.strategy = strategy;
    }

    public ImageSize getImagesize() {
        return imagesize;
    }

    public void setImagesize(final ImageSize imagesize) {
        this.imagesize = imagesize;
    }

    public ImageRatio.Ratio getRatio() {
        return ratio;
    }

    public void setRatio(final ImageRatio.Ratio ratio) {
        this.ratio = ratio;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(final int orientation) {
        this.orientation = orientation;
    }

    public ImageFormat getFormat() {
        return format;
    }

    public void setFormat(final ImageFormat format) {
        this.format = format;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(final Strategy strategy) {
        this.strategy = strategy;
    }

    public int[] getBackgroundRGB() {
        return backgroundRGB;
    }

    public void setBackgroundRGB(final int[] backgroundRGB) {
        this.backgroundRGB = backgroundRGB;
    }
}
