package live.nerotv.projectsbase.modules.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Say implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Say")) {
            if(!(s instanceof Player)) {
                if(args.length == 0) {
                    s.sendMessage("§4Fehler: §c/say [Nachricht]");
                } else {
                    String m="";
                    for(int i=0;i<args.length;i++) {
                        m=m+args[i]+" ";
                    }
                    Bukkit.getServer().broadcastMessage("§cKonsole§8: §7"+m.replace("&","§"));
                }
            } else {
                Player p = (Player)s;
                p.performCommand("neino");
            }
        }
        return false;
    }
}