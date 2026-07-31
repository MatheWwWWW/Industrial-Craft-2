/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class AbstractCauldronBlock
extends Block {
    private static final int f_151938_ = 2;
    private static final int f_151939_ = 4;
    private static final int f_151940_ = 3;
    private static final int f_151941_ = 2;
    protected static final int f_151936_ = 4;
    private static final VoxelShape f_151942_ = AbstractCauldronBlock.m_49796_(2.0, 4.0, 2.0, 14.0, 16.0, 14.0);
    protected static final VoxelShape f_151937_ = Shapes.m_83113_(Shapes.m_83144_(), Shapes.m_83124_(AbstractCauldronBlock.m_49796_(0.0, 0.0, 4.0, 16.0, 3.0, 12.0), AbstractCauldronBlock.m_49796_(4.0, 0.0, 0.0, 12.0, 3.0, 16.0), AbstractCauldronBlock.m_49796_(2.0, 0.0, 2.0, 14.0, 3.0, 14.0), f_151942_), BooleanOp.f_82685_);
    private final Map<Item, CauldronInteraction> f_151943_;

    public AbstractCauldronBlock(BlockBehaviour.Properties p_151946_, Map<Item, CauldronInteraction> p_151947_) {
        super(p_151946_);
        this.f_151943_ = p_151947_;
    }

    protected double m_142446_(BlockState p_151948_) {
        return 0.0;
    }

    protected boolean m_151979_(BlockState p_151980_, BlockPos p_151981_, Entity p_151982_) {
        return p_151982_.m_20186_() < (double)p_151981_.m_123342_() + this.m_142446_(p_151980_) && p_151982_.m_20191_().f_82292_ > (double)p_151981_.m_123342_() + 0.25;
    }

    @Override
    public InteractionResult m_6227_(BlockState p_151969_, Level p_151970_, BlockPos p_151971_, Player p_151972_, InteractionHand p_151973_, BlockHitResult p_151974_) {
        ItemStack $$6 = p_151972_.m_21120_(p_151973_);
        CauldronInteraction $$7 = this.f_151943_.get($$6.m_41720_());
        return $$7.m_175710_(p_151969_, p_151970_, p_151971_, p_151972_, p_151973_, $$6);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_151964_, BlockGetter p_151965_, BlockPos p_151966_, CollisionContext p_151967_) {
        return f_151937_;
    }

    @Override
    public VoxelShape m_6079_(BlockState p_151955_, BlockGetter p_151956_, BlockPos p_151957_) {
        return f_151942_;
    }

    @Override
    public boolean m_7278_(BlockState p_151986_) {
        return true;
    }

    @Override
    public boolean m_7357_(BlockState p_151959_, BlockGetter p_151960_, BlockPos p_151961_, PathComputationType p_151962_) {
        return false;
    }

    public abstract boolean m_142596_(BlockState var1);

    @Override
    public void m_213897_(BlockState p_220702_, ServerLevel p_220703_, BlockPos p_220704_, RandomSource p_220705_) {
        BlockPos $$4 = PointedDripstoneBlock.m_154055_(p_220703_, p_220704_);
        if ($$4 == null) {
            return;
        }
        Fluid $$5 = PointedDripstoneBlock.m_221849_(p_220703_, $$4);
        if ($$5 != Fluids.f_76191_ && this.m_142087_($$5)) {
            this.m_142310_(p_220702_, p_220703_, p_220704_, $$5);
        }
    }

    protected boolean m_142087_(Fluid p_151983_) {
        return false;
    }

    protected void m_142310_(BlockState p_151975_, Level p_151976_, BlockPos p_151977_, Fluid p_151978_) {
    }
}

