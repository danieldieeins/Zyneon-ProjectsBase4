package live.nerotv.projectsbase.modules.lock.commands;

import com.zyneonstudios.api.utils.Strings;
import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.lock.api.LockAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnlockHorse implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s instanceof Player) {
            Player p = (Player)s;
            if(p.getVehicle()==null) {
                API.sendErrorMessage(p,"§cDazu musst du auf einem §4gesicherten§c Pferd§8,§c Esel oder Maultier sitzen§8!");
            } else {
                if(LockAPI.isLocked(p.getVehicle().getUniqueId())) {
                    if(LockAPI.getOwner(p.getVehicle().getUniqueId()).getUniqueId().equals(p.getUniqueId())) {
                        LockAPI.unlock(p.getVehicle().getUniqueId());
                        API.sendMessage(p,"§7Du hast das Entity §aerfolgreich§7 entsichert§8!");
                    } else {
                        API.sendErrorMessage(p,"§cDas gehört dir nicht§8!");
                    }
                } else {
                    API.sendErrorMessage(p,"§cDazu musst du auf einem §4gesicherten§c Pferd§8,§c Esel oder Maultier sitzen§8!");
                }
            }
        } else {
            API.sendErrorMessage(s, Strings.needPlayer());
        }
        return false;
    }
}