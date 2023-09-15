package live.nerotv.projectsbase.modules.sit;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.ModuleLoader;
import live.nerotv.projectsbase.modules.sit.commands.Sit;
import live.nerotv.projectsbase.modules.sit.listener.SitDismount;
import live.nerotv.projectsbase.modules.sit.listener.SitInteract;

public class SitMain {

    public static String sitVersion = "1.0.0 Beta (E2)";

    public static void onLoad() {
        ModuleLoader.moduleVersions.add("§aSitModule §8- §a"+sitVersion);
    }

    public static void onEnable() {
        initCommands();
        initListener();
    }

    private static void initCommands() {
        API.initCommand("Sit",new Sit());
    }

    private static void initListener() {
        API.initListeners(new SitDismount());
        API.initListeners(new SitInteract());
    }

    public static void onDisable() {

    }
}