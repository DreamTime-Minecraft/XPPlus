// 
// Decompiled by Procyon v0.5.36
// 

package com.wylarel.xpplus.commands;

import java.util.List;
import org.bukkit.block.Block;
import com.wylarel.xpplus.utils.Utils;
import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.wylarel.xpplus.Main;
import org.bukkit.command.CommandExecutor;

public class XPSignCommand implements CommandExecutor
{
    private Main main;
    
    public XPSignCommand(final Main main) {
        this.main = main;
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String msg, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            final Block block = p.getTargetBlock((Set)null, 100);
            if (block == null) {
                sender.sendMessage("§cYou must be pointing to a sign to use this command");
                return false;
            }
            if (block.getType().name().contains("SIGN")) {
                final String loc = block.getLocation().toString();
                final List<String> list = (List<String>)this.main.getConfig().getStringList("xpsign.list");
                if (list.contains(loc)) {
                    list.remove(loc);
                    this.main.getConfig().set("xpsign.list", (Object)list);
                    this.main.saveConfig();
                    p.sendMessage(Utils.chat(this.main.getConfig().getString("xpsign.onremove").replace("<sign>", block.getType().name()).replace("<location>", block.getLocation().toString())));
                }
                else {
                    list.add(loc);
                    this.main.getConfig().set("xpsign.list", (Object)list);
                    this.main.saveConfig();
                    p.sendMessage(Utils.chat(this.main.getConfig().getString("xpsign.oncreate").replace("<sign>", block.getType().name()).replace("<location>", block.getLocation().toString())));
                }
            }
            else {
                sender.sendMessage("§cThis command can only target signs. Your are pointing to a §4" + block.getType().name() + " §cblock");
            }
        }
        else {
            sender.sendMessage("§cThis command can only be used by a player client");
        }
        return true;
    }
}
