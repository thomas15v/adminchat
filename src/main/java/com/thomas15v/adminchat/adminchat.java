package com.thomas15v.adminchat;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thomas on 4/8/14.
 */
public class adminchat extends JavaPlugin implements Listener {

    Map<String, Player> Admins = new HashMap<String, Player>();

    @Override
    public void onEnable() {
        super.onEnable();
        getServer().getPluginManager().registerEvents(this,this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            String name = ((Player) sender).getName();
            if (Admins.containsKey(name)){
                Admins.remove(name);
                sender.sendMessage("Ya left tha admin channel");
            }
            else{
                Admins.put(name, (Player) sender);
                sender.sendMessage("Ya joined tha admin channel");
            }
        }
        return true;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void PlayerChatEvent(PlayerChatEvent e){
        if (Admins.containsKey(e.getPlayer().getName())){
            e.setFormat(ChatColor.RED + "[ADMIN] " + ChatColor.RESET + "%s: %s");
            for (Player player : getServer().getOnlinePlayers())
                    if (!player.hasPermission("adminchat.chat") || !player.isOp()){
                        for (Player p : Admins.values())
                            p.sendMessage(ChatColor.RED + "[ADMIN]" + ChatColor.RESET + e.getPlayer().getDisplayName() + ": " + e.getMessage());
                        e.setCancelled(true);
                    }
        }
    }
}
