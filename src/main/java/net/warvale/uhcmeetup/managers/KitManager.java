package net.warvale.uhcmeetup.managers;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import net.warvale.uhcmeetup.kits.BuildUHCKit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KitManager {

    private static KitManager instance;
    private Random random = new Random();

    //kits
    private BuildUHCKit buildUHC;

    private List<ItemStack[]> inventoryItems = new ArrayList<>();
    private List<ItemStack[]> armorItems = new ArrayList<>();

    private KitManager() {
        this.buildUHC = new BuildUHCKit();
    }

    public static KitManager getInstance() {
        if (instance == null) {
            instance = new KitManager();
        }
        return instance;
    }

    public void randomizeKit(Player player) {
        if (player != null) {
            buildUHC.loadKit(player);
        }
    }

    private void loadKits() {

        ItemStack[] armor = new ItemStack[9];
        armor[3] = new ItemStack(Material.DIAMOND_HELMET);
        armor[3].addEnchantment(Enchantment.PROTECTION_PROJECTILE, 2);

        armor[2] = new ItemStack(Material.DIAMOND_CHESTPLATE);
        armor[2].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

        armor[1] = new ItemStack(Material.DIAMOND_LEGGINGS);
        armor[1].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

        armor[0] = new ItemStack(Material.DIAMOND_BOOTS);
        armor[0].addEnchantment(Enchantment.PROTECTION_PROJECTILE, 2);

        armorItems.add(armor);


    }

}
