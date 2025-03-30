package com.thedev.solarisefixes.modules.maskmodule;

import com.thedev.solarisefixes.SolariseFixes;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MaskManager {

    private final FileConfiguration config;

    public MaskManager(SolariseFixes plugin) {
        config = plugin.getMaskConfig().getConfig();
    }

    public boolean hasMask(Player player) {
        if(player.getInventory().getHelmet() == null) return false;

        ItemStack helmet = player.getInventory().getHelmet();

        if(helmet.getType() == Material.AIR || !helmet.hasItemMeta()
                || !helmet.getItemMeta().hasLore()) return false;

        return helmet.getItemMeta().getLore().contains(convertCode(config.getString("attached-lore")));
    }

    public boolean hasHydraMask(Player player) {
        if(!hasMask(player)) return false;
        if(hasFounderMask(player)) return true;

        ItemStack helmet = player.getInventory().getHelmet();

        return helmet.getItemMeta().getLore().contains(convertCode(config.getString("hydra-mask.attached-lore")));
    }

    public boolean hasSlayerMask(Player player) {
        if(!hasMask(player)) return false;
        if(hasFounderMask(player)) return true;

        ItemStack helmet = player.getInventory().getHelmet();

        return helmet.getItemMeta().getLore().contains(convertCode(config.getString("slayer-mask.attached-lore")));
    }

    public boolean hasComboMask(Player player) {
        if(!hasMask(player)) return false;
        if(hasFounderMask(player)) return true;

        ItemStack helmet = player.getInventory().getHelmet();

        return helmet.getItemMeta().getLore().contains(convertCode(config.getString("combo-mask.attached-lore")));
    }

    private boolean hasFounderMask(Player player) {
        if(!hasMask(player)) return false;

        ItemStack helmet = player.getInventory().getHelmet();

        return helmet.getItemMeta().getLore().contains(convertCode(config.getString("founder-mask.attached-lore")));
    }

    private String convertCode(String string) {
        return string.replace("&", "§");
    }
}
