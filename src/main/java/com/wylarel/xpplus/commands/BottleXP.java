// 
// Decompiled by Procyon v0.5.36
// 

package com.wylarel.xpplus.commands;

import org.bukkit.inventory.PlayerInventory;
import com.wylarel.xpplus.utils.Utils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.wylarel.xpplus.Main;
import org.bukkit.command.CommandExecutor;

public class BottleXP implements CommandExecutor
{
    private Main main;
    
    public BottleXP(final Main main) {
        this.main = main;
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String msg, final String[] args) {
        if (sender instanceof Player) {
            return onBottleXP(this.main, (Player)sender);
        }
        sender.sendMessage("§cThis command can only be used by a player client");
        return true;
    }
    
    public static boolean onBottleXP(final Main main, final Player p) {
        final int level = p.getLevel();
        final int newLevel = 0;
        int bottles = 0;
        switch (main.getConfig().getInt("bottlexp.type")) {
            case 0: {
                final float multLevel = level * (float)main.getConfig().getDouble("bottlexp.ratio");
                if (multLevel <= 17.0f) {
                    bottles = (int)Math.round((Math.pow(multLevel, 2.0) + 6.0f * multLevel) / 6.5);
                    break;
                }
                if (multLevel <= 31.0f) {
                    bottles = (int)Math.round((2.5 * Math.pow(multLevel, 2.0) - 40.5f * multLevel + 360.0) / 6.5);
                    break;
                }
                bottles = (int)Math.round((4.5 * Math.pow(multLevel, 2.0) - 162.5f * multLevel + 2220.0) / 6.5);
                break;
            }
            case 1: {
                bottles = Math.round(level * (float)main.getConfig().getDouble("bottlexp.ratio"));
                break;
            }
        }
        if (level > 0) {
            p.setLevel(newLevel);
            final PlayerInventory inventory = p.getInventory();
            final ItemStack itemStack = new ItemStack(Material.EXPERIENCE_BOTTLE, bottles);
            inventory.addItem(new ItemStack[] { itemStack });
            p.sendMessage(Utils.chat(main.getConfig().getString("bottlexp.message").replace("<bottles>", Integer.toString(bottles)).replace("<levels>", Integer.toString(level))));
        }
        else {
            p.sendMessage(Utils.chat(main.getConfig().getString("bottlexp.error.not_enough")));
        }
        return true;
    }
}
