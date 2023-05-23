package ian.tomaz.ethernal.commands;

import ian.tomaz.ethernal.references.CommandBase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CordCommand extends CommandBase {

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage("Suas Cordenadas: " + "X: " + player.getLocation().getX() + " " + "Y: " + player.getLocation().getY() + " " + "Z: " + player.getLocation().getZ());


        }
    }

}
