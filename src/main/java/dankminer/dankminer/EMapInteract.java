package dankminer.dankminer;

import dankminer.dankminer.utils.ImageUtils;
import dankminer.dankminer.utils.MemeLoader;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * Listens on interaction by a player with a map
 */
public class EMapInteract implements Listener {

    private Map<UUID, Long> cooldowns = new HashMap<>();

    //cooldown in seconds (RECOMMENDED AT 2!!!!)
    private int cooldown = 2;

    public EMapInteract(int cooldown) {

        try {
            this.cooldown = cooldown;
        }catch (Exception e){
            this.cooldown = 2;
        }

        if(MemeLoader.enable()){
            DankMiner.serverLog("§aSuccessfully enabled MemeLoader!");
        }
        else{
            DankMiner.serverLog("§cFailed to enable MemeLoader!");
        }

    }

    @EventHandler
    public void onMapInteract(PlayerInteractEvent piE){

        if(  piE.getAction() == Action.RIGHT_CLICK_AIR &&
             piE.getItem().getItemMeta().hasDisplayName() &&
             (piE.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("MEME") || piE.getItem().getItemMeta().getDisplayName().equals("§cM§aE§6M§9E"))){

            Player player = piE.getPlayer();

            if(!MemeLoader.isEnabled()) return;

            if(cooldowns.containsKey(player.getUniqueId())){
                if(cooldowns.get(player.getUniqueId()) > System.currentTimeMillis()){
                    player.sendMessage("§cYou have to wait some time to use this again");
                    return;
                }
            }
            cooldowns.put(player.getUniqueId(), System.currentTimeMillis() + (cooldown * 1000));

            //get random Post
            Post post = MemeLoader.getRandomPost();

            //load Image
            Image meme = MemeLoader.fetchImage(post.getMemeURL());
            if(meme == null) {
                //display Standby Image if failed to load Image from the web
                meme = MemeLoader.fetchImage(getClass().getResource("/STANDBY.png"));
                return;
            }
            //resize Image to 128x128 (Map-resolution)
            meme = ImageUtils.resize((BufferedImage) meme, 128, 128);

            //If map is empty
            if(piE.getMaterial() == Material.MAP){

                //create new FILLED_MAP item
                ItemStack map = new ItemStack(Material.FILLED_MAP, 1);

                //change Map Renderer and Display name
                MapMeta meta = (MapMeta) map.getItemMeta();
                meta.setDisplayName("§cM§aE§6M§9E");
                MapView view = Bukkit.createMap(piE.getPlayer().getWorld());
                for(MapRenderer renderer : view.getRenderers()){
                    view.removeRenderer(renderer);
                }
                view.addRenderer(new MemeRenderer(meme));
                meta.setMapView(view);
                List<String> lore = new ArrayList<>();
                lore.add("§bLeft click in air");
                lore.add("§bto get new random meme");
                meta.setLore(lore);
                map.setItemMeta(meta);

                //replace current item with new map
                player.getInventory().setItemInMainHand(map);
            }

            //if Map is filled
            if(piE.getMaterial() == Material.FILLED_MAP ){

                //get Item and MemeRenderer
                ItemStack map = piE.getItem();
                MapView mapView = ((MapMeta) map.getItemMeta()).getMapView();

                //send Link with url to player
                DankMiner.sendLink(piE.getPlayer(), "§6" + post.getTitle() + " §a| [§bpost§a]", "https://www." + post.getPostURL());

                //replace Renderers if MemeRenderer doesn't exist
                if(!(mapView.getRenderers().get(0) instanceof MemeRenderer)){
                    for(MapRenderer r : mapView.getRenderers()){
                        mapView.removeRenderer(r);
                    }
                    mapView.addRenderer(new MemeRenderer(meme));
                }

                //change image
                ((MemeRenderer) mapView.getRenderers().get(0)).changeMeme(meme);

            }


        }

    }

}
