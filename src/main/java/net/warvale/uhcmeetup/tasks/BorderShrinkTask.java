package net.warvale.uhcmeetup.tasks;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import net.warvale.uhcmeetup.UHCMeetup;
import net.warvale.uhcmeetup.message.MessageManager;
import net.warvale.uhcmeetup.message.PrefixType;
import net.warvale.uhcmeetup.utils.BorderUtils;
import net.warvale.uhcmeetup.utils.SoundUtils;


public class BorderShrinkTask extends BukkitRunnable {

    private int i = 10;

    @Override
    public void run() {
        --this.i;
        if (this.i >= 1) {
            MessageManager.broadcast(MessageManager.getPrefix(PrefixType.MAIN) + ChatColor.GOLD + "Border shrinking in " +  this.i + " seconds!");
            SoundUtils.playSound(Sound.NOTE_STICKS);
        } else if (this.i == 0) {
            if (UHCMeetup.getGame().getCurrentBorder() == 125) {
                UHCMeetup.getGame().setCurrentBorder(75);
                BorderUtils.shrinkBorder(UHCMeetup.getGame().getCurrentBorder(), this);
                MessageManager.broadcast(MessageManager.getPrefix(PrefixType.MAIN) + ChatColor.GOLD + "The world border has shrunk to " + UHCMeetup.getGame().getCurrentBorder()  + "x" + UHCMeetup.getGame().getCurrentBorder());
            } else if (UHCMeetup.getGame().getCurrentBorder() == 75) {
                UHCMeetup.getGame().setCurrentBorder(45);
                BorderUtils.shrinkBorder(UHCMeetup.getGame().getCurrentBorder(), this);
                MessageManager.broadcast(MessageManager.getPrefix(PrefixType.MAIN) + ChatColor.GOLD + "The world border has shrunk to " + UHCMeetup.getGame().getCurrentBorder()  + "x" + UHCMeetup.getGame().getCurrentBorder());
            } else if (UHCMeetup.getGame().getCurrentBorder() == 45) {
                UHCMeetup.getGame().setCurrentBorder(25);
                BorderUtils.shrinkBorder(UHCMeetup.getGame().getCurrentBorder(), this);
                MessageManager.broadcast(MessageManager.getPrefix(PrefixType.MAIN) + ChatColor.GOLD + "The world border has shrunk to " + UHCMeetup.getGame().getCurrentBorder()  + "x" + UHCMeetup.getGame().getCurrentBorder());
            } else if (UHCMeetup.getGame().getCurrentBorder() == 25) {
                UHCMeetup.getGame().setCurrentBorder(10);
                BorderUtils.shrinkBorder(UHCMeetup.getGame().getCurrentBorder(), this);
                MessageManager.broadcast(MessageManager.getPrefix(PrefixType.MAIN) + ChatColor.GOLD + "The world border has shrunk to " + UHCMeetup.getGame().getCurrentBorder()  + "x" + UHCMeetup.getGame().getCurrentBorder());
            }
        }
    }

}
