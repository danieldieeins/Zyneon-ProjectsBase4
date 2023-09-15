package live.nerotv.projectsbase.modules.essentials.commands;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.api.ServerAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Maintenance implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Maintenance")) {
            if(s.hasPermission("zyneon.leading.maintenance")) {
                ServerAPI.toggleMaintenance();
                API.sendMessage(s,"Der Serverwartungsmodus steht nun auf: §e"+ServerAPI.isMaintenance());
            } else {
                API.sendErrorMessage(s,API.noPerms);
            }
        }
        return false;
    }
}