package ian.tomaz.ethernal.utils;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import ian.tomaz.ethernal.database.DatabaseConnection;
import ian.tomaz.ethernal.database.DatabaseManager;
import ian.tomaz.ethernal.models.RegionModel;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class Utils {

    public static void initialize() throws SQLException {
        initializeMySQL();
        DatabaseManager.loadPlayersOnHash();
        DatabaseManager.loadTransactionsList();
        //DatabaseManager.loadRegionsOnList();
        //loadRegions();
    }

    private static void initializeMySQL() {
        DatabaseConnection.host = "database-1.cj9om5jp9bxo.us-east-2.rds.amazonaws.com";
        DatabaseConnection.database = "ethernal";
        DatabaseConnection.port = "3306";
        DatabaseConnection.username = "admin";
        DatabaseConnection.password = "Blackandwhite12*";

        new DatabaseManager();
    }

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String tag = color("&6[&3Ethernal&6]&3 ");
    public static String defaultText(String text) {
        return color("&3" + text);
    }
    public static String cancel = tag + color("&4Escreva 'cancelar' para cancelar essa operação!");


    private static void loadRegions() throws SQLException {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(WorldGuard.getInstance().getPlatform().getMatcher().getWorldByName("world"));

        assert regions != null;
        regions.getRegions().forEach((name, protectedRegion) -> {
            if (name.contains("barraca")) {
                double price = 50;
                double percentual = 0.10;
                double tax = price * percentual;

                DecimalFormat df = new DecimalFormat("#.00");
                String result = df.format(tax);
                double resultToDouble = Double.parseDouble(result);

                LocalDate localDate = LocalDate.now();
                LocalDate paymenteDate = localDate.plusDays(3);
                String unFormattedDate = paymenteDate.toString();
                String[] parts = unFormattedDate.split("-");

                int actualYear = Integer.parseInt(parts[0]);
                int actualMonth = Integer.parseInt(parts[1]);
                int actualDay = Integer.parseInt(parts[2]);

                String dataFormatted = actualDay + "/" + actualMonth + "/" + actualYear;
                boolean debounce = false;

                if (protectedRegion.hasMembersOrOwners()) {
                    RegionModel regionModel = new RegionModel(name, resultToDouble, price, true, dataFormatted, protectedRegion.getOwners().toString(), protectedRegion.getMembers().toString());

                    for (RegionModel model : EthernalMaps.regions) {
                        if (model.getName().equalsIgnoreCase(regionModel.getName())) {
                            debounce = true;
                        }
                    }

                    if (!debounce) {
                        DatabaseManager.saveRegionData(regionModel);
                        EthernalMaps.regions.add(regionModel);
                    }

                }

            }
        });
    }

    @SuppressWarnings("decreption")
    public static ItemStack getPlayerHead(String player) {

        ItemStack item = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();

        skullMeta.setOwner(player);
        skullMeta.setDisplayName(player);

        item.setItemMeta(skullMeta);

        return item;
    }


}
