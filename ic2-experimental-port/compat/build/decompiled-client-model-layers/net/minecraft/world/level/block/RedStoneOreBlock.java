/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class RedStoneOreBlock
extends Block {
    public static final BooleanProperty f_55450_ = RedstoneTorchBlock.f_55674_;

    public RedStoneOreBlock(BlockBehaviour.Properties p_55453_) {
        super(p_55453_);
        this.m_49959_((BlockState)this.m_49966_().m_61124_(f_55450_, false));
    }

    @Override
    public void m_6256_(BlockState p_55467_, Level p_55468_, BlockPos p_55469_, Player p_55470_) {
        RedStoneOreBlock.m_55492_(p_55467_, p_55468_, p_55469_);
        super.m_6256_(p_55467_, p_55468_, p_55469_, p_55470_);
    }

    @Override
    public void m_141947_(Level p_154299_, BlockPos p_154300_, BlockState p_154301_, Entity p_154302_) {
        if (!p_154302_.m_20161_()) {
            RedStoneOreBlock.m_55492_(p_154301_, p_154299_, p_154300_);
        }
        super.m_141947_(p_154299_, p_154300_, p_154301_, p_154302_);
    }

    @Override
    public InteractionResult m_6227_(BlockState p_55472_, Level p_55473_, BlockPos p_55474_, Player p_55475_, InteractionHand p_55476_, BlockHitResult p_55477_) {
        if (p_55473_.f_46443_) {
            RedStoneOreBlock.m_55454_(p_55473_, p_55474_);
        } else {
            RedStoneOreBlock.m_55492_(p_55472_, p_55473_, p_55474_);
        }
        ItemStack $$6 = p_55475_.m_21120_(p_55476_);
        if ($$6.m_41720_() instanceof BlockItem && new BlockPlaceContext(p_55475_, p_55476_, $$6, p_55477_).m_7059_()) {
            return InteractionResult.PASS;
        }
        return InteractionResult.SUCCESS;
    }

    private static void m_55492_(BlockState p_55493_, Level p_55494_, BlockPos p_55495_) {
        RedStoneOreBlock.m_55454_(p_55494_, p_55495_);
        if (!p_55493_.m_61143_(f_55450_).booleanValue()) {
            p_55494_.m_7731_(p_55495_, (BlockState)p_55493_.m_61124_(f_55450_, true), 3);
        }
    }

    @Override
    public boolean m_6724_(BlockState p_55486_) {
        return p_55486_.m_61143_(f_55450_);
    }

    @Override
    public void m_213898_(BlockState p_221918_, ServerLevel p_221919_, BlockPos p_221920_, RandomSource p_221921_) {
        if (p_221918_.m_61143_(f_55450_).booleanValue()) {
            p_221919_.m_7731_(p_221920_, (BlockState)p_221918_.m_61124_(f_55450_, false), 3);
        }
    }

    @Override
    public void m_213646_(BlockState p_221907_, ServerLevel p_221908_, BlockPos p_221909_, ItemStack p_221910_, boolean p_221911_) {
        super.m_213646_(p_221907_, p_221908_, p_221909_, p_221910_, p_221911_);
        if (p_221911_ && EnchantmentHelper.m_44843_(Enchantments.f_44985_, p_221910_) == 0) {
            int $$5 = 1 + p_221908_.f_46441_.m_188503_(5);
            this.m_49805_(p_221908_, p_221909_, $$5);
        }
    }

    @Override
    public void m_214162_(BlockState p_221913_, Level p_221914_, BlockPos p_221915_, RandomSource p_221916_) {
        if (p_221913_.m_61143_(f_55450_).booleanValue()) {
            RedStoneOreBlock.m_55454_(p_221914_, p_221915_);
        }
    }

    private static void m_55454_(Level p_55455_, BlockPos p_55456_) {
        double $$2 = 0.5625;
        RandomSource $$3 = p_55455_.f_46441_;
        for (Direction $$4 : Direction.values()) {
            BlockPos $$5 = p_55456_.m_121945_($$4);
            if (p_55455_.m_8055_($$5).m_60804_(p_55455_, $$5)) continue;
            Direction.Axis $$6 = $$4.m_122434_();
            double $$7 = $$6 == Direction.Axis.X ? 0.5 + 0.5625 * (double)$$4.m_122429_() : (double)$$3.m_188501_();
            double $$8 = $$6 == Direction.Axis.Y ? 0.5 + 0.5625 * (double)$$4.m_122430_() : (double)$$3.m_188501_();
            double $$9 = $$6 == Direction.Axis.Z ? 0.5 + 0.5625 * (double)$$4.m_122431_() : (double)$$3.m_188501_();
            p_55455_.m_7106_(DustParticleOptions.f_123656_, (double)p_55456_.m_123341_() + $$7, (double)p_55456_.m_123342_() + $$8, (double)p_55456_.m_123343_() + $$9, 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_55484_) {
        p_55484_.m_61104_(f_55450_);
    }
}

