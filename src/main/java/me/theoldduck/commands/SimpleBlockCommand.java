package me.theoldduck.commands;

import me.theoldduck.SimpleBlocks;
import me.theoldduck.managers.FileCreator;
import me.theoldduck.managers.Settings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SimpleBlockCommand implements CommandExecutor {
    SimpleBlocks plugin = SimpleBlocks.getInstance();
    FileCreator messages = plugin.getMessages();
    FileCreator blocks = plugin.getBlocks();
    Settings settings = new Settings();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("simpleblocks.admin")) {
            sender.sendMessage(settings.NoPermisos);
            return false;
        }

        if (args.length != 1) {
            sender.sendMessage(settings.ErrorMainCommand);
            return false;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> messages.save());
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> messages.reload());
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> blocks.save());
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> blocks.reload());
            sender.sendMessage(settings.Reload);
            return false;
        }

        return false;
    }
}