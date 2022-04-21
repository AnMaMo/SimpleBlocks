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

import java.util.ArrayList;

public class SetBlockCommand implements CommandExecutor {
    SimpleBlocks plugin = SimpleBlocks.getInstance();
    FileCreator blocks = plugin.getBlocks();
    Settings settings = new Settings();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // If sender is not player return.
        if (!(sender instanceof Player)){
            sender.sendMessage(settings.NoEresPlayer);
            return false;
        }

        Player player = (Player)sender;
        Location loc = player.getLocation().getBlock().getLocation();

        // If player has not permissions return.
        if (!player.hasPermission("simpleblocks.admin")) {
            sender.sendMessage(settings.NoPermisos);
            return false;
        }

        // If args length is not 1 return.
        if (args.length != 1) {
            player.sendMessage(settings.ErrorSetBlock);
            return false;
        }

        String name = args[0];

        // If block already exist return.
        if (blocks.getKeys(true).contains(name)) {
            player.sendMessage(settings.ErrorSetBlockExiste);
            return false;
        }

        ArrayList <String> items = new ArrayList<String>();
        items.add("DIAMOND_BLOCK");

        blocks.set(name + ".location", loc);
        blocks.set(name + ".sets", items);
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> blocks.save());
        loc.getBlock().setType(Material.DIAMOND_BLOCK);

        player.sendMessage(settings.BloqueSet);
        return false;
    }
}