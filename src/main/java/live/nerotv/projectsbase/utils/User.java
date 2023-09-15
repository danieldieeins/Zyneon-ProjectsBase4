package live.nerotv.projectsbase.utils;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.economy.utils.EconomyUser;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.UUID;

public class User {

    private final EconomyUser economyUser;
    private final RoleplayUser roleplayUser;
    private final OfflinePlayer offlinePlayer;
    private final Player player;
    private final UUID uuid;

    public User(UUID uuid) {
        if(API.users.containsKey(uuid)) {
            User u = API.users.get(uuid);
            this.economyUser = u.getEconomyUser();
            this.roleplayUser = u.getRoleplayUser();
            this.offlinePlayer = u.getOfflinePlayer();
            this.player = u.getPlayer();
            this.uuid = u.getUUID();
        } else {
            economyUser = new EconomyUser(uuid);
            roleplayUser = RoleplayAPI.getRoleplayUser(uuid);
            offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            if (Bukkit.getPlayer(uuid) != null) {
                player = Bukkit.getPlayer(uuid);
            } else {
                player = null;
            }
            this.uuid = uuid;
            API.users.put(uuid,this);
        }
    }

    public EconomyUser getEconomyUser() {
        return this.economyUser;
    }

    public RoleplayUser getRoleplayUser() {
        return this.roleplayUser;
    }

    public OfflinePlayer getOfflinePlayer() {
        return this.offlinePlayer;
    }

    public Player getPlayer() {
        return this.player;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public void sendRawMessage(String message) {
        if(player!=null) {
            player.sendMessage(message.replace("&&","%AND%").replace("&","§").replace("%AND%","&"));
        }
    }

    public void sendMessage(String message) {
        sendRawMessage(API.PN()+message);
        playSound(Sound.ENTITY_CHICKEN_EGG);
    }

    public void sendErrorMessage(String message) {
        sendRawMessage("§c"+message);
        playSound(Sound.BLOCK_ANVIL_BREAK);
    }

    public void sendActionbar(String message) {
        if(this.player!=null) {
            this.player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message.replace("&&","%and%").replace("&","§").replace("%and%","&")));
        }
    }

    public void playSound(Sound sound) {
        if(player!=null) {
            player.playSound(player.getLocation(),sound,100,100);
        }
    }
}
