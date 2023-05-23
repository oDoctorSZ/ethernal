package ian.tomaz.ethernal.commands.balancecommands;

import ian.tomaz.ethernal.models.PlayerModel;
import ian.tomaz.ethernal.models.RegionModel;
import ian.tomaz.ethernal.utils.EthernalMaps;
import ian.tomaz.ethernal.utils.InventoryBuilder;
import ian.tomaz.ethernal.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.time.LocalDate;

public class PayTaxCommand {

    public static void open(RegionModel model, Player player) {
        InventoryBuilder builder = new InventoryBuilder(3, "Pagar Taxa de Território");

        ItemBuilder payBuilder = new ItemBuilder("Pagar Taxa", Material.LIME_TERRACOTTA);
        payBuilder.addLore("Nome: " + model.getName());
        payBuilder.addLore("Taxa à ser paga: " + model.getTax());

        ItemBuilder cancelBuilder = new ItemBuilder("Cancelar", Material.RED_TERRACOTTA);

        builder.getInventory().setItem(12, payBuilder.getItem());
        builder.getInventory().setItem(14, cancelBuilder.getItem());

        player.openInventory(builder.getInventory());
    }

}
