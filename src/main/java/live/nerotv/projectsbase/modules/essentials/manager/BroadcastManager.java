package live.nerotv.projectsbase.modules.essentials.manager;

import live.nerotv.projectsbase.Main;
import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.api.ConfigAPI;
import live.nerotv.projectsbase.modules.essentials.EssentialsMain;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class BroadcastManager {

    static File Config = new File("plugins/ProjectsBase/config.yml");
    static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(Config);
    static ArrayList<String> Messages = new ArrayList<>();

    private static void saveDefaultConfig() {
        ConfigAPI.checkEntry("Core.Settings.Broadcasts.Enable",false,Config,cfg);
        ConfigAPI.checkEntry("Core.Settings.Broadcasts.SecondInterval",10,Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.Broadcasts",Messages,Config,cfg);
        ConfigAPI.saveConfig(Config,cfg);
        ConfigAPI.reloadConfig(Config,cfg);
        Messages = (ArrayList<String>)cfg.getList("Core.Strings.Broadcasts");
    }

    public static void start() {
        saveDefaultConfig();
        if(cfg.getBoolean("Core.Settings.Broadcasts.Enable")) {
            startBroadcastTimer(Bukkit.getServer().getScheduler());
        }
    }

    private static void startBroadcastTimer(BukkitScheduler scheduler) {
        int scheduleId = scheduler.scheduleSyncDelayedTask(Main.getInstance(), () -> {
            Integer size = Messages.size();
            Integer random = ThreadLocalRandom.current().nextInt(0,size);
            Bukkit.broadcastMessage(Messages.get(random).replace("&","§"));
            API.checkForRestart();
            if(!StaticManager.roleplayModule) {
                for(Player all : Bukkit.getOnlinePlayers()) {
                    EssentialsMain.setPrefix(all);
                }
            }
            startBroadcastTimer(scheduler);
        }, cfg.getLong("Core.Settings.Broadcasts.SecondInterval")*20);
    }
}