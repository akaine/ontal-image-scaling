package org.ontal.imgutil;

import java.awt.image.BufferedImage;

import org.ontal.imgutil.exceptions.SettingsException;
import org.ontal.imgutil.settings.Settings;
import org.ontal.imgutil.settings.Strategy;

/**
 * A media to access different transformation strategy implementations
 * specified by {@link Strategy} option. All its implementations saves a local
 * copy of the input and output {@link BufferedImage}. After executing any of
 * the processing methos a user should call {@link #getOutputImage()} method
 * to recover the transformed copy.
 *
 * @author akaine
 * @since May 2012
 */
public interface OImage {

    /**
     * Processes input image by height or width size and ratio specified in
     * {@link Settings}. Only one - height o width is required, if both are
     * set only width is taken in consideration.
     *
     * @throws SettingsException in case of invalid settings
     */
    void processByRatio() throws SettingsException;

    /**
     * Processes input image by both height and width specified in
     * {@link Settings}.
     *
     * @throws SettingsException in case of invalid settings
     */
    void processByDimmensions() throws SettingsException;

    /**
     * Recovers the transformed image instance.
     *
     * @return transformed image
     */
    BufferedImage getOutputImage();
}
