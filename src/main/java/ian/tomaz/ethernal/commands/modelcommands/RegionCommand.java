package ian.tomaz.ethernal.commands.modelcommands;

import ian.tomaz.ethernal.models.PlayerModel;
import ian.tomaz.ethernal.references.CommandBase;
import ian.tomaz.ethernal.utils.EthernalMaps;
import ian.tomaz.ethernal.utils.InventoryBuilder;
import ian.tomaz.ethernal.utils.ItemBuilder;
import ian.tomaz.ethernal.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDate;
import java.util.UUID;

public class RegionCommand extends CommandBase {

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (EthernalMaps.playerHash.containsKey(player.getUniqueId().toString())) {
                open(player);
            } else {
                player.sendMessage(Utils.tag + "Você ainda não está registrado!");
            }
        }
    }

    static int count = 0;

    public static void open(Player player) {
        InventoryBuilder builder = new InventoryBuilder(5, "Territórios");

        EthernalMaps.regions.forEach(model -> {
            if (model.isOccupied()) {
                if (model.getOwner_uuid().contains(player.getUniqueId().toString())) {
                    ItemBuilder itemModelBuilder = new ItemBuilder(String.valueOf(count), Material.GOLD_BLOCK);
                    itemModelBuilder.addLore("Nome: " + model.getName());
                    itemModelBuilder.addLore("Taxa à ser paga: " + model.getTax());
                    itemModelBuilder.addLore("Preço base: " + model.getPrice());
                    itemModelBuilder.addLore("Proxíma data de pagamento: " + model.getPayment());

                    LocalDate localDate = LocalDate.now();
                    String unFormattedDate = localDate.toString();
                    String[] parts = unFormattedDate.split("-");

                    int actualYear = Integer.parseInt(parts[0]);
                    int actualMonth = Integer.parseInt(parts[1]);
                    int actualDay = Integer.parseInt(parts[2]);

                    String dataFormatted = actualDay + "/" + actualMonth + "/" + actualYear;

                    if (dataFormatted.equalsIgnoreCase(model.getPayment())) {
                        itemModelBuilder.addLore("Clique com o botão esquerdo para pagar sua taxa!");
                    } else {
                        itemModelBuilder.addLore("Você ainda não precisa pagar taxa!");
                    }

                    builder.getInventory().setItem(count, itemModelBuilder.getItem());
                    count += 1;
                }
            }
        });

        count = 0;
        player.openInventory(builder.getInventory());
    }

}
