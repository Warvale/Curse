package net.warvale.uhcmeetup.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import net.warvale.uhcmeetup.utils.ItemStackUtil;

public class BuildUHCKit extends Kit {

    public BuildUHCKit() {
        super("BuildUHC");

        // Create default armor kit
        this.armorItems[3] = new ItemStack(Material.DIAMOND_HELMET);
        this.armorItems[3].addEnchantment(Enchantment.PROTECTION_PROJECTILE, 2);

        this.armorItems[2] = new ItemStack(Material.DIAMOND_CHESTPLATE);
        this.armorItems[2].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

        this.armorItems[1] = new ItemStack(Material.DIAMOND_LEGGINGS);
        this.armorItems[1].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

        this.armorItems[0] = new ItemStack(Material.DIAMOND_BOOTS);
        this.armorItems[0].addEnchantment(Enchantment.PROTECTION_PROJECTILE, 2);

        // Create default inventory kit
        this.inventoryItems[0] = new ItemStack(Material.DIAMOND_SWORD);
        this.inventoryItems[0].addEnchantment(Enchantment.DAMAGE_ALL, 3);

        this.inventoryItems[1] = new ItemStack(Material.FISHING_ROD);

        this.inventoryItems[2] = new ItemStack(Material.BOW);
        this.inventoryItems[2].addEnchantment(Enchantment.ARROW_DAMAGE, 3);

        this.inventoryItems[3] = new ItemStack(Material.COOKED_BEEF, 64);
        this.inventoryItems[4] = new ItemStack(Material.GOLDEN_APPLE, 6);
        this.inventoryItems[5] = ItemStackUtil.createGoldenHead(3);
        this.inventoryItems[6] = new ItemStack(Material.DIAMOND_PICKAXE);
        this.inventoryItems[7] = new ItemStack(Material.DIAMOND_AXE);
        this.inventoryItems[8] = new ItemStack(Material.WOOD, 64);
        this.inventoryItems[9] = new ItemStack(Material.ARROW, 64);
        this.inventoryItems[10] = new ItemStack(Material.COBBLESTONE, 64);
        this.inventoryItems[11] = new ItemStack(Material.WATER_BUCKET);
        this.inventoryItems[12] = new ItemStack(Material.WATER_BUCKET);
        this.inventoryItems[13] = new ItemStack(Material.LAVA_BUCKET);
        this.inventoryItems[14] = new ItemStack(Material.LAVA_BUCKET);
    }



}
