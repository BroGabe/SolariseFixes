package com.thedev.solarisefixes;

import com.thedev.solarisefixes.configuration.MaskConfig;
import com.thedev.solarisefixes.listeners.*;
import com.thedev.solarisefixes.modules.ModuleManager;
import com.thedev.solarisefixes.modules.combatmodule.listeners.CombatListener;
import com.thedev.solarisefixes.modules.debugmodule.DamageDebugEvents;
import com.thedev.solarisefixes.modules.maskmodule.abilities.AbilityManager;
import com.thedev.solarisefixes.modules.maskmodule.listeners.ComboListener;
import com.thedev.solarisefixes.modules.maskmodule.listeners.HydraListener;
import com.thedev.solarisefixes.modules.maskmodule.listeners.SlayerListener;
import lombok.Getter;
import net.minelink.ctplus.CombatTagPlus;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;


public final class SolariseFixes extends JavaPlugin {

    @Getter
    private AbilityManager abilityManager;

    @Getter
    private ModuleManager moduleManager;

    @Getter
    private MaskConfig maskConfig;

    @Getter
    private CombatTagPlus combatTagPlus;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        maskConfig = new MaskConfig(this);

        // Registering CombatTag
        registerCombatTag();

        // Register the ability manager
        abilityManager = new AbilityManager(this);

        // Register Module Manager
        moduleManager = new ModuleManager(this);


        // Registering the listeners for the plugin.
        Bukkit.getPluginManager().registerEvents(new EventsListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockingListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new MiscListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new DarkzoneListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new OutpostListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new DamageDebugEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new ProtectionListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new SharpnessListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new FactionListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new CombatListener(this), this);

        // Registering Listeners for Masks
        Bukkit.getPluginManager().registerEvents(new HydraListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ComboListener(this), this);
        Bukkit.getPluginManager().registerEvents(new SlayerListener(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCombatTag() {
        if(Bukkit.getPluginManager().getPlugin("CombatTagPlus") == null) return;

        combatTagPlus = (CombatTagPlus) Bukkit.getPluginManager().getPlugin("CombatTagPlus");
        System.out.println("[SolariseFixes] Loading CombatTagPlus Support");
    }

}
