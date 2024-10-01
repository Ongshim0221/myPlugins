package org.example1.terrahub;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemSettings {
    public ItemMeta setName(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        return meta;
    }
    public ItemMeta setLore(ItemStack item, ArrayList<String> lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        return meta;
    }
}
