package live.nerotv.projectsbase;

import live.nerotv.Preloader;
import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.api.ServerAPI;
import live.nerotv.projectsbase.modules.ModuleLoader;
import live.nerotv.projectsbase.modules.roleplay.listener.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class Main {

    private static Preloader instance;
    public static Preloader getInstance() { return instance; }
    public static PluginManager PM = Bukkit.getPluginManager();
    public static String Version = "v";

    public static void onLoad() {
        API.checkConfig();
        Version = Preloader.getVersion();
        API.sendInit();
        ServerAPI.sendConsoleMessage("Die ProjectsBase4 wird geladen...");
        ServerAPI.sendConsoleMessage(" §0 ");
        ModuleLoader.loadModules();
        for(int i = 0; i < ModuleLoader.moduleVersions.size(); i++) {
            ServerAPI.sendConsoleMessage("Geladen: "+ModuleLoader.moduleVersions.get(i));
        }
        ServerAPI.sendConsoleMessage("Geladen: §aProjectsBase §8- §a"+Version);
        ServerAPI.sendConsoleMessage("Resource: §9§nhttps://nerotv.live/projectsbase");
        ServerAPI.sendConsoleMessage("Website: §9§nhttps://www.zyneonstudios.com/");
        ServerAPI.sendConsoleMessage("Author: §cnerotvlive");
        ServerAPI.sendConsoleMessage(" §0 ");
        ServerAPI.sendConsoleMessage("Die ProjectsBase4 wurde geladen!");
        API.sendInit();
    }

    public static boolean onEnable() {
        instance = Preloader.getInstance();
        API.checkConfig();
        API.sendInit();
        ServerAPI.sendConsoleMessage("Die ProjectsBase4 wird aktiviert...");
        ServerAPI.sendConsoleMessage(" §0 ");
        Version = Preloader.getVersion();
        ModuleLoader.enableModules();
        for (int i = 0; i < ModuleLoader.moduleVersions.size(); i++) {
            ServerAPI.sendConsoleMessage("Aktiviert: " + ModuleLoader.moduleVersions.get(i));
        }
        ServerAPI.sendConsoleMessage("Aktiviert: §aProjectsBase §8- §a" + Version);
        ServerAPI.sendConsoleMessage("Resource: §9§nhttps://nerotv.live/projectsbase");
        ServerAPI.sendConsoleMessage("Website: §9§nhttps://www.zyneonstudios.com/");
        ServerAPI.sendConsoleMessage("Author: §cnerotvlive");
        ServerAPI.sendConsoleMessage(" §0 ");
        ServerAPI.sendConsoleMessage("Die ProjectsBase4 wurde aktiviert!");
        API.sendInit();
        API.initCommandList();
        PlayerCommand.initBlocked();
        return true;
    }

    public static void onDisable() {
        API.sendInit();
        ServerAPI.sendConsoleMessage("Die ProjectsBase4 wird deaktiviert...");
        ServerAPI.sendConsoleMessage(" §0 ");
        ModuleLoader.disableModules();
        for(int i = 0; i < ModuleLoader.moduleVersions.size(); i++) {
            ServerAPI.sendConsoleMessage("Deaktiviert: "+ModuleLoader.moduleVersions.get(i));
        }
        ServerAPI.sendConsoleMessage("Deaktiviert: §aProjectsBase §8- §a"+Version);
        ServerAPI.sendConsoleMessage("Resource: §9§nhttps://nerotv.live/projectsbase");
        ServerAPI.sendConsoleMessage("Website: §9§nhttps://www.zyneonstudios.com/");
        ServerAPI.sendConsoleMessage("Author: §cnerotvlive");
        ServerAPI.sendConsoleMessage(" §0 ");
        ServerAPI.sendConsoleMessage("Die ProjectsBase4 wurde deaktiviert!");
        API.sendInit();
        instance = null;
        PM = null;
        Version = null;
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"save-all");
    }
}