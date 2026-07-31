/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 */
package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableList;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class RespawnAnchorBlock
extends Block {
    public static final int f_154330_ = 0;
    public static final int f_154331_ = 4;
    public static final IntegerProperty f_55833_ = BlockStateProperties.f_61389_;
    private static final ImmutableList<Vec3i> f_55834_ = ImmutableList.of((Object)new Vec3i(0, 0, -1), (Object)new Vec3i(-1, 0, 0), (Object)new Vec3i(0, 0, 1), (Object)new Vec3i(1, 0, 0), (Object)new Vec3i(-1, 0, -1), (Object)new Vec3i(1, 0, -1), (Object)new Vec3i(-1, 0, 1), (Object)new Vec3i(1, 0, 1));
    private static final ImmutableList<Vec3i> f_55835_ = new ImmutableList.Builder().addAll(f_55834_).addAll(f_55834_.stream().map(Vec3i::m_7495_).iterator()).addAll(f_55834_.stream().map(Vec3i::m_7494_).iterator()).add((Object)new Vec3i(0, 1, 0)).build();

    public RespawnAnchorBlock(BlockBehaviour.Properties p_55838_) {
        super(p_55838_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_55833_, 0));
    }

    @Override
    public InteractionResult m_6227_(BlockState p_55874_, Level p_55875_, BlockPos p_55876_, Player p_55877_, InteractionHand p_55878_, BlockHitResult p_55879_) {
        ItemStack $$6 = p_55877_.m_21120_(p_55878_);
        if (p_55878_ == InteractionHand.MAIN_HAND && !RespawnAnchorBlock.m_55848_($$6) && RespawnAnchorBlock.m_55848_(p_55877_.m_21120_(InteractionHand.OFF_HAND))) {
            return InteractionResult.PASS;
        }
        if (RespawnAnchorBlock.m_55848_($$6) && RespawnAnchorBlock.m_55894_(p_55874_)) {
            RespawnAnchorBlock.m_55855_(p_55875_, p_55876_, p_55874_);
            if (!p_55877_.m_150110_().f_35937_) {
                $$6.m_41774_(1);
            }
            return InteractionResult.m_19078_(p_55875_.f_46443_);
        }
        if (p_55874_.m_61143_(f_55833_) == 0) {
            return InteractionResult.PASS;
        }
        if (RespawnAnchorBlock.m_55850_(p_55875_)) {
            ServerPlayer $$7;
            if (!(p_55875_.f_46443_ || ($$7 = (ServerPlayer)p_55877_).m_8963_() == p_55875_.m_46472_() && p_55876_.equals($$7.m_8961_()))) {
                $$7.m_9158_(p_55875_.m_46472_(), p_55876_, 0.0f, false, true);
                p_55875_.m_6263_(null, (double)p_55876_.m_123341_() + 0.5, (double)p_55876_.m_123342_() + 0.5, (double)p_55876_.m_123343_() + 0.5, SoundEvents.f_12326_, SoundSource.BLOCKS, 1.0f, 1.0f);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.CONSUME;
        }
        if (!p_55875_.f_46443_) {
            this.m_55890_(p_55874_, p_55875_, p_55876_);
        }
        return InteractionResult.m_19078_(p_55875_.f_46443_);
    }

    private static boolean m_55848_(ItemStack p_55849_) {
        return p_55849_.m_150930_(Items.f_42054_);
    }

    private static boolean m_55894_(BlockState p_55895_) {
        return p_55895_.m_61143_(f_55833_) < 4;
    }

    private static boolean m_55887_(BlockPos p_55888_, Level p_55889_) {
        FluidState $$2 = p_55889_.m_6425_(p_55888_);
        if (!$$2.m_205070_(FluidTags.f_13131_)) {
            return false;
        }
        if ($$2.m_76170_()) {
            return true;
        }
        float $$3 = $$2.m_76186_();
        if ($$3 < 2.0f) {
            return false;
        }
        FluidState $$4 = p_55889_.m_6425_(p_55888_.m_7495_());
        return !$$4.m_205070_(FluidTags.f_13131_);
    }

    private void m_55890_(BlockState p_55891_, Level p_55892_, final BlockPos p_55893_) {
        p_55892_.m_7471_(p_55893_, false);
        boolean $$3 = Direction.Plane.HORIZONTAL.m_122557_().map(p_55893_::m_121945_).anyMatch(p_55854_ -> RespawnAnchorBlock.m_55887_(p_55854_, p_55892_));
        final boolean $$4 = $$3 || p_55892_.m_6425_(p_55893_.m_7494_()).m_205070_(FluidTags.f_13131_);
        ExplosionDamageCalculator $$5 = new ExplosionDamageCalculator(){

            @Override
            public Optional<Float> m_6617_(Explosion p_55904_, BlockGetter p_55905_, BlockPos p_55906_, BlockState p_55907_, FluidState p_55908_) {
                if (p_55906_.equals(p_55893_) && $$4) {
                    return Optional.of(Float.valueOf(Blocks.f_49990_.m_7325_()));
                }
                return super.m_6617_(p_55904_, p_55905_, p_55906_, p_55907_, p_55908_);
            }
        };
        p_55892_.m_7703_(null, DamageSource.m_19334_(), $$5, (double)p_55893_.m_123341_() + 0.5, (double)p_55893_.m_123342_() + 0.5, (double)p_55893_.m_123343_() + 0.5, 5.0f, true, Explosion.BlockInteraction.DESTROY);
    }

    public static boolean m_55850_(Level p_55851_) {
        return p_55851_.m_6042_().f_63863_();
    }

    public static void m_55855_(Level p_55856_, BlockPos p_55857_, BlockState p_55858_) {
        p_55856_.m_7731_(p_55857_, (BlockState)p_55858_.m_61124_(f_55833_, p_55858_.m_61143_(f_55833_) + 1), 3);
        p_55856_.m_6263_(null, (double)p_55857_.m_123341_() + 0.5, (double)p_55857_.m_123342_() + 0.5, (double)p_55857_.m_123343_() + 0.5, SoundEvents.f_12376_, SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    @Override
    public void m_214162_(BlockState p_221969_, Level p_221970_, BlockPos p_221971_, RandomSource p_221972_) {
        if (p_221969_.m_61143_(f_55833_) == 0) {
            return;
        }
        if (p_221972_.m_188503_(100) == 0) {
            p_221970_.m_6263_(null, (double)p_221971_.m_123341_() + 0.5, (double)p_221971_.m_123342_() + 0.5, (double)p_221971_.m_123343_() + 0.5, SoundEvents.f_12375_, SoundSource.BLOCKS, 1.0f, 1.0f);
        }
        double $$4 = (double)p_221971_.m_123341_() + 0.5 + (0.5 - p_221972_.m_188500_());
        double $$5 = (double)p_221971_.m_123342_() + 1.0;
        double $$6 = (double)p_221971_.m_123343_() + 0.5 + (0.5 - p_221972_.m_188500_());
        double $$7 = (double)p_221972_.m_188501_() * 0.04;
        p_221970_.m_7106_(ParticleTypes.f_123789_, $$4, $$5, $$6, 0.0, $$7, 0.0);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_55886_) {
        p_55886_.m_61104_(f_55833_);
    }

    @Override
    public boolean m_7278_(BlockState p_55860_) {
        return true;
    }

    public static int m_55861_(BlockState p_55862_, int p_55863_) {
        return Mth.m_14143_((float)(p_55862_.m_61143_(f_55833_) - 0) / 4.0f * (float)p_55863_);
    }

    @Override
    public int m_6782_(BlockState p_55870_, Level p_55871_, BlockPos p_55872_) {
        return RespawnAnchorBlock.m_55861_(p_55870_, 15);
    }

    public static Optional<Vec3> m_55839_(EntityType<?> p_55840_, CollisionGetter p_55841_, BlockPos p_55842_) {
        Optional<Vec3> $$3 = RespawnAnchorBlock.m_55843_(p_55840_, p_55841_, p_55842_, true);
        if ($$3.isPresent()) {
            return $$3;
        }
        return RespawnAnchorBlock.m_55843_(p_55840_, p_55841_, p_55842_, false);
    }

    private static Optional<Vec3> m_55843_(EntityType<?> p_55844_, CollisionGetter p_55845_, BlockPos p_55846_, boolean p_55847_) {
        BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
        for (Vec3i $$5 : f_55835_) {
            $$4.m_122190_(p_55846_).m_122193_($$5);
            Vec3 $$6 = DismountHelper.m_38441_(p_55844_, p_55845_, $$4, p_55847_);
            if ($$6 == null) continue;
            return Optional.of($$6);
        }
        return Optional.empty();
    }

    @Override
    public boolean m_7357_(BlockState p_55865_, BlockGetter p_55866_, BlockPos p_55867_, PathComputationType p_55868_) {
        return false;
    }
}

