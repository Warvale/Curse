package net.warvale.uhcmeetup.tasks;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import net.warvale.uhcmeetup.UHCMeetup;
import net.warvale.uhcmeetup.utils.SoundUtils;

public class RestartTask extends BukkitRunnable {

    private int countdown = 11;

    @Override
    public void run() {
        countdown--;

        if (countdown > 0) {

            if (countdown <= 10) {
                this.broadcastShutdown(countdown);
                if (countdown <= 3) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        ByteArrayDataOutput out = ByteStreams.newDataOutput();
                        out.writeUTF("Connect");
                        out.writeUTF("lobby");

                        player.sendPluginMessage(UHCMeetup.getInstance(), "BungeeCord", out.toByteArray());
                    }
                }
            }

        } else {


            this.cancel();
            UHCMeetup.getInstance().getServer().shutdown();
        }

    }

    private void broadcastShutdown(int seconds) {
        Bukkit.broadcastMessage(UHCMeetup.PREFIX + ChatColor.RED + "Server restarting in " + seconds + " seconds!");
        SoundUtils.playSound(Sound.NOTE_STICKS);
    }

}
