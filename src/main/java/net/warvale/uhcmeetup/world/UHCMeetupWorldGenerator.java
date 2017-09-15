package net.warvale.uhcmeetup.world;

import net.warvale.uhcmeetup.UHCMeetup;
import net.warvale.uhcmeetup.managers.GameState;
import net.warvale.uhcmeetup.tasks.LobbyScoreboardTask;
import net.warvale.uhcmeetup.utils.BungeeUtils;
import net.warvale.worldborder.WorldFillerTaskCompleteEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

public class UHCMeetupWorldGenerator extends BukkitRunnable implements Listener {

	private World world;

	private boolean isGenerating = false;

	public UHCMeetupWorldGenerator() {
		// Fail-safe
		this.deleteDirectory(new File(UHCMeetup.getGame().getWorldName()));

		// Register listener
		UHCMeetup.getInstance().getServer().getPluginManager().registerEvents(this, UHCMeetup.getInstance());
	}

	@EventHandler
	public void onWorldFinishGeneration(WorldFillerTaskCompleteEvent event) {
		// Add the glass walls
		this.addGlassBorder();

		//set world rules
		World world = UHCMeetup.getGame().getWorld();
		world.setGameRuleValue("doDaylightCycle", "false");
		world.setGameRuleValue("doMobSpawning", "false");
		world.setGameRuleValue("naturalRegeneration", "false");

		//set worldborder
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "worldborder:wb whoosh off");
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "worldborder:wb denypearl on");
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "worldborder:wb " + UHCMeetup.getGame().getWorldName() + " setcorners " + UHCMeetup.getGame().getCurrentBorder() + " -" + UHCMeetup.getGame().getCurrentBorder() + " -" + UHCMeetup.getGame().getCurrentBorder() + " " + UHCMeetup.getGame().getCurrentBorder());
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "worldborder:wb shape square");
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "worldborder:wb knockback 2");
		UHCMeetup.getGame().setState(GameState.LOBBY);

		//start the scoreboard task
		new LobbyScoreboardTask().runTaskTimer(UHCMeetup.getInstance(), 20, 20);

		//send a plugin message so our lobby servers can announce the game
		BungeeUtils.sendWorldGenerationComplete();
	}

	@Override
	public void run() {
		// Don't let one start if one is generating already
		if (this.isGenerating) {
			return;
		}

		this.generateNewWorld();
	}

	private void generateNewWorld() {
		// Just to avoid any weird bukkit race conditions
		this.isGenerating = true;
		UHCMeetup.getGame().setState(GameState.WORLD_GENERATION);

		WorldCreator worldCreator = new WorldCreator(UHCMeetup.getGame().getWorldName());

		// Another fail safe
		try {
			this.world = UHCMeetup.getInstance().getServer().createWorld(worldCreator);
		} catch (Exception e) {
			Bukkit.getLogger().info("World NPE when trying to generate map.");
			Bukkit.getServer().unloadWorld(this.world, false);

			this.deleteDirectory(new File(UHCMeetup.getGame().getWorldName()));

			this.isGenerating = false;
			return;
		}

		int waterCount = 0;

		Bukkit.getLogger().info("Loaded a new world.");
		boolean flag = false;
		for (int i = -UHCMeetup.getGame().getCurrentBorder(); i <=UHCMeetup.getGame().getCurrentBorder(); ++i) {
			boolean isInvalid = false;
			for (int j = -UHCMeetup.getGame().getCurrentBorder(); j <= UHCMeetup.getGame().getCurrentBorder(); j++) {
				boolean isCenter = i >= -100 && i <= 100 && j >= -100 && j <= 100;
				if (isCenter) {
					Block block = this.world.getHighestBlockAt(i, j).getLocation().add(0, -1, 0).getBlock();
					if (block.getType() == Material.STATIONARY_WATER || block.getType() == Material.WATER || block.getType() == Material.LAVA || block.getType() == Material.STATIONARY_LAVA) {
						++waterCount;
					}
				}

				if (waterCount >= 1000) {
					Bukkit.getLogger().info("Invalid center, too much water/lava.");
					isInvalid = true;
					break;
				}
			}

			if (isInvalid) {
				flag = true;
				Bukkit.getLogger().info("Invalid biome2");
				break;
			}
		}

		// TODO: TESTING
		//if (flag) flag = false;

		// Actually got this far...we have a valid world, generate the rest
		if (flag) {
			Bukkit.getLogger().info("Failed to find a good seed (" + this.world.getSeed() + ").");
			Bukkit.getServer().unloadWorld(this.world, false);

			this.deleteDirectory(new File(UHCMeetup.getGame().getWorldName()));

			this.isGenerating = false;
			return;
		} else {
			Bukkit.getLogger().info("Found a good seed (" + this.world.getSeed() + ").");
			this.cancel();
		}

		// Create Lock
		File lock = new File(UHCMeetup.getGame().getWorldName() + "/gen.lock");
		try {
			lock.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
			return;
		}

		this.isGenerating = true;

		// Start the worldborder stuff now
		UHCMeetup.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(), "wb shape square");
		UHCMeetup.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(), "wb " + UHCMeetup.getGame().getWorldName() + " set " + UHCMeetup.getGame().getCurrentBorder() + " 0 0");
		UHCMeetup.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(), "wb " + UHCMeetup.getGame().getWorldName() + " fill 5000");
		UHCMeetup.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(), "wb fill confirm");
	}

	private boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}

	private void addGlassBorder() {
		new BukkitRunnable() {

			private World world = Bukkit.getServer().getWorld(UHCMeetup.getGame().getWorldName());

			private int counter = -UHCMeetup.getGame().getCurrentBorder() - 1;
			private boolean phase1 = false;
			private boolean phase2 = false;
			private boolean phase3 = false;

			@Override
			public void run() {
				if (!this.phase1) {
					int maxCounter = this.counter + 500;
					int x = -UHCMeetup.getGame().getCurrentBorder() - 1;
					for (int z = this.counter; z <= UHCMeetup.getGame().getCurrentBorder() && this.counter <= maxCounter; z++, this.counter++) {
						Block highestBlock = this.world.getHighestBlockAt(x, z);

						// Ignore non-solid blocks
						while (!highestBlock.getType().isSolid()
								|| highestBlock.getType() == Material.LEAVES || highestBlock.getType() == Material.LEAVES_2) {
							highestBlock = highestBlock.getRelative(0, -1, 0);
						}

						int y = highestBlock.getY() + 1;
						for (int i = y; i < 200; i++) {
							Block block = this.world.getBlockAt(x, i, z);

							block.setType(Material.GLASS);
							block.setData((byte) 0);
						}
					}

					if (this.counter >= UHCMeetup.getGame().getCurrentBorder()) {
						this.counter = -UHCMeetup.getGame().getCurrentBorder() - 1;
						this.phase1 = true;
					}

					return;
				}

				if (!this.phase2) {
					int maxCounter = this.counter + 500;
					int x = UHCMeetup.getGame().getCurrentBorder();
					for (int z = this.counter; z <= UHCMeetup.getGame().getCurrentBorder() && this.counter <= maxCounter; z++, this.counter++) {
						Block highestBlock = this.world.getHighestBlockAt(x, z);

						// Ignore non-solid blocks
						while (!highestBlock.getType().isSolid()
								|| highestBlock.getType() == Material.LEAVES || highestBlock.getType() == Material.LEAVES_2) {
							highestBlock = highestBlock.getRelative(0, -1, 0);
						}

						int y = highestBlock.getY() + 1;
						for (int i = y; i < 200; i++) {
							Block block = this.world.getBlockAt(x, i, z);

							block.setType(Material.GLASS);
							block.setData((byte) 0);
						}
					}

					if (this.counter >= UHCMeetup.getGame().getCurrentBorder()) {
						this.counter = -UHCMeetup.getGame().getCurrentBorder() - 1;
						this.phase2 = true;
					}

					return;
				}

				if (!this.phase3) {
					int maxCounter = this.counter + 500;
					int z = -UHCMeetup.getGame().getCurrentBorder() - 1;
					for (int x = this.counter; x <= UHCMeetup.getGame().getCurrentBorder() && this.counter <= maxCounter; x++, this.counter++) {
						if (x == UHCMeetup.getGame().getCurrentBorder() || x == -UHCMeetup.getGame().getCurrentBorder() - 1) {
							continue;
						}

						Block highestBlock = this.world.getHighestBlockAt(x, z);

						// Ignore non-solid blocks
						while (!highestBlock.getType().isSolid()
								|| highestBlock.getType() == Material.LEAVES || highestBlock.getType() == Material.LEAVES_2) {
							highestBlock = highestBlock.getRelative(0, -1, 0);
						}

						int y = highestBlock.getY() + 1;
						for (int i = y; i < 200; i++) {
							Block block = this.world.getBlockAt(x, i, z);

							block.setType(Material.GLASS);
							block.setData((byte) 0);
						}
					}

					if (this.counter >= UHCMeetup.getGame().getCurrentBorder()) {
						this.counter = -UHCMeetup.getGame().getCurrentBorder() - 1;
						this.phase3 = true;
					}

					return;
				}


				int maxCounter = this.counter + 500;
				int z = UHCMeetup.getGame().getCurrentBorder();
				for (int x = this.counter; x <= UHCMeetup.getGame().getCurrentBorder() && this.counter <= maxCounter; x++, this.counter++) {
					if (x == UHCMeetup.getGame().getCurrentBorder() || x == -UHCMeetup.getGame().getCurrentBorder() - 1) {
						continue;
					}

					Block highestBlock = this.world.getHighestBlockAt(x, z);

					// Ignore non-solid blocks
					while (!highestBlock.getType().isSolid()
							|| highestBlock.getType() == Material.LEAVES || highestBlock.getType() == Material.LEAVES_2) {
						highestBlock = highestBlock.getRelative(0, -1, 0);
					}

					int y = highestBlock.getY() + 1;
					for (int i = y; i < 200; i++) {
						Block block = this.world.getBlockAt(x, i, z);

						block.setType(Material.GLASS);
						block.setData((byte) 0);
					}
				}

				if (this.counter >= UHCMeetup.getGame().getCurrentBorder()) {
					this.cancel();
				}
			}
		}.runTaskTimer(UHCMeetup.getInstance(), 0L, 1L);
	}


}
