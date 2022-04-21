package me.theoldduck.guis;

import me.theoldduck.SimpleBlocks;
import me.theoldduck.managers.FileCreator;
import me.theoldduck.managers.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class EditMenu implements Listener {
    SimpleBlocks plugin = SimpleBlocks.getInstance();
    FileCreator blocks = plugin.getBlocks();
    Settings settings = new Settings();

    public void createInventory(Player player, String name) {

        // Create a inventory.
        Inventory inv = Bukkit.createInventory(null, 54, "EDIT " + name);
        player.openInventory(inv);

        ArrayList<String> items = (ArrayList<String>) blocks.getStringList(name + ".sets");

        // Iterate the items and add them to the inventory.
        for (int i = 0; i < items.size(); i++) {
            if (Material.getMaterial(items.get(i)) != null) {
                ItemStack item = new ItemStack(Material.valueOf(items.get(i)));
                inv.setItem(i, item);
            }
        }

        player.updateInventory();
    }

    // Save the inventory when the player closes it.
    @EventHandler
    public void closeinv(InventoryCloseEvent event) {
        String nameinv = event.getView().getTitle().replace("EDIT ", "");
        Inventory inv = event.getInventory();

        // Iterate the blocks to save name.
        for (String name : blocks.getKeys(false)) {
            if (nameinv.equals(name))
                nameinv = name;
        }

        // If title is the same.
        if (event.getView().getTitle().contains(nameinv) && event.getView().getTitle().contains("EDIT")) {
            blocks.set(nameinv + ".sets", null);
            ArrayList<String> newsItems = new ArrayList<>();

            // Iterate the inventory and save the items.
            for (ItemStack item : inv.getContents()) {
                if (item != null && item.getType() != Material.AIR && item.getType().isBlock()) {
                    newsItems.add(item.getType().toString());
                }
            }

            blocks.set(nameinv + ".sets", newsItems);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> blocks.save());

            event.getPlayer().sendMessage(settings.BloqueEditado);
        }
    }
}
