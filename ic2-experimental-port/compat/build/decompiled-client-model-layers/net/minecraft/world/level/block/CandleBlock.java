/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMaps
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 */
package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.List;
import java.util.function.ToIntFunction;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CandleBlock
extends AbstractCandleBlock
implements SimpleWaterloggedBlock {
    public static final int f_152788_ = 1;
    public static final int f_152789_ = 4;
    public static final IntegerProperty f_152790_ = BlockStateProperties.f_155994_;
    public static final BooleanProperty f_152791_ = AbstractCandleBlock.f_151895_;
    public static final BooleanProperty f_152792_ = BlockStateProperties.f_61362_;
    public static final ToIntFunction<BlockState> f_152793_ = p_152848_ -> p_152848_.m_61143_(f_152791_) != false ? 3 * p_152848_.m_61143_(f_152790_) : 0;
    private static final Int2ObjectMap<List<Vec3>> f_152794_ = Util.m_137537_(() -> {
        Int2ObjectOpenHashMap $$0 = new Int2ObjectOpenHashMap();
        $$0.defaultReturnValue((Object)ImmutableList.of());
        $$0.put(1, (Object)ImmutableList.of((Object)new Vec3(0.5, 0.5, 0.5)));
        $$0.put(2, (Object)ImmutableList.of((Object)new Vec3(0.375, 0.44, 0.5), (Object)new Vec3(0.625, 0.5, 0.44)));
        $$0.put(3, (Object)ImmutableList.of((Object)new Vec3(0.5, 0.313, 0.625), (Object)new Vec3(0.375, 0.44, 0.5), (Object)new Vec3(0.56, 0.5, 0.44)));
        $$0.put(4, (Object)ImmutableList.of((Object)new Vec3(0.44, 0.313, 0.56), (Object)new Vec3(0.625, 0.44, 0.56), (Object)new Vec3(0.375, 0.44, 0.375), (Object)new Vec3(0.56, 0.5, 0.375)));
        return Int2ObjectMaps.unmodifiable((Int2ObjectMap)$$0);
    });
    private static final VoxelShape f_152795_ = Block.m_49796_(7.0, 0.0, 7.0, 9.0, 6.0, 9.0);
    private static final VoxelShape f_152796_ = Block.m_49796_(5.0, 0.0, 6.0, 11.0, 6.0, 9.0);
    private static final VoxelShape f_152797_ = Block.m_49796_(5.0, 0.0, 6.0, 10.0, 6.0, 11.0);
    private static final VoxelShape f_152798_ = Block.m_49796_(5.0, 0.0, 5.0, 11.0, 6.0, 10.0);

    public CandleBlock(BlockBehaviour.Properties p_152801_) {
        super(p_152801_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_152790_, 1)).m_61124_(f_152791_, false)).m_61124_(f_152792_, false));
    }

    @Override
    public InteractionResult m_6227_(BlockState p_152822_, Level p_152823_, BlockPos p_152824_, Player p_152825_, InteractionHand p_152826_, BlockHitResult p_152827_) {
        if (p_152825_.m_150110_().f_35938_ && p_152825_.m_21120_(p_152826_).m_41619_() && p_152822_.m_61143_(f_152791_).booleanValue()) {
            CandleBlock.m_151899_(p_152825_, p_152822_, p_152823_, p_152824_);
            return InteractionResult.m_19078_(p_152823_.f_46443_);
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean m_6864_(BlockState p_152814_, BlockPlaceContext p_152815_) {
        if (!p_152815_.m_7078_() && p_152815_.m_43722_().m_41720_() == this.m_5456_() && p_152814_.m_61143_(f_152790_) < 4) {
            return true;
        }
        return super.m_6864_(p_152814_, p_152815_);
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_152803_) {
        BlockState $$1 = p_152803_.m_43725_().m_8055_(p_152803_.m_8083_());
        if ($$1.m_60713_(this)) {
            return (BlockState)$$1.m_61122_(f_152790_);
        }
        FluidState $$2 = p_152803_.m_43725_().m_6425_(p_152803_.m_8083_());
        boolean $$3 = $$2.m_76152_() == Fluids.f_76193_;
        return (BlockState)super.m_5573_(p_152803_).m_61124_(f_152792_, $$3);
    }

    @Override
    public BlockState m_7417_(BlockState p_152833_, Direction p_152834_, BlockState p_152835_, LevelAccessor p_152836_, BlockPos p_152837_, BlockPos p_152838_) {
        if (p_152833_.m_61143_(f_152792_).booleanValue()) {
            p_152836_.m_186469_(p_152837_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_152836_));
        }
        return super.m_7417_(p_152833_, p_152834_, p_152835_, p_152836_, p_152837_, p_152838_);
    }

    @Override
    public FluidState m_5888_(BlockState p_152844_) {
        if (p_152844_.m_61143_(f_152792_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_152844_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_152817_, BlockGetter p_152818_, BlockPos p_152819_, CollisionContext p_152820_) {
        switch (p_152817_.m_61143_(f_152790_)) {
            default: {
                return f_152795_;
            }
            case 2: {
                return f_152796_;
            }
            case 3: {
                return f_152797_;
            }
            case 4: 
        }
        return f_152798_;
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_152840_) {
        p_152840_.m_61104_(f_152790_, f_152791_, f_152792_);
    }

    @Override
    public boolean m_7361_(LevelAccessor p_152805_, BlockPos p_152806_, BlockState p_152807_, FluidState p_152808_) {
        if (p_152807_.m_61143_(f_152792_).booleanValue() || p_152808_.m_76152_() != Fluids.f_76193_) {
            return false;
        }
        BlockState $$4 = (BlockState)p_152807_.m_61124_(f_152792_, true);
        if (p_152807_.m_61143_(f_152791_).booleanValue()) {
            CandleBlock.m_151899_(null, $$4, p_152805_, p_152806_);
        } else {
            p_152805_.m_7731_(p_152806_, $$4, 3);
        }
        p_152805_.m_186469_(p_152806_, p_152808_.m_76152_(), p_152808_.m_76152_().m_6718_(p_152805_));
        return true;
    }

    public static boolean m_152845_(BlockState p_152846_) {
        return p_152846_.m_204338_(BlockTags.f_144265_, p_152810_ -> p_152810_.m_61138_(f_152791_) && p_152810_.m_61138_(f_152792_)) && p_152846_.m_61143_(f_152791_) == false && p_152846_.m_61143_(f_152792_) == false;
    }

    @Override
    protected Iterable<Vec3> m_142199_(BlockState p_152812_) {
        return (Iterable)f_152794_.get(p_152812_.m_61143_(f_152790_).intValue());
    }

    @Override
    protected boolean m_142595_(BlockState p_152842_) {
        return p_152842_.m_61143_(f_152792_) == false && super.m_142595_(p_152842_);
    }

    @Override
    public boolean m_7898_(BlockState p_152829_, LevelReader p_152830_, BlockPos p_152831_) {
        return Block.m_49863_(p_152830_, p_152831_.m_7495_(), Direction.UP);
    }
}

