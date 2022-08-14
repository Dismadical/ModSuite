package me.dismadical.modsuite.utils;

import me.dismadical.modsuite.ModHandler;
import me.dismadical.modsuite.ModSuite;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

/**
 * @author Dismadical (me.dismadical.modsuite.utils)
 * 7/13/2022
 * 5:50 PM
 * ModSuite
 * me.dismadical.modsuite.utils
 */

public class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public void setMaterial(Material material) {
        this.itemStack.setType(material);
    }

    public void setAmount(int amount) {
        this.itemStack.setAmount(amount);
    }

    public void setName(String name) {
        this.itemMeta.setDisplayName(name);
    }

    public void setLore(List<String> lore) {
        this.itemMeta.setLore(lore);
    }

    public void addEnchant(Enchantment enchantment, int level) {
        this.itemMeta.addEnchant(enchantment, level, true);
    }

    public void setPlayerHeadOwner(String owner) {
        SkullMeta skullMeta = (SkullMeta) this.itemMeta;
        skullMeta.setOwner(owner);
        this.itemMeta = (ItemMeta) skullMeta;
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }




}
