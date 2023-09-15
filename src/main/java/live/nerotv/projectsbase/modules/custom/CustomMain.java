package live.nerotv.projectsbase.modules.custom;

import live.nerotv.projectsbase.modules.ModuleLoader;

public class CustomMain {

    public static String customVersion = "1.0.0 (E1)";

    public static void onLoad() {
        ModuleLoader.moduleVersions.add("§aCustomModule §8- §a"+customVersion);
    }

    public static void onEnable() {
    }

    public static void onDisable() {

    }
}