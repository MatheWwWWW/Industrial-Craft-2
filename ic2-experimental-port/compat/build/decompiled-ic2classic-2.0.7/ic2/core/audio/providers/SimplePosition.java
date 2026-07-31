/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.audio.providers;

import ic2.core.audio.AudioManager;
import ic2.core.audio.IAudioPosition;
import ic2.core.audio.ISoundProvider;
import ic2.core.audio.providers.MovingEntityPosition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

public class SimplePosition
implements IAudioPosition {
    Level world;
    Vec3 pos;

    public SimplePosition(Level world, BlockPos pos) {
        this(world, Vec3.m_82528_((Vec3i)pos));
    }

    public SimplePosition(Level world, Vec3 pos) {
        this.world = world;
        this.pos = pos;
    }

    @Override
    public Level getWorld() {
        return this.world;
    }

    @Override
    public Vec3 getPosition() {
        return this.pos;
    }

    public static IAudioPosition getFrom(Object obj, AudioManager.SoundType spec) {
        if (obj instanceof ISoundProvider) {
            return ((ISoundProvider)obj).getPosition();
        }
        if (obj instanceof IAudioPosition) {
            return (IAudioPosition)obj;
        }
        if (obj instanceof Entity) {
            Entity e = (Entity)obj;
            if (spec == AudioManager.SoundType.STATIC) {
                return new SimplePosition(e.m_20193_(), e.m_20182_());
            }
            return new MovingEntityPosition(e);
        }
        if (obj instanceof BlockEntity) {
            BlockEntity te = (BlockEntity)obj;
            return new SimplePosition(te.m_58904_(), te.m_58899_());
        }
        return null;
    }
}

