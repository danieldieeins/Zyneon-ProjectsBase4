package live.nerotv.projectsbase.modules.essentials.commands;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.api.WorldAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Difficulty implements CommandExecutor {

    private void sendSyntax(CommandSender s) {
        if(!(s instanceof Player)) {
            API.sendErrorMessage(s,"§4Fehler: §c/difficulty [Schwierigkeit] [Welt]");
        } else {
            API.sendErrorMessage(s,"§4Fehler: §c/difficulty [Schwierigkeit] §7[Welt]");
        }
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Difficulty")) {
            if(s instanceof Player) {
                if(s.hasPermission("zyneon.team")) {
                    Player p = (Player)s;
                    if(args.length > 0) {
                        if(args.length == 1) {
                            if(WorldAPI.resolveDifficulty(args[0])!=null) {
                                WorldAPI.setDifficulty(p.getWorld(),args[0]);
                                API.sendMessage(p,"§7Du hast die Schwierigkeit von §a"+p.getWorld().getName()+"§7 auf §e"+args[0]+"§7 gesetzt§8!");
                            } else {
                                API.sendErrorMessage(p,"§cDiese Schwierigkeitsstufe existiert nicht§8!");
                                sendSyntax(p);
                            }
                        } else {
                            if(WorldAPI.resolveDifficulty(args[0])!=null) {
                                if (Bukkit.getWorld(args[1])!=null) {
                                    WorldAPI.setDifficulty(args[1],args[0]);
                                    API.sendMessage(p,"§7Du hast die Schwierigkeit von §a"+args[1]+"§7 auf §e"+args[0]+"§7 gesetzt§8!");
                                } else {
                                    API.sendErrorMessage(p,"§cDiese Welt existiert nicht§8!");
                                    sendSyntax(p);
                                }
                            } else {
                                API.sendErrorMessage(p,"§cDiese Schwierigkeitsstufe existiert nicht§8!");
                                sendSyntax(p);
                            }
                        }
                    } else {
                        API.sendMessage(p,"§7Die Schwierigkeit von §a"+p.getWorld().getName()+"§7 steht auf§8: §e"+p.getWorld().getDifficulty());
                        API.sendMessage(p,"§7Um sie zu ändern mache§8: §e/difficulty [Schwierigkeit] §7[Welt]");
                    }
                } else {
                    API.sendErrorMessage(s,API.noPerms);
                }
            } else {
                if(args.length >= 2) {
                    if(WorldAPI.resolveDifficulty(args[0])!=null) {
                        if(Bukkit.getWorld(args[1])!=null) {
                            WorldAPI.setDifficulty(args[1],args[0]);
                            API.sendMessage(s,"§7Du hast die Schwierigkeit von §a"+args[1]+"§7 auf §e"+args[0]+"§7 gesetzt§8!");
                        } else {
                            API.sendErrorMessage(s,"§cDiese Welt existiert nicht§8!");
                            sendSyntax(s);
                        }
                    } else {
                        API.sendErrorMessage(s,"§cDiese Schwierigkeitsstufe existiert nicht§8!");
                        sendSyntax(s);
                    }
                } else {
                    API.sendErrorMessage(s,"§cZu wenig Argumente§8!");
                    sendSyntax(s);
                }
            }
        }
        return false;
    }
}