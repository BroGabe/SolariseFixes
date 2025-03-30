package com.thedev.solarisefixes.modules;

import com.thedev.solarisefixes.SolariseFixes;
import com.thedev.solarisefixes.modules.combatmodule.CombatManager;
import com.thedev.solarisefixes.modules.combatmodule.PrinterAbuseManager;
import com.thedev.solarisefixes.modules.debugmodule.DebugModule;
import com.thedev.solarisefixes.modules.maskmodule.MaskManager;
import lombok.Getter;

@Getter
public class ModuleManager {

    private final MaskManager maskManager;

    private final DebugModule debugModule;

    public ModuleManager(SolariseFixes plugin) {
        maskManager = new MaskManager(plugin);
        debugModule = new DebugModule();
    }

}
