package me.tj.tjlandkitpvpremake.commands.msg;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ReplyCommand implements CommandExecutor {


    private final DataManager dataManager;

    public ReplyCommand(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[AquaRealm] Only players can use this command.");
            return true;
        }

        Player senderPlayer = (Player) sender;
        UUID senderId = senderPlayer.getUniqueId();

        PlayerData playerData = dataManager.getPlayerData(senderId);
        UUID lastMessagedPlayer = playerData.getLastMessagedPlayer();

        if (lastMessagedPlayer == null) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<bold><Aqua>Aqua<white>Realm</bold> | No one has messaged you."));
            return true;
        }

        Player targetPlayer = Bukkit.getPlayer(lastMessagedPlayer);
        if (targetPlayer == null || !targetPlayer.isOnline()) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<bold><Aqua>Aqua<white>Realm</bold> | The last messaged player is not online."));
            return true;
        }

        String message = String.join(" ", args);

        sender.sendMessage(MiniMessage.miniMessage().deserialize("<dark_gray>(<aqua>To <gray>" + targetPlayer.getName() + "<dark_gray>)<gray> " + message));
        targetPlayer.sendMessage(MiniMessage.miniMessage().deserialize("<dark_gray>(<aqua>From <gray>" + senderPlayer.getName() + "<dark_gray>)<gray> " + message));

        return true;
    }
}

