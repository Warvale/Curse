package net.warvale.uhcmeetup.tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import net.warvale.uhcmeetup.UHCMeetup;
import net.warvale.uhcmeetup.player.MeetupPlayer;
import net.warvale.uhcmeetup.player.PlayerManager;
import net.warvale.uhcmeetup.scoreboards.GameScoreboard;

public class GameTask extends BukkitRunnable {

    private int seconds = 0;
    private int shrinkTime = UHCMeetup.getGame().getShrinkTime() * 60;
    private String ffaWin = ChatColor.GOLD + "Congratulations to %player% for winning the UHC Meetup!";

    public void run() {
        seconds++;

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            GameScoreboard.getInstance().updateScoreboard(player);
        }

        if (this.shrinkTime == this.seconds) {
            UHCMeetup.getGame().startBorderShrink();
        }

        if (PlayerManager.getInstance().getAlivePlayers().size() == 1) {

            //todo: cancel tasks if any are running?
            for (MeetupPlayer player : PlayerManager.getInstance().getAlivePlayers()) {
                    Bukkit.broadcastMessage(UHCMeetup.PREFIX + ffaWin.replace("%player%", player.getName()));
            }

            this.cancel();
            new RestartTask().runTaskTimer(UHCMeetup.getInstance(), 20, 20);
        }

    }


}
