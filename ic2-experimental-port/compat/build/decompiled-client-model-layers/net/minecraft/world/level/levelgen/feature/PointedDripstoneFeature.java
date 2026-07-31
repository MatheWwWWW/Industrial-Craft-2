/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.PointedDripstoneConfiguration;

public class PointedDripstoneFeature
extends Feature<PointedDripstoneConfiguration> {
    public PointedDripstoneFeature(Codec<PointedDripstoneConfiguration> p_191067_) {
        super(p_191067_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<PointedDripstoneConfiguration> p_191078_) {
        WorldGenLevel $$1 = p_191078_.m_159774_();
        BlockPos $$2 = p_191078_.m_159777_();
        RandomSource $$3 = p_191078_.m_225041_();
        PointedDripstoneConfiguration $$4 = p_191078_.m_159778_();
        Optional<Direction> $$5 = PointedDripstoneFeature.m_225198_($$1, $$2, $$3);
        if ($$5.isEmpty()) {
            return false;
        }
        BlockPos $$6 = $$2.m_121945_($$5.get().m_122424_());
        PointedDripstoneFeature.m_225193_($$1, $$3, $$6, $$4);
        int $$7 = $$3.m_188501_() < $$4.f_191275_ && DripstoneUtils.m_159664_($$1.m_8055_($$2.m_121945_($$5.get()))) ? 2 : 1;
        DripstoneUtils.m_190847_($$1, $$2, $$5.get(), $$7, false);
        return true;
    }

    private static Optional<Direction> m_225198_(LevelAccessor p_225199_, BlockPos p_225200_, RandomSource p_225201_) {
        boolean $$3 = DripstoneUtils.m_159662_(p_225199_.m_8055_(p_225200_.m_7494_()));
        boolean $$4 = DripstoneUtils.m_159662_(p_225199_.m_8055_(p_225200_.m_7495_()));
        if ($$3 && $$4) {
            return Optional.of(p_225201_.m_188499_() ? Direction.DOWN : Direction.UP);
        }
        if ($$3) {
            return Optional.of(Direction.DOWN);
        }
        if ($$4) {
            return Optional.of(Direction.UP);
        }
        return Optional.empty();
    }

    private static void m_225193_(LevelAccessor p_225194_, RandomSource p_225195_, BlockPos p_225196_, PointedDripstoneConfiguration p_225197_) {
        DripstoneUtils.m_190853_(p_225194_, p_225196_);
        for (Direction $$4 : Direction.Plane.HORIZONTAL) {
            if (p_225195_.m_188501_() > p_225197_.f_191276_) continue;
            BlockPos $$5 = p_225196_.m_121945_($$4);
            DripstoneUtils.m_190853_(p_225194_, $$5);
            if (p_225195_.m_188501_() > p_225197_.f_191277_) continue;
            BlockPos $$6 = $$5.m_121945_(Direction.m_235672_(p_225195_));
            DripstoneUtils.m_190853_(p_225194_, $$6);
            if (p_225195_.m_188501_() > p_225197_.f_191278_) continue;
            BlockPos $$7 = $$6.m_121945_(Direction.m_235672_(p_225195_));
            DripstoneUtils.m_190853_(p_225194_, $$7);
        }
    }
}

