/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

public class TagMatchTest
extends RuleTest {
    public static final Codec<TagMatchTest> f_74690_ = TagKey.m_203877_(Registry.f_122901_).fieldOf("tag").xmap(TagMatchTest::new, p_205065_ -> p_205065_.f_74691_).codec();
    private final TagKey<Block> f_74691_;

    public TagMatchTest(TagKey<Block> p_205063_) {
        this.f_74691_ = p_205063_;
    }

    @Override
    public boolean m_213865_(BlockState p_230452_, RandomSource p_230453_) {
        return p_230452_.m_204336_(this.f_74691_);
    }

    @Override
    protected RuleTestType<?> m_7319_() {
        return RuleTestType.f_74315_;
    }
}

