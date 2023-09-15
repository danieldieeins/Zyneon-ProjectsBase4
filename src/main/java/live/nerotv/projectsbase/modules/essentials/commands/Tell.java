package live.nerotv.projectsbase.modules.essentials.commands;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.api.ServerAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tell implements CommandExecutor {

    private void sendSyntax(CommandSender s) {
        API.sendErrorMessage(s,"§4Fehler: §c/tell §c[Spieler] [Nachricht]");
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Tell")) {
            if(!(s instanceof Player)) {
                if(args.length >= 2) {
                    if(Bukkit.getPlayer(args[0])!=null) {
                        String msg="";
                        for(int i=1;i<args.length;i++) {
                            msg=msg+args[i]+" ";
                        }
                        Player t = Bukkit.getPlayer(args[0]);
                        msg = msg.replace("&","§");
                        API.sendMessage(s,"§8[§7MSG§8] §6Du §f-> §e" + t.getName() + "§8: §7"+msg);
                        API.sendMessage(t, "§8[§7MSG§8] §cKONSOLE§f -> §6Dir §8: §7" + msg);
                    } else {
                        API.sendErrorMessage(s,API.noPlayer);
                    }
                } else {
                    sendSyntax(s);
                }
            } else {
                Player p = (Player)s;
                String PN = p.getName();
                if(args.length >= 2) {
                    String msg="";
                    for(int i=1;i<args.length;i++) {
                        msg=msg+args[i]+" ";
                    }
                    msg = msg.replace("&","§");
                    if(Bukkit.getPlayer(args[0]) != null) {
                        Player p2 = Bukkit.getPlayer(args[0]);
                        String P2N = p2.getName();
                        if(PN.equals(P2N)) {
                            API.sendErrorMessage(s,"§cDu solltest eventuell darüber nachdenken, mit wem anders zu schreiben, statt mit dir selber.");
                        } else {
                            API.sendMessage(p, "§8[§7MSG§8] §6Du §f-> §e" + P2N + "§8: §7" + msg);
                            API.sendMessage(p2, "§8[§7MSG§8] §e" + PN + "§f -> §6Dir §8: §7" + msg);
                        }
                    } else {
                        API.sendErrorMessage(p,API.noPlayer);
                    }
                } else {
                    sendSyntax(p);
                }
            }
        }
        return false;
    }
}