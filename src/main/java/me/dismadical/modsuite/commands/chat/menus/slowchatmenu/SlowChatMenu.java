package me.dismadical.modsuite.commands.chat.menus.slowchatmenu;

import me.dismadical.modsuite.commands.chat.menus.slowchatmenu.buttons.ChangeSlowSpeedButton;
import me.dismadical.modsuite.commands.chat.menus.slowchatmenu.buttons.ToggleSlowChatButton;
import me.dismadical.modsuite.utils.CC;
import me.dismadical.modsuite.utils.menu.Button;
import me.dismadical.modsuite.utils.menu.Menu;

import java.util.HashMap;

/**
 * @author Dismadical (me.dismadical.modsuite.commands.chat.menus.slowchatmenu)
 * 7/14/2022
 * 4:49 PM
 * ModSuite
 * me.dismadical.modsuite.commands.chat.menus.slowchatmenu
 */

public class SlowChatMenu extends Menu {

    @Override
    public String getTitle() {
        return CC.translate("&b&lSlow Chat");
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public HashMap<Integer, Button> getButtons() {
        HashMap<Integer, Button> buttons = new HashMap<>();
        buttons.put(3, new ToggleSlowChatButton());
        buttons.put(5, new ChangeSlowSpeedButton());
        return buttons;
    }

}
