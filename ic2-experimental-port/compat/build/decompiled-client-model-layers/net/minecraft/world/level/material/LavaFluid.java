/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.material;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public abstract class LavaFluid
extends FlowingFluid {
    public static final float f_164528_ = 0.44444445f;

    @Override
    public Fluid m_5615_() {
        return Fluids.f_76194_;
    }

    @Override
    public Fluid m_5613_() {
        return Fluids.f_76195_;
    }

    @Override
    public Item m_6859_() {
        return Items.f_42448_;
    }

    @Override
    public void m_213811_(Level p_230567_, BlockPos p_230568_, FluidState p_230569_, RandomSource p_230570_) {
        BlockPos $$4 = p_230568_.m_7494_();
        if (p_230567_.m_8055_($$4).m_60795_() && !p_230567_.m_8055_($$4).m_60804_(p_230567_, $$4)) {
            if (p_230570_.m_188503_(100) == 0) {
                double $$5 = (double)p_230568_.m_123341_() + p_230570_.m_188500_();
                double $$6 = (double)p_230568_.m_123342_() + 1.0;
                double $$7 = (double)p_230568_.m_123343_() + p_230570_.m_188500_();
                p_230567_.m_7106_(ParticleTypes.f_123756_, $$5, $$6, $$7, 0.0, 0.0, 0.0);
                p_230567_.m_7785_($$5, $$6, $$7, SoundEvents.f_12032_, SoundSource.BLOCKS, 0.2f + p_230570_.m_188501_() * 0.2f, 0.9f + p_230570_.m_188501_() * 0.15f, false);
            }
            if (p_230570_.m_188503_(200) == 0) {
                p_230567_.m_7785_(p_230568_.m_123341_(), p_230568_.m_123342_(), p_230568_.m_123343_(), SoundEvents.f_12030_, SoundSource.BLOCKS, 0.2f + p_230570_.m_188501_() * 0.2f, 0.9f + p_230570_.m_188501_() * 0.15f, false);
            }
        }
    }

    @Override
    public void m_213812_(Level p_230572_, BlockPos p_230573_, FluidState p_230574_, RandomSource p_230575_) {
        if (!p_230572_.m_46469_().m_46207_(GameRules.f_46131_)) {
            return;
        }
        int $$4 = p_230575_.m_188503_(3);
        if ($$4 > 0) {
            BlockPos $$5 = p_230573_;
            for (int $$6 = 0; $$6 < $$4; ++$$6) {
                if (!p_230572_.m_46749_($$5 = $$5.m_7918_(p_230575_.m_188503_(3) - 1, 1, p_230575_.m_188503_(3) - 1))) {
                    return;
                }
                BlockState $$7 = p_230572_.m_8055_($$5);
                if ($$7.m_60795_()) {
                    if (!this.m_76227_(p_230572_, $$5)) continue;
                    p_230572_.m_46597_($$5, BaseFireBlock.m_49245_(p_230572_, $$5));
                    return;
                }
                if (!$$7.m_60767_().m_76334_()) continue;
                return;
            }
        } else {
            for (int $$8 = 0; $$8 < 3; ++$$8) {
                BlockPos $$9 = p_230573_.m_7918_(p_230575_.m_188503_(3) - 1, 0, p_230575_.m_188503_(3) - 1);
                if (!p_230572_.m_46749_($$9)) {
                    return;
                }
                if (!p_230572_.m_46859_($$9.m_7494_()) || !this.m_76245_(p_230572_, $$9)) continue;
                p_230572_.m_46597_($$9.m_7494_(), BaseFireBlock.m_49245_(p_230572_, $$9));
            }
        }
    }

    private boolean m_76227_(LevelReader p_76228_, BlockPos p_76229_) {
        for (Direction $$2 : Direction.values()) {
            if (!this.m_76245_(p_76228_, p_76229_.m_121945_($$2))) continue;
            return true;
        }
        return false;
    }

    private boolean m_76245_(LevelReader p_76246_, BlockPos p_76247_) {
        if (p_76247_.m_123342_() >= p_76246_.m_141937_() && p_76247_.m_123342_() < p_76246_.m_151558_() && !p_76246_.m_46805_(p_76247_)) {
            return false;
        }
        return p_76246_.m_8055_(p_76247_).m_60767_().m_76335_();
    }

    @Override
    @Nullable
    public ParticleOptions m_7792_() {
        return ParticleTypes.f_123800_;
    }

    @Override
    protected void m_7456_(LevelAccessor p_76216_, BlockPos p_76217_, BlockState p_76218_) {
        this.m_76212_(p_76216_, p_76217_);
    }

    @Override
    public int m_6719_(LevelReader p_76244_) {
        return p_76244_.m_6042_().f_63857_() ? 4 : 2;
    }

    @Override
    public BlockState m_5804_(FluidState p_76249_) {
        return (BlockState)Blocks.f_49991_.m_49966_().m_61124_(LiquidBlock.f_54688_, LavaFluid.m_76092_(p_76249_));
    }

    @Override
    public boolean m_6212_(Fluid p_76231_) {
        return p_76231_ == Fluids.f_76195_ || p_76231_ == Fluids.f_76194_;
    }

    @Override
    public int m_6713_(LevelReader p_76252_) {
        return p_76252_.m_6042_().f_63857_() ? 1 : 2;
    }

    @Override
    public boolean m_5486_(FluidState p_76233_, BlockGetter p_76234_, BlockPos p_76235_, Fluid p_76236_, Direction p_76237_) {
        return p_76233_.m_76155_(p_76234_, p_76235_) >= 0.44444445f && p_76236_.m_205067_(FluidTags.f_13131_);
    }

    @Override
    public int m_6718_(LevelReader p_76226_) {
        return p_76226_.m_6042_().f_63857_() ? 10 : 30;
    }

    @Override
    public int m_6886_(Level p_76203_, BlockPos p_76204_, FluidState p_76205_, FluidState p_76206_) {
        int $$4 = this.m_6718_(p_76203_);
        if (!(p_76205_.m_76178_() || p_76206_.m_76178_() || p_76205_.m_61143_(f_75947_).booleanValue() || p_76206_.m_61143_(f_75947_).booleanValue() || !(p_76206_.m_76155_(p_76203_, p_76204_) > p_76205_.m_76155_(p_76203_, p_76204_)) || p_76203_.m_213780_().m_188503_(4) == 0)) {
            $$4 *= 4;
        }
        return $$4;
    }

    private void m_76212_(LevelAccessor p_76213_, BlockPos p_76214_) {
        p_76213_.m_46796_(1501, p_76214_, 0);
    }

    @Override
    protected boolean m_6760_() {
        return false;
    }

    @Override
    protected void m_6364_(LevelAccessor p_76220_, BlockPos p_76221_, BlockState p_76222_, Direction p_76223_, FluidState p_76224_) {
        if (p_76223_ == Direction.DOWN) {
            FluidState $$5 = p_76220_.m_6425_(p_76221_);
            if (this.m_205067_(FluidTags.f_13132_) && $$5.m_205070_(FluidTags.f_13131_)) {
                if (p_76222_.m_60734_() instanceof LiquidBlock) {
                    p_76220_.m_7731_(p_76221_, Blocks.f_50069_.m_49966_(), 3);
                }
                this.m_76212_(p_76220_, p_76221_);
                return;
            }
        }
        super.m_6364_(p_76220_, p_76221_, p_76222_, p_76223_, p_76224_);
    }

    @Override
    protected boolean m_6685_() {
        return true;
    }

    @Override
    protected float m_6752_() {
        return 100.0f;
    }

    @Override
    public Optional<SoundEvent> m_142520_() {
        return Optional.of(SoundEvents.f_11783_);
    }

    public static class Flowing
    extends LavaFluid {
        @Override
        protected void m_7180_(StateDefinition.Builder<Fluid, FluidState> p_76260_) {
            super.m_7180_(p_76260_);
            p_76260_.m_61104_(f_75948_);
        }

        @Override
        public int m_7430_(FluidState p_76264_) {
            return p_76264_.m_61143_(f_75948_);
        }

        @Override
        public boolean m_7444_(FluidState p_76262_) {
            return false;
        }
    }

    public static class Source
    extends LavaFluid {
        @Override
        public int m_7430_(FluidState p_76269_) {
            return 8;
        }

        @Override
        public boolean m_7444_(FluidState p_76267_) {
            return true;
        }
    }
}

