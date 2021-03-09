package dankminer.dankminer.utils;

import dankminer.dankminer.DankMiner;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Class providing Utility to draw Images on Maps
 */
public class ImageUtils {

    /**
     * resize the image
     * Reference: zDevelopers
     * @param source the source {@link Image}
     * @param destinationW the target width
     * @param destinationH the target height
     * @return a scaled {@link BufferedImage}
     */
    public static BufferedImage resize(BufferedImage source, int destinationW, int destinationH) {
        float ratioW = (float) destinationW / (float) source.getWidth();
        float ratioH = (float) destinationH / (float) source.getHeight();
        int finalW;
        int finalH;
        int x;
        int y;

        if (ratioW > ratioH) {
            finalW = destinationW;
            finalH = (int) (source.getHeight() * ratioW);
        } else {
            finalW = (int) (source.getWidth() * ratioH);
            finalH = destinationH;
        }

        x = (destinationW - finalW) / 2;
        y = (destinationH - finalH) / 2;

        return drawImage(source, destinationW, destinationH, x, y, finalW, finalH);
    }

    /**
     * Draws the source image on a new buffer, and returns it.
     * The source buffer can be drawn at any size and position in the new buffer.
     * Reference: zDevelopers
     */
    private static BufferedImage drawImage(BufferedImage source, int bufferW, int bufferH, int posX, int posY, int sourceW, int sourceH) {
        Graphics graphics;
        BufferedImage newImage = null;
        try {
            newImage = new BufferedImage(bufferW, bufferH, BufferedImage.TYPE_INT_ARGB);

            graphics = newImage.getGraphics();
            graphics.drawImage(source, posX, posY, sourceW, sourceH, null);

            return newImage;
        } catch (final Throwable e) {
            DankMiner.serverLog("Error at drawing Image");
            System.err.println("Error at drawing Image");
            if (newImage != null) {
                newImage.flush();//Safe to free
            }
            throw e;
        }

    }

}
