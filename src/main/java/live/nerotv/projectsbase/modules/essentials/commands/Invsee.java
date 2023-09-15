package live.nerotv.projectsbase.modules.essentials.commands;

import live.nerotv.projectsbase.api.API;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Invsee implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Invsee")) {
            if(s instanceof Player) {
                Player p = (Player)s;
                if(p.hasPermission("zyneon.team")) {
                    if (args.length == 0) {
                        p.sendMessage("§4Fehler: §c/invsee [Spieler]");
                    } else {
                        if(Bukkit.getPlayer(args[0])!=null) {
                            Player t = Bukkit.getPlayer(args[0]);
                            p.openInventory(t.getInventory());
                            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG,100,100);
                        } else {
                            API.sendErrorMessage(p,API.noPlayer);
                        }
                    }
                } else {
                    API.sendErrorMessage(p,API.noPerms);
                }
            } else {
                API.sendErrorMessage(s,API.needPlayer);
            }
        }
        return false;
    }
}
