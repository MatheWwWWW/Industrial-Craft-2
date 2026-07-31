/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class PowderSnowCauldronBlock
extends LayeredCauldronBlock {
    public PowderSnowCauldronBlock(BlockBehaviour.Properties p_154290_, Predicate<Biome.Precipitation> p_154291_, Map<Item, CauldronInteraction> p_154292_) {
        super(p_154290_, p_154291_, p_154292_);
    }

    @Override
    protected void m_142266_(BlockState p_154294_, Level p_154295_, BlockPos p_154296_) {
        PowderSnowCauldronBlock.m_153559_((BlockState)Blocks.f_152476_.m_49966_().m_61124_(f_153514_, p_154294_.m_61143_(f_153514_)), p_154295_, p_154296_);
    }
}

