package live.nerotv.projectsbase.modules.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Sun implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Sun")) {
            if(!(s instanceof Player)) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"weather sun");
            } else {
                Player p = (Player)s;
                p.performCommand("weather sun");
            }
        }
        return false;
    }
}