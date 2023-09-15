package live.nerotv.projectsbase.modules.roleplay.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Rucksack implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Rucksack")) {
            if(!(s instanceof Player)) {
                s.sendMessage("§cDazu §4musst§c du ein Spieler sein§4!");
            } else {
                Player p = (Player)s;
                p.performCommand("bp");
            }
        }
        return false;
    }
}