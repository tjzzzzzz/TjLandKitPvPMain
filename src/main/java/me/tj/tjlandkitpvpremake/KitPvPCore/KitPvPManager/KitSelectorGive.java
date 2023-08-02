package me.tj.tjlandkitpvpremake.KitPvPCore.KitPvPManager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class KitSelectorGive implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        boolean hotbarIsEmpty = true;
        for (int slot = 0; slot < 9; slot++) {
            ItemStack item = player.getInventory().getItem(slot);
            if (item != null && item.getType() != Material.AIR) {
                hotbarIsEmpty = false;
                break;
            }
        }
        if (hotbarIsEmpty) {
            ItemStack kitSelector = createKitSelector(player);
            player.getInventory().setItem(0, kitSelector);

        }
    }

    private ItemStack createKitSelector(Player player) {
        ItemStack kitSelector = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta kitSelectorMeta = kitSelector.getItemMeta();
        String kitSelectorName = "&1&lKit Selector";
        kitSelectorMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', kitSelectorName));
        kitSelectorMeta.setUnbreakable(true);

        List<String> lore = new ArrayList<>();

        lore.add(ChatColor.GRAY + "Right click with to choose a kit");
        lore.add(ChatColor.RED + "Need help? Run /help!");

        kitSelectorMeta.setLore(lore);

        kitSelector.setItemMeta(kitSelectorMeta);



        return kitSelector;
    }
}

