package net.warvale.uhcmeetup.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import net.warvale.uhcmeetup.UHCMeetup;

import java.util.HashSet;
import java.util.Set;

public class BorderGeneration {

    private static Set<Material> passThroughMaterials = new HashSet<>();
    private static JavaPlugin plugin;

    public static void setup(JavaPlugin plugin) {
        BorderGeneration.plugin = plugin;
        BorderGeneration.passThroughMaterials.add(Material.LOG);
        BorderGeneration.passThroughMaterials.add(Material.LOG_2);
        BorderGeneration.passThroughMaterials.add(Material.LEAVES);
        BorderGeneration.passThroughMaterials.add(Material.LOG_2);
        BorderGeneration.passThroughMaterials.add(Material.AIR);
    }

    public static void addBedrockBorder(final int radius, int blocksHigh) {
        for (int i = 0; i < blocksHigh; i++) {
            new BukkitRunnable() {
                public void run() {
                    BorderGeneration.addBedrockBorder(radius);
                }
            }.runTaskLater(BorderGeneration.plugin, i);
        }
    }

    private static void figureOutBlockToMakeBedrock(int x, int z) {
        Block block = Bukkit.getWorld(UHCMeetup.getGame().getWorldName()).getHighestBlockAt(x, z);
        Block below = block.getRelative(BlockFace.DOWN);
        while (BorderGeneration.passThroughMaterials.contains(below.getType()) && below.getY() > 1) {
            below = below.getRelative(BlockFace.DOWN);
        }
        below.getRelative(BlockFace.UP).setType(Material.BEDROCK);
    }

    public static void addBedrockBorder(final int radius) {
        new BukkitRunnable() {

            private int counter = -radius - 1;
            private boolean phase1 = false;
            private boolean phase2 = false;
            private boolean phase3 = false;

            @Override
            public void run() {
                if (!phase1) {
                    int maxCounter = counter + 500;
                    int x = -radius - 1;
                    for (int z = counter; z <= radius && counter <= maxCounter; z++, counter++) {
                        BorderGeneration.figureOutBlockToMakeBedrock(x, z);
                    }

                    if (counter >= radius) {
                        counter = -radius - 1;
                        phase1 = true;
                    }

                    return;
                }

                if (!phase2) {
                    int maxCounter = counter + 500;
                    int x = radius;
                    for (int z = counter; z <= radius && counter <= maxCounter; z++, counter++) {
                        BorderGeneration.figureOutBlockToMakeBedrock(x, z);
                    }

                    if (counter >= radius) {
                        counter = -radius - 1;
                        phase2 = true;
                    }

                    return;
                }

                if (!phase3) {
                    int maxCounter = counter + 500;
                    int z = -radius - 1;
                    for (int x = counter; x <= radius && counter <= maxCounter; x++, counter++) {
                        if (x == radius || x == -radius - 1) {
                            continue;
                        }

                        BorderGeneration.figureOutBlockToMakeBedrock(x, z);
                    }

                    if (counter >= radius) {
                        counter = -radius - 1;
                        phase3 = true;
                    }

                    return;
                }


                int maxCounter = counter + 500;
                int z = radius;
                for (int x = counter; x <= radius && counter <= maxCounter; x++, counter++) {
                    if (x == radius || x == -radius - 1) {
                        continue;
                    }

                    BorderGeneration.figureOutBlockToMakeBedrock(x, z);
                }

                if (counter >= radius) {
                    this.cancel();
                }
            }
        }.runTaskTimer(BorderGeneration.plugin, 0, 5);
    }


}
