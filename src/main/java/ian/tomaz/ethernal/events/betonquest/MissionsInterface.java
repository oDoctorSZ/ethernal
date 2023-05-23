package ian.tomaz.ethernal.events.betonquest;

import ian.tomaz.ethernal.Ethernal;
import ian.tomaz.ethernal.commands.balancecommands.PayTaxCommand;
import ian.tomaz.ethernal.commands.balancecommands.TransactionsCommand;
import ian.tomaz.ethernal.database.DatabaseManager;
import ian.tomaz.ethernal.models.PlayerModel;
import ian.tomaz.ethernal.models.RegionModel;
import ian.tomaz.ethernal.models.TransactionsModel;
import ian.tomaz.ethernal.utils.EthernalMaps;
import ian.tomaz.ethernal.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDate;
import java.util.Objects;

public class MissionsInterface implements Listener {

    @SuppressWarnings("deprecated")
    @EventHandler
    public void onClickEvent(InventoryClickEvent e) {
        Ethernal.registeredInventorys.forEach(name -> {
            if (e.getView().getTitle().equalsIgnoreCase(name)) {
                e.setCancelled(true);

                if (name.equalsIgnoreCase("Banco")) {
                    ItemStack itemStack = e.getCurrentItem();
                    Player player = (Player) e.getWhoClicked();

                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("Transferência Bancária")) {
                        player.sendMessage(Utils.tag + "Para qual jogador deseja transferir? Use ('nickname quantidade')");
                        player.sendMessage(Utils.cancel);
                        EthernalMaps.currentInTransfer.add(player);
                        player.closeInventory();
                    }

                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("Deposito Bancário")) {
                        player.sendMessage(Utils.tag + "Quanto deseja depositar? Use ('quantidade')");
                        player.sendMessage(Utils.cancel);
                        EthernalMaps.currentInDeposit.add(player.getName());
                        player.closeInventory();
                    }

                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("Retire seu dinheiro")) {
                        player.sendMessage(Utils.tag + "Quanto deseja retirar? Use ('quantidade')");
                        player.sendMessage(Utils.cancel);
                        EthernalMaps.currentInCashout.add(player.getName());
                        player.closeInventory();
                    }

                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("Transações")) {
                        TransactionsCommand.open(player);
                    }

                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("Territórios")) {
                        TransactionsCommand.open(player);
                    }

                }

                if (name.equalsIgnoreCase("Territórios")) {
                    ItemStack itemStack = e.getCurrentItem();
                    Player player = (Player) e.getWhoClicked();

                    if (itemStack.getItemMeta().lore().contains("Clique com o botão esquerdo para pagar sua taxa!")) {
                        if (e.isLeftClick()) {
                            String[] regionName = itemStack.getItemMeta().getLore().get(0).split("Nome: ");

                            for (RegionModel model : EthernalMaps.regions) {
                                if (model.getName().equalsIgnoreCase(regionName[0])) {
                                    PayTaxCommand.open(model, player);
                                }
                            }


                        }
                    }
                }

                if (name.equalsIgnoreCase("Pagar Taxa de Território")) {
                    ItemStack itemStack = e.getCurrentItem();
                    Player player = (Player) e.getWhoClicked();

                    PlayerModel pModel = EthernalMaps.playerHash.get(player.getUniqueId().toString());

                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("Pagar Taxa")) {
                        if (e.isLeftClick()) {
                            String[] regionName = itemStack.getItemMeta().getLore().get(0).split("Nome: ");

                            for (RegionModel model : EthernalMaps.regions) {
                                if (model.getName().equalsIgnoreCase(regionName[0])) {
                                    if (pModel.getBalance() >= model.getTax()) {
                                        pModel.setBalance(pModel.getBalance() - model.getTax());

                                        LocalDate localDate = LocalDate.now().plusDays(3);
                                        String unFormattedDate = localDate.toString();
                                        String[] parts = unFormattedDate.split("-");

                                        int actualYear = Integer.parseInt(parts[0]);
                                        int actualMonth = Integer.parseInt(parts[1]);
                                        int actualDay = Integer.parseInt(parts[2]);

                                        String dataFormatted = actualDay + "/" + actualMonth + "/" + actualYear;
                                        model.setPayment(dataFormatted);

                                        DatabaseManager.savePlayerData(pModel);
                                        DatabaseManager.saveRegionData(model);

                                        DatabaseManager.saveTransactions(new TransactionsModel(pModel.getUuid(), "Taxa", model.getTax()));
                                        EthernalMaps.transactionsList.add(new TransactionsModel(pModel.getUuid(), "Taxa", model.getTax()));

                                        player.closeInventory();
                                        player.sendMessage(Utils.tag + "Taxa paga com sucesso!");
                                    } else {
                                        player.closeInventory();
                                        player.sendMessage(Utils.tag + "Você não tem dinheiro suficiente!");
                                    }
                                }
                            }


                        }
                    }
                }

            }
        });
    }


}
