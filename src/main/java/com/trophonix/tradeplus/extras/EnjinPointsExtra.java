package com.trophonix.tradeplus.extras;

import com.enjin.core.EnjinServices;
import com.enjin.rpc.mappings.mappings.general.RPCData;
import com.enjin.rpc.mappings.services.PointService;
import com.trophonix.tradeplus.TradePlus;
import com.trophonix.tradeplus.trade.Trade;
import com.trophonix.tradeplus.util.ItemFactory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnjinPointsExtra extends Extra {

    private final TradePlus pl;

    public EnjinPointsExtra(Player player1, Player player2, TradePlus pl, Trade trade) {
        super("enjinpoints", player1, player2, pl, trade);
        this.pl = pl;
    }

    @Override
    public double getMax(Player player) {
        try {
            return EnjinServices.getService(PointService.class).get(player.getName()).getResult();
        } catch (Exception ex) {
            pl.getLogger().warning("Failed to get enjinpoints for player: " + player.getName());
            return 0;
        }
    }

    @Override
    public void onTradeEnd() {
        if (value1 > 0) {
            transact(player1, player2, value1);
        }
        if (value2 > 0) {
            transact(player2, player1, value2);
        }
    }

    @Override
    public ItemStack _getIcon(Player player) {
        return ItemFactory.replaceInMeta(
                icon,
                "%AMOUNT%",
                decimalFormat.format(player.equals(player1) ? value1 : value2),
                "%CURRENCY%",
                "Enjin points",
                "%INCREMENT%",
                decimalFormat.format(increment),
                "%PLAYERINCREMENT%",
                decimalFormat.format(player.equals(player1) ? increment1 : increment2));
    }

    @Override
    public ItemStack _getTheirIcon(Player player) {
        return ItemFactory.replaceInMeta(
                theirIcon,
                "%AMOUNT%",
                decimalFormat.format(player.equals(player1) ? value1 : value2),
                "%CURRENCY%",
                "Enjin points");
    }

    private void transact(Player take, Player give, double points) {
        PointService pointService = EnjinServices.getService(PointService.class);
        RPCData<Integer> withdrawResponse = pointService.remove(take.getName(), (int) points);
        if (withdrawResponse == null) {
            pl.getLogger()
                    .warning("Failed to withdraw " + points + " points from " + take.getName() + ":");
            pl.getLogger().warning("Couldn't connect to enjin points api");
        } else if (withdrawResponse.getError() != null) {
            pl.getLogger()
                    .warning("Failed to withdraw " + points + " points from " + take.getName() + ":");
            pl.getLogger().warning(withdrawResponse.getError().getMessage());
        } else {
            RPCData<Integer> depositResponse = pointService.add(give.getName(), (int) points);
            if (depositResponse == null) {
                pl.getLogger().warning("Failed to give " + points + " points to " + give.getName() + ":");
                pl.getLogger().warning("Couldn't connect to enjin points api");
            } else if (depositResponse.getError() != null) {
                pl.getLogger().warning("Failed to give " + points + " points to " + give.getName() + ":");
                pl.getLogger().warning(depositResponse.getError().getMessage());
            }
        }
    }
}
