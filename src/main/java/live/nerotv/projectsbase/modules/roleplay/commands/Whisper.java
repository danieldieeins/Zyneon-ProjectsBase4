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

public class Whisper implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s instanceof Player) {
            Player p = (Player)s;
            RoleplayUser u = RoleplayAPI.getRoleplayUser(p);
            if(Characters.enableCharacters) {
                u.setRoleplayName(u.getActiveChar().getName());
                u.setRoleplayJob(u.getActiveChar().getJob());
            }
            if(u.isRoleplay()) {
                if(u.getRoleplayName() == null) {
                    API.sendErrorMessage(p,"§cBitte setze dir zuerst deinen Roleplaynamen mit §c/name [Vorname] §7[Zwischenname] &c[Nachname].");
                } else {
                    if(args.length == 0) {
                        API.sendErrorMessage(p,"§4Fehler: §c/whisper [Nachricht]");
                    } else {
                        String MSG="";
                        for(int i=0;i<args.length;i++) {
                            MSG=MSG+args[i]+" ";
                        }
                        String Job;
                        if(u.getRoleplayJob() == null) {
                            Job = "Arbeitslos";
                        } else {
                            Job = u.getRoleplayJob();
                        }
                        String RPM = "§6RP §8● §e"+Job+"§8 ● §6"+u.getRoleplayName()+"§8 (flüstert) » §f"+MSG.toLowerCase();
                        for(Player all: Bukkit.getOnlinePlayers()) {
                            if (p.getLocation().getWorld().equals(all.getLocation().getWorld()) && p.getLocation().distance(all.getLocation()) <= 5) {
                                all.sendMessage(RPM);
                            }
                        }
                        Bukkit.getConsoleSender().sendMessage(RPM);
                    }
                }
            } else {
                API.sendErrorMessage(p,"§cBitte gehe dazu mit §4/rp§c in den Roleplay-Modus!");
            }
        } else {
            API.sendErrorMessage(s,API.needPlayer);
        }
        return false;
    }
}