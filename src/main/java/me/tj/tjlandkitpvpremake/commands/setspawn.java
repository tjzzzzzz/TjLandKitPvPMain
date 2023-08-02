package me.tj.tjlandkitpvpremake.commands;

import me.tj.tjlandkitpvpremake.TjLandKitPvPRemake;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class setspawn implements CommandExecutor {
    private final TjLandKitPvPRemake plugin;
    public setspawn(TjLandKitPvPRemake plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("op")) {
                Location currentLocation = player.getLocation();
                FileConfiguration config = plugin.getConfig();
                config.set("Spawn.world", currentLocation.getWorld().getName());
                config.set("Spawn.x", currentLocation.getX());
                config.set("Spawn.y", currentLocation.getY());
                config.set("Spawn.z", currentLocation.getZ());
                config.set("Spawn.yaw", currentLocation.getYaw());
                config.set("Spawn.pitch", currentLocation.getPitch());
                plugin.saveConfig();
                player.sendMessage(MiniMessage.miniMessage().deserialize("<bold><Aqua>Tj<white>Land</bold> Spawn point has been set successfully."));
            } else {
                player.sendMessage(MiniMessage.miniMessage().deserialize("<bold><Aqua>Tj<white>Land</bold> No permission."));
            }
        } else {
            sender.sendMessage("[TjLand] Bruh get yo ass on the server.");
        }
        return true;
    }

}
