/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

public class BlockMatchTest
extends RuleTest {
    public static final Codec<BlockMatchTest> f_74063_ = Registry.f_122824_.m_194605_().fieldOf("block").xmap(BlockMatchTest::new, p_74073_ -> p_74073_.f_74064_).codec();
    private final Block f_74064_;

    public BlockMatchTest(Block p_74067_) {
        this.f_74064_ = p_74067_;
    }

    @Override
    public boolean m_213865_(BlockState p_230277_, RandomSource p_230278_) {
        return p_230277_.m_60713_(this.f_74064_);
    }

    @Override
    protected RuleTestType<?> m_7319_() {
        return RuleTestType.f_74313_;
    }
}

