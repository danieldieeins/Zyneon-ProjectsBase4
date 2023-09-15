package live.nerotv.projectsbase.modules.roleplay.commands;

import live.nerotv.projectsbase.api.ConfigAPI;
import live.nerotv.projectsbase.modules.roleplay.manager.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Shop implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Shop")) {
            if(ConfigAPI.CFG.getBoolean("Core.Settings.Commands.Shop.Enable")) {
                if (!(s instanceof Player)) {
                    s.sendMessage("§cDazu §4musst§c du ein Spieler sein§4!");
                } else {
                    Player p = (Player) s;
                    InventoryManager.openInv001(p);
                }
            } else {
                Bukkit.dispatchCommand(s,"neino");
            }
        }
        return false;
    }
}