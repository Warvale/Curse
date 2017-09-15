package net.warvale.uhcmeetup.schematics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.world.DataException;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;

@SuppressWarnings("deprecation")
public class SchematicHandler {

	public static ArrayList<Integer> doPaste(Location loc, String path) throws MaxChangedBlocksException, DataException, IOException{
        ArrayList<Integer> dimensions = new ArrayList<>();

        WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
        Bukkit.getLogger().info("Pasting "+path);
        File schematic = new File(path);

        EditSession editSession = new EditSession(new BukkitWorld(loc.getWorld()), 999999999);
        editSession.enableQueue();

        CuboidClipboard clipboard = MCEditSchematicFormat.getFormat(schematic).load(schematic);

        clipboard.paste(editSession, BukkitUtil.toVector(loc), true);
        editSession.flushQueue();

        dimensions.add(clipboard.getHeight());
        dimensions.add(clipboard.getLength());
        dimensions.add(clipboard.getWidth());

        Bukkit.getLogger().info("Successfully pasted '"+path+"' at "+loc.getBlockX()+" "+loc.getBlockY()+" "+loc.getBlockZ());

        return dimensions;
    }
}
