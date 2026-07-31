/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

public class BlockStateMatchTest
extends RuleTest {
    public static final Codec<BlockStateMatchTest> f_74089_ = BlockState.f_61039_.fieldOf("block_state").xmap(BlockStateMatchTest::new, p_74099_ -> p_74099_.f_74090_).codec();
    private final BlockState f_74090_;

    public BlockStateMatchTest(BlockState p_74093_) {
        this.f_74090_ = p_74093_;
    }

    @Override
    public boolean m_213865_(BlockState p_230293_, RandomSource p_230294_) {
        return p_230293_ == this.f_74090_;
    }

    @Override
    protected RuleTestType<?> m_7319_() {
        return RuleTestType.f_74314_;
    }
}

