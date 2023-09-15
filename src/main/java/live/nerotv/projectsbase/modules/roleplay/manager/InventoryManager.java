package live.nerotv.projectsbase.modules.roleplay.manager;

import live.nerotv.projectsbase.api.ConfigAPI;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class InventoryManager {

    public static void openInv001(Player p) {
        Inventory INV001;
        if(ConfigAPI.CFG.getBoolean("Core.Settings.Commands.Shop.MarkReward")) {
            INV001 = Bukkit.createInventory(null, 27, "§9AP-Shop");
            ItemManager.fillChestInventory (
                INV001, ItemManager.RewardMark001, ItemManager.RewardMark002, ItemManager.RewardMark005, ItemManager.RewardMark010,
                ItemManager.RewardMark020, ItemManager.RewardMark050, ItemManager.RewardMark100, ItemManager.RewardMark200, ItemManager.RewardMark500, ItemManager.BottleReward(), ItemManager.CoalReward(),
                ItemManager.GoldReward(), ItemManager.IronReward(), ItemManager.LapisReward(), ItemManager.DiamondReward(), ItemManager.NetherReward, ItemManager.RocketReward(), ItemManager.ElytraReward
            );
        } else {
            INV001 = Bukkit.createInventory(null, 27, "§9AP-Shop");
            ItemManager.fillChestInventory(
                    INV001, ItemManager.BottleReward(), ItemManager.CoalReward(),/* ItemManager.GoldReward(),*/ ItemManager.IronReward(), ItemManager.LapisReward(), ItemManager.DiamondReward(),
                    ItemManager.NetherReward, ItemManager.RocketReward(), ItemManager.ElytraReward, ItemManager.blackRevolverReward(), ItemManager.RevolverReward(), ItemManager.shotgunReward(), ItemManager.marksmanReward(), null, null, null, null, null, null
            );
        }
        p.openInventory(INV001);
        p.playSound(p.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
    }
    public static void openInv_APSHOP(Player p) {
        openInv001(p);
    }

    public static void openInv002(Player p) {
        Inventory INV002 = Bukkit.createInventory(null, InventoryType.HOPPER,"§9Warp-Menü");
        INV002.setItem(0, ItemManager.Placeholder);
        INV002.setItem(1, ItemManager.WarpC1);
        INV002.setItem(2, ItemManager.WarpC2);
        INV002.setItem(3, ItemManager.WarpETC);
        INV002.setItem(4, ItemManager.Placeholder);
        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
        p.openInventory(INV002);
    }


    public static void openInv_WarpC1(Player p) {
        Inventory INV003 = Bukkit.createInventory(null, InventoryType.HOPPER, ItemManager.WarpC1.getItemMeta().getDisplayName());
        INV003.setItem(0, ItemManager.Placeholder);
        INV003.setItem(1, ItemManager.WarpC101);
        INV003.setItem(2, ItemManager.WarpC102);
        INV003.setItem(3, ItemManager.WarpBack);
        INV003.setItem(4, ItemManager.Placeholder);
        p.playSound(p.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
        p.openInventory(INV003);
    }

    public static void openInv_WarpC2(Player p) {
        Inventory INV004 = Bukkit.createInventory(null, InventoryType.HOPPER, ItemManager.WarpC2.getItemMeta().getDisplayName());
        INV004.setItem(0, ItemManager.Placeholder);
        INV004.setItem(1, ItemManager.WarpC201);
        INV004.setItem(2, ItemManager.WarpC202);
        INV004.setItem(3, ItemManager.WarpBack);
        INV004.setItem(4, ItemManager.Placeholder);
        p.playSound(p.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
        p.openInventory(INV004);
    }

    public static void openInv_WarpETC(Player p) {
        Inventory INV005 = Bukkit.createInventory(null, InventoryType.HOPPER, ItemManager.WarpETC.getItemMeta().getDisplayName());
        INV005.setItem(0, ItemManager.Placeholder);
        INV005.setItem(1, ItemManager.WarpETC01);
        INV005.setItem(2, ItemManager.WarpETC02);
        INV005.setItem(3, ItemManager.WarpBack);
        INV005.setItem(4, ItemManager.Placeholder);
        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG,100,100);
        p.openInventory(INV005);
    }

    public static void openInv_GameMode(Player p) {
        Inventory INV006 = Bukkit.createInventory(null,InventoryType.HOPPER,"§bWähle §b§ldeinen§r§b Spielmodus§r§8...§r");
        INV006.setItem(0, ItemManager.GameModeSurvival);
        INV006.setItem(1, ItemManager.GameModeCreative);
        INV006.setItem(2, ItemManager.GameModeAdventure);
        INV006.setItem(3, ItemManager.GameModeSpectator);
        INV006.setItem(4, ItemManager.Close);
        p.playSound(p.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
        p.openInventory(INV006);
    }

    public static void openConfirmStopInventory(Player p) {
        Inventory confirmStopInventory = Bukkit.createInventory(null,InventoryType.HOPPER,"§fWillst du den Server §cstoppen§f?");
        confirmStopInventory.setItem(0, ItemManager.Placeholder);
        confirmStopInventory.setItem(1, ItemManager.Cancel);
        confirmStopInventory.setItem(2, ItemManager.Placeholder);
        confirmStopInventory.setItem(3, ItemManager.StopYes);
        confirmStopInventory.setItem(4, ItemManager.Placeholder);
        p.openInventory(confirmStopInventory);
        p.playSound(p.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
    }

    public static void openConfirmReloadInventory(Player p) {
        Inventory confirmReloadInventory = Bukkit.createInventory(null,InventoryType.HOPPER,"§fWillst du den Server §creloaden§f?");
        confirmReloadInventory.setItem(0, ItemManager.Placeholder);
        confirmReloadInventory.setItem(1, ItemManager.Cancel);
        confirmReloadInventory.setItem(2, ItemManager.Placeholder);
        confirmReloadInventory.setItem(3, ItemManager.ReloadYes);
        confirmReloadInventory.setItem(4, ItemManager.Placeholder);
        p.openInventory(confirmReloadInventory);
        p.playSound(p.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
    }

    public static Inventory confirmRulesInventory = Bukkit.createInventory(null,InventoryType.HOPPER,"§cRegelupdate§8, §estimmst du ihnen zu§8?");
    public static void openConfirmRulesInventory(Player p) {
        confirmRulesInventory.setItem(0, ItemManager.RulesPlaceHolder);
        confirmRulesInventory.setItem(1, ItemManager.RulesPlaceHolder);
        confirmRulesInventory.setItem(2, ItemManager.AcceptRules);
        confirmRulesInventory.setItem(3, ItemManager.RulesPlaceHolder);
        confirmRulesInventory.setItem(4, ItemManager.RulesPlaceHolder);
        p.openInventory(confirmRulesInventory);
        p.playSound(p.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
    }

    public static void openSettingsInventory(Player p) {
        RoleplayUser u = RoleplayAPI.getRoleplayUser(p);
        Inventory settingsInventory = Bukkit.createInventory(null,InventoryType.HOPPER,"§9Einstellungen");
        settingsInventory.setItem(0, ItemManager.Placeholder);
        //settingsInventory.setItem(1, ItemManager.protectionTime(u));
        settingsInventory.setItem(1, ItemManager.Placeholder);
        settingsInventory.setItem(2, ItemManager.automaticRoleplay(u));
        settingsInventory.setItem(3, ItemManager.Placeholder);
        settingsInventory.setItem(4, ItemManager.Placeholder);
        p.openInventory(settingsInventory);
        p.playSound(p.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
    }
}