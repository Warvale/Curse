package net.warvale.uhcmeetup.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import net.warvale.uhcmeetup.UHCMeetup;
import net.warvale.uhcmeetup.config.ConfigManager;
import net.warvale.uhcmeetup.tasks.BorderRunnable;

import java.util.HashSet;
import java.util.Set;

public class GameManager {

    private int maxPlayers;
    private int minPlayers;

    private boolean pvp;
    private String worldName;
    private int currentBorder;
    private int shrinkTime;
    private GameState state;
    private GameType type;
    private Set<Player> players = new HashSet<>();
    private Set<Player> spectators = new HashSet<>();

    public GameManager() {
        //game settings
        this.minPlayers = ConfigManager.getConfig().getInt("settings.minPlayers", 8);
        this.maxPlayers = ConfigManager.getConfig().getInt("settings.maxPlayers", 100);
        this.worldName = ConfigManager.getConfig().getString("settings.world", "uhcworld");

        this.pvp = false;
        this.state = GameState.LOADING;
        this.type = GameType.FFA;
        this.currentBorder = ConfigManager.getConfig().getInt("settings.borderSize", 125);
        this.shrinkTime = ConfigManager.getConfig().getInt("settings.shrinkTime", 3);
    }

    public void setPVP(boolean pvp) {
        this.pvp = pvp;
    }

    public boolean pvpEnabled() {
        return this.pvp;
    }

    public String getWorldName() {
        return this.worldName;
    }

    public World getWorld() {
        return Bukkit.getWorld(this.worldName);
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
        ConfigManager.getConfig().set("minPlayers", minPlayers);
        ConfigManager.getInstance().saveConfig();
    }

    public int getMinPlayers() {
        return this.minPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
        ConfigManager.getConfig().set("maxPlayers", maxPlayers);
        ConfigManager.getInstance().saveConfig();
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public void setCurrentBorder(int radius) {
        this.currentBorder = radius;
    }

    public int getCurrentBorder() {
        return this.currentBorder;
    }

    public GameState getState() {
        return this.state;
    }

    public boolean isState(GameState state) {
        return this.getState().equals(state);
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public GameType getType() {
        return this.type;
    }

    public boolean isType(GameType type) {
        return getType().equals(type);
    }

    public void setType(GameType type) {
        this.type = type;
    }

    public void setShrinkTime(int shrinkTime) {
        this.shrinkTime = shrinkTime;
    }

    public int getShrinkTime() {
        return this.shrinkTime;
    }

    public void startBorderShrink() {
        Bukkit.broadcastMessage(UHCMeetup.PREFIX + ChatColor.RED + "The border will now shrink every " + this.shrinkTime + " minutes until 10x10!");
        new BorderRunnable().runTaskTimerAsynchronously(UHCMeetup.getInstance(), this.shrinkTime * 20, this.shrinkTime * 20);
    }

}
