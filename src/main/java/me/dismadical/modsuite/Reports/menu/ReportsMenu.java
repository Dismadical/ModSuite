package me.dismadical.modsuite.Reports.menu;

import me.dismadical.modsuite.Reports.Report;
import me.dismadical.modsuite.Reports.ReportHandler;
import me.dismadical.modsuite.utils.CC;
import me.dismadical.modsuite.utils.ItemBuilder;
import me.dismadical.modsuite.utils.menu.Button;
import me.dismadical.modsuite.utils.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Dismadical (me.dismadical.modsuite.Reports.menu)
 * 7/13/2022
 * 8:05 PM
 * ModSuite
 * me.dismadical.modsuite.Reports.menu
 */

public class ReportsMenu extends Menu {

    @Override
    public String getTitle() {
        return CC.translate("&b&lREPORTS");
    }

    @Override
    public int getSlots() {
        return (int) (Math.ceil(ReportHandler.getReports().size() / 9.0) * 9);
    }

    @Override
    public HashMap<Integer, Button> getButtons() {
        HashMap<Integer, Button> buttons = new HashMap<>();
        for (Report report : ReportHandler.getReportList()) {
            buttons.put(buttons.size(), new Button() {
                @Override
                public ItemStack getItemStack() {
                    ItemBuilder itemBuilder = new ItemBuilder(Material.PAPER);
                    itemBuilder.setName(CC.translate("&b&lREPORT #" + report.getId()));
                    itemBuilder.setAmount(1);
                    List<String> lore = new ArrayList<>();
                    lore.add(CC.translate("&7Reporter: &b" + report.getReporter()));
                    lore.add(CC.translate("&7Reported: &b" + report.getReported()));
                    lore.add(CC.translate("&7Reason: &b" + report.getReason()));
                    lore.add("");
                    lore.add(CC.translate("&a&lLEFT CLICK TO TELEPORT"));
                    lore.add(CC.translate("&c&lRIGHT CLICK TO DELETE"));
                    itemBuilder.setLore(lore);
                    return itemBuilder.build();
                }

                @Override
                public void handleButton(Player player) {
                    if (this.getClickType() == ClickType.LEFT) {
                        if (Bukkit.getPlayerExact(report.getReported()) == null) {
                            player.sendMessage(CC.translate("&cThat player is not online."));
                        } else {
                            player.teleport(Bukkit.getPlayerExact(report.getReported()));
                            player.sendMessage(CC.translate("&aTeleported to " + report.getReported()));
                        }
                        return;
                    } else {
                        int id = Integer.parseInt(ChatColor.stripColor(getItemStack().getItemMeta().getDisplayName().replace("REPORT #", "")));
                        ReportHandler.deleteReport(id);
                        player.closeInventory();
                        if (ReportHandler.getReportList().isEmpty()) {
                            player.sendMessage(CC.translate("&cNo reports found."));
                        } else {
                            Menu menu = new ReportsMenu();
                            menu.open(player);
                        }
                    }
                }
            });
        }
        return buttons;
    }

}
