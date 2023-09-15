package live.nerotv.projectsbase.modules.economy;

import live.nerotv.Preloader;
import live.nerotv.projectsbase.Main;
import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.api.ServerAPI;
import live.nerotv.projectsbase.modules.ModuleLoader;
import live.nerotv.projectsbase.modules.economy.commands.Money;
import live.nerotv.projectsbase.modules.economy.commands.Pay;
import live.nerotv.projectsbase.modules.economy.utils.Economy;
import live.nerotv.projectsbase.modules.economy.utils.Ecosystem;
import live.nerotv.projectsbase.utils.MySQL;
import live.nerotv.projectsbase.modules.economy.utils.VaultEco;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;

public class EconomyMain {

    private static VaultEco vaultImpl;
    private static Economy eco = new Ecosystem();
    public static boolean isEco = false;
    public static Economy getEco() { return eco; }
    public static String economyVersion = "1.2.0 (E37)";
    public static String currencyName = "Mark";
    public static String subCurrencyName = "Pfennig";
    public static String currencyIcon = "§mM§r";

    public static void onLoad() {
        ModuleLoader.moduleVersions.add("§aEconomyModule §8- §a"+economyVersion);
    }

    public static void onEnable() {
        ServerAPI.sendConsoleMessage("[Economy] Versuche eine MySQL-Verbindung herzustellen...");
        boolean setupEconomy;
        eco = new Ecosystem();
        vaultImpl = new VaultEco();
        if(Main.PM.getPlugin("Vault") == null) {
            setupEconomy = false;
        } else {
            Bukkit.getServer().getServicesManager().register(net.milkbowl.vault.economy.Economy.class,vaultImpl,Preloader.getInstance(),ServicePriority.Normal);
            setupEconomy = true;
        }
        if(setupEconomy) {
            try {
                if (Ecosystem.checkTable()) {
                    ServerAPI.sendConsoleMessage("[Economy] §fMySQL-Verbindung wurde erfolgreich hergestellt§8!");
                    isEco = true;
                } else {
                    ServerAPI.sendConsoleMessage("[Economy] §cMySQL-Verbindung konnte nicht hergestellt werden§8!");
                    isEco = false;
                }
            } catch (NullPointerException e) {
                ServerAPI.sendConsoleMessage("[Economy] §cMySQL-Verbindung konnte nicht hergestellt werden§8!");
                isEco = false;
            }
        } else {
            ServerAPI.sendConsoleMessage("[Economy] §cMySQL-Verbindung konnte nicht hergestellt werden§8!");
            isEco = false;
        }
        API.initCommand("Money",new Money());
        API.initCommand("Pay",new Pay());
        ServerAPI.sendConsoleMessage(" §0 ");
    }

    public static void onDisable() {
        isEco = false;
        vaultImpl = null;
        eco = null;
        if(MySQL.isConnected()) {
            MySQL.disconnect();
        }
    }
}