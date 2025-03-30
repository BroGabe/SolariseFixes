package com.thedev.solarisefixes.listeners;

import com.golfing8.kore.FactionsKore;
import com.golfing8.kore.feature.OutpostFeature;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.thedev.solarisefixes.SolariseFixes;
import me.fullpage.mantichoes.api.events.HoeUseEvent;
import me.fullpage.manticrods.api.events.RodUseEvent;
import me.fullpage.manticsword.api.events.SwordUseEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class OutpostListeners implements Listener {

    private final OutpostFeature outpostFeature;

    private final String outpostName;

    private final String ecoOutpostName;

    private final double ecoMultiplier;

    private final double damageIncrease;

    private final double damageReduction;

    private final boolean enabled;

    public OutpostListeners(SolariseFixes plugin) {

        FileConfiguration config = plugin.getConfig();

        outpostName = config.getString("outpost-boost.name");
        ecoOutpostName = config.getString("outpost-boost.eco-boost.name");
        enabled = config.getBoolean("outpost-boost.enabled");

        ecoMultiplier = 1 + (config.getDouble("outpost-boost.eco-boost.amount") / 100);

        damageReduction = config.getDouble("outpost-boost.damage-reduction") / 100;
        damageIncrease = 1 + (config.getDouble("outpost-boost.damage-boost") / 100);

        outpostFeature = FactionsKore.get().getFeature(OutpostFeature.class);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) return;
        if(!outpostExists(outpostName)) return;

        Player attacker = (Player) event.getDamager();
        Player defender = (Player) event.getEntity();

        if(!hasOutpost(defender, outpostName) && !hasOutpost(attacker, outpostName)) return;

        if(hasOutpost(defender, outpostName) && inWater(defender)) {
            double decreaseDamage = event.getDamage() * damageReduction;
            event.setDamage(event.getDamage() - decreaseDamage);
        }

        if(!hasOutpost(attacker, outpostName) || !inWater(attacker)) return;

        event.setDamage(event.getDamage() * damageIncrease);
    }

    @EventHandler
    public void onSword(SwordUseEvent event) {
        if(!outpostExists(ecoOutpostName)) return;

        if(!hasOutpost(event.getPlayer(), ecoOutpostName)) return;

        event.setMoney(event.getMoney() * ecoMultiplier);
    }

    @EventHandler
    public void onRod(RodUseEvent event) {
        if(!outpostExists(ecoOutpostName)) return;

        if(!hasOutpost(event.getPlayer(), ecoOutpostName)) return;

        event.setMoney(event.getMoney() * ecoMultiplier);
    }

    @EventHandler
    public void onHoe(HoeUseEvent event) {
        if(!outpostExists(ecoOutpostName)) return;

        if(!hasOutpost(event.getPlayer(), ecoOutpostName)) return;

        event.setAutosellMoney(event.getAutosellMoney() * ecoMultiplier);
    }

    private boolean outpostExists(String outpostName) {
        return (outpostFeature.getOutpost(outpostName) != null);
    }

    private boolean hasOutpost(Player player, String outpostName) {
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);

        if(!fPlayer.hasFaction()) return false;

        return outpostFeature.hasOutpost(outpostName, fPlayer.getFaction().getId());
    }

    /**
     * Returns true if the player is inside water.
     * @return
     */
    private boolean inWater(Player player) {
        Location location = player.getLocation().getBlock().getLocation();

        return (location.getBlock().getType() == Material.WATER || location.getBlock().getType() == Material.STATIONARY_WATER);
    }
}
