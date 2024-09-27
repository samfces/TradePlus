package com.trophonix.tradeplus.gui;

import com.trophonix.tradeplus.util.ItemFactory;

public class MenuButton {

    private MenuAction action;
    private ItemFactory icon;

    public MenuButton(MenuAction action, ItemFactory icon) {
        this.action = action;
        this.icon = icon;
    }

    public MenuAction getAction() {
        return action;
    }

    public ItemFactory getIcon() {
        return icon;
    }
}
