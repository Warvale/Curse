package net.warvale.uhcmeetup.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;
import net.warvale.uhcmeetup.UHCMeetup;
import net.warvale.uhcmeetup.player.MeetupPlayer;
import net.warvale.uhcmeetup.player.PlayerManager;

public class ModCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        MeetupPlayer meetupPlayer = PlayerManager.getInstance().getMeeupPlayer(player.getUniqueId());

        if (meetupPlayer != null) {
            meetupPlayer.setState(MeetupPlayer.State.SPEC);
            VanishCommand.vanishPlayer(player);
        }



        return true;
    }

}
