package live.nerotv.projectsbase.modules.economy.commands;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.economy.EconomyMain;
import live.nerotv.projectsbase.modules.economy.utils.EconomyUser;
import live.nerotv.projectsbase.modules.economy.utils.Ecosystem;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Money implements CommandExecutor {

    private void sendSyntax(CommandSender s) {
        if(s instanceof Player) {
            Player p = (Player)s;
            if(p.hasPermission("zyneon.team")) {
                API.sendErrorMessage(p, "§4Fehler: §c/money §7[§fSpieler§7/set/add/remove/pay] [Spieler] [Wert]");
            } else {
                API.sendMessage(p,"§4Fehler: §c/money §7[§fSpieler§7/pay§7] [Spieler] [Wert]");
            }
        } else {
            API.sendErrorMessage(s,"§4Fehler: §c/money [§fSpieler§c/set/add/remove] [Spieler] [Wert]");
        }
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Money")) {
            if(s instanceof Player) {
                Player p = (Player)s;
                EconomyUser u = new EconomyUser(p.getUniqueId());
                if(args.length == 0) {
                    u.sendMessage("§7Du hast zurzeit §e"+u.getBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                } else if(args.length == 1) {
                    if(Bukkit.getPlayer(args[0])!=null) {
                        EconomyUser tU = new EconomyUser(Bukkit.getPlayer(args[0]).getUniqueId());
                        u.sendMessage("§a"+tU.getPlayer().getName()+"§7 hat zurzeit §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                    } else {
                        if(EconomyMain.getEco().hasAccount(Bukkit.getOfflinePlayer(args[0]).getUniqueId())) {
                            EconomyUser tU = new EconomyUser(Bukkit.getOfflinePlayer(args[0]).getUniqueId());
                            u.sendMessage("§a"+tU.getPlayer().getName()+"§7 hat zurzeit §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                        } else {
                            API.sendErrorMessage(p,"§4Fehler: §cDieser Spieler konnte nicht gefunden werden§8!");
                        }
                    }
                } else if(args.length == 3) {
                    if(args[0].equalsIgnoreCase("pay")) {
                        if(Bukkit.getPlayer(args[1])!=null) {
                            EconomyUser tU = new EconomyUser(Bukkit.getPlayer(args[1]).getUniqueId());
                            if(tU.getOfflinePlayer().getUniqueId()==p.getUniqueId()) {
                                u.sendErrorMessage("§4Fehler: §cDu kannst dir selbst kein Geld geben!");
                                return false;
                            }
                            API.isNumeric(args[2]); double d = Double.parseDouble(args[2]);
                            if(Ecosystem.checkPayment(p.getUniqueId(),d)) {
                                u.setBalance(u.getBalance()-d);
                                tU.setBalance(tU.getBalance()+d);
                                u.sendMessage("§7Du hast dem/der Spieler:in §a"+tU.getPlayer().getName()+"§e "+d+"§7 "+EconomyMain.currencyName+" gegeben§8!");
                                tU.sendMessage("§7Du hast von §a"+p.getName()+"§a "+d+"§7 "+EconomyMain.currencyName+"§7 erhalten und hast nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                            } else {
                                API.sendErrorMessage(p,"§4Fehler: §cDazu hast du nicht genug Geld§8!");
                            }
                        } else {
                            if(EconomyMain.getEco().hasAccount(Bukkit.getOfflinePlayer(args[1]).getUniqueId())) {
                                EconomyUser tU = new EconomyUser(Bukkit.getOfflinePlayer(args[1]).getUniqueId());
                                if(tU.getOfflinePlayer().getUniqueId()==p.getUniqueId()) {
                                    u.sendErrorMessage("§4Fehler: §cDu kannst dir selbst kein Geld geben!");
                                    return false;
                                }
                                API.isNumeric(args[2]); double d = Double.parseDouble(args[2]);
                                if(Ecosystem.checkPayment(p.getUniqueId(),d)) {
                                    u.setBalance(u.getBalance()-d);
                                    tU.setBalance(tU.getBalance()+d);
                                    u.sendMessage("§7Du hast dem/der Spieler:in §a"+tU.getPlayer().getName()+"§e "+d+"§7 "+EconomyMain.currencyName+" gegeben§8!");
                                    tU.sendMessage("§7Du hast von §a"+p.getName()+"§a "+d+"§7 "+EconomyMain.currencyName+"§7 erhalten und hast nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                                } else {
                                    API.sendErrorMessage(p,"§4Fehler: §cDazu hast du nicht genug Geld§8!");
                                }
                            } else {
                                API.sendErrorMessage(p,"§4Fehler: §cDieser Spieler konnte nicht gefunden werden§8!");
                            }
                        }
                    } else {
                        if(p.hasPermission("zyneon.team")) {
                            if(args[0].equalsIgnoreCase("set")) {
                                if(Bukkit.getPlayer(args[1])!=null) {
                                    EconomyUser tU = new EconomyUser(Bukkit.getPlayer(args[1]).getUniqueId());
                                    API.isNumeric(args[2]); double d = Double.parseDouble(args[2]);
                                    tU.setBalance(d);
                                    u.sendMessage("§7Der/die Spieler:in §a"+tU.getPlayer().getName()+"§7 hat nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                                    tU.sendMessage("§7Du hast nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                                } else {
                                    if(EconomyMain.getEco().hasAccount(Bukkit.getOfflinePlayer(args[1]).getUniqueId())) {
                                        EconomyUser tU = new EconomyUser(Bukkit.getOfflinePlayer(args[1]).getUniqueId());
                                        API.isNumeric(args[2]); double d = Double.parseDouble(args[2]);
                                        tU.setBalance(d);
                                        u.sendMessage("§7Der/die Spieler:in §a"+tU.getPlayer().getName()+"§7 hat nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                                        tU.sendMessage("§7Du hast nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                                    } else {
                                        API.sendErrorMessage(p,"§4Fehler: §cDieser Spieler konnte nicht gefunden werden§8!");
                                    }
                                }
                            } else if(args[0].equalsIgnoreCase("add")||args[0].equalsIgnoreCase("give")) {
                                if(Bukkit.getPlayer(args[1])!=null) {
                                    EconomyUser tU = new EconomyUser(Bukkit.getPlayer(args[1]).getUniqueId());
                                    API.isNumeric(args[2]); double d = Double.parseDouble(args[2]);
                                    tU.addBalance(d);
                                    u.sendMessage("§7Der/die Spieler:in §a"+tU.getPlayer().getName()+"§7 hat nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                                    tU.sendMessage("§7Du hast nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                                } else {
                                    if(EconomyMain.getEco().hasAccount(Bukkit.getOfflinePlayer(args[1]).getUniqueId())) {
                                        EconomyUser tU = new EconomyUser(Bukkit.getOfflinePlayer(args[1]).getUniqueId());
                                        API.isNumeric(args[2]); double d = Double.parseDouble(args[2]);
                                        tU.addBalance(d);
                                        u.sendMessage("§7Der/die Spieler:in §a"+tU.getPlayer().getName()+"§7 hat nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                                        tU.sendMessage("§7Du hast nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                                    } else {
                                        API.sendErrorMessage(p,"§4Fehler: §cDieser Spieler konnte nicht gefunden werden§8!");
                                    }
                                }
                            } else if(args[0].equalsIgnoreCase("remove")||args[0].equalsIgnoreCase("rem")) {
                                if(Bukkit.getPlayer(args[0])!=null) {
                                    EconomyUser tU = new EconomyUser(Bukkit.getPlayer(args[1]).getUniqueId());
                                    API.isNumeric(args[2]); double d = Double.parseDouble(args[2]);
                                    tU.removeBalance(d);
                                    u.sendMessage("§7Der/die Spieler:in §a"+tU.getPlayer().getName()+"§7 hat nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                                    tU.sendMessage("§7Du hast nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                                } else {
                                    if(EconomyMain.getEco().hasAccount(Bukkit.getOfflinePlayer(args[1]).getUniqueId())) {
                                        EconomyUser tU = new EconomyUser(Bukkit.getOfflinePlayer(args[1]).getUniqueId());
                                        API.isNumeric(args[2]); double d = Double.parseDouble(args[2]);
                                        tU.removeBalance(d);
                                        u.sendMessage("§7Der/die Spieler:in §a"+tU.getPlayer().getName()+"§7 hat nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                                        tU.sendMessage("§7Du hast nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                                    } else {
                                        API.sendErrorMessage(p,"§4Fehler: §cDieser Spieler konnte nicht gefunden werden§8!");
                                    }
                                }
                            } else {
                                sendSyntax(p);
                            }
                        } else {
                            sendSyntax(p);
                        }
                    }
                } else {
                    sendSyntax(p);
                }
            } else {
                if(args.length == 1) {
                    if(Bukkit.getPlayer(args[0])!=null) {
                        EconomyUser tU = new EconomyUser(Bukkit.getPlayer(args[0]).getUniqueId());
                        s.sendMessage("§a"+tU.getPlayer().getName()+"§7 hat zurzeit §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                    } else {
                        if(EconomyMain.getEco().hasAccount(Bukkit.getOfflinePlayer(args[0]).getUniqueId())) {
                            EconomyUser tU = new EconomyUser(Bukkit.getOfflinePlayer(args[0]).getUniqueId());
                            s.sendMessage("§a"+tU.getPlayer().getName()+"§7 hat zurzeit §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                        } else {
                            API.sendErrorMessage(s,"§4Fehler: §cDieser Spieler konnte nicht gefunden werden§8!");
                        }
                    }
                } else if(args.length == 3) {
                    if(s.hasPermission("zyneon.team")) {
                        if(args[0].equalsIgnoreCase("set")) {
                            if(Bukkit.getPlayer(args[1])!=null) {
                                EconomyUser tU = new EconomyUser(Bukkit.getPlayer(args[1]).getUniqueId());
                                API.isNumeric(args[2]); double d = Double.parseDouble(args[2]);
                                tU.setBalance(d);
                                s.sendMessage("§7Der/die Spieler:in §a"+tU.getPlayer().getName()+"§7 hat nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                                tU.sendMessage("§7Du hast nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                            } else {
                                if(EconomyMain.getEco().hasAccount(Bukkit.getOfflinePlayer(args[1]).getUniqueId())) {
                                    EconomyUser tU = new EconomyUser(Bukkit.getOfflinePlayer(args[1]).getUniqueId());
                                    API.isNumeric(args[2]); double d = Double.parseDouble(args[2]);
                                    tU.setBalance(d);
                                    s.sendMessage("§7Der/die Spieler:in §a"+tU.getPlayer().getName()+"§7 hat nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                                    tU.sendMessage("§7Du hast nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                                } else {
                                    API.sendErrorMessage(s,"§4Fehler: §cDieser Spieler konnte nicht gefunden werden§8!");
                                }
                            }
                        } else if(args[0].equalsIgnoreCase("add")||args[0].equalsIgnoreCase("give")) {
                            if(Bukkit.getPlayer(args[1])!=null) {
                                EconomyUser tU = new EconomyUser(Bukkit.getPlayer(args[1]).getUniqueId());
                                API.isNumeric(args[2]); double d = Double.parseDouble(args[2]);
                                tU.addBalance(d);
                                s.sendMessage("§7Der/die Spieler:in §a"+tU.getPlayer().getName()+"§7 hat nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                                tU.sendMessage("§7Du hast nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                            } else {
                                if(EconomyMain.getEco().hasAccount(Bukkit.getOfflinePlayer(args[1]).getUniqueId())) {
                                    EconomyUser tU = new EconomyUser(Bukkit.getOfflinePlayer(args[1]).getUniqueId());
                                    API.isNumeric(args[2]); double d = Double.parseDouble(args[2]);
                                    tU.addBalance(d);
                                    s.sendMessage("§7Der/die Spieler:in §a"+tU.getPlayer().getName()+"§7 hat nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                                    tU.sendMessage("§7Du hast nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                                } else {
                                    API.sendErrorMessage(s,"§4Fehler: §cDieser Spieler konnte nicht gefunden werden§8!");
                                }
                            }
                        } else if(args[0].equalsIgnoreCase("remove")||args[0].equalsIgnoreCase("rem")) {
                            if(Bukkit.getPlayer(args[0])!=null) {
                                EconomyUser tU = new EconomyUser(Bukkit.getPlayer(args[1]).getUniqueId());
                                API.isNumeric(args[2]); double d = Double.parseDouble(args[2]);
                                tU.removeBalance(d);
                                s.sendMessage("§7Der/die Spieler:in §a"+tU.getPlayer().getName()+"§7 hat nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                                tU.sendMessage("§7Du hast nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                            } else {
                                if(EconomyMain.getEco().hasAccount(Bukkit.getOfflinePlayer(args[1]).getUniqueId())) {
                                    EconomyUser tU = new EconomyUser(Bukkit.getOfflinePlayer(args[1]).getUniqueId());
                                    API.isNumeric(args[2]); double d = Double.parseDouble(args[2]);
                                    tU.removeBalance(d);
                                    s.sendMessage("§7Der/die Spieler:in §a"+tU.getPlayer().getName()+"§7 hat nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                                    tU.sendMessage("§7Du hast nun §e"+tU.getSmoothBalance()+"§7 "+EconomyMain.currencyName+"§8!");
                                } else {
                                    API.sendErrorMessage(s,"§4Fehler: §cDieser Spieler konnte nicht gefunden werden§8!");
                                }
                            }
                        } else {
                            sendSyntax(s);
                        }
                    } else {
                        sendSyntax(s);
                    }
                } else {
                    sendSyntax(s);
                }
            }
        }
        return false;
    }
}