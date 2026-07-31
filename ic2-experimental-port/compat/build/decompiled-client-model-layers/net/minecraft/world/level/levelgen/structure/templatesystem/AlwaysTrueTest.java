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

public class AlwaysTrueTest
extends RuleTest {
    public static final Codec<AlwaysTrueTest> f_73953_ = Codec.unit(() -> f_73954_);
    public static final AlwaysTrueTest f_73954_ = new AlwaysTrueTest();

    private AlwaysTrueTest() {
    }

    @Override
    public boolean m_213865_(BlockState p_230248_, RandomSource p_230249_) {
        return true;
    }

    @Override
    protected RuleTestType<?> m_7319_() {
        return RuleTestType.f_74312_;
    }
}

