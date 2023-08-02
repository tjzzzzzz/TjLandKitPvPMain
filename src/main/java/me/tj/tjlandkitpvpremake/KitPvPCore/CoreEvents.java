package me.tj.tjlandkitpvpremake.KitPvPCore;

import me.tj.tjlandkitpvpremake.TjLandKitPvPRemake;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CoreEvents implements Listener {
    private final TjLandKitPvPRemake plugin;
    public CoreEvents(TjLandKitPvPRemake plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        String playerName = player.getName();
        e.joinMessage(MiniMessage.miniMessage().deserialize(""));
        Bukkit.broadcast(MiniMessage.miniMessage().deserialize("<bold><Aqua>Tj<white>Land</bold> "+ playerName + " has joined the server!"));
        player.sendMessage(MiniMessage.miniMessage().deserialize("<bold><Aqua>Tj<white>Land</bold> Welcome to TjLand. If you have any questions feel free to ask or run /help!"));

        if(!player.hasPlayedBefore()) {
            FileConfiguration config = plugin.getConfig();
            if (config.isConfigurationSection("Spawn")) {
                ConfigurationSection spawnSection = config.getConfigurationSection("Spawn");
                String worldName = spawnSection.getString("world");
                double x = spawnSection.getDouble("x");
                double y = spawnSection.getDouble("y");
                double z = spawnSection.getDouble("z");
                float yaw = (float) spawnSection.getDouble("yaw");
                float pitch = (float) spawnSection.getDouble("pitch");
                Location spawnLocation = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);

                player.teleport(spawnLocation);
                player.sendMessage(MiniMessage.miniMessage().deserialize("<bold><Aqua>Tj<white>Land</bold> You've been teleported to spawn."));
                Bukkit.getLogger().info("[TjLand] " + playerName + " Teleported to spawn, because it's their first time joining.");
            } else {
                Bukkit.getLogger().info("[TjLand] There is no spawn point set, so new players will not get teleported to spawn on join!");
            }
        }
    }
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        String playerName = player.getName();
        e.quitMessage(MiniMessage.miniMessage().deserialize(""));
        Bukkit.broadcast(MiniMessage.miniMessage().deserialize("<bold><Aqua>Tj<white>Land</bold> "+ playerName + " has left the server!"));
    }
}
