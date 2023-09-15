package live.nerotv.projectsbase.modules.sit.api;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.sit.utils.Seat;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.HashMap;

public class SitAPI {

    public static ArrayList<Player> sittingPlayers = new ArrayList<>();
    public static HashMap<Block,Seat> seats = new HashMap<>();
    public static boolean debugStairs = true;

    public static boolean isSeat(Material type) {
        return API.isStair(type) || API.isSlab(type) || API.isCarpet(type) || type.equals(Material.SNOW);
    }

    public static boolean isSeat(Block block) {
        return isSeat(block.getType());
    }
}