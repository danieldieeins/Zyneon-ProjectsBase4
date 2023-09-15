package live.nerotv.projectsbase.modules.roleplay;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.api.ConfigAPI;
import live.nerotv.projectsbase.api.ServerAPI;
import live.nerotv.projectsbase.api.WorldAPI;
import live.nerotv.projectsbase.modules.ModuleLoader;
import live.nerotv.projectsbase.modules.essentials.manager.StaticManager;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.modules.roleplay.characters.Characters;
import live.nerotv.projectsbase.modules.roleplay.commands.*;
import live.nerotv.projectsbase.modules.roleplay.listener.*;
import live.nerotv.projectsbase.modules.roleplay.manager.ChatManager;
import live.nerotv.projectsbase.modules.roleplay.manager.DeathChestManager;
import live.nerotv.projectsbase.modules.roleplay.manager.PointsManager;
import live.nerotv.projectsbase.modules.roleplay.quests.QuestsMain;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import live.nerotv.projectsbase.utils.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;

public class RoleplayMain {

    public static Scoreboard State;
    public static String roleplayVersion = "4.4.0 (E4946)";

    public static void onLoad() {
        ModuleLoader.moduleVersions.add("§aRoleplayModule§8 - §a"+roleplayVersion);
        RoleplayAPI.roleplayConfig.checkEntry("Roleplay.subModules.Characters.enable",true);
        QuestsMain.onLoad();
    }

    public static void onEnable() {
        initModule();
        initCommands();
        initListener();
        for(Player all:Bukkit.getOnlinePlayers()) {
            setState(all);
        }
        PointsManager.checkTable();
        QuestsMain.onEnable();
    }

    public static void onDisable() {
        QuestsMain.onDisable();
        State = null;
        for(Player all : Bukkit.getOnlinePlayers()) {
            RoleplayUser u = RoleplayAPI.getRoleplayUser(all);
            u.setLastLocation(all.getLocation());
        }
        if(MySQL.isConnected()) {
            MySQL.disconnect();
        }
    }

