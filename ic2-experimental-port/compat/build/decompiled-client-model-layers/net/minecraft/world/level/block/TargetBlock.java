/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class TargetBlock
extends Block {
    private static final IntegerProperty f_57376_ = BlockStateProperties.f_61426_;
    private static final int f_154777_ = 20;
    private static final int f_154778_ = 8;

    public TargetBlock(BlockBehaviour.Properties p_57379_) {
        super(p_57379_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_57376_, 0));
    }

    @Override
    public void m_5581_(Level p_57381_, BlockState p_57382_, BlockHitResult p_57383_, Projectile p_57384_) {
        int $$4 = TargetBlock.m_57391_(p_57381_, p_57382_, p_57383_, p_57384_);
        Entity $$5 = p_57384_.m_37282_();
        if ($$5 instanceof ServerPlayer) {
            ServerPlayer $$6 = (ServerPlayer)$$5;
            $$6.m_36220_(Stats.f_12953_);
            CriteriaTriggers.f_10561_.m_70211_($$6, p_57384_, p_57383_.m_82450_(), $$4);
        }
    }

    private static int m_57391_(LevelAccessor p_57392_, BlockState p_57393_, BlockHitResult p_57394_, Entity p_57395_) {
        int $$5;
        int $$4 = TargetBlock.m_57408_(p_57394_, p_57394_.m_82450_());
        int n = $$5 = p_57395_ instanceof AbstractArrow ? 20 : 8;
        if (!p_57392_.m_183326_().m_183582_(p_57394_.m_82425_(), p_57393_.m_60734_())) {
            TargetBlock.m_57385_(p_57392_, p_57393_, $$4, p_57394_.m_82425_(), $$5);
        }
        return $$4;
    }

    private static int m_57408_(BlockHitResult p_57409_, Vec3 p_57410_) {
        double $$9;
        Direction $$2 = p_57409_.m_82434_();
        double $$3 = Math.abs(Mth.m_14185_(p_57410_.f_82479_) - 0.5);
        double $$4 = Math.abs(Mth.m_14185_(p_57410_.f_82480_) - 0.5);
        double $$5 = Math.abs(Mth.m_14185_(p_57410_.f_82481_) - 0.5);
        Direction.Axis $$6 = $$2.m_122434_();
        if ($$6 == Direction.Axis.Y) {
            double $$7 = Math.max($$3, $$5);
        } else if ($$6 == Direction.Axis.Z) {
            double $$8 = Math.max($$3, $$4);
        } else {
            $$9 = Math.max($$4, $$5);
        }
        return Math.max(1, Mth.m_14165_(15.0 * Mth.m_14008_((0.5 - $$9) / 0.5, 0.0, 1.0)));
    }

    private static void m_57385_(LevelAccessor p_57386_, BlockState p_57387_, int p_57388_, BlockPos p_57389_, int p_57390_) {
        p_57386_.m_7731_(p_57389_, (BlockState)p_57387_.m_61124_(f_57376_, p_57388_), 3);
        p_57386_.m_186460_(p_57389_, p_57387_.m_60734_(), p_57390_);
    }

    @Override
    public void m_213897_(BlockState p_222588_, ServerLevel p_222589_, BlockPos p_222590_, RandomSource p_222591_) {
        if (p_222588_.m_61143_(f_57376_) != 0) {
            p_222589_.m_7731_(p_222590_, (BlockState)p_222588_.m_61124_(f_57376_, 0), 3);
        }
    }

    @Override
    public int m_6378_(BlockState p_57402_, BlockGetter p_57403_, BlockPos p_57404_, Direction p_57405_) {
        return p_57402_.m_61143_(f_57376_);
    }

    @Override
    public boolean m_7899_(BlockState p_57418_) {
        return true;
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_57407_) {
        p_57407_.m_61104_(f_57376_);
    }

    @Override
    public void m_6807_(BlockState p_57412_, Level p_57413_, BlockPos p_57414_, BlockState p_57415_, boolean p_57416_) {
        if (p_57413_.m_5776_() || p_57412_.m_60713_(p_57415_.m_60734_())) {
            return;
        }
        if (p_57412_.m_61143_(f_57376_) > 0 && !p_57413_.m_183326_().m_183582_(p_57414_, this)) {
            p_57413_.m_7731_(p_57414_, (BlockState)p_57412_.m_61124_(f_57376_, 0), 18);
        }
    }
}

