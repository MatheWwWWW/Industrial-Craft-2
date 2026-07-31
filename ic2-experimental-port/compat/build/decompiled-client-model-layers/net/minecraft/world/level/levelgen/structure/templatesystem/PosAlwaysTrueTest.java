/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTestType;

public class PosAlwaysTrueTest
extends PosRuleTest {
    public static final Codec<PosAlwaysTrueTest> f_74187_ = Codec.unit(() -> f_74188_);
    public static final PosAlwaysTrueTest f_74188_ = new PosAlwaysTrueTest();

    private PosAlwaysTrueTest() {
    }

    @Override
    public boolean m_213782_(BlockPos p_230301_, BlockPos p_230302_, BlockPos p_230303_, RandomSource p_230304_) {
        return true;
    }

    @Override
    protected PosRuleTestType<?> m_6158_() {
        return PosRuleTestType.f_74205_;
    }
}

