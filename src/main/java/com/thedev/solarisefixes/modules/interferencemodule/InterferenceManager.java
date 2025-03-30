package com.thedev.solarisefixes.modules.interferencemodule;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InterferenceManager {

    private final Map<UUID, String> factionsTnT = new HashMap<>();

    public void addTntEntity(UUID entityUUID, String factionName) {
        factionsTnT.put(entityUUID, factionName);
    }

    public boolean containsTntEntity(UUID entityUUID) {
        return factionsTnT.containsKey(entityUUID);
    }

    public void removeTntEntity(UUID entityUUID) {
        factionsTnT.remove(entityUUID);
    }

    public String getEntityFaction(UUID entityUUID) {
        return factionsTnT.getOrDefault(entityUUID, "none");
    }
}
