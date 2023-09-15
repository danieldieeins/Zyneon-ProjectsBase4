package live.nerotv.projectsbase.modules.essentials.commands;

import live.nerotv.projectsbase.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Author implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Author")) {
            s.sendMessage("§8");
            s.sendMessage("§8");
            s.sendMessage("§8");
            s.sendMessage("§8");
            s.sendMessage("§8=================================================");
            s.sendMessage("§8");
            s.sendMessage("§9 Zyneon§8: §7ProjectsBase §9"+ Main.Version);
            s.sendMessage("§f by §cnerotvlive§f.");
            s.sendMessage("§8");
            s.sendMessage("§8=================================================");
            s.sendMessage("§8");
            s.sendMessage("§8");
        }
        return false;
    }
}