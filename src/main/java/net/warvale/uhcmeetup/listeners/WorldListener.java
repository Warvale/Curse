package net.warvale.uhcmeetup.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import net.warvale.uhcmeetup.UHCMeetup;
import net.warvale.uhcmeetup.managers.GameState;
import net.warvale.uhcmeetup.player.MeetupPlayer;
import net.warvale.uhcmeetup.player.PlayerManager;

public class WorldListener implements Listener {

    @EventHandler
    public void onWeather(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent e) {

        if (UHCMeetup.getGame().isState(GameState.LOBBY) || UHCMeetup.getGame().isState(GameState.STARTING)) {
                e.setCancelled(true);
        }

        if (UHCMeetup.getGame().isState(GameState.INGAME)) {
            for (MeetupPlayer meetupPlayer : PlayerManager.getInstance().getSpectators()) {
                if (meetupPlayer.getUUID().equals(e.getPlayer().getUniqueId())) {
                    e.setCancelled(true);
                }
            }
        }

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockCanBuild(BlockCanBuildEvent e) {

        if (UHCMeetup.getGame().isState(GameState.LOBBY) || UHCMeetup.getGame().isState(GameState.STARTING)) {
            e.setBuildable(false);
        }

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent e) {

        if (UHCMeetup.getGame().isState(GameState.LOBBY) || UHCMeetup.getGame().isState(GameState.STARTING)) {
            e.setCancelled(true);
        }

        if (UHCMeetup.getGame().isState(GameState.INGAME)) {
            for (MeetupPlayer meetupPlayer : PlayerManager.getInstance().getSpectators()) {
                if (meetupPlayer.getUUID().equals(e.getPlayer().getUniqueId())) {
                    e.setCancelled(true);
                }
            }
        }

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlaceMultiple(BlockMultiPlaceEvent e) {

        if (UHCMeetup.getGame().isState(GameState.LOBBY) || UHCMeetup.getGame().isState(GameState.STARTING)) {
            e.setCancelled(true);
        }

        if (UHCMeetup.getGame().isState(GameState.INGAME)) {
            for (MeetupPlayer meetupPlayer : PlayerManager.getInstance().getSpectators()) {
                if (meetupPlayer.getUUID().equals(e.getPlayer().getUniqueId())) {
                    e.setCancelled(true);
                }
            }
        }

    }

}
