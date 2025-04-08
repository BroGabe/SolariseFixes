package com.thedev.solarisefixes.listeners;

import com.golfing8.kore.event.PlayerPrinterEnterEvent;
import com.golfing8.kore.event.PlayerPrinterExitEvent;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.perms.Relation;
import com.thedev.solarisefixes.SolariseFixes;
import com.thedev.solarisefixes.utils.ColorUtil;
import it.ytnoos.lpx.api.LPX;
import it.ytnoos.lpx.api.LPXPlugin;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import net.ess3.api.events.UserTeleportHomeEvent;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Optional;

public class MiscListeners implements Listener {

    private final SolariseFixes plugin;

    private final FileConfiguration config;

    public MiscListeners(SolariseFixes plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    /**
     * Fixes an issue where player hearts multipliy
     * from healthboost on armorsets.
     * @param event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(event.getPlayer().getMaxHealth() > 20.0) {
            event.getPlayer().setMaxHealth(20.0);
        }
    }

    /**
     * Disables bow-boosting if config has it set to true.
     * @param event
     */
    @EventHandler
    public void onBow(EntityDamageByEntityEvent event) {
        if(!config.getBoolean("anti-bow-boosting")) return;

        if(!(event.getDamager() instanceof Arrow)) return;
        if(!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();
        Arrow arrow = (Arrow) event.getDamager();

        if(!player.equals(arrow.getShooter())) return;
        if(player.hasPermission(config.getString("admin-permission"))) return;

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolarise&d&lMC &eBow boosting is disabled!"));

        event.setCancelled(true);
    }

    /**
     * Disallows teleportation into enemy land if set to true in config.
     * @param event
     */
    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if(!config.getBoolean("disallow-enemy-tp")) return;

        if(event.getPlayer().hasPermission("solarise.admin")) return;

        if(event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) return;

        Player player = event.getPlayer();
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);

        Location to = event.getTo();

        FLocation toFlocation = new FLocation(to);

        if(fPlayer.getRelationToLocation(toFlocation) != Relation.ENEMY) return;

        event.setCancelled(true);

        player.sendMessage(ColorUtil.color("&4&lFACTIONS &cYou cannot teleport to enemy land!"));
    }

    /**
     * Disallows teleporting to essentials homes in enemy land if set to true.
     * @param event
     */
    @EventHandler
    public void onHome(UserTeleportHomeEvent event) {
        if(!config.getBoolean("disable-enemy-home")) return;
        if(event.getUser().getBase().hasPermission("solarise.admin")) return;

        Player player = event.getUser().getBase();
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);

        FLocation fLocation = new FLocation(event.getHomeLocation());

        if(fPlayer.getRelationToLocation(fLocation) != Relation.ENEMY) return;

        event.setCancelled(true);

        player.sendMessage(ColorUtil.color("&4&lFACTIONS &cYou cannot teleport to enemy land!"));
    }

    /**
     * Just removes resistance from a player if they consume a golden apple.
     * @param event
     */
    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        if(event.getItem().getType() != Material.GOLDEN_APPLE) return;
        if(!config.getBoolean("disable-apple-resistance")) return;

        Player player = event.getPlayer();

        if(player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) return;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        }, 10L);
    }

    @EventHandler
    public void onBrewingPlace(BlockPlaceEvent event) {
        if(event.getPlayer().hasPermission("solarisefixes.admin")) return;
        if(event.getBlock() == null || event.getBlock().getType() == null) return;
        if(event.getBlock().getType() != Material.BREWING_STAND) return;

        event.getPlayer().sendMessage(ColorUtil.color("&5&lSOLARISE&D&LMC &fYou &e&nCANNOT&f place this item!"));
        event.setCancelled(true);
    }

    @EventHandler
    public void onBrewingOpen(InventoryOpenEvent event) {
        if(!(event.getPlayer() instanceof Player)) return;
        if(event.getInventory().getType() != InventoryType.BREWING) return;

        Player player = (Player) event.getPlayer();
        player.sendMessage(ColorUtil.color("&5&lSOLARISE&D&LMC &fYou &c&nCANNOT&f use brewing stands!"));

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onStrDamage(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) return;

        Player attacker = (Player) event.getDamager();


        for(PotionEffect effect : attacker.getActivePotionEffects()) {
            if(!effect.getType().getName().equalsIgnoreCase("INCREASE_DAMAGE")) continue;
            if(effect.getAmplifier() != 2) return;

            event.setDamage(event.getDamage() * 0.90);
        }
    }

//    @EventHandler
//    public void onPrinter(PlayerPrinterEnterEvent event) {
//        Optional<ProtocolPlayer> playerOptional = plugin.getLpxPlugin().getInjector().getPlayer(event.getPlayer());
//
//        if(!playerOptional.isPresent()) return;
//
//        playerOptional.get().getPrinterData().setPrinterMode(true, 0);
//    }
//
//    @EventHandler
//    public void onPrinter(PlayerPrinterExitEvent event) {
//        if(event.getPlayer() == null) return;
//
//        Optional<ProtocolPlayer> playerOptional = plugin.getLpxPlugin().getInjector().getPlayer(event.getPlayer());
//
//        if(!playerOptional.isPresent()) return;
//
//        playerOptional.get().getPrinterData().setPrinterMode(false, 0);
//    }
}
