/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 */
package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LiquidBlock
extends Block
implements BucketPickup {
    public static final IntegerProperty f_54688_ = BlockStateProperties.f_61422_;
    protected final FlowingFluid f_54689_;
    private final List<FluidState> f_54691_;
    public static final VoxelShape f_54690_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    public static final ImmutableList<Direction> f_181233_ = ImmutableList.of((Object)Direction.DOWN, (Object)Direction.SOUTH, (Object)Direction.NORTH, (Object)Direction.EAST, (Object)Direction.WEST);

    protected LiquidBlock(FlowingFluid p_54694_, BlockBehaviour.Properties p_54695_) {
        super(p_54695_);
        this.f_54689_ = p_54694_;
        this.f_54691_ = Lists.newArrayList();
        this.f_54691_.add(p_54694_.m_76068_(false));
        for (int $$2 = 1; $$2 < 8; ++$$2) {
            this.f_54691_.add(p_54694_.m_75953_(8 - $$2, false));
        }
        this.f_54691_.add(p_54694_.m_75953_(8, true));
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_54688_, 0));
    }

    @Override
    public VoxelShape m_5939_(BlockState p_54760_, BlockGetter p_54761_, BlockPos p_54762_, CollisionContext p_54763_) {
        if (p_54763_.m_6513_(f_54690_, p_54762_, true) && p_54760_.m_61143_(f_54688_) == 0 && p_54763_.m_203682_(p_54761_.m_6425_(p_54762_.m_7494_()), p_54760_.m_60819_())) {
            return f_54690_;
        }
        return Shapes.m_83040_();
    }

    @Override
    public boolean m_6724_(BlockState p_54732_) {
        return p_54732_.m_60819_().m_76187_();
    }

    @Override
    public void m_213898_(BlockState p_221410_, ServerLevel p_221411_, BlockPos p_221412_, RandomSource p_221413_) {
        p_221410_.m_60819_().m_230562_(p_221411_, p_221412_, p_221413_);
    }

    @Override
    public boolean m_7420_(BlockState p_54745_, BlockGetter p_54746_, BlockPos p_54747_) {
        return false;
    }

    @Override
    public boolean m_7357_(BlockState p_54704_, BlockGetter p_54705_, BlockPos p_54706_, PathComputationType p_54707_) {
        return !this.f_54689_.m_205067_(FluidTags.f_13132_);
    }

    @Override
    public FluidState m_5888_(BlockState p_54765_) {
        int $$1 = p_54765_.m_61143_(f_54688_);
        return this.f_54691_.get(Math.min($$1, 8));
    }

    @Override
    public boolean m_6104_(BlockState p_54716_, BlockState p_54717_, Direction p_54718_) {
        return p_54717_.m_60819_().m_76152_().m_6212_(this.f_54689_);
    }

    @Override
    public RenderShape m_7514_(BlockState p_54738_) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public List<ItemStack> m_7381_(BlockState p_54720_, LootContext.Builder p_54721_) {
        return Collections.emptyList();
    }

    @Override
    public VoxelShape m_5940_(BlockState p_54749_, BlockGetter p_54750_, BlockPos p_54751_, CollisionContext p_54752_) {
        return Shapes.m_83040_();
    }

    @Override
    public void m_6807_(BlockState p_54754_, Level p_54755_, BlockPos p_54756_, BlockState p_54757_, boolean p_54758_) {
        if (this.m_54696_(p_54755_, p_54756_, p_54754_)) {
            p_54755_.m_186469_(p_54756_, p_54754_.m_60819_().m_76152_(), this.f_54689_.m_6718_(p_54755_));
        }
    }

    @Override
    public BlockState m_7417_(BlockState p_54723_, Direction p_54724_, BlockState p_54725_, LevelAccessor p_54726_, BlockPos p_54727_, BlockPos p_54728_) {
        if (p_54723_.m_60819_().m_76170_() || p_54725_.m_60819_().m_76170_()) {
            p_54726_.m_186469_(p_54727_, p_54723_.m_60819_().m_76152_(), this.f_54689_.m_6718_(p_54726_));
        }
        return super.m_7417_(p_54723_, p_54724_, p_54725_, p_54726_, p_54727_, p_54728_);
    }

    @Override
    public void m_6861_(BlockState p_54709_, Level p_54710_, BlockPos p_54711_, Block p_54712_, BlockPos p_54713_, boolean p_54714_) {
        if (this.m_54696_(p_54710_, p_54711_, p_54709_)) {
            p_54710_.m_186469_(p_54711_, p_54709_.m_60819_().m_76152_(), this.f_54689_.m_6718_(p_54710_));
        }
    }

    private boolean m_54696_(Level p_54697_, BlockPos p_54698_, BlockState p_54699_) {
        if (this.f_54689_.m_205067_(FluidTags.f_13132_)) {
            boolean $$3 = p_54697_.m_8055_(p_54698_.m_7495_()).m_60713_(Blocks.f_50136_);
            for (Direction $$4 : f_181233_) {
                BlockPos $$5 = p_54698_.m_121945_($$4.m_122424_());
                if (p_54697_.m_6425_($$5).m_205070_(FluidTags.f_13131_)) {
                    Block $$6 = p_54697_.m_6425_(p_54698_).m_76170_() ? Blocks.f_50080_ : Blocks.f_50652_;
                    p_54697_.m_46597_(p_54698_, $$6.m_49966_());
                    this.m_54700_(p_54697_, p_54698_);
                    return false;
                }
                if (!$$3 || !p_54697_.m_8055_($$5).m_60713_(Blocks.f_50568_)) continue;
                p_54697_.m_46597_(p_54698_, Blocks.f_50137_.m_49966_());
                this.m_54700_(p_54697_, p_54698_);
                return false;
            }
        }
        return true;
    }

    private void m_54700_(LevelAccessor p_54701_, BlockPos p_54702_) {
        p_54701_.m_46796_(1501, p_54702_, 0);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_54730_) {
        p_54730_.m_61104_(f_54688_);
    }

    @Override
    public ItemStack m_142598_(LevelAccessor p_153772_, BlockPos p_153773_, BlockState p_153774_) {
        if (p_153774_.m_61143_(f_54688_) == 0) {
            p_153772_.m_7731_(p_153773_, Blocks.f_50016_.m_49966_(), 11);
            return new ItemStack(this.f_54689_.m_6859_());
        }
        return ItemStack.f_41583_;
    }

    @Override
    public Optional<SoundEvent> m_142298_() {
        return this.f_54689_.m_142520_();
    }
}

