package live.nerotv;

import live.nerotv.projectsbase.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Preloader extends JavaPlugin {

    private static Preloader instance;
    private static String version = "v";
    public static String getVersion() { if(version.equalsIgnoreCase("v")) { return "0"; } else { return version; } }
    public static Preloader getInstance() { return instance; }

    @Override
    public void onLoad() {
        sendMessage("Loading ProjectsBase preloader...");
        version = getDescription().getVersion();
        sendMessage("Loading ProjectsBase...");
        Main.onLoad();
    }

    @Override
    public void onEnable() {
        sendMessage("Checking for plugin dependencies...");
        if(Main.PM.getPlugin("Vault")==null) {
            sendMessage("Couldn't find Vault, disabling economy features!");
        }
        if(Main.PM.getPlugin("LuckPerms")==null) {
            sendMessage("Couldn't find LuckPerms, disabling bypass features!");
        }
        if(Main.PM.getPlugin("ZyneonAPI")==null) {
            sendMessage("Couldn't find ZyneonAPI, disabling plugin!");
            return;
        }
        sendMessage("Enabling ProjectsBase...");
        instance = this;
        Main.onEnable();
    }

    @Override
    public void onDisable() {
        if(Main.PM.getPlugin("ZyneonAPI")==null) {
            return;
        }
        sendMessage("Disabling ProjectsBase plugin...");
        Main.onDisable();
        version = null;
        instance = null;
    }

    private void sendMessage(String message) {
        Bukkit.getConsoleSender().sendMessage("§7[ProjectsBase] §e[PRELOADER] §7"+message);
    }
}