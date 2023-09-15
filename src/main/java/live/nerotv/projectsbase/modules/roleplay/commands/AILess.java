package live.nerotv.projectsbase.modules.roleplay.commands;

import com.zyneonstudios.api.utils.Strings;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class AILess implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if(s instanceof Player) {
            Player p = (Player)s;
            RoleplayUser u = RoleplayAPI.getRoleplayUser(p);
            if(p.hasPermission("zyneon.team")) {
                if(args.length == 0) {
                    u.sendErrorMessage("§4Fehler§8: §c/ailess [entity]");
                } else {
                    Entity entity = p.getWorld().spawnEntity(p.getLocation(), EntityType.valueOf(args[0].toUpperCase()));
                    entity.setFireTicks(0);
                    entity.setGravity(false);
                    entity.setInvulnerable(true);
                    if(entity instanceof LivingEntity) {
                        LivingEntity lentity = (LivingEntity)entity;
                        lentity.setAI(false);
                        lentity.setCustomName("§0");
                        lentity.setCustomNameVisible(false);
                        lentity.setInvulnerable(true);
                        lentity.setNoDamageTicks(-1);
                    }
                }
            } else {
                u.sendErrorMessage(Strings.noPerms());
            }
        } else {
            s.sendMessage(Strings.needPlayer());
        }
        return false;
    }
}