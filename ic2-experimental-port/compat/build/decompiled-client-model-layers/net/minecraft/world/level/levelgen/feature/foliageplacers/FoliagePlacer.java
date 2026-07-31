/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.Products$P2
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Mu
 */
package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.material.Fluids;

public abstract class FoliagePlacer {
    public static final Codec<FoliagePlacer> f_68519_ = Registry.f_122858_.m_194605_().dispatch(FoliagePlacer::m_5897_, FoliagePlacerType::m_68604_);
    protected final IntProvider f_68520_;
    protected final IntProvider f_68521_;

    protected static <P extends FoliagePlacer> Products.P2<RecordCodecBuilder.Mu<P>, IntProvider, IntProvider> m_68573_(RecordCodecBuilder.Instance<P> p_68574_) {
        return p_68574_.group((App)IntProvider.m_146545_(0, 16).fieldOf("radius").forGetter(p_161449_ -> p_161449_.f_68520_), (App)IntProvider.m_146545_(0, 16).fieldOf("offset").forGetter(p_161447_ -> p_161447_.f_68521_));
    }

    public FoliagePlacer(IntProvider p_161411_, IntProvider p_161412_) {
        this.f_68520_ = p_161411_;
        this.f_68521_ = p_161412_;
    }

    protected abstract FoliagePlacerType<?> m_5897_();

    public void m_225604_(LevelSimulatedReader p_225605_, BiConsumer<BlockPos, BlockState> p_225606_, RandomSource p_225607_, TreeConfiguration p_225608_, int p_225609_, FoliageAttachment p_225610_, int p_225611_, int p_225612_) {
        this.m_213633_(p_225605_, p_225606_, p_225607_, p_225608_, p_225609_, p_225610_, p_225611_, p_225612_, this.m_225591_(p_225607_));
    }

    protected abstract void m_213633_(LevelSimulatedReader var1, BiConsumer<BlockPos, BlockState> var2, RandomSource var3, TreeConfiguration var4, int var5, FoliageAttachment var6, int var7, int var8, int var9);

    public abstract int m_214116_(RandomSource var1, int var2, TreeConfiguration var3);

    public int m_214117_(RandomSource p_225593_, int p_225594_) {
        return this.f_68520_.m_214085_(p_225593_);
    }

    private int m_225591_(RandomSource p_225592_) {
        return this.f_68521_.m_214085_(p_225592_);
    }

    protected abstract boolean m_214203_(RandomSource var1, int var2, int var3, int var4, int var5, boolean var6);

    protected boolean m_214202_(RandomSource p_225639_, int p_225640_, int p_225641_, int p_225642_, int p_225643_, boolean p_225644_) {
        int $$9;
        int $$8;
        if (p_225644_) {
            int $$6 = Math.min(Math.abs(p_225640_), Math.abs(p_225640_ - 1));
            int $$7 = Math.min(Math.abs(p_225642_), Math.abs(p_225642_ - 1));
        } else {
            $$8 = Math.abs(p_225640_);
            $$9 = Math.abs(p_225642_);
        }
        return this.m_214203_(p_225639_, $$8, p_225641_, $$9, p_225643_, p_225644_);
    }

    protected void m_225628_(LevelSimulatedReader p_225629_, BiConsumer<BlockPos, BlockState> p_225630_, RandomSource p_225631_, TreeConfiguration p_225632_, BlockPos p_225633_, int p_225634_, int p_225635_, boolean p_225636_) {
        int $$8 = p_225636_ ? 1 : 0;
        BlockPos.MutableBlockPos $$9 = new BlockPos.MutableBlockPos();
        for (int $$10 = -p_225634_; $$10 <= p_225634_ + $$8; ++$$10) {
            for (int $$11 = -p_225634_; $$11 <= p_225634_ + $$8; ++$$11) {
                if (this.m_214202_(p_225631_, $$10, p_225635_, $$11, p_225634_, p_225636_)) continue;
                $$9.m_122154_(p_225633_, $$10, p_225635_, $$11);
                FoliagePlacer.m_225622_(p_225629_, p_225630_, p_225631_, p_225632_, $$9);
            }
        }
    }

    protected static void m_225622_(LevelSimulatedReader p_225623_, BiConsumer<BlockPos, BlockState> p_225624_, RandomSource p_225625_, TreeConfiguration p_225626_, BlockPos p_225627_) {
        if (TreeFeature.m_67272_(p_225623_, p_225627_)) {
            BlockState $$5 = p_225626_.f_161213_.m_213972_(p_225625_, p_225627_);
            if ($$5.m_61138_(BlockStateProperties.f_61362_)) {
                $$5 = (BlockState)$$5.m_61124_(BlockStateProperties.f_61362_, p_225623_.m_142433_(p_225627_, p_225638_ -> p_225638_.m_164512_(Fluids.f_76193_)));
            }
            p_225624_.accept(p_225627_, $$5);
        }
    }

    public static final class FoliageAttachment {
        private final BlockPos f_161450_;
        private final int f_68582_;
        private final boolean f_68583_;

        public FoliageAttachment(BlockPos p_68585_, int p_68586_, boolean p_68587_) {
            this.f_161450_ = p_68585_;
            this.f_68582_ = p_68586_;
            this.f_68583_ = p_68587_;
        }

        public BlockPos m_161451_() {
            return this.f_161450_;
        }

        public int m_68589_() {
            return this.f_68582_;
        }

        public boolean m_68590_() {
            return this.f_68583_;
        }
    }
}

