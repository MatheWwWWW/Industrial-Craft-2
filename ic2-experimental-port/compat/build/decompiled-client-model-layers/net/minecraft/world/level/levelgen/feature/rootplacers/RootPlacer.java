/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.Products$P3
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Mu
 */
package net.minecraft.world.level.levelgen.feature.rootplacers;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.rootplacers.AboveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public abstract class RootPlacer {
    public static final Codec<RootPlacer> f_225859_ = Registry.f_235742_.m_194605_().dispatch(RootPlacer::m_213745_, RootPlacerType::m_225903_);
    protected final IntProvider f_225860_;
    protected final BlockStateProvider f_225861_;
    protected final Optional<AboveRootPlacement> f_225862_;

    protected static <P extends RootPlacer> Products.P3<RecordCodecBuilder.Mu<P>, IntProvider, BlockStateProvider, Optional<AboveRootPlacement>> m_225885_(RecordCodecBuilder.Instance<P> p_225886_) {
        return p_225886_.group((App)IntProvider.f_146531_.fieldOf("trunk_offset_y").forGetter(p_225897_ -> p_225897_.f_225860_), (App)BlockStateProvider.f_68747_.fieldOf("root_provider").forGetter(p_225895_ -> p_225895_.f_225861_), (App)AboveRootPlacement.f_225753_.optionalFieldOf("above_root_placement").forGetter(p_225888_ -> p_225888_.f_225862_));
    }

    public RootPlacer(IntProvider p_225865_, BlockStateProvider p_225866_, Optional<AboveRootPlacement> p_225867_) {
        this.f_225860_ = p_225865_;
        this.f_225861_ = p_225866_;
        this.f_225862_ = p_225867_;
    }

    protected abstract RootPlacerType<?> m_213745_();

    public abstract boolean m_213684_(LevelSimulatedReader var1, BiConsumer<BlockPos, BlockState> var2, RandomSource var3, BlockPos var4, BlockPos var5, TreeConfiguration var6);

    protected boolean m_213551_(LevelSimulatedReader p_225868_, BlockPos p_225869_) {
        return TreeFeature.m_67272_(p_225868_, p_225869_);
    }

    protected void m_213654_(LevelSimulatedReader p_225874_, BiConsumer<BlockPos, BlockState> p_225875_, RandomSource p_225876_, BlockPos p_225877_, TreeConfiguration p_225878_) {
        if (!this.m_213551_(p_225874_, p_225877_)) {
            return;
        }
        p_225875_.accept(p_225877_, this.m_225870_(p_225874_, p_225877_, this.f_225861_.m_213972_(p_225876_, p_225877_)));
        if (this.f_225862_.isPresent()) {
            AboveRootPlacement $$5 = this.f_225862_.get();
            BlockPos $$6 = p_225877_.m_7494_();
            if (p_225876_.m_188501_() < $$5.f_225755_() && p_225874_.m_7433_($$6, BlockBehaviour.BlockStateBase::m_60795_)) {
                p_225875_.accept($$6, this.m_225870_(p_225874_, $$6, $$5.f_225754_().m_213972_(p_225876_, $$6)));
            }
        }
    }

    protected BlockState m_225870_(LevelSimulatedReader p_225871_, BlockPos p_225872_, BlockState p_225873_) {
        if (p_225873_.m_61138_(BlockStateProperties.f_61362_)) {
            boolean $$3 = p_225871_.m_142433_(p_225872_, p_225890_ -> p_225890_.m_205070_(FluidTags.f_13131_));
            return (BlockState)p_225873_.m_61124_(BlockStateProperties.f_61362_, $$3);
        }
        return p_225873_;
    }

    public BlockPos m_225891_(BlockPos p_225892_, RandomSource p_225893_) {
        return p_225892_.m_6630_(this.f_225860_.m_214085_(p_225893_));
    }
}

