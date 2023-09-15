package live.nerotv.projectsbase.modules.roleplay.commands;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.modules.roleplay.characters.Characters;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Objects;
import java.util.UUID;

public class Force implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(Characters.enableCharacters) {
            API.sendErrorMessage(s,"§4Fehler§8:§c dieser Befehl ist nicht mit dem Submodul \"Characters\" kompatibel§8!");
            return false;
        }
        if(cmd.getName().equalsIgnoreCase("Force")) {
            if(s.hasPermission("zyneon.team")) {
                if(args.length > 2) {
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
                    RoleplayUser user = RoleplayAPI.getRoleplayUser(uuid);
                    if(args[0].equalsIgnoreCase("job")) {
                        String c="";
                        for(int i=2;i<args.length;i++) {
                            c=c+args[i]+" ";
                        }
                        c = c.substring(0,c.length()-1);
                        user.setRoleplayJob(c);
                        API.sendMessage(s,"§7Du hast den Roleplay-Job von §a"+user.getOfflinePlayer().getName()+"§7 auf §e"+user.getRoleplayJob()+"§7 gesetzt§8!");
                    } else if(args[0].equalsIgnoreCase("name")) {
                        String c="";
                        for(int i=2;i<args.length;i++) {
                            c=c+args[i]+" ";
                        }
                        c = c.substring(0,c.length()-1);
                        user.setRoleplayName(c);
                        API.sendMessage(s,"§7Du hast den Roleplay-Name von §a"+user.getOfflinePlayer().getName()+"§7 auf §e"+user.getRoleplayName()+"§7 gesetzt§8!");
                    } else {
                        API.sendErrorMessage(s,"§4Fehler: §c/force [job/name] [Spieler] [Inhalt]");
                    }
                } else {
                    API.sendErrorMessage(s,"§4Fehler: §c/force [job/name] [Spieler] [Inhalt]");
                }
            } else {
                API.sendErrorMessage(s,API.noPerms);
            }
        }
        return false;
    }
}