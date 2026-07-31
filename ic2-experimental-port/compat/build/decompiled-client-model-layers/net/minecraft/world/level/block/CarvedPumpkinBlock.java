/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockMaterialPredicate;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;

public class CarvedPumpkinBlock
extends HorizontalDirectionalBlock
implements Wearable {
    public static final DirectionProperty f_51367_ = HorizontalDirectionalBlock.f_54117_;
    @Nullable
    private BlockPattern f_51368_;
    @Nullable
    private BlockPattern f_51369_;
    @Nullable
    private BlockPattern f_51370_;
    @Nullable
    private BlockPattern f_51371_;
    private static final Predicate<BlockState> f_51372_ = p_51396_ -> p_51396_ != null && (p_51396_.m_60713_(Blocks.f_50143_) || p_51396_.m_60713_(Blocks.f_50144_));

    protected CarvedPumpkinBlock(BlockBehaviour.Properties p_51375_) {
        super(p_51375_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_51367_, Direction.NORTH));
    }

    @Override
    public void m_6807_(BlockState p_51387_, Level p_51388_, BlockPos p_51389_, BlockState p_51390_, boolean p_51391_) {
        if (p_51390_.m_60713_(p_51387_.m_60734_())) {
            return;
        }
        this.m_51378_(p_51388_, p_51389_);
    }

    public boolean m_51381_(LevelReader p_51382_, BlockPos p_51383_) {
        return this.m_51392_().m_61184_(p_51382_, p_51383_) != null || this.m_51394_().m_61184_(p_51382_, p_51383_) != null;
    }

    private void m_51378_(Level p_51379_, BlockPos p_51380_) {
        block9: {
            BlockPattern.BlockPatternMatch $$2;
            block8: {
                $$2 = this.m_51393_().m_61184_(p_51379_, p_51380_);
                if ($$2 == null) break block8;
                for (int $$3 = 0; $$3 < this.m_51393_().m_61202_(); ++$$3) {
                    BlockInWorld $$4 = $$2.m_61229_(0, $$3, 0);
                    p_51379_.m_7731_($$4.m_61176_(), Blocks.f_50016_.m_49966_(), 2);
                    p_51379_.m_46796_(2001, $$4.m_61176_(), Block.m_49956_($$4.m_61168_()));
                }
                SnowGolem $$5 = EntityType.f_20528_.m_20615_(p_51379_);
                BlockPos $$6 = $$2.m_61229_(0, 2, 0).m_61176_();
                $$5.m_7678_((double)$$6.m_123341_() + 0.5, (double)$$6.m_123342_() + 0.05, (double)$$6.m_123343_() + 0.5, 0.0f, 0.0f);
                p_51379_.m_7967_($$5);
                for (ServerPlayer $$7 : p_51379_.m_45976_(ServerPlayer.class, $$5.m_20191_().m_82400_(5.0))) {
                    CriteriaTriggers.f_10580_.m_68256_($$7, $$5);
                }
                for (int $$8 = 0; $$8 < this.m_51393_().m_61202_(); ++$$8) {
                    BlockInWorld $$9 = $$2.m_61229_(0, $$8, 0);
                    p_51379_.m_6289_($$9.m_61176_(), Blocks.f_50016_);
                }
                break block9;
            }
            $$2 = this.m_51397_().m_61184_(p_51379_, p_51380_);
            if ($$2 == null) break block9;
            for (int $$10 = 0; $$10 < this.m_51397_().m_61203_(); ++$$10) {
                for (int $$11 = 0; $$11 < this.m_51397_().m_61202_(); ++$$11) {
                    BlockInWorld $$12 = $$2.m_61229_($$10, $$11, 0);
                    p_51379_.m_7731_($$12.m_61176_(), Blocks.f_50016_.m_49966_(), 2);
                    p_51379_.m_46796_(2001, $$12.m_61176_(), Block.m_49956_($$12.m_61168_()));
                }
            }
            BlockPos $$13 = $$2.m_61229_(1, 2, 0).m_61176_();
            IronGolem $$14 = EntityType.f_20460_.m_20615_(p_51379_);
            $$14.m_28887_(true);
            $$14.m_7678_((double)$$13.m_123341_() + 0.5, (double)$$13.m_123342_() + 0.05, (double)$$13.m_123343_() + 0.5, 0.0f, 0.0f);
            p_51379_.m_7967_($$14);
            for (ServerPlayer $$15 : p_51379_.m_45976_(ServerPlayer.class, $$14.m_20191_().m_82400_(5.0))) {
                CriteriaTriggers.f_10580_.m_68256_($$15, $$14);
            }
            for (int $$16 = 0; $$16 < this.m_51397_().m_61203_(); ++$$16) {
                for (int $$17 = 0; $$17 < this.m_51397_().m_61202_(); ++$$17) {
                    BlockInWorld $$18 = $$2.m_61229_($$16, $$17, 0);
                    p_51379_.m_6289_($$18.m_61176_(), Blocks.f_50016_);
                }
            }
        }
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_51377_) {
        return (BlockState)this.m_49966_().m_61124_(f_51367_, p_51377_.m_8125_().m_122424_());
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51385_) {
        p_51385_.m_61104_(f_51367_);
    }

    private BlockPattern m_51392_() {
        if (this.f_51368_ == null) {
            this.f_51368_ = BlockPatternBuilder.m_61243_().m_61247_(" ", "#", "#").m_61244_('#', BlockInWorld.m_61169_(BlockStatePredicate.m_61287_(Blocks.f_50127_))).m_61249_();
        }
        return this.f_51368_;
    }

    private BlockPattern m_51393_() {
        if (this.f_51369_ == null) {
            this.f_51369_ = BlockPatternBuilder.m_61243_().m_61247_("^", "#", "#").m_61244_('^', BlockInWorld.m_61169_(f_51372_)).m_61244_('#', BlockInWorld.m_61169_(BlockStatePredicate.m_61287_(Blocks.f_50127_))).m_61249_();
        }
        return this.f_51369_;
    }

    private BlockPattern m_51394_() {
        if (this.f_51370_ == null) {
            this.f_51370_ = BlockPatternBuilder.m_61243_().m_61247_("~ ~", "###", "~#~").m_61244_('#', BlockInWorld.m_61169_(BlockStatePredicate.m_61287_(Blocks.f_50075_))).m_61244_('~', BlockInWorld.m_61169_(BlockMaterialPredicate.m_61262_(Material.f_76296_))).m_61249_();
        }
        return this.f_51370_;
    }

    private BlockPattern m_51397_() {
        if (this.f_51371_ == null) {
            this.f_51371_ = BlockPatternBuilder.m_61243_().m_61247_("~^~", "###", "~#~").m_61244_('^', BlockInWorld.m_61169_(f_51372_)).m_61244_('#', BlockInWorld.m_61169_(BlockStatePredicate.m_61287_(Blocks.f_50075_))).m_61244_('~', BlockInWorld.m_61169_(BlockMaterialPredicate.m_61262_(Material.f_76296_))).m_61249_();
        }
        return this.f_51371_;
    }
}

