package me.tj.tjlandkitpvpremake.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
public class gmc implements CommandExecutor {
    public boolean isPlayerInGroup(Player player, String group) {
        return player.hasPermission("group." + group);
    }
    public boolean isPlayerNotInStaffGroup(Player player) {
        return !(isPlayerInGroup(player, "owner") ||
                isPlayerInGroup(player, "developer") ||
                isPlayerInGroup(player, "admin") ||
                isPlayerInGroup(player, "moderator") ||
                isPlayerInGroup(player, "helper"));
    }
    public boolean isPlayerInStaffGroup(Player player) {
        return isPlayerInGroup(player, "owner") ||
                isPlayerInGroup(player, "developer") ||
                isPlayerInGroup(player, "admin") ||
                isPlayerInGroup(player, "moderator") ||
                isPlayerInGroup(player, "helper");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (isPlayerNotInStaffGroup(player)) {
                player.sendMessage(MiniMessage.miniMessage().deserialize("<bold><Aqua>Tj<white>Land</bold> No permission."));
            }
            if (isPlayerInStaffGroup(player)) {
                if (args.length == 0) {
                    if (player.getGameMode().equals(GameMode.CREATIVE)) {
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<bold><Aqua>Tj<white>Land</bold> You're already in creative mode."));
                    } else {
                        player.setGameMode(GameMode.CREATIVE);
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<bold><Aqua>Tj<white>Land</bold> Gamemode set to creative."));
                    }
                } else {
                    String targetName = args[0];
                    Player target = Bukkit.getServer().getPlayerExact(targetName);
                    if (target == null) {
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<bold><Aqua>Tj<white>Land</bold> Player " + targetName + " not found."));
                        return true;
                    }
                    String targetname = target.getPlayer().getName();
                    if (target.getGameMode().equals(GameMode.CREATIVE)) {
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<bold><Aqua>Tj<white>Land</bold> " + targetname + " is already in creative mode."));
                    } else {
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<bold><Aqua>Tj<white>Land</bold> Gamemode creative set for " + targetname));
                        target.setGameMode(GameMode.CREATIVE);
                    }
                }
            }
        }
        return true;
    }
}
