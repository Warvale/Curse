package net.warvale.uhcmeetup.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import net.warvale.uhcmeetup.UHCMeetup;
import net.warvale.uhcmeetup.managers.GameState;
import net.warvale.uhcmeetup.scoreboards.LobbyScoreboard;

public class LobbyScoreboardTask extends BukkitRunnable {

    @Override
    public void run() {
        if (UHCMeetup.getGame().isState(GameState.LOBBY)) {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                LobbyScoreboard.getInstance().updateScoreboard(player);
            }
        } else {
            this.cancel();
        }
    }

}
