package live.nerotv.projectsbase.modules.essentials.manager;

import live.nerotv.projectsbase.modules.roleplay.manager.ItemManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CustomItemManager {

    public static ItemStack getCustomItem(String name) {
        if(name.equalsIgnoreCase("KNIFE")) {
            return KNIFE;
        } else if(name.equalsIgnoreCase("SHOTGUN")) {
            return SHOTGUN;
        } else if(name.equalsIgnoreCase("REVOLVER")) {
            return REVOLVER;
        } else if(name.equalsIgnoreCase("GOLDEN_SHOTGUN")||name.equalsIgnoreCase("SHOTGUN_GOLDEN")||name.equalsIgnoreCase("UNBREAKABLE_SHOTGUN")||name.equalsIgnoreCase("SHOTGUN_UNBREAKABLE")) {
            return GOLDEN_SHOTGUN;
        } else if(name.equalsIgnoreCase("GOLDEN_REVOLVER")||name.equalsIgnoreCase("REVOLVER_GOLDEN")||name.equalsIgnoreCase("UNBREAKABLE_REVOLVER")||name.equalsIgnoreCase("REVOLVER_UNBREAKABLE")) {
            return GOLDEN_REVOLVER;
        } else if(name.equalsIgnoreCase("GOLDEN_MARKSMAN_PISTOL")||name.equalsIgnoreCase("MARKSMAN_PISTOL_GOLDEN")||name.equalsIgnoreCase("UNBREAKABLE_MARKSMAN_PISTOL")||name.equalsIgnoreCase("MARKSMAN_PISTOL_UNBREAKABLE")) {
            return GOLDEN_MARKSMAN_PISTOL;
        } else if(name.equalsIgnoreCase("MARKSMAN_PISTOL")) {
            return MARKSMAN_PISTOL;
        } else if(name.equalsIgnoreCase("SLINGSHOT")) {
            return SLINGSHOT;
        } else if(name.equalsIgnoreCase("AMMO")) {
            return AMMO;
        } else if(name.equalsIgnoreCase("SALOON_DOOR")) {
            return SALOON_DOOR;
        } else if(name.equalsIgnoreCase("DARK_SALOON_DOOR")) {
            return DARK_SALOON_DOOR;
        } else if(name.equalsIgnoreCase("BLACK_REVOLVER")||name.equalsIgnoreCase("REVOLVER_BLACK")) {
            return BLACK_REVOLVER;
        } else if(name.equalsIgnoreCase("BATTLE_AXE")) {
            return BATTLE_AXE;
        } else {
            return null;
        }
    }

    public static ItemStack KNIFE = ItemManager.knifeItem();
    public static ItemStack BATTLE_AXE = ItemManager.battleAxeItem();
    public static ItemStack REVOLVER = new ItemStack(Material.CROSSBOW);
    public static ItemStack MARKSMAN_PISTOL = ItemManager.marksmanPistolItem();
    public static ItemStack GOLDEN_MARKSMAN_PISTOL = ItemManager.goldenMarksmanPistolItem();
    public static ItemStack GOLDEN_REVOLVER = ItemManager.goldenRevolverItem();
    public static ItemStack GOLDEN_SHOTGUN = ItemManager.goldenShotgunItem();
    public static ItemStack SHOTGUN = ItemManager.shotgunItem();
    public static ItemStack SLINGSHOT = new ItemStack(Material.BOW);
    public static ItemStack AMMO = new ItemStack(Material.ARROW);
    public static ItemStack DARK_SALOON_DOOR = new ItemStack(Material.CRIMSON_DOOR);
    public static ItemStack BLACK_REVOLVER = ItemManager.blackRevolverItem();
    public static ItemStack SALOON_DOOR = new ItemStack(Material.WARPED_DOOR);
}