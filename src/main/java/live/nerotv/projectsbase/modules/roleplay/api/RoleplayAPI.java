package live.nerotv.projectsbase.modules.roleplay.api;

import com.zyneonstudios.api.paper.configuration.Config;
import com.zyneonstudios.api.paper.utils.Countdown;
import live.nerotv.projectsbase.Main;
import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.essentials.commands.Vanish;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class RoleplayAPI {

    public static Config roleplayConfig = new Config("plugins/ProjectsBase/roleplay/config.yml");
    public static ArrayList<UUID> roleplayPlayers = new ArrayList<>();
    public static HashMap<UUID,RoleplayUser> roleplayUsers = new HashMap();
    public static boolean isStarted = false;
    public static int roleplayYear = 0;

    public static void protectPlayer(Player player) {
        RoleplayUser u = getRoleplayUser(player);
        if(u.getProtectionTime()>0) {
            if (!API.protectedPlayers.contains(player)) {
                API.protectedPlayers.add(player);
            }
            if (!Vanish.vP.contains(player)) {
                Vanish.vP.add(player);
            }
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.hidePlayer(player);
            }
            player.setInvulnerable(true);
            API.sendMessage(player, "§7Du hast nun §a" + u.getProtectionTime() + " Sekunden §eSpawnprotection§8!");
            new Countdown(u.getProtectionTime(), Main.getInstance()) {
                @Override
                public void count(int current) {
                    if (current == 0) {
                        API.protectedPlayers.remove(player);
                        API.sendMessage(player, "§7Du hast nun §ckeine §eSpawnprotection§c mehr§8!");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 100);
                        player.setInvulnerable(false);
                        Vanish.vP.remove(player);
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.showPlayer(player);
                        }
                    }
                }
            }.start();
        }
    }

    public static RoleplayUser getRoleplayUser(UUID uuid) {
        if(roleplayUsers.containsKey(uuid)) {
            return roleplayUsers.get(uuid);
        } else {
            return new RoleplayUser(uuid);
        }
    }

    public static RoleplayUser getRoleplayUser(Player player) {
        return getRoleplayUser(player.getUniqueId());
    }
}