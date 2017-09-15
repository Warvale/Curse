package net.warvale.uhcmeetup.utils;

import org.bukkit.inventory.ItemStack;

public class KitUtils {

    public static ItemStack[] clone(ItemStack[] items) {
        ItemStack[] ret = new ItemStack[items.length];
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                ret[i] = items[i].clone();
            }
        }
        return ret;
    }

}
