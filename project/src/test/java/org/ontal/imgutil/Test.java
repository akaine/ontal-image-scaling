package org.ontal.imgutil;

import java.io.IOException;
import java.nio.file.Paths;

import org.ontal.imgutil.scaling.ImageScaler;

public class Test {

    private static final String basePath = "E:/opt";
    private static final String inputFile = "original.jpg";

    public static void main(final String[] args) throws IOException {
        new ImageScaler(Paths.get(basePath, inputFile))
                .fit(200, 200, new int[] { 192, 205, 224 })
                .saveAs(ImageFormat.JPG, Paths.get(basePath), "fit1");
        new ImageScaler(Paths.get(basePath, inputFile))
                .fit(250, 150, new int[] { 192, 205, 224 })
                .saveAs(ImageFormat.JPG, Paths.get(basePath), "fit2");
        new ImageScaler(Paths.get(basePath, inputFile))
                .expand(200, 200)
                .saveAs(ImageFormat.JPG, Paths.get(basePath), "expand");
        new ImageScaler(Paths.get(basePath, inputFile))
                .adjust(200, 200)
                .saveAs(ImageFormat.JPG, Paths.get(basePath), "adjust");
        new ImageScaler(Paths.get(basePath, inputFile))
                .limit(Dimension.WIDTH, 200)
                .saveAs(ImageFormat.JPG, Paths.get(basePath), "limit");
        new ImageScaler(Paths.get(basePath, inputFile))
                .tile(350, 250)
                .saveAs(ImageFormat.JPG, Paths.get(basePath), "tile");

    }

}
