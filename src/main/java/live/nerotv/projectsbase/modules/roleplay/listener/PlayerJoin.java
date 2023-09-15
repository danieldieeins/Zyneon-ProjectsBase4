package live.nerotv.projectsbase.modules.roleplay.listener;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.api.ServerAPI;
import live.nerotv.projectsbase.api.WarpAPI;
import live.nerotv.projectsbase.modules.essentials.commands.Vanish;
import live.nerotv.projectsbase.modules.roleplay.RoleplayMain;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.modules.roleplay.characters.Characters;
import live.nerotv.projectsbase.modules.roleplay.manager.ItemManager;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import live.nerotv.projectsbase.utils.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

import static live.nerotv.projectsbase.modules.essentials.manager.StaticManager.tablistFooter;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.setInvulnerable(false);
        e.setJoinMessage(null);
        MySQL.transfer(p);
        RoleplayUser u = RoleplayAPI.getRoleplayUser(p);
        File file = new File("plugins/ProjectsBase/Temp/" + u.getUUID().toString() + "-loginouts.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if(config.getString("User.lastLocaion.w")!=null) {
            if (Bukkit.getWorld(config.getString("User.lastLocaion.w")) == null) {
                if (WarpAPI.isWarpEnabled("warp_01")) {
                    p.teleport(WarpAPI.getWarp("warp_01"));
                } else {
                    p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                }
            }
        }
        /*if(!(API.whitelist.contains(e.getPlayer().getUniqueId().toString()))) {
            e.setJoinMessage("");
            p.kickPlayer("§cDu darfst das Projekt §8(noch?) §cnicht spielen§8!");
            return;
        }*/
        if(API.isStopping) {
            e.setJoinMessage("");
            p.sendMessage("§cDer Server startet zurzeit neu!");
            API.switchServer(p,"Lobby-1");
            p.kickPlayer("§cDer Server startet zurzeit neu!");
            return;
        }
        API.protectedPlayers.remove(p);
        if(p.getInventory().getItem(8)!=null) {
            if (p.getInventory().getItem(8).getItemMeta().getDisplayName().equals(ItemManager.backItem.getItemMeta().getDisplayName())) {
                p.getInventory().clear(8);
            }
        }
        if(Vanish.vP.contains(p)) {
            for(Player all : Bukkit.getOnlinePlayers()) {
                all.showPlayer(p);
            }
            Vanish.vP.remove(p);
        }
        if(!p.hasPermission("zyneon.team")) {
            for (Player vanished : Vanish.vP) {
                p.hidePlayer(vanished);
            }
        }
        for (Player vanished : API.protectedPlayers) {
            p.hidePlayer(vanished);
        }
        API.removeBypassPerms(p);
        p.setPlayerListFooter(tablistFooter.replace("&","§"));
        if(p.isInvisible()) {
            p.setInvisible(false);
        }
        if(p.isInvulnerable()) {
            p.setInvulnerable(false);
        }
        if(ServerAPI.isMaintenance()) {
            e.setJoinMessage("");
            if(p.hasPermission("zyneon.bypassmaintenance")) {
                u.setRoleplay(false);
                u.setLastLogin();
                RoleplayMain.setState(p);
                for(Player all:Bukkit.getOnlinePlayers()) {
                    if(all.getUniqueId()!=p.getUniqueId()) {
                        all.sendMessage("§8» §a"+p.getName());
                    }
                }
                p.sendMessage("§cDer Server befindet sich momentan in §4Wartungsarbeiten§c!");
            } else {
                p.kickPlayer("§cDer Server befindet sich momentan in §4Wartungsarbeiten§c!");
            }
        } else {
            u.setRoleplay(false);
            u.setLastLogin();
            RoleplayMain.setState(p);
            for(Player all:Bukkit.getOnlinePlayers()) {
                if(all.getUniqueId()!=p.getUniqueId()) {
                    all.sendMessage("§8» §a"+p.getName());
                }
            }
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"op "+p.getName());
        //RoleplayAPI.protectPlayer(p);
        p.setOp(false);
        if(u.getRoleplayName() != null) {
            if(u.isAutomaticRoleplay()) {
                p.performCommand("rp");
                API.sendMessage(p, "§eWICHTIG§8: §7Du bist nun im §eRoleplay§8-§eModus§8,§7 solltest du nicht roleplayen wollen§8, §7mache §a/roleplay§8!");
            } else {
                u.sendMessage("§7Willst du mit dem Roleplay loslegen§8? §7Mache §e/roleplay§8!");
            }
        } else {
            API.sendErrorMessage(p,"§cDu hast dir noch keinen Roleplay-Namen gesetzt§8!§c Tue dies mit /name§8!");
        }
        if(API.maintenance) {
            API.addBypassPerms(p);
        }
        API.dispatchConsoleCommand("recipe take "+p.getName()+" *");
        API.dispatchConsoleCommand("recipe give "+p.getName()+" *");
        if(Characters.enableCharacters) {
            u.setRoleplayName(u.getActiveChar().getName());
            u.setRoleplayJob(u.getActiveChar().getJob());
        }
    }

    /*@EventHandler
    public void preLogin(PlayerPreLoginEvent e) {
        if(!(API.whitelist.contains(e.getUniqueId().toString()))) {
            e.setKickMessage("§cDu darfst das Projekt §8(noch?) §cnicht spielen§8!");
            e.setResult(PlayerPreLoginEvent.Result.KICK_OTHER);
        }
    }*/
}