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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

public abstract class RuleTest {
    public static final Codec<RuleTest> f_74307_ = Registry.f_122861_.m_194605_().dispatch("predicate_type", RuleTest::m_7319_, RuleTestType::m_74324_);

    public abstract boolean m_213865_(BlockState var1, RandomSource var2);

    protected abstract RuleTestType<?> m_7319_();
}

