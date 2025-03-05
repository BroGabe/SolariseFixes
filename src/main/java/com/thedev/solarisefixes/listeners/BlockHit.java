package com.thedev.solarisefixes.listeners;

import com.thedev.solarisefixes.SolariseFixes;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class BlockHit implements Listener {

    private final SolariseFixes plugin;

    public BlockHit(SolariseFixes plugin) {
        this.plugin = plugin;
    }

    /**
     * Fixes players taking way less damage when blocking.
     * @param event
     */
    @EventHandler
    public void onBlockDamage(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) return;

        Player defender = (Player) event.getEntity();

        if(!defender.isBlocking()) return;

        FileConfiguration config = plugin.getConfig();

        double damageIncrease = config.getDouble("increase-blocking-damage");

        double finalDamage = event.getDamage() * damageIncrease;

        event.setDamage(finalDamage);
    }
}
