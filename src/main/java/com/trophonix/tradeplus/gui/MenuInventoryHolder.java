package com.trophonix.tradeplus.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuInventoryHolder implements InventoryHolder {

    private Inventory current;

    @Override
    public Inventory getInventory() {
        return current;
    }

    public Inventory getCurrent() {
        return current;
    }

    public void setCurrent(Inventory current) {
        this.current = current;
    }
}
