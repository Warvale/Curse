package net.warvale.uhcmeetup.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import net.warvale.uhcmeetup.UHCMeetup;

import java.util.HashMap;
import java.util.Map;

public class LobbyScoreboard {

    private Map<Player, Scoreboard> scoreboards = new HashMap<>();

    private static LobbyScoreboard instance;

    public static LobbyScoreboard getInstance() {
        if (instance == null) {
            instance = new LobbyScoreboard();
        }
        return instance;
    }

    public void newScoreboard(Player player) {
        if (player != null) {

            ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
            Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
            Objective objective = scoreboard.registerNewObjective("lobby", "dummy");
            objective.setDisplayName(ChatColor.DARK_GRAY + "» " + ChatColor.DARK_AQUA + "UHC Meetup" + ChatColor.DARK_GRAY + " «");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

            objective.getScore("   ").setScore(6);

            Team gameMode = scoreboard.registerNewTeam("GameMode");
            gameMode.addEntry(ChatColor.GREEN + "Mode: ");
            gameMode.setSuffix(ChatColor.WHITE + UHCMeetup.getGame().getType().toString());
            objective.getScore(ChatColor.GREEN + "Mode: ").setScore(5);

            objective.getScore("  ").setScore(4);

            Team online = scoreboard.registerNewTeam("PlayersOnline");
            online.addEntry(ChatColor.GREEN + "Players: ");
            online.setSuffix(ChatColor.WHITE + String.valueOf(Bukkit.getServer().getOnlinePlayers().size()) + ChatColor.GRAY + "/"
                    + ChatColor.WHITE + String.valueOf(UHCMeetup.getGame().getMaxPlayers()));
            objective.getScore(ChatColor.GREEN + "Players: ").setScore(3);

            objective.getScore(" ").setScore(2);
            objective.getScore(ChatColor.GREEN + "warvale.net").setScore(1);

            this.scoreboards.put(player, scoreboard);
            player.setScoreboard(this.scoreboards.get(player));
        }
    }

    public void updateScoreboard(Player player) {
        if (player != null) {
            if (!this.scoreboards.containsKey(player)) {
                this.newScoreboard(player);
            } else {
                Team online = this.scoreboards.get(player).getTeam("PlayersOnline");
                online.setSuffix(ChatColor.WHITE + String.valueOf(Bukkit.getServer().getOnlinePlayers().size()) + ChatColor.GRAY + "/"
                        + ChatColor.WHITE + String.valueOf(UHCMeetup.getGame().getMaxPlayers()));
            }
        }
    }

    public Map<Player, Scoreboard> getScoreboards() {
        return scoreboards;
    }
}
