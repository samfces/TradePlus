package com.trophonix.tradeplus.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Lucas on 5/20/17.
 */
public class TradeAcceptEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final Player sender;
    private final Player receiver;

    private boolean cancelled;

    public TradeAcceptEvent(Player sender, Player receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Player getSender() {
        return sender;
    }

    public Player getReceiver() {
        return receiver;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
