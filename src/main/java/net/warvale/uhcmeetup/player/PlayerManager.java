package net.warvale.uhcmeetup.player;

import java.util.*;

public class PlayerManager {

    private static PlayerManager instance;
    private Map<UUID, MeetupPlayer> players = new HashMap<>();

    public static PlayerManager getInstance() {
        if (instance == null) {
            instance = new PlayerManager();
        }
        return instance;
    }

    public Map<UUID, MeetupPlayer> getMeetupPlayers() {
        return this.players;
    }

    public MeetupPlayer getMeeupPlayer(UUID playerUUID) {
        return this.players.get(playerUUID);
    }

    public boolean doesMeetupPlayerExsists(UUID playerUUID) {
        return this.players.containsKey(playerUUID);
    }

    public void createMeetupPlayer(UUID playerUUID, MeetupPlayer.State state) {
        this.players.put(playerUUID, new MeetupPlayer(playerUUID, state));
    }

    public void removeMeetupPlayer(UUID playerUUID) {
        if (this.players.containsKey(playerUUID)) {

            /*if (DevotedFFA.get().getGame().isStatsEnabled()) {
                getFFAPlayer(playerUUID).saveData();
            }*/

            this.players.remove(playerUUID);
        }
    }

    public Set<MeetupPlayer> getSpectators() {
        Set<MeetupPlayer> spectators = new HashSet<>();
        for (MeetupPlayer player : getMeetupPlayers().values()) {
            if (player.isVanishedPlayer()) {
                spectators.add(player);
            }
        }
        return spectators;
    }

    public Set<MeetupPlayer> getAlivePlayers() {
        Set<MeetupPlayer> alive = new HashSet<>();
        for (MeetupPlayer player : getMeetupPlayers().values()) {
            if (player.isAliveAndPlaying()) {
                alive.add(player);
            }
        }
        return alive;
    }
}
