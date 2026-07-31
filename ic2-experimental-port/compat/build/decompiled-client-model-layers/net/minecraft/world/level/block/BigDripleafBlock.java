/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  it.unimi.dsi.fastutil.objects.Object2IntArrayMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BigDripleafStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Tilt;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BigDripleafBlock
extends HorizontalDirectionalBlock
implements BonemealableBlock,
SimpleWaterloggedBlock {
    private static final BooleanProperty f_152200_ = BlockStateProperties.f_61362_;
    private static final EnumProperty<Tilt> f_152201_ = BlockStateProperties.f_155996_;
    private static final int f_152202_ = -1;
    private static final Object2IntMap<Tilt> f_152203_ = (Object2IntMap)Util.m_137469_(new Object2IntArrayMap(), p_152305_ -> {
        p_152305_.defaultReturnValue(-1);
        p_152305_.put((Object)Tilt.UNSTABLE, 10);
        p_152305_.put((Object)Tilt.PARTIAL, 10);
        p_152305_.put((Object)Tilt.FULL, 100);
    });
    private static final int f_152204_ = 5;
    private static final int f_152205_ = 6;
    private static final int f_152206_ = 11;
    private static final int f_152207_ = 13;
    private static final Map<Tilt, VoxelShape> f_152208_ = ImmutableMap.of((Object)Tilt.NONE, (Object)Block.m_49796_(0.0, 11.0, 0.0, 16.0, 15.0, 16.0), (Object)Tilt.UNSTABLE, (Object)Block.m_49796_(0.0, 11.0, 0.0, 16.0, 15.0, 16.0), (Object)Tilt.PARTIAL, (Object)Block.m_49796_(0.0, 11.0, 0.0, 16.0, 13.0, 16.0), (Object)Tilt.FULL, (Object)Shapes.m_83040_());
    private static final VoxelShape f_152209_ = Block.m_49796_(0.0, 13.0, 0.0, 16.0, 16.0, 16.0);
    private static final Map<Direction, VoxelShape> f_152210_ = ImmutableMap.of((Object)Direction.NORTH, (Object)Shapes.m_83148_(BigDripleafStemBlock.f_152321_, f_152209_, BooleanOp.f_82685_), (Object)Direction.SOUTH, (Object)Shapes.m_83148_(BigDripleafStemBlock.f_152322_, f_152209_, BooleanOp.f_82685_), (Object)Direction.EAST, (Object)Shapes.m_83148_(BigDripleafStemBlock.f_152323_, f_152209_, BooleanOp.f_82685_), (Object)Direction.WEST, (Object)Shapes.m_83148_(BigDripleafStemBlock.f_152324_, f_152209_, BooleanOp.f_82685_));
    private final Map<BlockState, VoxelShape> f_152211_;

    protected BigDripleafBlock(BlockBehaviour.Properties p_152214_) {
        super(p_152214_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_152200_, false)).m_61124_(f_54117_, Direction.NORTH)).m_61124_(f_152201_, Tilt.NONE));
        this.f_152211_ = this.m_152458_(BigDripleafBlock::m_152317_);
    }

    private static VoxelShape m_152317_(BlockState p_152318_) {
        return Shapes.m_83110_(f_152208_.get(p_152318_.m_61143_(f_152201_)), f_152210_.get(p_152318_.m_61143_(f_54117_)));
    }

    public static void m_220792_(LevelAccessor p_220793_, RandomSource p_220794_, BlockPos p_220795_, Direction p_220796_) {
        int $$6;
        int $$4 = Mth.m_216271_(p_220794_, 2, 5);
        BlockPos.MutableBlockPos $$5 = p_220795_.m_122032_();
        for ($$6 = 0; $$6 < $$4 && BigDripleafBlock.m_152251_(p_220793_, $$5, p_220793_.m_8055_($$5)); ++$$6) {
            $$5.m_122173_(Direction.UP);
        }
        int $$7 = p_220795_.m_123342_() + $$6 - 1;
        $$5.m_142448_(p_220795_.m_123342_());
        while ($$5.m_123342_() < $$7) {
            BigDripleafStemBlock.m_152349_(p_220793_, $$5, p_220793_.m_6425_($$5), p_220796_);
            $$5.m_122173_(Direction.UP);
        }
        BigDripleafBlock.m_152241_(p_220793_, $$5, p_220793_.m_6425_($$5), p_220796_);
    }

    private static boolean m_152319_(BlockState p_152320_) {
        return p_152320_.m_60795_() || p_152320_.m_60713_(Blocks.f_49990_) || p_152320_.m_60713_(Blocks.f_152547_);
    }

    protected static boolean m_152251_(LevelHeightAccessor p_152252_, BlockPos p_152253_, BlockState p_152254_) {
        return !p_152252_.m_151570_(p_152253_) && BigDripleafBlock.m_152319_(p_152254_);
    }

    protected static boolean m_152241_(LevelAccessor p_152242_, BlockPos p_152243_, FluidState p_152244_, Direction p_152245_) {
        BlockState $$4 = (BlockState)((BlockState)Blocks.f_152545_.m_49966_().m_61124_(f_152200_, p_152244_.m_164512_(Fluids.f_76193_))).m_61124_(f_54117_, p_152245_);
        return p_152242_.m_7731_(p_152243_, $$4, 3);
    }

    @Override
    public void m_5581_(Level p_152228_, BlockState p_152229_, BlockHitResult p_152230_, Projectile p_152231_) {
        this.m_152282_(p_152229_, p_152228_, p_152230_.m_82425_(), Tilt.FULL, SoundEvents.f_144131_);
    }

    @Override
    public FluidState m_5888_(BlockState p_152312_) {
        if (p_152312_.m_61143_(f_152200_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_152312_);
    }

    @Override
    public boolean m_7898_(BlockState p_152289_, LevelReader p_152290_, BlockPos p_152291_) {
        BlockPos $$3 = p_152291_.m_7495_();
        BlockState $$4 = p_152290_.m_8055_($$3);
        return $$4.m_60713_(this) || $$4.m_60713_(Blocks.f_152546_) || $$4.m_204336_(BlockTags.f_184227_);
    }

    @Override
    public BlockState m_7417_(BlockState p_152293_, Direction p_152294_, BlockState p_152295_, LevelAccessor p_152296_, BlockPos p_152297_, BlockPos p_152298_) {
        if (p_152294_ == Direction.DOWN && !p_152293_.m_60710_(p_152296_, p_152297_)) {
            return Blocks.f_50016_.m_49966_();
        }
        if (p_152293_.m_61143_(f_152200_).booleanValue()) {
            p_152296_.m_186469_(p_152297_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_152296_));
        }
        if (p_152294_ == Direction.UP && p_152295_.m_60713_(this)) {
            return Blocks.f_152546_.m_152465_(p_152293_);
        }
        return super.m_7417_(p_152293_, p_152294_, p_152295_, p_152296_, p_152297_, p_152298_);
    }

    @Override
    public boolean m_7370_(BlockGetter p_152223_, BlockPos p_152224_, BlockState p_152225_, boolean p_152226_) {
        BlockState $$4 = p_152223_.m_8055_(p_152224_.m_7494_());
        return BigDripleafBlock.m_152319_($$4);
    }

    @Override
    public boolean m_214167_(Level p_220788_, RandomSource p_220789_, BlockPos p_220790_, BlockState p_220791_) {
        return true;
    }

    @Override
    public void m_214148_(ServerLevel p_220783_, RandomSource p_220784_, BlockPos p_220785_, BlockState p_220786_) {
        BlockState $$5;
        BlockPos $$4 = p_220785_.m_7494_();
        if (BigDripleafBlock.m_152251_(p_220783_, $$4, $$5 = p_220783_.m_8055_($$4))) {
            Direction $$6 = p_220786_.m_61143_(f_54117_);
            BigDripleafStemBlock.m_152349_(p_220783_, p_220785_, p_220786_.m_60819_(), $$6);
            BigDripleafBlock.m_152241_(p_220783_, $$4, $$5.m_60819_(), $$6);
        }
    }

    @Override
    public void m_7892_(BlockState p_152266_, Level p_152267_, BlockPos p_152268_, Entity p_152269_) {
        if (p_152267_.f_46443_) {
            return;
        }
        if (p_152266_.m_61143_(f_152201_) == Tilt.NONE && BigDripleafBlock.m_152301_(p_152268_, p_152269_) && !p_152267_.m_46753_(p_152268_)) {
            this.m_152282_(p_152266_, p_152267_, p_152268_, Tilt.UNSTABLE, null);
        }
    }

    @Override
    public void m_213897_(BlockState p_220798_, ServerLevel p_220799_, BlockPos p_220800_, RandomSource p_220801_) {
        if (p_220799_.m_46753_(p_220800_)) {
            BigDripleafBlock.m_152313_(p_220798_, p_220799_, p_220800_);
            return;
        }
        Tilt $$4 = p_220798_.m_61143_(f_152201_);
        if ($$4 == Tilt.UNSTABLE) {
            this.m_152282_(p_220798_, p_220799_, p_220800_, Tilt.PARTIAL, SoundEvents.f_144131_);
        } else if ($$4 == Tilt.PARTIAL) {
            this.m_152282_(p_220798_, p_220799_, p_220800_, Tilt.FULL, SoundEvents.f_144131_);
        } else if ($$4 == Tilt.FULL) {
            BigDripleafBlock.m_152313_(p_220798_, p_220799_, p_220800_);
        }
    }

    @Override
    public void m_6861_(BlockState p_152271_, Level p_152272_, BlockPos p_152273_, Block p_152274_, BlockPos p_152275_, boolean p_152276_) {
        if (p_152272_.m_46753_(p_152273_)) {
            BigDripleafBlock.m_152313_(p_152271_, p_152272_, p_152273_);
        }
    }

    private static void m_152232_(Level p_152233_, BlockPos p_152234_, SoundEvent p_152235_) {
        float $$3 = Mth.m_216283_(p_152233_.f_46441_, 0.8f, 1.2f);
        p_152233_.m_5594_(null, p_152234_, p_152235_, SoundSource.BLOCKS, 1.0f, $$3);
    }

    private static boolean m_152301_(BlockPos p_152302_, Entity p_152303_) {
        return p_152303_.m_20096_() && p_152303_.m_20182_().f_82480_ > (double)((float)p_152302_.m_123342_() + 0.6875f);
    }

    private void m_152282_(BlockState p_152283_, Level p_152284_, BlockPos p_152285_, Tilt p_152286_, @Nullable SoundEvent p_152287_) {
        int $$5;
        BigDripleafBlock.m_152277_(p_152283_, p_152284_, p_152285_, p_152286_);
        if (p_152287_ != null) {
            BigDripleafBlock.m_152232_(p_152284_, p_152285_, p_152287_);
        }
        if (($$5 = f_152203_.getInt((Object)p_152286_)) != -1) {
            p_152284_.m_186460_(p_152285_, this, $$5);
        }
    }

    private static void m_152313_(BlockState p_152314_, Level p_152315_, BlockPos p_152316_) {
        BigDripleafBlock.m_152277_(p_152314_, p_152315_, p_152316_, Tilt.NONE);
        if (p_152314_.m_61143_(f_152201_) != Tilt.NONE) {
            BigDripleafBlock.m_152232_(p_152315_, p_152316_, SoundEvents.f_144132_);
        }
    }

    private static void m_152277_(BlockState p_152278_, Level p_152279_, BlockPos p_152280_, Tilt p_152281_) {
        Tilt $$4 = p_152278_.m_61143_(f_152201_);
        p_152279_.m_7731_(p_152280_, (BlockState)p_152278_.m_61124_(f_152201_, p_152281_), 2);
        if (p_152281_.m_156084_() && p_152281_ != $$4) {
            p_152279_.m_142346_(null, GameEvent.f_157792_, p_152280_);
        }
    }

    @Override
    public VoxelShape m_5939_(BlockState p_152307_, BlockGetter p_152308_, BlockPos p_152309_, CollisionContext p_152310_) {
        return f_152208_.get(p_152307_.m_61143_(f_152201_));
    }

    @Override
    public VoxelShape m_5940_(BlockState p_152261_, BlockGetter p_152262_, BlockPos p_152263_, CollisionContext p_152264_) {
        return this.f_152211_.get(p_152261_);
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_152221_) {
        BlockState $$1 = p_152221_.m_43725_().m_8055_(p_152221_.m_8083_().m_7495_());
        FluidState $$2 = p_152221_.m_43725_().m_6425_(p_152221_.m_8083_());
        boolean $$3 = $$1.m_60713_(Blocks.f_152545_) || $$1.m_60713_(Blocks.f_152546_);
        return (BlockState)((BlockState)this.m_49966_().m_61124_(f_152200_, $$2.m_164512_(Fluids.f_76193_))).m_61124_(f_54117_, $$3 ? $$1.m_61143_(f_54117_) : p_152221_.m_8125_().m_122424_());
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_152300_) {
        p_152300_.m_61104_(f_152200_, f_54117_, f_152201_);
    }
}

