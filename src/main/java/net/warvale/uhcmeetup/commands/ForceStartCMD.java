package net.warvale.uhcmeetup.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import net.warvale.uhcmeetup.UHCMeetup;
import net.warvale.uhcmeetup.managers.GameState;
import net.warvale.uhcmeetup.managers.ScatterManager;

public class ForceStartCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("uhcmeetup.forcestart")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission.");
            return false;
        }

        if (!UHCMeetup.getGame().isState(GameState.LOBBY)) {
            sender.sendMessage(ChatColor.RED + "You can only force start the game during the " + GameState.LOBBY.toString() + " state");
            return true;
        }

        ScatterManager.getInstance().startScatter(sender);
        return true;
    }

}
