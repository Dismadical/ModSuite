package me.dismadical.modsuite.commands.chat.menus.slowchatmenu.buttons;

import com.sun.org.apache.xpath.internal.operations.Mod;
import me.dismadical.modsuite.ModSuite;
import me.dismadical.modsuite.utils.CC;
import me.dismadical.modsuite.utils.ItemBuilder;
import me.dismadical.modsuite.utils.menu.Button;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dismadical (me.dismadical.modsuite.commands.chat.menus.slowchatmenu.buttons)
 * 7/14/2022
 * 4:53 PM
 * ModSuite
 * me.dismadical.modsuite.commands.chat.menus.slowchatmenu.buttons
 */

public class ToggleSlowChatButton extends Button {

    @Override
    public ItemStack getItemStack() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.LEVER);
        itemBuilder.setAmount(1);
        itemBuilder.setName(CC.translate("&b&lToggle Slow Chat"));
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&7Click to toggle the slow"));
        lore.add(CC.translate("&7chat for normal players."));
        lore.add("");
        lore.add(CC.translate("&cSlowed&7: " + ((ModSuite.getInstance().isChatSlowed()) ? CC.translate("&aYes") : CC.translate("&cNo"))));
        itemBuilder.setLore(lore);
        return itemBuilder.build();
    }

    @Override
    public void handleButton(Player player) {
        if (ModSuite.getInstance().isChatSlowed()) {
            ModSuite.getInstance().setChatSlowed(false);
        } else {
            ModSuite.getInstance().setChatSlowed(true);
        }
        player.closeInventory();
        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(CC.translate("&cChat has been " + (ModSuite.getInstance().isChatSlowed() ? "&cslowed" : "&cunslowed") + " by " + player.getName() + ".")));
    }
}
