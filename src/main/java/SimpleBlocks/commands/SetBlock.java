package SimpleBlocks.commands;

import SimpleBlocks.SimpleBlocks;
import SimpleBlocks.managers.Color;
import SimpleBlocks.managers.FileCreator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SetBlock implements CommandExecutor {
    private final SimpleBlocks plugin;

    public SetBlock(SimpleBlocks plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileCreator messages = this.plugin.getMessages();
        FileCreator bloques = plugin.getBlocks();

        // Si el sender no es un player mandaremos un mensaje de error y return
        if (!(sender instanceof Player)){
            sender.sendMessage(Color.color(messages.getString("NoEresPlayer")));
            return false;
        }

        Player player = (Player)sender;
        Location loc = player.getLocation().getBlock().getLocation();
        String prefix = messages.getString("Prefix");

        // Si el player no tiene permiso "simpleblocks.admin" mensaje de error y return
        if (!player.hasPermission("simpleblocks.admin")) {
            sender.sendMessage(Color.color(prefix + messages.getString("NoPermisos")));
            return false;
        }

        // Si el comando no es correcto mensaje de error y return
        if (args.length != 1) {
            player.sendMessage(Color.color(prefix + messages.getString("ErrorSetBlock")));
            return false;
        }

        // Si el bloque ya existe mensaje de error y return
        if (bloques.getStringList("Bloques").contains(args[0])) {
            player.sendMessage(Color.color(prefix + messages.getString("ErrorSetBlockExiste")));
            return false;
        }

        // Guardamos el nombre del bloque
        String nombre = args[0];
        ArrayList <String> items = new ArrayList<String>();
        items.add("DIAMOND_BLOCK");

        // Guardamos la info en la config
        bloques.set(nombre + ".location", loc);
        bloques.set(nombre + ".sets", items);
        bloques.save();

        // Steamos la loc en Bloque de diamante para que se pueda usar
        loc.getBlock().setType(Material.DIAMOND_BLOCK);

        // Mensaje de confirmacion
        player.sendMessage(Color.color(prefix + messages.getString("BloqueSet")));
        return false;
    }
}