    private static void initModule() {
        ServerAPI.sendConsoleMessage("§f[Roleplay] Initialisiere Modul...");
        ServerAPI.sendConsoleMessage(" §0 ");
        ServerAPI.sendConsoleMessage("§f[Roleplay] -> Scoreboard und Ränge werden initialisiert...");
        State = Bukkit.getScoreboardManager().getNewScoreboard();
        State.registerNewTeam("0RP0");
        State.registerNewTeam("1NP0");
        State.registerNewTeam("2FW0");
        State.registerNewTeam("0RP1");
        State.registerNewTeam("1NP1");
        State.registerNewTeam("2FW1");
        State.registerNewTeam("0RP2");
        State.registerNewTeam("1NP2");
        State.registerNewTeam("2FW2");
        State.registerNewTeam("0RP3");
        State.registerNewTeam("1NP3");
        State.registerNewTeam("2FW3");
        State.getTeam("0RP0").setSuffix("§8 ● §eRP");
        State.getTeam("1NP0").setSuffix("§8 ● §7Off-RP");
        State.getTeam("2FW0").setSuffix("§8 ● §7Farmw§8.");
        State.getTeam("0RP0").setNameTagVisibility(NameTagVisibility.NEVER);
        State.getTeam("1NP0").setNameTagVisibility(NameTagVisibility.NEVER);
        State.getTeam("2FW0").setNameTagVisibility(NameTagVisibility.valueOf(StaticManager.FWNametags));
        State.getTeam("0RP0").setCanSeeFriendlyInvisibles(false);
        State.getTeam("1NP0").setCanSeeFriendlyInvisibles(false);
        State.getTeam("2FW0").setCanSeeFriendlyInvisibles(false);
        State.getTeam("0RP1").setSuffix("§8 ● §eRP");
        State.getTeam("1NP1").setSuffix("§8 ● §7Off-RP");
        State.getTeam("2FW1").setSuffix("§8 ● §7Farmw§8.");
        State.getTeam("0RP1").setNameTagVisibility(NameTagVisibility.NEVER);
        State.getTeam("1NP1").setNameTagVisibility(NameTagVisibility.NEVER);
        State.getTeam("2FW1").setNameTagVisibility(NameTagVisibility.valueOf(StaticManager.FWNametags));
        State.getTeam("0RP1").setCanSeeFriendlyInvisibles(false);
        State.getTeam("1NP1").setCanSeeFriendlyInvisibles(false);
        State.getTeam("2FW1").setCanSeeFriendlyInvisibles(false);
        State.getTeam("0RP2").setSuffix("§8 ● §eRP");
        State.getTeam("1NP2").setSuffix("§8 ● §7Off-RP");
        State.getTeam("2FW2").setSuffix("§8 ● §7Farmw§8.");
        State.getTeam("0RP2").setNameTagVisibility(NameTagVisibility.NEVER);
        State.getTeam("1NP2").setNameTagVisibility(NameTagVisibility.NEVER);
        State.getTeam("2FW2").setNameTagVisibility(NameTagVisibility.valueOf(StaticManager.FWNametags));
        State.getTeam("0RP2").setCanSeeFriendlyInvisibles(false);
        State.getTeam("1NP2").setCanSeeFriendlyInvisibles(false);
        State.getTeam("2FW2").setCanSeeFriendlyInvisibles(false);
        State.getTeam("0RP3").setSuffix("§8 ● §eRP");
        State.getTeam("1NP3").setSuffix("§8 ● §7Off-RP");
        State.getTeam("2FW3").setSuffix("§8 ● §7Farmw§8.");
        State.getTeam("0RP3").setNameTagVisibility(NameTagVisibility.NEVER);
        State.getTeam("1NP3").setNameTagVisibility(NameTagVisibility.NEVER);
        State.getTeam("2FW3").setNameTagVisibility(NameTagVisibility.valueOf(StaticManager.FWNametags));
        State.getTeam("0RP3").setCanSeeFriendlyInvisibles(false);
        State.getTeam("1NP3").setCanSeeFriendlyInvisibles(false);
        State.getTeam("2FW3").setCanSeeFriendlyInvisibles(false);
        ServerAPI.sendConsoleMessage("§f[Roleplay] -> Scoreboard und Ränge wurde initialisiert!");
        ServerAPI.sendConsoleMessage(" §0 ");
        ServerAPI.sendConsoleMessage("§f[Roleplay] -> Farmwelt und Dimensionen werden eingestellt...");
        if(StaticManager.restrictedNether) {
            WorldAPI.createWorld("FWN", World.Environment.NETHER, WorldType.NORMAL,true);
        }
        if(StaticManager.farmworld) {
            WorldAPI.createWorld(StaticManager.farmworldName,World.Environment.NORMAL,WorldType.LARGE_BIOMES,true);
            File FarmFile = new File("plugins/ProjectsBase/Warps/farmwelt.yml");
            YamlConfiguration FF = YamlConfiguration.loadConfiguration(FarmFile);
            FF.set("Warp.World", ConfigAPI.CFG.getString("Core.Settings.Farmwelt.Name"));
            ConfigAPI.saveConfig(FarmFile,FF);
        }
        ServerAPI.sendConsoleMessage("§f[Roleplay] Farmwelt und Dimensionen wurden eingestellt!");
        ServerAPI.sendConsoleMessage(" §0 ");
        ServerAPI.sendConsoleMessage("§f[Roleplay] Sub-Module werden geladen!");
        Characters.initCharacters();
        ServerAPI.sendConsoleMessage("§f[Roleplay] Sub-Module werden geladen!");
        ServerAPI.sendConsoleMessage(" §0 ");
        ServerAPI.sendConsoleMessage("§f[Roleplay] Modul initialisiert!");
        ServerAPI.sendConsoleMessage(" §0 ");
    }

