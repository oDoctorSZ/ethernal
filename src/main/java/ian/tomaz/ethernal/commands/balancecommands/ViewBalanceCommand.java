package ian.tomaz.ethernal.commands.balancecommands;

import ian.tomaz.ethernal.models.PlayerModel;
import ian.tomaz.ethernal.references.CommandBase;
import ian.tomaz.ethernal.utils.EthernalMaps;
import ian.tomaz.ethernal.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ViewBalanceCommand extends CommandBase {

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerModel model = EthernalMaps.playerHash.get(player.getUniqueId().toString());
            player.sendMessage(Utils.tag + "Dinheiro Atual: " + model.getBalance());
        }
    }

}
