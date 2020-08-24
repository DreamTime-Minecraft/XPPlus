// 
// Decompiled by Procyon v0.5.36
// 

package com.wylarel.xpplus.listeners;

import org.bukkit.event.EventHandler;
import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import com.wylarel.xpplus.commands.BottleXP;
import org.bukkit.event.player.PlayerInteractEvent;
import com.wylarel.xpplus.Main;
import org.bukkit.event.Listener;

public class XPSign implements Listener
{
    private Main main;
    
    public XPSign(final Main main) {
        this.main = main;
    }
    
    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if (e.getClickedBlock() == null) {
            return;
        }
        if (e.getClickedBlock().getType().name().contains("SIGN") && p.hasPermission("xpsign.use")) {
            final Block block = e.getClickedBlock();
            final String loc = block.getLocation().toString();
            final List<String> list = (List<String>)this.main.getConfig().getStringList("xpsign.list");
            if (list.contains(loc)) {
                BottleXP.onBottleXP(this.main, p);
            }
        }
    }
}
