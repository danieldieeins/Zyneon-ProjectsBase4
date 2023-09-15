package live.nerotv.projectsbase.modules.essentials.commands;

import live.nerotv.projectsbase.api.API;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Broadcast implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Broadcast")) {
            if(!(s instanceof Player)) {
                if(args.length == 0) {
                    s.sendMessage("§4Fehler: §c/broadcast [Nachricht]");
                } else {
                    String m="";
                    for(int i=0;i<args.length;i++) {
                        m=m+args[i]+" ";
                    }
                    for(Player all:Bukkit.getOnlinePlayers()) {
                        all.playSound(all.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
                        all.sendMessage("§cWichtig §8| §f"+m.replace("&","§"));
                    }
                    Bukkit.getConsoleSender().sendMessage("§cWichtig §8| §f"+m.replace("&","§"));
                }
            } else {
                Player p = (Player)s;
                if(p.hasPermission("zyneon.team")) {
                    if(args.length == 0) {
                        p.sendMessage("§4Fehler: §c/broadcast [Nachricht]");
                        p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
                    } else {
                        String m="";
                        for(int i=0;i<args.length;i++) {
                            m=m+args[i]+" ";
                        }
                        for(Player all:Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
                            all.sendMessage("§cWichtig §8| §f"+m.replace("&","§"));
                        }
                        Bukkit.getConsoleSender().sendMessage("§cWichtig §8| §f"+m.replace("&","§"));
                    }
                } else {
                    API.sendErrorMessage(p,API.noPerms);
                }
            }
        }
        return false;
    }
}