package com.thedev.solarisefixes.listeners;

import com.massivecraft.factions.event.LandClaimEvent;
import com.thedev.solarisefixes.SolariseFixes;
import com.thedev.solarisefixes.utils.ColorUtil;
import net.ess3.api.events.teleport.TeleportWarmupEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class EventsListeners implements Listener {

    private final SolariseFixes plugin;

    public EventsListeners(SolariseFixes plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClaim(LandClaimEvent event) {
        if(event.getfPlayer().getPlayer().hasPermission("solarisefixes.admin")) return;
        if(!event.getLocation().getWorldName().equalsIgnoreCase("EventWorld")) return;

        event.getfPlayer().getPlayer().sendMessage(ColorUtil.color("&5&lSolarise&d&lMC &fYou &c&nCANNOT&f claim land here!"));
        event.setCancelled(true);
    }

    /**
     * Disables teleportion via essentials when inside darkzone.
     * deny-teleportation: true
     * @param event
     */
    @EventHandler
    public void onEssentialsTP(TeleportWarmupEvent event) {
        if(event.getTeleporter().getBase().hasPermission("solarise.admin")) return;

        if(event.getTeleportCause() != PlayerTeleportEvent.TeleportCause.COMMAND) return;
        if(event.getTarget() == null || event.getTarget().getLocation() == null) return;

        if(!event.getTarget().getLocation().getWorld().getName().equalsIgnoreCase("EventWorld")) return;

        Player player = event.getTeleporter().getBase();

        player.sendMessage(ColorUtil.color("&5&lSOLARISE&d&lMC &fYou &c&nCANNOT&f manually teleport to event world!"));
        event.setCancelled(true);
    }

    @EventHandler
    public void onFlight(PlayerChangedWorldEvent event) {
        if(!event.getPlayer().getWorld().getName().equalsIgnoreCase("EventWorld")) return;
        event.getPlayer().setFlying(false);
        event.getPlayer().setAllowFlight(false);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Block initialBlock = event.getFrom().clone().subtract(0, 1, 0).getBlock();
        Block toBlock = event.getTo().clone().subtract(0, 1, 0).getBlock();

        if(initialBlock.equals(toBlock)) return;

        if(!event.getPlayer().getWorld().getName().equalsIgnoreCase("EventWorld")) return;

        if(!event.getPlayer().isFlying()) return;

        if(event.getPlayer().hasPermission("solarisefixes.admin")) return;

        event.getPlayer().setFlying(false);
        event.getPlayer().setAllowFlight(false);
    }
}
