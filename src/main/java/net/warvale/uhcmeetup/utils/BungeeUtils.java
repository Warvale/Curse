package net.warvale.uhcmeetup.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import net.warvale.uhcmeetup.UHCMeetup;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class BungeeUtils {

    public static void sendWorldGenerationComplete() {
        try {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();

            out.writeUTF("Forward");
            out.writeUTF("ALL");
            out.writeUTF("UHCMeetup"); // "customchannel" for example
            out.writeUTF("GenerationComplete");
            out.writeUTF("nauhcm1"); //todo: make dynamic
            out.writeUTF(UHCMeetup.PREFIX);

            Bukkit.getServer().sendPluginMessage(UHCMeetup.getInstance(), "BungeeCord", out.toByteArray());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
