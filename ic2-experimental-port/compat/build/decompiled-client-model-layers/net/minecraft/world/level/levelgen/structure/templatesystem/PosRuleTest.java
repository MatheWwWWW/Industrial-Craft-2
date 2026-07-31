/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTestType;

public abstract class PosRuleTest {
    public static final Codec<PosRuleTest> f_74198_ = Registry.f_122862_.m_194605_().dispatch("predicate_type", PosRuleTest::m_6158_, PosRuleTestType::m_74214_);

    public abstract boolean m_213782_(BlockPos var1, BlockPos var2, BlockPos var3, RandomSource var4);

    protected abstract PosRuleTestType<?> m_6158_();
}

