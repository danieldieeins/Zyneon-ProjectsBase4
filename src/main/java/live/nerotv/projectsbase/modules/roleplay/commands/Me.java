package live.nerotv.projectsbase.modules.roleplay.commands;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.modules.roleplay.characters.Characters;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Me implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Me")) {
            if(!(s instanceof Player)) {
                s.sendMessage("§cDazu §4musst§c du ein Spieler sein§4!");
            } else {
                Player p = (Player)s;
                RoleplayUser u = RoleplayAPI.getRoleplayUser(p);
                if(Characters.enableCharacters) {
                    u.setRoleplayName(u.getActiveChar().getName());
                    u.setRoleplayJob(u.getActiveChar().getJob());
                }
                if(args.length == 0) {
                    API.sendErrorMessage(s,"§4Fehler: §c/do [Aktion]");
                } else {
                    if(u.isRoleplay()) {
                        if(u.getRoleplayName() == null) {
                            API.sendErrorMessage(p,"§cBitte setze dir zuerst deinen Roleplaynamen mit §c/name [Vorname] §7[Zwischenname] &c[Nachname].");
                        } else {
                            String m="";
                            for(int i=0;i<args.length;i++) {
                                m=m+args[i]+" ";
                            }
                            for(Player all:Bukkit.getOnlinePlayers()) {
                                if (p.getLocation().getWorld().equals(all.getLocation().getWorld()) && p.getLocation().distance(all.getLocation()) <= 29) {
                                    all.sendMessage("§8* §e"+u.getRoleplayName()+"§7 "+m+"§8*");
                                }
                            }
                        }
                    } else {
                        API.sendErrorMessage(p,"§cBitte gehe dazu mit §4/rp§c in den Roleplay-Modus!");
                    }
                }
            }
        }
        return false;
    }
}
