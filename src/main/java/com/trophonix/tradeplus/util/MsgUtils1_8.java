package com.trophonix.tradeplus.util;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

class MsgUtils1_8 {

    public static void send(Player player, String onHover, String onClick, String[] messages) {
        for (String m : messages) {
            BaseComponent[] comps =
                    TextComponent.fromLegacyText(MsgUtils.color(m));
            for (BaseComponent comp : comps) {
                if (onHover != null)
                    comp.setHoverEvent(
                            new HoverEvent(
                                    HoverEvent.Action.SHOW_TEXT,
                                    TextComponent.fromLegacyText(
                                            MsgUtils.color(onHover))));
                if (onClick != null)
                    comp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, onClick));
            }
            player.spigot().sendMessage(comps);
        }
    }
}
