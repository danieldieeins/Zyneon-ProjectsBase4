package live.nerotv.projectsbase.modules.roleplay.commands;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.essentials.manager.StaticManager;
import live.nerotv.projectsbase.modules.roleplay.RoleplayMain;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Roleplay implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!(s instanceof Player)) {
            s.sendMessage("§cDazu §4musst§c du ein Spieler sein§4!");
            return false;
        } else {
            Player p = (Player)s;
            RoleplayUser u = RoleplayAPI.getRoleplayUser(p);
            if (u.isRoleplay()) {
                u.setRoleplay(false);
                API.sendMessage(p, "§7Du bist nun §cnicht mehr§7 im §eRoleplay-Modus§7!");
                RoleplayMain.setState(p);
                return false;
            } else {
                if (p.getWorld().getName().equalsIgnoreCase(StaticManager.farmworldName)) {
                    u.sendErrorMessage("§cDu bist zurzeit in der Farmwelt§8!");
                    return false;
                } else if (p.getWorld().getEnvironment().equals(World.Environment.THE_END)) {
                    u.sendErrorMessage("§cDu bist zurzeit im End§8!");
                    return false;
                } else if(StaticManager.restrictedNether) {
                    if (p.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
                        u.sendErrorMessage("§cDu bist zurzeit in der Nether§8-§cFarmwelt§8!");
                    } else {
                        u.setRoleplay(true);
                        API.sendMessage(p, "§7Du hast den §eRoleplay-Modus§a aktiviert§7!");
                        RoleplayMain.setState(p);
                    }
                    return false;
                } else {
                    u.setRoleplay(true);
                    API.sendMessage(p, "§7Du hast den §eRoleplay-Modus§a aktiviert§7!");
                    RoleplayMain.setState(p);
                    return false;
                }
            }
        }
    }
}