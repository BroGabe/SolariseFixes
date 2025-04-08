package com.thedev.solarisefixes.configuration;

import com.thedev.solarisefixes.SolariseFixes;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public class ConfigManager {

    private final boolean disallowEnemyTp;

    private final boolean disallowBowBoosting;

    private final boolean disallowEnemyHomes;

    private final boolean disableAppleResistance;

    private final boolean doProtectionNerf;

    private final boolean doSharpnessBuff;

    private final boolean doOutpostBoost;

    private final boolean darkzoneEnabled;

    private final int blockingDmgIncrease;

    private final double protectionNerfAmount;

    private final double sharpnessBuffAmount;

    private final String adminPermission;

    public ConfigManager(SolariseFixes plugin) {
        FileConfiguration config = plugin.getConfig();

        blockingDmgIncrease = config.getInt("increase-blocking-damage");


        disallowEnemyTp = config.getBoolean("disallow-enemy-tp");
        disallowBowBoosting = config.getBoolean("anti-bow-boosting");
        disallowEnemyHomes = config.getBoolean("disable-enemy-home");
        disableAppleResistance = config.getBoolean("disable-apple-resistance");
        doProtectionNerf = config.getBoolean("protection-nerf.enabled");
        doSharpnessBuff = config.getBoolean("sharpness-buff.enabled");
        doOutpostBoost = config.getBoolean("outpost-boost.enabled");
        darkzoneEnabled = config.getBoolean("darkzone-feature.enabled");

        protectionNerfAmount = config.getDouble("protection-nerf.nerf-per-piece");
        sharpnessBuffAmount = config.getDouble("sharpness-buff.amount");

        adminPermission = config.getString("admin-permission");
    }
}
