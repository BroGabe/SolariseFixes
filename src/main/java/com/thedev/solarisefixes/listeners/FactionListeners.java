package com.thedev.solarisefixes.listeners;

import com.golfing8.kore.FactionsKore;
import com.golfing8.kore.event.ChunkBusterUseEvent;
import com.golfing8.kore.event.GenBlockUseEvent;
import com.golfing8.kore.event.StackedSpawnerRemoveEvent;
import com.golfing8.kore.event.roam.PlayerRoamExitEvent;
import com.golfing8.kore.feature.RaidSpectateFeature;
import com.golfing8.kore.feature.RaidingOutpostFeature;
import com.golfing8.kore.feature.ShieldFeature;
import com.massivecraft.factions.*;
import com.massivecraft.factions.event.FactionDisbandEvent;
import com.massivecraft.factions.event.LandUnclaimAllEvent;
import com.massivecraft.factions.event.LandUnclaimEvent;
import com.thedev.solarisefixes.SolariseFixes;
import com.thedev.solarisefixes.utils.ColorUtil;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FactionListeners implements Listener {

    private final SolariseFixes plugin;

    public FactionListeners(SolariseFixes plugin) {
        this.plugin = plugin;
    }

    /**
     * Fixes an issue where roaming in enemy land will disable
     * your flight when you exit roam in areas you can normally fly in.
     * @param event
     */
    @EventHandler
    public void onRoamExit(PlayerRoamExitEvent event) {
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(event.getPlayer());

        if(!fPlayer.canFlyAtLocation()) return;

        event.getPlayer().setAllowFlight(true);

        event.getPlayer().setFlying(true);
        fPlayer.setFlying(true);
    }

    /**
     * Fixes an issue where flight is disabled when spectating a raid
     * through /f spectate <raid>
     * @param event
     */
    @EventHandler
    public void onTryFly(PlayerMoveEvent event) {
        Block initialBlock = event.getFrom().clone().subtract(0, 1, 0).getBlock();
        Block toBlock = event.getTo().clone().subtract(0, 1, 0).getBlock();

        if(initialBlock.equals(toBlock)) return;

        RaidSpectateFeature feature = FactionsKore.get().getFeature(RaidSpectateFeature.class);

        if(!feature.isActivelySpectating(event.getPlayer())) return;

        Player spectator = event.getPlayer();
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(spectator);

        if(spectator.isFlying()) return;

        spectator.setAllowFlight(true);
        fPlayer.setFlying(true);
        spectator.setFlying(true);
    }

    @EventHandler
    public void onRpostGen(GenBlockUseEvent event) {
        Location placedLocation = event.getUsedAt();

        RaidingOutpostFeature feature = FactionsKore.get().getFeature(RaidingOutpostFeature.class);

        String rpostWorldName = feature.getOutpost().getWorld().getName();
        String placedWorldName = placedLocation.getWorld().getName();

        if(!rpostWorldName.equalsIgnoreCase(placedWorldName)) return;

        FLocation fLocation = new FLocation(placedLocation);

        if(!Board.getInstance().getFactionAt(fLocation).getTag().equalsIgnoreCase("RaidOutpost")) return;

        if(!feature.getOutpost().isBeingRaided()) return;

        event.getOwner().sendMessage(ColorUtil.color("&5&lSOLARISE&d&lMC &fYou &c&nCANNOT&f genpatch during an active raid!"));

        event.setCancelled(true);
    }
}
