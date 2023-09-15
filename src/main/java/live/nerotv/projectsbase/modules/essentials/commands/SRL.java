package live.nerotv.projectsbase.modules.essentials.commands;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.api.ServerAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SRL implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("SRL")) {
            if(s.hasPermission("zyneon.leading")) {
                if(API.isStopping) {
                    API.sendMessage(s,"§cDer Server fährt bereits runter!");
                    return false;
                }
                API.sendMessage(s,"§7Du hast den §eStopvorgang§7 gestartet§8.");
                ServerAPI.scheduledShutdown();
            } else {
                API.sendErrorMessage(s,API.noPerms);
            }
        }
        return false;
    }
}
