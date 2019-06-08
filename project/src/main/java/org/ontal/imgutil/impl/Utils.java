package org.ontal.imgutil.impl;

import org.ontal.imgutil.settings.Settings;

/**
 * Local utility class for internal calculus support.
 *
 * @author akaine
 * @since May 2012
 */
class Utils {

    /**
     * Calculates target target width and height values by specified width or
     * height and ratio.
     *
     * @param settings configuration applied to the transformation and processing implementation.
     * @return target width and height values
     */
    public static int[] calcTargetDims(final Settings settings) {
        int targetWidth, targetHeight;

        if(settings.getImagesize().getWidth() == null) {
            targetHeight = settings.getImagesize().getHeight();
            targetWidth = targetHeight * settings.getRatio().getX() / settings.getRatio().getY();
        }
        else {
            targetWidth = settings.getImagesize().getWidth();
            targetHeight = targetWidth * settings.getRatio().getY() / settings.getRatio().getX();
        }

        return new int[] { targetWidth, targetHeight };
    }
}
