package SimpleBlocks.commands;


import SimpleBlocks.SimpleBlocks;
import SimpleBlocks.managers.Color;
import SimpleBlocks.managers.FileCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveBlock implements CommandExecutor {
    private final SimpleBlocks plugin;

    public RemoveBlock(SimpleBlocks plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileCreator messages = this.plugin.getMessages();
        FileCreator bloques = plugin.getBlocks();

        // Si el sender no es un player mandaremos un mensaje de error y return
        if (!(sender instanceof Player)) {
            sender.sendMessage(Color.color(messages.getString("NoEresPlayer")));
            return false;
        }

        Player player = (Player) sender;
        String prefix = messages.getString("Prefix");

        // Si el player no tiene permiso "simpleblocks.admin" mensaje de error y return
        if (!player.hasPermission("simpleblocks.admin")) {
            sender.sendMessage(Color.color(prefix + messages.getString("NoPermisos")));
            return false;
        }

        // Si el comando no es correcto mensaje de error y return
        if (args.length != 1) {
            player.sendMessage(Color.color(prefix + messages.getString("ErrorRemoveBlock")));
            return false;
        }

        // Guardamos el nombre del bloque y lo borramos de la configuracion
        String name = args[0];

        if (!bloques.contains(name)) {
            player.sendMessage(Color.color(prefix + messages.getString("BloqueInexistente")));
            return false;
        }


        bloques.set(name, null);
        bloques.save();
        player.sendMessage(Color.color(prefix + messages.getString("BloqueRemove")));
        return false;
    }
}