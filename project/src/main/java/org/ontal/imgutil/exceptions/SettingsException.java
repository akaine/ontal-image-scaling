package org.ontal.imgutil.exceptions;

import org.ontal.imgutil.settings.Settings;

/**
 * This exception may be thrown by image processing methods that have detected
 * inconsistencies in transformation settings.
 *
 * @see Settings
 *
 * @author akaine
 * @since May 2012
 */
public class SettingsException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param msg detail message
     * @param ex cause
     */
    public SettingsException(final String msg, final Throwable ex) {
        super(msg, ex);
    }
}
