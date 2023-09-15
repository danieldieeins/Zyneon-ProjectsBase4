package live.nerotv.projectsbase.modules.roleplay.commands;

import live.nerotv.projectsbase.api.API;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RedstoneLock implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("RedstoneLock")) {
            if(s instanceof Player) {
                Player p = (Player)s;
                if(API.PM.getPlugin("LWC")!=null) {
                    if(args.length == 0) {
                        p.performCommand("lwc flag redstone on");
                    } else {
                        if(args[0].equalsIgnoreCase("off")) {
                            p.performCommand("lwc flag redstone off");
                        } else if(args[0].equalsIgnoreCase("on")) {
                            p.performCommand("lwc flag redstone on");
                        } else {
                            API.sendErrorMessage(p,"§4Fehler:§c /redstonelock §7[on|off]");
                        }
                    }
                } else {
                    p.performCommand("/neino");
                }
            } else {
                s.sendMessage("§cDazu §4musst§c du ein Spieler sein§4!");
            }
        }
        return false;
    }
}