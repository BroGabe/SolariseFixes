package com.thedev.solarisefixes.modules.combatmodule;

import com.golfing8.kore.feature.RaidingOutpostFeature;
import com.thedev.solarisefixes.SolariseFixes;
import com.thedev.solarisefixes.modules.combatmodule.events.CombatTagExpireEvent;
import com.thedev.solarisefixes.utils.ColorUtil;
import net.minelink.ctplus.TagManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class CombatManager {

//    private final SolariseFixes plugin;
//
//    private final TagManager tagManager;
//
//    private final Set<UUID> taggedPlayers = new HashSet<>();
//
//    private BukkitTask combatTask;
//
//    public CombatManager(SolariseFixes plugin) {
//        this.plugin = plugin;
//
//        tagManager = plugin.getCombatTagPlus().getTagManager();
//    }
//
//    public void addTaggedPlayers(Player player1, Player player2) {
//        taggedPlayers.add(player1.getUniqueId());
//        taggedPlayers.add(player2.getUniqueId());
//
//        startTask();
//    }
//
//    private boolean isTaskActive() {
//        return combatTask != null && Bukkit.getScheduler().isCurrentlyRunning(combatTask.getTaskId());
//    }
//
//    private void startTask() {
//        if(isTaskActive()) return;
//
//        // Do the task to check if they are still tagged
//        combatTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
//            if(taggedPlayers.isEmpty()) {
//                Bukkit.getScheduler().cancelTask(combatTask.getTaskId());
//                combatTask.cancel();
//                return;
//            }
//
//            Iterator<UUID> playerIterator = taggedPlayers.iterator();
//
//            while(playerIterator.hasNext()) {
//                UUID taggedUUID = playerIterator.next();
//
//                if(Bukkit.getPlayer(taggedUUID) == null) {
//                    playerIterator.remove();
//                    continue;
//                }
//
//                if(tagManager.isTagged(taggedUUID)) continue;
//
//                Player player = Bukkit.getPlayer(taggedUUID);
//
//                CombatTagExpireEvent event = new CombatTagExpireEvent(player);
//
//                Bukkit.getPluginManager().callEvent(event);
//
//                playerIterator.remove();
//            }
//        }, 20L, 1L);
//    }
}
