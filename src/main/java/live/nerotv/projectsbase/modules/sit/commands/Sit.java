package live.nerotv.projectsbase.modules.sit.commands;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.sit.api.SitAPI;
import live.nerotv.projectsbase.utils.User;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Sit implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmnd, String label, String[] args) {
        if(s instanceof Player) {
            Player p = (Player)s;
            User u = API.getUser(p);
            if(SitAPI.sittingPlayers.contains(p)) {
                SitAPI.sittingPlayers.remove(p);
                if(p.getVehicle()!=null) {
                    p.getVehicle().eject();
                    p.teleport(p.getLocation().add(0,1,0));
                }
                u.sendActionbar("§7Du sitzt nun nicht mehr§8.");
                return false;
            } else {
                if(p.isOnGround()) {
                    Location loc = p.getLocation();
                    ArmorStand chair = (ArmorStand)p.getWorld().spawnEntity(loc.subtract(0.0D, 1.7D, 0.0D), EntityType.ARMOR_STAND);
                    chair.setGravity(false);
                    chair.setVisible(SitAPI.debugStairs);
                    chair.setInvulnerable(true);
                    chair.addPassenger(p);
                    SitAPI.sittingPlayers.add(p);
                    u.sendActionbar("§7Du sitzt nun§8.");
                } else {
                    u.sendErrorMessage("§cDazu musst du auf dem Boden §nstehen§r§8!");
                }
            }
        } else {
            API.sendErrorMessage(s,API.needPlayer);
        }
        return false;
    }
}