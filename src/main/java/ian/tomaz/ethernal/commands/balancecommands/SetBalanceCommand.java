package ian.tomaz.ethernal.commands.balancecommands;

import ian.tomaz.ethernal.database.DatabaseManager;
import ian.tomaz.ethernal.models.PlayerModel;
import ian.tomaz.ethernal.references.CommandBase;
import ian.tomaz.ethernal.utils.EthernalMaps;
import ian.tomaz.ethernal.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetBalanceCommand extends CommandBase {

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("ethernal.admin")) {
                Player target = Bukkit.getPlayer(args[2]);
                double val = Double.parseDouble(args[3]);

                PlayerModel model = EthernalMaps.playerHash.get(target.getUniqueId().toString());

                model.setBalance(val);
                DatabaseManager.savePlayerData(model);
                player.sendMessage(Utils.tag + "A quantidade de Auros " + val + " foi setada para " + target.getName());
            } else {
                player.sendMessage(Utils.tag + "Você não tem permissão para executar esse comando!");
            }

        }
    }

}
