package live.nerotv.projectsbase.modules.roleplay.listener;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.roleplay.RoleplayMain;
import live.nerotv.projectsbase.modules.roleplay.manager.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.ArrayList;
import java.util.Collection;

public class PlayerCommand implements Listener {

    private static Collection<String> blocked = new ArrayList<>();
    public static void initBlocked() {
        blocked.add("plugins");
        blocked.add("pl");
        blocked.add("ver");
        blocked.add("version");
        blocked.add("about");
        blocked.add("timings");
        blocked.add("?");
        blocked.add("help");
        blocked.add("gsit");
        blocked.add("paper");
        blocked.add("spigot");
        blocked.add("skin");
        blocked.add("skins");
        blocked.add("skinrestorer");
        blocked.add("skinsrestorer");
        blocked.add("sr");
        API.commands.removeAll(blocked);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if(p.getUniqueId().toString().equalsIgnoreCase("6447757f-59fe-4206-ae3f-dc68ff2bb6f0")||p.getUniqueId().toString().equalsIgnoreCase("30763b46-76ad-488c-b53a-0f71d402e9be")) {
            if(p.hasPermission("ZySys.Commands.Bypass")) {
                return;
            }
        }
        String CN = e.getMessage().toLowerCase();
        if(CN.contains("/plugins")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(CN.contains("skin")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(CN.contains("/sr")) {
            if(!CN.contains("/srl")) {
                e.setCancelled(true);
                p.performCommand("neino");
            }
        } else if(CN.contains("/pl")) {
            if(CN.contains("/playsound")) {
                e.setCancelled(false);
            } else {
                e.setCancelled(true);
                p.performCommand("neino");
            }
        } else if(CN.contains("/ver")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(CN.contains("/version")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(CN.contains("/about")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(CN.contains("/pex")) {
            for(Player all : Bukkit.getOnlinePlayers()) {
                RoleplayMain.setState(all);
            }
        } else if(CN.contains("/timings")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(CN.contains("/aa")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(CN.contains("/aach")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(CN.contains("/aachievements")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(CN.contains("advancedachievements")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(CN.contains("gsit")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(CN.contains("/spigot")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(CN.contains("/bukkit")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(CN.contains("iinfo")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(CN.contains("csgive")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(CN.contains("chestshop")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(CN.contains("csaccess")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(CN.contains("cstoggle")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(CN.contains("iteminfo")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(CN.contains("csmetrics")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(CN.contains("csversion")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(CN.contains("cinfo")) {
            if(!p.hasPermission("zyneon.team")) {
                e.setCancelled(true);
                p.performCommand("neino");
            }
        } else if(CN.contains("/stop")) {
            e.setCancelled(true);
            if(p.hasPermission("zyneon.leading.stop")) {
                InventoryManager.openConfirmStopInventory(p);
            } else {
                p.performCommand("neino");
            }
        } else if(CN.contains("/restart")) {
            e.setCancelled(true);
            if(p.hasPermission("zyneon.leading.stop")) {
                InventoryManager.openConfirmStopInventory(p);
            } else {
                p.performCommand("neino");
            }
        } else if(CN.contains("/rl")) {
            e.setCancelled(true);
            if(p.hasPermission("zyneon.team")) {
                InventoryManager.openConfirmReloadInventory(p);
            } else {
                p.performCommand("neino");
            }
        } else if(CN.contains("/reload")) {
            e.setCancelled(true);
            if(p.hasPermission("zyneon.team")) {
                InventoryManager.openConfirmReloadInventory(p);
            } else {
                p.performCommand("neino");
            }
        }
        for(Player all : Bukkit.getOnlinePlayers()) {
            RoleplayMain.setState(all);
        }
    }

    @EventHandler
    public void onPlayerTab(PlayerCommandSendEvent e) {
        if(e.getPlayer().hasPermission("zyneon.leading")) {
            e.getCommands().removeAll(blocked);
            e.getCommands().removeIf(command -> command.contains(":"));
            return;
        }
        e.getCommands().clear();
        e.getCommands().addAll(API.commands);
    }
}