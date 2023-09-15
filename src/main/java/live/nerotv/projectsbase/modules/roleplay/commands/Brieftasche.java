package live.nerotv.projectsbase.modules.roleplay.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Brieftasche implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Brieftasche")) {
            Bukkit.dispatchCommand(s,"money");
        }
        return false;
    }
}