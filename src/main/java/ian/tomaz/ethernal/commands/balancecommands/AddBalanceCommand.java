package ian.tomaz.ethernal.commands.balancecommands;

import ian.tomaz.ethernal.models.PlayerModel;
import ian.tomaz.ethernal.references.CommandBase;
import ian.tomaz.ethernal.utils.EthernalMaps;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddBalanceCommand extends CommandBase {

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("ethernal.admin")) {
                Player target = Bukkit.getPlayer(args[2]);
                double val = Double.parseDouble(args[3]);

                PlayerModel model = EthernalMaps.playerHash.get(target.getUniqueId().toString());

                model.setBalance(model.getBalance() + val);
                player.sendMessage("A quantidade de Auros " + val + " foi adicionada para " + target.getName());
            } else {
                player.sendMessage("Você não tem permissão para executar esse comando!");
            }

        }
    }

}
