package live.nerotv.projectsbase.modules.lock.commands;

import com.zyneonstudios.api.utils.Strings;
import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.lock.api.LockAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

public class LockHorse implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s instanceof Player) {
            Player p = (Player)s;
            if(p.getVehicle()==null) {
                API.sendErrorMessage(p,"§cDazu musst du auf einem §4gezähmten§c Pferd§8,§c Esel oder Maultier sitzen§8!");
            } else {
                if(isLockable(p.getVehicle())) {
                    Entity horse = p.getVehicle();
                    if(LockAPI.isLocked(horse.getUniqueId())) {
                        API.sendErrorMessage(p,"§cDieses Entity gehört schon jemandem!§8!");
                    } else {
                        LockAPI.setOwner(horse.getUniqueId(),p.getUniqueId());
                        API.sendMessage(p,"§7Du hast das Entity §aerfolgreich§7 gesichert§8!");
                    }
                } else {
                    API.sendErrorMessage(p,"§cDazu musst du auf einem §4gezähmten§c Pferd§8,§c Esel oder Maultier sitzen§8!");
                }
            }
        } else {
            API.sendErrorMessage(s, Strings.needPlayer());
        }
        return false;
    }

    private boolean isLockable(Entity entity) {
        if(entity.getType().equals(EntityType.HORSE)) {
            Horse horse = (Horse)entity;
            return horse.isTamed();
        } else if(entity.getType().equals(EntityType.SKELETON_HORSE)) {
            SkeletonHorse horse = (SkeletonHorse)entity;
            return horse.isTamed();
        } else if(entity.getType().equals(EntityType.ZOMBIE_HORSE)) {
            ZombieHorse horse = (ZombieHorse)entity;
            return horse.isTamed();
        } else if(entity.getType().equals(EntityType.MULE)) {
            Mule horse = (Mule)entity;
            return horse.isTamed();
        } else if(entity.getType().equals(EntityType.DONKEY)) {
            Donkey horse = (Donkey) entity;
            return horse.isTamed();
        }
        return false;
    }
}
