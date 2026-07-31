/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.EndGatewayConfiguration;

public class EndGatewayFeature
extends Feature<EndGatewayConfiguration> {
    public EndGatewayFeature(Codec<EndGatewayConfiguration> p_65682_) {
        super(p_65682_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<EndGatewayConfiguration> p_159715_) {
        BlockPos $$1 = p_159715_.m_159777_();
        WorldGenLevel $$2 = p_159715_.m_159774_();
        EndGatewayConfiguration $$3 = p_159715_.m_159778_();
        for (BlockPos $$4 : BlockPos.m_121940_($$1.m_7918_(-1, -2, -1), $$1.m_7918_(1, 2, 1))) {
            boolean $$8;
            boolean $$5 = $$4.m_123341_() == $$1.m_123341_();
            boolean $$6 = $$4.m_123342_() == $$1.m_123342_();
            boolean $$7 = $$4.m_123343_() == $$1.m_123343_();
            boolean bl = $$8 = Math.abs($$4.m_123342_() - $$1.m_123342_()) == 2;
            if ($$5 && $$6 && $$7) {
                BlockPos $$9 = $$4.m_7949_();
                this.m_5974_($$2, $$9, Blocks.f_50446_.m_49966_());
                $$3.m_67656_().ifPresent(p_65699_ -> {
                    BlockEntity $$4 = $$2.m_7702_($$9);
                    if ($$4 instanceof TheEndGatewayBlockEntity) {
                        TheEndGatewayBlockEntity $$5 = (TheEndGatewayBlockEntity)$$4;
                        $$5.m_59955_((BlockPos)p_65699_, $$3.m_67657_());
                        $$4.m_6596_();
                    }
                });
                continue;
            }
            if ($$6) {
                this.m_5974_($$2, $$4, Blocks.f_50016_.m_49966_());
                continue;
            }
            if ($$8 && $$5 && $$7) {
                this.m_5974_($$2, $$4, Blocks.f_50752_.m_49966_());
                continue;
            }
            if (!$$5 && !$$7 || $$8) {
                this.m_5974_($$2, $$4, Blocks.f_50016_.m_49966_());
                continue;
            }
            this.m_5974_($$2, $$4, Blocks.f_50752_.m_49966_());
        }
        return true;
    }
}

