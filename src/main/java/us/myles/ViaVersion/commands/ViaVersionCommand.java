package us.myles.ViaVersion.commands;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.myles.ViaVersion.ViaVersionPlugin;
import us.myles.ViaVersion.api.ViaVersion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fillefilip8 on 2016-03-03.
 */
@RequiredArgsConstructor
public class ViaVersionCommand implements CommandExecutor {

    private final ViaVersionPlugin plugin;


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("viaversion.admin")) {
            if (args.length == 0) {
                sender.sendMessage(color("&aViaVersion &c" + ViaVersion.getInstance().getVersion()));
                sender.sendMessage(color("&6Commands:"));
                sender.sendMessage(color("&2/viaversion list &7- &6Shows lists of all 1.9 clients and 1.8 clients."));
                sender.sendMessage(color("&2/viaversion autoteam &7- &6Toggle automatically teaming to prevent colliding."));
                sender.sendMessage(color("&2/viaversion dontbugme &7- &6Toggle checking for updates."));
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("list")) {
                    List<String> portedPlayers = new ArrayList<>();
                    List<String> normalPlayers = new ArrayList<>();
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (ViaVersion.getInstance().isPorted(p)) {
                            portedPlayers.add(p.getName());
                        } else {
                            normalPlayers.add(p.getName());
                        }
                    }

                    sender.sendMessage(color("&8[&61.9&8]: &b" + portedPlayers.toString()));
                    sender.sendMessage(color("&8[&61.8&8]: &b" + normalPlayers.toString()));
                }
                if (args[0].equalsIgnoreCase("debug")) {
                    plugin.setDebug(!plugin.isDebug());
                    sender.sendMessage(color("&6Debug mode is now " + (plugin.isDebug() ? "&aenabled" : "&cdisabled")));
                }
                if (args[0].equalsIgnoreCase("dontbugme")) {
                    boolean newValue = !plugin.getConfig().getBoolean("checkforupdates", true);
                    plugin.getConfig().set("checkforupdates", newValue);
                    plugin.saveConfig();
                    sender.sendMessage(color("&6We will " + (newValue ? "&anotify you about updates." : "&cnot tell you about updates.")));
                }
                if (args[0].equalsIgnoreCase("autoteam")) {
                    boolean newValue = !plugin.getConfig().getBoolean("auto-team", true);
                    plugin.getConfig().set("auto-team", newValue);
                    plugin.saveConfig();
                    sender.sendMessage(color("&6We will " + (newValue ? "&aautomatically team players" : "&cno longer auto team players")));
                    sender.sendMessage(color("&6All players will need to re-login for the change to take place."));

                }
            }

        }
        return false;
    }

    public String color(String string) {
        return string.replace("&", "§");
    }
}
