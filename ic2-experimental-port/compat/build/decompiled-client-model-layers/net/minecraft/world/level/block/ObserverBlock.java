/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ObserverBlock
extends DirectionalBlock {
    public static final BooleanProperty f_55082_ = BlockStateProperties.f_61448_;

    public ObserverBlock(BlockBehaviour.Properties p_55085_) {
        super(p_55085_);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_52588_, Direction.SOUTH)).m_61124_(f_55082_, false));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_55125_) {
        p_55125_.m_61104_(f_52588_, f_55082_);
    }

    @Override
    public BlockState m_6843_(BlockState p_55115_, Rotation p_55116_) {
        return (BlockState)p_55115_.m_61124_(f_52588_, p_55116_.m_55954_(p_55115_.m_61143_(f_52588_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_55112_, Mirror p_55113_) {
        return p_55112_.m_60717_(p_55113_.m_54846_(p_55112_.m_61143_(f_52588_)));
    }

    @Override
    public void m_213897_(BlockState p_221840_, ServerLevel p_221841_, BlockPos p_221842_, RandomSource p_221843_) {
        if (p_221840_.m_61143_(f_55082_).booleanValue()) {
            p_221841_.m_7731_(p_221842_, (BlockState)p_221840_.m_61124_(f_55082_, false), 2);
        } else {
            p_221841_.m_7731_(p_221842_, (BlockState)p_221840_.m_61124_(f_55082_, true), 2);
            p_221841_.m_186460_(p_221842_, this, 2);
        }
        this.m_55088_(p_221841_, p_221842_, p_221840_);
    }

    @Override
    public BlockState m_7417_(BlockState p_55118_, Direction p_55119_, BlockState p_55120_, LevelAccessor p_55121_, BlockPos p_55122_, BlockPos p_55123_) {
        if (p_55118_.m_61143_(f_52588_) == p_55119_ && !p_55118_.m_61143_(f_55082_).booleanValue()) {
            this.m_55092_(p_55121_, p_55122_);
        }
        return super.m_7417_(p_55118_, p_55119_, p_55120_, p_55121_, p_55122_, p_55123_);
    }

    private void m_55092_(LevelAccessor p_55093_, BlockPos p_55094_) {
        if (!p_55093_.m_5776_() && !p_55093_.m_183326_().m_183582_(p_55094_, this)) {
            p_55093_.m_186460_(p_55094_, this, 2);
        }
    }

    protected void m_55088_(Level p_55089_, BlockPos p_55090_, BlockState p_55091_) {
        Direction $$3 = p_55091_.m_61143_(f_52588_);
        BlockPos $$4 = p_55090_.m_121945_($$3.m_122424_());
        p_55089_.m_46586_($$4, this, p_55090_);
        p_55089_.m_46590_($$4, this, $$3);
    }

    @Override
    public boolean m_7899_(BlockState p_55138_) {
        return true;
    }

    @Override
    public int m_6376_(BlockState p_55127_, BlockGetter p_55128_, BlockPos p_55129_, Direction p_55130_) {
        return p_55127_.m_60746_(p_55128_, p_55129_, p_55130_);
    }

    @Override
    public int m_6378_(BlockState p_55101_, BlockGetter p_55102_, BlockPos p_55103_, Direction p_55104_) {
        if (p_55101_.m_61143_(f_55082_).booleanValue() && p_55101_.m_61143_(f_52588_) == p_55104_) {
            return 15;
        }
        return 0;
    }

    @Override
    public void m_6807_(BlockState p_55132_, Level p_55133_, BlockPos p_55134_, BlockState p_55135_, boolean p_55136_) {
        if (p_55132_.m_60713_(p_55135_.m_60734_())) {
            return;
        }
        if (!p_55133_.m_5776_() && p_55132_.m_61143_(f_55082_).booleanValue() && !p_55133_.m_183326_().m_183582_(p_55134_, this)) {
            BlockState $$5 = (BlockState)p_55132_.m_61124_(f_55082_, false);
            p_55133_.m_7731_(p_55134_, $$5, 18);
            this.m_55088_(p_55133_, p_55134_, $$5);
        }
    }

    @Override
    public void m_6810_(BlockState p_55106_, Level p_55107_, BlockPos p_55108_, BlockState p_55109_, boolean p_55110_) {
        if (p_55106_.m_60713_(p_55109_.m_60734_())) {
            return;
        }
        if (!p_55107_.f_46443_ && p_55106_.m_61143_(f_55082_).booleanValue() && p_55107_.m_183326_().m_183582_(p_55108_, this)) {
            this.m_55088_(p_55107_, p_55108_, (BlockState)p_55106_.m_61124_(f_55082_, false));
        }
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_55087_) {
        return (BlockState)this.m_49966_().m_61124_(f_52588_, p_55087_.m_7820_().m_122424_().m_122424_());
    }
}

