package live.nerotv.projectsbase.modules.roleplay.quests.commands;

import com.zyneonstudios.api.utils.Strings;
import live.nerotv.projectsbase.api.API;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Quests implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s instanceof Player) {
            Player p = (Player)s;

        } else {
            API.sendErrorMessage(s, Strings.needPlayer());
        }
        return false;
    }
}
