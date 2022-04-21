package me.theoldduck.managers;

import me.theoldduck.SimpleBlocks;

public class Settings {
    SimpleBlocks plugin = SimpleBlocks.getInstance();
    FileCreator messages = plugin.getMessages();

    public final String Prefix = Color.color(messages.getString("Prefix"));
    public final String NoEresPlayer = Color.color(Prefix + messages.getString("NoEresPlayer"));
    public final String NoPermisos = Color.color(Prefix + messages.getString("NoPermisos"));
    public final String ErrorSetBlock = Color.color(Prefix + messages.getString("ErrorSetBlock"));
    public final String ErrorRemoveBlock = Color.color(Prefix + messages.getString("ErrorRemoveBlock"));
    public final String ErrorEditBlock = Color.color(Prefix + messages.getString("ErrorEditBlock"));
    public final String BloqueSet = Color.color(Prefix + messages.getString("BloqueSet"));
    public final String BloqueRemove = Color.color(Prefix + messages.getString("BloqueRemove"));
    public final String BloqueInexistente = Color.color(Prefix + messages.getString("BloqueInexistente"));
    public final String BloqueEditado = Color.color(Prefix + messages.getString("BloqueEditado"));
    public final String ErrorSetBlockExiste = Color.color(Prefix + messages.getString("ErrorSetBlockExiste"));
    public final String ErrorMainCommand = Color.color(Prefix + messages.getString("ErrorMainCommand"));
    public final String Reload = Color.color(Prefix + messages.getString("Reload"));
    public final String NoLocationFound = Color.color(Prefix + messages.getString("NoLocationFound"));
    public final String NewLocationSet = Color.color(Prefix + messages.getString("NewLocationSet"));
}
