package ian.tomaz.ethernal.utils;

import ian.tomaz.ethernal.Ethernal;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class InventoryBuilder {
    private Inventory inv;

    public InventoryBuilder(int size, String name) {
        this.inv = Bukkit.createInventory(null, size * 9, name);
        Ethernal.registeredInventorys.add(name);
    }

    public Inventory getInventory() {
        return inv;
    }

}
