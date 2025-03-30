package com.thedev.solarisefixes.modules.maskmodule.listeners;

import com.thedev.solarisefixes.SolariseFixes;
import com.thedev.solarisefixes.modules.maskmodule.MaskManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HydraListener implements Listener {

    private final MaskManager maskManager;

    private final FileConfiguration config;

    public HydraListener(SolariseFixes plugin) {
        maskManager = plugin.getModuleManager().getMaskManager();
        config = plugin.getMaskConfig().getConfig();
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) return;

        Player attacker = (Player) event.getDamager();

        if(!maskManager.hasHydraMask(attacker)) return;
        if(!inWater(attacker)) return;

        double damageMultipler = 1.00 + ((double) config.getInt("hydra-mask.damage-increase") / 100);

        event.setDamage(event.getDamage() * damageMultipler);
    }


    /**
     * Returns true if the player is inside water.
     * @return
     */
    private boolean inWater(Player player) {
        Location location = player.getLocation().getBlock().getLocation();

        return (location.getBlock().getType() == Material.WATER || location.getBlock().getType() == Material.STATIONARY_WATER);
    }
}
