/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.mutable.MutableInt
 */
package net.minecraft.world.level.lighting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.BlockLightSectionStorage;
import net.minecraft.world.level.lighting.LayerLightEngine;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableInt;

public final class BlockLightEngine
extends LayerLightEngine<BlockLightSectionStorage.BlockDataLayerStorageMap, BlockLightSectionStorage> {
    private static final Direction[] f_75488_ = Direction.values();
    private final BlockPos.MutableBlockPos f_75489_ = new BlockPos.MutableBlockPos();

    public BlockLightEngine(LightChunkGetter p_75492_) {
        super(p_75492_, LightLayer.BLOCK, new BlockLightSectionStorage(p_75492_));
    }

    private int m_75508_(long p_75509_) {
        int $$1 = BlockPos.m_121983_(p_75509_);
        int $$2 = BlockPos.m_122008_(p_75509_);
        int $$3 = BlockPos.m_122015_(p_75509_);
        BlockGetter $$4 = this.f_75630_.m_6196_(SectionPos.m_123171_($$1), SectionPos.m_123171_($$3));
        if ($$4 != null) {
            return $$4.m_7146_(this.f_75489_.m_122178_($$1, $$2, $$3));
        }
        return 0;
    }

    @Override
    protected int m_6359_(long p_75505_, long p_75506_, int p_75507_) {
        VoxelShape $$11;
        int $$5;
        int $$4;
        if (p_75506_ == Long.MAX_VALUE) {
            return 15;
        }
        if (p_75505_ == Long.MAX_VALUE) {
            return p_75507_ + 15 - this.m_75508_(p_75506_);
        }
        if (p_75507_ >= 15) {
            return p_75507_;
        }
        int $$3 = Integer.signum(BlockPos.m_121983_(p_75506_) - BlockPos.m_121983_(p_75505_));
        Direction $$6 = Direction.m_122378_($$3, $$4 = Integer.signum(BlockPos.m_122008_(p_75506_) - BlockPos.m_122008_(p_75505_)), $$5 = Integer.signum(BlockPos.m_122015_(p_75506_) - BlockPos.m_122015_(p_75505_)));
        if ($$6 == null) {
            return 15;
        }
        MutableInt $$7 = new MutableInt();
        BlockState $$8 = this.m_75664_(p_75506_, $$7);
        if ($$7.getValue() >= 15) {
            return 15;
        }
        BlockState $$9 = this.m_75664_(p_75505_, null);
        VoxelShape $$10 = this.m_75678_($$9, p_75505_, $$6);
        if (Shapes.m_83145_($$10, $$11 = this.m_75678_($$8, p_75506_, $$6.m_122424_()))) {
            return 15;
        }
        return p_75507_ + Math.max(1, $$7.getValue());
    }

    @Override
    protected void m_7900_(long p_75494_, int p_75495_, boolean p_75496_) {
        long $$3 = SectionPos.m_123235_(p_75494_);
        for (Direction $$4 : f_75488_) {
            long $$5 = BlockPos.m_121915_(p_75494_, $$4);
            long $$6 = SectionPos.m_123235_($$5);
            if ($$3 != $$6 && !((BlockLightSectionStorage)this.f_75632_).m_75791_($$6)) continue;
            this.m_75593_(p_75494_, $$5, p_75495_, p_75496_);
        }
    }

    @Override
    protected int m_6357_(long p_75498_, long p_75499_, int p_75500_) {
        int $$3 = p_75500_;
        if (Long.MAX_VALUE != p_75499_) {
            int $$4 = this.m_6359_(Long.MAX_VALUE, p_75498_, 0);
            if ($$3 > $$4) {
                $$3 = $$4;
            }
            if ($$3 == 0) {
                return $$3;
            }
        }
        long $$5 = SectionPos.m_123235_(p_75498_);
        DataLayer $$6 = ((BlockLightSectionStorage)this.f_75632_).m_75758_($$5, true);
        for (Direction $$7 : f_75488_) {
            DataLayer $$11;
            long $$8 = BlockPos.m_121915_(p_75498_, $$7);
            if ($$8 == p_75499_) continue;
            long $$9 = SectionPos.m_123235_($$8);
            if ($$5 == $$9) {
                DataLayer $$10 = $$6;
            } else {
                $$11 = ((BlockLightSectionStorage)this.f_75632_).m_75758_($$9, true);
            }
            if ($$11 == null) continue;
            int $$12 = this.m_6359_($$8, p_75498_, this.m_75682_($$11, $$8));
            if ($$3 > $$12) {
                $$3 = $$12;
            }
            if ($$3 != 0) continue;
            return $$3;
        }
        return $$3;
    }

    @Override
    public void m_8116_(BlockPos p_75502_, int p_75503_) {
        ((BlockLightSectionStorage)this.f_75632_).m_75785_();
        this.m_75576_(Long.MAX_VALUE, p_75502_.m_121878_(), 15 - p_75503_, true);
    }
}

