package com.buuz135.mhud;



import com.buuz135.mhud.testing.CustomTickingSystem;
import com.hypixel.hytale.protocol.packets.interface_.CustomHud;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.PlayerRef;


import javax.annotation.Nonnull;
import java.util.HashMap;


public class MultipleHUD extends JavaPlugin {

    private static MultipleHUD instance;

    public static MultipleHUD getInstance() {
        return instance;
    }

    public MultipleHUD(@Nonnull JavaPluginInit init) {
        super(init);
        instance = this;
    }

    @Override
    protected void setup() {
        super.setup();
        this.getEntityStoreRegistry().registerSystem(new CustomTickingSystem());
    }

    public void setCustomHud(Player player, PlayerRef playerRef, String hudIdentifier, CustomUIHud customHud) {
        var currentCustomHud = player.getHudManager().getCustomHud();
        if (currentCustomHud instanceof MultipleCustomUIHud multipleCustomUIHud) {
            multipleCustomUIHud.getCustomHuds().put(hudIdentifier, customHud);
            player.getHudManager().setCustomHud(playerRef, new MultipleCustomUIHud(playerRef, multipleCustomUIHud.getCustomHuds()));
        } else {
            var huds = new HashMap<String, CustomUIHud>();
            huds.put(hudIdentifier, customHud);
            if (currentCustomHud != null) huds.put("Unknown", currentCustomHud);
            player.getHudManager().setCustomHud(playerRef, new MultipleCustomUIHud(playerRef, huds));
        }
    }

}