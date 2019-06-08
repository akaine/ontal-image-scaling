package org.ontal.imgutil.settings;

/**
 * Image aspect ratio configuration container class used by {@link Settings}.
 * Along with normal fields and constructors contains a list of predefined
 * constants of horizontal and vertical oriented ratios for ratio dependent
 * transformations.
 *
 * @author akaine
 * @since May 2012
 */
public class ImageRatio {

    // Horizontal ratios ==========================================================================

    /**
     * Static instance of 1:1 ratio.
     */
    public final static Ratio X1Y1 = new Ratio(1, 1);

    /**
     * Static instance of 3:2 ratio.
     */
    public final static Ratio X3Y2 = new Ratio(3, 2);

    /**
     * Static instance of 4:3 ratio.
     */
    public final static Ratio X4Y3 = new Ratio(4, 3);

    /**
     * Static instance of 5:3 ratio.
     */
    public final static Ratio X5Y3 = new Ratio(5, 3);

    /**
     * Static instance of 5:4 ratio.
     */
    public final static Ratio X5Y4 = new Ratio(5, 4);

    /**
     * Static instance of 16:9 ratio.
     */
    public final static Ratio X16Y9 = new Ratio(16, 9);

    /**
     * Static instance of 16:10 (8:5) ratio.
     */
    public final static Ratio X16Y10 = new Ratio(8, 5);

    // Vertical ratios ==========================================================================

    /**
     * Static instance of 3:2 ratio.
     */
    public final static Ratio X2Y3 = new Ratio(2, 3);

    /**
     * Static instance of 4:3 ratio.
     */
    public final static Ratio X3Y4 = new Ratio(3, 4);

    /**
     * Static instance of 5:3 ratio.
     */
    public final static Ratio X3Y5 = new Ratio(3, 5);

    /**
     * Static instance of 5:4 ratio.
     */
    public final static Ratio X4Y5 = new Ratio(4, 5);

    /**
     * Static instance of 16:9 ratio.
     */
    public final static Ratio X9Y16 = new Ratio(9, 16);

    /**
     * Static instance of 16:10 (8:5) ratio.
     */
    public final static Ratio X10Y16 = new Ratio(10, 16);

    /**
     * Static instance of auto ratio (special).
     */
    public final static Ratio AUTO = new Ratio(0, 1);

    private ImageRatio() {
    }

    // Ratio ======================================================================================

    /**
     * Ratio attributes container class.
     *
     * @author User
     * @since May 2012
     */
    public static class Ratio {

        private final int x;

        private final int y;

        private final float coefficient;

        /**
         * Constructor that allows to define a custom ratio.
         *
         * @param x image width in pixels
         * @param y image height in pixels
         */
        public Ratio(final int x, final int y) {
            this.x = x;
            this.y = y;
            this.coefficient = x / y;
        }

        /**
         * {@link #coefficient} property getter.
         *
         * @return coefficient property value
         */
        public float getCoefficient() {
            return coefficient;
        }

        /**
         * {@link #x} property getter.
         *
         * @return x property value
         */
        public int getX() {
            return x;
        }

        /**
         * {@link #y} property getter.
         *
         * @return y property value
         */
        public int getY() {
            return y;
        }
    }
}
