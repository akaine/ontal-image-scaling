package org.ontal.imgutil.settings;

/**
 * Contains list of options specifying image processing strategy used by
 * {@link Settings}.
 *
 * @author akaine
 * @since May 2012
 */
public enum Strategy {

    /**
     * In case target ratio is different from the original scales, centers and
     * then crops the image.
     */
    FIT,

    /**
     * In case target ratio is different from the original scales the image
     * adding borders. Borders background color is configured separately via
     * {@link Settings#setBackgroundRGB(int[])}.
     */
    ADJUST,

    /**
     * Scales image to meet output dimesions.
     */
    EXPAND,

    /**
     * Tiles image.
     */
    TILE,

    /**
     * Scales image to fit in Y or X limit defined in pixels. If the image is
     * smaller than the limit no changes applied.
     */
    LIMIT;
}
