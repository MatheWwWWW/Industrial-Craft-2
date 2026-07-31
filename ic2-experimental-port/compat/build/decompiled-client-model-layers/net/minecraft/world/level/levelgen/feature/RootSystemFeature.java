/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.RootSystemConfiguration;

public class RootSystemFeature
extends Feature<RootSystemConfiguration> {
    public RootSystemFeature(Codec<RootSystemConfiguration> p_160218_) {
        super(p_160218_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<RootSystemConfiguration> p_160257_) {
        BlockPos $$2;
        WorldGenLevel $$1 = p_160257_.m_159774_();
        if (!$$1.m_8055_($$2 = p_160257_.m_159777_()).m_60795_()) {
            return false;
        }
        RandomSource $$3 = p_160257_.m_225041_();
        BlockPos $$4 = p_160257_.m_159777_();
        RootSystemConfiguration $$5 = p_160257_.m_159778_();
        BlockPos.MutableBlockPos $$6 = $$4.m_122032_();
        if (RootSystemFeature.m_225202_($$1, p_160257_.m_159775_(), $$5, $$3, $$6, $$4)) {
            RootSystemFeature.m_225216_($$1, $$5, $$3, $$4, $$6);
        }
        return true;
    }

    private static boolean m_160235_(WorldGenLevel p_160236_, RootSystemConfiguration p_160237_, BlockPos p_160238_) {
        BlockPos.MutableBlockPos $$3 = p_160238_.m_122032_();
        for (int $$4 = 1; $$4 <= p_160237_.f_161103_; ++$$4) {
            $$3.m_122173_(Direction.UP);
            BlockState $$5 = p_160236_.m_8055_($$3);
            if (RootSystemFeature.m_160252_($$5, $$4, p_160237_.f_161113_)) continue;
            return false;
        }
        return true;
    }

    private static boolean m_160252_(BlockState p_160253_, int p_160254_, int p_160255_) {
        if (p_160253_.m_60795_()) {
            return true;
        }
        int $$3 = p_160254_ + 1;
        return $$3 <= p_160255_ && p_160253_.m_60819_().m_205070_(FluidTags.f_13131_);
    }

    private static boolean m_225202_(WorldGenLevel p_225203_, ChunkGenerator p_225204_, RootSystemConfiguration p_225205_, RandomSource p_225206_, BlockPos.MutableBlockPos p_225207_, BlockPos p_225208_) {
        for (int $$6 = 0; $$6 < p_225205_.f_161108_; ++$$6) {
            p_225207_.m_122173_(Direction.UP);
            if (!p_225205_.f_198355_.test(p_225203_, p_225207_) || !RootSystemFeature.m_160235_(p_225203_, p_225205_, p_225207_)) continue;
            Vec3i $$7 = p_225207_.m_7495_();
            if (p_225203_.m_6425_((BlockPos)$$7).m_205070_(FluidTags.f_13132_) || !p_225203_.m_8055_((BlockPos)$$7).m_60767_().m_76333_()) {
                return false;
            }
            if (!p_225205_.f_161102_.m_203334_().m_226357_(p_225203_, p_225204_, p_225206_, p_225207_)) continue;
            RootSystemFeature.m_225222_(p_225208_, p_225208_.m_123342_() + $$6, p_225203_, p_225205_, p_225206_);
            return true;
        }
        return false;
    }

    private static void m_225222_(BlockPos p_225223_, int p_225224_, WorldGenLevel p_225225_, RootSystemConfiguration p_225226_, RandomSource p_225227_) {
        int $$5 = p_225223_.m_123341_();
        int $$6 = p_225223_.m_123343_();
        BlockPos.MutableBlockPos $$7 = p_225223_.m_122032_();
        for (int $$8 = p_225223_.m_123342_(); $$8 < p_225224_; ++$$8) {
            RootSystemFeature.m_225209_(p_225225_, p_225226_, p_225227_, $$5, $$6, $$7.m_122178_($$5, $$8, $$6));
        }
    }

    private static void m_225209_(WorldGenLevel p_225210_, RootSystemConfiguration p_225211_, RandomSource p_225212_, int p_225213_, int p_225214_, BlockPos.MutableBlockPos p_225215_) {
        int $$6 = p_225211_.f_161104_;
        Predicate<BlockState> $$7 = p_204762_ -> p_204762_.m_204336_(p_204761_.f_161105_);
        for (int $$8 = 0; $$8 < p_225211_.f_161107_; ++$$8) {
            p_225215_.m_122154_(p_225215_, p_225212_.m_188503_($$6) - p_225212_.m_188503_($$6), 0, p_225212_.m_188503_($$6) - p_225212_.m_188503_($$6));
            if ($$7.test(p_225210_.m_8055_(p_225215_))) {
                p_225210_.m_7731_(p_225215_, p_225211_.f_161106_.m_213972_(p_225212_, p_225215_), 2);
            }
            p_225215_.m_142451_(p_225213_);
            p_225215_.m_142443_(p_225214_);
        }
    }

    private static void m_225216_(WorldGenLevel p_225217_, RootSystemConfiguration p_225218_, RandomSource p_225219_, BlockPos p_225220_, BlockPos.MutableBlockPos p_225221_) {
        int $$5 = p_225218_.f_161109_;
        int $$6 = p_225218_.f_161110_;
        for (int $$7 = 0; $$7 < p_225218_.f_161112_; ++$$7) {
            BlockState $$8;
            p_225221_.m_122154_(p_225220_, p_225219_.m_188503_($$5) - p_225219_.m_188503_($$5), p_225219_.m_188503_($$6) - p_225219_.m_188503_($$6), p_225219_.m_188503_($$5) - p_225219_.m_188503_($$5));
            if (!p_225217_.m_46859_(p_225221_) || !($$8 = p_225218_.f_161111_.m_213972_(p_225219_, p_225221_)).m_60710_(p_225217_, p_225221_) || !p_225217_.m_8055_((BlockPos)p_225221_.m_7494_()).m_60783_(p_225217_, p_225221_, Direction.DOWN)) continue;
            p_225217_.m_7731_(p_225221_, $$8, 2);
        }
    }
}

