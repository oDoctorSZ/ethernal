package ian.tomaz.ethernal.commands.modelcommands;

import ian.tomaz.ethernal.references.CommandBase;
import ian.tomaz.ethernal.utils.EthernalMaps;
import ian.tomaz.ethernal.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PModelRegister extends CommandBase {

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            EthernalMaps.typingList.add(player);

            player.sendMessage(Utils.tag + "Qual o nome que você quer registrar?");
            player.sendMessage(Utils.cancel);

        }
    }

}
