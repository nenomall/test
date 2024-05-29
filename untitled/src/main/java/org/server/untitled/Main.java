package org.server.untitled;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Main extends JavaPlugin implements Listener, CommandExecutor {
    public static Main instance;
    public final Map<UUID, Long> bankMap = new HashMap<>();
    public final Map<UUID, Objective> scoreboardMap = new HashMap<>();
    public final Map<String, String> prefixMap = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        instance = this;
        getCommand("jump").setExecutor(new ComJump());
        getCommand("scoreboard").setExecutor(new ComScoreboard());
        getCommand("emerald").setExecutor(new Eco());
        timerFunc();
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws InterruptedException {
        Player player = event.getPlayer();

        new Prefixes().setTab(player);
        new Eco().scoreboardSet(player);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        Bukkit.broadcastMessage("§8 [§6G§8] §f" + event.getPlayer().getDisplayName() + "§8 » §f" + event.getMessage());
    }

    public void timerFunc() {
        BukkitRunnable timer = new BukkitRunnable() {
            @Override
            public void run() {
                World world = Bukkit.getWorlds().get(0);
                String currentTime = "Текущее время: ";
                if (world.getTime() >= 0 && world.getTime() <= 6000) {
                    currentTime += "Утро";
                } else if (world.getTime() > 6000 && world.getTime() <= 12000) {
                    currentTime += "День";
                } else if (world.getTime() > 12000 && world.getTime() <= 18000) {
                    currentTime += "Вечер";
                } else {
                    currentTime += "Ночь";
                }
                Bukkit.broadcastMessage(currentTime);
            }

        };

        timer.runTaskTimer(Main.instance, 0L, 600L);
    }

}








