/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.audio;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public interface IAudioPosition {
    public Level getWorld();

    public Vec3 getPosition();

    default public boolean isInSameWorld(Level world) {
        return IAudioPosition.isInSameWorld(world, this.getWorld());
    }

    public static boolean isInSameWorld(Level world, Level otherWorld) {
        return IAudioPosition.getId(world) == IAudioPosition.getId(otherWorld);
    }

    public static ResourceKey<Level> getId(Level world) {
        return world == null ? null : world.m_46472_();
    }
}

