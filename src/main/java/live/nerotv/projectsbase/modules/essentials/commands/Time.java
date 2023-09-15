package live.nerotv.projectsbase.modules.essentials.commands;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.api.WorldAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Time implements CommandExecutor {

    private void sendSyntax(CommandSender s) {
        if(!(s instanceof Player)) {
            s.sendMessage("§4Fehler: §c/time [set/add/remove] [Wert] [Welt]");
        } else {
            API.sendErrorMessage(s,"§4Fehler: §c/time [set/add/remove] [Wert] §7[Welt]");
        }
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Time")) {
            if(!(s instanceof Player)) {
                if(args.length < 3) {
                    s.sendMessage("§4Fehler: §c/time [set/add/remove] [Wert] [Welt]");
                } else {
                    if (Bukkit.getWorld(args[2]) != null) {
                        World world = Bukkit.getWorld(args[2]);
                        if (args[0].equalsIgnoreCase("set")) {
                            if (args[1].equalsIgnoreCase("day")) {
                                world.setTime(0);
                                API.sendMessage(s, "Du hast die Zeit der Welt §e"+world.getName()+"§7 auf §eTag§7 gesetzt!");
                            } else if (args[1].equalsIgnoreCase("night")) {
                                world.setTime(13500);
                                API.sendMessage(s, "Du hast die Zeit der Welt §e"+world.getName()+"§7 auf §eNacht§7 gesetzt!");
                            } else {
                                if (API.isNumeric(args[1])) {
                                    world.setTime(Integer.parseInt(args[1]));
                                    API.sendMessage(s, "Du hast die Zeit der Welt §e"+world.getName()+"§7 auf §e" + args[1] + "§7 gesetzt!");
                                } else {
                                    sendSyntax(s);
                                }
                            }
                        } else if (args[0].equalsIgnoreCase("add")) {
                            if (API.isNumeric(args[1])) {
                                WorldAPI.addTime(Integer.parseInt(args[1]), world);
                                API.sendMessage(s, "Du hast die Zeit der Welt §e"+world.getName()+"§7 um §e" + args[1] + "§7 erhöht!");
                            } else {
                                sendSyntax(s);
                            }
                        } else if (args[0].equalsIgnoreCase("remove")) {
                            if (API.isNumeric(args[1])) {
                                WorldAPI.removeTime(Integer.parseInt(args[1]), world);
                                API.sendMessage(s, "Du hast die Zeit der Welt §e"+world.getName()+"§7 um §e" + args[1] + "§7 verringert!");
                            } else {
                                sendSyntax(s);
                            }
                        } else {
                            sendSyntax(s);
                        }
                    } else {
                        s.sendMessage("§cDie Welt §4"+args[2]+"§c existiert nicht!");
                    }
                }
            } else {
                Player p = (Player)s;
                if(p.hasPermission("zyneon.team")) {
                    if (args.length == 2) {
                        if (args[0].equalsIgnoreCase("set")) {
                            if (args[1].equalsIgnoreCase("day")) {
                                p.getWorld().setTime(0);
                                API.sendMessage(p, "Du hast die Zeit auf §eTag§7 gesetzt!");
                            } else if (args[1].equalsIgnoreCase("night")) {
                                p.getWorld().setTime(13500);
                                API.sendMessage(p, "Du hast die Zeit auf §eNacht§7 gesetzt!");
                            } else {
                                if (API.isNumeric(args[1])) {
                                    p.getWorld().setTime(Integer.parseInt(args[1]));
                                    API.sendMessage(p, "Du hast die Zeit auf §e" + args[1] + "§7 gesetzt!");
                                } else {
                                    sendSyntax(s);
                                }
                            }
                        } else if (args[0].equalsIgnoreCase("add")) {
                            if (API.isNumeric(args[1])) {
                                WorldAPI.addTime(Integer.parseInt(args[1]), p.getWorld());
                                API.sendMessage(p, "Du hast die Zeit um §e" + args[1] + "§7 erhöht!");
                            } else {
                                sendSyntax(s);
                            }
                        } else if (args[0].equalsIgnoreCase("remove")) {
                            if (API.isNumeric(args[1])) {
                                WorldAPI.removeTime(Integer.parseInt(args[1]), p.getWorld());
                                API.sendMessage(p, "Du hast die Zeit um §e" + args[1] + "§7 verringert!");
                            } else {
                                sendSyntax(s);
                            }
                        } else {
                            sendSyntax(s);
                        }
                    } else if (args.length > 2) {
                        if (Bukkit.getWorld(args[2]) != null) {
                            World world = Bukkit.getWorld(args[2]);
                            if (args[0].equalsIgnoreCase("set")) {
                                if (args[1].equalsIgnoreCase("day")) {
                                    world.setTime(0);
                                    API.sendMessage(p, "Du hast die Zeit der Welt §e"+world.getName()+"§7 auf §eTag§7 gesetzt!");
                                } else if (args[1].equalsIgnoreCase("night")) {
                                    world.setTime(13500);
                                    API.sendMessage(p, "Du hast die Zeit der Welt §e"+world.getName()+"§7 auf §eNacht§7 gesetzt!");
                                } else {
                                    if (API.isNumeric(args[1])) {
                                        world.setTime(Integer.parseInt(args[1]));
                                        API.sendMessage(p, "Du hast die Zeit der Welt §e"+world.getName()+"§7 auf §e" + args[1] + "§7 gesetzt!");
                                    } else {
                                        sendSyntax(s);
                                    }
                                }
                            } else if (args[0].equalsIgnoreCase("add")) {
                                if (API.isNumeric(args[1])) {
                                    WorldAPI.addTime(Integer.parseInt(args[1]), world);
                                    API.sendMessage(p, "Du hast die Zeit der Welt §e"+world.getName()+"§7 um §e" + args[1] + "§7 erhöht!");
                                } else {
                                    sendSyntax(s);
                                }
                            } else if (args[0].equalsIgnoreCase("remove")) {
                                if (API.isNumeric(args[1])) {
                                    WorldAPI.removeTime(Integer.parseInt(args[1]), world);
                                    API.sendMessage(p, "Du hast die Zeit der Welt §e"+world.getName()+"§7 um §e" + args[1] + "§7 verringert!");
                                } else {
                                    sendSyntax(s);
                                }
                            } else {
                                sendSyntax(s);
                            }
                        } else {
                            API.sendErrorMessage(p,"§cDie Welt §4"+args[2]+"§c existiert nicht!");
                        }
                    } else {
                        sendSyntax(s);
                    }
                } else {
                    API.sendErrorMessage(p,API.noPerms);
                }
            }
        }
        return false;
    }
}