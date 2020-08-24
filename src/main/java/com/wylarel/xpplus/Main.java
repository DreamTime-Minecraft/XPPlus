// 
// Decompiled by Procyon v0.5.36
// 

package com.wylarel.xpplus;

import com.wylarel.xpplus.listeners.XPSign;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import com.wylarel.xpplus.listeners.OneClickBottles;
import com.wylarel.xpplus.commands.XPSignCommand;
import com.wylarel.xpplus.commands.ClearXP;
import org.bukkit.command.CommandExecutor;
import com.wylarel.xpplus.commands.BottleXP;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    public void onEnable() {
        this.saveDefaultConfig();
        System.out.println("XPPlus started");
        this.getCommand("bottlexp").setExecutor((CommandExecutor)new BottleXP(this));
        this.getCommand("clearxp").setExecutor((CommandExecutor)new ClearXP(this));
        this.getCommand("xpsign").setExecutor((CommandExecutor)new XPSignCommand(this));
        this.getServer().getPluginManager().registerEvents((Listener)new OneClickBottles(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new XPSign(this), (Plugin)this);
    }
    
    public void onDisable() {
        System.out.println("XPPlus stopped");
    }
}
