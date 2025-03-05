package com.thedev.solarisefixes;

import com.thedev.solarisefixes.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SolariseFixes extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        // Registering the listeners for the plugin.
        Bukkit.getPluginManager().registerEvents(new BlockHit(this), this);
        Bukkit.getPluginManager().registerEvents(new MiscFixes(this), this);
        Bukkit.getPluginManager().registerEvents(new DarkzoneEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new CaveFixes(this), this);
        Bukkit.getPluginManager().registerEvents(new LmsFixes(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
