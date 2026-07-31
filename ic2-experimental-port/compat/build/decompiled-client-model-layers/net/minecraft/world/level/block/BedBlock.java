/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.ArrayUtils
 */
package net.minecraft.world.level.block;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.ArrayUtils;

public class BedBlock
extends HorizontalDirectionalBlock
implements EntityBlock {
    public static final EnumProperty<BedPart> f_49440_ = BlockStateProperties.f_61391_;
    public static final BooleanProperty f_49441_ = BlockStateProperties.f_61445_;
    protected static final int f_152166_ = 9;
    protected static final VoxelShape f_49442_ = Block.m_49796_(0.0, 3.0, 0.0, 16.0, 9.0, 16.0);
    private static final int f_152167_ = 3;
    protected static final VoxelShape f_49443_ = Block.m_49796_(0.0, 0.0, 0.0, 3.0, 3.0, 3.0);
    protected static final VoxelShape f_49444_ = Block.m_49796_(0.0, 0.0, 13.0, 3.0, 3.0, 16.0);
    protected static final VoxelShape f_49445_ = Block.m_49796_(13.0, 0.0, 0.0, 16.0, 3.0, 3.0);
    protected static final VoxelShape f_49446_ = Block.m_49796_(13.0, 0.0, 13.0, 16.0, 3.0, 16.0);
    protected static final VoxelShape f_49447_ = Shapes.m_83124_(f_49442_, f_49443_, f_49445_);
    protected static final VoxelShape f_49448_ = Shapes.m_83124_(f_49442_, f_49444_, f_49446_);
    protected static final VoxelShape f_49449_ = Shapes.m_83124_(f_49442_, f_49443_, f_49444_);
    protected static final VoxelShape f_49450_ = Shapes.m_83124_(f_49442_, f_49445_, f_49446_);
    private final DyeColor f_49451_;

    public BedBlock(DyeColor p_49454_, BlockBehaviour.Properties p_49455_) {
        super(p_49455_);
        this.f_49451_ = p_49454_;
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_49440_, BedPart.FOOT)).m_61124_(f_49441_, false));
    }

    @Nullable
    public static Direction m_49485_(BlockGetter p_49486_, BlockPos p_49487_) {
        BlockState $$2 = p_49486_.m_8055_(p_49487_);
        return $$2.m_60734_() instanceof BedBlock ? $$2.m_61143_(f_54117_) : null;
    }

    @Override
    public InteractionResult m_6227_(BlockState p_49515_, Level p_49516_, BlockPos p_49517_, Player p_49518_, InteractionHand p_49519_, BlockHitResult p_49520_) {
        if (p_49516_.f_46443_) {
            return InteractionResult.CONSUME;
        }
        if (p_49515_.m_61143_(f_49440_) != BedPart.HEAD && !(p_49515_ = p_49516_.m_8055_(p_49517_ = p_49517_.m_121945_(p_49515_.m_61143_(f_54117_)))).m_60713_(this)) {
            return InteractionResult.CONSUME;
        }
        if (!BedBlock.m_49488_(p_49516_)) {
            p_49516_.m_7471_(p_49517_, false);
            BlockPos $$6 = p_49517_.m_121945_(p_49515_.m_61143_(f_54117_).m_122424_());
            if (p_49516_.m_8055_($$6).m_60713_(this)) {
                p_49516_.m_7471_($$6, false);
            }
            p_49516_.m_7703_(null, DamageSource.m_19334_(), null, (double)p_49517_.m_123341_() + 0.5, (double)p_49517_.m_123342_() + 0.5, (double)p_49517_.m_123343_() + 0.5, 5.0f, true, Explosion.BlockInteraction.DESTROY);
            return InteractionResult.SUCCESS;
        }
        if (p_49515_.m_61143_(f_49441_).booleanValue()) {
            if (!this.m_49490_(p_49516_, p_49517_)) {
                p_49518_.m_5661_(Component.m_237115_("block.minecraft.bed.occupied"), true);
            }
            return InteractionResult.SUCCESS;
        }
        p_49518_.m_7720_(p_49517_).ifLeft(p_49477_ -> {
            if (p_49477_.m_36423_() != null) {
                p_49518_.m_5661_(p_49477_.m_36423_(), true);
            }
        });
        return InteractionResult.SUCCESS;
    }

    public static boolean m_49488_(Level p_49489_) {
        return p_49489_.m_6042_().f_63862_();
    }

    private boolean m_49490_(Level p_49491_, BlockPos p_49492_) {
        List<Villager> $$2 = p_49491_.m_6443_(Villager.class, new AABB(p_49492_), LivingEntity::m_5803_);
        if ($$2.isEmpty()) {
            return false;
        }
        $$2.get(0).m_5796_();
        return true;
    }

    @Override
    public void m_142072_(Level p_152169_, BlockState p_152170_, BlockPos p_152171_, Entity p_152172_, float p_152173_) {
        super.m_142072_(p_152169_, p_152170_, p_152171_, p_152172_, p_152173_ * 0.5f);
    }

    @Override
    public void m_5548_(BlockGetter p_49483_, Entity p_49484_) {
        if (p_49484_.m_20162_()) {
            super.m_5548_(p_49483_, p_49484_);
        } else {
            this.m_49456_(p_49484_);
        }
    }

    private void m_49456_(Entity p_49457_) {
        Vec3 $$1 = p_49457_.m_20184_();
        if ($$1.f_82480_ < 0.0) {
            double $$2 = p_49457_ instanceof LivingEntity ? 1.0 : 0.8;
            p_49457_.m_20334_($$1.f_82479_, -$$1.f_82480_ * (double)0.66f * $$2, $$1.f_82481_);
        }
    }

    @Override
    public BlockState m_7417_(BlockState p_49525_, Direction p_49526_, BlockState p_49527_, LevelAccessor p_49528_, BlockPos p_49529_, BlockPos p_49530_) {
        if (p_49526_ == BedBlock.m_49533_(p_49525_.m_61143_(f_49440_), p_49525_.m_61143_(f_54117_))) {
            if (p_49527_.m_60713_(this) && p_49527_.m_61143_(f_49440_) != p_49525_.m_61143_(f_49440_)) {
                return (BlockState)p_49525_.m_61124_(f_49441_, p_49527_.m_61143_(f_49441_));
            }
            return Blocks.f_50016_.m_49966_();
        }
        return super.m_7417_(p_49525_, p_49526_, p_49527_, p_49528_, p_49529_, p_49530_);
    }

    private static Direction m_49533_(BedPart p_49534_, Direction p_49535_) {
        return p_49534_ == BedPart.FOOT ? p_49535_ : p_49535_.m_122424_();
    }

    @Override
    public void m_5707_(Level p_49505_, BlockPos p_49506_, BlockState p_49507_, Player p_49508_) {
        BlockPos $$5;
        BlockState $$6;
        BedPart $$4;
        if (!p_49505_.f_46443_ && p_49508_.m_7500_() && ($$4 = p_49507_.m_61143_(f_49440_)) == BedPart.FOOT && ($$6 = p_49505_.m_8055_($$5 = p_49506_.m_121945_(BedBlock.m_49533_($$4, p_49507_.m_61143_(f_54117_))))).m_60713_(this) && $$6.m_61143_(f_49440_) == BedPart.HEAD) {
            p_49505_.m_7731_($$5, Blocks.f_50016_.m_49966_(), 35);
            p_49505_.m_5898_(p_49508_, 2001, $$5, Block.m_49956_($$6));
        }
        super.m_5707_(p_49505_, p_49506_, p_49507_, p_49508_);
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_49479_) {
        Direction $$1 = p_49479_.m_8125_();
        BlockPos $$2 = p_49479_.m_8083_();
        BlockPos $$3 = $$2.m_121945_($$1);
        Level $$4 = p_49479_.m_43725_();
        if ($$4.m_8055_($$3).m_60629_(p_49479_) && $$4.m_6857_().m_61937_($$3)) {
            return (BlockState)this.m_49966_().m_61124_(f_54117_, $$1);
        }
        return null;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_49547_, BlockGetter p_49548_, BlockPos p_49549_, CollisionContext p_49550_) {
        Direction $$4 = BedBlock.m_49557_(p_49547_).m_122424_();
        switch ($$4) {
            case NORTH: {
                return f_49447_;
            }
            case SOUTH: {
                return f_49448_;
            }
            case WEST: {
                return f_49449_;
            }
        }
        return f_49450_;
    }

    public static Direction m_49557_(BlockState p_49558_) {
        Direction $$1 = p_49558_.m_61143_(f_54117_);
        return p_49558_.m_61143_(f_49440_) == BedPart.HEAD ? $$1.m_122424_() : $$1;
    }

    public static DoubleBlockCombiner.BlockType m_49559_(BlockState p_49560_) {
        BedPart $$1 = p_49560_.m_61143_(f_49440_);
        if ($$1 == BedPart.HEAD) {
            return DoubleBlockCombiner.BlockType.FIRST;
        }
        return DoubleBlockCombiner.BlockType.SECOND;
    }

    private static boolean m_49541_(BlockGetter p_49542_, BlockPos p_49543_) {
        return p_49542_.m_8055_(p_49543_.m_7495_()).m_60734_() instanceof BedBlock;
    }

    public static Optional<Vec3> m_49458_(EntityType<?> p_49459_, CollisionGetter p_49460_, BlockPos p_49461_, float p_49462_) {
        Direction $$6;
        Direction $$4 = p_49460_.m_8055_(p_49461_).m_61143_(f_54117_);
        Direction $$5 = $$4.m_122427_();
        Direction direction = $$6 = $$5.m_122370_(p_49462_) ? $$5.m_122424_() : $$5;
        if (BedBlock.m_49541_(p_49460_, p_49461_)) {
            return BedBlock.m_49463_(p_49459_, p_49460_, p_49461_, $$4, $$6);
        }
        int[][] $$7 = BedBlock.m_49538_($$4, $$6);
        Optional<Vec3> $$8 = BedBlock.m_49469_(p_49459_, p_49460_, p_49461_, $$7, true);
        if ($$8.isPresent()) {
            return $$8;
        }
        return BedBlock.m_49469_(p_49459_, p_49460_, p_49461_, $$7, false);
    }

    private static Optional<Vec3> m_49463_(EntityType<?> p_49464_, CollisionGetter p_49465_, BlockPos p_49466_, Direction p_49467_, Direction p_49468_) {
        int[][] $$5 = BedBlock.m_49551_(p_49467_, p_49468_);
        Optional<Vec3> $$6 = BedBlock.m_49469_(p_49464_, p_49465_, p_49466_, $$5, true);
        if ($$6.isPresent()) {
            return $$6;
        }
        BlockPos $$7 = p_49466_.m_7495_();
        Optional<Vec3> $$8 = BedBlock.m_49469_(p_49464_, p_49465_, $$7, $$5, true);
        if ($$8.isPresent()) {
            return $$8;
        }
        int[][] $$9 = BedBlock.m_49536_(p_49467_);
        Optional<Vec3> $$10 = BedBlock.m_49469_(p_49464_, p_49465_, p_49466_, $$9, true);
        if ($$10.isPresent()) {
            return $$10;
        }
        Optional<Vec3> $$11 = BedBlock.m_49469_(p_49464_, p_49465_, p_49466_, $$5, false);
        if ($$11.isPresent()) {
            return $$11;
        }
        Optional<Vec3> $$12 = BedBlock.m_49469_(p_49464_, p_49465_, $$7, $$5, false);
        if ($$12.isPresent()) {
            return $$12;
        }
        return BedBlock.m_49469_(p_49464_, p_49465_, p_49466_, $$9, false);
    }

    private static Optional<Vec3> m_49469_(EntityType<?> p_49470_, CollisionGetter p_49471_, BlockPos p_49472_, int[][] p_49473_, boolean p_49474_) {
        BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos();
        for (int[] $$6 : p_49473_) {
            $$5.m_122178_(p_49472_.m_123341_() + $$6[0], p_49472_.m_123342_(), p_49472_.m_123343_() + $$6[1]);
            Vec3 $$7 = DismountHelper.m_38441_(p_49470_, p_49471_, $$5, p_49474_);
            if ($$7 == null) continue;
            return Optional.of($$7);
        }
        return Optional.empty();
    }

    @Override
    public PushReaction m_5537_(BlockState p_49556_) {
        return PushReaction.DESTROY;
    }

    @Override
    public RenderShape m_7514_(BlockState p_49545_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_49532_) {
        p_49532_.m_61104_(f_54117_, f_49440_, f_49441_);
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_152175_, BlockState p_152176_) {
        return new BedBlockEntity(p_152175_, p_152176_, this.f_49451_);
    }

    @Override
    public void m_6402_(Level p_49499_, BlockPos p_49500_, BlockState p_49501_, @Nullable LivingEntity p_49502_, ItemStack p_49503_) {
        super.m_6402_(p_49499_, p_49500_, p_49501_, p_49502_, p_49503_);
        if (!p_49499_.f_46443_) {
            BlockPos $$5 = p_49500_.m_121945_(p_49501_.m_61143_(f_54117_));
            p_49499_.m_7731_($$5, (BlockState)p_49501_.m_61124_(f_49440_, BedPart.HEAD), 3);
            p_49499_.m_6289_(p_49500_, Blocks.f_50016_);
            p_49501_.m_60701_(p_49499_, p_49500_, 3);
        }
    }

    public DyeColor m_49554_() {
        return this.f_49451_;
    }

    @Override
    public long m_7799_(BlockState p_49522_, BlockPos p_49523_) {
        BlockPos $$2 = p_49523_.m_5484_(p_49522_.m_61143_(f_54117_), p_49522_.m_61143_(f_49440_) == BedPart.HEAD ? 0 : 1);
        return Mth.m_14130_($$2.m_123341_(), p_49523_.m_123342_(), $$2.m_123343_());
    }

    @Override
    public boolean m_7357_(BlockState p_49510_, BlockGetter p_49511_, BlockPos p_49512_, PathComputationType p_49513_) {
        return false;
    }

    private static int[][] m_49538_(Direction p_49539_, Direction p_49540_) {
        return (int[][])ArrayUtils.addAll((Object[])BedBlock.m_49551_(p_49539_, p_49540_), (Object[])BedBlock.m_49536_(p_49539_));
    }

    private static int[][] m_49551_(Direction p_49552_, Direction p_49553_) {
        return new int[][]{{p_49553_.m_122429_(), p_49553_.m_122431_()}, {p_49553_.m_122429_() - p_49552_.m_122429_(), p_49553_.m_122431_() - p_49552_.m_122431_()}, {p_49553_.m_122429_() - p_49552_.m_122429_() * 2, p_49553_.m_122431_() - p_49552_.m_122431_() * 2}, {-p_49552_.m_122429_() * 2, -p_49552_.m_122431_() * 2}, {-p_49553_.m_122429_() - p_49552_.m_122429_() * 2, -p_49553_.m_122431_() - p_49552_.m_122431_() * 2}, {-p_49553_.m_122429_() - p_49552_.m_122429_(), -p_49553_.m_122431_() - p_49552_.m_122431_()}, {-p_49553_.m_122429_(), -p_49553_.m_122431_()}, {-p_49553_.m_122429_() + p_49552_.m_122429_(), -p_49553_.m_122431_() + p_49552_.m_122431_()}, {p_49552_.m_122429_(), p_49552_.m_122431_()}, {p_49553_.m_122429_() + p_49552_.m_122429_(), p_49553_.m_122431_() + p_49552_.m_122431_()}};
    }

    private static int[][] m_49536_(Direction p_49537_) {
        return new int[][]{{0, 0}, {-p_49537_.m_122429_(), -p_49537_.m_122431_()}};
    }
}

