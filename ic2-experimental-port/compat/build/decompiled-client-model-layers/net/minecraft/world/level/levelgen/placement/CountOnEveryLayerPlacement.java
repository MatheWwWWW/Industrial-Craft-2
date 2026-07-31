/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

@Deprecated
public class CountOnEveryLayerPlacement
extends PlacementModifier {
    public static final Codec<CountOnEveryLayerPlacement> f_191599_ = IntProvider.m_146545_(0, 256).fieldOf("count").xmap(CountOnEveryLayerPlacement::new, p_191611_ -> p_191611_.f_191600_).codec();
    private final IntProvider f_191600_;

    private CountOnEveryLayerPlacement(IntProvider p_191603_) {
        this.f_191600_ = p_191603_;
    }

    public static CountOnEveryLayerPlacement m_191606_(IntProvider p_191607_) {
        return new CountOnEveryLayerPlacement(p_191607_);
    }

    public static CountOnEveryLayerPlacement m_191604_(int p_191605_) {
        return CountOnEveryLayerPlacement.m_191606_(ConstantInt.m_146483_(p_191605_));
    }

    @Override
    public Stream<BlockPos> m_213676_(PlacementContext p_226329_, RandomSource p_226330_, BlockPos p_226331_) {
        boolean $$5;
        Stream.Builder<BlockPos> $$3 = Stream.builder();
        int $$4 = 0;
        do {
            $$5 = false;
            for (int $$6 = 0; $$6 < this.f_191600_.m_214085_(p_226330_); ++$$6) {
                int $$8;
                int $$9;
                int $$7 = p_226330_.m_188503_(16) + p_226331_.m_123341_();
                int $$10 = CountOnEveryLayerPlacement.m_191612_(p_226329_, $$7, $$9 = p_226329_.m_191824_(Heightmap.Types.MOTION_BLOCKING, $$7, $$8 = p_226330_.m_188503_(16) + p_226331_.m_123343_()), $$8, $$4);
                if ($$10 == Integer.MAX_VALUE) continue;
                $$3.add(new BlockPos($$7, $$10, $$8));
                $$5 = true;
            }
            ++$$4;
        } while ($$5);
        return $$3.build();
    }

    @Override
    public PlacementModifierType<?> m_183327_() {
        return PlacementModifierType.f_191856_;
    }

    private static int m_191612_(PlacementContext p_191613_, int p_191614_, int p_191615_, int p_191616_, int p_191617_) {
        BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos(p_191614_, p_191615_, p_191616_);
        int $$6 = 0;
        BlockState $$7 = p_191613_.m_191828_($$5);
        for (int $$8 = p_191615_; $$8 >= p_191613_.m_191830_() + 1; --$$8) {
            $$5.m_142448_($$8 - 1);
            BlockState $$9 = p_191613_.m_191828_($$5);
            if (!CountOnEveryLayerPlacement.m_191608_($$9) && CountOnEveryLayerPlacement.m_191608_($$7) && !$$9.m_60713_(Blocks.f_50752_)) {
                if ($$6 == p_191617_) {
                    return $$5.m_123342_() + 1;
                }
                ++$$6;
            }
            $$7 = $$9;
        }
        return Integer.MAX_VALUE;
    }

    private static boolean m_191608_(BlockState p_191609_) {
        return p_191609_.m_60795_() || p_191609_.m_60713_(Blocks.f_49990_) || p_191609_.m_60713_(Blocks.f_49991_);
    }
}

