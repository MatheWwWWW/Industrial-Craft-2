/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.client.color.block;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.IdMapper;
import net.minecraft.core.Registry;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MaterialColor;

public class BlockColors {
    private static final int f_168640_ = -1;
    private final IdMapper<BlockColor> f_92571_ = new IdMapper(32);
    private final Map<Block, Set<Property<?>>> f_92572_ = Maps.newHashMap();

    public static BlockColors m_92574_() {
        BlockColors $$0 = new BlockColors();
        $$0.m_92589_((p_92646_, p_92647_, p_92648_, p_92649_) -> {
            if (p_92647_ == null || p_92648_ == null) {
                return -1;
            }
            return BiomeColors.m_108793_(p_92647_, p_92646_.m_61143_(DoublePlantBlock.f_52858_) == DoubleBlockHalf.UPPER ? p_92648_.m_7495_() : p_92648_);
        }, Blocks.f_50360_, Blocks.f_50359_);
        $$0.m_92586_(DoublePlantBlock.f_52858_, Blocks.f_50360_, Blocks.f_50359_);
        $$0.m_92589_((p_92641_, p_92642_, p_92643_, p_92644_) -> {
            if (p_92642_ == null || p_92643_ == null) {
                return GrassColor.m_46415_(0.5, 1.0);
            }
            return BiomeColors.m_108793_(p_92642_, p_92643_);
        }, Blocks.f_50440_, Blocks.f_50035_, Blocks.f_50034_, Blocks.f_50231_);
        $$0.m_92589_((p_92636_, p_92637_, p_92638_, p_92639_) -> FoliageColor.m_46106_(), Blocks.f_50051_);
        $$0.m_92589_((p_92631_, p_92632_, p_92633_, p_92634_) -> FoliageColor.m_46112_(), Blocks.f_50052_);
        $$0.m_92589_((p_92626_, p_92627_, p_92628_, p_92629_) -> {
            if (p_92627_ == null || p_92628_ == null) {
                return FoliageColor.m_46113_();
            }
            return BiomeColors.m_108804_(p_92627_, p_92628_);
        }, Blocks.f_50050_, Blocks.f_50053_, Blocks.f_50054_, Blocks.f_50055_, Blocks.f_50191_, Blocks.f_220838_);
        $$0.m_92589_((p_92621_, p_92622_, p_92623_, p_92624_) -> {
            if (p_92622_ == null || p_92623_ == null) {
                return -1;
            }
            return BiomeColors.m_108811_(p_92622_, p_92623_);
        }, Blocks.f_49990_, Blocks.f_50628_, Blocks.f_152476_);
        $$0.m_92589_((p_92616_, p_92617_, p_92618_, p_92619_) -> RedStoneWireBlock.m_55606_(p_92616_.m_61143_(RedStoneWireBlock.f_55500_)), Blocks.f_50088_);
        $$0.m_92586_(RedStoneWireBlock.f_55500_, Blocks.f_50088_);
        $$0.m_92589_((p_92611_, p_92612_, p_92613_, p_92614_) -> {
            if (p_92612_ == null || p_92613_ == null) {
                return -1;
            }
            return BiomeColors.m_108793_(p_92612_, p_92613_);
        }, Blocks.f_50130_);
        $$0.m_92589_((p_92606_, p_92607_, p_92608_, p_92609_) -> 14731036, Blocks.f_50188_, Blocks.f_50187_);
        $$0.m_92589_((p_92601_, p_92602_, p_92603_, p_92604_) -> {
            int $$4 = p_92601_.m_61143_(StemBlock.f_57013_);
            int $$5 = $$4 * 32;
            int $$6 = 255 - $$4 * 8;
            int $$7 = $$4 * 4;
            return $$5 << 16 | $$6 << 8 | $$7;
        }, Blocks.f_50190_, Blocks.f_50189_);
        $$0.m_92586_(StemBlock.f_57013_, Blocks.f_50190_, Blocks.f_50189_);
        $$0.m_92589_((p_92596_, p_92597_, p_92598_, p_92599_) -> {
            if (p_92597_ == null || p_92598_ == null) {
                return 7455580;
            }
            return 2129968;
        }, Blocks.f_50196_);
        return $$0;
    }

    public int m_92582_(BlockState p_92583_, Level p_92584_, BlockPos p_92585_) {
        BlockColor $$3 = this.f_92571_.m_7942_(Registry.f_122824_.m_7447_(p_92583_.m_60734_()));
        if ($$3 != null) {
            return $$3.m_92566_(p_92583_, null, null, 0);
        }
        MaterialColor $$4 = p_92583_.m_60780_(p_92584_, p_92585_);
        return $$4 != null ? $$4.f_76396_ : -1;
    }

    public int m_92577_(BlockState p_92578_, @Nullable BlockAndTintGetter p_92579_, @Nullable BlockPos p_92580_, int p_92581_) {
        BlockColor $$4 = this.f_92571_.m_7942_(Registry.f_122824_.m_7447_(p_92578_.m_60734_()));
        return $$4 == null ? -1 : $$4.m_92566_(p_92578_, p_92579_, p_92580_, p_92581_);
    }

    public void m_92589_(BlockColor p_92590_, Block ... p_92591_) {
        for (Block $$2 : p_92591_) {
            this.f_92571_.m_122664_(p_92590_, Registry.f_122824_.m_7447_($$2));
        }
    }

    private void m_92592_(Set<Property<?>> p_92593_, Block ... p_92594_) {
        for (Block $$2 : p_92594_) {
            this.f_92572_.put($$2, p_92593_);
        }
    }

    private void m_92586_(Property<?> p_92587_, Block ... p_92588_) {
        this.m_92592_((Set<Property<?>>)ImmutableSet.of(p_92587_), p_92588_);
    }

    public Set<Property<?>> m_92575_(Block p_92576_) {
        return (Set)this.f_92572_.getOrDefault(p_92576_, (Set<Property<?>>)ImmutableSet.of());
    }
}

