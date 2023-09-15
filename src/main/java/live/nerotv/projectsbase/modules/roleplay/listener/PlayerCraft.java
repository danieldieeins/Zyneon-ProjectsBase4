package live.nerotv.projectsbase.modules.roleplay.listener;

import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.modules.roleplay.manager.ItemManager;
import live.nerotv.projectsbase.modules.essentials.manager.StaticManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import static org.bukkit.Material.*;

public class PlayerCraft implements Listener {

    @EventHandler
    public void onCraft(PrepareItemCraftEvent e) {
        if(StaticManager.projectileGlassBreak) {
            if(e.getRecipe()!=null) {
                if(e.getRecipe().getResult().getType().equals(Material.CROSSBOW)) {
                    ItemManager.blockCrafting(e);
                } else if(e.getRecipe().getResult().getType().equals(Material.ARROW)) {
                    ItemManager.blockCrafting(e);
                } else if(e.getRecipe().getResult().getType().equals(Material.TNT)) {
                    ItemManager.blockCrafting(e);
                } else if(e.getRecipe().getResult().getType().equals(Material.WARPED_DOOR)) {
                    if(e.getInventory().contains(Material.WARPED_PLANKS)) {
                        ItemManager.blockCrafting(e);
                    }
                } else if(e.getRecipe().getResult().getType().equals(Material.CRIMSON_DOOR)) {
                    if(e.getInventory().contains(Material.CRIMSON_PLANKS)) {
                        ItemManager.blockCrafting(e);
                    }
                } else if(e.getRecipe().getResult().getType().equals(Material.BOW)) {
                    ItemManager.blockCrafting(e);
                }
            }
        }
    }

    @EventHandler
    public void onEnchant(PrepareItemEnchantEvent e) {
        if(!RoleplayAPI.isStarted) {
            e.setCancelled(true);
            return;
        }
        if(StaticManager.projectileGlassBreak) {
            if(e.getItem().getType().equals(CROSSBOW)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onAnvil(PrepareAnvilEvent e) {
        if(StaticManager.projectileGlassBreak) {
            if(e.getInventory().getItem(0)!=null&&e.getInventory().getItem(1)!=null) {
                if(e.getInventory().getItem(1).getType().equals(ENCHANTED_BOOK) && e.getInventory().getItem(0).getType().equals(CROSSBOW)) {
                    EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta)e.getInventory().getItem(1).getItemMeta();
                    if(enchantmentStorageMeta.hasStoredEnchant(Enchantment.MULTISHOT)) {
                        e.setResult(new ItemStack(AIR));
                    }
                }
            }
        }
    }
}