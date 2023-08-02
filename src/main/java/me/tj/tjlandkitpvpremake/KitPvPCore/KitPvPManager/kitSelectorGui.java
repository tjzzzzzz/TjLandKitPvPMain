package me.tj.tjlandkitpvpremake.KitPvPCore.KitPvPManager;

import me.tj.tjlandkitpvpremake.TjLandKitPvPRemake;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.ArrayList;

public class kitSelectorGui implements Listener {
    private final TjLandKitPvPRemake plugin;
    public kitSelectorGui(TjLandKitPvPRemake plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (event.getAction().name().contains("RIGHT_CLICK") && itemInHand.getType() == Material.DIAMOND_SWORD) {
            if (itemInHand.getItemMeta().hasDisplayName() &&
                    itemInHand.getItemMeta().getDisplayName().equals(ChatColor.DARK_BLUE + ChatColor.BOLD.toString() + "Kit Selector")) {
                Inventory kitSelectorGui = createKitSelectorInventory(player);
                player.openInventory(kitSelectorGui);

            }
        }
    }

    private Inventory createKitSelectorInventory(Player player) {
        String invName = "§1§lKit Selector Menu";
        Inventory kitselectorguiInv = Bukkit.createInventory(player, 9, ChatColor.translateAlternateColorCodes('&', invName));
        String pvpKitName = "&f&lPvP Kit";
        ItemStack pvpKit = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemMeta pvpKitMeta = pvpKit.getItemMeta();
        pvpKitMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', pvpKitName));
        ArrayList<String> pvpkitLore = new ArrayList<>();
        pvpkitLore.add("§7Default PvP Kit");
        pvpkitLore.add("§c§lClick to equip");
        pvpKitMeta.setLore(pvpkitLore);
        pvpKit.setItemMeta(pvpKitMeta);
        kitselectorguiInv.setItem(4, pvpKit);
        return kitselectorguiInv;
    }



    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "§1§lKit Selector Menu"))) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            String pvpKitName = ChatColor.translateAlternateColorCodes('&', "&f&lPvP Kit");
            if (clickedItem != null && clickedItem.getType() == Material.IRON_CHESTPLATE
                    && clickedItem.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', pvpKitName)) ) {

                ItemStack ironHelmet = new ItemStack(Material.IRON_HELMET);
                ItemStack ironChestplate = new ItemStack(Material.IRON_CHESTPLATE);
                ItemStack ironLeggings = new ItemStack(Material.IRON_LEGGINGS);
                ItemStack ironBoots = new ItemStack(Material.IRON_BOOTS);
                player.getInventory().setHelmet(ironHelmet);
                player.getInventory().setChestplate(ironChestplate);
                player.getInventory().setLeggings(ironLeggings);
                player.getInventory().setBoots(ironBoots);

                ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);
                player.getInventory().setItem(0, diamondSword);
                ItemStack instantHealthPotion = new ItemStack(Material.SPLASH_POTION, 1);
                PotionMeta potionMeta = (PotionMeta) instantHealthPotion.getItemMeta();
                potionMeta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL, false, true));
                instantHealthPotion.setItemMeta(potionMeta);

                for (int i = 1; i < player.getInventory().getSize(); i++) {
                    if (i != 0 && i != 40 && player.getInventory().getItem(i) == null) {
                        player.getInventory().setItem(i, instantHealthPotion);
                    }
                }
                player.updateInventory();
                player.sendMessage(ChatColor.GREEN + "You have equipped the PvP Kit!");

            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        e.getDrops().clear();
        new BukkitRunnable() {
            @Override
            public void run() {
                player.spigot().respawn();
            }
        }.runTaskLater(plugin, 0);
    }
    private ItemStack createKitSelector(Player player) {
        ItemStack kitSelector = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta kitSelectorMeta = kitSelector.getItemMeta();
        String kitSelectorName = "&1&lKit Selector";
        kitSelectorMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', kitSelectorName));
        kitSelectorMeta.setUnbreakable(true);
        ArrayList<String> kitSelectorLore = new ArrayList<>();
        kitSelectorLore.add(ChatColor.translateAlternateColorCodes('&', "&7Right click with this item, to choose a kit."));
        kitSelectorLore.add(ChatColor.translateAlternateColorCodes('&', "&7Need help, run /help!"));
        kitSelectorMeta.setLore(kitSelectorLore);
        kitSelector.setItemMeta(kitSelectorMeta);
        return kitSelector;
    }


    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
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
            e.setRespawnLocation(spawnLocation);
            player.sendMessage(MiniMessage.miniMessage().deserialize("<bold><Aqua>Tj<white>Land</bold> You've been teleported to spawn."));
            ItemStack kitSelector = createKitSelector(player);
            player.getInventory().setItem(0, kitSelector);
            Bukkit.getLogger().info("[TjLand] " + player.getDisplayName() + " Teleported to spawn because they died.");
        } else {
            Bukkit.getLogger().info("[TjLand] There is no spawn point set, so we cannot teleport a player when they die to spawn. Set a spawn point by doing /setspawn.");
        }
    }




}

