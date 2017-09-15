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
            objective.setDisplayName(ChatColor.DARK_GRAY + "» " + ChatColor.RED + "UHC Meetup" + ChatColor.DARK_GRAY + " «");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

            objective.getScore("  ").setScore(9);
            Team gameMode = scoreboard.registerNewTeam("GameMode");
            gameMode.addEntry(ChatColor.AQUA + "Mode: ");
            gameMode.setSuffix(ChatColor.WHITE + UHCMeetup.getGame().getType().toString());
            objective.getScore(ChatColor.AQUA + "Mode: ").setScore(8);

            Team kills = scoreboard.registerNewTeam("PlayerKills");
            kills.addEntry(ChatColor.AQUA + "Your Kills: ");
            kills.setSuffix(ChatColor.WHITE + String.valueOf(PlayerManager.getInstance().getMeeupPlayer(player.getUniqueId()).getKills()));
            objective.getScore(ChatColor.AQUA + "Your Kills: ").setScore(7);


            Team online = scoreboard.registerNewTeam("PlayersLeft");
            online.addEntry(ChatColor.AQUA + "Players Left: ");
            online.setSuffix(ChatColor.WHITE + String.valueOf(PlayerManager.getInstance().getAlivePlayers().size()));
            objective.getScore(ChatColor.AQUA + "Players Left: ").setScore(6);

            Team specs = scoreboard.registerNewTeam("SpecCount");
            specs.addEntry(ChatColor.AQUA + "Spectators: ");
            specs.setSuffix(ChatColor.WHITE + String.valueOf(PlayerManager.getInstance().getSpectators().size()));
            objective.getScore(ChatColor.AQUA + "Spectators: ").setScore(5);

            objective.getScore("  ").setScore(4);
            Team border = scoreboard.registerNewTeam("BorderSize");
            border.addEntry(ChatColor.AQUA + "Border: ");
            border.setSuffix(ChatColor.WHITE + String.valueOf(UHCMeetup.getGame().getCurrentBorder()));
            objective.getScore(ChatColor.AQUA + "Border: ").setScore(3);

            objective.getScore(" ").setScore(2);
            objective.getScore(ChatColor.RED + "warvale.net").setScore(1);

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
