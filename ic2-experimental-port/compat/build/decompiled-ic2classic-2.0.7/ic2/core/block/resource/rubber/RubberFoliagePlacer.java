/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  net.minecraft.core.BlockPos
 *  net.minecraft.util.RandomSource
 *  net.minecraft.util.valueproviders.ConstantInt
 *  net.minecraft.util.valueproviders.IntProvider
 *  net.minecraft.world.level.LevelSimulatedReader
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.levelgen.feature.TreeFeature
 *  net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration
 *  net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
 *  net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer$FoliageAttachment
 *  net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType
 */
package ic2.core.block.resource.rubber;

import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import ic2.core.platform.events.WorldGenerator;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class RubberFoliagePlacer
extends FoliagePlacer {
    public static final Codec<RubberFoliagePlacer> ENCODER = RecordCodecBuilder.create(p_236737_0_ -> RubberFoliagePlacer.m_68573_((RecordCodecBuilder.Instance)p_236737_0_).apply((Applicative)p_236737_0_, RubberFoliagePlacer::new));

    public RubberFoliagePlacer() {
        super((IntProvider)ConstantInt.m_146483_((int)0), (IntProvider)ConstantInt.m_146483_((int)0));
    }

    public RubberFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    protected FoliagePlacerType<?> m_5897_() {
        return WorldGenerator.RUBBER_FOLIAGE;
    }

    protected void m_213633_(LevelSimulatedReader world, BiConsumer<BlockPos, BlockState> placer, RandomSource rand, TreeConfiguration config, int p_225617_, FoliagePlacer.FoliageAttachment position, int p_225619_, int p_225620_, int p_225621_) {
        BlockPos pos = position.m_161451_();
        if (TreeFeature.m_67272_((LevelSimulatedReader)world, (BlockPos)pos)) {
            placer.accept(pos, config.f_161213_.m_213972_(rand, pos));
        }
    }

    public int m_214116_(RandomSource random, int p_230374_2_, TreeConfiguration p_230374_3_) {
        int h = 8;
        int height = h / 2;
        h -= height;
        return height += random.m_188503_(h + 1);
    }

    protected boolean m_214203_(RandomSource p_225595_, int p_225596_, int p_225597_, int p_225598_, int p_225599_, boolean p_225600_) {
        return false;
    }
}

