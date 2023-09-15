package live.nerotv.projectsbase.modules.roleplay.characters.managers;

import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.modules.roleplay.characters.objects.Character;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.UUID;

public class InventoryManager {

    public static void openCharactersMenu(Player player) {
        Inventory inv;
        if(player.hasPermission("zyneon.leading")) {
            inv = Bukkit.createInventory(null, 54, "Charakterauswahl§8...");
        } else {
            inv = Bukkit.createInventory(null, 27, "Charakterauswahl§8...");
        }
        RoleplayUser u = RoleplayAPI.getRoleplayUser(player);
        ArrayList<String> cids = u.getChars();
        int i = 0;
        for (String cid : cids) {
            i=i+1;
            /*if(i>2) {
                if(player.hasPermission("zyneon.premium")) {
                    cid = cid.replace(" ", "").replace("[", "").replace("]", "").replace(",", "");
                    Character c = new Character(UUID.fromString(cid));
                    ItemStack item = live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.createItem(Material.PLAYER_HEAD, c.getName(), "§8" + c.getUUID().toString(), "§8Drücke SHIFT+Linksklick um den Charakter zu löschen§8!");
                    SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
                    if (u.getActiveChar() != null) {
                        if (cid.equals(u.getActiveChar().getUUID().toString())) {
                            itemMeta.addEnchant(Enchantment.LOYALTY, 1, true);
                            itemMeta.setOwningPlayer(player);
                            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_DYE, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS);
                        }
                    }
                    item.setItemMeta(itemMeta);
                    inv.addItem(item);
                }
            } else {*/
                cid = cid.replace(" ", "").replace("[", "").replace("]", "").replace(",", "");
                Character c = new Character(UUID.fromString(cid));
                ItemStack item = live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.createItem(Material.PLAYER_HEAD, c.getName(), "§8" + c.getUUID().toString(), "§8Drücke SHIFT+Linksklick um den Charakter zu löschen§8!");
                SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
                if (u.getActiveChar() != null) {
                    if (cid.equals(u.getActiveChar().getUUID().toString())) {
                        itemMeta.addEnchant(Enchantment.LOYALTY, 1, true);
                        itemMeta.setOwningPlayer(player);
                        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_DYE, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS);
                    }
                }
                item.setItemMeta(itemMeta);
                inv.addItem(item);
            //}
        }
        inv.addItem(ItemManager.createCharacter());
        if(player.hasPermission("zyneon.leading")) {
            inv.setItem(45, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
            inv.setItem(46, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
            inv.setItem(47, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
            inv.setItem(48, ItemManager.characterSettings(u));
            inv.setItem(49, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
            inv.setItem(50, ItemManager.closeMenu());
            inv.setItem(51, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
            inv.setItem(52, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
            inv.setItem(53, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
        } else {
            inv.setItem(18, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
            inv.setItem(19, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
            inv.setItem(20, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
            inv.setItem(21, ItemManager.characterSettings(u));
            inv.setItem(22, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
            inv.setItem(23, ItemManager.closeMenu());
            inv.setItem(24, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
            inv.setItem(25, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
            inv.setItem(26, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
        }
        player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG,100,100);
        player.openInventory(inv);
    }

    public static void openOutfitsInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, 18, "Outfits§8...");
        RoleplayUser u = RoleplayAPI.getRoleplayUser(player);
        Character c = u.getActiveChar();
        ArrayList<String> outfits = c.getOutfits();
        int l = 0;
        for (String outfit : outfits) {
            l = l + 1;
            /*if (l > 2) {
                if (player.hasPermission("zyneon.premium")) {
                    String name = outfit.substring(outfit.indexOf("-=-OUTFITNAME=")).replace("-=-OUTFITNAME=", "");
                    outfit = outfit.replace(name, "").replace("-=-OUTFITNAME=", "");
                    outfit = outfit.replace(" ", "").replace("__SKINVARIANTSLIM0012__", "").replace("[", "").replace("]", "").replace(",", "");
                    ItemStack item = live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.createItem(Material.PLAYER_HEAD, name, "§8Drücke SHIFT+Linksklick um das Outfit zu löschen§8!");
                    SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
                    if (u.getActiveChar().getSkin() != null) {
                        if (outfit.equals(u.getActiveChar().getSkin())) {
                            itemMeta.addEnchant(Enchantment.LOYALTY, 1, true);
                            itemMeta.setOwningPlayer(player);
                            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_DYE, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS);
                        }
                    }
                    item.setItemMeta(itemMeta);
                    inv.addItem(item);
                }
            } else {*/
                String name = outfit.substring(outfit.indexOf("-=-OUTFITNAME=")).replace("-=-OUTFITNAME=", "");
                outfit = outfit.replace(name, "").replace("-=-OUTFITNAME=", "");
                outfit = outfit.replace(" ", "").replace("__SKINVARIANTSLIM0012__", "").replace("[", "").replace("]", "").replace(",", "");
                ItemStack item = live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.createItem(Material.PLAYER_HEAD, name, "§8Drücke SHIFT+Linksklick um das Outfit zu löschen§8!");
                SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
                if (u.getActiveChar().getSkin() != null) {
                    if (outfit.equals(u.getActiveChar().getSkin())) {
                        itemMeta.addEnchant(Enchantment.LOYALTY, 1, true);
                        itemMeta.setOwningPlayer(player);
                        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_DYE, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS);
                    }
                }
                item.setItemMeta(itemMeta);
                inv.addItem(item);
            //}
        }
        inv.addItem(ItemManager.addOutfit());
        inv.setItem(8, ItemManager.skinVariant(c));
        inv.setItem(9, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
        inv.setItem(10, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
        inv.setItem(11, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
        inv.setItem(12, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
        inv.setItem(13, ItemManager.characters2());
        inv.setItem(14, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
        inv.setItem(15, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
        inv.setItem(16, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
        inv.setItem(17, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
        player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
        player.openInventory(inv);
    }

    public static void openCharacterSettings(Player player) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST,"Charakter anpassen§8...");
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
        }
        inv.setItem(10,ItemManager.changeName(player));
        inv.setItem(11,ItemManager.changeJob(player));
        inv.setItem(12,ItemManager.changeAge(player));
        if(RoleplayAPI.getRoleplayUser(player).getActiveChar().getOutfits().size()>0) {
            inv.setItem(13, ItemManager.outfitMenu());
        } else {
            inv.setItem(13, ItemManager.changeSkin(player));
        }
        inv.setItem(15,ItemManager.description(player));
        inv.setItem(16,ItemManager.characters());
        player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG,100,100);
        player.openInventory(inv);
    }

    public static void openDeleteCharacterMenu(Player player, Character character) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER,"Willst du "+character.getName()+" löschen§8?");
        inv.setItem(0, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
        inv.setItem(1, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
        inv.setItem(2, ItemManager.deleteChar(character,character.getUUID().toString()));
        inv.setItem(3, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
        inv.setItem(4, live.nerotv.projectsbase.modules.roleplay.manager.ItemManager.Placeholder);
        player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG,100,100);
        player.openInventory(inv);
    }
}