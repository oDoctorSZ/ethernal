package ian.tomaz.ethernal;

import ian.tomaz.ethernal.commands.CommandRegister;
import ian.tomaz.ethernal.database.DatabaseManager;
import ian.tomaz.ethernal.events.PChatEvent;
import ian.tomaz.ethernal.events.betonquest.MissionsInterface;
import ian.tomaz.ethernal.models.PlayerModel;
import ian.tomaz.ethernal.utils.EthernalMaps;
import ian.tomaz.ethernal.utils.Utils;
import org.betonquest.betonquest.commands.QuestCommand;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class Ethernal extends JavaPlugin {

    public static List<String> registeredInventorys = new ArrayList<>();

    @Override
    public void onEnable() {
        getCommand("core").setExecutor(new CommandRegister());
        Bukkit.getPluginManager().registerEvents(new MissionsInterface(), this);
        Bukkit.getPluginManager().registerEvents(new PChatEvent(), this);

        try {
            Utils.initialize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            PlayerModel playerModel = EthernalMaps.playerHash.get(player.getUniqueId().toString());
            DatabaseManager.savePlayerData(playerModel);
        });
    }

}
