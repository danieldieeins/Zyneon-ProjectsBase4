package live.nerotv.projectsbase.api;

import live.nerotv.projectsbase.Main;
import live.nerotv.projectsbase.modules.essentials.manager.StaticManager;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.utils.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommandYamlParser;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class API {

    public static HashMap<UUID, User> users = new HashMap();

    public static PluginManager PM = Main.PM;
    static File Config = ConfigAPI.Config;
    static YamlConfiguration cfg = ConfigAPI.CFG;
    public static String PN() {
        if(cfg.getString("Core.Strings.Projektname")==null) {
            return "Unbekannt §8» §7";
        } else {
            return cfg.getString("Core.Strings.Projektname")+"§8 » §7";
        }
    }
    public static Collection<String> commands = new ArrayList<>();
    public static String Prefix = PN()+" §8| §7";
    public static String noPlayer = "§cDieser Spieler ist nicht zu finden!";
    public static String noPerms = "§cDazu bist du nicht berechtigt!";
    public static String noMoney = "§cDazu hast du nicht genug AP!";
    public static String needPlayer = "§cDazu §4musst§c du ein Spieler sein§4!";
    public static boolean maintenance=false;
    public static ArrayList<Player> protectedPlayers = new ArrayList<>();
    public static ArrayList<String> whitelist = new ArrayList<>();
    public static boolean isStopping=false;
    public static int RestartDay = getYearDay()+1;

    public static void clearPlayerChat(Player p) {
        for(int i= 0; i < 150; i++){
            p.sendMessage("§0");
        }
    }

    public static void checkForRestart() {
        /*if(getYearDay()==RestartDay) {
            if(!isStopping) {
                ServerAPI.scheduledShutdown();
            }
        }*/
    }

    public static void initCommand(String commandName,CommandExecutor command) {
        ServerAPI.sendConsoleMessage("§f  -> §7Lade Command \"§e"+commandName+"§7\"...");
        Objects.requireNonNull(Main.getInstance().getCommand(commandName)).setExecutor(command);
    }

    public static void initListeners(Listener listener) {
        ServerAPI.sendConsoleMessage("§f  -> §7Lade Listener in der Klasse \"§e"+listener.getClass().getSimpleName()+"§7\"...");
        PM.registerEvents(listener,Main.getInstance());
    }

    public static void checkConfig() {
        //NORMALE CONFIG
        ConfigAPI.reloadConfig(Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.Projektname","Projekt",Config,cfg);
        StaticManager.projectName = cfg.getString("Core.Strings.Projektname");
        ConfigAPI.checkEntry("Core.Strings.Projektleitung","Unbekannt",Config,cfg);
        StaticManager.projectLeading = cfg.getString("Core.Strings.Projektleitung");
        ConfigAPI.checkEntry("Core.Strings.Projektart","Unbekannt",Config,cfg);
        StaticManager.projectTheme = cfg.getString("Core.Strings.Projektart");
        ConfigAPI.checkEntry("Core.Strings.Projektversion","1.0 Alpha",Config,cfg);
        StaticManager.projectVersion = cfg.getString("Core.Strings.Projektversion");
        ConfigAPI.checkEntry("Core.Strings.Projektregeln","https://www.zyneonstudios.com/projektregeln",Config,cfg);
        StaticManager.projectRules = cfg.getString("Core.Strings.Projektregeln");

        //MYSQL
        ConfigAPI.checkEntry("Core.Settings.MySQL.enable",false,ConfigAPI.Config,ConfigAPI.CFG);
        ConfigAPI.checkEntry("Core.Settings.MySQL.host","host",ConfigAPI.Config,ConfigAPI.CFG);
        ConfigAPI.checkEntry("Core.Settings.MySQL.port","port",ConfigAPI.Config,ConfigAPI.CFG);
        ConfigAPI.checkEntry("Core.Settings.MySQL.database","datenbank",ConfigAPI.Config,ConfigAPI.CFG);
        ConfigAPI.checkEntry("Core.Settings.MySQL.username","benutzer",ConfigAPI.Config,ConfigAPI.CFG);
        ConfigAPI.checkEntry("Core.Settings.MySQL.password","passwort",ConfigAPI.Config,ConfigAPI.CFG);

        //ARRAYS
        //ConfigAPI.checkEntry("Core.Arrays.Whitelist.UUIDS",whitelist,Config,cfg);
        ConfigAPI.checkEntry("Core.Arrays.BlockedStrings.General", ChatAPI.blockedStrings,Config,cfg);
        ConfigAPI.checkEntry("Core.Arrays.BlockedStrings.Names", ChatAPI.blockedNames,Config,cfg);
        ConfigAPI.checkEntry("Core.Arrays.BlockedStrings.Jobs", ChatAPI.blockedJobs,Config,cfg);
        //whitelist = (ArrayList<String>)ConfigAPI.CFG.getList("Core.Arrays.Whitelist.UUIDS");
        ChatAPI.blockedStrings = (ArrayList<String>)cfg.getList("Core.Arrays.BlockedStrings.General");
        ChatAPI.blockedNames = (ArrayList<String>)cfg.getList("Core.Arrays.BlockedStrings.Names");
        ChatAPI.blockedJobs = (ArrayList<String>)cfg.getList("Core.Arrays.BlockedStrings.Jobs");

        //HELP
        ConfigAPI.checkEntry("Core.Strings.HelpLine01","1",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpLine02","2",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpLine03","3",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpLine04","4",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpLine05","5",Config,cfg);

        //TABLIST
        ConfigAPI.checkEntry("Core.Strings.Tablist.Image","https://nerotv.live/zyneon.png",Config,cfg);
        StaticManager.tablistImage = cfg.getString("Core.Strings.Tablist.Image");
        ConfigAPI.checkEntry("Core.Strings.Tablist.Header","Header",Config,cfg);
        StaticManager.tablistHeader = cfg.getString("Core.Strings.Tablist.Header");
        ConfigAPI.checkEntry("Core.Strings.Tablist.Footer", "Footer",Config,cfg);
        StaticManager.tablistFooter = cfg.getString("Core.Strings.Tablist.Footer");

        //MODULES
        ConfigAPI.checkEntry("Modules.Bounty.Enable",false,Config,cfg);
        StaticManager.bountyModule = cfg.getBoolean("Modules.Bounty.Enable");
        ConfigAPI.checkEntry("Modules.Economy.Enable",false,Config,cfg);
        StaticManager.ecoModule = cfg.getBoolean("Modules.Economy.Enable");
        ConfigAPI.checkEntry("Modules.Essentials.Enable",true,Config,cfg);
        StaticManager.essentialsModule = cfg.getBoolean("Modules.Essentials.Enable");
        ConfigAPI.checkEntry("Modules.Roleplay.Enable",true,Config,cfg);
        ConfigAPI.checkEntry("Modules.Roleplay.Year",1623,Config,cfg);
        RoleplayAPI.roleplayYear = cfg.getInt("Modules.Roleplay.Year");
        StaticManager.roleplayModule = cfg.getBoolean("Modules.Roleplay.Enable");
        ConfigAPI.checkEntry("Modules.PvP.Enable",false,Config,cfg);
        StaticManager.pvpModule = cfg.getBoolean("Modules.PvP.Enable");
        ConfigAPI.checkEntry("Modules.Sit.Enable",true,Config,cfg);
        StaticManager.sitModule = cfg.getBoolean("Modules.Sit.Enable");
        ConfigAPI.checkEntry("Modules.Lock.Enable",true,Config,cfg);
        StaticManager.lockModule = cfg.getBoolean("Modules.Lock.Enable");

        //TEAMHELP
        ConfigAPI.checkEntry("Core.Strings.HelpTeam101","§8=================================================",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam102","1",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam103","2",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam104","3",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam105","4",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam106","5",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam107","6",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam108","7",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam109","8",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam110","§8=================================================",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam201","§8=================================================",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam202","1",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam203","2",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam204","3",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam205","4",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam206","5",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam207","6",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam208","7",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam209","8",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam210","§8=================================================",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam301","§8=================================================",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam302","1",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam303","2",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam304","3",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam305","4",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam306","5",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam307","6",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam308","7",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam309","8",Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.HelpTeam310","§8=================================================",Config,cfg);

        //WARPGUI NAMEN
        ConfigAPI.checkEntry("Core.WarpGUI.Names.StartMenu01","Stadt 1§r",Config,cfg);
        ConfigAPI.checkEntry("Core.WarpGUI.Names.StartMenu02","Stadt 2§f",Config,cfg);
        ConfigAPI.checkEntry("Core.WarpGUI.Names.StartMenu03","Sonstiges §7(Farmwelt...)",Config,cfg);
        ConfigAPI.checkEntry("Core.WarpGUI.Names.Sub1Menu01","Warp 1§4",Config,cfg);
        ConfigAPI.checkEntry("Core.WarpGUI.Names.Sub1Menu02","Warp 2§4",Config,cfg);
        ConfigAPI.checkEntry("Core.WarpGUI.Names.Sub2Menu01","Warp 1§1",Config,cfg);
        ConfigAPI.checkEntry("Core.WarpGUI.Names.Sub2Menu02","Warp 2§1",Config,cfg);
        ConfigAPI.checkEntry("Core.WarpGUI.Names.Sub3Menu01","§eFarmwelt",Config,cfg);
        ConfigAPI.checkEntry("Core.WarpGUI.Names.Sub3Menu02","§cNether",Config,cfg);

        //WARPGUI ITEMS
        ConfigAPI.checkEntry("Core.WarpGUI.Items.StartMenu01","DIAMOND",Config,cfg);
        ConfigAPI.checkEntry("Core.WarpGUI.Items.StartMenu02","EMERALD",Config,cfg);
        ConfigAPI.checkEntry("Core.WarpGUI.Items.StartMenu03","QUARTZ",Config,cfg);
        ConfigAPI.checkEntry("Core.WarpGUI.Items.Sub1Menu01","DIAMOND_HORSE_ARMOR",Config,cfg);
        ConfigAPI.checkEntry("Core.WarpGUI.Items.Sub1Menu02","IRON_HORSE_ARMOR",Config,cfg);
        ConfigAPI.checkEntry("Core.WarpGUI.Items.Sub2Menu01","DIAMOND_HORSE_ARMOR",Config,cfg);
        ConfigAPI.checkEntry("Core.WarpGUI.Items.Sub2Menu02","IRON_HORSE_ARMOR",Config,cfg);
        ConfigAPI.checkEntry("Core.WarpGUI.Items.Sub3Menu01","IRON_PICKAXE",Config,cfg);
        ConfigAPI.checkEntry("Core.WarpGUI.Items.Sub3Menu02","NETHERITE_INGOT",Config,cfg);

        //WARPGUI WARPS
        ConfigAPI.checkEntry("Core.WarpGUI.Warps.Sub1Menu01","Warp_01",Config,cfg);
        ConfigAPI.checkEntry("Core.WarpGUI.Warps.Sub1Menu02","Warp_02",Config,cfg);
        ConfigAPI.checkEntry("Core.WarpGUI.Warps.Sub2Menu01","Warp_03",Config,cfg);
        ConfigAPI.checkEntry("Core.WarpGUI.Warps.Sub2Menu02","Warp_04",Config,cfg);
        ConfigAPI.checkEntry("Core.WarpGUI.Warps.Sub3Menu01","Warp_05",Config,cfg);
        ConfigAPI.checkEntry("Core.WarpGUI.Warps.Sub3Menu02","Warp_06",Config,cfg);

        //INIT FONT
        ConfigAPI.checkEntry("Core.Init.Line01","§9█§9█§9█§9█§9█§9█§9█§8╗§9█§9█§8╗§8░§8░§8░§9█§9█§8╗§9█§9█§9█§8╗§8░§8░§9█§9█§8╗§9█§9█§9█§9█§9█§9█§9█§8╗§8░§9█§9█§9█§9█§9█§8╗§8░§9█§9█§9█§8╗§8░§8░§9█§9█§8╗",Config,cfg);
        ConfigAPI.checkEntry("Core.Init.Line02","§8╚§8═§8═§8═§8═§9█§9█§8║§8╚§9█§9█§8╗§8░§9█§9█§8╔§8╝§9█§9█§9█§9█§8╗§8░§9█§9█§8║§9█§9█§8╔§8═§8═§8═§8═§8╝§9█§9█§8╔§8═§8═§9█§9█§8╗§9█§9█§9█§9█§8╗§8░§9█§9█§8║",Config,cfg);
        ConfigAPI.checkEntry("Core.Init.Line03","§8░§8░§9█§9█§9█§8╔§8═§8╝§8░§8╚§9█§9█§9█§9█§8╔§8╝§8░§9█§9█§8╔§9█§9█§8╗§9█§9█§8║§9█§9█§9█§9█§9█§8╗§8░§8░§9█§9█§8║§8░§8░§9█§9█§8║§9█§9█§8╔§9█§9█§8╗§9█§9█§8║",Config,cfg);
        ConfigAPI.checkEntry("Core.Init.Line04","§9█§9█§8╔§8═§8═§8╝§8░§8░§8░§8░§8╚§9█§9█§8╔§8╝§8░§8░§9█§9█§8║§8╚§9█§9█§9█§9█§8║§9█§9█§8╔§8═§8═§8╝§8░§8░§9█§9█§8║§8░§8░§9█§9█§8║§9█§9█§8║§8╚§9█§9█§9█§9█§8║",Config,cfg);
        ConfigAPI.checkEntry("Core.Init.Line05","§9█§9█§9█§9█§9█§9█§9█§8╗§8░§8░§8░§9█§9█§8║§8░§8░§8░§9█§9█§8║§8░§8╚§9█§9█§9█§8║§9█§9█§9█§9█§9█§9█§9█§8╗§8╚§9█§9█§9█§9█§9█§8╔§8╝§9█§9█§8║§8░§8╚§9█§9█§9█§8║",Config,cfg);
        ConfigAPI.checkEntry("Core.Init.Line06","§8╚§8═§8═§8═§8═§8═§8═§8╝§8░§8░§8░§8╚§8═§8╝§8░§8░§8░§8╚§8═§8╝§8░§8░§8╚§8═§8═§8╝§8╚§8═§8═§8═§8═§8═§8═§8╝§8░§8╚§8═§8═§8═§8═§8╝§8░§8╚§8═§8╝§8░§8░§8╚§8═§8═§8╝",Config,cfg);

        //SETTINGS
        ConfigAPI.checkEntry("Core.Settings.Commands.Buch",false,Config,cfg);
        StaticManager.bookCommand = cfg.getBoolean("Core.Settings.Buch");
        ConfigAPI.checkEntry("Core.Settings.RestrictedNether",false,Config,cfg);
        StaticManager.restrictedNether = cfg.getBoolean("Core.Settings.RestrictedNether");
        ConfigAPI.checkEntry("Core.Settings.Death.Chest",false,Config,cfg);
        StaticManager.deathChest = cfg.getBoolean("Core.Settings.Death.Chest");
        ConfigAPI.checkEntry("Core.Settings.DailyRewardAP.Enable",false,Config,cfg);
        StaticManager.dailyReward = cfg.getBoolean("Core.Settings.DailyRewardAP.Enable");
        ConfigAPI.checkEntry("Core.Settings.DailyRewardAP.FirstLogin",120,Config,cfg);
        StaticManager.rewardFirst = cfg.getInt("Core.Settings.DailyRewardAP.FirstLogin");
        ConfigAPI.checkEntry("Core.Settings.DailyRewardAP.DefaultLogin",60,Config,cfg);
        StaticManager.rewardDaily = cfg.getInt("Core.Settings.DailyRewardAP.DefaultLogin");
        ConfigAPI.checkEntry("Core.Settings.RequiredRulesLevel",1,Config,cfg);
        StaticManager.requiredRulesLevel = cfg.getInt("Core.Settings.RequiredRulesLevel");
        ConfigAPI.checkEntry("Core.Settings.Farmwelt.Enable",false,Config,cfg);
        StaticManager.farmworld = cfg.getBoolean("Core.Settings.Farmwelt.Enable");
        ConfigAPI.checkEntry("Core.Settings.Farmwelt.showNametags","NEVER",Config,cfg);
        StaticManager.FWNametags = cfg.getString("Core.Settings.Farmwelt.showNametags");
        ConfigAPI.checkEntry("Core.Settings.Commands.Shop.Enable",false,Config,cfg);
        StaticManager.shopCommand = cfg.getBoolean("Core.Settings.Commands.Shop.Enable");
        ConfigAPI.checkEntry("Core.Settings.Commands.Shop.MarkReward",false,Config,cfg);
        StaticManager.shopEconomy = cfg.getBoolean("Core.Settings.Commands.Shop.MarkReward");
        ConfigAPI.checkEntry("Core.Settings.Farmwelt.Name","FW1",Config,cfg);
        StaticManager.farmworldName = cfg.getString("Core.Settings.Farmwelt.Name");
        ConfigAPI.checkEntry("Core.Settings.Difficulty","NORMAL",Config,cfg);
        StaticManager.difficulty = cfg.getString("Core.Settings.Difficulty");
        ConfigAPI.checkEntry("Core.Settings.Maintenance",false,Config,cfg);
        maintenance = cfg.getBoolean("Core.Settings.Maintenance");
        ConfigAPI.checkEntry("Core.Settings.isStarted",false,Config,cfg);
        RoleplayAPI.isStarted = cfg.getBoolean("Core.Settings.isStarted");
        ConfigAPI.checkEntry("Core.Settings.Commands.Check.showIP",true,Config,cfg);
        StaticManager.checkIP = cfg.getBoolean("Core.Settings.Commands.Check.ShowIP");
        ConfigAPI.checkEntry("Core.Settings.Projectiles.Break.Glass",false,Config,cfg);
        StaticManager.projectileGlassBreak = cfg.getBoolean("Core.Settings.Projectiles.Break.Glass");
        ConfigAPI.checkEntry("Core.Settings.CustomItems.IRON_KNIFE",false,Config,cfg);
        ConfigAPI.checkEntry("Core.Settings.CustomItems.BATTLE_AXE",false,Config,cfg);
        ConfigAPI.checkEntry("Core.Settings.CustomItems.SALOON_DOOR",false,Config,cfg);
        ConfigAPI.checkEntry("Core.Settings.CustomItems.DARK_SALOON_DOOR",false,Config,cfg);
        ConfigAPI.saveConfig(Config,cfg);
    }

    public static void initCommandList() {
        for(Command all: PluginCommandYamlParser.parse(Main.getInstance())) {
            commands.add(all.getName().toLowerCase());
            for(String aliases:all.getAliases()) {
                commands.add(aliases.toLowerCase());
            }
        }
        commands.add("party");
        commands.add("give");
        commands.add("sit");
        commands.add("crawl");
        commands.add("lay");
        commands.add("lock");
        commands.add("cpublic");
        commands.add("cdonation");
        commands.add("trust");
    }

    public static String getDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy.HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static boolean isSpeedCompatible(String Check) {
        if(isNumeric(Check)) {
            int i = Integer.parseInt(Check);
            return i >= 0 && i <= 9;
        } else {
            return false;
        }
    }

    public static String getTime() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        return format.format(now);
    }

    public static int getYearDay() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public static void switchServer(Player player, String serverName) {
        try {
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(byteArray);
            out.writeUTF("Connect");
            out.writeUTF(serverName);
            player.sendPluginMessage(Main.getInstance(), "BungeeCord", byteArray.toByteArray());
        } catch (Exception ignore) {}
    }

    public static void dispatchConsoleCommand(String Command) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),Command);
    }

    public static void sendInit() {
        ConfigAPI.reloadConfig(Config,cfg);
        ServerAPI.sendConsoleMessage(Objects.requireNonNull(cfg.getString("Core.Init.Line01")));
        ServerAPI.sendConsoleMessage(Objects.requireNonNull(cfg.getString("Core.Init.Line02")));
        ServerAPI.sendConsoleMessage(Objects.requireNonNull(cfg.getString("Core.Init.Line03")));
        ServerAPI.sendConsoleMessage(Objects.requireNonNull(cfg.getString("Core.Init.Line04")));
        ServerAPI.sendConsoleMessage(Objects.requireNonNull(cfg.getString("Core.Init.Line05")));
        ServerAPI.sendConsoleMessage(Objects.requireNonNull(cfg.getString("Core.Init.Line06")));
        ServerAPI.sendConsoleMessage("§7Plugin §aProjectsBase v"+Main.Version+"§7 by §cnerotvlive§7!");
    }

    public static int getPing(Player p) {
        String v = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        if (!p.getClass().getName().equals("org.bukkit.craftbukkit." + v + ".entity.CraftPlayer")) {
            p = Bukkit.getPlayer(p.getUniqueId());
        }
        try {
            return p.getPing();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean hasINF(Player player) {
        return player.hasPermission("zyneon.creator") || player.hasPermission("zyneon.team") || player.hasPermission("zyneon.leading");
    }

    public static String getPrefix(Player player) {
        if(player.hasPermission("zyneon.team")) {
            return "Team";
        } else if(player.hasPermission("zyneon.creator")) {
            return "Creator";
        } else if(player.hasPermission("zyneon.premium")) {
            return "Premium";
        } else return "Spieler";
    }

    public static String getColorPrefix(Player player) {
        if(player.hasPermission("zyneon.team")) {
            return "§cTeam";
        } else if(player.hasPermission("zyneon.creator")) {
            return "§dCreator";
        } else if(player.hasPermission("zyneon.premium")) {
            return "§6Premium";
        } else return "§7User";
    }

    public static void addBypassPerms(Player p) {
        if(Main.PM.getPlugin("LuckPerms")!=null) {
            API.dispatchConsoleCommand("lp user " + p.getName() + " permission set lwc.* true");
            API.dispatchConsoleCommand("lp user " + p.getName() + " permission set lwct.* true");
            API.dispatchConsoleCommand("lp user " + p.getName() + " permission set lwcx.* true");
            API.dispatchConsoleCommand("lp user " + p.getName() + " permission set lwctrust.* true");
            API.dispatchConsoleCommand("lp user " + p.getName() + " permission set ChestShop.* true");
            API.dispatchConsoleCommand("lp user " + p.getName() + " permission set GChestShop.* true");
            API.dispatchConsoleCommand("lp user " + p.getName() + " permission set ZySys.Commands.Bypass true");
            API.dispatchConsoleCommand("lp user " + p.getName() + " permission set ZySys.Locks.Bypass true");
        }
    }

    public static void removeBypassPerms(Player p) {
        if(Main.PM.getPlugin("LuckPerms")!=null) {
            API.dispatchConsoleCommand("lp user " + p.getName() + " permission unset lwc.*");
            API.dispatchConsoleCommand("lp user " + p.getName() + " permission unset lwct.*");
            API.dispatchConsoleCommand("lp user " + p.getName() + " permission unset lwcx.*");
            API.dispatchConsoleCommand("lp user " + p.getName() + " permission unset lwctrust.*");
            API.dispatchConsoleCommand("lp user " + p.getName() + " permission unset ChestShop.*");
            API.dispatchConsoleCommand("lp user " + p.getName() + " permission unset GChestShop.*");
            API.dispatchConsoleCommand("lp user " + p.getName() + " permission unset ZySys.Commands.Bypass");
            API.dispatchConsoleCommand("lp user " + p.getName() + " permission unset ZySys.Locks.Bypass");
        }
    }

    public static User getUser(Player player) {
        return getUser(player.getUniqueId());
    }

    public static User getUser(UUID uuid) {
        if(users.containsKey(uuid)) {
            return users.get(uuid);
        } else {
            return new User(uuid);
        }
    }

    public static void sendRawMessage(CommandSender receiver, String message) {
        if(receiver instanceof Player) {
            getUser((Player)receiver).sendRawMessage(message.replace("&","§"));
        } else {
            receiver.sendMessage(message.replace("&","§"));
        }
    }

    public static void sendMessage(String message) {
        sendMessage(Bukkit.getConsoleSender(),message);
    }

    public static void sendMessage(CommandSender receiver, String message) {
        if(receiver instanceof Player) {
            User u = getUser((Player)receiver);
            u.playSound(Sound.ENTITY_CHICKEN_EGG);
            u.sendRawMessage(PN()+message);
        } else {
            sendRawMessage(receiver, PN() + message);
        }
    }

    public static void sendMessage(CommandSender receiver, String message, Sound sound) {
        if(receiver instanceof Player) {
            User u = getUser((Player)receiver);
            u.playSound(sound);
            u.sendRawMessage(PN()+message);
        } else {
            sendMessage(receiver,PN() + message);
        }
    }

    public static void sendErrorMessage(String message) {
        sendErrorMessage(Bukkit.getConsoleSender(),message);
    }

    public static void sendErrorMessage(CommandSender receiver, String message) {
        if(receiver instanceof Player) {
            getUser((Player)receiver).playSound(Sound.BLOCK_ANVIL_BREAK);
        }
        sendRawMessage(receiver,"§c"+message);
    }

    public static boolean isNumericPart(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isNumeric(String Check) {
        if(isNumericPart(Check)) {
            return !(Double.parseDouble(Check) > 999999998);
        } else {
            return false;
        }
    }

    public static boolean hasAvailableSlot(Player player){
        return !invFull(player);
    }

    public static boolean invFull(Player p) {
        return !Arrays.asList(p.getInventory().getStorageContents()).contains(null);
    }

    public static void changeGamemode(Player p, String GameMode) {
        if (GameMode.equalsIgnoreCase("0")) {
            p.setGameMode(org.bukkit.GameMode.SURVIVAL);
            sendMessage(p,"Du bist nun im " + getGamemode(p) + "§7!");
        }
        else if (GameMode.equalsIgnoreCase("1")) {
            p.setGameMode(org.bukkit.GameMode.CREATIVE);
            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100.0f, 100.0f);
            sendMessage(p,"Du bist nun im " + getGamemode(p) + "§7!");
        }
        else if (GameMode.equalsIgnoreCase("2")) {
            p.setGameMode(org.bukkit.GameMode.ADVENTURE);
            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100.0f, 100.0f);
            sendMessage(p,"Du bist nun im " + getGamemode(p) + "§7!");
        }
        else if (GameMode.equalsIgnoreCase("3")) {
            p.setGameMode(org.bukkit.GameMode.SPECTATOR);
            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100.0f, 100.0f);
            sendMessage(p,"Du bist nun im " + getGamemode(p) + "§7!");
        }
        else if (GameMode.equalsIgnoreCase("SURVIVAL")) {
            p.setGameMode(org.bukkit.GameMode.SURVIVAL);
            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100.0f, 100.0f);
            sendMessage(p,"Du bist nun im " + getGamemode(p) + "§7!");
        }
        else if (GameMode.equalsIgnoreCase("CREATIVE")) {
            p.setGameMode(org.bukkit.GameMode.CREATIVE);
            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100.0f, 100.0f);
            sendMessage(p,"Du bist nun im " + getGamemode(p) + "§7!");
        }
        else if (GameMode.equalsIgnoreCase("ADVENTURE")) {
            p.setGameMode(org.bukkit.GameMode.ADVENTURE);
            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100.0f, 100.0f);
            sendMessage(p,"Du bist nun im " + getGamemode(p) + "§7!");
        }
        else if (GameMode.equalsIgnoreCase("SPECTATOR")) {
            p.setGameMode(org.bukkit.GameMode.SPECTATOR);
            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100.0f, 100.0f);
            sendMessage(p,"Du bist nun im " + getGamemode(p) + "§7!");
        }
    }

    public static String getGamemode(Player p) {
        String GameMode = "";
        if (p != null) {
            if (p.getGameMode().toString().equals("SURVIVAL")) {
                GameMode = "§aÜberlebensmodus";
            }
            else if (p.getGameMode().toString().equals("CREATIVE")) {
                GameMode = "§aKreativmodus";
            }
            else if (p.getGameMode().toString().equals("ADVENTURE")) {
                GameMode = "§aAbenteuermodus";
            }
            else if (p.getGameMode().toString().equals("SPECTATOR")) {
                GameMode = "§aZuschauermodus";
            }
            else {
                GameMode = "Nothing";
            }
        }
        else {
            GameMode = "Nothing";
        }
        return GameMode;
    }

    public static boolean isWool(Material type) {
        return type.toString().toLowerCase().contains("wool");
    }

    public static boolean isTerracotta(Material type) {
        return type.toString().toLowerCase().contains("terracotta");
    }

    public static boolean isGlazedTerracotta(Material type) {
        return type.toString().toLowerCase().contains("glazed_terracotta");
    }

    public static boolean isCarpet(Material type) {
        return type.toString().toLowerCase().contains("_carpet");
    }

    public static boolean isGlass(Material type) {
        return type.toString().toLowerCase().contains("glass");
    }

    public static boolean isGlassPane(Material type) {
        return type.toString().toLowerCase().contains("glass_pane");
    }

    public static boolean isShulkerBox(Material type) {
        return type.toString().toLowerCase().contains("shulker_box");
    }

    public static boolean isConcrete(Material type) {
        return type.toString().toLowerCase().contains("concrete");
    }

    public static boolean isConcretePowder(Material type) {
        return type.toString().toLowerCase().contains("concrete_powder");
    }

    public static boolean isBed(Material type) {
        return type.toString().toLowerCase().contains("_bed");
    }

    public static boolean isBanner(Material type) {
        return type.toString().toLowerCase().contains("_banner");
    }

    public static boolean isCandle(Material type) {
        return type.toString().toLowerCase().contains("_candle");
    }

    public static boolean isStair(Material type) {
        return type.toString().toLowerCase().contains("stairs");
    }

    public static boolean isSlab(Material type) {
        return type.toString().toLowerCase().contains("_slab");
    }
}