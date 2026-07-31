/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.Optional;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BigDripleafBlock;
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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BigDripleafStemBlock
extends HorizontalDirectionalBlock
implements BonemealableBlock,
SimpleWaterloggedBlock {
    private static final BooleanProperty f_152325_ = BlockStateProperties.f_61362_;
    private static final int f_152326_ = 6;
    protected static final VoxelShape f_152321_ = Block.m_49796_(5.0, 0.0, 9.0, 11.0, 16.0, 15.0);
    protected static final VoxelShape f_152322_ = Block.m_49796_(5.0, 0.0, 1.0, 11.0, 16.0, 7.0);
    protected static final VoxelShape f_152323_ = Block.m_49796_(1.0, 0.0, 5.0, 7.0, 16.0, 11.0);
    protected static final VoxelShape f_152324_ = Block.m_49796_(9.0, 0.0, 5.0, 15.0, 16.0, 11.0);

    protected BigDripleafStemBlock(BlockBehaviour.Properties p_152329_) {
        super(p_152329_);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_152325_, false)).m_61124_(f_54117_, Direction.NORTH));
    }

    @Override
    public VoxelShape m_5940_(BlockState p_152360_, BlockGetter p_152361_, BlockPos p_152362_, CollisionContext p_152363_) {
        switch (p_152360_.m_61143_(f_54117_)) {
            case SOUTH: {
                return f_152322_;
            }
            default: {
                return f_152321_;
            }
            case WEST: {
                return f_152324_;
            }
            case EAST: 
        }
        return f_152323_;
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_152376_) {
        p_152376_.m_61104_(f_152325_, f_54117_);
    }

    @Override
    public FluidState m_5888_(BlockState p_152378_) {
        if (p_152378_.m_61143_(f_152325_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_152378_);
    }

    @Override
    public boolean m_7898_(BlockState p_152365_, LevelReader p_152366_, BlockPos p_152367_) {
        BlockPos $$3 = p_152367_.m_7495_();
        BlockState $$4 = p_152366_.m_8055_($$3);
        BlockState $$5 = p_152366_.m_8055_(p_152367_.m_7494_());
        return !(!$$4.m_60713_(this) && !$$4.m_204336_(BlockTags.f_184227_) || !$$5.m_60713_(this) && !$$5.m_60713_(Blocks.f_152545_));
    }

    protected static boolean m_152349_(LevelAccessor p_152350_, BlockPos p_152351_, FluidState p_152352_, Direction p_152353_) {
        BlockState $$4 = (BlockState)((BlockState)Blocks.f_152546_.m_49966_().m_61124_(f_152325_, p_152352_.m_164512_(Fluids.f_76193_))).m_61124_(f_54117_, p_152353_);
        return p_152350_.m_7731_(p_152351_, $$4, 3);
    }

    @Override
    public BlockState m_7417_(BlockState p_152369_, Direction p_152370_, BlockState p_152371_, LevelAccessor p_152372_, BlockPos p_152373_, BlockPos p_152374_) {
        if (!(p_152370_ != Direction.DOWN && p_152370_ != Direction.UP || p_152369_.m_60710_(p_152372_, p_152373_))) {
            p_152372_.m_186460_(p_152373_, this, 1);
        }
        if (p_152369_.m_61143_(f_152325_).booleanValue()) {
            p_152372_.m_186469_(p_152373_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_152372_));
        }
        return super.m_7417_(p_152369_, p_152370_, p_152371_, p_152372_, p_152373_, p_152374_);
    }

    @Override
    public void m_213897_(BlockState p_220813_, ServerLevel p_220814_, BlockPos p_220815_, RandomSource p_220816_) {
        if (!p_220813_.m_60710_(p_220814_, p_220815_)) {
            p_220814_.m_46961_(p_220815_, true);
        }
    }

    @Override
    public boolean m_7370_(BlockGetter p_152340_, BlockPos p_152341_, BlockState p_152342_, boolean p_152343_) {
        Optional<BlockPos> $$4 = BlockUtil.m_177845_(p_152340_, p_152341_, p_152342_.m_60734_(), Direction.UP, Blocks.f_152545_);
        if (!$$4.isPresent()) {
            return false;
        }
        BlockPos $$5 = $$4.get().m_7494_();
        BlockState $$6 = p_152340_.m_8055_($$5);
        return BigDripleafBlock.m_152251_(p_152340_, $$5, $$6);
    }

    @Override
    public boolean m_214167_(Level p_220808_, RandomSource p_220809_, BlockPos p_220810_, BlockState p_220811_) {
        return true;
    }

    @Override
    public void m_214148_(ServerLevel p_220803_, RandomSource p_220804_, BlockPos p_220805_, BlockState p_220806_) {
        Optional<BlockPos> $$4 = BlockUtil.m_177845_(p_220803_, p_220805_, p_220806_.m_60734_(), Direction.UP, Blocks.f_152545_);
        if (!$$4.isPresent()) {
            return;
        }
        BlockPos $$5 = $$4.get();
        BlockPos $$6 = $$5.m_7494_();
        Direction $$7 = p_220806_.m_61143_(f_54117_);
        BigDripleafStemBlock.m_152349_(p_220803_, $$5, p_220803_.m_6425_($$5), $$7);
        BigDripleafBlock.m_152241_(p_220803_, $$6, p_220803_.m_6425_($$6), $$7);
    }

    @Override
    public ItemStack m_7397_(BlockGetter p_152336_, BlockPos p_152337_, BlockState p_152338_) {
        return new ItemStack(Blocks.f_152545_);
    }
}

