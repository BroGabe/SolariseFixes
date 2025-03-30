package com.thedev.solarisefixes.modules.maskmodule.listeners;

import com.thedev.solarisefixes.SolariseFixes;
import com.thedev.solarisefixes.modules.maskmodule.MaskManager;
import me.fullpage.mantichoes.api.events.HoeUseEvent;
import me.fullpage.manticrods.api.events.RodUseEvent;
import me.fullpage.manticsword.api.events.SwordUseEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SlayerListener implements Listener {

    private final MaskManager maskManager;

    private final FileConfiguration maskConfig;

    public SlayerListener(SolariseFixes plugin) {
        maskManager = plugin.getModuleManager().getMaskManager();

        maskConfig = plugin.getMaskConfig().getConfig();
    }

    @EventHandler
    public void onSwordEvent(SwordUseEvent event) {
        if(!maskManager.hasSlayerMask(event.getPlayer())) return;

        double multiplier = 1 + (maskConfig.getDouble("slayer-mask.increase-sale") / 100);

        event.setMoney(event.getMoney() * multiplier);
    }

    @EventHandler
    public void onRodEvent(RodUseEvent event) {
        if(!maskManager.hasSlayerMask(event.getPlayer())) return;

        double multiplier = 1 + (maskConfig.getDouble("slayer-mask.rod-increase") / 100);

        event.setMoney(event.getMoney() * multiplier);
    }

    @EventHandler
    public void onHoe(HoeUseEvent event) {
        if(!maskManager.hasSlayerMask(event.getPlayer())) return;

        double multiplier = 1 + (maskConfig.getDouble("slayer-mask.increase-sale") / 100);

        event.setAutosellMoney(event.getAutosellMoney() * multiplier);
    }
}
