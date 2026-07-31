/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Maps
 */
package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CandleCakeBlock
extends AbstractCandleBlock {
    public static final BooleanProperty f_152850_ = AbstractCandleBlock.f_151895_;
    protected static final float f_152851_ = 1.0f;
    protected static final VoxelShape f_152852_ = Block.m_49796_(1.0, 0.0, 1.0, 15.0, 8.0, 15.0);
    protected static final VoxelShape f_152853_ = Block.m_49796_(7.0, 8.0, 7.0, 9.0, 14.0, 9.0);
    protected static final VoxelShape f_152854_ = Shapes.m_83110_(f_152852_, f_152853_);
    private static final Map<Block, CandleCakeBlock> f_152855_ = Maps.newHashMap();
    private static final Iterable<Vec3> f_152856_ = ImmutableList.of((Object)new Vec3(0.5, 1.0, 0.5));

    protected CandleCakeBlock(Block p_152859_, BlockBehaviour.Properties p_152860_) {
        super(p_152860_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_152850_, false));
        f_152855_.put(p_152859_, this);
    }

    @Override
    protected Iterable<Vec3> m_142199_(BlockState p_152868_) {
        return f_152856_;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_152875_, BlockGetter p_152876_, BlockPos p_152877_, CollisionContext p_152878_) {
        return f_152854_;
    }

    @Override
    public InteractionResult m_6227_(BlockState p_152884_, Level p_152885_, BlockPos p_152886_, Player p_152887_, InteractionHand p_152888_, BlockHitResult p_152889_) {
        ItemStack $$6 = p_152887_.m_21120_(p_152888_);
        if ($$6.m_150930_(Items.f_42409_) || $$6.m_150930_(Items.f_42613_)) {
            return InteractionResult.PASS;
        }
        if (!(CandleCakeBlock.m_152906_(p_152889_) && p_152887_.m_21120_(p_152888_).m_41619_() && p_152884_.m_61143_(f_152850_).booleanValue())) {
            InteractionResult $$7 = CakeBlock.m_51185_(p_152885_, p_152886_, Blocks.f_50145_.m_49966_(), p_152887_);
            if ($$7.m_19077_()) {
                CandleCakeBlock.m_49950_(p_152884_, p_152885_, p_152886_);
            }
            return $$7;
        }
        CandleCakeBlock.m_151899_(p_152887_, p_152884_, p_152885_, p_152886_);
        return InteractionResult.m_19078_(p_152885_.f_46443_);
    }

    private static boolean m_152906_(BlockHitResult p_152907_) {
        return p_152907_.m_82450_().f_82480_ - (double)p_152907_.m_82425_().m_123342_() > 0.5;
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_152905_) {
        p_152905_.m_61104_(f_152850_);
    }

    @Override
    public ItemStack m_7397_(BlockGetter p_152862_, BlockPos p_152863_, BlockState p_152864_) {
        return new ItemStack(Blocks.f_50145_);
    }

    @Override
    public BlockState m_7417_(BlockState p_152898_, Direction p_152899_, BlockState p_152900_, LevelAccessor p_152901_, BlockPos p_152902_, BlockPos p_152903_) {
        if (p_152899_ == Direction.DOWN && !p_152898_.m_60710_(p_152901_, p_152902_)) {
            return Blocks.f_50016_.m_49966_();
        }
        return super.m_7417_(p_152898_, p_152899_, p_152900_, p_152901_, p_152902_, p_152903_);
    }

    @Override
    public boolean m_7898_(BlockState p_152891_, LevelReader p_152892_, BlockPos p_152893_) {
        return p_152892_.m_8055_(p_152893_.m_7495_()).m_60767_().m_76333_();
    }

    @Override
    public int m_6782_(BlockState p_152880_, Level p_152881_, BlockPos p_152882_) {
        return CakeBlock.f_152743_;
    }

    @Override
    public boolean m_7278_(BlockState p_152909_) {
        return true;
    }

    @Override
    public boolean m_7357_(BlockState p_152870_, BlockGetter p_152871_, BlockPos p_152872_, PathComputationType p_152873_) {
        return false;
    }

    public static BlockState m_152865_(Block p_152866_) {
        return f_152855_.get(p_152866_).m_49966_();
    }

    public static boolean m_152910_(BlockState p_152911_) {
        return p_152911_.m_204338_(BlockTags.f_144268_, p_152896_ -> p_152896_.m_61138_(f_152850_) && p_152911_.m_61143_(f_152850_) == false);
    }
}

