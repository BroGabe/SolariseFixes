package com.thedev.solarisefixes.listeners;

import com.thedev.solarisefixes.SolariseFixes;
import com.thedev.solarisefixes.utils.ColorUtil;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;

public class CaveFixes implements Listener {

    private final FileConfiguration config;

    private final boolean enabled;

    private final String worldName;

    private final List<String> allowedTypes = new ArrayList<>();

    public CaveFixes(SolariseFixes plugin) {
        config = plugin.getConfig();

        enabled = config.getBoolean("caves-feature.enabled");
        worldName = config.getString("caves-feature.world");

        config.getStringList("caves-feature.allowed-break").forEach(s -> allowedTypes.add(s.toUpperCase()));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(event.getPlayer().hasPermission("solarise.admin")) return;
        if(!enabled || !event.getBlock().getLocation().getWorld().getName().equalsIgnoreCase(worldName)) return;

        if(allowedTypes.contains(event.getBlock().getType().name().toUpperCase())) return;

        Player player = event.getPlayer();

        player.sendMessage(ColorUtil.color(config.getString("caves-feature.message")));
        player.playSound(player.getLocation(), Sound.NOTE_BASS, 10, 6);

        event.setCancelled(true);
    }
}
