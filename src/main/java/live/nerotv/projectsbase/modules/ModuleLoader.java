package live.nerotv.projectsbase.modules;

import live.nerotv.projectsbase.api.ServerAPI;
import live.nerotv.projectsbase.modules.bounty.BountyMain;
import live.nerotv.projectsbase.modules.economy.EconomyMain;
import live.nerotv.projectsbase.modules.essentials.EssentialsMain;
import live.nerotv.projectsbase.modules.essentials.manager.StaticManager;
import live.nerotv.projectsbase.modules.lock.LockMain;
import live.nerotv.projectsbase.modules.roleplay.RoleplayMain;
import live.nerotv.projectsbase.modules.sit.SitMain;

import java.util.ArrayList;

public class ModuleLoader {

    public static ArrayList<String> moduleVersions = new ArrayList<>();

    public static void loadModules() {
        if(StaticManager.essentialsModule) {
            ModuleAPI.sendSystemMessage("Lade EssentialsModule...");
            EssentialsMain.onLoad();
            ModuleAPI.sendSystemMessage("EssentialsModule erfolgreich geladen!");
            ServerAPI.sendConsoleMessage(" §0 ");
        }
        if(StaticManager.sitModule) {
            ModuleAPI.sendSystemMessage("Lade SitModule...");
            SitMain.onLoad();
            ModuleAPI.sendSystemMessage("SitModule erfolgreich geladen!");
            ServerAPI.sendConsoleMessage(" §0 ");
        }
        if(StaticManager.ecoModule) {
            ModuleAPI.sendSystemMessage("Lade EconomyModule...");
            EconomyMain.onLoad();
            ModuleAPI.sendSystemMessage("EconomyModule erfolgreich geladen!");
            ServerAPI.sendConsoleMessage(" §0 ");
        }
        if(StaticManager.roleplayModule) {
            ModuleAPI.sendSystemMessage("Lade RoleplayModule...");
            RoleplayMain.onLoad();
            ModuleAPI.sendSystemMessage("RoleplayModule erfolgreich geladen!");
            ServerAPI.sendConsoleMessage(" §0 ");
        }
        if(StaticManager.bountyModule) {
            ModuleAPI.sendSystemMessage("Lade BountyModule...");
            BountyMain.onLoad();
            ModuleAPI.sendSystemMessage("BountyModule erfolgreich geladen!");
            ServerAPI.sendConsoleMessage(" §0 ");
        }
        if(StaticManager.lockModule) {
            ModuleAPI.sendSystemMessage("Lade LockModule...");
            LockMain.onLoad();
            ModuleAPI.sendSystemMessage("LockModule erfolgreich geladen!");
            ServerAPI.sendConsoleMessage(" §0 ");
        }
    }

    public static void enableModules() {
        if(StaticManager.essentialsModule) {
            ModuleAPI.sendSystemMessage("Aktiviere EssentialsModule...");
            EssentialsMain.onEnable();
            ModuleAPI.sendSystemMessage("EssentialsModule erfolgreich aktiviert!");
            ServerAPI.sendConsoleMessage(" §0 ");
        }
        if(StaticManager.sitModule) {
            ModuleAPI.sendSystemMessage("Aktiviere SitModule...");
            SitMain.onEnable();
            ModuleAPI.sendSystemMessage("SitModule erfolgreich aktiviert!");
            ServerAPI.sendConsoleMessage(" §0 ");
        }
        if(StaticManager.ecoModule) {
            ModuleAPI.sendSystemMessage("Aktiviere EconomyModule...");
            EconomyMain.onEnable();
            ModuleAPI.sendSystemMessage("EconomyModule erfolgreich aktiviert!");
            ServerAPI.sendConsoleMessage(" §0 ");
        }
        if(StaticManager.roleplayModule) {
            ModuleAPI.sendSystemMessage("Aktiviere RoleplayModule...");
            RoleplayMain.onEnable();
            ModuleAPI.sendSystemMessage("RoleplayModule erfolgreich aktiviert!");
            ServerAPI.sendConsoleMessage(" §0 ");
        }
        if(StaticManager.bountyModule) {
            ModuleAPI.sendSystemMessage("Aktiviere BountyModule...");
            BountyMain.onEnable();
            ModuleAPI.sendSystemMessage("BountyModule erfolgreich aktiviert!");
            ServerAPI.sendConsoleMessage(" §0 ");
        }
        if(StaticManager.lockModule) {
            ModuleAPI.sendSystemMessage("Aktiviere LockModule...");
            LockMain.onEnable();
            ModuleAPI.sendSystemMessage("LockModule erfolgreich aktiviert!");
            ServerAPI.sendConsoleMessage(" §0 ");
        }
    }

    public static void disableModules() {
        if(StaticManager.essentialsModule) {
            ModuleAPI.sendSystemMessage("Deaktiviere EssentialsModule...");
            EssentialsMain.onDisable();
            ModuleAPI.sendSystemMessage("EssentialsModule erfolgreich deaktiviert!");
            ServerAPI.sendConsoleMessage(" §0 ");
        }
        if(StaticManager.sitModule) {
            ModuleAPI.sendSystemMessage("Deaktiviere SitModule...");
            SitMain.onDisable();
            ModuleAPI.sendSystemMessage("SitModule erfolgreich deaktiviert!");
            ServerAPI.sendConsoleMessage(" §0 ");
        }
        if(StaticManager.ecoModule) {
            ModuleAPI.sendSystemMessage("Deaktiviere EconomyModule...");
            EconomyMain.onDisable();
            ModuleAPI.sendSystemMessage("EconomyModule erfolgreich deaktiviert!");
            ServerAPI.sendConsoleMessage(" §0 ");
        }
        if(StaticManager.roleplayModule) {
            ModuleAPI.sendSystemMessage("Deaktiviere RoleplayModule...");
            RoleplayMain.onDisable();
            ModuleAPI.sendSystemMessage("RoleplayModule erfolgreich deaktiviert!");
            ServerAPI.sendConsoleMessage(" §0 ");
        }
        if(StaticManager.bountyModule) {
            ModuleAPI.sendSystemMessage("Deaktiviere BountyModule...");
            BountyMain.onDisable();
            ModuleAPI.sendSystemMessage("BountyModule erfolgreich deaktivert!");
            ServerAPI.sendConsoleMessage(" §0 ");
        }
        if(StaticManager.lockModule) {
            ModuleAPI.sendSystemMessage("Deaktiviere LockModule...");
            LockMain.onDisable();
            ModuleAPI.sendSystemMessage("LockModule erfolgreich deaktivert!");
            ServerAPI.sendConsoleMessage(" §0 ");
        }
    }
}