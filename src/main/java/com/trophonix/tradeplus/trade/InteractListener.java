package com.trophonix.tradeplus.trade;

import com.trophonix.tradeplus.TradePlus;
import com.trophonix.tradeplus.util.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InteractListener implements Listener {

    private final TradePlus pl;

    private final Map<UUID, Long> lastTrigger = new HashMap<>();

    public InteractListener(TradePlus pl) {
        this.pl = pl;
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() instanceof Player && pl.getTradeConfig().isAllowShiftRightClick()) {
            Long last = lastTrigger.get(event.getPlayer().getUniqueId());
            if (last != null && System.currentTimeMillis() < last + 5000L) return;
            Player player = event.getPlayer();
            Player interacted = (Player) event.getRightClicked();
            if (PlayerUtil.isVanished(interacted)) {
                return;
            }
            String action = pl.getTradeConfig().getAction();
            if ((action.contains("sneak") || action.contains("crouch") || action.contains("shift"))
                    && !player.isSneaking()) return;
            if (action.contains("right")) {
                event.setCancelled(true);
                event.getPlayer().performCommand("trade " + interacted.getName());
                lastTrigger.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Long last = lastTrigger.get(event.getEntity().getUniqueId());
            if (last != null && System.currentTimeMillis() < last + 5000L) return;
            Player player = (Player) event.getDamager();
            Player interacted = (Player) event.getEntity();
            if (PlayerUtil.isVanished(interacted)) {
                return;
            }
            String action = pl.getTradeConfig().getAction();
            if ((action.contains("sneak") || action.contains("crouch") || action.contains("shift"))
                    && !player.isSneaking()) return;
            if (action.contains("left")) {
                event.setCancelled(true);
                player.performCommand("trade " + interacted.getName());
                lastTrigger.put(player.getUniqueId(), System.currentTimeMillis());
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        lastTrigger.remove(event.getPlayer().getUniqueId());
    }

}
