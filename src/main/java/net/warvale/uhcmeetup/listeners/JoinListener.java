package net.warvale.uhcmeetup.listeners;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import net.warvale.uhcmeetup.UHCMeetup;
import net.warvale.uhcmeetup.commands.VanishCommand;
import net.warvale.uhcmeetup.managers.GameState;
import net.warvale.uhcmeetup.player.MeetupPlayer;
import net.warvale.uhcmeetup.player.PlayerManager;
import net.warvale.uhcmeetup.scoreboards.GameScoreboard;
import net.warvale.uhcmeetup.scoreboards.LobbyScoreboard;
import net.warvale.uhcmeetup.utils.SoundUtils;

public class JoinListener implements Listener {

    @EventHandler
    public void onPreJoin(AsyncPlayerPreLoginEvent e) {
        if (UHCMeetup.getGame().isState(GameState.WORLD_GENERATION)) {
            e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            e.setKickMessage(UHCMeetup.PREFIX + "You can not join while the world is generating.");
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Player player = e.getPlayer();

        //todo: game states

        if (UHCMeetup.getGame().isState(GameState.LOBBY)) {
            //tp them to the lobby world
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.getInventory().setHelmet(new ItemStack(Material.AIR));
            player.getInventory().setChestplate(new ItemStack(Material.AIR));
            player.getInventory().setLeggings(new ItemStack(Material.AIR));
            player.getInventory().setBoots(new ItemStack(Material.AIR));
            player.setGameMode(GameMode.ADVENTURE);
            player.teleport(UHCMeetup.getInstance().getSpawn());

            //show the lobby scoreboard
            new LobbyScoreboard().newScoreboard(player);

            int minPlayers = UHCMeetup.getGame().getMinPlayers() - Bukkit.getServer().getOnlinePlayers().size();
            SoundUtils.playSound(Sound.NOTE_STICKS);
            Bukkit.broadcastMessage(UHCMeetup.PREFIX + ChatColor.RED + String.valueOf(minPlayers) + ChatColor.DARK_GREEN + " more players needed to start the game!");

            if (!PlayerManager.getInstance().doesMeetupPlayerExsists(player.getUniqueId())) {
                PlayerManager.getInstance().createMeetupPlayer(player.getUniqueId(), MeetupPlayer.State.PLAYER);
            }
        }

        if (UHCMeetup.getGame().isState(GameState.INGAME)) {
            if (!PlayerManager.getInstance().doesMeetupPlayerExsists(player.getUniqueId())) {
                PlayerManager.getInstance().createMeetupPlayer(player.getUniqueId(), MeetupPlayer.State.SPEC);
            }

            //show the game scoreboard
            new GameScoreboard().newScoreboard(player);

            //vanish them
            VanishCommand.vanishPlayer(player);
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        this.processLeave(e.getPlayer());
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        this.processLeave(e.getPlayer());
    }

    private void processLeave(Player player) {
        if (PlayerManager.getInstance().doesMeetupPlayerExsists(player.getUniqueId())) {
            PlayerManager.getInstance().removeMeetupPlayer(player.getUniqueId());
        }
    }

}
