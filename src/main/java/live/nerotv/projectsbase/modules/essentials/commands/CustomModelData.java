package live.nerotv.projectsbase.modules.essentials.commands;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.essentials.manager.StaticManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomModelData implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("CustomModelData")) {
            if(s instanceof Player) {
                Player p = (Player)s;
                if(p.hasPermission("zyneon.team")) {
                    if(args.length == 0) {
                        API.sendErrorMessage(p,"§4Fehler: §c/custommodeldata [int]");
                    } else {
                        if(API.isNumeric(args[0])) {
                            if (p.getItemInHand().getItemMeta() != null) {
                                ItemStack item = p.getItemInHand();
                                ItemMeta itemMeta = item.getItemMeta();
                                Integer modelData = Integer.parseInt(args[0]);
                                itemMeta.setCustomModelData(modelData);
                                item.setItemMeta(itemMeta);
                                p.setItemInHand(new ItemStack(Material.AIR));
                                p.setItemInHand(item);
                                API.sendMessage(p,"§7Du hast die CustomModelData von §a"+itemMeta.getDisplayName()+"§7 auf §e"+modelData+"§7 gesetzt§8.");
                            } else {
                                API.sendErrorMessage(p, "§cDu hast kein gültiges Item in der Hand!");
                            }
                        } else {
                            API.sendErrorMessage(p,"§4Fehler: §c/custommodeldata [int]");
                        }
                    }
                } else {
                    API.sendErrorMessage(p,API.noPerms);
                }
            } else {
                API.sendErrorMessage(s, StaticManager.needPlayer);
            }
        }
        return false;
    }
}
