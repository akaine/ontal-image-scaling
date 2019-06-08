package org.ontal.imgutil.settings;

/**
 * Image dimensions configuration container class used by {@link Settings}.
 * Along with normal fields and constructors contains a list of predefined
 * constants of widths and heights for ratio dependent transformations.
 *
 * @author akaine
 * @since May 2012
 */
public final class ImageSize {

    // Standard widths ============================================================================

    /**
     * Predefined value equivalent to 50px width.
     */
    public final static ImageSize X0050 = new ImageSize(50, null);

    /**
     * Predefined value equivalent to 60px width.
     */
    public final static ImageSize X0060 = new ImageSize(60, null);

    /**
     * Predefined value equivalent to 80px width.
     */
    public final static ImageSize X0080 = new ImageSize(80, null);

    /**
     * Predefined value equivalent to 90px width.
     */
    public final static ImageSize X0090 = new ImageSize(90, null);

    /**
     * Predefined value equivalent to 100px width.
     */
    public final static ImageSize X0100 = new ImageSize(100, null);

    /**
     * Predefined value equivalent to 120px width.
     */
    public final static ImageSize X0120 = new ImageSize(120, null);

    /**
     * Predefined value equivalent to 240px width.
     */
    public final static ImageSize X0240 = new ImageSize(240, null);

    /**
     * Predefined value equivalent to 320px width.
     */
    public final static ImageSize X0320 = new ImageSize(320, null);

    /**
     * Predefined value equivalent to 400px width.
     */
    public final static ImageSize X0400 = new ImageSize(400, null);

    /**
     * Predefined value equivalent to 480px width.
     */
    public final static ImageSize X0480 = new ImageSize(480, null);

    /**
     * Predefined value equivalent to 600px width.
     */
    public final static ImageSize X0600 = new ImageSize(600, null);

    /**
     * Predefined value equivalent to 640px width.
     */
    public final static ImageSize X0640 = new ImageSize(640, null);

    /**
     * Predefined value equivalent to 720px width.
     */
    public final static ImageSize X0720 = new ImageSize(720, null);

    /**
     * Predefined value equivalent to 768px width.
     */
    public final static ImageSize X0768 = new ImageSize(768, null);

    /**
     * Predefined value equivalent to 800px width.
     */
    public final static ImageSize X0800 = new ImageSize(800, null);

    /**
     * Predefined value equivalent to 900px width.
     */
    public final static ImageSize X0900 = new ImageSize(900, null);

    /**
     * Predefined value equivalent to 960px width.
     */
    public final static ImageSize X0960 = new ImageSize(960, null);

    /**
     * Predefined value equivalent to 1024px width.
     */
    public final static ImageSize X1024 = new ImageSize(1024, null);

    /**
     * Predefined value equivalent to 1080px width.
     */
    public final static ImageSize X1080 = new ImageSize(1080, null);

    /**
     * Predefined value equivalent to 1200px width.
     */
    public final static ImageSize X1200 = new ImageSize(1200, null);

    /**
     * Predefined value equivalent to 1280px width.
     */
    public final static ImageSize X1280 = new ImageSize(1280, null);

    /**
     * Predefined value equivalent to 1440px width.
     */
    public final static ImageSize X1440 = new ImageSize(1440, null);

    /**
     * Predefined value equivalent to 1920px width.
     */
    public final static ImageSize X1920 = new ImageSize(1920, null);

    // Standard heights ===========================================================================

    /**
     * Predefined value equivalent to 50px height.
     */
    public final static ImageSize Y0050 = new ImageSize(null, 50);

    /**
     * Predefined value equivalent to 60px height.
     */
    public final static ImageSize Y0060 = new ImageSize(null, 60);

    /**
     * Predefined value equivalent to 80px height.
     */
    public final static ImageSize Y0080 = new ImageSize(null, 80);

    /**
     * Predefined value equivalent to 90px height.
     */
    public final static ImageSize Y0090 = new ImageSize(null, 90);

    /**
     * Predefined value equivalent to 100px height.
     */
    public final static ImageSize Y0100 = new ImageSize(null, 100);

    /**
     * Predefined value equivalent to 120px height.
     */
    public final static ImageSize Y0120 = new ImageSize(null, 120);

    /**
     * Predefined value equivalent to 60px height.
     */
    public final static ImageSize Y0240 = new ImageSize(null, 240);

    /**
     * Predefined value equivalent to 320px height.
     */
    public final static ImageSize Y0320 = new ImageSize(null, 320);

    /**
     * Predefined value equivalent to 400px height.
     */
    public final static ImageSize Y0400 = new ImageSize(null, 400);

    /**
     * Predefined value equivalent to 480px height.
     */
    public final static ImageSize Y0480 = new ImageSize(null, 480);

    /**
     * Predefined value equivalent to 600px height.
     */
    public final static ImageSize Y0600 = new ImageSize(null, 600);

    /**
     * Predefined value equivalent to 720px height.
     */
    public final static ImageSize Y0720 = new ImageSize(null, 720);

    /**
     * Predefined value equivalent to 768px height.
     */
    public final static ImageSize Y0768 = new ImageSize(null, 768);

    /**
     * Predefined value equivalent to 800px height.
     */
    public final static ImageSize Y0800 = new ImageSize(null, 800);

    /**
     * Predefined value equivalent to 768px height.
     */
    public final static ImageSize Y0900 = new ImageSize(null, 900);

    /**
     * Predefined value equivalent to 768px height.
     */
    public final static ImageSize Y0960 = new ImageSize(null, 960);

    /**
     * Predefined value equivalent to 1024px height.
     */
    public final static ImageSize Y1024 = new ImageSize(null, 1024);

    /**
     * Predefined value equivalent to 1080px height.
     */
    public final static ImageSize Y1080 = new ImageSize(null, 1080);

    /**
     * Predefined value equivalent to 1200px height.
     */
    public final static ImageSize Y1200 = new ImageSize(null, 1200);

    /**
     * Predefined value equivalent to 1280px height.
     */
    public final static ImageSize Y1280 = new ImageSize(null, 1280);

    /**
     * Predefined value equivalent to 1440px height.
     */
    public final static ImageSize Y1440 = new ImageSize(null, 1440);

    /**
     * Predefined value equivalent to 1920px height.
     */
    public final static ImageSize Y1920 = new ImageSize(null, 1920);

    // Class fields ===============================================================================

    /**
     * Width in pixels.
     */
    private Integer width;

    /**
     * Height in pixels.
     */
    private Integer height;

    // Constructors ===============================================================================

    /**
     * Constructor that allows to define image dimensions.
     *
     * @param width image width in pixels
     * @param height image height in pixels
     */
    public ImageSize(final Integer width, final Integer height) {
        this.width = width;
        this.height = height;
    }

    // Accessors ==================================================================================

    /**
     * {@link #width} property getter.
     *
     * @return width property value
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * {@link #width} property setter.
     *
     * @param width property value
     */
    public void setWidth(final Integer width) {
        this.width = width;
    }

    /**
     * {@link #height} property getter.
     *
     * @return height property value
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * {@link #height} property setter.
     *
     * @param height property value
     */
    public void setHeight(final Integer height) {
        this.height = height;
    }
}
