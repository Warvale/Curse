package net.warvale.uhcmeetup.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import net.warvale.uhcmeetup.UHCMeetup;
import net.warvale.uhcmeetup.tasks.BorderRunnable;

public class BorderUtils {

    public static void shrinkBorder(int n, BukkitRunnable bukkitRunnable) {
        bukkitRunnable.cancel();
        UHCMeetup.getGame().setCurrentBorder(n);
        World world = UHCMeetup.getGame().getWorld();
        boolean bl = false;
        for (Player player : UHCMeetup.getGame().getWorld().getPlayers()) {
            if (player.getLocation().getBlockX() > n) {
                bl = true;
                player.setNoDamageTicks(55);
                player.setFallDistance(0.0f);
                player.teleport(new Location(world, n - 4, world.getHighestBlockYAt(n - 4, player.getLocation().getBlockZ()) + 0.5, player.getLocation().getBlockZ()));
                player.setFallDistance(0.0f);
                player.getLocation().add(0.0, 2.0, 0.0).getBlock().setType(Material.AIR);
                player.getLocation().add(0.0, 3.0, 0.0).getBlock().setType(Material.AIR);
                player.getLocation().add(0.0, 4.0, 0.0).getBlock().setType(Material.AIR);
                player.teleport(new Location(world, player.getLocation().getBlockX(), world.getHighestBlockAt(player.getLocation().getBlockX(), player.getLocation().getBlockZ()).getLocation().getBlockY() + 0.5, player.getLocation().getBlockZ()));
            }
            if (player.getLocation().getBlockZ() > n) {
                bl = true;
                player.setNoDamageTicks(55);
                player.setFallDistance(0.0f);
                player.teleport(new Location(world, player.getLocation().getBlockX(), world.getHighestBlockYAt(player.getLocation().getBlockX(), n - 4) + 0.5, n - 4));
                player.setFallDistance(0.0f);
                player.getLocation().add(0.0, 2.0, 0.0).getBlock().setType(Material.AIR);
                player.getLocation().add(0.0, 3.0, 0.0).getBlock().setType(Material.AIR);
                player.getLocation().add(0.0, 4.0, 0.0).getBlock().setType(Material.AIR);
                player.teleport(new Location(world, player.getLocation().getBlockX(), world.getHighestBlockAt(player.getLocation().getBlockX(), player.getLocation().getBlockZ()).getLocation().getBlockY() + 0.5, player.getLocation().getBlockZ()));
            }
            if (player.getLocation().getBlockX() < - n) {
                bl = true;
                player.setNoDamageTicks(55);
                player.setFallDistance(0.0f);
                player.teleport(new Location(world, - n + 4, world.getHighestBlockYAt(- n + 4, player.getLocation().getBlockZ()) + 0.5, player.getLocation().getBlockZ()));
                player.setFallDistance(0.0f);
                player.getLocation().add(0.0, 2.0, 0.0).getBlock().setType(Material.AIR);
                player.getLocation().add(0.0, 3.0, 0.0).getBlock().setType(Material.AIR);
                player.getLocation().add(0.0, 4.0, 0.0).getBlock().setType(Material.AIR);
                player.teleport(new Location(world, player.getLocation().getBlockX(), world.getHighestBlockAt(player.getLocation().getBlockX(), player.getLocation().getBlockZ()).getLocation().getBlockY() + 0.5, player.getLocation().getBlockZ()));
            }
            if (player.getLocation().getBlockZ() >= - n) continue;
            bl = true;
            player.setNoDamageTicks(55);
            player.setFallDistance(0.0f);
            player.teleport(new Location(world, player.getLocation().getBlockX(), world.getHighestBlockYAt(player.getLocation().getBlockX(), - n + 4) + 0.5, - n + 4));
            player.setFallDistance(0.0f);
            player.getLocation().add(0.0, 2.0, 0.0).getBlock().setType(Material.AIR);
            player.getLocation().add(0.0, 3.0, 0.0).getBlock().setType(Material.AIR);
            player.getLocation().add(0.0, 4.0, 0.0).getBlock().setType(Material.AIR);
            player.teleport(new Location(world, player.getLocation().getBlockX(), world.getHighestBlockAt(player.getLocation().getBlockX(), player.getLocation().getBlockZ()).getLocation().getBlockY() + 0.5, player.getLocation().getBlockZ()));
        }

        BorderGeneration.addBedrockBorder(n, 5);
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "worldborder:wb " + UHCMeetup.getGame().getWorldName() + " setcorners " + n + " -" + n + " -" + n + " " + n);

        if (n == 10) {
            return;
        }

        new BorderRunnable().runTaskTimerAsynchronously(UHCMeetup.getInstance(), UHCMeetup.getGame().getShrinkTime() * 20, UHCMeetup.getGame().getShrinkTime() * 20);
    }

}