    private static void initListener() {
        ServerAPI.sendConsoleMessage("§f[Roleplay] Lade Listener...");
        API.initListeners(new EntitySpawn());
        API.initListeners(new PlayerJoin());
        API.initListeners(new PlayerMove());
        API.initListeners(new PlayerQuit());
        API.initListeners(new PlayerInventory());
        API.initListeners(new PlayerDamage());
        API.initListeners(new PlayerCommand());
        API.initListeners(new PlayerInteract());
        API.initListeners(new WorldSignChange());
        API.initListeners(new PlayerCraft());
        API.initListeners(new ProjectileHit());
        API.initListeners(new WorldChange());
        API.initListeners(new ChatManager());
        API.initListeners(new DeathChestManager());
        ServerAPI.sendConsoleMessage("§f[Roleplay] Listener geladen!");
        ServerAPI.sendConsoleMessage(" §0 ");
    }

    private static void initCommands() {
        ServerAPI.sendConsoleMessage("§f[Roleplay] Lade Commands...");
        API.initCommand("AdventurePoints",new AdventurePoints());
        API.initCommand("Achievements",new Achievements());
        API.initCommand("AILess",new AILess());
        API.initCommand("Autoclose",new Autoclose());
        API.initCommand("Brieftasche",new Brieftasche());
        API.initCommand("Buch",new Buch());
        API.initCommand("Check",new Check());
        API.initCommand("DiscordLink",new DiscordLink());
        API.initCommand("Fix",new Fix());
        API.initCommand("Force",new Force());
        API.initCommand("Job",new Job());
        API.initCommand("Do",new Me());
        API.initCommand("Name",new Name());
        API.initCommand("RedstoneLock",new RedstoneLock());
        API.initCommand("Roleplay",new Roleplay());
        API.initCommand("RoleplayTeleport",new RoleplayTeleport());
        API.initCommand("Rucksack",new Rucksack());
        API.initCommand("Settings",new Settings());
        API.initCommand("Shop",new Shop());
        API.initCommand("Shout",new Shout());
        API.initCommand("Start",new Start());
        API.initCommand("Team",new Team());
        API.initCommand("Warp",new Warp());
        API.initCommand("Whisper",new Whisper());
        ServerAPI.sendConsoleMessage("§f[Roleplay] Commands geladen!");
        ServerAPI.sendConsoleMessage(" §0 ");
    }

