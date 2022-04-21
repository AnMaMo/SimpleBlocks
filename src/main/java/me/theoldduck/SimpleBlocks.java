package me.theoldduck;

import me.theoldduck.commands.*;
import me.theoldduck.events.BreakBlock;
import me.theoldduck.guis.EditMenu;
import me.theoldduck.managers.FileCreator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleBlocks extends JavaPlugin {
    private FileCreator blocks;
    private FileCreator messages;
    private static SimpleBlocks instance;

    public void onEnable() {
        instance = this;
        blocks = new FileCreator(this, "blocks", ".yml");
        messages = new FileCreator(this, "messages", ".yml");
        regEvents();
        regCommands();
    }

    //Getter of instance
    public static SimpleBlocks getInstance() {
        return instance;
    }

    // Register commands
    public void regCommands(){
        getCommand("sbsetblock").setExecutor(new SetBlockCommand());
        getCommand("sbeditblock").setExecutor(new EditCommand());
        getCommand("sbremoveblock").setExecutor(new RemoveBlockCommand());
        getCommand("simpbleblocks").setExecutor(new SimpleBlockCommand());
        getCommand("sbmovehere").setExecutor(new MoveHereCommand());
    }

    // Register events
    public void regEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new BreakBlock(), this);
        pm.registerEvents(new EditMenu(), this);
    }

    public FileCreator getBlocks() {
        return this.blocks;
    }
    public FileCreator getMessages() {
        return this.messages;
    }

}
