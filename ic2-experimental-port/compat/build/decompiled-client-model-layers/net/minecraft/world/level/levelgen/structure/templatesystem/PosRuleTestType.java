/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.templatesystem.AxisAlignedLinearPosTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.LinearPosTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosAlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTest;

public interface PosRuleTestType<P extends PosRuleTest> {
    public static final PosRuleTestType<PosAlwaysTrueTest> f_74205_ = PosRuleTestType.m_74211_("always_true", PosAlwaysTrueTest.f_74187_);
    public static final PosRuleTestType<LinearPosTest> f_74206_ = PosRuleTestType.m_74211_("linear_pos", LinearPosTest.f_74147_);
    public static final PosRuleTestType<AxisAlignedLinearPosTest> f_74207_ = PosRuleTestType.m_74211_("axis_aligned_linear_pos", AxisAlignedLinearPosTest.f_73962_);

    public Codec<P> m_74214_();

    public static <P extends PosRuleTest> PosRuleTestType<P> m_74211_(String p_74212_, Codec<P> p_74213_) {
        return Registry.m_122961_(Registry.f_122862_, p_74212_, () -> p_74213_);
    }
}

