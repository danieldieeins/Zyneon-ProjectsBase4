package live.nerotv.projectsbase.modules.essentials;

import live.nerotv.Preloader;
import live.nerotv.projectsbase.Main;
import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.api.ConfigAPI;
import live.nerotv.projectsbase.api.ServerAPI;
import live.nerotv.projectsbase.modules.ModuleLoader;
import live.nerotv.projectsbase.modules.essentials.commands.*;
import live.nerotv.projectsbase.modules.essentials.listener.PlayerChat;
import live.nerotv.projectsbase.modules.essentials.listener.PlayerJoin;
import live.nerotv.projectsbase.modules.essentials.listener.PlayerQuit;
import live.nerotv.projectsbase.modules.essentials.manager.BroadcastManager;
import live.nerotv.projectsbase.modules.essentials.manager.StaticManager;
import live.nerotv.projectsbase.utils.Receiver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Scoreboard;

public class EssentialsMain {

    static Scoreboard Scoreboard;
    public static String essentialsVersion = "4.2.0 (E173)";

    public static void onLoad() {
        ModuleLoader.moduleVersions.add("§aEssentialsModule §8- §a"+essentialsVersion);
    }

    public static void onEnable() {
        initModule();
        initCommands();
        initListener();
        if (ConfigAPI.CFG.getBoolean("Core.Settings.Maintenance")) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                if (all.hasPermission("zyneon.bypassmaintenance")) {
                    all.sendMessage("§cDer Server befindet sich momentan in §4Wartungsarbeiten§c!");
                } else {
                    all.kickPlayer("§cDer Server befindet sich nun in §4Wartungsarbeiten§c!");
                }
            }
        }
    }

    public static void onDisable() {
        for(Player all : Bukkit.getOnlinePlayers()) {
            API.switchServer(all,"Lobby-1");
        }
        Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel((Plugin)Preloader.getInstance(),"base:bungee",new Receiver());
        Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel((Plugin)Preloader.getInstance(), "BungeeCord");
    }

    private static void initModule() {
        ServerAPI.sendConsoleMessage("§f[Essentials] Initialisiere Modul...");
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(Main.getInstance(),"base:bungee",new Receiver());
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(Main.getInstance(), "BungeeCord");
        if(!StaticManager.roleplayModule) {
            Scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            Scoreboard.registerNewTeam("00000Team");
            Scoreboard.registerNewTeam("01Creator");
            Scoreboard.registerNewTeam("02Premium");
            Scoreboard.registerNewTeam("03Spieler");
            Scoreboard.getTeam("00000Team").setPrefix("§cTeam §8● §f");
            Scoreboard.getTeam("01Creator").setPrefix("§dCreator §8● §f");
            Scoreboard.getTeam("02Premium").setPrefix("§6Premium §8● §f");
            Scoreboard.getTeam("03Spieler").setPrefix("§7User §8● §f");
            Scoreboard.getTeam("00000Team").setCanSeeFriendlyInvisibles(false);
            Scoreboard.getTeam("01Creator").setCanSeeFriendlyInvisibles(false);
            Scoreboard.getTeam("02Premium").setCanSeeFriendlyInvisibles(false);
            Scoreboard.getTeam("03Spieler").setCanSeeFriendlyInvisibles(false);
            Scoreboard.getTeam("00000Team").setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, org.bukkit.scoreboard.Team.OptionStatus.NEVER);
            Scoreboard.getTeam("01Creator").setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, org.bukkit.scoreboard.Team.OptionStatus.NEVER);
            Scoreboard.getTeam("02Premium").setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, org.bukkit.scoreboard.Team.OptionStatus.NEVER);
            Scoreboard.getTeam("03Spieler").setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, org.bukkit.scoreboard.Team.OptionStatus.NEVER);
        }
        BroadcastManager.start();
        for(Player all : Bukkit.getOnlinePlayers()) {
            setPrefix(all);
        }
        ServerAPI.sendConsoleMessage("§f[Essentials] Modul initialisiert!");
    }

    private static void initCommands() {
        ServerAPI.sendConsoleMessage("§f[Essentials] Lade Commands...");
        API.initCommand("Tell",new Tell());
        API.initCommand("Author",new Author());
        API.initCommand("Gamemode",new Gamemode());
        API.initCommand("Teleport",new Teleport());
        API.initCommand("SRL",new SRL());
        API.initCommand("Heal",new Heal());
        API.initCommand("Kill",new Kill());
        API.initCommand("Feed",new Feed());
        API.initCommand("Clearchat",new Clearchat());
        API.initCommand("Sudo",new Sudo());
        API.initCommand("Time",new Time());
        API.initCommand("Day",new Day());
        API.initCommand("Night",new Night());
        API.initCommand("Broadcast",new Broadcast());
        API.initCommand("?",new Help());
        API.initCommand("Difficulty",new Difficulty());
        API.initCommand("Fly",new Fly());
        API.initCommand("Weather",new Weather());
        API.initCommand("Rain",new Rain());
        API.initCommand("Hat",new Hat());
        API.initCommand("Sun",new Sun());
        API.initCommand("Say",new Say());
        API.initCommand("Thunder",new Thunder());
        API.initCommand("Disconnect",new Disconnect());
        API.initCommand("Maintenance",new Maintenance());
        API.initCommand("Vanish",new Vanish());
        API.initCommand("God",new God());
        API.initCommand("Ping",new Ping());
        API.initCommand("Speed",new Speed());
        API.initCommand("CustomModelData",new CustomModelData());
        API.initCommand("Enderchest",new Enderchest());
        API.initCommand("World",new World());
        API.initCommand("Item",new Item());
        API.initCommand("Invsee",new Invsee());
        API.initCommand("MOC",new MOC());
        ServerAPI.sendConsoleMessage("§f[Essentials] Commands geladen!");
        ServerAPI.sendConsoleMessage(" §0 ");
    }

    private static void initListener() {
        ServerAPI.sendConsoleMessage("§f[Essentials] Lade Listener...");
        if(!StaticManager.roleplayModule) {
            API.initListeners(new PlayerChat());
            API.initListeners(new PlayerJoin());
            API.initListeners(new PlayerQuit());
        }
        ServerAPI.sendConsoleMessage("§f[Essentials] Listener geladen!");
        ServerAPI.sendConsoleMessage(" §0 ");
    }

    public static void setPrefix(Player player) {
        if(!StaticManager.roleplayModule) {
            String Name = player.getName();
            player.setScoreboard(Scoreboard);
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission("zyneon.team")) {
                    Scoreboard.getTeam("00000Team").addPlayer(p);
                    p.setDisplayName(Scoreboard.getTeam("00000Team").getPrefix() + Name);
                } else if (p.hasPermission("zyneon.creator")) {
                    Scoreboard.getTeam("01Creator").addPlayer(p);
                    p.setDisplayName(Scoreboard.getTeam("01Creator").getPrefix() + Name);
                } else if (p.hasPermission("zyneon.premium")) {
                    Scoreboard.getTeam("02Premium").addPlayer(p);
                    p.setDisplayName(Scoreboard.getTeam("02Premium").getPrefix() + Name);
                } else {
                    Scoreboard.getTeam("03Spieler").addPlayer(p);
                    p.setDisplayName(Scoreboard.getTeam("03Spieler").getPrefix() + Name);
                }
            }
            player.setScoreboard(Scoreboard);
        }
    }
}