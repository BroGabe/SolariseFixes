package com.thedev.solarisefixes.modules.interferencemodule.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionShootFactionEvent extends Event implements Cancellable {

    private final String faction1;

    private final String faction2;

    public FactionShootFactionEvent(String faction1, String faction2) {
        this.faction1 = faction1;
        this.faction2 = faction2;
    }
    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean cancel) {

    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
