package ian.tomaz.ethernal.references;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class CommandBase {
    public void execute(CommandSender commandSender, Command command, String[] args) {}
}
