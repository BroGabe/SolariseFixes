package com.thedev.solarisefixes.modules.combatmodule.listeners;

import com.golfing8.kore.event.PlayerPrinterEnterEvent;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.thedev.solarisefixes.SolariseFixes;
import com.thedev.solarisefixes.modules.combatmodule.CombatManager;
import com.thedev.solarisefixes.modules.combatmodule.PrinterAbuseManager;
import com.thedev.solarisefixes.modules.combatmodule.events.CombatTagExpireEvent;
import com.thedev.solarisefixes.utils.ColorUtil;
import net.minelink.ctplus.event.PlayerCombatTagEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CombatListener implements Listener {

    private final PrinterAbuseManager printerAbuseManager;

    private final CombatManager combatManager;

    public CombatListener(SolariseFixes plugin) {
        combatManager = plugin.getModuleManager().getCombatManager();
        printerAbuseManager = plugin.getModuleManager().getPrinterAbuseManager();
    }

    @EventHandler
    public void onCombat(PlayerCombatTagEvent event) {
        combatManager.addTaggedPlayers(event.getVictim(), event.getAttacker());
    }

    @EventHandler
    public void onExpire(CombatTagExpireEvent event) {
        printerAbuseManager.addCooldownPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPrinter(PlayerPrinterEnterEvent event) {
        if(event.getPlayer().hasPermission("solarisefixes.admin")) return;

        if(!printerAbuseManager.hasCooldown(event.getPlayer())) return;

        boolean nearbyEnemy = false;

        for(Entity entity : event.getPlayer().getNearbyEntities(20, 5, 20)) {
            if(!(entity instanceof Player)) continue;
            Player nearbyPlayer = (Player) entity;

            FPlayer nearbyFPlayer = FPlayers.getInstance().getByPlayer(nearbyPlayer);

            if(!nearbyFPlayer.hasFaction()) continue;
            if(nearbyPlayer.hasPermission("solarisefixes.admin")) continue;

            nearbyEnemy = true;
        }

        if(!nearbyEnemy) return;

        event.getPlayer().sendMessage(ColorUtil.color("&5&lSOLARISE&d&lMC &fYou &c&nCANNOT&f enter printer quickly after leaving combat!"));
        event.setCancelled(true);
    }
}
