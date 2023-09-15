package live.nerotv.projectsbase.modules.essentials.commands;

import com.zyneonstudios.api.utils.Strings;
import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.utils.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MOC implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s instanceof Player) {
            Player p = (Player)s;
            User u = API.getUser(p);
            if(p.hasPermission("zyneon.team")) {
                if(args.length == 0) {
                    u.sendMessage("Mit §e/moc <command> §7kannst du einen MOHIST OPERATOR COMMAND in die Mohist-Konsole senden§8.");
                    u.sendMessage("Mit §e/moc op "+u.getPlayer().getName()+"§7 kannst du dir selbst OP geben§8!");
                } else {
                    StringBuilder m= new StringBuilder();
                    for (String arg : args) {
                        m.append(arg).append(" ");
                    }
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), m.toString());
                    u.sendMessage("Dein Befehl wurde ausgeführt§8.");
                }
            } else {
                u.sendErrorMessage(Strings.noPerms());
            }
        } else {
            API.sendErrorMessage(s, Strings.needPlayer());
        }
        return false;
    }
}
