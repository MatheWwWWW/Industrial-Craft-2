/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public interface RuleTestType<P extends RuleTest> {
    public static final RuleTestType<AlwaysTrueTest> f_74312_ = RuleTestType.m_74321_("always_true", AlwaysTrueTest.f_73953_);
    public static final RuleTestType<BlockMatchTest> f_74313_ = RuleTestType.m_74321_("block_match", BlockMatchTest.f_74063_);
    public static final RuleTestType<BlockStateMatchTest> f_74314_ = RuleTestType.m_74321_("blockstate_match", BlockStateMatchTest.f_74089_);
    public static final RuleTestType<TagMatchTest> f_74315_ = RuleTestType.m_74321_("tag_match", TagMatchTest.f_74690_);
    public static final RuleTestType<RandomBlockMatchTest> f_74316_ = RuleTestType.m_74321_("random_block_match", RandomBlockMatchTest.f_74258_);
    public static final RuleTestType<RandomBlockStateMatchTest> f_74317_ = RuleTestType.m_74321_("random_blockstate_match", RandomBlockStateMatchTest.f_74275_);

    public Codec<P> m_74324_();

    public static <P extends RuleTest> RuleTestType<P> m_74321_(String p_74322_, Codec<P> p_74323_) {
        return Registry.m_122961_(Registry.f_122861_, p_74322_, () -> p_74323_);
    }
}

