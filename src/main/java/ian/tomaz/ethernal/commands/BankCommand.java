package ian.tomaz.ethernal.commands;

import ian.tomaz.ethernal.models.PlayerModel;
import ian.tomaz.ethernal.references.CommandBase;
import ian.tomaz.ethernal.utils.EthernalMaps;
import ian.tomaz.ethernal.utils.InventoryBuilder;
import ian.tomaz.ethernal.utils.ItemBuilder;
import ian.tomaz.ethernal.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.slf4j.helpers.Util;

public class BankCommand extends CommandBase {

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (EthernalMaps.playerHash.containsKey(player.getUniqueId().toString())) {
                if (player.hasPermission("ethernal.admin")) {
                    open(player);
                }
            } else {
                player.sendMessage(Utils.tag + "Você ainda não está registrado!");
            }
        }
    }

    public void open(Player player) {
        InventoryBuilder builder = new InventoryBuilder(6, "Banco");
        PlayerModel model = EthernalMaps.playerHash.get(player.getUniqueId().toString());

        ItemBuilder itemModelBuilder = new ItemBuilder(Utils.color("&b"+model.getName()), Utils.getPlayerHead(player.getName()));
        itemModelBuilder.addLore("Raça: " + model.getRace());
        itemModelBuilder.addLore("Dinheiro: " + model.getBalance());
        itemModelBuilder.addLore("Profissão Atual: " + model.getProfession());
        builder.getInventory().setItem(13, itemModelBuilder.getItem());

        ItemBuilder bankTransfer = new ItemBuilder("Transferência Bancária", Utils.getPlayerHead(player.getName()));
        bankTransfer.addLore(Utils.color("Use essa área para transferir dinheiro para outro jogador"));
        builder.getInventory().setItem(29, bankTransfer.getItem());

        ItemBuilder bankDeposit = new ItemBuilder("Deposito Bancário", Utils.getPlayerHead(player.getName()));
        bankTransfer.addLore(Utils.color("Use essa área para depositar seu dinheiro no banco"));
        builder.getInventory().setItem(31, bankDeposit.getItem());

        ItemBuilder bankOut = new ItemBuilder("Retire seu dinheiro", Utils.getPlayerHead(player.getName()));
        bankOut.addLore(Utils.color("Use essa área para retirar seu dinheiro"));
        builder.getInventory().setItem(33, bankOut.getItem());

        ItemBuilder transactions = new ItemBuilder("Transações", Utils.getPlayerHead(player.getName()));
        transactions.addLore(Utils.color("&aUse essa área para ver suas transações passadas"));
        builder.getInventory().setItem(52, transactions.getItem());

        ItemBuilder regions = new ItemBuilder("Territórios", Utils.getPlayerHead(player.getName()));
        regions.addLore(Utils.color("&aUse essa área para seus territórios"));
        builder.getInventory().setItem(53, regions.getItem());

        player.openInventory(builder.getInventory());
    }


}
