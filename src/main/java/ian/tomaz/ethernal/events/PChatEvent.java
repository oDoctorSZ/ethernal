package ian.tomaz.ethernal.events;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import ian.tomaz.ethernal.database.DatabaseManager;
import ian.tomaz.ethernal.models.PlayerModel;
import ian.tomaz.ethernal.models.TransactionsModel;
import ian.tomaz.ethernal.utils.EthernalMaps;
import ian.tomaz.ethernal.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PChatEvent implements Listener {

    private PlayerModel playerModel;

    @SuppressWarnings("deprecated")
    @EventHandler
    public void onMsgSend(PlayerChatEvent e) {
        Player player = e.getPlayer();
        String uuid = player.getUniqueId().toString();

        if (!EthernalMaps.playerHash.containsKey(player.getUniqueId().toString())) {
            if (EthernalMaps.typingList.contains(player)) {
                e.setCancelled(true);
                String profession = getPlayerProfession(player);
                List<String> owned = getRegionsOwned(player);

                if (e.getMessage().equalsIgnoreCase("cancelar")) {
                    EthernalMaps.typingList.remove(player);
                }

                playerModel = new PlayerModel(uuid, e.getMessage(), "Humano", 0, profession, owned.toString());
                EthernalMaps.playerHash.put(uuid, playerModel);

                new DatabaseManager().savePlayerData(playerModel);
                EthernalMaps.typingList.remove(player);
            }

            if (EthernalMaps.currentInTransfer.contains(player)) {
                e.setCancelled(true);
                String msg = e.getMessage();
                String[] args = e.getMessage().split(" ");

                if (!(args.length >= 2)) {
                    PlayerModel model = EthernalMaps.playerHash.get(player.getUniqueId().toString());
                    PlayerModel target = EthernalMaps.playerHash.get(Bukkit.getPlayer(args[0]).getUniqueId().toString());
                    double val = Double.parseDouble(args[1]);

                    if (model.getBalance() >= val) {
                        model.setBalance(model.getBalance() - val);
                        target.setBalance(target.getBalance() + val);


                        DatabaseManager.saveTransactions(new TransactionsModel(uuid.toString(), -val));
                        EthernalMaps.transactionsList.add(new TransactionsModel(uuid.toString(), target.getUuid().toString(), -val));
                        player.sendMessage(Utils.tag + "Você fez uma transferência para " + target.getName() + " no valor de " + val);
                        EthernalMaps.currentInTransfer.remove(player);
                    } else {
                        player.sendMessage(Utils.tag + "Você não tem dinheiro suficiente para executa essa transação!");
                        EthernalMaps.currentInTransfer.remove(player);
                    }

                    if (msg.equalsIgnoreCase("cancelar")) {
                        EthernalMaps.currentInTransfer.remove(player.getName());
                        player.sendMessage(Utils.color("&4Operação Cancelada!"));
                    } else if (Bukkit.getPlayer(args[0]).getName().equalsIgnoreCase(player.getName())) {
                        EthernalMaps.currentInTransfer.remove(player);
                        player.sendMessage(Utils.color("&4Você não pode enviar dinheiro para sí mesmo!"));
                    }

                    EthernalMaps.currentInTransfer.remove(player.getName());
                } else {
                    player.sendMessage(Utils.color("&4Erro! Tente novamente."));
                }
            }

            if (EthernalMaps.currentInDeposit.contains(player.getName())) {
                e.setCancelled(true);
                String msg = e.getMessage();
                PlayerModel model = EthernalMaps.playerHash.get(player.getUniqueId().toString());
                int val = Integer.parseInt(msg);

                for (ItemStack item : player.getInventory().getContents()) {
                    if (item.getType() == Material.PAPER) {
                        if (item.hasItemMeta()) {
                            if (item.getItemMeta().getCustomModelData() == 22) {
                                if (item.getAmount() >= val) {
                                    item.setAmount(item.getAmount() - val);
                                    model.setBalance(model.getBalance() + val);
                                    player.sendMessage(Utils.tag + "Foi depositado " + val + " Auros em sua conta!");

                                    DatabaseManager.saveTransactions(new TransactionsModel(uuid.toString(),"Deposito", val));
                                    EthernalMaps.transactionsList.add(new TransactionsModel(uuid.toString(),"Deposito", val));
                                    EthernalMaps.currentInDeposit.remove(player.getName());
                                } else {
                                    player.sendMessage(Utils.color("Não é possível depositar um valor que você não tem!"));
                                }
                            }
                        }
                    }
                }

                EthernalMaps.currentInDeposit.remove(player.getName());

                if (msg.equalsIgnoreCase("cancelar")) {
                    EthernalMaps.currentInDeposit.remove(player.getName());
                    player.sendMessage(Utils.color("&4Operação Cancelada!"));
                }
            }

            if (EthernalMaps.currentInCashout.contains(player.getName())) {
                e.setCancelled(true);
                String msg = e.getMessage();
                PlayerModel model = EthernalMaps.playerHash.get(player.getUniqueId().toString());
                int val = Integer.parseInt(msg);

                if (val <= model.getBalance()) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mi give MATERIAL MOEDA_AUREUS " + player.getName());
                    player.sendMessage(Utils.tag + "Você sacou " + val + " moedas!");

                    DatabaseManager.saveTransactions(new TransactionsModel(uuid.toString(),"Saque", -val));
                    EthernalMaps.transactionsList.add(new TransactionsModel(uuid.toString(),"Saque", -val));
                } else {
                    player.sendMessage(Utils.color("&4Você não tem dinheiro suficiente para executar esse saque!"));
                }

                EthernalMaps.currentInCashout.remove(player.getName());

                if (msg.equalsIgnoreCase("cancelar")) {
                    EthernalMaps.currentInCashout.remove(player.getName());
                    player.sendMessage(Utils.color("&4Operação Cancelada!"));
                }
            }



        }


    }

    public String getPlayerProfession(Player player) {
        Iterator<PermissionAttachmentInfo> iterator = player.getEffectivePermissions().iterator();
        String prof = "";

        while (iterator.hasNext()) {
            String permission = iterator.next().getPermission();
            if (permission.contains("ferreiro")) {
                prof = "Ferreiro";
                return prof;
            } else if (permission.contains("ocultista")) {
                prof = "Ocultista";
                return prof;
            } else if (permission.contains("lenhador")) {
                prof = "Lenhador(a)";
                return prof;
            } else if (permission.contains("minerador")) {
                prof = "Minerador(a)";
                return prof;
            } else if (permission.contains("agricultor")) {
                prof = "Agricultor(a)";
                return prof;
            } else if (permission.contains("cozinheiro")) {
                prof = "Cozinheiro(a)";
                return prof;
            } else if (permission.contains("pescador")) {
                prof = "Pescador(a)";
                return prof;
            }
        }

        return prof;
    }
    public List<String> getRegionsOwned(Player player) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(WorldGuard.getInstance().getPlatform().getMatcher().getWorldByName("world"));
        List<String> pRegions = new ArrayList<>();

        regions.getRegions().forEach((name, protectedRegion) -> {
            if (protectedRegion.getOwners().contains(player.getUniqueId())) {
                pRegions.add(name);
            }
        });

        return pRegions;

    }


}
