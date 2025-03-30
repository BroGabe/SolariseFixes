package com.thedev.solarisefixes.configuration;

import com.thedev.solarisefixes.SolariseFixes;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MaskConfig {

    private final SolariseFixes plugin;

    @Getter
    private File file;
    @Getter
    private FileConfiguration config;

    public MaskConfig(SolariseFixes plugin) {
        this.plugin = plugin;

        initializeConfig();
    }

    private void initializeConfig() {
        file = new File(plugin.getDataFolder(), "masks.yml");

        if(file.exists()) {
            config = YamlConfiguration.loadConfiguration(file);
            return;
        }

        plugin.saveResource("masks.yml", false);

        config = YamlConfiguration.loadConfiguration(file);
    }

    public void reloadConfig() {
        initializeConfig();
    }
}
