package live.nerotv.projectsbase.modules.roleplay.commands;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import live.nerotv.projectsbase.utils.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Fix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s instanceof Player) {
            Player p = (Player)s;
            User u = API.getUser(p);
            RoleplayUser rU = u.getRoleplayUser();
            rU.sendMessage("§7Versuche serverseitige Probleme zu beheben§8...");
            rU.sendMessage("§7Sollte dies dein Problem nicht beheben§8,§7 versuche bitte zu rejoinen§8. §7Wenn das Problem dann immernoch besteht§8,§7 kontaktiere bitte den Support auf dem Discord Server§8.");

            API.dispatchConsoleCommand("recipe take "+p.getName()+" *");
            API.dispatchConsoleCommand("recipe give "+p.getName()+" *");
        } else {
            API.sendErrorMessage(API.needPlayer);
        }
        return false;
    }
}
