package com.thedev.solarisefixes.modules.interferencemodule.listeners;

import com.golfing8.winespigot.event.TnTDispenseEvent;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import com.thedev.solarisefixes.modules.interferencemodule.InterferenceManager;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class RaidListener implements Listener {

    private final InterferenceManager manager;

    public RaidListener(InterferenceManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onRaid(TnTDispenseEvent event) {
        FLocation fLocation = new FLocation(event.getEntity().getLocation());
        Faction factionAtLocation = Board.getInstance().getFactionAt(fLocation);

        if(factionAtLocation.isWilderness() || factionAtLocation.isWarZone() || factionAtLocation.isSafeZone()) return;

        manager.addTntEntity(event.getEntity().getUniqueId(), factionAtLocation.getTag());
    }

    @EventHandler
    public void onExplosion(ExplosionPrimeEvent event) {
        if(event.getEntity().getType() != EntityType.PRIMED_TNT) return;

        if(!manager.containsTntEntity(event.getEntity().getUniqueId())) return;



    }

}
