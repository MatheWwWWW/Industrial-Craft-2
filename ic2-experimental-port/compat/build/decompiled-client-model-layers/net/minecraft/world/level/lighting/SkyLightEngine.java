/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.mutable.MutableInt
 */
package net.minecraft.world.level.lighting;

import java.util.Locale;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.LayerLightEngine;
import net.minecraft.world.level.lighting.SkyLightSectionStorage;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableInt;

public final class SkyLightEngine
extends LayerLightEngine<SkyLightSectionStorage.SkyDataLayerStorageMap, SkyLightSectionStorage> {
    private static final Direction[] f_75839_ = Direction.values();
    private static final Direction[] f_75840_ = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

    public SkyLightEngine(LightChunkGetter p_75843_) {
        super(p_75843_, LightLayer.SKY, new SkyLightSectionStorage(p_75843_));
    }

    @Override
    protected int m_6359_(long p_75855_, long p_75856_, int p_75857_) {
        boolean $$19;
        VoxelShape $$17;
        int $$13;
        int $$12;
        if (p_75856_ == Long.MAX_VALUE || p_75855_ == Long.MAX_VALUE) {
            return 15;
        }
        if (p_75857_ >= 15) {
            return p_75857_;
        }
        MutableInt $$3 = new MutableInt();
        BlockState $$4 = this.m_75664_(p_75856_, $$3);
        if ($$3.getValue() >= 15) {
            return 15;
        }
        int $$5 = BlockPos.m_121983_(p_75855_);
        int $$6 = BlockPos.m_122008_(p_75855_);
        int $$7 = BlockPos.m_122015_(p_75855_);
        int $$8 = BlockPos.m_121983_(p_75856_);
        int $$9 = BlockPos.m_122008_(p_75856_);
        int $$10 = BlockPos.m_122015_(p_75856_);
        int $$11 = Integer.signum($$8 - $$5);
        Direction $$14 = Direction.m_122378_($$11, $$12 = Integer.signum($$9 - $$6), $$13 = Integer.signum($$10 - $$7));
        if ($$14 == null) {
            throw new IllegalStateException(String.format(Locale.ROOT, "Light was spread in illegal direction %d, %d, %d", $$11, $$12, $$13));
        }
        BlockState $$15 = this.m_75664_(p_75855_, null);
        VoxelShape $$16 = this.m_75678_($$15, p_75855_, $$14);
        if (Shapes.m_83145_($$16, $$17 = this.m_75678_($$4, p_75856_, $$14.m_122424_()))) {
            return 15;
        }
        boolean $$18 = $$5 == $$8 && $$7 == $$10;
        boolean bl = $$19 = $$18 && $$6 > $$9;
        if ($$19 && p_75857_ == 0 && $$3.getValue() == 0) {
            return 0;
        }
        return p_75857_ + Math.max(1, $$3.getValue());
    }

    @Override
    protected void m_7900_(long p_75845_, int p_75846_, boolean p_75847_) {
        long $$12;
        long $$13;
        int $$9;
        long $$3 = SectionPos.m_123235_(p_75845_);
        int $$4 = BlockPos.m_122008_(p_75845_);
        int $$5 = SectionPos.m_123207_($$4);
        int $$6 = SectionPos.m_123171_($$4);
        if ($$5 != 0) {
            boolean $$7 = false;
        } else {
            int $$8 = 0;
            while (!((SkyLightSectionStorage)this.f_75632_).m_75791_(SectionPos.m_123186_($$3, 0, -$$8 - 1, 0)) && ((SkyLightSectionStorage)this.f_75632_).m_75870_($$6 - $$8 - 1)) {
                ++$$8;
            }
            $$9 = $$8;
        }
        long $$10 = BlockPos.m_121910_(p_75845_, 0, -1 - $$9 * 16, 0);
        long $$11 = SectionPos.m_123235_($$10);
        if ($$3 == $$11 || ((SkyLightSectionStorage)this.f_75632_).m_75791_($$11)) {
            this.m_75593_(p_75845_, $$10, p_75846_, p_75847_);
        }
        if ($$3 == ($$13 = SectionPos.m_123235_($$12 = BlockPos.m_121915_(p_75845_, Direction.UP))) || ((SkyLightSectionStorage)this.f_75632_).m_75791_($$13)) {
            this.m_75593_(p_75845_, $$12, p_75846_, p_75847_);
        }
        block1: for (Direction $$14 : f_75840_) {
            int $$15 = 0;
            do {
                long $$16;
                long $$17;
                if ($$3 == ($$17 = SectionPos.m_123235_($$16 = BlockPos.m_121910_(p_75845_, $$14.m_122429_(), -$$15, $$14.m_122431_())))) {
                    this.m_75593_(p_75845_, $$16, p_75846_, p_75847_);
                    continue block1;
                }
                if (!((SkyLightSectionStorage)this.f_75632_).m_75791_($$17)) continue;
                long $$18 = BlockPos.m_121910_(p_75845_, 0, -$$15, 0);
                this.m_75593_($$18, $$16, p_75846_, p_75847_);
            } while (++$$15 <= $$9 * 16);
        }
    }

    @Override
    protected int m_6357_(long p_75849_, long p_75850_, int p_75851_) {
        int $$3 = p_75851_;
        long $$4 = SectionPos.m_123235_(p_75849_);
        DataLayer $$5 = ((SkyLightSectionStorage)this.f_75632_).m_75758_($$4, true);
        for (Direction $$6 : f_75839_) {
            int $$12;
            DataLayer $$10;
            long $$7 = BlockPos.m_121915_(p_75849_, $$6);
            if ($$7 == p_75850_) continue;
            long $$8 = SectionPos.m_123235_($$7);
            if ($$4 == $$8) {
                DataLayer $$9 = $$5;
            } else {
                $$10 = ((SkyLightSectionStorage)this.f_75632_).m_75758_($$8, true);
            }
            if ($$10 != null) {
                int $$11 = this.m_75682_($$10, $$7);
            } else {
                if ($$6 == Direction.DOWN) continue;
                $$12 = 15 - ((SkyLightSectionStorage)this.f_75632_).m_164457_($$7, true);
            }
            int $$13 = this.m_6359_($$7, p_75849_, $$12);
            if ($$3 > $$13) {
                $$3 = $$13;
            }
            if ($$3 != 0) continue;
            return $$3;
        }
        return $$3;
    }

    @Override
    protected void m_6185_(long p_75859_) {
        ((SkyLightSectionStorage)this.f_75632_).m_75785_();
        long $$1 = SectionPos.m_123235_(p_75859_);
        if (((SkyLightSectionStorage)this.f_75632_).m_75791_($$1)) {
            super.m_6185_(p_75859_);
        } else {
            p_75859_ = BlockPos.m_122027_(p_75859_);
            while (!((SkyLightSectionStorage)this.f_75632_).m_75791_($$1) && !((SkyLightSectionStorage)this.f_75632_).m_75890_($$1)) {
                $$1 = SectionPos.m_123191_($$1, Direction.UP);
                p_75859_ = BlockPos.m_121910_(p_75859_, 0, 16, 0);
            }
            if (((SkyLightSectionStorage)this.f_75632_).m_75791_($$1)) {
                super.m_6185_(p_75859_);
            }
        }
    }

    @Override
    public String m_6647_(long p_75853_) {
        return super.m_6647_(p_75853_) + (((SkyLightSectionStorage)this.f_75632_).m_75890_(p_75853_) ? "*" : "");
    }
}

