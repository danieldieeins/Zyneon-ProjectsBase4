package live.nerotv.projectsbase.modules;

import live.nerotv.projectsbase.api.ServerAPI;

public class ModuleAPI {

    public static void sendSystemMessage(String message) {
        ServerAPI.sendConsoleMessage("§e§l[ModuleSystem]§r " + message.replace("&", "§"));
    }
}