package live.nerotv.projectsbase.modules.lock;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.ModuleLoader;
import live.nerotv.projectsbase.modules.lock.commands.LockHorse;
import live.nerotv.projectsbase.modules.lock.commands.UnlockHorse;
import live.nerotv.projectsbase.modules.lock.listener.HorseAccessEvent;
import live.nerotv.projectsbase.modules.lock.listener.HorseTameEvent;

public class LockMain {

    public static String lockVersion = "0.2.1 Beta (E6)";

    public static void onLoad() {
        ModuleLoader.moduleVersions.add("§aLockModule §8- §a" + lockVersion);
    }

    public static void onEnable() {
        initCommands();
        initListener();
    }

    private static void initCommands() {
        API.initCommand("LockHorse",new LockHorse());
        API.initCommand("UnlockHorse",new UnlockHorse());
    }

    private static void initListener() {
        API.initListeners(new HorseAccessEvent());
        API.initListeners(new HorseTameEvent());
    }

    public static void onDisable() {

    }
}