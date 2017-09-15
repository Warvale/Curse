package net.warvale.uhcmeetup.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import net.warvale.uhcmeetup.UHCMeetup;
import net.warvale.uhcmeetup.managers.GameState;

public class LobbyListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockDamage(BlockDamageEvent e) {
        if (!UHCMeetup.getGame().isState(GameState.LOBBY)) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClick(InventoryClickEvent e) {

        if (!UHCMeetup.getGame().isState(GameState.LOBBY)) {
            return;
        }

        if (!e.getWhoClicked().hasPermission("uhcmeetup.lobbyprotect.bypass")) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onItemDrop(PlayerDropItemEvent e) {

        if (!UHCMeetup.getGame().isState(GameState.LOBBY)) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onItemPickup(PlayerPickupItemEvent e) {

        if (!UHCMeetup.getGame().isState(GameState.LOBBY)) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDamage(EntityDamageEvent e) {

        if (!UHCMeetup.getGame().isState(GameState.LOBBY)) {
            return;
        }

        if (e.getEntity() instanceof Player) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent e) {

        if (!UHCMeetup.getGame().isState(GameState.LOBBY)) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerFoodLevelChange(FoodLevelChangeEvent e) {

        if (!UHCMeetup.getGame().isState(GameState.LOBBY)) {
            return;
        }

        e.setCancelled(true);
    }

    public void onPlayerMove(PlayerMoveEvent e) {
        if (UHCMeetup.getGame().isState(GameState.LOBBY) && e.getTo().getBlockY() < 0) {
            e.getPlayer().teleport(UHCMeetup.getInstance().getLobby().getLoc());
        }

    }

}
