// 
// Decompiled by Procyon v0.5.36
// 

package com.wylarel.xpplus.listeners;

import org.bukkit.*;
import org.bukkit.event.EventHandler;
import com.wylarel.xpplus.utils.Utils;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import com.wylarel.xpplus.Main;
import org.bukkit.event.Listener;

public class OneClickBottles implements Listener
{
    private Main main;
    
    public OneClickBottles(final Main main) {
        this.main = main;
    }
    @EventHandler
    public void on(ExpBottleEvent e) {
        Bukkit.broadcastMessage(e.getEntity().getName() + " бросил бутылочку опыта и выбил " + e.getExperience() + "exp");
    }

    @EventHandler
    public void onProjectileThrownEvent(final ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof ThrownExpBottle && this.main.getConfig().getBoolean("oneclickbottles.enabled")) {
            final ThrownExpBottle entity = (ThrownExpBottle)event.getEntity();
            final Player player = (Player)entity.getShooter();
            if (player.isSneaking() && player.hasPermission("oneclickbottles.use")) {
                event.setCancelled(true);
                final int amount = getAmount(player, new ItemStack(Material.EXPERIENCE_BOTTLE));
                player.sendMessage(Utils.chat(this.main.getConfig().getString("oneclickbottles.message").replace("<bottles>", Integer.toString(amount))));
                player.playSound(player.getLocation(), Sound.ENTITY_SPLASH_POTION_BREAK, 0.4f, 0.7f);
                final Location pos = player.getLocation();
                player.getWorld().spawnParticle(Particle.WATER_SPLASH, pos.getX(), pos.getY() + 0.7, pos.getZ(), 100);
                player.giveExp(Math.round(amount * 6.5f));
                deleteAll(player, new ItemStack(Material.EXPERIENCE_BOTTLE));
                player.updateInventory();
            }
        }
    }
    
    public static int getAmount(final Player arg0, final ItemStack arg1) {
        if (arg1 == null) {
            return 0;
        }
        int amount = 0;
        for (int i = 0; i < 36; ++i) {
            final ItemStack slot = arg0.getInventory().getItem(i);
            if (slot != null) {
                if (slot.isSimilar(arg1)) {
                    amount += slot.getAmount();
                }
            }
        }
        return amount;
    }
    
    public static void deleteAll(final Player arg0, final ItemStack arg1) {
        for (int i = 0; i < 36; ++i) {
            final ItemStack slot = arg0.getInventory().getItem(i);
            if (slot != null && slot.isSimilar(arg1)) {
                slot.setAmount(0);
            }
        }
    }
}
