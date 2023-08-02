package me.tj.tjlandkitpvpremake;

import me.tj.tjlandkitpvpremake.KitPvPCore.CoreEvents;
import me.tj.tjlandkitpvpremake.KitPvPCore.KitPvPManager.KitSelectorGive;
import me.tj.tjlandkitpvpremake.KitPvPCore.KitPvPManager.kitManagerRegions;
import me.tj.tjlandkitpvpremake.KitPvPCore.KitPvPManager.kitSelectorGui;
import me.tj.tjlandkitpvpremake.commands.*;
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
        getCommand("setspawn").setExecutor(new setspawn(this));
        getCommand("spawn").setExecutor(new spawn(this));

        // Registering basic events
        getServer().getPluginManager().registerEvents(new CoreEvents(this), this);
        getServer().getPluginManager().registerEvents(new KitSelectorGive(), this);
        getServer().getPluginManager().registerEvents(new kitSelectorGui(this), this);
        getServer().getPluginManager().registerEvents(new kitManagerRegions(this), this);

    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("[TjLandKit] unloaded successfully.");
    }
}
