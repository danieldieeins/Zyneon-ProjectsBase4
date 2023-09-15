package live.nerotv.projectsbase.modules.roleplay.manager;

import live.nerotv.projectsbase.Main;
import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.api.ConfigAPI;
import live.nerotv.projectsbase.modules.essentials.manager.CustomItemManager;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemManager {

    public static ItemStack createItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore((List) Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    public static boolean checkItemStack(String item) {
        item = item.toUpperCase().replace("-", "_");
        ItemStack itemStack;
        try {
            itemStack = new ItemStack(Material.getMaterial(item));
            return true;
        } catch (IllegalArgumentException e) {
            try {
                if (CustomItemManager.getCustomItem(item) == null) {
                    return false;
                }
                itemStack = CustomItemManager.getCustomItem(item);
                return true;
            } catch (IllegalArgumentException e2) {
                return false;
            }
        }
    }

    public static void addCraftings() {
        if(ConfigAPI.CFG.getBoolean("Core.Settings.CustomItems.IRON_KNIFE")) {
            addIronKnifeCrafting();
        }
        if(ConfigAPI.CFG.getBoolean("Core.Settings.CustomItems.BATTLE_AXE")) {
            addBattleAxeCrafting();
        }
        if(ConfigAPI.CFG.getBoolean("Core.Settings.CustomItems.SALOON_DOOR")) {
            addSaloonDoorCrafting();
        }
        if(ConfigAPI.CFG.getBoolean("Core.Settings.CustomItems.DARK_SALOON_DOOR")) {
            addDarkSaloonDoorCrafting();
        }
        addSkeletonSkullCrafting0();
        addSkeletonSkullCrafting1();
        addSkeletonSkullCrafting2();
    }

    private static void addSkeletonSkullCrafting0() {
        NamespacedKey key = new NamespacedKey(Main.getInstance(),"skull_skeleton");
        ShapelessRecipe recipe = new ShapelessRecipe(key,new ItemStack(Material.SKELETON_SKULL));
        recipe.addIngredient(Material.BONE_MEAL);
        recipe.addIngredient(Material.BONE_MEAL);
        recipe.addIngredient(Material.BONE_MEAL);
        recipe.addIngredient(Material.BONE_MEAL);
        recipe.addIngredient(Material.WITHER_SKELETON_SKULL);
        recipe.addIngredient(Material.BONE_MEAL);
        recipe.addIngredient(Material.BONE_MEAL);
        recipe.addIngredient(Material.BONE_MEAL);
        recipe.addIngredient(Material.BONE_MEAL);
        Bukkit.addRecipe(recipe);
    }

    private static void addSkeletonSkullCrafting1() {
        NamespacedKey key = new NamespacedKey(Main.getInstance(),"skull_skeleton_one");
        ShapelessRecipe recipe = new ShapelessRecipe(key,new ItemStack(Material.SKELETON_SKULL));
        recipe.addIngredient(Material.WHITE_DYE);
        recipe.addIngredient(Material.WHITE_DYE);
        recipe.addIngredient(Material.WHITE_DYE);
        recipe.addIngredient(Material.WHITE_DYE);
        recipe.addIngredient(Material.WITHER_SKELETON_SKULL);
        recipe.addIngredient(Material.WHITE_DYE);
        recipe.addIngredient(Material.WHITE_DYE);
        recipe.addIngredient(Material.WHITE_DYE);
        recipe.addIngredient(Material.WHITE_DYE);
        Bukkit.addRecipe(recipe);
    }

    private static void addSkeletonSkullCrafting2() {
        NamespacedKey key = new NamespacedKey(Main.getInstance(),"skull_skeleton_two");
        ShapelessRecipe recipe = new ShapelessRecipe(key,new ItemStack(Material.SKELETON_SKULL));
        recipe.addIngredient(Material.WITHER_SKELETON_SKULL);
        recipe.addIngredient(Material.BONE);
        recipe.addIngredient(Material.BONE);
        recipe.addIngredient(Material.BONE);
        Bukkit.addRecipe(recipe);
    }

    private static void addSaloonDoorCrafting() {
        NamespacedKey key = new NamespacedKey(Main.getInstance(),"saloon_door_zero");
        ShapedRecipe recipe = new ShapedRecipe(key,new ItemStack(CustomItemManager.SALOON_DOOR.getType()));
        recipe.shape("IW");
        recipe.setIngredient('I',Material.IRON_INGOT);
        recipe.setIngredient('W',Material.OAK_DOOR);
        Bukkit.addRecipe(recipe);
    }

    private static void addDarkSaloonDoorCrafting() {
        NamespacedKey key = new NamespacedKey(Main.getInstance(),"dark_saloon_door");
        ShapedRecipe recipe = new ShapedRecipe(key,new ItemStack(CustomItemManager.DARK_SALOON_DOOR.getType()));
        recipe.shape("IW");
        recipe.setIngredient('I',Material.IRON_INGOT);
        recipe.setIngredient('W',Material.SPRUCE_DOOR);
        Bukkit.addRecipe(recipe);
    }

    private static void addIronKnifeCrafting() {
        NamespacedKey key = new NamespacedKey(Main.getInstance(),"iron_knife");
        ShapedRecipe recipe = new ShapedRecipe(key,knifeItem());
        recipe.shape("I","S");
        recipe.setIngredient('I',Material.IRON_INGOT);
        recipe.setIngredient('S',Material.STICK);
        Bukkit.addRecipe(recipe);
    }

    private static void addBattleAxeCrafting() {
        NamespacedKey key = new NamespacedKey(Main.getInstance(),"battle_axe");
        ShapedRecipe recipe = new ShapedRecipe(key,battleAxeItem());
        recipe.shape("NI*","CS*","*S*");
        battleAxe(recipe);
        alternativeBattleAxeCrafting();
    }

    private static void alternativeBattleAxeCrafting() {
        NamespacedKey key = new NamespacedKey(Main.getInstance(),"battle_axe_2");
        ShapedRecipe recipe = new ShapedRecipe(key,battleAxeItem());
        recipe.shape("IN*","CS*","*S*");
        battleAxe(recipe);
    }

    public static void blockCrafting(PrepareItemCraftEvent e) {
        e.getInventory().setResult(new ItemStack(Material.AIR));
        for (HumanEntity p : e.getViewers()) {
            if (p instanceof Player) {
                API.sendErrorMessage((Player) p, "§cDu kannst das so nicht bekommen§8!");
            }
        }
    }

    private static void battleAxe(ShapedRecipe recipe) {
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('I',Material.IRON_INGOT);
        recipe.setIngredient('S',Material.STICK);
        recipe.setIngredient('*',Material.AIR);
        Bukkit.addRecipe(recipe);
    }

    public static void fillChestInventory(Inventory inv,ItemStack i1,ItemStack i2,ItemStack i3,ItemStack i4,ItemStack i5,ItemStack i6,ItemStack i7,ItemStack i8,ItemStack i9,ItemStack i10,ItemStack i11,ItemStack i12,ItemStack i13,ItemStack i14,ItemStack i15,ItemStack i16,ItemStack i17,ItemStack i18) {
        inv.setItem(0,i1);inv.setItem(1,i2);inv.setItem(2,i3);inv.setItem(3,i4);inv.setItem(4,i5);inv.setItem(5,i6);inv.setItem(6,i7);inv.setItem(7,i8);inv.setItem(8,i9);
        inv.setItem(9,i10);inv.setItem(10,i11);inv.setItem(11,i12);inv.setItem(12,i13);inv.setItem(13,i14);inv.setItem(14,i15);inv.setItem(15,i16);inv.setItem(16,i17);inv.setItem(17,i18);
        inv.setItem(18,Placeholder);
        inv.setItem(19,Placeholder);
        inv.setItem(20,Placeholder);
        inv.setItem(21,Placeholder);
        inv.setItem(22,Close);
        inv.setItem(23,Placeholder);
        inv.setItem(24,Placeholder);
        inv.setItem(25,Placeholder);
        inv.setItem(26,Placeholder);
    }

    public static void fillShopInventory(Inventory inv,ItemStack i1,ItemStack i2,ItemStack i3,ItemStack i4,ItemStack i5,ItemStack i6,ItemStack i7,ItemStack i8,ItemStack i9) {
        inv.setItem(0,i1);inv.setItem(1,i2);inv.setItem(2,i3);inv.setItem(3,i4);inv.setItem(4,i5);inv.setItem(5,i6);inv.setItem(6,i7);inv.setItem(7,i8);inv.setItem(8,i9);
        inv.setItem(9,Placeholder);
        inv.setItem(10,Placeholder);
        inv.setItem(11,Placeholder);
        inv.setItem(12,Placeholder);
        inv.setItem(13,Close);
        inv.setItem(14,Placeholder);
        inv.setItem(15,Placeholder);
        inv.setItem(16,Placeholder);
        inv.setItem(17,Placeholder);
    }

    public static ItemStack Placeholder = createItem(Material.BLACK_STAINED_GLASS_PANE,"§0","§0");
    public static ItemStack Placeholder2 = createItem(Material.RED_STAINED_GLASS_PANE,"§cKommt noch...","§0");
    public static ItemStack Close = createItem(Material.BARRIER,"§cSchließen");
    public static ItemStack WarpBack = createItem(Material.DARK_OAK_DOOR,"§cZurück§0");
    public static ItemStack ColorBack = createItem(Material.DARK_OAK_DOOR,"§cZurück§1");

    public static ItemStack WarpC1 = createItem(Material.getMaterial(ConfigAPI.CFG.getString("Core.WarpGUI.Items.StartMenu01")),ConfigAPI.CFG.getString("Core.WarpGUI.Names.StartMenu01"));
    public static ItemStack WarpC2 = createItem(Material.getMaterial(ConfigAPI.CFG.getString("Core.WarpGUI.Items.StartMenu02")),ConfigAPI.CFG.getString("Core.WarpGUI.Names.StartMenu02"));
    public static ItemStack WarpETC = createItem(Material.getMaterial(ConfigAPI.CFG.getString("Core.WarpGUI.Items.StartMenu03")),ConfigAPI.CFG.getString("Core.WarpGUI.Names.StartMenu03"));
    public static ItemStack WarpC101 = createItem(Material.getMaterial(ConfigAPI.CFG.getString("Core.WarpGUI.Items.Sub1Menu01")),ConfigAPI.CFG.getString("Core.WarpGUI.Names.Sub1Menu01"));
    public static ItemStack WarpC201 = createItem(Material.getMaterial(ConfigAPI.CFG.getString("Core.WarpGUI.Items.Sub2Menu01")),ConfigAPI.CFG.getString("Core.WarpGUI.Names.Sub2Menu01"));
    public static ItemStack WarpETC01 = createItem(Material.getMaterial(ConfigAPI.CFG.getString("Core.WarpGUI.Items.Sub3Menu01")),ConfigAPI.CFG.getString("Core.WarpGUI.Names.Sub3Menu01"));
    public static ItemStack WarpC102 = createItem(Material.getMaterial(ConfigAPI.CFG.getString("Core.WarpGUI.Items.Sub1Menu02")),ConfigAPI.CFG.getString("Core.WarpGUI.Names.Sub1Menu02"));
    public static ItemStack WarpC202 = createItem(Material.getMaterial(ConfigAPI.CFG.getString("Core.WarpGUI.Items.Sub2Menu02")),ConfigAPI.CFG.getString("Core.WarpGUI.Names.Sub2Menu02"));
    public static ItemStack WarpETC02 = createItem(Material.getMaterial(ConfigAPI.CFG.getString("Core.WarpGUI.Items.Sub3Menu02")),ConfigAPI.CFG.getString("Core.WarpGUI.Names.Sub3Menu02"));

    public static ItemStack ElytraReward = createItem(Material.ELYTRA,"§bUnkaputtbare Elytren","§915.000 AP");
    public static ItemStack RewardMark001 = createItem(Material.GOLD_NUGGET,"§a1 Mark","§92 AP");
    public static ItemStack RewardMark002 = createItem(Material.GOLD_NUGGET,"§a2 Mark","§94 AP");
    public static ItemStack RewardMark005 = createItem(Material.PAPER,"§a5 Mark","§910 AP");
    public static ItemStack RewardMark010 = createItem(Material.PAPER,"§910 Mark","§920 AP");
    public static ItemStack RewardMark020 = createItem(Material.PAPER,"§e20 Mark","§940 AP");
    public static ItemStack RewardMark050 = createItem(Material.PAPER,"§c50 Mark","§9100 AP");
    public static ItemStack RewardMark100 = createItem(Material.PAPER,"§d100 Mark","§9200 AP");
    public static ItemStack RewardMark200 = createItem(Material.PAPER,"§2200 Mark","§9400 AP");
    public static ItemStack RewardMark500 = createItem(Material.PAPER,"§4500 Mark","§91.000 AP");
    public static ItemStack Mark001 = createItem(Material.GOLD_NUGGET,"§a§o1 Mark");
    public static ItemStack Mark002 = createItem(Material.GOLD_NUGGET,"§a§o2 Mark");
    public static ItemStack Mark005 = createItem(Material.PAPER,"§a§o5 Mark");
    public static ItemStack Mark010 = createItem(Material.PAPER,"§9§o10 Mark");
    public static ItemStack Mark020 = createItem(Material.PAPER,"§e§o20 Mark");
    public static ItemStack Mark050 = createItem(Material.PAPER,"§c§o50 Mark");
    public static ItemStack Mark100 = createItem(Material.PAPER,"§d§o100 Mark");
    public static ItemStack Mark200 = createItem(Material.PAPER,"§2§o200 Mark");
    public static ItemStack Mark500 = createItem(Material.PAPER,"§4§o500 Mark");
    public static ItemStack NetherReward = createItem(Material.NETHERITE_INGOT,"§cNetherit","§92.500 AP");

    public static ItemStack RevolverReward() {
        ItemStack Return = goldenRevolverItem();
        ItemMeta returnMeta = Return.getItemMeta();
        returnMeta.setDisplayName("§6Unkaputtbarer Revolver");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§925.000 AP");
        returnMeta.setLore(lore);
        returnMeta.setUnbreakable(false);
        Return.setItemMeta(returnMeta);
        return Return;
    }

    public static ItemStack RocketReward() {
        ItemStack Reward = createItem(Material.FIREWORK_ROCKET,"§r§fRaketen","§9100 AP");
        Reward.setAmount(64);
        return Reward;
    }

    public static ItemStack BottleReward() {
        ItemStack Reward = createItem(Material.EXPERIENCE_BOTTLE,"XP-Flasche","§9500 AP");
        Reward.setAmount(32);
        return Reward;
    }
    public static ItemStack CoalReward() {
        ItemStack Reward = createItem(Material.COAL,"§fKohle§r §r","§9500 AP");
        Reward.setAmount(32);
        return Reward;
    }
    public static ItemStack GoldReward() {
        ItemStack Reward = createItem(Material.GOLD_INGOT,"§6Gold","§9500 AP");
        Reward.setAmount(8);
        return Reward;
    }
    public static ItemStack IronReward() {
        ItemStack Reward = createItem(Material.IRON_INGOT,"§7Eisen","§9600 AP");
        Reward.setAmount(8);
        return Reward;
    }
    public static ItemStack LapisReward() {
        ItemStack Reward = createItem(Material.LAPIS_LAZULI,"§1Lapis-Lazuli","§9700 AP");
        Reward.setAmount(16);
        return Reward;
    }
    public static ItemStack DiamondReward() {
        ItemStack Reward = createItem(Material.DIAMOND,"§bDiamanten","§9900 AP");
        Reward.setAmount(2);
        return Reward;
    }

    public static ItemStack ReloadYes = createItem(
            Material.GREEN_CONCRETE,
            "§aJa§r"
    );

    public static ItemStack Cancel = createItem(
            Material.RED_CONCRETE,
            "§cNein§r"
    );

    public static ItemStack StopYes = createItem(
            Material.GREEN_CONCRETE,
            "§aJa§2"
    );

    public static ItemStack GameModeSurvival = createItem(
            Material.APPLE,
            "§bÜberlebensmodus§r"
    );

    public static ItemStack GameModeCreative = createItem(
            Material.GOLDEN_APPLE,
            "§bKreativmodus§r"
    );

    public static ItemStack GameModeAdventure = createItem(
            Material.STICK,
            "§bAbenteuermodus§r"
    );

    public static ItemStack GameModeSpectator = createItem(
            Material.DIAMOND_HELMET,
            "§bZuschauermodus§r"
    );

    public static ItemStack RulesPlaceHolder = createItem(
            Material.PAPER,
            "§bRegeln auf:",
            "§9§n"+ConfigAPI.CFG.getString("Core.Strings.Projektregeln")
    );

    public static ItemStack AcceptRules = createItem(
            Material.GREEN_CONCRETE,
            "Mit dem Klicken bestätige ich, dass:",
            "§a- ich die Regeln gelesen habe,",
            "§a- ich den Regeln zustimme,",
            "§a- ich verspreche mich an die Regeln zu halten",
            "§7und dass",
            "§b- ich der §9Nerofy.de§b Datenschutzerklärung zustimme."
    );

    public static ItemStack automaticRoleplay(RoleplayUser u) {
        ItemStack Return;
        if(u.isAutomaticRoleplay()) {
            Return = createItem(Material.ENDER_EYE,"§aAutomatisches Roleplay","§7Klicke zum Deaktivieren§8.");
        } else {
            Return = createItem(Material.ENDER_PEARL,"§cAutomatisches Roleplay","§7Klicke zum Aktivieren§8.");
        }
        return Return;
    }

    public static ItemStack protectionTime(RoleplayUser u) {
        ItemStack Return;
        if(u.getProtectionTime() == 0) {
            Return = createItem(Material.RED_DYE,"§cSpawnschutzzeit §8(0 Sekunden)","§7Klicke zum Ändern§8.");
        } else if(u.getProtectionTime() == 15) {
            Return = createItem(Material.YELLOW_DYE,"§eSpawnschutzzeit §8(15 Sekunden)","§7Klicke zum Ändern§8.");
        } else {
            Return = createItem(Material.LIME_DYE,"§aSpawnschutzzeit §8(30 Sekunden)","§7Klicke zum Ändern§8.");
        }
        return Return;
    }

    public static ItemStack knifeItem() {
        ItemStack knife = new ItemStack(Material.STONE_SWORD);
        ItemMeta knifeMeta = knife.getItemMeta();
        knifeMeta.setDisplayName("§rMesser");
        knifeMeta.setCustomModelData(1);
        knife.setItemMeta(knifeMeta);
        return knife;
    }

    public static ItemStack battleAxeItem() {
        ItemStack battleAxe = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta battleAxeMeta = battleAxe.getItemMeta();
        battleAxeMeta.setDisplayName("§rBeil");
        battleAxeMeta.setCustomModelData(1);
        battleAxe.setItemMeta(battleAxeMeta);
        return battleAxe;
    }

    public static ItemStack shotgunItem() {
        ItemStack shotgun = new ItemStack(Material.CROSSBOW);
        ItemMeta shotgunMeta = shotgun.getItemMeta();
        shotgunMeta.setDisplayName("§rSchrotflinte");
        shotgunMeta.setCustomModelData(1);
        shotgunMeta.addEnchant(Enchantment.MULTISHOT,1,false);
        shotgunMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        shotgun.setItemMeta(shotgunMeta);
        return shotgun;
    }

    public static ItemStack goldenRevolverItem() {
        ItemStack shotgun = new ItemStack(Material.CROSSBOW);
        ItemMeta shotgunMeta = shotgun.getItemMeta();
        shotgunMeta.setDisplayName("§6Goldener Revolver");
        shotgunMeta.setCustomModelData(2);
        shotgunMeta.setUnbreakable(true);
        shotgun.setItemMeta(shotgunMeta);
        return shotgun;
    }

    public static ItemStack goldenShotgunItem() {
        ItemStack shotgun = new ItemStack(Material.CROSSBOW);
        ItemMeta shotgunMeta = shotgun.getItemMeta();
        shotgunMeta.setDisplayName("§6Goldene Schrotflinte");
        shotgunMeta.setCustomModelData(3);
        shotgunMeta.setUnbreakable(true);
        shotgunMeta.addEnchant(Enchantment.MULTISHOT,1,false);
        shotgunMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        shotgun.setItemMeta(shotgunMeta);
        return shotgun;
    }

    public static ItemStack blackRevolverReward() {
        ItemStack reward = blackRevolverItem();
        ItemMeta rewardMeta = reward.getItemMeta();
        rewardMeta.setDisplayName("§7Schwarzer Revolver");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§910.000 AP");
        rewardMeta.setLore(lore);
        rewardMeta.setUnbreakable(false);
        reward.setItemMeta(rewardMeta);
        return reward;
    }

    public static ItemStack marksmanReward() {
        ItemStack reward = goldenMarksmanPistolItem();
        ItemMeta rewardMeta = reward.getItemMeta();
        rewardMeta.setDisplayName("§6Unkaputtbare Präzisionspistole");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§980.000 AP");
        rewardMeta.setLore(lore);
        rewardMeta.setUnbreakable(false);
        reward.setItemMeta(rewardMeta);
        return reward;
    }

    public static ItemStack shotgunReward() {
        ItemStack reward = goldenShotgunItem();
        ItemMeta rewardMeta = reward.getItemMeta();
        rewardMeta.setDisplayName("§6Unkaputtbare Schrotflinte");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§940.000 AP");
        rewardMeta.setLore(lore);
        rewardMeta.setUnbreakable(false);
        reward.setItemMeta(rewardMeta);
        return reward;
    }

    public static ItemStack marksmanPistolItem() {
        ItemStack shotgun = new ItemStack(Material.CROSSBOW);
        ItemMeta shotgunMeta = shotgun.getItemMeta();
        shotgunMeta.setDisplayName("§ePräzisionspistole");
        shotgunMeta.addEnchant(Enchantment.PIERCING,4,false);
        shotgunMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        shotgunMeta.setCustomModelData(4);
        shotgun.setItemMeta(shotgunMeta);
        return shotgun;
    }

    public static ItemStack goldenMarksmanPistolItem() {
        ItemStack shotgun = new ItemStack(Material.CROSSBOW);
        ItemMeta shotgunMeta = shotgun.getItemMeta();
        shotgunMeta.setUnbreakable(true);
        shotgunMeta.setDisplayName("§6Goldene Präzisionspistole");
        shotgunMeta.setCustomModelData(5);
        shotgunMeta.addEnchant(Enchantment.PIERCING,4,false);
        shotgunMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        shotgun.setItemMeta(shotgunMeta);
        return shotgun;
    }

    public static ItemStack blackRevolverItem() {
        ItemStack shotgun = new ItemStack(Material.CROSSBOW);
        ItemMeta shotgunMeta = shotgun.getItemMeta();
        shotgunMeta.setDisplayName("§rSchwarzer Revolver");
        shotgunMeta.setCustomModelData(6);
        shotgun.setItemMeta(shotgunMeta);
        return shotgun;
    }

    public static ItemStack backItem = createItem(
            Material.SLIME_BALL,
            "§aAktionsmenü"
    );
}