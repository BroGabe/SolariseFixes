package com.thedev.solarisefixes.modules.maskmodule.listeners;

import com.thedev.solarisefixes.SolariseFixes;
import com.thedev.solarisefixes.modules.maskmodule.MaskManager;
import com.thedev.solarisefixes.modules.maskmodule.abilities.types.ComboManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ComboListener implements Listener {

    private final ComboManager comboManager;

    private final MaskManager maskManager;

    public ComboListener(SolariseFixes plugin) {
        comboManager = plugin.getAbilityManager().getComboManager();

        maskManager = plugin.getModuleManager().getMaskManager();
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) return;

        Player attacker = (Player) event.getDamager();
        Player defender = (Player) event.getEntity();

        if (!maskManager.hasComboMask(attacker) && !maskManager.hasComboMask(defender)) return;

        if (maskManager.hasComboMask(defender)) {
            comboManager.resetCombo(defender);
        }

        if (!maskManager.hasComboMask(attacker)) return;

        double multiplier = 1 + (comboManager.getMultiplier(attacker) / 100);

        event.setDamage(event.getDamage() * multiplier);

        comboManager.addCombo(attacker);
    }
}
