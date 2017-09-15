package net.warvale.uhcmeetup.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import net.warvale.uhcmeetup.UHCMeetup;
import net.warvale.uhcmeetup.player.PlayerManager;

import java.util.HashMap;
import java.util.Map;

public class GameScoreboard {

    private Map<Player, Scoreboard> scoreboards = new HashMap<>();

    private static GameScoreboard instance;

    public static GameScoreboard getInstance() {
        if (instance == null) {
            instance = new GameScoreboard();
        }
        return instance;
    }

    public void newScoreboard(Player player) {
        if (player != null) {

            ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
            Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
            Objective objective = scoreboard.registerNewObjective("game", "dummy");
            objective.setDisplayName(ChatColor.DARK_GRAY + "» " + ChatColor.DARK_AQUA + "UHC Meetup" + ChatColor.DARK_GRAY + " «");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

            objective.getScore("  ").setScore(9);
            Team gameMode = scoreboard.registerNewTeam("GameMode");
            gameMode.addEntry(ChatColor.GREEN + "Mode: ");
            gameMode.setSuffix(ChatColor.WHITE + UHCMeetup.getGame().getType().toString());
            objective.getScore(ChatColor.GREEN + "Mode: ").setScore(8);

            Team kills = scoreboard.registerNewTeam("PlayerKills");
            kills.addEntry(ChatColor.GREEN + "Your Kills: ");
            kills.setSuffix(ChatColor.WHITE + String.valueOf(PlayerManager.getInstance().getMeeupPlayer(player.getUniqueId()).getKills()));
            objective.getScore(ChatColor.GREEN + "Your Kills: ").setScore(7);


            Team online = scoreboard.registerNewTeam("PlayersLeft");
            online.addEntry(ChatColor.GREEN + "Players Left: ");
            online.setSuffix(ChatColor.WHITE + String.valueOf(PlayerManager.getInstance().getAlivePlayers().size()));
            objective.getScore(ChatColor.GREEN + "Players Left: ").setScore(6);

            Team specs = scoreboard.registerNewTeam("SpecCount");
            specs.addEntry(ChatColor.GREEN + "Spectators: ");
            specs.setSuffix(ChatColor.WHITE + String.valueOf(PlayerManager.getInstance().getSpectators().size()));
            objective.getScore(ChatColor.GREEN + "Spectators: ").setScore(5);

            objective.getScore("  ").setScore(4);
            Team border = scoreboard.registerNewTeam("BorderSize");
            border.addEntry(ChatColor.GREEN + "Border: ");
            border.setSuffix(ChatColor.WHITE + String.valueOf(UHCMeetup.getGame().getCurrentBorder()));
            objective.getScore(ChatColor.GREEN + "Border: ").setScore(3);

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
                Team kills = this.scoreboards.get(player).getTeam("PlayerKills");
                kills.setSuffix(ChatColor.WHITE + String.valueOf(PlayerManager.getInstance().getMeeupPlayer(player.getUniqueId()).getKills()));

                Team online = this.scoreboards.get(player).getTeam("PlayersLeft");
                online.setSuffix(ChatColor.WHITE + String.valueOf(PlayerManager.getInstance().getAlivePlayers().size()));

                Team specs = this.scoreboards.get(player).getTeam("SpecCount");
                specs.setSuffix(ChatColor.WHITE + String.valueOf(PlayerManager.getInstance().getSpectators().size()));

                Team border = this.scoreboards.get(player).getTeam("BorderSize");
                border.setSuffix(ChatColor.WHITE + String.valueOf(UHCMeetup.getGame().getCurrentBorder()));
            }
        }
    }

    public Map<Player, Scoreboard> getScoreboards() {
        return scoreboards;
    }
}
