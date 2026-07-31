/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.structure.placement;

import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;

public final class RandomSpreadType
extends Enum<RandomSpreadType>
implements StringRepresentable {
    public static final /* enum */ RandomSpreadType LINEAR = new RandomSpreadType("linear");
    public static final /* enum */ RandomSpreadType TRIANGULAR = new RandomSpreadType("triangular");
    public static final Codec<RandomSpreadType> f_205014_;
    private final String f_205016_;
    private static final /* synthetic */ RandomSpreadType[] $VALUES;

    public static RandomSpreadType[] values() {
        return (RandomSpreadType[])$VALUES.clone();
    }

    public static RandomSpreadType valueOf(String p_205032_) {
        return Enum.valueOf(RandomSpreadType.class, p_205032_);
    }

    private RandomSpreadType(String p_205022_) {
        this.f_205016_ = p_205022_;
    }

    @Override
    public String m_7912_() {
        return this.f_205016_;
    }

    public int m_227018_(RandomSource p_227019_, int p_227020_) {
        return switch (this) {
            default -> throw new IncompatibleClassChangeError();
            case LINEAR -> p_227019_.m_188503_(p_227020_);
            case TRIANGULAR -> (p_227019_.m_188503_(p_227020_) + p_227019_.m_188503_(p_227020_)) / 2;
        };
    }

    private static /* synthetic */ RandomSpreadType[] m_205029_() {
        return new RandomSpreadType[]{LINEAR, TRIANGULAR};
    }

    static {
        $VALUES = RandomSpreadType.m_205029_();
        f_205014_ = StringRepresentable.m_216439_(RandomSpreadType::values);
    }
}

