package live.nerotv.projectsbase.modules.roleplay.commands;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.api.ConfigAPI;
import live.nerotv.projectsbase.modules.roleplay.manager.InventoryManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Settings implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Settings")) {
            if(!(s instanceof Player)) {
                if(args.length >= 3) {
                    if(args[0].equalsIgnoreCase("Config")) {
                        String c="";
                        for(int i=2;i<args.length;i++) {
                            c=c+args[i]+" ";
                        }
                        c = c.substring(0,c.length()-1);
                        if(c.equalsIgnoreCase("false")) {
                            ConfigAPI.CFG.set(args[1],false);
                        } else if(c.equalsIgnoreCase("true")) {
                            ConfigAPI.CFG.set(args[1],true);
                        } else {
                            ConfigAPI.CFG.set(args[1],c);
                        }
                        ConfigAPI.saveConfig(ConfigAPI.Config,ConfigAPI.CFG);
                        ConfigAPI.reloadConfig(ConfigAPI.Config,ConfigAPI.CFG);
                        s.sendMessage(API.Prefix+"§7Du hast den Config-Wert des Pfades §e"+args[1]+"§7 auf §e"+c+" §7gesetzt!");
                    } else {
                        s.sendMessage("§4Fehler: §c/settings config [Pfad] [Wert]");
                    }
                } else {
                    s.sendMessage("§4Fehler: §c/settings config [Pfad] [Wert]");
                }
            } else {
                Player p = (Player)s;
                if(args.length >= 3) {
                    if(args[0].equalsIgnoreCase("config")) {
                        if(p.hasPermission("zyneon.leading")) {
                            String c="";
                            for(int i=2;i<args.length;i++) {
                                c=c+args[i]+" ";
                            }
                            c = c.substring(0,c.length()-1);
                            if(c.equalsIgnoreCase("false")) {
                                ConfigAPI.CFG.set(args[1],false);
                            } else if(c.equalsIgnoreCase("true")) {
                                ConfigAPI.CFG.set(args[1],true);
                            } else {
                                ConfigAPI.CFG.set(args[1],c);
                            }
                            ConfigAPI.saveConfig(ConfigAPI.Config,ConfigAPI.CFG);
                            ConfigAPI.reloadConfig(ConfigAPI.Config,ConfigAPI.CFG);
                            API.sendMessage(p,"Du hast den Config-Wert des Pfades §e"+args[1]+"§7 auf §e"+c+" §7gesetzt!");
                        } else {
                            p.performCommand("settings");
                        }
                    } else {
                        p.performCommand("settings");
                    }
                } else {

                    InventoryManager.openSettingsInventory(p);

                }
            }
        }
        return false;
    }
}