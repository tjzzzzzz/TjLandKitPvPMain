package me.tj.tjlandkitpvpremake.commands;

import me.tj.tjlandkitpvpremake.TjLandKitPvPRemake;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
public class spawn implements CommandExecutor {

    private final TjLandKitPvPRemake plugin;
    public spawn(TjLandKitPvPRemake plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("op")) {
                FileConfiguration config = plugin.getConfig();
                if (config.isConfigurationSection("Spawn")) {
                    String senderName = player.getName();
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
                    Bukkit.getLogger().info("[TjLand] " + senderName + " Teleported to spawn using /spawn.");
                } else {
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<bold><Aqua>Tj<white>Land</bold> There is not spawn point set run /setspawn to set it."));
                }
            } else {
                player.sendMessage(MiniMessage.miniMessage().deserialize("<bold><Aqua>Tj<white>Land</bold> No permission"));
            }
        } else {
            sender.sendMessage("[TjLand] Bruh get yo ass on the server.");
        }
        return true;
    }

}


