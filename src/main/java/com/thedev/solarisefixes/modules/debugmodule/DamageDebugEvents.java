package com.thedev.solarisefixes.modules.debugmodule;

import com.thedev.solarisefixes.SolariseFixes;
import com.thedev.solarisefixes.utils.ColorUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.text.DecimalFormat;

public class DamageDebugEvents implements Listener {

    private final SolariseFixes plugin;

    public DamageDebugEvents(SolariseFixes plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) return;

        DebugModule debugModule = plugin.getModuleManager().getDebugModule();

        if(!debugModule.containsDebug(event.getDamager().getUniqueId())) return;

        Player attacker = (Player) event.getDamager();
        Player defender = (Player) event.getEntity();

        DecimalFormat formatter = new DecimalFormat("#.##");

        String healthBefore = formatter.format(defender.getHealth());
        String healthAfter = formatter.format(defender.getHealth() - event.getFinalDamage());


        double heartsDealth = defender.getHealth() - (defender.getHealth() - event.getFinalDamage());

        attacker.sendMessage(ColorUtil.color("&7"));
        attacker.sendMessage(ColorUtil.color("&4&lHealth before: &a" + healthBefore));
        attacker.sendMessage(ColorUtil.color("&7"));
        attacker.sendMessage(ColorUtil.color("&4&lHealth after: &a" + healthAfter));
        attacker.sendMessage(ColorUtil.color("&7"));
        attacker.sendMessage(ColorUtil.color("&bHearts dealt: &f" + formatter.format(heartsDealth)));
        attacker.sendMessage(ColorUtil.color("&7"));
    }

    @EventHandler
    public void forStr3(PlayerInteractEvent event) {
        if(!event.getPlayer().hasPermission("solarisefixes.admin")) return;

        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        if(event.getClickedBlock().getType() != Material.EMERALD_BLOCK) return;

        ItemStack handItem = event.getPlayer().getItemInHand();

        if(handItem == null || handItem.getType() == Material.AIR ||
        handItem.getType() != Material.STICK) return;


        event.getPlayer().removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 30, 2));
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 30, 0));
    }

    @EventHandler
    public void onToggle(PlayerInteractEvent event) {
        if(!event.getPlayer().hasPermission("solarisefixes.admin")) return;

        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        if(event.getClickedBlock().getType() != Material.REDSTONE_BLOCK) return;

        event.getPlayer().sendMessage(ColorUtil.color("&5&lSOLARISEMC &fYou have &etoggled&f your damage debug!"));

        plugin.getModuleManager().getDebugModule().toggleDebug(event.getPlayer().getUniqueId());
    }
}
