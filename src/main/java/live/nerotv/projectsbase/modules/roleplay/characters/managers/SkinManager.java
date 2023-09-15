package live.nerotv.projectsbase.modules.roleplay.characters.managers;

import live.nerotv.projectsbase.Main;
import net.skinsrestorer.api.PlayerWrapper;
import net.skinsrestorer.api.SkinVariant;
import net.skinsrestorer.api.SkinsRestorerAPI;
import net.skinsrestorer.api.exception.SkinRequestException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SkinManager {

    public static void setSkin(Player player, String skinURL, SkinVariant variant) throws NullPointerException {
        if(skinURL.contains("__SKINVARIANTSLIM0012__")) {
            variant = SkinVariant.SLIM;
        }
        SkinVariant finalVariant = variant;
        skinURL = skinURL.replace("__SKINVARIANTSLIM0012__","");
        String finalSkinURL = skinURL;
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), (Runnable) () -> {
            try {
                try {
                    try {
                        SkinsRestorerAPI.getApi().applySkin(new PlayerWrapper(player), SkinsRestorerAPI.getApi().genSkinUrl(finalSkinURL, finalVariant));
                    } catch (SkinRequestException ignore1) {}
                } catch (IllegalArgumentException ignore2) {}
            } catch (NullPointerException ignore3) {}
        });
    }
}