package live.nerotv.projectsbase.modules.essentials.commands;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.roleplay.manager.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Gamemode implements CommandExecutor {

    public boolean onCommand(final CommandSender s, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("gamemode")) {
            if (s.hasPermission("zyneon.team")) {
                if (!(s instanceof Player)) {
                    if (args.length < 2) {
                        s.sendMessage("Fehler: /gamemode [0-3] [Spieler]");
                    }
                    else {
                        final Player p = Bukkit.getPlayer(args[1]);
                        if (p == null) {
                            s.sendMessage(API.noPlayer);
                        }
                        else {
                            API.changeGamemode(p, args[0]);
                            s.sendMessage("Der Spieler " + p.getName() + " spielt nun im " + API.getGamemode(p) + "!");
                        }
                    }
                }
                else if (args.length == 0) {
                    Player p = (Player)s;
                    InventoryManager.openInv_GameMode(p);
                    API.sendErrorMessage(s,"§4Fehler: §c/gamemode §c[Gamemode] §7[Spieler]");
                }
                else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("0")) {
                        final Player p = (Player)s;
                        API.changeGamemode(p, args[0]);
                    }
                    else if (args[0].equalsIgnoreCase("1")) {
                        final Player p = (Player)s;
                        API.changeGamemode(p, args[0]);
                    }
                    else if (args[0].equalsIgnoreCase("2")) {
                        final Player p = (Player)s;
                        API.changeGamemode(p, args[0]);
                    }
                    else if (args[0].equalsIgnoreCase("3")) {
                        final Player p = (Player)s;
                        API.changeGamemode(p, args[0]);
                    }
                    else if (args[0].equalsIgnoreCase("Survival")) {
                        final Player p = (Player)s;
                        API.changeGamemode(p, args[0]);
                    }
                    else if (args[0].equalsIgnoreCase("creative")) {
                        final Player p = (Player)s;
                        API.changeGamemode(p, args[0]);
                    }
                    else if (args[0].equalsIgnoreCase("adventure")) {
                        final Player p = (Player)s;
                        API.changeGamemode(p, args[0]);
                    }
                    else if (args[0].equalsIgnoreCase("spectator")) {
                        final Player p = (Player)s;
                        API.changeGamemode(p, args[0]);
                    }
                    else {
                        final Player p = (Player)s;
                        InventoryManager.openInv_GameMode(p);
                        API.sendErrorMessage(s,"§4Fehler: §c/gamemode §c[Gamemode] §7[Spieler]");
                    }
                }
                else {
                    final Player p = Bukkit.getPlayer(args[1]);
                    if (p == null) {
                        API.sendErrorMessage(s,API.noPlayer);
                    }
                    else if (args[0].equalsIgnoreCase("0")) {
                        API.changeGamemode(p, args[0]);
                        API.sendMessage(s,"§7Du hast den Spieler §e" + p.getName() + "§7 in den §a" + API.getGamemode(p) + "§7 gesetzt!");
                    }
                    else if (args[0].equalsIgnoreCase("1")) {
                        API.changeGamemode(p, args[0]);
                        API.sendMessage(s,"§7Du hast den Spieler §e" + p.getName() + "§7 in den §a" + API.getGamemode(p) + "§7 gesetzt!");
                    }
                    else if (args[0].equalsIgnoreCase("2")) {
                        API.changeGamemode(p, args[0]);
                        API.sendMessage(s,"§7Du hast den Spieler §e" + p.getName() + "§7 in den §a" + API.getGamemode(p) + "§7 gesetzt!");
                    }
                    else if (args[0].equalsIgnoreCase("3")) {
                        API.changeGamemode(p, args[0]);
                        API.sendMessage(s,"§7Du hast den Spieler §e" + p.getName() + "§7 in den §a" + API.getGamemode(p) + "§7 gesetzt!");
                    }
                    else if (args[0].equalsIgnoreCase("SURVIVAL")) {
                        API.changeGamemode(p, args[0]);
                        API.sendMessage(s,"§7Du hast den Spieler §e" + p.getName() + "§7 in den §a" + API.getGamemode(p) + "§7 gesetzt!");
                    }
                    else if (args[0].equalsIgnoreCase("CREATIVE")) {
                        API.changeGamemode(p, args[0]);
                        API.sendMessage(s,"§7Du hast den Spieler §e" + p.getName() + "§7 in den §a" + API.getGamemode(p) + "§7 gesetzt!");
                    }
                    else if (args[0].equalsIgnoreCase("ADVENTURE")) {
                        API.changeGamemode(p, args[0]);
                        API.sendMessage(s,"§7Du hast den Spieler §e" + p.getName() + "§7 in den §a" + API.getGamemode(p) + "§7 gesetzt!");
                    }
                    else if (args[0].equalsIgnoreCase("SPECTATOR")) {
                        API.changeGamemode(p, args[0]);
                        API.sendMessage(s,"§7Du hast den Spieler §e" + p.getName() + "§7 in den §a" + API.getGamemode(p) + "§7 gesetzt!");
                    }
                    else {
                        API.sendErrorMessage(s,"§4Fehler: §c/gamemode §c[Gamemode] §7[Spieler]");
                    }
                }
            } else {
                API.sendErrorMessage(s,API.noPerms);
            }
        }
        return false;
    }
}