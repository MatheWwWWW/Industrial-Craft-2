/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTestType;

public class LinearPosTest
extends PosRuleTest {
    public static final Codec<LinearPosTest> f_74147_ = RecordCodecBuilder.create(p_74160_ -> p_74160_.group((App)Codec.FLOAT.fieldOf("min_chance").orElse((Object)Float.valueOf(0.0f)).forGetter(p_163737_ -> Float.valueOf(p_163737_.f_74148_)), (App)Codec.FLOAT.fieldOf("max_chance").orElse((Object)Float.valueOf(0.0f)).forGetter(p_163735_ -> Float.valueOf(p_163735_.f_74149_)), (App)Codec.INT.fieldOf("min_dist").orElse((Object)0).forGetter(p_163733_ -> p_163733_.f_74150_), (App)Codec.INT.fieldOf("max_dist").orElse((Object)0).forGetter(p_163731_ -> p_163731_.f_74151_)).apply((Applicative)p_74160_, LinearPosTest::new));
    private final float f_74148_;
    private final float f_74149_;
    private final int f_74150_;
    private final int f_74151_;

    public LinearPosTest(float p_74154_, float p_74155_, int p_74156_, int p_74157_) {
        if (p_74156_ >= p_74157_) {
            throw new IllegalArgumentException("Invalid range: [" + p_74156_ + "," + p_74157_ + "]");
        }
        this.f_74148_ = p_74154_;
        this.f_74149_ = p_74155_;
        this.f_74150_ = p_74156_;
        this.f_74151_ = p_74157_;
    }

    @Override
    public boolean m_213782_(BlockPos p_230296_, BlockPos p_230297_, BlockPos p_230298_, RandomSource p_230299_) {
        int $$4 = p_230297_.m_123333_(p_230298_);
        float $$5 = p_230299_.m_188501_();
        return $$5 <= Mth.m_144920_(this.f_74148_, this.f_74149_, Mth.m_184655_($$4, this.f_74150_, this.f_74151_));
    }

    @Override
    protected PosRuleTestType<?> m_6158_() {
        return PosRuleTestType.f_74206_;
    }
}

