package me.dismadical.modsuite.utils.menu;

import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * @author Dismadical (me.dismadical.survival.utils.menu)
 * 7/12/2022
 * 6:38 PM
 * Survival
 * me.dismadical.survival.utils.menu
 */

public abstract class Button {

    protected ItemStack itemStack;
    protected ClickType clickAction;

    public Button() {}

    public abstract ItemStack getItemStack();

    public abstract void handleButton(Player player);

    public ClickType getClickType() {
        return clickAction;
    }

    public void setClickType(ClickType clickAction) {
        this.clickAction = clickAction;
    }



}
