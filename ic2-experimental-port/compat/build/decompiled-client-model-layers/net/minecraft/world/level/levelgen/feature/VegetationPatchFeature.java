/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;

public class VegetationPatchFeature
extends Feature<VegetationPatchConfiguration> {
    public VegetationPatchFeature(Codec<VegetationPatchConfiguration> p_160588_) {
        super(p_160588_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<VegetationPatchConfiguration> p_160612_) {
        WorldGenLevel $$1 = p_160612_.m_159774_();
        VegetationPatchConfiguration $$2 = p_160612_.m_159778_();
        RandomSource $$3 = p_160612_.m_225041_();
        BlockPos $$4 = p_160612_.m_159777_();
        Predicate<BlockState> $$5 = p_204782_ -> p_204782_.m_204336_(p_204781_.f_161281_);
        int $$6 = $$2.f_161289_.m_214085_($$3) + 1;
        int $$7 = $$2.f_161289_.m_214085_($$3) + 1;
        Set<BlockPos> $$8 = this.m_213631_($$1, $$2, $$3, $$4, $$5, $$6, $$7);
        this.m_225330_(p_160612_, $$1, $$2, $$3, $$8, $$6, $$7);
        return !$$8.isEmpty();
    }

    protected Set<BlockPos> m_213631_(WorldGenLevel p_225311_, VegetationPatchConfiguration p_225312_, RandomSource p_225313_, BlockPos p_225314_, Predicate<BlockState> p_225315_, int p_225316_, int p_225317_) {
        BlockPos.MutableBlockPos $$7 = p_225314_.m_122032_();
        BlockPos.MutableBlockPos $$8 = $$7.m_122032_();
        Direction $$9 = p_225312_.f_161284_.m_162107_();
        Direction $$10 = $$9.m_122424_();
        HashSet<BlockPos> $$11 = new HashSet<BlockPos>();
        for (int $$12 = -p_225316_; $$12 <= p_225316_; ++$$12) {
            boolean $$13 = $$12 == -p_225316_ || $$12 == p_225316_;
            for (int $$14 = -p_225317_; $$14 <= p_225317_; ++$$14) {
                int $$19;
                boolean $$18;
                boolean $$15 = $$14 == -p_225317_ || $$14 == p_225317_;
                boolean $$16 = $$13 || $$15;
                boolean $$17 = $$13 && $$15;
                boolean bl = $$18 = $$16 && !$$17;
                if ($$17 || $$18 && (p_225312_.f_161290_ == 0.0f || p_225313_.m_188501_() > p_225312_.f_161290_)) continue;
                $$7.m_122154_(p_225314_, $$12, 0, $$14);
                for ($$19 = 0; p_225311_.m_7433_($$7, BlockBehaviour.BlockStateBase::m_60795_) && $$19 < p_225312_.f_161287_; ++$$19) {
                    $$7.m_122173_($$9);
                }
                for ($$19 = 0; p_225311_.m_7433_($$7, p_204784_ -> !p_204784_.m_60795_()) && $$19 < p_225312_.f_161287_; ++$$19) {
                    $$7.m_122173_($$10);
                }
                $$8.m_122159_($$7, p_225312_.f_161284_.m_162107_());
                BlockState $$20 = p_225311_.m_8055_($$8);
                if (!p_225311_.m_46859_($$7) || !$$20.m_60783_(p_225311_, $$8, p_225312_.f_161284_.m_162107_().m_122424_())) continue;
                int $$21 = p_225312_.f_161285_.m_214085_(p_225313_) + (p_225312_.f_161286_ > 0.0f && p_225313_.m_188501_() < p_225312_.f_161286_ ? 1 : 0);
                BlockPos $$22 = $$8.m_7949_();
                boolean $$23 = this.m_225323_(p_225311_, p_225312_, p_225315_, p_225313_, $$8, $$21);
                if (!$$23) continue;
                $$11.add($$22);
            }
        }
        return $$11;
    }

    protected void m_225330_(FeaturePlaceContext<VegetationPatchConfiguration> p_225331_, WorldGenLevel p_225332_, VegetationPatchConfiguration p_225333_, RandomSource p_225334_, Set<BlockPos> p_225335_, int p_225336_, int p_225337_) {
        for (BlockPos $$7 : p_225335_) {
            if (!(p_225333_.f_161288_ > 0.0f) || !(p_225334_.m_188501_() < p_225333_.f_161288_)) continue;
            this.m_213555_(p_225332_, p_225333_, p_225331_.m_159775_(), p_225334_, $$7);
        }
    }

    protected boolean m_213555_(WorldGenLevel p_225318_, VegetationPatchConfiguration p_225319_, ChunkGenerator p_225320_, RandomSource p_225321_, BlockPos p_225322_) {
        return p_225319_.f_161283_.m_203334_().m_226357_(p_225318_, p_225320_, p_225321_, p_225322_.m_121945_(p_225319_.f_161284_.m_162107_().m_122424_()));
    }

    protected boolean m_225323_(WorldGenLevel p_225324_, VegetationPatchConfiguration p_225325_, Predicate<BlockState> p_225326_, RandomSource p_225327_, BlockPos.MutableBlockPos p_225328_, int p_225329_) {
        for (int $$6 = 0; $$6 < p_225329_; ++$$6) {
            BlockState $$8;
            BlockState $$7 = p_225325_.f_161282_.m_213972_(p_225327_, p_225328_);
            if ($$7.m_60713_(($$8 = p_225324_.m_8055_(p_225328_)).m_60734_())) continue;
            if (!p_225326_.test($$8)) {
                return $$6 != 0;
            }
            p_225324_.m_7731_(p_225328_, $$7, 2);
            p_225328_.m_122173_(p_225325_.f_161284_.m_162107_());
        }
        return true;
    }
}

