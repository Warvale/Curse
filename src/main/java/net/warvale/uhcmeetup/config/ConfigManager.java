package net.warvale.uhcmeetup.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import net.warvale.uhcmeetup.UHCMeetup;

import java.io.File;

public class ConfigManager {

    private static ConfigManager instance;

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    //config
    private static FileConfiguration config;
    private File configFile;

    public void setup() {

        //copy default config file
        UHCMeetup.getInstance().getLogger().info("Loading configs...");

        try {

            if (!UHCMeetup.getInstance().getDataFolder().exists()) {
                UHCMeetup.getInstance().getDataFolder().mkdir();
            }

            configFile = new File(UHCMeetup.getInstance().getDataFolder(), "config.yml");
            if (!configFile.exists()) {
                //copy default config file and defaults
                configFile.createNewFile();
            }

            reloadConfig();
        } catch (Exception ex) {
            ex.printStackTrace();
            UHCMeetup.getInstance().getLogger().severe("Could not load configs");
        }

    }

    public static FileConfiguration getConfig() {
        return config;
    }

    public void reloadConfig() {
        try {

            config = new YamlConfiguration();
            config.load(configFile);

        } catch (Exception ex) {
            UHCMeetup.getInstance().getLogger().severe("Could not reload config: " + configFile.getName());
        }
    }


    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (Exception ex) {
            UHCMeetup.getInstance().getLogger().severe("Could not save config: " + configFile.getName());
        }
    }

}
