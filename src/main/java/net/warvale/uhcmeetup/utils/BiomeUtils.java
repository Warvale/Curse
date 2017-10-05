package net.warvale.uhcmeetup.utils;

import net.minecraft.server.v1_8_R3.BiomeBase;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock;


public class BiomeUtils {

    public static void setBiomeBase(Biome from, Biome to, int plus)
    {
        BiomeBase[] biomes = BiomeBase.getBiomes();

        BiomeBase fromBB = CraftBlock.biomeToBiomeBase(from);
        BiomeBase toBB = CraftBlock.biomeToBiomeBase(to);

        biomes[(fromBB.id + plus)] = toBB;
    }

}
