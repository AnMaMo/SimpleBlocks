package SimpleBlocks.events;

import SimpleBlocks.SimpleBlocks;
import SimpleBlocks.managers.FileCreator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class BreakBlock implements Listener {

    private SimpleBlocks plugin;
    public BreakBlock(SimpleBlocks plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void BlockBreak(BlockBreakEvent event) {
        FileCreator messages = this.plugin.getMessages();
        FileCreator bloques = plugin.getBlocks();

        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location breakloc = block.getLocation();

        // Iteraremos todos los bloques de la config
        for (String bloque : bloques.getKeys(false)) {
            // Guardamos la location del bloque y lo comparamos con la location del bloque roto
            Location bloquelocation = bloques.getLocation(bloque + ".location");

            // Si las dos location son iguales es que se ha roto ese bloque
            if (breakloc.equals(bloquelocation)) {

                // cojemos el array de items
                ArrayList<String> items = (ArrayList<String>) bloques.getStringList(bloque + ".sets");

                // Generaremos un numero aleatorio entre 1 y el largo del array
                int random = (int) (Math.random() * items.size());

                // Iteraremos los drops del evento y los dropearemos
                for (ItemStack drop : event.getBlock().getDrops()) {
                    block.getWorld().dropItem(breakloc, drop);
                }

                // Daremos la xp al usuario
                player.giveExp(event.getExpToDrop());

                // Cojeremos el nuevo bloque que setear
                String newblock = items.get(random);

                // Miraremos que el bloque sea un bloque valido y lo setearemos
                if (Material.getMaterial(newblock) != null && Material.valueOf(newblock).isBlock()) {
                    block.setType(Material.valueOf(newblock));
                } else {
                    block.setType(Material.STONE);
                    return;
                }

                // Cancelamos el evento de romper
                event.setCancelled(true);
            }
        }
    }
}