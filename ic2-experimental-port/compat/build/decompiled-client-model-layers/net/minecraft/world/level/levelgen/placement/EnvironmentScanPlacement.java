/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.placement;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class EnvironmentScanPlacement
extends PlacementModifier {
    private final Direction f_191639_;
    private final BlockPredicate f_191640_;
    private final BlockPredicate f_191641_;
    private final int f_191642_;
    public static final Codec<EnvironmentScanPlacement> f_191638_ = RecordCodecBuilder.create(p_191650_ -> p_191650_.group((App)Direction.f_194527_.fieldOf("direction_of_search").forGetter(p_191672_ -> p_191672_.f_191639_), (App)BlockPredicate.f_190392_.fieldOf("target_condition").forGetter(p_191670_ -> p_191670_.f_191640_), (App)BlockPredicate.f_190392_.optionalFieldOf("allowed_search_condition", (Object)BlockPredicate.m_190435_()).forGetter(p_191668_ -> p_191668_.f_191641_), (App)Codec.intRange((int)1, (int)32).fieldOf("max_steps").forGetter(p_191652_ -> p_191652_.f_191642_)).apply((Applicative)p_191650_, EnvironmentScanPlacement::new));

    private EnvironmentScanPlacement(Direction p_191645_, BlockPredicate p_191646_, BlockPredicate p_191647_, int p_191648_) {
        this.f_191639_ = p_191645_;
        this.f_191640_ = p_191646_;
        this.f_191641_ = p_191647_;
        this.f_191642_ = p_191648_;
    }

    public static EnvironmentScanPlacement m_191657_(Direction p_191658_, BlockPredicate p_191659_, BlockPredicate p_191660_, int p_191661_) {
        return new EnvironmentScanPlacement(p_191658_, p_191659_, p_191660_, p_191661_);
    }

    public static EnvironmentScanPlacement m_191653_(Direction p_191654_, BlockPredicate p_191655_, int p_191656_) {
        return EnvironmentScanPlacement.m_191657_(p_191654_, p_191655_, BlockPredicate.m_190435_(), p_191656_);
    }

    @Override
    public Stream<BlockPos> m_213676_(PlacementContext p_226336_, RandomSource p_226337_, BlockPos p_226338_) {
        BlockPos.MutableBlockPos $$3 = p_226338_.m_122032_();
        WorldGenLevel $$4 = p_226336_.m_191831_();
        if (!this.f_191641_.test($$4, $$3)) {
            return Stream.of(new BlockPos[0]);
        }
        for (int $$5 = 0; $$5 < this.f_191642_; ++$$5) {
            if (this.f_191640_.test($$4, $$3)) {
                return Stream.of($$3);
            }
            $$3.m_122173_(this.f_191639_);
            if ($$4.m_151562_($$3.m_123342_())) {
                return Stream.of(new BlockPos[0]);
            }
            if (!this.f_191641_.test($$4, $$3)) break;
        }
        if (this.f_191640_.test($$4, $$3)) {
            return Stream.of($$3);
        }
        return Stream.of(new BlockPos[0]);
    }

    @Override
    public PlacementModifierType<?> m_183327_() {
        return PlacementModifierType.f_191857_;
    }
}

