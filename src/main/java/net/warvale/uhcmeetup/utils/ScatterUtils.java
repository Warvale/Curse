package net.warvale.uhcmeetup.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import net.warvale.uhcmeetup.UHCMeetup;

import java.util.Random;

public class ScatterUtils {

    public static void scatterPlayerRandom(Player player, Integer radius) {
        Random random = new Random();
        World world = UHCMeetup.getGame().getWorld();
        if (world == null) return;
        Location finalTeleport = new Location(world, 0.0, 0.0, 0.0);
        double randomAngle = random.nextDouble() * 3.141592653589793D * 2.0D;
        double newradius = radius * random.nextDouble();
        int[] coords = convertFromRadiansToBlock(newradius, randomAngle);

        finalTeleport.setX(coords[0]);
        finalTeleport.setZ(coords[1]);
        finalTeleport.setX(finalTeleport.getX() + 0.0);
        finalTeleport.setZ(finalTeleport.getZ() + 0.0);
        finalTeleport.setX(Math.round(finalTeleport.getX()) + 0.5);
        finalTeleport.setZ(Math.round(finalTeleport.getZ()) + 0.5);

        if (world.getChunkAt(finalTeleport).isLoaded()) {
            world.getChunkAt(finalTeleport).load(true);
        }

        finalTeleport.setY(getSafeY(finalTeleport) + 10.0D);
        scatterPlayer(player, finalTeleport);
    }

    private static int getSafeY(Location location) {
        return location.getWorld().getHighestBlockYAt(location);
    }

    private static int[] convertFromRadiansToBlock(double radius, double angle) {
        return new int[] { (int) Math.round(radius * Math.cos(angle)), (int) Math.round(radius * Math.sin(angle)) };
    }

    private static void scatterPlayer(Player player, Location location) {
        player.teleport(location);
    }



}
