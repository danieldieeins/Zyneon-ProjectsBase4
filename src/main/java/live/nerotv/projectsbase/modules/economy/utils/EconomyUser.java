package live.nerotv.projectsbase.modules.economy.utils;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.economy.EconomyMain;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.UUID;

public class EconomyUser {

    private final PlayerBalance playerBalance;
    private final OfflinePlayer offlinePlayer;
    private final Player player;
    private final UUID uuid;
    private double balance;

    public EconomyUser(UUID uuid) {
        if(EconomyMain.isEco) {
            this.playerBalance = EconomyMain.getEco().getBalance(uuid);
            this.balance = this.playerBalance.getBalance();
        } else {
            this.playerBalance = null;
            this.balance = 0;
        }
        this.offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if(Bukkit.getPlayer(uuid)!=null) {
            this.player = Bukkit.getPlayer(uuid);
        } else {
            this.player = null;
        }
        this.uuid = uuid;
    }

    public void setBalance(double balance) {
        if(EconomyMain.isEco) {
            EconomyMain.getEco().set(uuid,balance);
            this.balance = balance;
        }
    }

    public void addBalance(double balance) {
        this.setBalance(this.balance+balance);
    }

    public void removeBalance(double balance) {
        this.setBalance(this.balance-balance);
    }

    public double getBalance() {
        return this.balance;
    }

    public double getSmoothBalance() {
        double value = this.balance;
        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
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

    public PlayerBalance getPlayerBalance() {
        return this.playerBalance;
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

    public void playSound(Sound sound) {
        if(player!=null) {
            player.playSound(player.getLocation(),sound,100,100);
        }
    }
}