/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CampfireBlock
extends BaseEntityBlock
implements SimpleWaterloggedBlock {
    protected static final VoxelShape f_51226_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 7.0, 16.0);
    public static final BooleanProperty f_51227_ = BlockStateProperties.f_61443_;
    public static final BooleanProperty f_51228_ = BlockStateProperties.f_61450_;
    public static final BooleanProperty f_51229_ = BlockStateProperties.f_61362_;
    public static final DirectionProperty f_51230_ = BlockStateProperties.f_61374_;
    private static final VoxelShape f_51231_ = Block.m_49796_(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
    private static final int f_152748_ = 5;
    private final boolean f_51232_;
    private final int f_51233_;

    public CampfireBlock(boolean p_51236_, int p_51237_, BlockBehaviour.Properties p_51238_) {
        super(p_51238_);
        this.f_51232_ = p_51236_;
        this.f_51233_ = p_51237_;
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_51227_, true)).m_61124_(f_51228_, false)).m_61124_(f_51229_, false)).m_61124_(f_51230_, Direction.NORTH));
    }

    @Override
    public InteractionResult m_6227_(BlockState p_51274_, Level p_51275_, BlockPos p_51276_, Player p_51277_, InteractionHand p_51278_, BlockHitResult p_51279_) {
        ItemStack $$8;
        CampfireBlockEntity $$7;
        Optional<CampfireCookingRecipe> $$9;
        BlockEntity $$6 = p_51275_.m_7702_(p_51276_);
        if ($$6 instanceof CampfireBlockEntity && ($$9 = ($$7 = (CampfireBlockEntity)$$6).m_59051_($$8 = p_51277_.m_21120_(p_51278_))).isPresent()) {
            if (!p_51275_.f_46443_ && $$7.m_238284_(p_51277_, p_51277_.m_150110_().f_35937_ ? $$8.m_41777_() : $$8, $$9.get().m_43753_())) {
                p_51277_.m_36220_(Stats.f_12975_);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void m_7892_(BlockState p_51269_, Level p_51270_, BlockPos p_51271_, Entity p_51272_) {
        if (p_51269_.m_61143_(f_51227_).booleanValue() && p_51272_ instanceof LivingEntity && !EnchantmentHelper.m_44938_((LivingEntity)p_51272_)) {
            p_51272_.m_6469_(DamageSource.f_19305_, this.f_51233_);
        }
        super.m_7892_(p_51269_, p_51270_, p_51271_, p_51272_);
    }

    @Override
    public void m_6810_(BlockState p_51281_, Level p_51282_, BlockPos p_51283_, BlockState p_51284_, boolean p_51285_) {
        if (p_51281_.m_60713_(p_51284_.m_60734_())) {
            return;
        }
        BlockEntity $$5 = p_51282_.m_7702_(p_51283_);
        if ($$5 instanceof CampfireBlockEntity) {
            Containers.m_19010_(p_51282_, p_51283_, ((CampfireBlockEntity)$$5).m_59065_());
        }
        super.m_6810_(p_51281_, p_51282_, p_51283_, p_51284_, p_51285_);
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_51240_) {
        BlockPos $$2;
        Level $$1 = p_51240_.m_43725_();
        boolean $$3 = $$1.m_6425_($$2 = p_51240_.m_8083_()).m_76152_() == Fluids.f_76193_;
        return (BlockState)((BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_(f_51229_, $$3)).m_61124_(f_51228_, this.m_51323_($$1.m_8055_($$2.m_7495_())))).m_61124_(f_51227_, !$$3)).m_61124_(f_51230_, p_51240_.m_8125_());
    }

    @Override
    public BlockState m_7417_(BlockState p_51298_, Direction p_51299_, BlockState p_51300_, LevelAccessor p_51301_, BlockPos p_51302_, BlockPos p_51303_) {
        if (p_51298_.m_61143_(f_51229_).booleanValue()) {
            p_51301_.m_186469_(p_51302_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_51301_));
        }
        if (p_51299_ == Direction.DOWN) {
            return (BlockState)p_51298_.m_61124_(f_51228_, this.m_51323_(p_51300_));
        }
        return super.m_7417_(p_51298_, p_51299_, p_51300_, p_51301_, p_51302_, p_51303_);
    }

    private boolean m_51323_(BlockState p_51324_) {
        return p_51324_.m_60713_(Blocks.f_50335_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_51309_, BlockGetter p_51310_, BlockPos p_51311_, CollisionContext p_51312_) {
        return f_51226_;
    }

    @Override
    public RenderShape m_7514_(BlockState p_51307_) {
        return RenderShape.MODEL;
    }

    @Override
    public void m_214162_(BlockState p_220918_, Level p_220919_, BlockPos p_220920_, RandomSource p_220921_) {
        if (!p_220918_.m_61143_(f_51227_).booleanValue()) {
            return;
        }
        if (p_220921_.m_188503_(10) == 0) {
            p_220919_.m_7785_((double)p_220920_.m_123341_() + 0.5, (double)p_220920_.m_123342_() + 0.5, (double)p_220920_.m_123343_() + 0.5, SoundEvents.f_11784_, SoundSource.BLOCKS, 0.5f + p_220921_.m_188501_(), p_220921_.m_188501_() * 0.7f + 0.6f, false);
        }
        if (this.f_51232_ && p_220921_.m_188503_(5) == 0) {
            for (int $$4 = 0; $$4 < p_220921_.m_188503_(1) + 1; ++$$4) {
                p_220919_.m_7106_(ParticleTypes.f_123756_, (double)p_220920_.m_123341_() + 0.5, (double)p_220920_.m_123342_() + 0.5, (double)p_220920_.m_123343_() + 0.5, p_220921_.m_188501_() / 2.0f, 5.0E-5, p_220921_.m_188501_() / 2.0f);
            }
        }
    }

    public static void m_152749_(@Nullable Entity p_152750_, LevelAccessor p_152751_, BlockPos p_152752_, BlockState p_152753_) {
        BlockEntity $$5;
        if (p_152751_.m_5776_()) {
            for (int $$4 = 0; $$4 < 20; ++$$4) {
                CampfireBlock.m_51251_((Level)p_152751_, p_152752_, p_152753_.m_61143_(f_51228_), true);
            }
        }
        if (($$5 = p_152751_.m_7702_(p_152752_)) instanceof CampfireBlockEntity) {
            ((CampfireBlockEntity)$$5).m_59066_();
        }
        p_152751_.m_142346_(p_152750_, GameEvent.f_157792_, p_152752_);
    }

    @Override
    public boolean m_7361_(LevelAccessor p_51257_, BlockPos p_51258_, BlockState p_51259_, FluidState p_51260_) {
        if (!p_51259_.m_61143_(BlockStateProperties.f_61362_).booleanValue() && p_51260_.m_76152_() == Fluids.f_76193_) {
            boolean $$4 = p_51259_.m_61143_(f_51227_);
            if ($$4) {
                if (!p_51257_.m_5776_()) {
                    p_51257_.m_5594_(null, p_51258_, SoundEvents.f_11914_, SoundSource.BLOCKS, 1.0f, 1.0f);
                }
                CampfireBlock.m_152749_(null, p_51257_, p_51258_, p_51259_);
            }
            p_51257_.m_7731_(p_51258_, (BlockState)((BlockState)p_51259_.m_61124_(f_51229_, true)).m_61124_(f_51227_, false), 3);
            p_51257_.m_186469_(p_51258_, p_51260_.m_76152_(), p_51260_.m_76152_().m_6718_(p_51257_));
            return true;
        }
        return false;
    }

    @Override
    public void m_5581_(Level p_51244_, BlockState p_51245_, BlockHitResult p_51246_, Projectile p_51247_) {
        BlockPos $$4 = p_51246_.m_82425_();
        if (!p_51244_.f_46443_ && p_51247_.m_6060_() && p_51247_.m_142265_(p_51244_, $$4) && !p_51245_.m_61143_(f_51227_).booleanValue() && !p_51245_.m_61143_(f_51229_).booleanValue()) {
            p_51244_.m_7731_($$4, (BlockState)p_51245_.m_61124_(BlockStateProperties.f_61443_, true), 11);
        }
    }

    public static void m_51251_(Level p_51252_, BlockPos p_51253_, boolean p_51254_, boolean p_51255_) {
        RandomSource $$4 = p_51252_.m_213780_();
        SimpleParticleType $$5 = p_51254_ ? ParticleTypes.f_123778_ : ParticleTypes.f_123777_;
        p_51252_.m_6485_($$5, true, (double)p_51253_.m_123341_() + 0.5 + $$4.m_188500_() / 3.0 * (double)($$4.m_188499_() ? 1 : -1), (double)p_51253_.m_123342_() + $$4.m_188500_() + $$4.m_188500_(), (double)p_51253_.m_123343_() + 0.5 + $$4.m_188500_() / 3.0 * (double)($$4.m_188499_() ? 1 : -1), 0.0, 0.07, 0.0);
        if (p_51255_) {
            p_51252_.m_7106_(ParticleTypes.f_123762_, (double)p_51253_.m_123341_() + 0.5 + $$4.m_188500_() / 4.0 * (double)($$4.m_188499_() ? 1 : -1), (double)p_51253_.m_123342_() + 0.4, (double)p_51253_.m_123343_() + 0.5 + $$4.m_188500_() / 4.0 * (double)($$4.m_188499_() ? 1 : -1), 0.0, 0.005, 0.0);
        }
    }

    public static boolean m_51248_(Level p_51249_, BlockPos p_51250_) {
        for (int $$2 = 1; $$2 <= 5; ++$$2) {
            BlockPos $$3 = p_51250_.m_6625_($$2);
            BlockState $$4 = p_51249_.m_8055_($$3);
            if (CampfireBlock.m_51319_($$4)) {
                return true;
            }
            boolean $$5 = Shapes.m_83157_(f_51231_, $$4.m_60742_(p_51249_, p_51250_, CollisionContext.m_82749_()), BooleanOp.f_82689_);
            if (!$$5) continue;
            BlockState $$6 = p_51249_.m_8055_($$3.m_7495_());
            return CampfireBlock.m_51319_($$6);
        }
        return false;
    }

    public static boolean m_51319_(BlockState p_51320_) {
        return p_51320_.m_61138_(f_51227_) && p_51320_.m_204336_(BlockTags.f_13087_) && p_51320_.m_61143_(f_51227_) != false;
    }

    @Override
    public FluidState m_5888_(BlockState p_51318_) {
        if (p_51318_.m_61143_(f_51229_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_51318_);
    }

    @Override
    public BlockState m_6843_(BlockState p_51295_, Rotation p_51296_) {
        return (BlockState)p_51295_.m_61124_(f_51230_, p_51296_.m_55954_(p_51295_.m_61143_(f_51230_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_51292_, Mirror p_51293_) {
        return p_51292_.m_60717_(p_51293_.m_54846_(p_51292_.m_61143_(f_51230_)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51305_) {
        p_51305_.m_61104_(f_51227_, f_51228_, f_51229_, f_51230_);
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_152759_, BlockState p_152760_) {
        return new CampfireBlockEntity(p_152759_, p_152760_);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_152755_, BlockState p_152756_, BlockEntityType<T> p_152757_) {
        if (p_152755_.f_46443_) {
            if (p_152756_.m_61143_(f_51227_).booleanValue()) {
                return CampfireBlock.m_152132_(p_152757_, BlockEntityType.f_58911_, CampfireBlockEntity::m_155318_);
            }
        } else {
            if (p_152756_.m_61143_(f_51227_).booleanValue()) {
                return CampfireBlock.m_152132_(p_152757_, BlockEntityType.f_58911_, CampfireBlockEntity::m_155306_);
            }
            return CampfireBlock.m_152132_(p_152757_, BlockEntityType.f_58911_, CampfireBlockEntity::m_155313_);
        }
        return null;
    }

    @Override
    public boolean m_7357_(BlockState p_51264_, BlockGetter p_51265_, BlockPos p_51266_, PathComputationType p_51267_) {
        return false;
    }

    public static boolean m_51321_(BlockState p_51322_) {
        return p_51322_.m_204338_(BlockTags.f_13087_, p_51262_ -> p_51262_.m_61138_(f_51229_) && p_51262_.m_61138_(f_51227_)) && p_51322_.m_61143_(f_51229_) == false && p_51322_.m_61143_(f_51227_) == false;
    }
}

