package live.nerotv.projectsbase.modules.roleplay.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Achievements implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Achievements")) {
            if(s instanceof Player) {
                Player p = (Player)s;
                p.performCommand("advancedachievements:aa list");
            } else {
                Bukkit.dispatchCommand(s, "advancedachievements:aa list");
            }
        }
        return false;
    }
}