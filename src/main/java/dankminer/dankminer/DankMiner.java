package dankminer.dankminer;

import dankminer.dankminer.commands.CMeme;
import dankminer.dankminer.utils.ConfigFile;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * DankMiner Class
 */
public final class DankMiner extends JavaPlugin {

    @Override
    public void onEnable() {

        serverLog("Enabled Dank Miner!");
        ConfigFile.createDefaultConfig();
        this.register();

    }

    //utility methods
    public static void serverLog(String text){
        Bukkit.getConsoleSender().sendMessage(text);
    }

    public static void sendLink(Player p, String message, String url){

        TextComponent component = new TextComponent(message);
        component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));

        p.spigot().sendMessage((BaseComponent) component);

    }

    private void register(){

        FileConfiguration config = ConfigFile.getConfigFile();

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new EMapInteract(config.getInt("cooldown")), this);
        Bukkit.getPluginCommand("meme").setExecutor(new CMeme(config.getBoolean("op")));

    }

    @Override
    public void onDisable() {

    }

}
