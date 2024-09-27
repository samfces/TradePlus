package com.trophonix.tradeplus.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Map;

public class TradeMenu {

    private Inventory inventory;
    private Player player;
    private TradeMenu partner;

    private Map<Integer, MenuButton> buttons;

    public Inventory getInventory() {
        return inventory;
    }

    public Player getPlayer() {
        return player;
    }

    public TradeMenu getPartner() {
        return partner;
    }

    public Map<Integer, MenuButton> getButtons() {
        return buttons;
    }
}
