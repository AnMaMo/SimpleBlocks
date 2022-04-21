package me.theoldduck.events;

import me.theoldduck.SimpleBlocks;
import me.theoldduck.managers.FileCreator;
import me.theoldduck.managers.Settings;
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
    SimpleBlocks plugin = SimpleBlocks.getInstance();
    FileCreator blocks = plugin.getBlocks();
    Settings settings = new Settings();

    // Block break event.
    @EventHandler
    public void BlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location breakLocation = block.getLocation();

        // Iterate config blocks.
        for (String blockPath : blocks.getKeys(false)) {

            // Check if config contains location
            if (!blocks.contains(blockPath + ".location")){
                player.sendMessage(settings.NoLocationFound);
                return;
            }

            Location blockPathLocation = blocks.getLocation(blockPath + ".location");

            // If locations is the same.
            if (breakLocation.equals(blockPathLocation)) {
                ArrayList<String> sets = (ArrayList<String>) blocks.getStringList(blockPath + ".sets");
                int random = (int) (Math.random() * sets.size());

                // Iterate drops to event and drop it.
                for (ItemStack drop : event.getBlock().getDrops()) {
                    block.getWorld().dropItem(breakLocation, drop);
                }

                player.giveExp(event.getExpToDrop());
                String newblock = sets.get(random);

                // Check is not null, and check if is a block.
                if (Material.getMaterial(newblock) != null && Material.valueOf(newblock).isBlock()) {
                    block.setType(Material.valueOf(newblock));
                } else {
                    block.setType(Material.STONE);
                    return;
                }

                // Cancel break event.
                event.setCancelled(true);
            }
        }
    }
}