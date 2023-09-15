package live.nerotv.projectsbase.modules.essentials.commands;

import live.nerotv.projectsbase.api.API;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Feed implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Feed")) {
            if(!(s instanceof Player)) {
                if(args.length == 0) {
                    s.sendMessage("§4Fehler: §c/feed [Spieler]");
                } else {
                    if(Bukkit.getPlayer(args[0]) != null) {
                        Player p = Bukkit.getPlayer(args[0]);
                        p.setFoodLevel(20);
                        API.sendMessage(p,"Du wurdest §agefüttert§7!");
                        s.sendMessage(API.Prefix+"Du hast den Spieler §e"+p.getName()+"§a gefüttert§7!");
                    } else {
                        s.sendMessage(API.noPlayer);
                    }
                }
            } else {
                Player p = (Player) s;
                if(s.hasPermission("zyneon.team")) {
                    if (args.length == 0) {
                        p.setFoodLevel(20);
                        API.sendMessage(p, "Du hast dich §agefüttert§7!");
                    } else {
                        if (Bukkit.getPlayer(args[0]) != null) {
                            Player t = Bukkit.getPlayer(args[0]);
                            t.setFoodLevel(20);
                            API.sendMessage(t, "Du wurdest §agefüttert§7!");
                            API.sendMessage(p, "Du hast den Spieler §e" + t.getName() + "§a gefüttert§7!");
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