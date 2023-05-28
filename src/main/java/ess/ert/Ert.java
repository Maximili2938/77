package ess.ert;

import ess.ert.Lister.Dateo;
import ess.ert.commands.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ert extends JavaPlugin implements Listener {

    public static Dateo date = new Dateo();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Плагин успешно включён");

        // Регистрация событий и команд
        getCommand("fly").setExecutor(new Fly());
        getCommand("skull").setExecutor(new SkullCommand());
        EdiCommand ediCommand = new EdiCommand(this);
        getCommand("edi").setExecutor(ediCommand);
        getServer().getPluginManager().registerEvents(ediCommand, this);
        getServer().getPluginManager().registerEvents(this, this);
        new GodPotion(this);
        new SignEditor(this);
        new Native(this);
        new catana(this);
        new SBO(this);
        new Dateo();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Плагин успешно выключен");
    }
}