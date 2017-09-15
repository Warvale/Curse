package net.warvale.uhcmeetup.tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import net.warvale.uhcmeetup.UHCMeetup;
import net.warvale.uhcmeetup.message.MessageManager;
import net.warvale.uhcmeetup.message.PrefixType;

public class BorderRunnable extends BukkitRunnable {

    private int shrinkInterval = UHCMeetup.getGame().getShrinkTime() * 60;


    @Override
    public void run() {

        this.shrinkInterval -= 10;

        if (this.shrinkInterval == 10) {
            Bukkit.getServer().broadcastMessage(MessageManager.getPrefix(PrefixType.MAIN) + ChatColor.GOLD + "Border shrinking in 10 seconds!");
            new BorderShrinkTask().runTaskTimerAsynchronously(UHCMeetup.getInstance(), this.shrinkInterval * 20, this.shrinkInterval * 20);
            this.cancel();
        } else if (this.shrinkInterval > 10){
            switch (this.shrinkInterval) {
                case 640:
                    MessageManager.broadcast(MessageManager.getPrefix(PrefixType.MAIN) + ChatColor.GOLD + "Border shrinking in " + this.shrinkInterval / 60 + " minutes!");
                    break;
                case 540:
                    MessageManager.broadcast(MessageManager.getPrefix(PrefixType.MAIN) + ChatColor.GOLD + "Border shrinking in " + this.shrinkInterval / 60 + " minutes!");
                    break;
                case 480:
                    MessageManager.broadcast(MessageManager.getPrefix(PrefixType.MAIN) + ChatColor.GOLD + "Border shrinking in " + this.shrinkInterval / 60 + " minutes!");
                    break;
                case 420:
                    MessageManager.broadcast(MessageManager.getPrefix(PrefixType.MAIN) + ChatColor.GOLD + "Border shrinking in " + this.shrinkInterval / 60 + " minutes!");
                    break;
                case 360:
                    MessageManager.broadcast(MessageManager.getPrefix(PrefixType.MAIN) + ChatColor.GOLD + "Border shrinking in " + this.shrinkInterval / 60 + " minutes!");
                    break;
                case 300:
                    MessageManager.broadcast(MessageManager.getPrefix(PrefixType.MAIN) + ChatColor.GOLD + "Border shrinking in " + this.shrinkInterval / 60 + " minutes!");
                    break;
                case 240:
                    MessageManager.broadcast(MessageManager.getPrefix(PrefixType.MAIN) + ChatColor.GOLD + "Border shrinking in " + this.shrinkInterval / 60 + " minutes!");
                    break;
                case 180:
                    MessageManager.broadcast(MessageManager.getPrefix(PrefixType.MAIN) + ChatColor.GOLD + "Border shrinking in " + this.shrinkInterval / 60 + " minutes!");
                    break;
                case 120:
                    MessageManager.broadcast(MessageManager.getPrefix(PrefixType.MAIN) + ChatColor.GOLD + "Border shrinking in " + this.shrinkInterval / 60 + " minutes!");
                    break;
                case 60:
                    MessageManager.broadcast(MessageManager.getPrefix(PrefixType.MAIN) + ChatColor.GOLD + "Border shrinking in " + this.shrinkInterval / 60 + " minutes!");
                    break;
            }

        }

    }


}
