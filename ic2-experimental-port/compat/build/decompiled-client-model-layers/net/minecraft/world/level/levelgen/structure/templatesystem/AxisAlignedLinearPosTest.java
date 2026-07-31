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
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTestType;

public class AxisAlignedLinearPosTest
extends PosRuleTest {
    public static final Codec<AxisAlignedLinearPosTest> f_73962_ = RecordCodecBuilder.create(p_73977_ -> p_73977_.group((App)Codec.FLOAT.fieldOf("min_chance").orElse((Object)Float.valueOf(0.0f)).forGetter(p_163719_ -> Float.valueOf(p_163719_.f_73963_)), (App)Codec.FLOAT.fieldOf("max_chance").orElse((Object)Float.valueOf(0.0f)).forGetter(p_163717_ -> Float.valueOf(p_163717_.f_73964_)), (App)Codec.INT.fieldOf("min_dist").orElse((Object)0).forGetter(p_163715_ -> p_163715_.f_73965_), (App)Codec.INT.fieldOf("max_dist").orElse((Object)0).forGetter(p_163713_ -> p_163713_.f_73966_), (App)Direction.Axis.f_122447_.fieldOf("axis").orElse((Object)Direction.Axis.Y).forGetter(p_163711_ -> p_163711_.f_73967_)).apply((Applicative)p_73977_, AxisAlignedLinearPosTest::new));
    private final float f_73963_;
    private final float f_73964_;
    private final int f_73965_;
    private final int f_73966_;
    private final Direction.Axis f_73967_;

    public AxisAlignedLinearPosTest(float p_73970_, float p_73971_, int p_73972_, int p_73973_, Direction.Axis p_73974_) {
        if (p_73972_ >= p_73973_) {
            throw new IllegalArgumentException("Invalid range: [" + p_73972_ + "," + p_73973_ + "]");
        }
        this.f_73963_ = p_73970_;
        this.f_73964_ = p_73971_;
        this.f_73965_ = p_73972_;
        this.f_73966_ = p_73973_;
        this.f_73967_ = p_73974_;
    }

    @Override
    public boolean m_213782_(BlockPos p_230251_, BlockPos p_230252_, BlockPos p_230253_, RandomSource p_230254_) {
        Direction $$4 = Direction.m_122390_(Direction.AxisDirection.POSITIVE, this.f_73967_);
        float $$5 = Math.abs((p_230252_.m_123341_() - p_230253_.m_123341_()) * $$4.m_122429_());
        float $$6 = Math.abs((p_230252_.m_123342_() - p_230253_.m_123342_()) * $$4.m_122430_());
        float $$7 = Math.abs((p_230252_.m_123343_() - p_230253_.m_123343_()) * $$4.m_122431_());
        int $$8 = (int)($$5 + $$6 + $$7);
        float $$9 = p_230254_.m_188501_();
        return $$9 <= Mth.m_144920_(this.f_73963_, this.f_73964_, Mth.m_184655_($$8, this.f_73965_, this.f_73966_));
    }

    @Override
    protected PosRuleTestType<?> m_6158_() {
        return PosRuleTestType.f_74207_;
    }
}

