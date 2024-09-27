package com.trophonix.tradeplus.commands;

import com.trophonix.tradeplus.TradePlus;
import com.trophonix.tradeplus.trade.Trade;
import com.trophonix.tradeplus.util.MsgUtils;
import com.trophonix.tradeplus.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TradePlusCommand implements CommandExecutor, TabCompleter {

    private final TradePlus pl;
    private final List<String> arg0 = Arrays.asList("reload", "rl", "force", "spectate");

    public TradePlusCommand(TradePlus pl) {
//    super(Collections.singletonList("tradeplus"));
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("tradeplus.admin")) {
            pl.getTradeConfig().getErrorsNoPermsAdmin().send(sender);
            return true;
        }

        switch (args.length) {
            case 1:
                if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
                    pl.reload();
                    pl.getTradeConfig().getAdminConfigReloaded().send(sender);
                    return true;
                }
                break;
            case 2:
                if (pl.getTradeConfig().isSpectateEnabled() && args[0].equalsIgnoreCase("spectate")) {
                    Player player = Bukkit.getPlayer(args[1]);
                    if (player == null || !player.isOnline()) {
                        pl.getTradeConfig().getAdminInvalidPlayers().send(sender);
                        return true;
                    }
                    Trade trade = pl.getTrade(player);
                    if (trade == null) {
                        pl.getTradeConfig().getAdminNoTrade().send(player);
                    } else {
                        player.openInventory(trade.getSpectatorInv());
                    }
                    return true;
                }
                break;
            case 3:
                if (args[0].equalsIgnoreCase("force")) {
                    Player p1 = Bukkit.getPlayer(args[1]);
                    Player p2 = Bukkit.getPlayer(args[2]);
                    if (p1 == null || p2 == null || !p1.isOnline() || !p2.isOnline() || p1.equals(p2)) {
                        pl.getTradeConfig().getAdminInvalidPlayers().send(sender);
                        return true;
                    }
                    pl.getTradeConfig().getAdminForcedTrade().send(sender, "%PLAYER1%", p1.getName(), "%PLAYER2%", p2.getName());
                    pl.getTradeConfig().getForcedTrade().send(p1, "%PLAYER%", p2.getName());
                    pl.getTradeConfig().getForcedTrade().send(p2, "%PLAYER%", p1.getName());
                    Trade trade = new Trade(p1, p2);
                    if (sender instanceof Player && !(sender.equals(p1) || sender.equals(p2)))
                        ((Player) sender).openInventory(trade.getSpectatorInv());
                    return true;
                } else if (args[0].equalsIgnoreCase("spectate")) {
                    if (!(sender instanceof Player)) {
                        pl.getTradeConfig().getAdminPlayersOnly().send(sender);
                        return true;
                    }
                    Player player = (Player) sender;
                    Player p1 = Bukkit.getPlayer(args[1]);
                    Player p2 = Bukkit.getPlayer(args[2]);
                    if (p1 == null || p2 == null || !p1.isOnline() || !p2.isOnline() || p1.equals(p2)) {
                        pl.getTradeConfig().getAdminInvalidPlayers().send(sender);
                        return true;
                    }
                    Trade trade = pl.getTrade(p1, p2);
                    if (trade == null) {
                        pl.getTradeConfig().getAdminNoTrade().send(player);
                    } else {
                        player.openInventory(trade.getSpectatorInv());
                    }
                    return true;
                }
                break;
        }
        MsgUtils.send(sender, new String[]{"&6&l<----- Trade+ by Trophonix ----->", "&e/trade <player> &fSend a trade request", "&e/tradeplus reload &fReload config files", "&e/tradeplus force <player1> <player2> &fForce 2 players to trade"});
        if (pl.getTradeConfig().isSpectateEnabled())
            MsgUtils.send(sender, "&e/tradeplus spectate <player(s)> &fSpectate an ongoing trade");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        StringBuilder builder = new StringBuilder(alias.toLowerCase());
        for (String arg : args) {
            builder.append(" ").append(arg);
        }
        String full = builder.toString();
        if (args.length == 0) {
            return arg0;
        } else if (args.length == 1 && !full.endsWith(" ")) {
            return arg0.stream().filter(name -> !name.equalsIgnoreCase(args[0]) && name.startsWith(args[0].toLowerCase())).collect(Collectors.toList());
        } else if (args.length > 1 && !full.endsWith(" ") && (args[0].equalsIgnoreCase("force") || args[0].equalsIgnoreCase("spectate"))) {
            return Bukkit.getOnlinePlayers().stream().filter(p -> !PlayerUtil.isVanished(p)).map(Player::getName).filter(name -> !name.equalsIgnoreCase(args[args.length - 1]) && name.toLowerCase().startsWith(args[args.length - 1])).collect(Collectors.toList());
        }
        return null;
    }
}
