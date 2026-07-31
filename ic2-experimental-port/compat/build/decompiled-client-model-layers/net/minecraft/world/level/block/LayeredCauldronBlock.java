/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class LayeredCauldronBlock
extends AbstractCauldronBlock {
    public static final int f_153512_ = 1;
    public static final int f_153513_ = 3;
    public static final IntegerProperty f_153514_ = BlockStateProperties.f_61418_;
    private static final int f_153517_ = 6;
    private static final double f_153518_ = 3.0;
    public static final Predicate<Biome.Precipitation> f_153515_ = p_153553_ -> p_153553_ == Biome.Precipitation.RAIN;
    public static final Predicate<Biome.Precipitation> f_153516_ = p_153526_ -> p_153526_ == Biome.Precipitation.SNOW;
    private final Predicate<Biome.Precipitation> f_153519_;

    public LayeredCauldronBlock(BlockBehaviour.Properties p_153522_, Predicate<Biome.Precipitation> p_153523_, Map<Item, CauldronInteraction> p_153524_) {
        super(p_153522_, p_153524_);
        this.f_153519_ = p_153523_;
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_153514_, 1));
    }

    @Override
    public boolean m_142596_(BlockState p_153555_) {
        return p_153555_.m_61143_(f_153514_) == 3;
    }

    @Override
    protected boolean m_142087_(Fluid p_153551_) {
        return p_153551_ == Fluids.f_76193_ && this.f_153519_ == f_153515_;
    }

    @Override
    protected double m_142446_(BlockState p_153528_) {
        return (6.0 + (double)p_153528_.m_61143_(f_153514_).intValue() * 3.0) / 16.0;
    }

    @Override
    public void m_7892_(BlockState p_153534_, Level p_153535_, BlockPos p_153536_, Entity p_153537_) {
        if (!p_153535_.f_46443_ && p_153537_.m_6060_() && this.m_151979_(p_153534_, p_153536_, p_153537_)) {
            p_153537_.m_20095_();
            if (p_153537_.m_142265_(p_153535_, p_153536_)) {
                this.m_142266_(p_153534_, p_153535_, p_153536_);
            }
        }
    }

    protected void m_142266_(BlockState p_153556_, Level p_153557_, BlockPos p_153558_) {
        LayeredCauldronBlock.m_153559_(p_153556_, p_153557_, p_153558_);
    }

    public static void m_153559_(BlockState p_153560_, Level p_153561_, BlockPos p_153562_) {
        int $$3 = p_153560_.m_61143_(f_153514_) - 1;
        BlockState $$4 = $$3 == 0 ? Blocks.f_50256_.m_49966_() : (BlockState)p_153560_.m_61124_(f_153514_, $$3);
        p_153561_.m_46597_(p_153562_, $$4);
        p_153561_.m_220407_(GameEvent.f_157792_, p_153562_, GameEvent.Context.m_223722_($$4));
    }

    @Override
    public void m_141997_(BlockState p_153539_, Level p_153540_, BlockPos p_153541_, Biome.Precipitation p_153542_) {
        if (!CauldronBlock.m_182450_(p_153540_, p_153542_) || p_153539_.m_61143_(f_153514_) == 3 || !this.f_153519_.test(p_153542_)) {
            return;
        }
        BlockState $$4 = (BlockState)p_153539_.m_61122_(f_153514_);
        p_153540_.m_46597_(p_153541_, $$4);
        p_153540_.m_220407_(GameEvent.f_157792_, p_153541_, GameEvent.Context.m_223722_($$4));
    }

    @Override
    public int m_6782_(BlockState p_153530_, Level p_153531_, BlockPos p_153532_) {
        return p_153530_.m_61143_(f_153514_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_153549_) {
        p_153549_.m_61104_(f_153514_);
    }

    @Override
    protected void m_142310_(BlockState p_153544_, Level p_153545_, BlockPos p_153546_, Fluid p_153547_) {
        if (this.m_142596_(p_153544_)) {
            return;
        }
        BlockState $$4 = (BlockState)p_153544_.m_61124_(f_153514_, p_153544_.m_61143_(f_153514_) + 1);
        p_153545_.m_46597_(p_153546_, $$4);
        p_153545_.m_220407_(GameEvent.f_157792_, p_153546_, GameEvent.Context.m_223722_($$4));
        p_153545_.m_46796_(1047, p_153546_, 0);
    }
}

