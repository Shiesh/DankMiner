package dankminer.dankminer;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import java.awt.*;

public class MemeRenderer extends MapRenderer {

    private Image meme;
    private boolean possible;

    public MemeRenderer(Image image) {
        super();
        changeMeme(image);
    }

    /**
     * changes the displayed image
     * @param image the new {@link Image} to be rendered
     */
    public void changeMeme(Image image){
        this.meme = image;
        //checks if the image is null and within the size of 128x128
        possible = !(meme == null || meme.getWidth(null) > 128 || meme.getHeight(null) > 128);
    }

    /**
     * Overrides the render method of {@link MapRenderer}
     * @param map
     * @param canvas
     * @param player
     */
    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
        try {
            if(possible){
                canvas.drawImage(0,0, meme);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            DankMiner.serverLog("§cmaybe MemeLoader isn't set up properly!");
        }
    }
}
