package com.trophonix.tradeplus.events;

import com.trophonix.tradeplus.logging.TradeLog;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TradeCompleteEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final TradeLog trade;
    private final Player playerOne;
    private final Player playerTwo;

    public TradeCompleteEvent(TradeLog trade, Player playerOne, Player playerTwo) {
        this.trade = trade;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public TradeCompleteEvent(boolean isAsync, TradeLog trade, Player playerOne, Player playerTwo) {
        super(isAsync);
        this.trade = trade;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public TradeLog getTrade() {
        return trade;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }
}
