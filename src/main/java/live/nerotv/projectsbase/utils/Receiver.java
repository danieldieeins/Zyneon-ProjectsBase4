package live.nerotv.projectsbase.utils;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import live.nerotv.projectsbase.api.ServerAPI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class Receiver implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player ignore, byte[] bytes) {
        if(!channel.equalsIgnoreCase("base:bungee")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        String subChannel = in.readUTF();
        if (subChannel.equalsIgnoreCase("spigot")) {
            String data1 = in.readUTF();
            int data2 = in.readInt();
        } else if(subChannel.contains("stopServer")) {
            ServerAPI.scheduledShutdown();
        } else if(subChannel.contains("playSound_")) {
            String data1 = in.readUTF();
            int data2 = in.readInt();
            if(Bukkit.getPlayer(data1)!=null) {
                String nS = subChannel.replace("playSound_","");
                Player p = Bukkit.getPlayer(data1);
                p.playSound(p.getLocation(), Sound.valueOf(nS),100,100);
            }
        }
    }
}