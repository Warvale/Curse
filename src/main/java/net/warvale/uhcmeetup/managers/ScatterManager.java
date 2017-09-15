package net.warvale.uhcmeetup.managers;

import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import net.warvale.uhcmeetup.UHCMeetup;
import net.warvale.uhcmeetup.player.MeetupPlayer;
import net.warvale.uhcmeetup.player.PlayerManager;
import net.warvale.uhcmeetup.scoreboards.LobbyScoreboard;
import net.warvale.uhcmeetup.tasks.GameTask;
import net.warvale.uhcmeetup.utils.ScatterUtils;
import net.warvale.uhcmeetup.utils.SoundUtils;

import java.util.ArrayList;
import java.util.List;

public class ScatterManager {

    private int i;
    private final List<Player> toScatter = new ArrayList<>();
    private int tpTime = 45;

    private static ScatterManager instance;

    public static ScatterManager getInstance() {
        if (instance == null) {
            instance = new ScatterManager();
        }
        return instance;
    }

    public void startScatter(CommandSender sender) {
        //update game state
        UHCMeetup.getGame().setState(GameState.STARTING);
        sender.sendMessage(ChatColor.GREEN + "Starting UHCMeetup Game...");

        for (MeetupPlayer meetupPlayer : PlayerManager.getInstance().getMeetupPlayers().values()) {
            if (meetupPlayer == null || meetupPlayer.getState().equals(MeetupPlayer.State.MOD)) continue;
            toScatter.add(meetupPlayer.getPlayer());
        }

        this.i = toScatter.size() - 1;

        Bukkit.getScheduler().runTaskLater(UHCMeetup.getInstance(), new Runnable(){
            @Override
            public void run() {
                ScatterManager.this.start();
            }
        }, 80);

    }

    public void startScatter() {
        //update game state
        UHCMeetup.getGame().setState(GameState.STARTING);

        for (MeetupPlayer meetupPlayer : PlayerManager.getInstance().getMeetupPlayers().values()) {
            if (meetupPlayer == null || meetupPlayer.getState().equals(MeetupPlayer.State.MOD)) continue;
            toScatter.add(meetupPlayer.getPlayer());
        }

        this.i = toScatter.size() - 1;

        Bukkit.getScheduler().runTaskLater(UHCMeetup.getInstance(), new Runnable(){
            @Override
            public void run() {
                ScatterManager.this.start();
            }
        }, 80);

    }

    public void start() {

        this.broadcastStartMessage(45);

        new BukkitRunnable() {

            @Override
            public void run() {
                tpTime--;

                if (tpTime > 0) {
                    switch (tpTime) {
                        case 40:
                            ScatterManager.this.broadcastStartMessage(tpTime);
                            break;
                        case 35:
                            ScatterManager.this.broadcastStartMessage(tpTime);
                            break;
                        case 30:
                            ScatterManager.this.broadcastStartMessage(tpTime);
                            break;
                        case 25:
                            ScatterManager.this.broadcastStartMessage(tpTime);
                            break;
                        case 20:
                            ScatterManager.this.broadcastStartMessage(tpTime);
                            break;
                        case 15:
                            ScatterManager.this.broadcastStartMessage(tpTime);
                            break;
                    }

                    if (tpTime <= 10) {
                        ScatterManager.this.broadcastStartMessage(tpTime);
                    }

                    try {
                        ScatterManager.this.toScatter.get(ScatterManager.this.i);
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        try {
                            ScatterManager.this.toScatter.remove(ScatterManager.this.i);
                        } catch (ArrayIndexOutOfBoundsException err) {
                            System.out.println("Null remove player scatter.");
                        }
                    }

                    try {
                        Player player = ScatterManager.this.toScatter.get(ScatterManager.this.i);
                        if (player != null) {
                            if (player.getWorld().equals(UHCMeetup.getInstance().getSpawn().getWorld())) {
                                PotionEffect potionEffect = new PotionEffect(PotionEffectType.BLINDNESS, 99999, 1);
                                player.addPotionEffect(potionEffect);
                                potionEffect = new PotionEffect(PotionEffectType.SLOW, 99999, 10);
                                player.addPotionEffect(potionEffect);
                                potionEffect = new PotionEffect(PotionEffectType.JUMP, 99999, 200);
                                player.addPotionEffect(potionEffect);

                                player.setHealth(20);
                                player.setFoodLevel(20);

                                player.getInventory().clear();
                                player.getInventory().setArmorContents(null);
                                player.getInventory().setHelmet(new ItemStack(Material.AIR));
                                player.getInventory().setChestplate(new ItemStack(Material.AIR));
                                player.getInventory().setLeggings(new ItemStack(Material.AIR));
                                player.getInventory().setBoots(new ItemStack(Material.AIR));


                                player.setExp(0);
                                player.setLevel(0);
                                player.setFireTicks(0);
                                player.setGameMode(GameMode.SURVIVAL);
                                ScatterUtils.scatterPlayerRandom(player, UHCMeetup.getGame().getCurrentBorder());
                                KitManager.getInstance().randomizeKit(player);
                            } else {
                                ScatterManager.this.toScatter.remove(ScatterManager.this.i);
                            }
                            ScatterManager.this.i--;
                        }
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        System.out.println("Null player scatter.");
                    }
                } else {
                    this.cancel();

                    UHCMeetup.getGame().getWorld().setPVP(true);
                    UHCMeetup.getGame().setPVP(true);

                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        if (player == null) continue;
                        player.getActivePotionEffects().clear();

                        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                            player.removePotionEffect(potionEffect.getType());
                        }
                    }

                    LobbyScoreboard.getInstance().getScoreboards().clear();

                    UHCMeetup.getGame().setState(GameState.INGAME);
                    new GameTask().runTaskTimer(UHCMeetup.getInstance(), 20, 20);
                    Bukkit.broadcastMessage(UHCMeetup.PREFIX + ChatColor.GOLD + "The game has started!");
                    Bukkit.broadcastMessage(UHCMeetup.PREFIX + ChatColor.GOLD + "The border will shrink in 3 minutes!");
                }
            }

        }.runTaskTimer(UHCMeetup.getInstance(), 20, 20);

    }

    public void broadcastStartMessage(int seconds) {
        Bukkit.broadcastMessage(UHCMeetup.PREFIX + ChatColor.GOLD + "Starting in " + ChatColor.AQUA + seconds + ChatColor.GOLD + " seconds!");
        SoundUtils.playSound(Sound.NOTE_STICKS);
    }


}