    public static void setState(Player p) {
        RoleplayUser u = RoleplayAPI.getRoleplayUser(p);
        if (StaticManager.farmworld) {
            Bukkit.getWorld(StaticManager.farmworldName).setDifficulty(Difficulty.NORMAL);
        }
        Bukkit.getWorlds().get(0).setDifficulty(Difficulty.valueOf(StaticManager.difficulty));
        String Prefix = API.getColorPrefix(p) + " §8● §f";

        if (p.getWorld().getName().equalsIgnoreCase(StaticManager.farmworldName)) {
            if (p.hasPermission("zyneon.team")) {
                State.getTeam("2FW0").addPlayer(p);
                p.setDisplayName(Prefix + p.getName() + State.getTeam("2FW0").getSuffix());
                p.setPlayerListName(p.getDisplayName());
            } else if (p.hasPermission("zyneon.creator")) {
                State.getTeam("2FW1").addPlayer(p);
                p.setDisplayName(Prefix + p.getName() + State.getTeam("2FW0").getSuffix());
                p.setPlayerListName(p.getDisplayName());
            } else if (p.hasPermission("zyneon.premium")) {
                State.getTeam("2FW2").addPlayer(p);
                p.setDisplayName(Prefix + p.getName() + State.getTeam("2FW0").getSuffix());
                p.setPlayerListName(p.getDisplayName());
            } else {
                State.getTeam("2FW3").addPlayer(p);
                p.setDisplayName(Prefix + p.getName() + State.getTeam("2FW0").getSuffix());
                p.setPlayerListName(p.getDisplayName());
            }
            return;
        }

        if (StaticManager.restrictedNether&&p.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
            if (p.hasPermission("zyneon.team")) {
                State.getTeam("2FW0").addPlayer(p);
                p.setDisplayName(Prefix + p.getName() + State.getTeam("2FW0").getSuffix());
                p.setPlayerListName(p.getDisplayName());
            } else if (p.hasPermission("zyneon.creator")) {
                State.getTeam("2FW1").addPlayer(p);
                p.setDisplayName(Prefix + p.getName() + State.getTeam("2FW0").getSuffix());
                p.setPlayerListName(p.getDisplayName());
            } else if (p.hasPermission("zyneon.premium")) {
                State.getTeam("2FW2").addPlayer(p);
                p.setDisplayName(Prefix + p.getName() + State.getTeam("2FW0").getSuffix());
                p.setPlayerListName(p.getDisplayName());
            } else {
                State.getTeam("2FW3").addPlayer(p);
                p.setDisplayName(Prefix + p.getName() + State.getTeam("2FW0").getSuffix());
                p.setPlayerListName(p.getDisplayName());
            }
            return;
        }

        if (p.getWorld().getEnvironment().equals(World.Environment.THE_END)) {
            if (p.hasPermission("zyneon.team")) {
                State.getTeam("2FW0").addPlayer(p);
                p.setDisplayName(Prefix + p.getName() + State.getTeam("2FW0").getSuffix());
                p.setPlayerListName(p.getDisplayName());
            } else if (p.hasPermission("zyneon.creator")) {
                State.getTeam("2FW1").addPlayer(p);
                p.setDisplayName(Prefix + p.getName() + State.getTeam("2FW0").getSuffix());
                p.setPlayerListName(p.getDisplayName());
            } else if (p.hasPermission("zyneon.premium")) {
                State.getTeam("2FW2").addPlayer(p);
                p.setDisplayName(Prefix + p.getName() + State.getTeam("2FW0").getSuffix());
                p.setPlayerListName(p.getDisplayName());
            } else {
                State.getTeam("2FW3").addPlayer(p);
                p.setDisplayName(Prefix + p.getName() + State.getTeam("2FW0").getSuffix());
                p.setPlayerListName(p.getDisplayName());
            }
            return;
        }

        if (p.hasPermission("zyneon.team")) {
            if (u.isRoleplay()) {
                State.getTeam("0RP0").addPlayer(p);
                p.setDisplayName(Prefix + p.getName() + State.getTeam("0RP0").getSuffix());
                p.setPlayerListName(p.getDisplayName());
            } else {
                State.getTeam("1NP0").addPlayer(p);
                p.setDisplayName(Prefix + p.getName() + State.getTeam("1NP0").getSuffix());
                p.setPlayerListName(p.getDisplayName());
            }
        } else if (p.hasPermission("zyneon.creator")) {
            if (u.isRoleplay()) {
                State.getTeam("0RP1").addPlayer(p);
                p.setDisplayName(Prefix + p.getName() + State.getTeam("0RP1").getSuffix());
                p.setPlayerListName(p.getDisplayName());
            } else {
                State.getTeam("1NP1").addPlayer(p);
                p.setDisplayName(Prefix + p.getName() + State.getTeam("1NP1").getSuffix());
                p.setPlayerListName(p.getDisplayName());
            }
        } else if (p.hasPermission("zyneon.premium")) {
            if (u.isRoleplay()) {
                State.getTeam("0RP2").addPlayer(p);
                p.setDisplayName(Prefix + p.getName() + State.getTeam("0RP2").getSuffix());
                p.setPlayerListName(p.getDisplayName());
            } else {
                State.getTeam("1NP2").addPlayer(p);
                p.setDisplayName(Prefix + p.getName() + State.getTeam("1NP2").getSuffix());
                p.setPlayerListName(p.getDisplayName());
            }
        } else {
            if (u.isRoleplay()) {
                State.getTeam("0RP3").addPlayer(p);
                p.setDisplayName(Prefix + p.getName() + State.getTeam("0RP3").getSuffix());
                p.setPlayerListName(p.getDisplayName());
            } else {
                State.getTeam("1NP3").addPlayer(p);
                p.setDisplayName(Prefix + p.getName() + State.getTeam("1NP3").getSuffix());
                p.setPlayerListName(p.getDisplayName());
            }
        }
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setScoreboard(State);
        }
    }
}