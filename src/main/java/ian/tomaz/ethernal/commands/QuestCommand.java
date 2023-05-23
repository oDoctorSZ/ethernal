package ian.tomaz.ethernal.commands;

import ian.tomaz.ethernal.references.CommandBase;
import ian.tomaz.ethernal.utils.InventoryBuilder;
import ian.tomaz.ethernal.utils.ItemBuilder;
import org.betonquest.betonquest.BetonQuest;
import org.betonquest.betonquest.api.Objective;
import org.betonquest.betonquest.api.profiles.Profile;
import org.betonquest.betonquest.utils.PlayerConverter;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import java.util.List;

public class QuestCommand extends CommandBase {
    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if (sender instanceof Player) {
            open(((Player) sender).getPlayer());
        }
    }

    public void open(Player player) {
        InventoryBuilder builder = new InventoryBuilder(5, "Missions");
        Profile profile = PlayerConverter.getID(player);
        List<Objective> objectives = BetonQuest.getInstance().getPlayerObjectives(profile);

        for (int i = 0; i < objectives.size(); i++) {
            Objective objective = objectives.get(i);
            ItemBuilder itemBuilder = new ItemBuilder(objective.getLabel(), Material.BOOK);
            builder.getInventory().setItem(i, itemBuilder.getItem());
        }

        player.openInventory(builder.getInventory());
    }

}
