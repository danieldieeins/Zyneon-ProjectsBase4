package live.nerotv.projectsbase.modules.essentials.commands;

import live.nerotv.projectsbase.api.API;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Kill implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Kill")) {
            if(!(s instanceof Player)) {
                if(args.length == 0) {
                    s.sendMessage("§4Fehler: §c/kill [Spieler]");
                } else {
                    if(Bukkit.getPlayer(args[0]) != null) {
                        Player p = Bukkit.getPlayer(args[0]);
                        p.setHealth(0);
                        p.setFoodLevel(0);
                        p.setFireTicks(0);
                        s.sendMessage(API.Prefix+"Du hast den Spieler §e"+p.getName()+"§c getötet§7!");
                    } else {
                        s.sendMessage(API.noPlayer);
                    }
                }
            } else {
                Player p = (Player) s;
                if(s.hasPermission("zyneon.team")) {
                    if (args.length == 0) {
                        p.setHealth(0);
                        p.setFoodLevel(0);
                        p.setFireTicks(0);
                    } else {
                        if (Bukkit.getPlayer(args[0]) != null) {
                            Player t = Bukkit.getPlayer(args[0]);
                            t.setHealth(0);
                            t.setFoodLevel(0);
                            t.setFireTicks(0);
                            API.sendMessage(p, "Du hast den Spieler §e" + t.getName() + "§c getötet§7!");
                        } else {
                            API.sendErrorMessage(p,API.noPlayer);
                        }
                    }
                } else {
                    API.sendErrorMessage(p,API.noPerms);
                }
            }
        }
        return false;
    }
}