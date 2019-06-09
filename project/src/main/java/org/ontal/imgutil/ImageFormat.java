package org.ontal.imgutil;

/**
 * Bitmap formats.
 *
 * @author akaine
 * @since May 2012
 */
public enum ImageFormat {

    /**
     * PNG: Portable Network Graphic
     */
    PNG("png"),

    /**
     * JPG: JPEG Image
     */
    JPG("jpg"),

    /**
     * JPG: JPEG Image (alternative)
     */
    JPEG("jpeg"),

    /**
     * BMP: Bitmap Image File
     */
    BMP("bmp"),

    /**
     * GIF: Graphical Interchange Format File
     */
    GIF("gif");

    private String fileExtension;

    private ImageFormat(final String fileExtension) {
        this.fileExtension = fileExtension;
    }

    /**
     * Looks for the ImageFormat by its file extension.
     *
     * @param fileExtension file extension
     * @return ImageFormat enum object
     */
    public static final ImageFormat getByExtension(final String fileExtension) {
        ImageFormat obj = null;

        for(final ImageFormat o : ImageFormat.values()) {
            if(o.fileExtension.equalsIgnoreCase(fileExtension.trim())) {
                obj = o;
            }
        }

        return obj;
    }

    /**
     * Returns file extension.
     *
     * @return file extension
     */
    public final String getFileExtension() {
        return this.fileExtension;
    }

    /**
     * Returns format name.
     *
     * @return format name
     */
    public final String getName() {
        return this.fileExtension;
    }
}
