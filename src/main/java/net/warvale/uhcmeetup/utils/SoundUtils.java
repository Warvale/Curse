package net.warvale.uhcmeetup.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundUtils {

    public static void playSound(Sound sound) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.playSound(player.getLocation(), sound, 1, 1);
        }
    }

}
