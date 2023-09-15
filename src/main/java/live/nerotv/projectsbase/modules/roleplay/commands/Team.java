package live.nerotv.projectsbase.modules.roleplay.commands;

import live.nerotv.projectsbase.Main;
import live.nerotv.projectsbase.api.API;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Team implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Team")) {
            if(s.hasPermission("zyneon.team")) {
                if (args.length == 0) {
                    Bukkit.dispatchCommand(s, "help team 1");
                } else {
                    if(Main.PM.getPlugin("LuckPerms")!=null) {
                        if (args[0].equalsIgnoreCase("on")) {
                            if (s instanceof Player) {
                                API.addBypassPerms((Player) s);
                                API.sendMessage(s, "Du hast die Sicherungsumgehungen §aaktiviert§7!");
                            } else {
                                Bukkit.dispatchCommand(s, "help team " + args[0]);
                            }
                        } else if (args[0].equalsIgnoreCase("off")) {
                            if (s instanceof Player) {
                                API.removeBypassPerms((Player) s);
                                API.sendMessage(s, "Du hast die Sicherungsumgehungen §cdeaktiviert§7!");
                            } else {
                                Bukkit.dispatchCommand(s, "help team " + args[0]);
                            }
                        } else {
                            Bukkit.dispatchCommand(s, "help team " + args[0]);
                        }
                    } else {
                        API.sendErrorMessage(s,"§cDieses Feature ist zurzeit deaktiviert!");
                    }
                }
            } else {
                Bukkit.dispatchCommand(s,"help");
            }
        }
        return false;
    }
}