package SimpleBlocks;

import SimpleBlocks.commands.EditCommand;
import SimpleBlocks.commands.RemoveBlock;
import SimpleBlocks.commands.SetBlock;
import SimpleBlocks.events.BreakBlock;
import SimpleBlocks.guis.EditMenu;
import SimpleBlocks.managers.FileCreator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class SimpleBlocks extends JavaPlugin {

    private FileCreator bloques;
    private FileCreator messages;

    public void onEnable() {
        bloques = new FileCreator(this, "bloques", ".yml");
        messages = new FileCreator(this, "messages", ".yml");
        regEvents();
        regCommands();
    }

    public void regCommands(){
        getCommand("sbsetblock").setExecutor(new SetBlock(this));
        getCommand("sbeditblock").setExecutor(new EditCommand(this));
        getCommand("sbremoveblock").setExecutor(new RemoveBlock(this));
    }

    public void regEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new BreakBlock(this), this);
        pm.registerEvents(new EditMenu(this), this);
    }

    public FileCreator getBlocks() {
        return this.bloques;
    }
    public FileCreator getMessages() {
        return this.messages;
    }

}
