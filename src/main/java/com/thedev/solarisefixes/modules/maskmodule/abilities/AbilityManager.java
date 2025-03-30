package com.thedev.solarisefixes.modules.maskmodule.abilities;

import com.thedev.solarisefixes.SolariseFixes;
import com.thedev.solarisefixes.modules.maskmodule.abilities.types.ComboManager;

public class AbilityManager {

    private final ComboManager comboManager;

    public AbilityManager(SolariseFixes plugin) {
        comboManager = new ComboManager(plugin);
    }

    public ComboManager getComboManager() {
        return comboManager;
    }
}
