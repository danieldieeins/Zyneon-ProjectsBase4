package live.nerotv.projectsbase.modules.sit.listener;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.sit.api.SitAPI;
import live.nerotv.projectsbase.modules.sit.utils.Seat;
import live.nerotv.projectsbase.utils.User;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class SitInteract implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock() != null) {
                if (SitAPI.isSeat(e.getClickedBlock())) {
                    Player p = e.getPlayer();
                    Location pLoc = p.getLocation();
                    User u = API.getUser(p);
                    if (SitAPI.sittingPlayers.contains(p)) {
                        SitAPI.sittingPlayers.remove(p);
                        if (p.getVehicle() != null) {
                            p.getVehicle().eject();
                            p.teleport(p.getLocation().add(0, 1, 0));
                        }
                    }
                    Location loc = e.getClickedBlock().getLocation();
                    ArmorStand chair;
                    if (API.isStair(e.getClickedBlock().getType())) {
                        Stairs chairStair = (Stairs) e.getClickedBlock().getBlockData();
                        if (chairStair.getFacing().equals(BlockFace.EAST)) {
                            loc.setYaw(90);
                            chair = (ArmorStand) loc.getWorld().spawnEntity(loc.add(0.55D, -1.2D, 0.5D), EntityType.ARMOR_STAND);
                        } else if (chairStair.getFacing().equals(BlockFace.WEST)) {
                            loc.setYaw(-90);
                            chair = (ArmorStand) loc.getWorld().spawnEntity(loc.add(0.45D, -1.2D, 0.5D), EntityType.ARMOR_STAND);
                        } else if (chairStair.getFacing().equals(BlockFace.NORTH)) {
                            loc.setYaw(1);
                            chair = (ArmorStand) loc.getWorld().spawnEntity(loc.add(0.5D, -1.2D, 0.45D), EntityType.ARMOR_STAND);
                        } else if (chairStair.getFacing().equals(BlockFace.SOUTH)) {
                            loc.setYaw(179);
                            chair = (ArmorStand) loc.getWorld().spawnEntity(loc.add(0.5D, -1.2D, 0.55D), EntityType.ARMOR_STAND);
                        } else {
                            chair = (ArmorStand) loc.getWorld().spawnEntity(loc.add(0.5D, -1.2D, 0.5D), EntityType.ARMOR_STAND);
                        }
                    } else {
                        chair = (ArmorStand) loc.getWorld().spawnEntity(loc.add(0.5D, -1.2D, 0.5D), EntityType.ARMOR_STAND);
                    }
                    chair.setGravity(false);
                    chair.setVisible(SitAPI.debugStairs);
                    chair.setInvulnerable(true);
                    chair.addPassenger(p);
                    SitAPI.sittingPlayers.add(p);
                    SitAPI.seats.put(e.getClickedBlock(),new Seat(chair,e.getClickedBlock(),p,pLoc));
                    u.sendActionbar("§7Du sitzt nun§8.");
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Block b = e.getBlock();
        if(SitAPI.seats.containsKey(b)) {
            Seat s = SitAPI.seats.get(b);
            s.destroySeat();
        }
    }
}