package com.thedev.solarisefixes.listeners;

import com.thedev.solarisefixes.SolariseFixes;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SharpnessListeners implements Listener {

    private final FileConfiguration config;

    public SharpnessListeners(SolariseFixes plugin) {
        config = plugin.getConfig();
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player)) return;
        if(!config.getBoolean("sharpness-buff.enabled")) return;

        Player attacker = (Player) event.getDamager();

        if(!attacker.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL)) return;

        if(attacker.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL) != 6) return;

        double multiplier = 1 + (config.getDouble("sharpness-buff.amount")/100);

        event.setDamage(event.getDamage() * multiplier);
    }
}
