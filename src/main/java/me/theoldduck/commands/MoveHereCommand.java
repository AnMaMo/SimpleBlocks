package me.theoldduck.commands;

import me.theoldduck.SimpleBlocks;
import me.theoldduck.managers.FileCreator;
import me.theoldduck.managers.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoveHereCommand implements CommandExecutor {
    SimpleBlocks plugin = SimpleBlocks.getInstance();
    FileCreator blocks = plugin.getBlocks();
    Settings settings = new Settings();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // If sender is not a player return.
        if (!(sender instanceof Player)) {
            sender.sendMessage(settings.NoEresPlayer);
            return false;
        }

        Player player = (Player) sender;

        // If player has not permission return.
        if (!player.hasPermission("simpleblocks.admin")) {
            sender.sendMessage(settings.NoPermisos);
            return false;
        }

        // If args length is not 1 return.
        if (args.length != 1) {
            player.sendMessage(settings.ErrorRemoveBlock);
            return false;
        }

        String name = args[0];
        Location newLoc = player.getLocation().getBlock().getLocation();

        // If blocks does not contains name return.
        if (!blocks.contains(name)) {
            player.sendMessage(settings.BloqueInexistente);
            return false;
        }

        // Set 0 to old location
        Location oldLoc = blocks.getLocation(name + ".location");
        oldLoc.getBlock().setType(Material.AIR);

        // Set new location to blocks.
        blocks.set(name + ".location", newLoc);
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> blocks.save());
        player.sendMessage(settings.NewLocationSet);

        // Set diamond block to new location
        newLoc.getBlock().setType(Material.DIAMOND_BLOCK);
        return false;
    }
}