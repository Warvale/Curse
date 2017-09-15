package net.warvale.uhcmeetup.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import net.warvale.uhcmeetup.UHCMeetup;
import net.warvale.uhcmeetup.commands.VanishCommand;
import net.warvale.uhcmeetup.managers.GameState;
import net.warvale.uhcmeetup.player.MeetupPlayer;
import net.warvale.uhcmeetup.player.PlayerManager;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player dead = e.getEntity().getPlayer();
        Player killer = e.getEntity().getKiller();

        MeetupPlayer meetupPlayer = PlayerManager.getInstance().getMeeupPlayer(dead.getUniqueId());
        MeetupPlayer kilerPlayer = PlayerManager.getInstance().getMeeupPlayer(killer.getUniqueId());

        kilerPlayer.addKill();
        meetupPlayer.addDeath();

        meetupPlayer.setState(MeetupPlayer.State.DEAD);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        if (UHCMeetup.getGame().isState(GameState.INGAME)) {
            e.setRespawnLocation(new Location(UHCMeetup.getGame().getWorld(), 0, 70, 0));
            Player player = e.getPlayer();

            MeetupPlayer meetupPlayer = PlayerManager.getInstance().getMeeupPlayer(player.getUniqueId());
            meetupPlayer.setState(MeetupPlayer.State.SPEC);
            VanishCommand.vanishPlayer(player);
        } else {
            e.setRespawnLocation(UHCMeetup.getInstance().getSpawn());
        }
    }

}
