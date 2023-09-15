package live.nerotv.projectsbase.modules.roleplay.commands;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class Check implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s.hasPermission("zyneon.team")) {
            if(args.length == 0) {
                API.sendErrorMessage(s,"§4Fehler: §c/check [OnlinePlayer/UUID]");
            } else {
                UUID uuid;
                if (Bukkit.getPlayer(args[0]) != null) {
                    uuid = Objects.requireNonNull(Bukkit.getPlayer(args[0])).getUniqueId();
                } else {
                    try {
                        uuid = UUID.fromString(args[0]);
                        if (RoleplayAPI.getRoleplayUser(uuid).getLastLogin()==null) {
                            API.sendErrorMessage(s, "Diese/r Spieler/in konnte nicht gefunden werden§8!");
                            return false;
                        }
                    } catch (IllegalArgumentException exception) {
                        if (RoleplayAPI.getRoleplayUser(Bukkit.getOfflinePlayer(args[0]).getUniqueId()).getLastLogin()==null) {
                            API.sendErrorMessage(s, "Diese/r Spieler/in konnte nicht gefunden werden§8!");
                            return false;
                        } else {
                            uuid = Bukkit.getOfflinePlayer(args[0]).getUniqueId();
                        }
                    }
                }
                RoleplayUser user = RoleplayAPI.getRoleplayUser(uuid);
                sendBar(s);
                s.sendMessage("§6Name§8: §f" + user.getOfflinePlayer().getName());
                s.sendMessage("§6UUID§8: §f" + user.getUUID());
                s.sendMessage("§6Roleplay§8: §f" + user.isRoleplay());
                s.sendMessage("§6Kontostand§8: §f" + API.getUser(uuid).getEconomyUser().getBalance() + "§8 (AP: " + user.getAdventurePoints() + ")");
                s.sendMessage("§6Zuletzt Eingeloggt§8: §f"+ user.getLastLogin().replace(" - ","§8 - §f").replace(":","§8:§f").replace(".","§8.§f"));
                if (Bukkit.getPlayer(args[0]) != null) {
                    s.sendMessage("§6Gamemode§8: §f" + user.getPlayer().getGameMode());
                }
                s.sendMessage("§6RP-Name§8: §f" + user.getRoleplayName());
                s.sendMessage("§6RP-Job§8: §f" + user.getRoleplayJob());
                if (Bukkit.getPlayer(args[0]) != null) {
                    s.sendMessage("§6Befindet sich bei§8: §f" + user.getPlayer().getLocation().getBlockX() + " " + user.getPlayer().getLocation().getBlockY() + " " + user.getPlayer().getLocation().getBlockZ() + " §8(" + user.getPlayer().getLocation().getWorld().getName() + "§8)");
                } else {
                    if(user.getLastLocation()!=null) {
                        s.sendMessage("§6War zuletzt bei§8: §f"+user.getLastLocation().getBlockX() + " " + user.getLastLocation().getBlockY() + " " + user.getLastLocation().getBlockZ() + " §8(" + user.getLastLocation().getWorld().getName() + "§8)");
                    }
                }
                sendBar(s);
                user.playSound(Sound.ENTITY_CHICKEN_EGG);
            }
        } else {
            API.sendErrorMessage(s,API.noPerms);
        }
        return false;
    }

    private void sendBar(CommandSender r) {
        if(r instanceof Player) {
            RoleplayUser user = RoleplayAPI.getRoleplayUser((Player)r);
            user.sendRawMessage("§8=================================================");
        } else {
            API.sendRawMessage(r,"§8=================================================");
        }
    }
}