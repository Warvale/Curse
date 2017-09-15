package net.warvale.uhcmeetup.message;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class MessageManager {

    private static MessageManager instance;
    private static HashMap<PrefixType, String> prefix = new HashMap<>();
    public static final String NO_PERMISSION_MESSAGE = "§cYou don't have permission.";

    public static MessageManager getInstance() {
        if (instance == null) {
            instance = new MessageManager();
        }
        return instance;
    }

    public void setup() {
        prefix.put(PrefixType.MAIN, ChatColor.GOLD + "" + ChatColor.BOLD + "[" + ChatColor.DARK_AQUA + "UHCMeetup" + ChatColor.GOLD + "" + ChatColor.BOLD + "]");
        prefix.put(PrefixType.ARROW, ChatColor.DARK_GRAY + "»");
        prefix.put(PrefixType.PERMISSIONS, ChatColor.GOLD + "Permissions" + " " + ChatColor.DARK_GRAY + "»");
    }

    public static String getPrefix(PrefixType type) {
        return prefix.get(type) + " " + ChatColor.RESET;
    }

    /**
     * Broadcasts a message to everyone online.
     *
     * @param message the message.
     */
    public static void broadcast(String message) {
        broadcast(message, null);
    }

    /**
     * Broadcasts a message to everyone online with a specific permission.
     *
     * @param message the message.
     * @param permission the permission.
     */
    public static void broadcast(String message, String permission) {
        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            if (permission != null && !online.hasPermission(permission)) {
                continue;
            }

            online.sendMessage(message);
        }

        message = message.replaceAll("§l", "");
        message = message.replaceAll("§o", "");
        message = message.replaceAll("§r", "§f");
        message = message.replaceAll("§m", "");
        message = message.replaceAll("§n", "");

        Bukkit.getLogger().info(message);
    }

}
