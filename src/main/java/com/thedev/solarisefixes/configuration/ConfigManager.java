package com.thedev.solarisefixes.configuration;

import com.thedev.solarisefixes.SolariseFixes;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    @Getter
    private final boolean disallowEnemyTp;

    @Getter
    private final boolean disallowBowBoosting;

    @Getter
    private final boolean disallowEnemyHomes;

    @Getter
    private final boolean disableAppleResistance;

    @Getter
    private final boolean doProtectionNerf;

    @Getter
    private final boolean doSharpnessBuff;

    @Getter
    private final boolean doOutpostBoost;

    @Getter
    private final boolean darkzoneEnabled;

    @Getter
    private final int blockingDmgIncrease;

    @Getter
    private final double protectionNerfAmount;

    @Getter
    private final double sharpnessBuffAmount;

    @Getter
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
