package me.tj.tjlandkitpvpremake.commands.msg;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.UUID;

public class MessageCommand implements CommandExecutor {

    private final DataManager dataManager;

    public MessageCommand(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[AquaRealm] Only players can use this command.");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<bold><Aqua>Aqua<white>Realm</bold> | Usage: /msg <player> <message>"));
            return true;
        }

        Player senderPlayer = (Player) sender;
        Player targetPlayer = Bukkit.getPlayer(args[0]);

        if (targetPlayer == null || !targetPlayer.isOnline()) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<bold><Aqua>Aqua<white>Realm</bold> | Player not found."));
            return true;
        }

        UUID senderId = senderPlayer.getUniqueId();
        UUID targetId = targetPlayer.getUniqueId();

        String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        dataManager.getPlayerData(senderId).setLastMessagedPlayer(targetId);
        dataManager.getPlayerData(targetId).setLastMessagedPlayer(senderId);

        sender.sendMessage(MiniMessage.miniMessage().deserialize("<dark_gray>(<aqua>To <gray>" + targetPlayer.getName() + "<dark_gray>)<gray> " + message));
        targetPlayer.sendMessage(MiniMessage.miniMessage().deserialize("<dark_gray>(<aqua>From <gray>" + senderPlayer.getName() + "<dark_gray>)<gray> " + message));
        return true;


    }
}