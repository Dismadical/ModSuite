package me.dismadical.modsuite.commands.chat.menus.slowchatmenu.buttons;

import me.dismadical.modsuite.ModSuite;
import me.dismadical.modsuite.utils.CC;
import me.dismadical.modsuite.utils.ItemBuilder;
import me.dismadical.modsuite.utils.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dismadical (me.dismadical.modsuite.commands.chat.menus.slowchatmenu.buttons)
 * 7/14/2022
 * 4:55 PM
 * ModSuite
 * me.dismadical.modsuite.commands.chat.menus.slowchatmenu.buttons
 */

public class ChangeSlowSpeedButton extends Button {


    @Override
    public ItemStack getItemStack() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.CLOCK);
        itemBuilder.setAmount(1);
        itemBuilder.setName(CC.translate("&b&lChange Slow Chat Speed"));
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&7Click to change the"));
        lore.add(CC.translate("&7speed of the slow chat"));
        lore.add("");
        lore.add(CC.translate("&cSpeed&7: &c" + ModSuite.getInstance().getChatSlowTime() + " Seconds"));
        itemBuilder.setLore(lore);
        return itemBuilder.build();
    }

    @Override
    public void handleButton(Player player) {
        player.closeInventory();
        player.setMetadata("slowchat", new FixedMetadataValue(ModSuite.getInstance(), 0));
        player.sendMessage(CC.translate("&aType the new speed in seconds."));
    }
}
