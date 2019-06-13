# OntalSoft Quick Image Scaling Utility

This utility offers quick image scaling transformations based on initial parameters and selected via method transformation type.

There are several scaling transofmations available: **Fit**, **Expand**, **Adjust**, **Limit** and **Tile**. 

After the transformation is done, user can call any of the available for convience methods to obtain/save the resulting image.

Usage examples:
```java
// get scaled BufferedImage
final BufferedImage scaledImage = 
        new ImageScaler(Paths.get("/tmp/originalImage.jpg"))
                .adjust(200, 200)
                .getImage();

// save scaled image into local file system
new ImageScaler(Paths.get("/tmp/originalImage.jpg"))
        .fit(200, 200, new int[] { 192, 205, 224 })
        .saveAs(ImageFormat.JPG, Paths.get("/tmp"), "scaledImage");

// get scaled image bytes
final byte[] scaledImageBytes = 
        new ImageScaler(Paths.get("/tmp/originalImage.jpg"))
                .fit(200, 200)
                .toByteArray(ImageFormat.PNG);
```

## Fit

Scales image fitting and centering it onto the output canvas with its original ratio conserved leaving unocupied canvas space filled with the specified background color (white by default)). This can be particularly useful when the user needs to standardize the output dimmensions and at the same time maintain the original image uncut.

Original | Fit example 1 | Fit example 2
--- | --- | ---
| ![Original](docs/original.jpg?raw=true "Original") | ![Fit1](docs/fit1.jpg?raw=true "Fit1") | ![Fit2](docs/fit2.jpg?raw=true "Fit2")

## Expand

Scales image to the target dimensions. When target ratio is different from the original one an image distortion may occur. Useful when the user just needs to scale the image without maintaining its ratio.

Original | Exapnd example 
--- | --- 
| ![Original](docs/original.jpg?raw=true "Original") | ![Exapnd](docs/expand.jpg?raw=true "Exapnd") 

## Adjust

Scales image to the max target dimension while maintaining its original ratio. In case output ratio is different from the original one the image is centered and cropped. Useful when the user needs to standardize the output dimmensions without having monotone borders.

Original | Adjust example 
--- | --- 
| ![Original](docs/original.jpg?raw=true "Original") | ![Adjust](docs/adjust.jpg?raw=true "Adjust") 

## Limit

Scales image fitting it within the limit defined in pixels along the specified dimension. Useful for setting a dimmensional limit for images without loosing their ratio.

Original | Limit example 
--- | --- 
| ![Original](docs/original.jpg?raw=true "Original") | ![Limit](docs/limit.jpg?raw=true "Limit") 

## Tile

Uses input image as a tile filling the output image with the original image copies as they fit. Isn't particularly useful but what the hell =).

Original | Tile example 
--- | --- 
| ![Original](docs/original.jpg?raw=true "Original") | ![Tile](docs/tile.jpg?raw=true "Tile") 

## Binaries
If you're feeling lazy and just want to grab the latest JARs, they are here:
- [ontal-image-scaling-2.0.1.jar](https://raw.githubusercontent.com/akaine/ontal-image-scaling/master/bin/ontal-image-scaling-2.0.1.jar)
- [ontal-image-scaling-2.0.1-javadoc.jar](https://raw.githubusercontent.com/akaine/ontal-image-scaling/master/bin/ontal-image-scaling-2.0.1-javadoc.jar)

## API Docs
Project api docs are available here: [API reference](https://akaine.github.io/ontal-image-scaling/apidocs/org/ontal/imgutil/ImageScaler.html)
