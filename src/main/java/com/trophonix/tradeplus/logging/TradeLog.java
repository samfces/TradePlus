package com.trophonix.tradeplus.logging;

import com.trophonix.tradeplus.util.ItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TradeLog implements PostProcessor {

    private Trader player1, player2;
    private List<ItemFactory> player1Items, player2Items;
    private List<ExtraOffer> player1ExtraOffers, player2ExtraOffers;
    private Date time;

    public TradeLog() {
    }

    public TradeLog(
            OfflinePlayer player1,
            OfflinePlayer player2,
            List<ItemFactory> player1Items,
            List<ItemFactory> player2Items,
            List<ExtraOffer> player1ExtraOffers,
            List<ExtraOffer> player2ExtraOffers) {
        this.player1 = new Trader(player1.getUniqueId(), player1.getName());
        this.player2 = new Trader(player2.getUniqueId(), player2.getName());
        player1Items.sort((o1, o2) -> Integer.compare(o2.getAmount(), o1.getAmount()));
        this.player1Items = player1Items;
        player2Items.sort((o1, o2) -> Integer.compare(o2.getAmount(), o1.getAmount()));
        this.player2Items = player2Items;
        this.player1ExtraOffers = player1ExtraOffers;
        this.player2ExtraOffers = player2ExtraOffers;
        this.time = new Date();
    }

    public Trader getPlayer1() {
        return player1;
    }

    public Trader getPlayer2() {
        return player2;
    }

    public List<ItemFactory> getPlayer1Items() {
        return player1Items;
    }

    public List<ItemFactory> getPlayer2Items() {
        return player2Items;
    }

    public List<ExtraOffer> getPlayer1ExtraOffers() {
        return player1ExtraOffers;
    }

    public List<ExtraOffer> getPlayer2ExtraOffers() {
        return player2ExtraOffers;
    }

    public Date getTime() {
        return time;
    }

    @Override
    public void doPostProcessing() {
        player1.updateName();
        player2.updateName();
    }

    public static class Trader {

        private UUID uniqueId;
        private String lastKnownName;

        void updateName() {
            OfflinePlayer op = Bukkit.getOfflinePlayer(uniqueId);
            if (op.getName() == null) lastKnownName = "unknown";
            else lastKnownName = op.getName();
        }

        public Trader(UUID uniqueId, String lastKnownName) {
            this.uniqueId = uniqueId;
            this.lastKnownName = lastKnownName;
        }

        public UUID getUniqueId() {
            return uniqueId;
        }

        public String getLastKnownName() {
            return lastKnownName;
        }
    }

    public static class ExtraOffer {

        private String id;
        private double value;

        public ExtraOffer() {
        }

        public ExtraOffer(String id, double value) {
            this.id = id;
            this.value = value;
        }

        public String getId() {
            return id;
        }

        public double getValue() {
            return value;
        }
    }
}
