package com.thedev.solarisefixes.listeners;

import com.thedev.solarisefixes.SolariseFixes;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ProtectionListeners implements Listener {

    private final SolariseFixes plugin;

    public ProtectionListeners(SolariseFixes plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) return;

        Player defender = (Player) event.getEntity();

        FileConfiguration config = plugin.getConfig();

        if(!config.getBoolean("protection-nerf.enabled")) return;

        double nerfPerPiece = config.getDouble("protection-nerf.nerf-per-piece");

        double finalNerfAmount = 0.0;

        for(ItemStack itemStack : defender.getInventory().getArmorContents()) {
            if(!itemStack.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) continue;
            if(itemStack.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) != 5) continue;
            finalNerfAmount = finalNerfAmount + nerfPerPiece;
        }

        double multiplier = 1 + (finalNerfAmount / 100);

        event.setDamage(event.getDamage() * multiplier);
    }
}
