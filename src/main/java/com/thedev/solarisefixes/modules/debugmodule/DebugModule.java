package com.thedev.solarisefixes.modules.debugmodule;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class DebugModule {

    private final Set<UUID> debugPlayers = new HashSet<>();

    public void toggleDebug(UUID uuid) {
        if(debugPlayers.contains(uuid)) {
            debugPlayers.remove(uuid);
            return;
        }

        debugPlayers.add(uuid);
    }

    public boolean containsDebug(UUID uuid) {
        return debugPlayers.contains(uuid);
    }

}
