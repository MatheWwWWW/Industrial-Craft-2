/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldServer
 *  net.minecraftforge.common.util.FakePlayerFactory
 */
package ic2.core;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class Ic2Player {
    public static EntityPlayer get(World world) {
        if (world instanceof WorldServer) {
            return FakePlayerFactory.get((WorldServer)((WorldServer)world), (GameProfile)Ic2Player.getGameProfile(world.field_73011_w.getDimension()));
        }
        return null;
    }

    private static GameProfile getGameProfile(int dim) {
        String name = "[IC2 " + dim + "]";
        UUID uuid = UUID.nameUUIDFromBytes(name.getBytes(Charsets.UTF_8));
        return new GameProfile(uuid, name);
    }
}

