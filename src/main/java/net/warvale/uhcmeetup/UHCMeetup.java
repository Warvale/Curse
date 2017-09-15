package net.warvale.uhcmeetup;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import net.warvale.uhcmeetup.commands.ForceStartCMD;
import net.warvale.uhcmeetup.commands.ModCMD;
import net.warvale.uhcmeetup.commands.VanishCommand;
import net.warvale.uhcmeetup.config.ConfigManager;
import net.warvale.uhcmeetup.listeners.DeathListener;
import net.warvale.uhcmeetup.listeners.JoinListener;
import net.warvale.uhcmeetup.listeners.LobbyListener;
import net.warvale.uhcmeetup.listeners.WorldListener;
import net.warvale.uhcmeetup.managers.GameState;
import net.warvale.uhcmeetup.message.MessageManager;
import net.warvale.uhcmeetup.tasks.LobbyScoreboardTask;
import net.warvale.uhcmeetup.utils.BorderGeneration;
import net.warvale.uhcmeetup.managers.GameManager;
import net.warvale.uhcmeetup.managers.WorldManager;
import net.warvale.uhcmeetup.utils.FileUtils;
import net.warvale.uhcmeetup.world.UHCMeetupWorldGenerator;

import java.io.File;

public class UHCMeetup extends JavaPlugin {

    private static UHCMeetup instance;

    private WorldManager worldManager;
    private static GameManager gameManager;
    public final static String PREFIX = ChatColor.GOLD + "" + ChatColor.BOLD + "[" + ChatColor.DARK_AQUA + "UHCMeetup" + ChatColor.GOLD + "" + ChatColor.BOLD + "] ";

    @Override
    public void onEnable() {
        UHCMeetup.instance = this;

        //register bungeecord
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        //setup config manager and border generation
        ConfigManager.getInstance().setup();
        MessageManager.getInstance().setup();
        BorderGeneration.setup(this);

        //setup world manager
        this.worldManager = new WorldManager(this);
        gameManager = new GameManager();

        //register listeners
        registerListener(new JoinListener());
        registerListener(new DeathListener());
        registerListener(new LobbyListener());
        registerListener(new WorldListener());

        //register commands
        this.registerCommands();

        //being world generation
        new UHCMeetupWorldGenerator().runTaskTimer(this, 20L, 20L);
    }

    @Override
    public void onDisable() {

        World uhcworld = this.getServer().getWorld(gameManager.getWorldName());

        // Unload world
        if (uhcworld != null) {
            this.getServer().unloadWorld(uhcworld, false);
        }

        // Delete world
        FileUtils.deleteDirectory(new File(gameManager.getWorldName()));
    }

    public static UHCMeetup getInstance() {
        return UHCMeetup.instance;
    }

    public static void registerListener(Listener l) {
        Bukkit.getPluginManager().registerEvents(l, UHCMeetup.instance);
    }

    private void  registerCommands() {
        getCommand("forcestart").setExecutor(new ForceStartCMD());
        getCommand("vanish").setExecutor(new VanishCommand());
        getCommand("mod").setExecutor(new ModCMD());
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public static GameManager getGame() {
        return gameManager;
    }

    /**
     * Get the spawnpoint of the lobby.
     *
     * @return The lobby spawnpoint.
     */
    public Location getSpawn() {
        FileConfiguration config = ConfigManager.getConfig();

        World world = Bukkit.getWorld(config.getString("spawn.world", "lobby"));

        if (world == null) {
            world = Bukkit.getWorlds().get(0);
        }

        double x = config.getDouble("spawn.x", 0.5);
        double y = config.getDouble("spawn.y", 50);
        double z = config.getDouble("spawn.z", 0.5);
        float yaw = (float) config.getDouble("spawn.yaw", 0);
        float pitch = (float) config.getDouble("spawn.pitch", 0);

        Location loc = new Location(world, x, y, z, yaw, pitch);
        return loc;
    }
}
