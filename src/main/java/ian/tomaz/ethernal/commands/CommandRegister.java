package ian.tomaz.ethernal.commands;

import ian.tomaz.ethernal.commands.balancecommands.AddBalanceCommand;
import ian.tomaz.ethernal.commands.balancecommands.SetBalanceCommand;
import ian.tomaz.ethernal.commands.balancecommands.TransactionsCommand;
import ian.tomaz.ethernal.commands.balancecommands.ViewBalanceCommand;
import ian.tomaz.ethernal.commands.modelcommands.PModelRegister;
import ian.tomaz.ethernal.commands.modelcommands.ProfileCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandRegister implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("quest")) {
                    new QuestCommand().execute(commandSender, command, args);
                } else if (args[0].equalsIgnoreCase("cords")) {
                    new CordCommand().execute(commandSender, command, args);
                } else if (args[0].equalsIgnoreCase("balance")) {
                    if (args[1].equalsIgnoreCase("set")) {
                        new SetBalanceCommand().execute(commandSender, command, args);
                    } else if (args[1].equalsIgnoreCase("add")) {
                        new AddBalanceCommand().execute(commandSender, command, args);
                    } else {
                        new ViewBalanceCommand().execute(commandSender, command, args);
                    }
                } else if (args[0].equalsIgnoreCase("profile")) {
                    new ProfileCommand().execute(commandSender, command, args);
                } else if (args[0].equalsIgnoreCase("register")) {
                    new PModelRegister().execute(commandSender, command, args);
                } else if (args[0].equalsIgnoreCase("banco")) {
                    if (player.hasPermission("ethernal.admin")) {
                        if (args.length == 1) {
                            new BankCommand().execute(commandSender, command, args);
                        } else {
                            if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))) {
                                Player target = Bukkit.getPlayer(args[1]);
                                assert target != null;
                                new BankCommand().open(target);
                            }
                        }

                    }
                } else if (args[0].equalsIgnoreCase("transaction")) {
                    new TransactionsCommand().execute(commandSender, command, args);
                }
            }

        }
        return false;
    }
}
