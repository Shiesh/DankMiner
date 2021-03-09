package dankminer.dankminer.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigFile {

    private static final String CONFIG_PATH = Bukkit.getServer().getPluginManager().getPlugin("DankMiner").getDataFolder().getAbsolutePath();

    /**
     * Load FileConfiguration from the DankMiner.yml config file
     */
    public static FileConfiguration getConfigFile(){

        File file;

        try{
            file = new File(CONFIG_PATH + ".yml");
            if(!file.exists()){
                file.createNewFile();
                return null;
            }
        }catch (Exception e){
            return null;
        }

        return YamlConfiguration.loadConfiguration(file);

    }

    /**
     * Creates a new DankMiner.yml file if none exists
     */
    public static void createDefaultConfig(){

        //default content
        String content = "subreddit: dankmemes\n" +
                         "cooldown: 2\n" +
                         "op: true\n";

        try{
            File file = new File( CONFIG_PATH + ".yml");
            if(!file.exists()){
                Files.writeString(Path.of(CONFIG_PATH + ".yml"), content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
