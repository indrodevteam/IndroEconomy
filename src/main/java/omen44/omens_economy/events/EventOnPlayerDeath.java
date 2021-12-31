package omen44.omens_economy.events;

import omen44.omens_economy.datamanager.ConfigTools;
import omen44.omens_economy.utils.EconomyUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static omen44.omens_economy.utils.ShortcutsUtils.mPrefix;

public class EventOnPlayerDeath implements Listener {
    EconomyUtils eco = new EconomyUtils();
    ConfigTools configTools = new ConfigTools();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        FileConfiguration config = configTools.getConfig("config.yml");

        //initialise the values needed
        Player player = event.getEntity();
        double moneyLossPercent = config.getInt("money.deathLossPercent") / 100.0;
        int wallet = eco.getMoney(player, "wallet");
        String symbol = config.getString("money.moneySymbol");
        double moneyLost = wallet * moneyLossPercent;
        int finalWallet = wallet - (int) moneyLost;

        //reduce their wallet by the percentage
        player.sendMessage(mPrefix + "You have died!\n" + mPrefix + "You have lost " + symbol + moneyLost);
        eco.setWallet(player, finalWallet);
    }
}
