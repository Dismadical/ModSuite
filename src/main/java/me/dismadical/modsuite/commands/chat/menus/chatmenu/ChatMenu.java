package me.dismadical.modsuite.commands.chat.menus.chatmenu;

import me.dismadical.modsuite.commands.chat.menus.chatmenu.buttons.ClearChatButton;
import me.dismadical.modsuite.commands.chat.menus.chatmenu.buttons.MuteChatButton;
import me.dismadical.modsuite.commands.chat.menus.chatmenu.buttons.SlowChatButton;
import me.dismadical.modsuite.utils.CC;
import me.dismadical.modsuite.utils.menu.Button;
import me.dismadical.modsuite.utils.menu.Menu;

import java.util.HashMap;

/**
 * @author Dismadical (me.dismadical.modsuite.commands.chat.menus.chatmenu)
 * 7/14/2022
 * 4:32 PM
 * ModSuite
 * me.dismadical.modsuite.commands.chat.menus.chatmenu
 */

public class ChatMenu extends Menu {


    @Override
    public String getTitle() {
        return CC.translate("&b&lChat");
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public HashMap<Integer, Button> getButtons() {
        HashMap<Integer, Button> buttons = new HashMap<>();
        buttons.put(11, new SlowChatButton());
        buttons.put(13, new ClearChatButton());
        buttons.put(15, new MuteChatButton());
        return buttons;
    }
}
