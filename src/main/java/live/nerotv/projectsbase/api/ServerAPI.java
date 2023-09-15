package live.nerotv.projectsbase.api;

import live.nerotv.projectsbase.Main;
import live.nerotv.projectsbase.utils.Countdown;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ServerAPI {

    public static void sendConsoleMessage(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage("§9Zyneon §8| §7"+message.replace("&","§"));
    }

    public static void scheduledShutdown() {
        if(!API.isStopping) {
            API.isStopping = true;
            new Countdown(27, Main.getInstance()) {
                @Override
                public void count(int current) {
                    if (current < 11) {
                        int cur = current - 1;
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a subtitle \"§8...§7startet der Server neu§8!\"");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title \"§7In §e" + cur + " Sekunden§8...\"");
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,100,100);
                        }
                        if (current <= 6) {
                            Bukkit.broadcastMessage("§cWICHTIG§8: §7Serverneustart in §e" + cur + " Sekunden§8!");
                        }
                        if (current == 1) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                API.switchServer(all, "Lobby-1");
                            }
                        } else if (current == 0) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                API.switchServer(all, "Lobby-1");
                            }
                            Bukkit.shutdown();
                        }
                    } else {
                        if (current == 26) {
                            Bukkit.broadcastMessage("§cWICHTIG§8: §7Serverneustart in §e25 Sekunden§8!");
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,100,100);
                            }
                        } else if (current == 21) {
                            Bukkit.broadcastMessage("§cWICHTIG§8: §7Serverneustart in §e20 Sekunden§8!");
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,100,100);
                            }
                        } else if (current == 16) {
                            Bukkit.broadcastMessage("§cWICHTIG§8: §7Serverneustart in §e15 Sekunden§8!");
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,100,100);
                            }
                        } else if (current == 11) {
                            Bukkit.broadcastMessage("§cWICHTIG§8: §7Serverneustart in §e10 Sekunden§8!");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a subtitle \"§8...§7startet der Server neu§8!\"");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title \"§7In §e10 Sekunden§8...\"");
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,100,100);
                            }
                        }
                    }
                }
            }.start();
        }
    }

    public static void setMaintenance(boolean state) {
        ConfigAPI.CFG.set("Core.Settings.Maintenance",state);
        API.maintenance = state;
        ConfigAPI.saveConfig(ConfigAPI.Config,ConfigAPI.CFG);
    }

    public static void toggleMaintenance() {
        if(isMaintenance()) {
            setMaintenance(false);
        } else {
            setMaintenance(true);
        }
    }

    public static boolean isMaintenance() {
        return API.maintenance;
    }
}