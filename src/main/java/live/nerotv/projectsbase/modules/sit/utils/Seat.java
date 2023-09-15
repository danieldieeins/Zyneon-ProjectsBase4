package live.nerotv.projectsbase.modules.sit.utils;

import live.nerotv.projectsbase.modules.sit.api.SitAPI;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class Seat {

    private Location ejectLocation;
    private ArmorStand seatStand;
    private Player player;
    private Block block;

    public Seat(ArmorStand seatStand, Block block, Player player, Location ejectLocation) {
        this.ejectLocation = ejectLocation;
        this.seatStand = seatStand;
        this.player = player;
        this.block = block;
    }

    public Block getBlock() {
        return this.block;
    }

    public Player getPlayer() {
        return this.player;
    }

    public ArmorStand getSeatStand() {
        return this.seatStand;
    }

    public Location getLocation() {
        return this.block.getLocation();
    }

    public void replacePlayer(Player player) {
        SitAPI.sittingPlayers.remove(this.player);
        if(this.player.getVehicle()!=null) {
            this.player.getVehicle().eject();
            this.player.teleport(ejectLocation);
        }
        this.seatStand.remove();
        this.player = player;
        this.ejectLocation = this.player.getLocation();
        if(this.player.getVehicle()!=null) {
            this.player.getVehicle().eject();
        }
        if(!SitAPI.sittingPlayers.contains(this.player)) {
            SitAPI.sittingPlayers.add(this.player);
        }
        this.seatStand.addPassenger(this.player);
    }

    public void destroySeat() {
        SitAPI.sittingPlayers.remove(this.player);
        if(this.player.getVehicle()!=null) {
            this.player.getVehicle().eject();
            this.player.teleport(ejectLocation);
        }
        this.seatStand.remove();
        this.seatStand = null;
        this.player = null;
        SitAPI.seats.remove(this.block);
        this.block = null;
        System.gc();
    }
}