package live.nerotv.projectsbase.modules.custom.items;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Throwable {

    private ItemStack item;
    private Player thrower = null;
    private final Location origin;

    public Throwable(ItemStack itemStack, Location origin) {
        this.item = itemStack;
        this.origin = origin;
    }

    public Throwable(ItemStack itemStack, Player thrower) {
        this.item = itemStack;
        this.thrower = thrower;
        this.origin = thrower.getLocation();
    }

    public void throwItem() {
        ArmorStand throwThing = origin.getWorld().spawn(origin,ArmorStand.class);
        throwThing.setGravity(false);
        throwThing.setInvulnerable(true);
        throwThing.setVisible(false);
        throwThing.setHelmet(item);
        if(thrower!=null) {
            Location l = thrower.getLocation();
            l.setYaw(l.getYaw()+90);
            Arrow throwBase = thrower.launchProjectile(Arrow.class, l.getDirection());
            throwBase.addPassenger(throwThing);
        } else {
            
        }
    }

    public Player getThrower() {
        return this.thrower;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public void remove() {
        this.item = null;
        System.gc();
    }
}