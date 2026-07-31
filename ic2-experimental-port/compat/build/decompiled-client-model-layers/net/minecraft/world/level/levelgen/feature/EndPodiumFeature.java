/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class EndPodiumFeature
extends Feature<NoneFeatureConfiguration> {
    public static final int f_159718_ = 4;
    public static final int f_159719_ = 4;
    public static final int f_159720_ = 1;
    public static final float f_159721_ = 0.5f;
    public static final BlockPos f_65714_ = BlockPos.f_121853_;
    private final boolean f_65715_;

    public EndPodiumFeature(boolean p_65718_) {
        super(NoneFeatureConfiguration.f_67815_);
        this.f_65715_ = p_65718_;
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_159723_) {
        BlockPos $$1 = p_159723_.m_159777_();
        WorldGenLevel $$2 = p_159723_.m_159774_();
        for (BlockPos $$3 : BlockPos.m_121940_(new BlockPos($$1.m_123341_() - 4, $$1.m_123342_() - 1, $$1.m_123343_() - 4), new BlockPos($$1.m_123341_() + 4, $$1.m_123342_() + 32, $$1.m_123343_() + 4))) {
            boolean $$4 = $$3.m_123314_($$1, 2.5);
            if (!$$4 && !$$3.m_123314_($$1, 3.5)) continue;
            if ($$3.m_123342_() < $$1.m_123342_()) {
                if ($$4) {
                    this.m_5974_($$2, $$3, Blocks.f_50752_.m_49966_());
                    continue;
                }
                if ($$3.m_123342_() >= $$1.m_123342_()) continue;
                this.m_5974_($$2, $$3, Blocks.f_50259_.m_49966_());
                continue;
            }
            if ($$3.m_123342_() > $$1.m_123342_()) {
                this.m_5974_($$2, $$3, Blocks.f_50016_.m_49966_());
                continue;
            }
            if (!$$4) {
                this.m_5974_($$2, $$3, Blocks.f_50752_.m_49966_());
                continue;
            }
            if (this.f_65715_) {
                this.m_5974_($$2, new BlockPos($$3), Blocks.f_50257_.m_49966_());
                continue;
            }
            this.m_5974_($$2, new BlockPos($$3), Blocks.f_50016_.m_49966_());
        }
        for (int $$5 = 0; $$5 < 4; ++$$5) {
            this.m_5974_($$2, $$1.m_6630_($$5), Blocks.f_50752_.m_49966_());
        }
        BlockPos $$6 = $$1.m_6630_(2);
        for (Direction $$7 : Direction.Plane.HORIZONTAL) {
            this.m_5974_($$2, $$6.m_121945_($$7), (BlockState)Blocks.f_50082_.m_49966_().m_61124_(WallTorchBlock.f_58119_, $$7));
        }
        return true;
    }
}

