package me.tj.tjlandkitpvpremake;

import me.tj.tjlandkitpvpremake.commands.gmc;
import me.tj.tjlandkitpvpremake.commands.gms;
import me.tj.tjlandkitpvpremake.commands.gmsp;
import me.tj.tjlandkitpvpremake.commands.help;
import me.tj.tjlandkitpvpremake.commands.msg.DataManager;
import me.tj.tjlandkitpvpremake.commands.msg.MessageCommand;
import me.tj.tjlandkitpvpremake.commands.msg.ReplyCommand;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class TjLandKitPvPRemake extends JavaPlugin {
    private DataManager dataManager;

    @Override
    public void onEnable() {
        // Logging
        Bukkit.getLogger().info("[TjLandKit] loaded successfully.");

        // Luck-Perms Api
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            LuckPerms api = provider.getProvider();
        }

        // Msg command registering
        dataManager = new DataManager();
        getCommand("msg").setExecutor(new MessageCommand(dataManager));
        getCommand("r").setExecutor(new ReplyCommand(dataManager));

        // Registering basic commands.
        getCommand("gmc").setExecutor(new gmc());
        getCommand("gms").setExecutor(new gms());
        getCommand("gmsp").setExecutor(new gmsp());
        getCommand("help").setExecutor(new help());

    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("[TjLandKit] unloaded successfully.");
    }
}
