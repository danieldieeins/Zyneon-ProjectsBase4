package live.nerotv.projectsbase.modules.roleplay.commands;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.api.ConfigAPI;
import live.nerotv.projectsbase.api.WarpAPI;
import live.nerotv.projectsbase.modules.essentials.manager.StaticManager;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.modules.roleplay.manager.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Warp implements CommandExecutor {

    void sendES(Player p) {
        API.sendErrorMessage(p, "§cDieser Warp existiert bereits!");
    }

    void sendESN(Player p) {
        API.sendErrorMessage(p, "§cDieser Warp existiert nicht!");
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("Warp")) {
            if(!RoleplayAPI.isStarted) {
                return false;
            }
                if (!(s instanceof Player)) {
                    s.sendMessage("§cDazu §4musst§c du ein Spieler sein§4!");
                } else {
                    Player p = (Player)s;
                    if (args.length == 0) {
                        if(StaticManager.roleplayModule) {
                            InventoryManager.openInv002(p);
                        } else {
                            if(p.hasPermission("zyneon.team")) {
                                API.sendErrorMessage(p,"§4Fehler: §c/warp [§7list§c/set/remove/enable/disable/tp] [Warp-Name]");
                            } else {
                                API.sendErrorMessage(p,API.noPerms);
                            }
                        }
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG,100,100);
                    } else if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("list")) {
                            if (p.hasPermission("zyneon.team")) {
                                p.performCommand("warp list 1");
                            } else if (args[0].equalsIgnoreCase("old") && ConfigAPI.CFG.getBoolean("Core.Settings.Commands.Map.Enable")) {
                                Location l = p.getLocation();
                                p.teleport(new Location(Bukkit.getWorld("Welt_old"),l.getX(),l.getY(),l.getZ(),l.getYaw(),l.getPitch()));
                            } else if (args[0].equalsIgnoreCase("new") && ConfigAPI.CFG.getBoolean("Core.Settings.Commands.Map.Enable")) {
                                Location l = p.getLocation();
                                p.teleport(new Location(Bukkit.getWorld("Welt"),l.getX(),l.getY(),l.getZ(),l.getYaw(),l.getPitch()));
                            } else if(args[0].equalsIgnoreCase("map") && ConfigAPI.CFG.getBoolean("Core.Settings.Commands.Map.Enable")) {
                                processTeleport(p);
                            } else {
                                p.performCommand("warp");
                            }
                        } else {
                            p.performCommand("warp");
                        }
                    } else {
                        String Warpname = args[1];
                        if (p.hasPermission("zyneon.team")) {
                            if (args[0].equalsIgnoreCase("set")) {
                                if (WarpAPI.ifWarpExists(Warpname)) {
                                    sendES(p);
                                } else {
                                    WarpAPI.setWarp(Warpname, p, false);
                                    API.sendMessage(p, "Du hast §aerfolgreich den Warp-Punkt \"§e" + Warpname + "§7\" hinzugefügt. Nutze §f/warp enable " + Warpname + "§7 oder §f/warp toggle " + Warpname + "§7, um den Warp-Punkt zu aktivieren.");
                                }
                            } else if (args[0].equalsIgnoreCase("teleport")) {
                                if (WarpAPI.isWarpEnabled(Warpname)) {
                                    p.teleport(WarpAPI.getWarp(Warpname));
                                    p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG,100,100);
                                } else {
                                    sendESN(p);
                                }
                            } else if (args[0].equalsIgnoreCase("tp")) {
                                if (WarpAPI.isWarpEnabled(Warpname)) {
                                    p.teleport(WarpAPI.getWarp(Warpname));
                                    p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG,100,100);
                                } else {
                                    sendESN(p);
                                }
                            } else if (args[0].equalsIgnoreCase("enable")) {
                                if (WarpAPI.ifWarpExists(Warpname)) {
                                    WarpAPI.enableWarp(Warpname);
                                    API.sendMessage(p, "Du hast §aerfolgreich§7 den Warp \"§e" + Warpname + "§7\" aktiviert!");
                                } else {
                                    sendESN(p);
                                }
                            } else if (args[0].equalsIgnoreCase("disable")) {
                                if (WarpAPI.isWarpEnabled(Warpname)) {
                                    WarpAPI.disableWarp(Warpname);
                                    API.sendMessage(p, "Du hast erfolgreich den Warp \"§e" + Warpname + "§7\" §cdeaktiviert§7!");
                                } else {
                                    sendESN(p);
                                }
                            } else if (args[0].equalsIgnoreCase("toggle")) {
                                if (WarpAPI.ifWarpExists(Warpname)) {
                                    if (WarpAPI.isWarpEnabled(Warpname)) {
                                        p.performCommand("warp disable " + Warpname);
                                    } else {
                                        p.performCommand("warp enable " + Warpname);
                                    }
                                } else {
                                    sendESN(p);
                                }
                            } else if(args[0].equalsIgnoreCase("map") && ConfigAPI.CFG.getBoolean("Core.Settings.Commands.Map.Enable")) {
                                processTeleport(p);
                            } else if (args[0].equalsIgnoreCase("new") && ConfigAPI.CFG.getBoolean("Core.Settings.Commands.Map.Enable")) {
                                Location l = p.getLocation();
                                p.teleport(new Location(Bukkit.getWorld("Welt"),l.getX(),l.getY(),l.getZ(),l.getYaw(),l.getPitch()));
                            } else if (args[0].equalsIgnoreCase("old") && ConfigAPI.CFG.getBoolean("Core.Settings.Commands.Map.Enable")) {
                                Location l = p.getLocation();
                                p.teleport(new Location(Bukkit.getWorld("Welt_old"),l.getX(),l.getY(),l.getZ(),l.getYaw(),l.getPitch()));
                            } else if (args[0].equalsIgnoreCase("delete")) {
                                if (WarpAPI.ifWarpExists(Warpname)) {
                                    WarpAPI.removeWarp(Warpname);
                                    API.sendMessage(p, "Du hast erfolgreich den Warp-Punkt §e" + Warpname + "§c entfernt§7!");
                                } else {
                                    sendESN(p);
                                }
                            } else if (args[0].equalsIgnoreCase("remove")) {
                                if (WarpAPI.ifWarpExists(Warpname)) {
                                    WarpAPI.removeWarp(Warpname);
                                    API.sendMessage(p, "Du hast erfolgreich den Warp-Punkt §e" + Warpname + "§c entfernt§7!");
                                } else {
                                    sendESN(p);
                                }
                            } else if (args[0].equalsIgnoreCase("del")) {
                                if (WarpAPI.ifWarpExists(Warpname)) {
                                    WarpAPI.removeWarp(Warpname);
                                    API.sendMessage(p, "Du hast erfolgreich den Warp-Punkt §e" + Warpname + "§c entfernt§7!");
                                } else {
                                    sendESN(p);
                                }
                            } else if (args[0].equalsIgnoreCase("rem")) {
                                if (WarpAPI.ifWarpExists(Warpname)) {
                                    WarpAPI.removeWarp(Warpname);
                                    API.sendMessage(p, "Du hast erfolgreich den Warp-Punkt §e" + Warpname + "§c entfernt§7!");
                                } else {
                                    sendESN(p);
                                }
                            } else if (args[0].equalsIgnoreCase("list")) {
                                API.sendMessage(p, "§7Es existieren folgende Warp-Punkte:");
                                WarpAPI.sendWarpList(s);
                            } else {
                                p.performCommand("warp");
                            }
                        } else {
                            p.performCommand("warp");
                        }
                    }
                }
        }
        return false;
    }

    private void processTeleport(Player p) {
        Location l = p.getLocation();
        if(p.getWorld().getName().equals("Welt")) {
            p.teleport(new Location(Bukkit.getWorld("Welt_old"), l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch()));
        } else {
            p.teleport(new Location(Bukkit.getWorld("Welt"), l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch()));
        }
    }
}