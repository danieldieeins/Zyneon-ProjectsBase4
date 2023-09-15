package live.nerotv.projectsbase.modules.essentials.commands;

import live.nerotv.projectsbase.api.API;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Hat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s instanceof Player) {
            Player p = (Player)s;
            if(p.hasPermission("zyneon.team")) {
                if(args.length == 0) {
                    if(p.getInventory().getItemInMainHand().getType()!=Material.AIR) {
                        if(p.getInventory().getHelmet()==null) {
                            p.getInventory().setHelmet(p.getInventory().getItemInMainHand());
                            p.getInventory().setItemInMainHand(null);
                        } else {
                            ItemStack helmet = p.getInventory().getHelmet();
                            p.getInventory().setHelmet(p.getInventory().getItemInMainHand());
                            p.getInventory().setItemInMainHand(helmet);
                        }
                        API.sendMessage(p,"§7Du hast dir deinen Helm gesetzt§8!");
                    } else {
                        API.sendErrorMessage(p,"§cDieses Item kannst du nicht nutzen§8!");
                    }
                } else {
                    if(Bukkit.getPlayer(args[0])!=null) {
                        Player t = Bukkit.getPlayer(args[0]);
                        if(t.getInventory().getHelmet()==null) {
                            t.getInventory().setHelmet(p.getInventory().getItemInMainHand());
                            p.getInventory().setItemInMainHand(null);
                        } else {
                            ItemStack helmet = t.getInventory().getHelmet();
                            t.getInventory().setHelmet(p.getInventory().getItemInMainHand());
                            t.getInventory().addItem(helmet);
                        }
                        API.sendMessage(p,"§7Du hast den Helm von §e"+t.getName()+"§7 gesetzt§8!");
                    } else {
                        API.sendErrorMessage(p,API.noPlayer);
                    }
                }
            } else {
                API.sendErrorMessage(p,API.noPerms);
            }
        } else {
            API.sendErrorMessage(s,API.needPlayer);
        }
        return false;
    }
}