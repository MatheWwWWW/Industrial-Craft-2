/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.server.level;

import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;

public class PlayerRespawnLogic {
    @Nullable
    protected static BlockPos m_183928_(ServerLevel p_183929_, int p_183930_, int p_183931_) {
        int $$5;
        boolean $$3 = p_183929_.m_6042_().f_63856_();
        LevelChunk $$4 = p_183929_.m_6325_(SectionPos.m_123171_(p_183930_), SectionPos.m_123171_(p_183931_));
        int n = $$5 = $$3 ? p_183929_.m_7726_().m_8481_().m_142051_(p_183929_) : $$4.m_5885_(Heightmap.Types.MOTION_BLOCKING, p_183930_ & 0xF, p_183931_ & 0xF);
        if ($$5 < p_183929_.m_141937_()) {
            return null;
        }
        int $$6 = $$4.m_5885_(Heightmap.Types.WORLD_SURFACE, p_183930_ & 0xF, p_183931_ & 0xF);
        if ($$6 <= $$5 && $$6 > $$4.m_5885_(Heightmap.Types.OCEAN_FLOOR, p_183930_ & 0xF, p_183931_ & 0xF)) {
            return null;
        }
        BlockPos.MutableBlockPos $$7 = new BlockPos.MutableBlockPos();
        for (int $$8 = $$5 + 1; $$8 >= p_183929_.m_141937_(); --$$8) {
            $$7.m_122178_(p_183930_, $$8, p_183931_);
            BlockState $$9 = p_183929_.m_8055_($$7);
            if (!$$9.m_60819_().m_76178_()) break;
            if (!Block.m_49918_($$9.m_60812_(p_183929_, $$7), Direction.UP)) continue;
            return ((BlockPos)$$7.m_7494_()).m_7949_();
        }
        return null;
    }

    @Nullable
    public static BlockPos m_183932_(ServerLevel p_183933_, ChunkPos p_183934_) {
        if (SharedConstants.m_183707_(p_183934_)) {
            return null;
        }
        for (int $$2 = p_183934_.m_45604_(); $$2 <= p_183934_.m_45608_(); ++$$2) {
            for (int $$3 = p_183934_.m_45605_(); $$3 <= p_183934_.m_45609_(); ++$$3) {
                BlockPos $$4 = PlayerRespawnLogic.m_183928_(p_183933_, $$2, $$3);
                if ($$4 == null) continue;
                return $$4;
            }
        }
        return null;
    }
}

