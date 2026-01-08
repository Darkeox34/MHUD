package com.buuz135.mhud;


import com.hypixel.hytale.protocol.packets.interface_.CustomHud;
import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

class MultipleCustomUIHud extends CustomUIHud {

    private final HashMap<String, CustomUIHud> customHuds;

    public MultipleCustomUIHud(@NonNullDecl PlayerRef playerRef, HashMap<String, CustomUIHud> customHuds) {
        super(playerRef);
        this.customHuds = customHuds;
    }

    @Override
    protected void build(@NonNullDecl UICommandBuilder uiCommandBuilder) {
        for (String key : customHuds.keySet()) {
            var hud = customHuds.get(key);
            try {
                var method = hud.getClass().getDeclaredMethod("build", UICommandBuilder.class);
                method.setAccessible(true);
                method.invoke(hud, uiCommandBuilder);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public HashMap<String, CustomUIHud> getCustomHuds() {
        return customHuds;
    }
}
