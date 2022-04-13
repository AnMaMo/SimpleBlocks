package SimpleBlocks.guis;

import SimpleBlocks.SimpleBlocks;
import SimpleBlocks.managers.FileCreator;
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
    private SimpleBlocks plugin;
    public EditMenu(SimpleBlocks plugin) {
        this.plugin = plugin;
    }

    public void createInventory(Player player, String name) {
        FileCreator messages = this.plugin.getMessages();
        FileCreator bloques = plugin.getBlocks();

        // Creamos un inventario con el nombre de la variable name
        Inventory inv = Bukkit.createInventory(null, 54, "EDIT " + name);

        // Abrimos el inventario al usuario
        player.openInventory(inv);
        // cojemos el array de items
        ArrayList<String> items = (ArrayList<String>) bloques.getStringList(name + ".sets");

        // Recorremos el array de items
        for (int i = 0; i < items.size(); i++) {
            // Si el item no es null
            if (Material.getMaterial(items.get(i)) != null) {
                // Creamos un itemstack con el bloque
                ItemStack item = new ItemStack(Material.valueOf(items.get(i)));
                // Añadimos el item al inventario
                inv.setItem(i, item);
            }
        }

        // Updateamos el inventario
        player.updateInventory();
    }

    // Evento que se ejecuta cuando se cierra el inventario
    @EventHandler
    public void closeinv(InventoryCloseEvent event) {
        FileCreator messages = this.plugin.getMessages();
        FileCreator bloques = plugin.getBlocks();
        String nameinv = event.getView().getTitle().replace("EDIT ", "");
        Inventory inv = event.getInventory();

        // Iteramos los bloques de la config para ver si el inventario es de un bloque
        for (String name : bloques.getKeys(false)) {
            if (nameinv.equals(name))
                nameinv = name;
        }

        // Si el titulo contiene el nombre del bloque es que es su inventario
        if (event.getView().getTitle().contains(nameinv) && event.getView().getTitle().contains("EDIT")) {
            // Borraremos el arraylist de sets y crearemos uno nuevo
            bloques.set(nameinv + ".sets", null);
            ArrayList<String> nuevositems = new ArrayList<>();

            // Recorremos el inventario
            for (ItemStack item : inv.getContents()) {
                // Si el item no es null y es un bloque
                if (item != null && item.getType() != Material.AIR && item.getType().isBlock()) {
                    nuevositems.add(item.getType().toString());
                }
            }

            // Añadimos el arraylist de items al bloque
            bloques.set(nameinv + ".sets", nuevositems);
            bloques.save();

            // Mensaje al usuario
            event.getPlayer().sendMessage(messages.getString("BloqueEditado"));
        }
    }
}
