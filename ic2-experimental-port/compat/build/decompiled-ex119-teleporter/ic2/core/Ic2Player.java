/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 */
package ic2.core;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import ic2.core.IC2;
import ic2.core.util.Util;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class Ic2Player {
    public static Player get(Level level) {
        if (level instanceof ServerLevel) {
            return IC2.envProxy.createFakePlayer((ServerLevel)level, Ic2Player.getGameProfile(Util.getDimId(level)));
        }
        return null;
    }

    private static GameProfile getGameProfile(ResourceLocation resourceLocation) {
        String string = "[IC2 " + resourceLocation + "]";
        UUID uUID = UUID.nameUUIDFromBytes(string.getBytes(Charsets.UTF_8));
        return new GameProfile(uUID, string);
    }
}

