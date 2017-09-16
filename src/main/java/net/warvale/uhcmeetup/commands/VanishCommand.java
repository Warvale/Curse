package net.warvale.uhcmeetup.commands;

import org.bukkit.*;
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
import net.warvale.uhcmeetup.managers.GameState;
import net.warvale.uhcmeetup.player.MeetupPlayer;
import net.warvale.uhcmeetup.player.PlayerManager;

import java.util.HashSet;

public class VanishCommand implements CommandExecutor {

    public static HashSet<Player> players = new HashSet<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // Don't let regular users use this cmd
            if (!player.isOp()) {
                return true;
            }

            if (args.length > 0) {
                Player p = UHCMeetup.getInstance().getServer().getPlayer(args[0]);
                if (p == null) {
                    player.sendMessage(ChatColor.RED + "Player is not online or doesn't exist.");
                } else {
                    player = p;
                }
            }

            // Vanish whoever now
            if (VanishCommand.players.contains(player)) {
                VanishCommand.unvanishPlayer(player);
            } else {
                VanishCommand.vanishPlayer(player);
            }
        }

        return true;
    }

    public static void vanishPlayer(final Player player) {
        VanishCommand.players.add(player);
        player.spigot().setCollidesWithEntities(false);
        player.sendMessage(ChatColor.GREEN + "You have vanished!");

        // Hide players
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.hidePlayer(player);
        }

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getInventory().setHelmet(new ItemStack(Material.AIR));
        player.getInventory().setChestplate(new ItemStack(Material.AIR));
        player.getInventory().setLeggings(new ItemStack(Material.AIR));
        player.getInventory().setBoots(new ItemStack(Material.AIR));

        if (UHCMeetup.getGame().isState(GameState.LOBBY)) {
            player.teleport(new Location(UHCMeetup.getGame().getWorld(), 0, 100, 0));
        }

        player.setAllowFlight(true);
        player.setFlying(true);
        player.setGameMode(GameMode.SPECTATOR);
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));
    }

    public static void unvanishPlayer(Player player) {
        VanishCommand.players.remove(player);
        player.spigot().setCollidesWithEntities(true);
        player.sendMessage(ChatColor.GREEN + "You are now visible!");

        // Show players
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.showPlayer(player);
        }
    }

}
