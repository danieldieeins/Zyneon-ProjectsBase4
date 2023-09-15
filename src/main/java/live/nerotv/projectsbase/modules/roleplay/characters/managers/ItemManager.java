package live.nerotv.projectsbase.modules.roleplay.characters.managers;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.modules.roleplay.characters.objects.Character;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;

public class ItemManager {

    public static ItemStack changeName(Player player) {
        ItemStack item = live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.createItem(Material.NAME_TAG, "§aNamen ändern");
        ItemMeta itemMeta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        RoleplayUser u = RoleplayAPI.getRoleplayUser(player);
        if(u.getActiveChar()!=null) {
            lore.add("§8" + u.getActiveChar().getName());
        }
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_DESTROYS,ItemFlag.HIDE_DYE,ItemFlag.HIDE_PLACED_ON,ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack changeJob(Player player) {
        ItemStack item = live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.createItem(Material.NETHERITE_PICKAXE,"§aBeruf ändern");
        ItemMeta itemMeta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        RoleplayUser u = RoleplayAPI.getRoleplayUser(player);
        if(u.getActiveChar()!=null) {
            lore.add("§8" + u.getActiveChar().getJob());
        }
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_DESTROYS,ItemFlag.HIDE_DYE,ItemFlag.HIDE_PLACED_ON,ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack changeAge(Player player) {
        ItemStack item = live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.createItem(Material.PAPER,"§aAlter ändern");
        ItemMeta itemMeta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        RoleplayUser u = RoleplayAPI.getRoleplayUser(player);
        if(u.getActiveChar()!=null) {
            lore.add("§8" + u.getActiveChar().getAge());
        }
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_DESTROYS,ItemFlag.HIDE_DYE,ItemFlag.HIDE_PLACED_ON,ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack description(Player player) {
        ItemStack item = live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.createItem(Material.FILLED_MAP,"§aBeschreibung");
        ItemMeta itemMeta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        RoleplayUser u = RoleplayAPI.getRoleplayUser(player);
        if(u.getActiveChar()!=null) {
            Character c = u.getActiveChar();
            lore.add("§f" + c.getName() + "§8,");
            lore.add("§f" + c.getAge() + "§7 Jahre alt§8,");
            lore.add("§7arbeitet als §f" + c.getJob());
        }
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_DESTROYS,ItemFlag.HIDE_DYE,ItemFlag.HIDE_PLACED_ON,ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack changeSkin(Player player) {
        ItemStack item = live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.createItem(Material.PLAYER_HEAD, "§aOutfit erstellen");
        SkullMeta itemMeta = (SkullMeta)item.getItemMeta();
        itemMeta.setOwningPlayer(player);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§8Mittels direkter .png-URL");
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_DESTROYS,ItemFlag.HIDE_DYE,ItemFlag.HIDE_PLACED_ON,ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(itemMeta);
        return item;

    }

    public static ItemStack characters() {
        ItemStack item = live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.createItem(Material.EMERALD,"§aZurück...");
        ItemMeta itemMeta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§8...zur Charakterauswahl");
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_DESTROYS,ItemFlag.HIDE_DYE,ItemFlag.HIDE_PLACED_ON,ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack characters2() {
        ItemStack item = live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.createItem(Material.EMERALD,"§aCharaktereinstellungen...");
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_DESTROYS,ItemFlag.HIDE_DYE,ItemFlag.HIDE_PLACED_ON,ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack addOutfit() {
        ItemStack item = live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.createItem(Material.PLAYER_HEAD,"§aNeues Outfit erstellen","§8Erstelle ein neues Outfit");
        SkullMeta itemMeta = (SkullMeta)item.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_DESTROYS,ItemFlag.HIDE_DYE,ItemFlag.HIDE_PLACED_ON,ItemFlag.HIDE_POTION_EFFECTS);
        setSkinViaBase64(itemMeta,"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkZDIwYmU5MzUyMDk0OWU2Y2U3ODlkYzRmNDNlZmFlYjI4YzcxN2VlNmJmY2JiZTAyNzgwMTQyZjcxNiJ9fX0=");
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack outfitMenu() {
        ItemStack item = live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.createItem(Material.PLAYER_HEAD,"§aOutfits","§8Verwalte deine Outfits");
        SkullMeta itemMeta = (SkullMeta)item.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_DESTROYS,ItemFlag.HIDE_DYE,ItemFlag.HIDE_PLACED_ON,ItemFlag.HIDE_POTION_EFFECTS);
        setSkinViaBase64(itemMeta,"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDVjNmRjMmJiZjUxYzM2Y2ZjNzcxNDU4NWE2YTU2ODNlZjJiMTRkNDdkOGZmNzE0NjU0YTg5M2Y1ZGE2MjIifX19");
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack createCharacter() {
        ItemStack item = live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.createItem(Material.PLAYER_HEAD,"§aCharakter erstellen","§8Erstellt einen neuen Charakter");
        SkullMeta itemMeta = (SkullMeta)item.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_DESTROYS,ItemFlag.HIDE_DYE,ItemFlag.HIDE_PLACED_ON,ItemFlag.HIDE_POTION_EFFECTS);
        setSkinViaBase64(itemMeta,"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkZDIwYmU5MzUyMDk0OWU2Y2U3ODlkYzRmNDNlZmFlYjI4YzcxN2VlNmJmY2JiZTAyNzgwMTQyZjcxNiJ9fX0=");
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack characterSettings(RoleplayUser u) {
        ItemStack item = live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.createItem(Material.COMPARATOR, "§r§5Aktuellen Charakter anpassen");
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(null);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_DESTROYS,ItemFlag.HIDE_DYE,ItemFlag.HIDE_PLACED_ON,ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack closeMenu() {
        ItemStack item = live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.createItem(Material.BARRIER, "§r§5Menü schließen");
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(null);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_DESTROYS,ItemFlag.HIDE_DYE,ItemFlag.HIDE_PLACED_ON,ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack deleteChar(Character character,String UUID) {
        ItemStack item = live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.createItem(Material.RED_DYE, "§c"+character.getName()+" löschen",UUID);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_DESTROYS,ItemFlag.HIDE_DYE,ItemFlag.HIDE_PLACED_ON,ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack skinVariant(Character c) {
        ItemStack item = live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.createItem(Material.COMPARATOR, "§aSkintyp wechseln","§8"+c.getVariant().toString());
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_DESTROYS,ItemFlag.HIDE_DYE,ItemFlag.HIDE_PLACED_ON,ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static void setSkinViaBase64(SkullMeta meta, String base64) {
        try {
            Method setProfile = meta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            setProfile.setAccessible(true);
            GameProfile profile = new GameProfile(UUID.randomUUID(), "skull-texture");
            profile.getProperties().put("textures", new Property("textures", base64));
            setProfile.invoke(meta, profile);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}