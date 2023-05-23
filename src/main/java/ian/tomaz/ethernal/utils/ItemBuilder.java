package ian.tomaz.ethernal.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {
    private final short damage = 0;

    private ItemStack item;
    private ItemMeta meta;
    private String itemName;
    private String targetInv;
    private List<String> lore = new ArrayList<>();
    private String command;

    public ItemBuilder(String displayName, Material material) {
        this.targetInv = "";
        item = new ItemStack(material, 1);
        meta = item.getItemMeta();
        this.itemName = displayName;
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
    }

    public ItemBuilder(String displayName, ItemStack itemStack) {
        this.targetInv = "";
        item = itemStack;
        meta = item.getItemMeta();
        this.itemName = displayName;
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
    }

    public ItemBuilder(Material material) {
        item = new ItemStack(material);
    }

    public ItemBuilder(String displayName, Material material, String targetInv) {
        item = new ItemStack(material, 1);
        meta = item.getItemMeta();
        this.itemName = displayName;
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        this.targetInv = targetInv;
    }

    public ItemBuilder(ItemStack item, String targetInv, String displayName){
        this.item = item;
        meta = this.item.getItemMeta();
        this.itemName = displayName;
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        this.targetInv = targetInv;
    }

    public void setLore(String... lore) {
        List<String> loreList = new ArrayList<>();
        for (int i = 0; i < lore.length; i++) {
            loreList.add(lore[i].replace("&", "§"));
        }
        this.lore = loreList;
        meta.setLore(loreList);
        meta.setDisplayName(itemName);
        item.setItemMeta(meta);
    }

    public void addLore(String loreItem) {
        String newLore = loreItem.replace("&", "§");

        lore.add(newLore);
        meta.setLore(this.lore);
        meta.setDisplayName(itemName);
        item.setItemMeta(meta);
    }



    public void setLore(List<String> lore) {
        List<String> newLore = new ArrayList<>();
        lore.forEach(item -> {
            newLore.add(item.replace("&","§"));
        });
        this.lore = newLore;
        meta.setLore(newLore);
        meta.setDisplayName(itemName);
        item.setItemMeta(meta);
    }

    public ItemStack getItem() {
        return item;
    }

    public String getTarget() {
        return targetInv;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommmand(){
        return command;
    }

    public String getName() {
        return itemName;
    }

}
