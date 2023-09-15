package live.nerotv.projectsbase.modules.roleplay.commands;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.api.ChatAPI;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.modules.roleplay.characters.Characters;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Name implements CommandExecutor {

    private void sendSyntax(CommandSender s) {
        if(!(s instanceof Player)) {
            s.sendMessage("§cDazu §4musst§c du ein Spieler sein§4!");
        } else {
            Player p = (Player)s;
            API.sendErrorMessage(s,"§4Fehler: §c/name [Vorname] §7[Mittelname] §c[Nachname]");
        }
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(Characters.enableCharacters) {
            Bukkit.dispatchCommand(s,"char");
            return false;
        }
        if(cmd.getName().equalsIgnoreCase("Name")) {
            if(!(s instanceof Player)) {
                sendSyntax(s);
            } else {
                Player p = (Player)s;
                RoleplayUser u = RoleplayAPI.getRoleplayUser(p);
                if(args.length == 0) {
                    sendSyntax(s);
                } else {
                    if(args.length > 3) {
                        API.sendErrorMessage(p,"§cDie Länge des Names darf maximal 3 Wörter betragen§8!");
                    } else {
                        String c="";
                        for(int i=0;i<args.length;i++) {
                            c=c+args[i]+" ";
                        }
                        c = c.substring(0,c.length()-1);
                        if(ChatAPI.isNameBlocked(c)) {
                            API.sendErrorMessage(p,"§cDieser Name ist ungültig§8!");
                        } else {
                            u.setRoleplayName(c);
                            API.sendMessage(p,"§7Dein §eRoleplay-Name§7 lautet nun§8: §e"+c);
                        }
                    }
                }
            }
        }
        return false;
    }
}