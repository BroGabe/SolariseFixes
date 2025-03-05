package com.thedev.solarisefixes.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class LmsFixes implements Listener {

    /**
     * Grants saturation to players inside LMS.
     * @param event
     */
    @EventHandler
    public void onLmsMove(PlayerMoveEvent event) {
        if(!event.getPlayer().getWorld().getName().equalsIgnoreCase("LMS2")) return;

        Player player = event.getPlayer();

        if(player.hasPotionEffect(PotionEffectType.SATURATION)) return;

        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 20 * 300, 2));
    }

    /**
     * Heals player and grants potions after killing an opponent in LMS.
     * @param event
     */
    @EventHandler
    public void onLmsDeath(PlayerDeathEvent event) {
        if(!event.getEntity().getWorld().getName().equalsIgnoreCase("LMS2")) return;
        if(event.getEntity().getKiller() == null) return;

        Player killer = event.getEntity().getKiller();

        ItemStack potionItem = new ItemStack(Material.POTION, 10, (short) 5);

        Potion potion = new Potion(1);
        potion.setSplash(true);
        potion.setLevel(2);
        potion.setType(PotionType.INSTANT_HEAL);
        potion.apply(potionItem);

        killer.getInventory().addItem(potionItem);
    }
}
