package live.nerotv.projectsbase.modules.roleplay.commands;

import live.nerotv.projectsbase.Main;
import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.api.ConfigAPI;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.utils.Countdown;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Start implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("start")) {
            if(s instanceof Player) {
                Player p = (Player)s;
                if(p.hasPermission("zyneon.leading")) {
                    if(RoleplayAPI.isStarted) {
                        API.sendErrorMessage(p,"§cDas Projekt ist bereits gestartet§8!");
                    } else {
                        new Countdown(15, Main.getInstance()) {
                            @Override
                            public void count(int current) {
                                if(current < 11&&current > 0) {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a subtitle \"§8...§7startet das Projekt§8!\"");
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title \"§7In §e" + current + " Sekunden§8...\"");
                                    Bukkit.broadcastMessage(API.PN()+"§7Das Projekt startet in §e"+current+" Sekunden§8!");
                                    for (Player all : Bukkit.getOnlinePlayers()) {
                                        all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,100,100);
                                    }
                                } else if(current == 0) {
                                    RoleplayAPI.isStarted = true;
                                    ConfigAPI.CFG.set("Core.Settings.isStarted",true);
                                    ConfigAPI.saveConfig(ConfigAPI.Config,ConfigAPI.CFG);
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a subtitle \"§8...§7viel Spaß beim Spielen§8!\"");
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title \"§fDas Projekt ist gestartet§8...\"");
                                    Bukkit.broadcastMessage(API.PN()+"§7Das Projekt ist nun gestartet§8, §7viel Spaß beim Spielen!");
                                    Bukkit.dispatchCommand(p,"worldborder set 6000000 999999");
                                    World world = Bukkit.getWorlds().get(0);
                                    world.setTime(0);
                                    world.setThundering(false);
                                    world.setStorm(false);
                                    world.setDifficulty(Difficulty.PEACEFUL);
                                    for(Player all : Bukkit.getOnlinePlayers()) {
                                        all.setHealth(20);
                                        all.setFireTicks(0);
                                        all.setFoodLevel(20);
                                        all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,100,100);
                                    }
                                    world.setDifficulty(Difficulty.NORMAL);
                                }
                            }
                        }.start();
                    }
                } else {
                    API.sendErrorMessage(p,API.noPerms);
                }
            } else {
                API.sendErrorMessage(s,API.needPlayer);
            }
        }
        return false;
    }
}