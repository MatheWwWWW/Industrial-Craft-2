/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;

public abstract class DiodeBlock
extends HorizontalDirectionalBlock {
    protected static final VoxelShape f_52495_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
    public static final BooleanProperty f_52496_ = BlockStateProperties.f_61448_;

    protected DiodeBlock(BlockBehaviour.Properties p_52499_) {
        super(p_52499_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_52556_, BlockGetter p_52557_, BlockPos p_52558_, CollisionContext p_52559_) {
        return f_52495_;
    }

    @Override
    public boolean m_7898_(BlockState p_52538_, LevelReader p_52539_, BlockPos p_52540_) {
        return DiodeBlock.m_49936_(p_52539_, p_52540_.m_7495_());
    }

    @Override
    public void m_213897_(BlockState p_221065_, ServerLevel p_221066_, BlockPos p_221067_, RandomSource p_221068_) {
        if (this.m_7346_(p_221066_, p_221067_, p_221065_)) {
            return;
        }
        boolean $$4 = p_221065_.m_61143_(f_52496_);
        boolean $$5 = this.m_7320_(p_221066_, p_221067_, p_221065_);
        if ($$4 && !$$5) {
            p_221066_.m_7731_(p_221067_, (BlockState)p_221065_.m_61124_(f_52496_, false), 2);
        } else if (!$$4) {
            p_221066_.m_7731_(p_221067_, (BlockState)p_221065_.m_61124_(f_52496_, true), 2);
            if (!$$5) {
                p_221066_.m_186464_(p_221067_, this, this.m_6112_(p_221065_), TickPriority.VERY_HIGH);
            }
        }
    }

    @Override
    public int m_6376_(BlockState p_52561_, BlockGetter p_52562_, BlockPos p_52563_, Direction p_52564_) {
        return p_52561_.m_60746_(p_52562_, p_52563_, p_52564_);
    }

    @Override
    public int m_6378_(BlockState p_52520_, BlockGetter p_52521_, BlockPos p_52522_, Direction p_52523_) {
        if (!p_52520_.m_61143_(f_52496_).booleanValue()) {
            return 0;
        }
        if (p_52520_.m_61143_(f_54117_) == p_52523_) {
            return this.m_5968_(p_52521_, p_52522_, p_52520_);
        }
        return 0;
    }

    @Override
    public void m_6861_(BlockState p_52525_, Level p_52526_, BlockPos p_52527_, Block p_52528_, BlockPos p_52529_, boolean p_52530_) {
        if (p_52525_.m_60710_(p_52526_, p_52527_)) {
            this.m_7321_(p_52526_, p_52527_, p_52525_);
            return;
        }
        BlockEntity $$6 = p_52525_.m_155947_() ? p_52526_.m_7702_(p_52527_) : null;
        DiodeBlock.m_49892_(p_52525_, p_52526_, p_52527_, $$6);
        p_52526_.m_7471_(p_52527_, false);
        for (Direction $$7 : Direction.values()) {
            p_52526_.m_46672_(p_52527_.m_121945_($$7), this);
        }
    }

    protected void m_7321_(Level p_52577_, BlockPos p_52578_, BlockState p_52579_) {
        boolean $$4;
        if (this.m_7346_(p_52577_, p_52578_, p_52579_)) {
            return;
        }
        boolean $$3 = p_52579_.m_61143_(f_52496_);
        if ($$3 != ($$4 = this.m_7320_(p_52577_, p_52578_, p_52579_)) && !p_52577_.m_183326_().m_183588_(p_52578_, this)) {
            TickPriority $$5 = TickPriority.HIGH;
            if (this.m_52573_(p_52577_, p_52578_, p_52579_)) {
                $$5 = TickPriority.EXTREMELY_HIGH;
            } else if ($$3) {
                $$5 = TickPriority.VERY_HIGH;
            }
            p_52577_.m_186464_(p_52578_, this, this.m_6112_(p_52579_), $$5);
        }
    }

    public boolean m_7346_(LevelReader p_52511_, BlockPos p_52512_, BlockState p_52513_) {
        return false;
    }

    protected boolean m_7320_(Level p_52502_, BlockPos p_52503_, BlockState p_52504_) {
        return this.m_7312_(p_52502_, p_52503_, p_52504_) > 0;
    }

    protected int m_7312_(Level p_52544_, BlockPos p_52545_, BlockState p_52546_) {
        Direction $$3 = p_52546_.m_61143_(f_54117_);
        BlockPos $$4 = p_52545_.m_121945_($$3);
        int $$5 = p_52544_.m_46681_($$4, $$3);
        if ($$5 >= 15) {
            return $$5;
        }
        BlockState $$6 = p_52544_.m_8055_($$4);
        return Math.max($$5, $$6.m_60713_(Blocks.f_50088_) ? $$6.m_61143_(RedStoneWireBlock.f_55500_) : 0);
    }

    protected int m_52547_(LevelReader p_52548_, BlockPos p_52549_, BlockState p_52550_) {
        Direction $$3 = p_52550_.m_61143_(f_54117_);
        Direction $$4 = $$3.m_122427_();
        Direction $$5 = $$3.m_122428_();
        return Math.max(this.m_52551_(p_52548_, p_52549_.m_121945_($$4), $$4), this.m_52551_(p_52548_, p_52549_.m_121945_($$5), $$5));
    }

    protected int m_52551_(LevelReader p_52552_, BlockPos p_52553_, Direction p_52554_) {
        BlockState $$3 = p_52552_.m_8055_(p_52553_);
        if (this.m_6137_($$3)) {
            if ($$3.m_60713_(Blocks.f_50330_)) {
                return 15;
            }
            if ($$3.m_60713_(Blocks.f_50088_)) {
                return $$3.m_61143_(RedStoneWireBlock.f_55500_);
            }
            return p_52552_.m_46852_(p_52553_, p_52554_);
        }
        return 0;
    }

    @Override
    public boolean m_7899_(BlockState p_52572_) {
        return true;
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_52501_) {
        return (BlockState)this.m_49966_().m_61124_(f_54117_, p_52501_.m_8125_().m_122424_());
    }

    @Override
    public void m_6402_(Level p_52506_, BlockPos p_52507_, BlockState p_52508_, LivingEntity p_52509_, ItemStack p_52510_) {
        if (this.m_7320_(p_52506_, p_52507_, p_52508_)) {
            p_52506_.m_186460_(p_52507_, this, 1);
        }
    }

    @Override
    public void m_6807_(BlockState p_52566_, Level p_52567_, BlockPos p_52568_, BlockState p_52569_, boolean p_52570_) {
        this.m_52580_(p_52567_, p_52568_, p_52566_);
    }

    @Override
    public void m_6810_(BlockState p_52532_, Level p_52533_, BlockPos p_52534_, BlockState p_52535_, boolean p_52536_) {
        if (p_52536_ || p_52532_.m_60713_(p_52535_.m_60734_())) {
            return;
        }
        super.m_6810_(p_52532_, p_52533_, p_52534_, p_52535_, p_52536_);
        this.m_52580_(p_52533_, p_52534_, p_52532_);
    }

    protected void m_52580_(Level p_52581_, BlockPos p_52582_, BlockState p_52583_) {
        Direction $$3 = p_52583_.m_61143_(f_54117_);
        BlockPos $$4 = p_52582_.m_121945_($$3.m_122424_());
        p_52581_.m_46586_($$4, this, p_52582_);
        p_52581_.m_46590_($$4, this, $$3);
    }

    protected boolean m_6137_(BlockState p_52585_) {
        return p_52585_.m_60803_();
    }

    protected int m_5968_(BlockGetter p_52541_, BlockPos p_52542_, BlockState p_52543_) {
        return 15;
    }

    public static boolean m_52586_(BlockState p_52587_) {
        return p_52587_.m_60734_() instanceof DiodeBlock;
    }

    public boolean m_52573_(BlockGetter p_52574_, BlockPos p_52575_, BlockState p_52576_) {
        Direction $$3 = p_52576_.m_61143_(f_54117_).m_122424_();
        BlockState $$4 = p_52574_.m_8055_(p_52575_.m_121945_($$3));
        return DiodeBlock.m_52586_($$4) && $$4.m_61143_(f_54117_) != $$3;
    }

    protected abstract int m_6112_(BlockState var1);
}

