// 
// Decompiled by Procyon v0.5.36
// 

package com.wylarel.xpplus.commands;

import com.wylarel.xpplus.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.wylarel.xpplus.Main;
import org.bukkit.command.CommandExecutor;

public class ClearXP implements CommandExecutor
{
    private Main main;
    
    public ClearXP(final Main main) {
        this.main = main;
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String msg, final String[] args) {
        if (sender instanceof Player) {
            Player target;
            final Player p = target = (Player)sender;
            if (args.length != 0 && p.hasPermission("clearxp.use")) {
                target = Bukkit.getPlayer(args[0]);
                if (target.getExp() == 0.0f && target.getLevel() == 0) {
                    target.sendMessage(Utils.chat(this.main.getConfig().getString("clearxp.other.error.already_null").replace("<player>", target.getDisplayName())));
                }
                else {
                    target.sendMessage(Utils.chat(this.main.getConfig().getString("clearxp.other.message").replace("<player>", target.getDisplayName())));
                }
            }
            else if (target.getExp() == 0.0f && target.getLevel() == 0) {
                target.sendMessage(Utils.chat(this.main.getConfig().getString("clearxp.self.error.already_null")));
            }
            else {
                target.sendMessage(Utils.chat(this.main.getConfig().getString("clearxp.self.message")));
            }
            target.setExp(0.0f);
            target.setLevel(0);
        }
        else {
            sender.sendMessage("§cThis command can only be used by a player client");
        }
        return true;
    }
}
