/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BubbleColumnBlock
extends Block
implements BucketPickup {
    public static final BooleanProperty f_50956_ = BlockStateProperties.f_61430_;
    private static final int f_152700_ = 5;

    public BubbleColumnBlock(BlockBehaviour.Properties p_50959_) {
        super(p_50959_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_50956_, true));
    }

    @Override
    public void m_7892_(BlockState p_50976_, Level p_50977_, BlockPos p_50978_, Entity p_50979_) {
        BlockState $$4 = p_50977_.m_8055_(p_50978_.m_7494_());
        if ($$4.m_60795_()) {
            p_50979_.m_6845_(p_50976_.m_61143_(f_50956_));
            if (!p_50977_.f_46443_) {
                ServerLevel $$5 = (ServerLevel)p_50977_;
                for (int $$6 = 0; $$6 < 2; ++$$6) {
                    $$5.m_8767_(ParticleTypes.f_123769_, (double)p_50978_.m_123341_() + p_50977_.f_46441_.m_188500_(), p_50978_.m_123342_() + 1, (double)p_50978_.m_123343_() + p_50977_.f_46441_.m_188500_(), 1, 0.0, 0.0, 0.0, 1.0);
                    $$5.m_8767_(ParticleTypes.f_123795_, (double)p_50978_.m_123341_() + p_50977_.f_46441_.m_188500_(), p_50978_.m_123342_() + 1, (double)p_50978_.m_123343_() + p_50977_.f_46441_.m_188500_(), 1, 0.0, 0.01, 0.0, 0.2);
                }
            }
        } else {
            p_50979_.m_20321_(p_50976_.m_61143_(f_50956_));
        }
    }

    @Override
    public void m_213897_(BlockState p_220888_, ServerLevel p_220889_, BlockPos p_220890_, RandomSource p_220891_) {
        BubbleColumnBlock.m_152702_(p_220889_, p_220890_, p_220888_, p_220889_.m_8055_(p_220890_.m_7495_()));
    }

    @Override
    public FluidState m_5888_(BlockState p_51016_) {
        return Fluids.f_76193_.m_76068_(false);
    }

    public static void m_152707_(LevelAccessor p_152708_, BlockPos p_152709_, BlockState p_152710_) {
        BubbleColumnBlock.m_152702_(p_152708_, p_152709_, p_152708_.m_8055_(p_152709_), p_152710_);
    }

    public static void m_152702_(LevelAccessor p_152703_, BlockPos p_152704_, BlockState p_152705_, BlockState p_152706_) {
        if (!BubbleColumnBlock.m_152715_(p_152705_)) {
            return;
        }
        BlockState $$4 = BubbleColumnBlock.m_152717_(p_152706_);
        p_152703_.m_7731_(p_152704_, $$4, 2);
        BlockPos.MutableBlockPos $$5 = p_152704_.m_122032_().m_122173_(Direction.UP);
        while (BubbleColumnBlock.m_152715_(p_152703_.m_8055_($$5))) {
            if (!p_152703_.m_7731_($$5, $$4, 2)) {
                return;
            }
            $$5.m_122173_(Direction.UP);
        }
    }

    private static boolean m_152715_(BlockState p_152716_) {
        return p_152716_.m_60713_(Blocks.f_50628_) || p_152716_.m_60713_(Blocks.f_49990_) && p_152716_.m_60819_().m_76186_() >= 8 && p_152716_.m_60819_().m_76170_();
    }

    private static BlockState m_152717_(BlockState p_152718_) {
        if (p_152718_.m_60713_(Blocks.f_50628_)) {
            return p_152718_;
        }
        if (p_152718_.m_60713_(Blocks.f_50135_)) {
            return (BlockState)Blocks.f_50628_.m_49966_().m_61124_(f_50956_, false);
        }
        if (p_152718_.m_60713_(Blocks.f_50450_)) {
            return (BlockState)Blocks.f_50628_.m_49966_().m_61124_(f_50956_, true);
        }
        return Blocks.f_49990_.m_49966_();
    }

    @Override
    public void m_214162_(BlockState p_220893_, Level p_220894_, BlockPos p_220895_, RandomSource p_220896_) {
        double $$4 = p_220895_.m_123341_();
        double $$5 = p_220895_.m_123342_();
        double $$6 = p_220895_.m_123343_();
        if (p_220893_.m_61143_(f_50956_).booleanValue()) {
            p_220894_.m_7107_(ParticleTypes.f_123773_, $$4 + 0.5, $$5 + 0.8, $$6, 0.0, 0.0, 0.0);
            if (p_220896_.m_188503_(200) == 0) {
                p_220894_.m_7785_($$4, $$5, $$6, SoundEvents.f_11776_, SoundSource.BLOCKS, 0.2f + p_220896_.m_188501_() * 0.2f, 0.9f + p_220896_.m_188501_() * 0.15f, false);
            }
        } else {
            p_220894_.m_7107_(ParticleTypes.f_123774_, $$4 + 0.5, $$5, $$6 + 0.5, 0.0, 0.04, 0.0);
            p_220894_.m_7107_(ParticleTypes.f_123774_, $$4 + (double)p_220896_.m_188501_(), $$5 + (double)p_220896_.m_188501_(), $$6 + (double)p_220896_.m_188501_(), 0.0, 0.04, 0.0);
            if (p_220896_.m_188503_(200) == 0) {
                p_220894_.m_7785_($$4, $$5, $$6, SoundEvents.f_11774_, SoundSource.BLOCKS, 0.2f + p_220896_.m_188501_() * 0.2f, 0.9f + p_220896_.m_188501_() * 0.15f, false);
            }
        }
    }

    @Override
    public BlockState m_7417_(BlockState p_50990_, Direction p_50991_, BlockState p_50992_, LevelAccessor p_50993_, BlockPos p_50994_, BlockPos p_50995_) {
        p_50993_.m_186469_(p_50994_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_50993_));
        if (!p_50990_.m_60710_(p_50993_, p_50994_) || p_50991_ == Direction.DOWN || p_50991_ == Direction.UP && !p_50992_.m_60713_(Blocks.f_50628_) && BubbleColumnBlock.m_152715_(p_50992_)) {
            p_50993_.m_186460_(p_50994_, this, 5);
        }
        return super.m_7417_(p_50990_, p_50991_, p_50992_, p_50993_, p_50994_, p_50995_);
    }

    @Override
    public boolean m_7898_(BlockState p_50986_, LevelReader p_50987_, BlockPos p_50988_) {
        BlockState $$3 = p_50987_.m_8055_(p_50988_.m_7495_());
        return $$3.m_60713_(Blocks.f_50628_) || $$3.m_60713_(Blocks.f_50450_) || $$3.m_60713_(Blocks.f_50135_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_51005_, BlockGetter p_51006_, BlockPos p_51007_, CollisionContext p_51008_) {
        return Shapes.m_83040_();
    }

    @Override
    public RenderShape m_7514_(BlockState p_51003_) {
        return RenderShape.INVISIBLE;
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_50997_) {
        p_50997_.m_61104_(f_50956_);
    }

    @Override
    public ItemStack m_142598_(LevelAccessor p_152712_, BlockPos p_152713_, BlockState p_152714_) {
        p_152712_.m_7731_(p_152713_, Blocks.f_50016_.m_49966_(), 11);
        return new ItemStack(Items.f_42447_);
    }

    @Override
    public Optional<SoundEvent> m_142298_() {
        return Fluids.f_76193_.m_142520_();
    }
}

