package net.warvale.uhcmeetup.managers;

import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import net.warvale.uhcmeetup.UHCMeetup;
import net.warvale.uhcmeetup.utils.BiomeUtils;
import net.warvale.uhcmeetup.utils.BorderGeneration;
import net.warvale.uhcmeetup.utils.BorderUtils;

import java.io.File;

public class WorldManager {

    private UHCMeetup plugin;

    public WorldManager(UHCMeetup plugin) {
        this.plugin = plugin;
    }

    public void createWorld() {
        final int borderSize = UHCMeetup.getGame().getCurrentBorder();
        final World world = Bukkit.getServer().createWorld(new WorldCreator(UHCMeetup.getGame().getWorldName()).environment(World.Environment.NORMAL).type(WorldType.NORMAL));
        world.setTime(0);
        world.setPVP(false);
        UHCMeetup.getGame().setPVP(false);
        world.setDifficulty(Difficulty.NORMAL);
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("doMobSpawning", "false");
        world.setSpawnLocation(0, 100, 0);
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "multiverse-core:mvimport " + UHCMeetup.getGame().getWorldName() + " normal");

        //swap biomes, and setup border
        BorderGeneration.addBedrockBorder(125, 5);
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "worldborder:wb whoosh off");
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "worldborder:wb denypearl on");
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "worldborder:wb " + UHCMeetup.getGame().getWorldName() + " setcorners " + borderSize + " -" + borderSize + " -" + borderSize + " " + borderSize);
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "worldborder:wb shape square");
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "worldborder:wb knockback 2");

        /*if (game.lobbyScoreboard()) {
            new BukkitRunnable(){

                public void run() {
                    if (!game.isScattering()) {
                        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                            lobbyScoreboard.updateScoreboard(player);
                        }
                    } else {
                        this.cancel();
                    }
                }
            }.runTaskTimerAsynchronously(DevotedUHC.getInstance(), 20, 20);
        }*/
    }

    public void deleteWorld(boolean genWorld) {
        World world = UHCMeetup.getGame().getWorld();
        if (world != null) {
            for (Player player : world.getPlayers()) {
                //player.teleport(game.getSpawnLocation());
                player.kickPlayer("Generating new world");
            }
            Bukkit.getServer().unloadWorld(world, false);
            this.deleteFile(world.getWorldFolder());
            this.deleteFile(world.getWorldFolder());
        }
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "multiverse-core:mvdelete " + UHCMeetup.getGame().getWorldName());
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "multiverse-core:mv confirm");
        if (genWorld) {
            new BukkitRunnable(){

                public void run() {
                    createWorld();
                }
            }.runTaskLater(UHCMeetup.getInstance(), 120);
        }
    }

    public boolean deleteFile(File file) {
        if (file.isDirectory()) {
            for (File toDelete : file.listFiles()) {
                if (this.deleteFile(toDelete)) continue;
                return false;
            }
        }
        return file.delete();
    }

    public boolean exists(String string) {
        File file = new File(string);
        return file.exists();
    }

    public void swapBiomes() {
        // Swap all biomes with other biomes
        BiomeUtils.setBiomeBase(Biome.OCEAN, Biome.FOREST, 0);
        BiomeUtils.setBiomeBase(Biome.RIVER, Biome.PLAINS, 0);
        BiomeUtils.setBiomeBase(Biome.BEACH, Biome.TAIGA, 0);
        BiomeUtils.setBiomeBase(Biome.JUNGLE, Biome.PLAINS, 0);
        BiomeUtils.setBiomeBase(Biome.JUNGLE_HILLS, Biome.TAIGA, 0);
        BiomeUtils.setBiomeBase(Biome.JUNGLE_EDGE, Biome.DESERT, 0);
        BiomeUtils.setBiomeBase(Biome.DEEP_OCEAN, Biome.PLAINS, 0);
        BiomeUtils.setBiomeBase(Biome.SAVANNA_PLATEAU, Biome.FOREST, 0);

        // Weird sub-biomes
        BiomeUtils.setBiomeBase(Biome.JUNGLE, Biome.PLAINS, 128);
        BiomeUtils.setBiomeBase(Biome.JUNGLE_EDGE, Biome.DESERT, 128);
        BiomeUtils.setBiomeBase(Biome.SAVANNA, Biome.SAVANNA, 128);
        BiomeUtils.setBiomeBase(Biome.SAVANNA_PLATEAU, Biome.DESERT, 128);

        // LIMITED threshold biomes
        BiomeUtils.setBiomeBase(Biome.FOREST_HILLS, Biome.FOREST, 0);
        BiomeUtils.setBiomeBase(Biome.BIRCH_FOREST_HILLS, Biome.FOREST, 0);
        BiomeUtils.setBiomeBase(Biome.BIRCH_FOREST_HILLS, Biome.FOREST, 128);
        BiomeUtils.setBiomeBase(Biome.BIRCH_FOREST_HILLS_MOUNTAINS, Biome.FOREST, 0);
        BiomeUtils.setBiomeBase(Biome.BIRCH_FOREST_MOUNTAINS, Biome.FOREST, 0);
        BiomeUtils.setBiomeBase(Biome.TAIGA, Biome.BIRCH_FOREST, 0);
        BiomeUtils.setBiomeBase(Biome.TAIGA, Biome.BIRCH_FOREST, 128);
        BiomeUtils.setBiomeBase(Biome.TAIGA_HILLS, Biome.BIRCH_FOREST, 0);
        BiomeUtils.setBiomeBase(Biome.TAIGA_MOUNTAINS, Biome.BIRCH_FOREST, 0);
        BiomeUtils.setBiomeBase(Biome.ICE_PLAINS, Biome.BIRCH_FOREST, 0);
        BiomeUtils.setBiomeBase(Biome.ICE_PLAINS, Biome.BIRCH_FOREST, 128);
        BiomeUtils.setBiomeBase(Biome.ICE_PLAINS_SPIKES, Biome.BIRCH_FOREST, 0);
        BiomeUtils.setBiomeBase(Biome.MEGA_SPRUCE_TAIGA, Biome.PLAINS, 0);
        BiomeUtils.setBiomeBase(Biome.MEGA_SPRUCE_TAIGA_HILLS, Biome.PLAINS, 0);
        BiomeUtils.setBiomeBase(Biome.MEGA_TAIGA, Biome.PLAINS, 0);
        BiomeUtils.setBiomeBase(Biome.MEGA_TAIGA, Biome.PLAINS, 128);
        BiomeUtils.setBiomeBase(Biome.MEGA_TAIGA_HILLS, Biome.PLAINS, 0);
        BiomeUtils.setBiomeBase(Biome.COLD_BEACH, Biome.DESERT, 0);
        BiomeUtils.setBiomeBase(Biome.COLD_TAIGA, Biome.PLAINS, 0);
        BiomeUtils.setBiomeBase(Biome.COLD_TAIGA, Biome.PLAINS, 128);
        BiomeUtils.setBiomeBase(Biome.COLD_TAIGA_HILLS, Biome.DESERT, 0);
        BiomeUtils.setBiomeBase(Biome.COLD_TAIGA_MOUNTAINS, Biome.DESERT, 0);

        // DISALLOWED threshold biomes
        BiomeUtils.setBiomeBase(Biome.ROOFED_FOREST_MOUNTAINS, Biome.PLAINS, 0);
        BiomeUtils.setBiomeBase(Biome.MESA, Biome.PLAINS, 0);
        BiomeUtils.setBiomeBase(Biome.MESA, Biome.PLAINS, 128);
        BiomeUtils.setBiomeBase(Biome.MESA_PLATEAU, Biome.PLAINS, 0);
        BiomeUtils.setBiomeBase(Biome.MESA_PLATEAU, Biome.PLAINS, 128);
        BiomeUtils.setBiomeBase(Biome.MESA_BRYCE, Biome.PLAINS, 0);
        BiomeUtils.setBiomeBase(Biome.MESA_PLATEAU_FOREST, Biome.PLAINS, 0);
        BiomeUtils.setBiomeBase(Biome.MESA_PLATEAU_MOUNTAINS, Biome.PLAINS, 0);
        BiomeUtils.setBiomeBase(Biome.MESA_PLATEAU_FOREST_MOUNTAINS, Biome.PLAINS, 0);
        BiomeUtils.setBiomeBase(Biome.EXTREME_HILLS, Biome.PLAINS, 0);
        BiomeUtils.setBiomeBase(Biome.EXTREME_HILLS, Biome.DESERT, 128);
        BiomeUtils.setBiomeBase(Biome.EXTREME_HILLS_MOUNTAINS, Biome.PLAINS, 0);
        BiomeUtils.setBiomeBase(Biome.EXTREME_HILLS_PLUS, Biome.FOREST, 0);
        BiomeUtils.setBiomeBase(Biome.EXTREME_HILLS_PLUS, Biome.FOREST, 128);
        BiomeUtils.setBiomeBase(Biome.EXTREME_HILLS_PLUS_MOUNTAINS, Biome.FOREST, 0);
        BiomeUtils.setBiomeBase(Biome.FROZEN_OCEAN, Biome.PLAINS, 0);
        BiomeUtils.setBiomeBase(Biome.FROZEN_RIVER, Biome.PLAINS, 0);
        BiomeUtils.setBiomeBase(Biome.ICE_MOUNTAINS, Biome.PLAINS, 0);
    }

}
