package com.thedev.solarisefixes.listeners;

import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.perms.Relation;
import com.thedev.solarisefixes.SolariseFixes;
import com.thedev.solarisefixes.utils.ColorUtil;
import net.ess3.api.events.UserTeleportHomeEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class MiscFixes implements Listener {

    private final FileConfiguration config;

    public MiscFixes(SolariseFixes plugin) {
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
}
