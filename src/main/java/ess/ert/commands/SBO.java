package ess.ert.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import ess.ert.Lister.Dateo;

public class SBO implements Listener {
    private JavaPlugin plugin;
    private Scoreboard scoreboard;
    private Objective objective;
    private Team dayNightTeam;

    public SBO(JavaPlugin plugin) {
        this.plugin = plugin;
        registerEvents();
        startScoreboardUpdateTask();
    }

    private void registerEvents() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void applyScoreboard(Player player) {
        if (scoreboard == null) {
            return;
        }

        player.setScoreboard(scoreboard);
    }

    private void createScoreboard() {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("Skyblock", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        dayNightTeam = scoreboard.registerNewTeam("dayNightTeam");
        dayNightTeam.addEntry(ChatColor.GREEN.toString());
        dayNightTeam.setPrefix(ChatColor.GREEN + "D");
        objective.getScore(ChatColor.GREEN.toString()).setScore(1);
    }

    private void updateScoreboard() {
        World world = Bukkit.getWorlds().get(0); // Получаем первый загруженный мир (можно изменить по необходимости)
        boolean isDay = world.getTime() < 12300 || world.getTime() > 23850;

        String emoji = Dateo.getDayData(isDay); // Получаем значение emoji из класса Date

        if (dayNightTeam != null) {
            dayNightTeam.setPrefix(emoji.replace("&", "§"));
        }
    }

    private void startScoreboardUpdateTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (scoreboard == null) {
                    createScoreboard();
                }
                updateScoreboard();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    applyScoreboard(player);
                }
            }
        }.runTaskTimer(plugin, 0L, 2L); // Запуск задачи каждую секунду (20 тиков)
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        applyScoreboard(player);
    }
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (event.toWeatherState()) {
            event.setCancelled(true); // Отменяем изменение погоды на дождь
        }
    }
}
