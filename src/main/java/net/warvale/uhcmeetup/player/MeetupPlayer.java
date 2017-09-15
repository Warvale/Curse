package net.warvale.uhcmeetup.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MeetupPlayer {

    public enum State {
        PLAYER, DEAD, SPEC, MOD
    }

    private UUID uuid;
    private State state;
    private boolean doStatTracking;
    private int kills = 0;
    private int deaths = 0;
    private int killStreak = 0;
    private int totalKills = 0;
    private int totalDeaths = 0;
    private int highestKillStreak = 0;

    public MeetupPlayer(UUID uuid, State state) {
        this.uuid = uuid;
        this.state = state;

        // Only track stats if we were a player (used for the end flushing)
        this.doStatTracking = this.state.equals(State.PLAYER);
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public String getName() {
        return Bukkit.getServer().getOfflinePlayer(uuid).getName();
    }

    public void addTotalDeath() {
        ++this.totalDeaths;
    }

    public int getTotalDeaths() {
        return this.totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public void addTotalKill() {
        ++this.totalKills;
    }

    public int getTotalKills() {
        return this.totalKills;
    }

    public void setTotalKills(int totalKills) {
        this.totalKills = totalKills;
    }

    public void addKill() {
        ++this.kills;
    }

    public int getKills() {
        return this.kills;
    }

    public void addDeath() {
        ++this.deaths;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public int getHighestKillStreak() {
        return this.highestKillStreak;
    }

    public void setHighestKillStreak(int n) {
        this.highestKillStreak = n;
    }

    public int getKillStreak() {
        return killStreak;
    }

    public void addKillStreak() {
        ++this.killStreak;
    }

    public void resetKillStreak() {
        this.killStreak = 0;
    }

    public boolean isAliveAndPlaying() {
        return this.state.equals(State.PLAYER);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        /*if (state.equals(State.DEAD) && this.state != State.PLAYER) {
            throw new RuntimeException("Player being moved to dead state from " + this.state.name());
        }*/
        if (state.equals(State.DEAD) && !this.state.equals(State.PLAYER)) {
            throw new RuntimeException("Player being moved to dead state from " + this.state.name());
        }

        this.state = state;

        /*if (state == State.DEAD) {

        }*/
    }

    public boolean isVanishedPlayer() {
        return this.state.equals(State.SPEC) || this.state.equals(State.MOD);
    }

    public boolean trackStats() {
        return this.doStatTracking;
    }

}
