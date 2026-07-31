/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.audio;

import ic2.core.audio.PositionSpec;
import ic2.core.util.Util;
import java.lang.ref.WeakReference;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

public class AudioPosition {
    private final WeakReference<Level> worldRef;
    public final float x;
    public final float y;
    public final float z;

    public static AudioPosition getFrom(Object object, PositionSpec positionSpec) {
        if (object instanceof AudioPosition) {
            return (AudioPosition)object;
        }
        if (object instanceof Entity) {
            Entity entity = (Entity)object;
            return new AudioPosition(entity.m_20193_(), (float)entity.m_20185_(), (float)entity.m_20186_(), (float)entity.m_20189_());
        }
        if (object instanceof BlockEntity) {
            BlockEntity blockEntity = (BlockEntity)object;
            return new AudioPosition(blockEntity.m_58904_(), (float)blockEntity.m_58899_().m_123341_() + 0.5f, (float)blockEntity.m_58899_().m_123342_() + 0.5f, (float)blockEntity.m_58899_().m_123343_() + 0.5f);
        }
        return null;
    }

    public AudioPosition(Level level, float f, float f2, float f3) {
        this.worldRef = new WeakReference<Level>(level);
        this.x = f;
        this.y = f2;
        this.z = f3;
    }

    public AudioPosition(Level level, BlockPos blockPos) {
        this(level, (float)blockPos.m_123341_() + 0.5f, (float)blockPos.m_123342_() + 0.5f, (float)blockPos.m_123343_() + 0.5f);
    }

    public Level getWorld() {
        return (Level)this.worldRef.get();
    }

    public Vec3 asVec3d() {
        return new Vec3((double)this.x, (double)this.y, (double)this.z);
    }

    public boolean isSameBlock(BlockPos blockPos) {
        return blockPos.m_123341_() == Util.roundToNegInf(this.x) && blockPos.m_123342_() == Util.roundToNegInf(this.y) && blockPos.m_123343_() == Util.roundToNegInf(this.z);
    }
}

