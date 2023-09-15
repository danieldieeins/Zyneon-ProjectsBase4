package live.nerotv.projectsbase.modules.economy.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Pay implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Pay")) {
            if(args.length == 0) {
                Bukkit.dispatchCommand(s,"money pay");
            } else {
                String m="";
                for(int i=0;i<args.length;i++) {
                    m=m+args[i]+" ";
                }
                Bukkit.dispatchCommand(s,"money pay "+m);
            }
        }
        return false;
    }
}