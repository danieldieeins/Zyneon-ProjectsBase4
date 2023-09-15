package live.nerotv.projectsbase.modules.essentials.commands;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.essentials.manager.CustomItemManager;
import live.nerotv.projectsbase.modules.roleplay.manager.ItemManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Item implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Item")) {
            if(s instanceof Player) {
                Player p = (Player)s;
                if(p.hasPermission("zyneon.team.item")) {
                    if(args.length == 1) {
                        if(ItemManager.checkItemStack(args[0])) {
                            if(API.invFull(p)) {
                                API.sendErrorMessage(p,"§cDein Inventar ist voll§8!");
                                return false;
                            } else {
                                ItemStack item;
                                if(CustomItemManager.getCustomItem(args[0])!=null) {
                                    item = CustomItemManager.getCustomItem(args[0]);
                                } else {
                                    item = new ItemStack(Material.getMaterial(args[0]));
                                }
                                p.getInventory().addItem(item);
                                API.sendMessage(p,"§7Du hast dir das Item §e"+args[0]+"§7 gegeben§8!");
                            }
                        } else {
                            API.sendErrorMessage(p,"§cDieses Item gibt es nicht§8!");
                        }
                    } else if(args.length == 2) {
                        if(!API.isNumeric(args[1])) {
                            API.sendErrorMessage(p,"§cDas ist keine gültige Anzahl§8!");
                            return false;
                        }
                        int i = Integer.parseInt(args[1]);
                        if(i > 64) {
                            API.sendErrorMessage(p,"§cDas ist keine gültige Anzahl§8!");
                            return false;
                        } else if(i < 1) {
                            API.sendErrorMessage(p,"§cDas ist keine gültige Anzahl§8!");
                            return false;
                        }
                        if(ItemManager.checkItemStack(args[0])) {
                            if(API.invFull(p)) {
                                API.sendErrorMessage(p,"§cDein Inventar ist voll§8!");
                                return false;
                            } else {
                                ItemStack item;
                                if(CustomItemManager.getCustomItem(args[0])!=null) {
                                    item = CustomItemManager.getCustomItem(args[0]);
                                } else {
                                    item = new ItemStack(Material.getMaterial(args[0]));
                                }
                                item.setAmount(i);
                                p.getInventory().addItem(item);
                                API.sendMessage(p,"§7Du hast dir das Item §e"+args[0]+"§7 gegeben§8!");
                            }
                        } else {
                            API.sendErrorMessage(p,"§cDieses Item gibt es nicht§8!");
                        }
                    } else {
                        API.sendErrorMessage(p,"§4Fehler: §c/item [Item] §7[Anzahl]");
                    }
                } else {
                    API.sendErrorMessage(p,API.noPerms);
                }
            } else {
                API.sendErrorMessage(s,API.needPlayer);
            }
        }
        return false;
    }
}