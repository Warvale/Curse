package net.warvale.uhcmeetup.kits;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import net.warvale.uhcmeetup.utils.KitUtils;

import java.util.UUID;

public class Kit {

    private String name;
    protected ItemStack[] inventoryItems = new ItemStack[36];
    protected ItemStack[] armorItems = new ItemStack[9];

    public Kit(String name) {
        this.name = name;
    }

    public ItemStack[] getInventoryItems(){
        return this.inventoryItems;
    }
    public ItemStack[] getArmorItems(){
        return this.armorItems;
    }

    public void loadKit(Player player) {
        player.getInventory().setContents(this.inventoryItems);
        player.getInventory().setArmorContents(this.armorItems);
    }

}
