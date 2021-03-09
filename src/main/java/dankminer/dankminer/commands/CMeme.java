package dankminer.dankminer.commands;

import dankminer.dankminer.DankMiner;
import dankminer.dankminer.MemeRenderer;
import dankminer.dankminer.utils.MemeLoader;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the CommandExecuter for the "/meme" command
 */
public class CMeme implements CommandExecutor {

    //is OP required to run this command
    private boolean needOp = true;

    public CMeme(boolean op) {
        //Load needOp variable from the config file
        needOp = op;
    }

    /**
     * Command execution
     * @param sender the sender on the command
     * @param command the command itself
     * @param label -
     * @param args command arguments
     * @return a boolean if the command was valid (Ic always true)
     */
    @Override
    public boolean onCommand( CommandSender sender,  Command command,  String label,  String[] args) {

        //checks if sender is player
        if(!(sender instanceof Player)) {
            DankMiner.serverLog("§cCommand can only be executed by a player!");
            return true;
        }

        Player player = (Player) sender;

        //checks if player needs/has op
        if(!player.isOp() && needOp){
            player.sendMessage("§cYou don't have the permission for this command!");
            return true;
        }

        //create a new filled map with the MemeRenderer
        ItemStack map = new ItemStack(Material.FILLED_MAP, 1);
        MapMeta meta = (MapMeta) map.getItemMeta();
        meta.setDisplayName("§cM§aE§6M§9E");
        MapView view = Bukkit.createMap(player.getWorld());
        for(MapRenderer renderer : view.getRenderers()){
            view.removeRenderer(renderer);
        }
        view.addRenderer(new MemeRenderer(MemeLoader.fetchImage(getClass().getResource("/STANDBY.png"))));
        meta.setMapView(view);
        List<String> lore = new ArrayList<>();
        lore.add("§bLeft click in air");
        lore.add("§bto get new random meme");
        meta.setLore(lore);
        map.setItemMeta(meta);

        //replaces the current Item in main hand with new map
        player.getInventory().setItemInMainHand(map);

        return true;
    }

}
