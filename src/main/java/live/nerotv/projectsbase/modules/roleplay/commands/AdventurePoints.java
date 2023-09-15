package live.nerotv.projectsbase.modules.roleplay.commands;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.api.ServerAPI;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

public class AdventurePoints implements CommandExecutor {

    private void sendSyntax(Player p) {
        if(p.hasPermission("zyneon.team")) {
            API.sendErrorMessage(p,"§4Fehler: §c/ap §7[set/§fget§7/remove/add§7] §7[Spieler/§fSpieler§7] [Wert]");
        } else {
            API.sendErrorMessage(p,"§4Fehler: §c/ap §7[get] [Spielername]");
        }
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("AdventurePoints")) {
            if(!(s instanceof Player)) {
                if(args.length < 2) {
                    s.sendMessage("Fehler: ap get [Spieler]");
                    s.sendMessage("Fehler: ap set/add/remove [Spieler] [Wert]");
                } else if(args.length == 2) {
                    if(args[0].equalsIgnoreCase("get")) {
                        if(Bukkit.getPlayer(args[1]) != null) {
                            Player p = Bukkit.getPlayer(args[1]);
                            s.sendMessage(API.Prefix+"§7Der Spieler §e"+p.getName()+"§7 hat §a"+ RoleplayAPI.getRoleplayUser(p).getAdventurePoints()+" AP§7!");
                        } else {
                            ServerAPI.sendConsoleMessage(API.noPlayer);
                        }
                    } else {
                        s.sendMessage("Fehler: ap get [Spieler]");
                        s.sendMessage("Fehler: ap set/add/remove [Spieler] [Wert]");
                    }
                } else {
                    if(args[0].equalsIgnoreCase("set")) {
                        if(Bukkit.getPlayer(args[1]) != null) {
                            Player p = Bukkit.getPlayer(args[1]);
                            if(API.isNumeric(args[2])) {
                                RoleplayAPI.getRoleplayUser(p).setAdventurePoints(Integer.parseInt(args[2]));
                                s.sendMessage(API.Prefix+"§7Der Spieler §e"+p.getName()+"§7 hat nun §a"+ RoleplayAPI.getRoleplayUser(p).getAdventurePoints()+" AP§7!");
                                API.sendMessage(p,"Du hast nun §e"+ RoleplayAPI.getRoleplayUser(p).getAdventurePoints()+" AP§7!");
                            } else {
                                ServerAPI.sendConsoleMessage("§cDie AP-Anzahl muss eine Zahl sein!");
                            }
                        } else {
                            ServerAPI.sendConsoleMessage(API.noPlayer);
                        }
                    } else if(args[0].equalsIgnoreCase("add")) {
                        if(Bukkit.getPlayer(args[1]) != null) {
                            Player p = Bukkit.getPlayer(args[1]);
                            if(API.isNumeric(args[2])) {
                                int Value = RoleplayAPI.getRoleplayUser(p).getAdventurePoints()+Integer.parseInt(args[2]);
                                RoleplayAPI.getRoleplayUser(p).setAdventurePoints(Value);
                                s.sendMessage(API.Prefix+"§7Der Spieler §e"+p.getName()+"§7 hat nun §a"+ RoleplayAPI.getRoleplayUser(p).getAdventurePoints()+" AP§7!");
                                API.sendMessage(p,"Du hast nun §e"+ RoleplayAPI.getRoleplayUser(p).getAdventurePoints()+" AP§7!");
                            } else {
                                ServerAPI.sendConsoleMessage("§cDie AP-Anzahl muss eine Zahl sein!");
                            }
                        } else {
                            ServerAPI.sendConsoleMessage(API.noPlayer);
                        }
                    } else if(args[0].equalsIgnoreCase("remove")) {
                        if(Bukkit.getPlayer(args[1]) != null) {
                            Player p = Bukkit.getPlayer(args[1]);
                            if(API.isNumeric(args[2])) {
                                int Value = RoleplayAPI.getRoleplayUser(p).getAdventurePoints()-Integer.parseInt(args[2]);
                                RoleplayAPI.getRoleplayUser(p).setAdventurePoints(Value);
                                s.sendMessage(API.Prefix+"§7Der Spieler §e"+p.getName()+"§7 hat nun §a"+ RoleplayAPI.getRoleplayUser(p).getAdventurePoints()+" AP§7!");
                                API.sendMessage(p,"Du hast nun §e"+ RoleplayAPI.getRoleplayUser(p).getAdventurePoints()+" AP§7!");
                            } else {
                                ServerAPI.sendConsoleMessage("§cDie AP-Anzahl muss eine Zahl sein!");
                            }
                        } else {
                            ServerAPI.sendConsoleMessage(API.noPlayer);
                        }
                    } else {
                        s.sendMessage("Fehler: ap set/add/remove [Spieler] [Wert]");
                        s.sendMessage("Fehler: ap get [Spieler]");
                    }
                }
            } else {
                Player p = (Player)s;
                if(args.length == 0) {
                    API.sendMessage(p, "Du hast momentan §e" + RoleplayAPI.getRoleplayUser(p).getAdventurePoints() + " AP§7.");
                } else if(args.length == 1) {
                    sendSyntax(p);
                } else if(args.length == 2) {
                    if(args[0].equalsIgnoreCase("get")) {
                        if(Bukkit.getPlayer(args[1]) != null) {
                            Player p2 = Bukkit.getPlayer(args[1]);
                            API.sendMessage(p,"Der Spieler §e"+p2.getName()+"§7 hat §a"+ RoleplayAPI.getRoleplayUser(p2).getAdventurePoints()+" AP§7.");
                        } else {
                            UUID uuid;
                            if (Bukkit.getPlayer(args[0]) != null) {
                                uuid = Objects.requireNonNull(Bukkit.getPlayer(args[0])).getUniqueId();
                            } else {
                                try {
                                    uuid = UUID.fromString(args[0]);
                                    if (RoleplayAPI.getRoleplayUser(uuid).getLastLogin()==null) {
                                        API.sendErrorMessage(s, "Diese/r Spieler/in konnte nicht gefunden werden§8!");
                                        return false;
                                    }
                                } catch (IllegalArgumentException exception) {
                                    if (RoleplayAPI.getRoleplayUser(Bukkit.getOfflinePlayer(args[0]).getUniqueId()).getLastLogin()==null) {
                                        API.sendErrorMessage(s, "Diese/r Spieler/in konnte nicht gefunden werden§8!");
                                        return false;
                                    } else {
                                        uuid = Bukkit.getOfflinePlayer(args[0]).getUniqueId();
                                    }
                                }
                            }
                            RoleplayUser target = RoleplayAPI.getRoleplayUser(uuid);
                            API.sendMessage(p,"Der Spieler §e"+args[1]+"§7 hat §a"+ target.getAdventurePoints()+" AP§7.");
                        }
                    } else {
                        sendSyntax(p);
                    }
                } else {
                    if(args[0].equalsIgnoreCase("set")) {
                        if(p.hasPermission("zyneon.team")) {
                            if(Bukkit.getPlayer(args[1]) != null) {
                                Player p2 = Bukkit.getPlayer(args[1]);
                                if(API.isNumeric(args[2])) {
                                    RoleplayAPI.getRoleplayUser(p2).setAdventurePoints(Integer.parseInt(args[2]));
                                    API.sendMessage(p,"Der Spieler §e"+p2.getName()+"§7 hat nun §a"+ RoleplayAPI.getRoleplayUser(p2).getAdventurePoints()+" AP§7!");
                                    API.sendMessage(p2,"Du hast nun §e"+ RoleplayAPI.getRoleplayUser(p2).getAdventurePoints()+" AP§7!");
                                } else {
                                    API.sendErrorMessage(p,"§cDie AP-Anzahl muss eine Zahl sein!");
                                }
                            } else {
                                UUID uuid;
                                if (Bukkit.getPlayer(args[0]) != null) {
                                    uuid = Objects.requireNonNull(Bukkit.getPlayer(args[0])).getUniqueId();
                                } else {
                                    try {
                                        uuid = UUID.fromString(args[0]);
                                        if (RoleplayAPI.getRoleplayUser(uuid).getLastLogin()==null) {
                                            API.sendErrorMessage(s, "Diese/r Spieler/in konnte nicht gefunden werden§8!");
                                            return false;
                                        }
                                    } catch (IllegalArgumentException exception) {
                                        if (RoleplayAPI.getRoleplayUser(Bukkit.getOfflinePlayer(args[0]).getUniqueId()).getLastLogin()==null) {
                                            API.sendErrorMessage(s, "Diese/r Spieler/in konnte nicht gefunden werden§8!");
                                            return false;
                                        } else {
                                            uuid = Bukkit.getOfflinePlayer(args[0]).getUniqueId();
                                        }
                                    }
                                }
                                RoleplayUser target = RoleplayAPI.getRoleplayUser(uuid);
                                if(API.isNumeric(args[2])) {
                                    target.setAdventurePoints(Integer.parseInt(args[2]));
                                    API.sendMessage(p,"Der Spieler §e"+args[1]+"§7 hat nun §a"+ target.getAdventurePoints()+" AP§7!");
                                } else {
                                    API.sendErrorMessage(p,"§cDie AP-Anzahl muss eine Zahl sein!");
                                }
                            }
                        } else {
                            API.sendErrorMessage(p,API.noPerms);
                        }
                    }  else if(args[0].equalsIgnoreCase("remove")) {
                        if(p.hasPermission("zyneon.team")) {
                            if(Bukkit.getPlayer(args[1]) != null) {
                                Player p2 = Bukkit.getPlayer(args[1]);
                                if(API.isNumeric(args[2])) {
                                    Integer Value = RoleplayAPI.getRoleplayUser(p2).getAdventurePoints()-Integer.parseInt(args[2]);
                                    RoleplayAPI.getRoleplayUser(p2).setAdventurePoints(Value);
                                    API.sendMessage(p,"Der Spieler §e"+p2.getName()+"§7 hat nun §a"+ RoleplayAPI.getRoleplayUser(p2).getAdventurePoints()+" AP§7!");
                                    API.sendMessage(p2,"Du hast nun §e"+ RoleplayAPI.getRoleplayUser(p2).getAdventurePoints()+" AP§7!");
                                } else {
                                    API.sendErrorMessage(p,"§cDie AP-Anzahl muss eine Zahl sein!");
                                }
                            } else {
                                UUID uuid;
                                if (Bukkit.getPlayer(args[0]) != null) {
                                    uuid = Objects.requireNonNull(Bukkit.getPlayer(args[0])).getUniqueId();
                                } else {
                                    try {
                                        uuid = UUID.fromString(args[0]);
                                        if (RoleplayAPI.getRoleplayUser(uuid).getLastLogin()==null) {
                                            API.sendErrorMessage(s, "Diese/r Spieler/in konnte nicht gefunden werden§8!");
                                            return false;
                                        }
                                    } catch (IllegalArgumentException exception) {
                                        if (RoleplayAPI.getRoleplayUser(Bukkit.getOfflinePlayer(args[0]).getUniqueId()).getLastLogin()==null) {
                                            API.sendErrorMessage(s, "Diese/r Spieler/in konnte nicht gefunden werden§8!");
                                            return false;
                                        } else {
                                            uuid = Bukkit.getOfflinePlayer(args[0]).getUniqueId();
                                        }
                                    }
                                }
                                RoleplayUser target = RoleplayAPI.getRoleplayUser(uuid);
                                if(API.isNumeric(args[2])) {
                                    target.setAdventurePoints(target.getAdventurePoints()-Integer.parseInt(args[2]));
                                    API.sendMessage(p,"Der Spieler §e"+args[1]+"§7 hat nun §a"+ target.getAdventurePoints()+" AP§7!");
                                } else {
                                    API.sendErrorMessage(p,"§cDie AP-Anzahl muss eine Zahl sein!");
                                }
                            }
                        } else {
                            API.sendErrorMessage(p,API.noPerms);
                        }
                    } else if(args[0].equalsIgnoreCase("add")) {
                        if(p.hasPermission("zyneon.team")) {
                            if(Bukkit.getPlayer(args[1]) != null) {
                                Player p2 = Bukkit.getPlayer(args[1]);
                                if(API.isNumeric(args[2])) {
                                    Integer Value = RoleplayAPI.getRoleplayUser(p2).getAdventurePoints()+Integer.parseInt(args[2]);
                                    RoleplayAPI.getRoleplayUser(p2).setAdventurePoints(Value);
                                    API.sendMessage(p,"Der Spieler §e"+p2.getName()+"§7 hat nun §a"+ RoleplayAPI.getRoleplayUser(p2).getAdventurePoints()+" AP§7!");
                                    API.sendMessage(p2,"Du hast nun §e"+ RoleplayAPI.getRoleplayUser(p2).getAdventurePoints()+" AP§7!");
                                } else {
                                    API.sendErrorMessage(p,"§cDie AP-Anzahl muss eine Zahl sein!");
                                }
                            } else {
                                UUID uuid;
                                if (Bukkit.getPlayer(args[0]) != null) {
                                    uuid = Objects.requireNonNull(Bukkit.getPlayer(args[0])).getUniqueId();
                                } else {
                                    try {
                                        uuid = UUID.fromString(args[0]);
                                        if (RoleplayAPI.getRoleplayUser(uuid).getLastLogin()==null) {
                                            API.sendErrorMessage(s, "Diese/r Spieler/in konnte nicht gefunden werden§8!");
                                            return false;
                                        }
                                    } catch (IllegalArgumentException exception) {
                                        if (RoleplayAPI.getRoleplayUser(Bukkit.getOfflinePlayer(args[0]).getUniqueId()).getLastLogin()==null) {
                                            API.sendErrorMessage(s, "Diese/r Spieler/in konnte nicht gefunden werden§8!");
                                            return false;
                                        } else {
                                            uuid = Bukkit.getOfflinePlayer(args[0]).getUniqueId();
                                        }
                                    }
                                }
                                RoleplayUser target = RoleplayAPI.getRoleplayUser(uuid);
                                if(API.isNumeric(args[2])) {
                                    target.setAdventurePoints(target.getAdventurePoints()+Integer.parseInt(args[2]));
                                    API.sendMessage(p,"Der Spieler §e"+args[1]+"§7 hat nun §a"+ target.getAdventurePoints()+" AP§7!");
                                } else {
                                    API.sendErrorMessage(p,"§cDie AP-Anzahl muss eine Zahl sein!");
                                }
                            }
                        } else {
                            API.sendErrorMessage(p,API.noPerms);
                        }
                    }
                }
            }
        }
        return false;
    }
}