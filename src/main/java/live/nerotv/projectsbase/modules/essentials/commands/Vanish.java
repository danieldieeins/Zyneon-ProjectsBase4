package live.nerotv.projectsbase.modules.essentials.commands;

import live.nerotv.projectsbase.api.API;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;

public class Vanish implements CommandExecutor {

    public static ArrayList<Player> vP = new ArrayList<>();
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Vanish")) {
            if(s instanceof Player) {
                if(s.hasPermission("zyneon.team")) {
                    Player p = (Player)s;
                    if(vP.contains(p)) {
                        vP.remove(p);
                        for(Player all : Bukkit.getOnlinePlayers()) {
                            all.showPlayer(p);
                            if(!all.getName().equals(p.getName())) {
                                if (all.hasPermission("zyneon.team")) {
                                    API.sendMessage(all,"§e"+p.getName()+"§7 ist nun §enicht mehr §r§eunsichtbar§8!");
                                } else {
                                    all.sendMessage("§8» §a"+p.getName());
                                }
                            }
                        }
                        API.sendMessage(p,"§7Du bist nun §anicht mehr§r§a unsichtbar§8!");
                    } else {
                        vP.add(p);
                        for(Player all : Bukkit.getOnlinePlayers()) {
                            all.hidePlayer(p);
                            if(!all.getName().equals(p.getName())) {
                                if (all.hasPermission("zyneon.team")) {
                                    API.sendMessage(all,"§e"+p.getName()+"§7 ist nun §eunsichtbar§8! §8(Du kannst sie/ihn aber noch sehen, da du die Rechte dazu hast!)");
                                    all.showPlayer(p);
                                } else {
                                    all.sendMessage("§8« §c"+p.getName());
                                }
                            }
                        }
                        API.sendMessage(p,"§7Du bist nun §aunsichtbar§8!");
                    }
                } else {
                    API.sendErrorMessage(s,API.noPerms);
                }
            } else {
                API.sendErrorMessage(s,API.needPlayer);
            }
        }
        return false;
    }
}