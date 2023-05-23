package ian.tomaz.ethernal.commands.balancecommands;

import ian.tomaz.ethernal.models.PlayerModel;
import ian.tomaz.ethernal.models.TransactionsModel;
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
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class TransactionsCommand extends CommandBase {

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            open(player);
        }
    }

    static int count = 0;

    public static void open(Player player) {
        InventoryBuilder builder = new InventoryBuilder(5, "Histórico de Transações");

        EthernalMaps.transactionsList.forEach(model -> {
            if (model.getUuid().equalsIgnoreCase(player.getUniqueId().toString())) {
                if (count < 45) {
                    String target = "";
                    ItemStack head = null;

                    if (model.getUuidTarget() != null) {
                        if (!model.getUuidTarget().equalsIgnoreCase("Saque")) {
                            if (!model.getUuidTarget().equalsIgnoreCase("Taxa")) {
                                if (!model.getUuidTarget().equalsIgnoreCase("Deposito")) {
                                    if (!model.getUuidTarget().equalsIgnoreCase("")) {
                                        target = Bukkit.getPlayer(UUID.fromString(model.getUuidTarget())).getName();
                                        head = Utils.getPlayerHead(Bukkit.getPlayer(UUID.fromString(model.getUuidTarget())).getName());
                                    } else {

                                        if (model.getUuidTarget().equalsIgnoreCase("taxa")) {
                                            target = "Taxa";
                                        } else if (model.getUuidTarget().equalsIgnoreCase("saque")) {
                                            target = "Saque";
                                        } else if (model.getUuidTarget().equalsIgnoreCase("deposito")) {
                                            target = "Deposito";
                                        }

                                        head = Utils.getPlayerHead(player.getName());
                                    }
                                } else {

                                    if (model.getUuidTarget().equalsIgnoreCase("taxa")) {
                                        target = "Taxa";
                                    } else if (model.getUuidTarget().equalsIgnoreCase("saque")) {
                                        target = "Saque";
                                    } else if (model.getUuidTarget().equalsIgnoreCase("deposito")) {
                                        target = "Deposito";
                                    }

                                    head = Utils.getPlayerHead(player.getName());
                                }
                            } else {

                                if (model.getUuidTarget().equalsIgnoreCase("taxa")) {
                                    target = "Taxa";
                                } else if (model.getUuidTarget().equalsIgnoreCase("saque")) {
                                    target = "Saque";
                                } else if (model.getUuidTarget().equalsIgnoreCase("deposito")) {
                                    target = "Deposito";
                                }

                                head = Utils.getPlayerHead(player.getName());
                            }
                        } else {

                            if (model.getUuidTarget().equalsIgnoreCase("taxa")) {
                                target = "Taxa";
                            } else if (model.getUuidTarget().equalsIgnoreCase("saque")) {
                                target = "Saque";
                            } else if (model.getUuidTarget().equalsIgnoreCase("deposito")) {
                                target = "Deposito";
                            }

                            head = Utils.getPlayerHead(player.getName());
                        }
                    } else {
                        target = "";
                        head = Utils.getPlayerHead(player.getName());
                    }

                    ItemBuilder itemModelBuilder = new ItemBuilder(String.valueOf(count), head);
                    itemModelBuilder.addLore("De: " + Bukkit.getPlayer(UUID.fromString(model.getUuid())).getDisplayName());
                    itemModelBuilder.addLore("Para: " + target);
                    itemModelBuilder.addLore("Valor: " + model.getValue());
                    builder.getInventory().setItem(count, itemModelBuilder.getItem());
                    count += 1;
                }
            }
        });

        count = 0;
        player.openInventory(builder.getInventory());
    }



}
