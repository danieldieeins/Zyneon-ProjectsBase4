package live.nerotv.projectsbase.modules.roleplay.commands;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.api.ConfigAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Buch implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Buch")) {
            if(!(s instanceof Player)) {
                s.sendMessage("§cDazu §4musst§c du ein Spieler sein§4!");
            } else {
                Player p = (Player)s;
                if(ConfigAPI.CFG.getBoolean("Core.Settings.Commands.Buch")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"minecraft:give "+p.getName()+" writable_book");
                    API.sendMessage(p,"Du hast ein beschreibbares Buch erhalten.");
                } else {
                    p.performCommand("dieserbefehlwirdniemalsexistieren");
                }
            }
        }
        return false;
    }
}