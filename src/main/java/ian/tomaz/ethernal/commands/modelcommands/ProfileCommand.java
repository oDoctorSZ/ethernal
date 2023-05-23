package ian.tomaz.ethernal.commands.modelcommands;

import ian.tomaz.ethernal.models.PlayerModel;
import ian.tomaz.ethernal.references.CommandBase;
import ian.tomaz.ethernal.utils.EthernalMaps;
import ian.tomaz.ethernal.utils.InventoryBuilder;
import ian.tomaz.ethernal.utils.ItemBuilder;
import ian.tomaz.ethernal.utils.Utils;
import org.betonquest.betonquest.BetonQuest;
import org.betonquest.betonquest.api.Objective;
import org.betonquest.betonquest.api.profiles.Profile;
import org.betonquest.betonquest.utils.PlayerConverter;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.slf4j.helpers.Util;

import java.util.List;

public class ProfileCommand extends CommandBase {

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

    public void open(Player player) {
        InventoryBuilder builder = new InventoryBuilder(5, "Perfil");
        PlayerModel model = EthernalMaps.playerHash.get(player.getUniqueId().toString());


        ItemBuilder itemModelBuilder = new ItemBuilder(model.getName(), Utils.getPlayerHead(player.getName()));
        itemModelBuilder.addLore("Raça: " + model.getRace());
        itemModelBuilder.addLore("Dinheiro: " + model.getBalance());
        itemModelBuilder.addLore("Profissão Atual: " + model.getProfession());
        builder.getInventory().setItem(13, itemModelBuilder.getItem());

        ItemBuilder itemRegionBuilder = new ItemBuilder(model.getName(), Material.GOLD_BLOCK);
        itemRegionBuilder.addLore("Raça: " + model.getRace());
        itemRegionBuilder.addLore("Dinheiro: " + model.getBalance());
        itemRegionBuilder.addLore("Profissão Atual: " + model.getProfession());
        builder.getInventory().setItem(31, itemRegionBuilder.getItem());



        player.openInventory(builder.getInventory());
    }


}
