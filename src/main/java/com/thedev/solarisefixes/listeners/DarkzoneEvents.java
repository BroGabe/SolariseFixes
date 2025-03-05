package com.thedev.solarisefixes.listeners;

import com.massivecraft.factions.event.FPlayerTeleportEvent;
import com.thedev.solarisefixes.SolariseFixes;
import com.thedev.solarisefixes.utils.ColorUtil;
import net.ess3.api.events.teleport.TeleportWarmupEvent;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.List;

public class DarkzoneEvents implements Listener {

    private final FileConfiguration config;

    private final boolean enabled;

    private final boolean denyTeleport;

    private final String worldName;

    public DarkzoneEvents(SolariseFixes plugin) {
        config = plugin.getConfig();

        enabled = config.getBoolean("darkzone-feature.enabled");
        denyTeleport = config.getBoolean("darkzone-feature.deny-teleportation");
        worldName = config.getString("darkzone-feature.world");
    }

    /**
     * Disables teleportion via essentials when inside darkzone.
     * deny-teleportation: true
     * @param event
     */
    @EventHandler
    public void onEssentialsTP(TeleportWarmupEvent event) {
        if(event.getTeleporter().getBase().hasPermission("solarise.admin")) return;

        if(!enabled || !denyTeleport) return;
        if(event.getTeleportCause() != PlayerTeleportEvent.TeleportCause.COMMAND) return;

        Player teleporter = event.getTeleporter().getBase();

        if(!teleporter.getWorld().getName().equalsIgnoreCase(worldName)) return;

        teleporter.sendMessage(ColorUtil.color("&4&lDARKZONE &cTeleporting this way is disabled!"));
        teleporter.playSound(teleporter.getLocation(), Sound.NOTE_BASS, 10, 6);
        event.setCancelled(true);
    }

    /**
     * Disables faction teleportion commands inside darkzone.
     * requires deny-teleportation: true
     * @param event
     */
    @EventHandler
    public void onFWarp(FPlayerTeleportEvent event) {
        if(event.getfPlayer().getPlayer().hasPermission("solarise.admin")) return;

        if(!enabled || !denyTeleport) return;
        if(!event.getfPlayer().getPlayer().getWorld().getName().equalsIgnoreCase(worldName)) return;

        Player player = event.getfPlayer().getPlayer();

        player.sendMessage(ColorUtil.color("&4&lDARKZONE &cTeleporting this way is disabled!"));
        player.playSound(player.getLocation(), Sound.NOTE_BASS, 10, 6);
        event.setCancelled(true);
    }

    /**
     * Disallows players from entering commands not from
     * the allowed commands list.
     * @param event
     */
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if(event.getPlayer().hasPermission("solarise.admin")) return;

        if(!enabled) return;
        if(!event.getPlayer().getWorld().getName().equalsIgnoreCase(worldName)) return;

        String command = event.getMessage().toLowerCase();

        List<String> allowedCommands = new ArrayList<>();

        config.getStringList("darkzone-feature.allowed-cmds").forEach(cmd -> allowedCommands.add(cmd.toLowerCase()));

        for(String allowedCommand : allowedCommands) {
            if(command.startsWith(allowedCommand)) return;
        }

        Player player = event.getPlayer();

        player.sendMessage(ColorUtil.color(config.getString("darkzone-feature.message")));
        player.playSound(player.getLocation(), Sound.NOTE_BASS, 10, 6);
        event.setCancelled(true);
    }
}
