/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world;

import java.util.Arrays;
import java.util.Comparator;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;

public final class Difficulty
extends Enum<Difficulty> {
    public static final /* enum */ Difficulty PEACEFUL = new Difficulty(0, "peaceful");
    public static final /* enum */ Difficulty EASY = new Difficulty(1, "easy");
    public static final /* enum */ Difficulty NORMAL = new Difficulty(2, "normal");
    public static final /* enum */ Difficulty HARD = new Difficulty(3, "hard");
    private static final Difficulty[] f_19018_;
    private final int f_19019_;
    private final String f_19020_;
    private static final /* synthetic */ Difficulty[] $VALUES;

    public static Difficulty[] values() {
        return (Difficulty[])$VALUES.clone();
    }

    public static Difficulty valueOf(String p_19039_) {
        return Enum.valueOf(Difficulty.class, p_19039_);
    }

    private Difficulty(int p_19026_, String p_19027_) {
        this.f_19019_ = p_19026_;
        this.f_19020_ = p_19027_;
    }

    public int m_19028_() {
        return this.f_19019_;
    }

    public Component m_19033_() {
        return Component.m_237115_("options.difficulty." + this.f_19020_);
    }

    public static Difficulty m_19029_(int p_19030_) {
        return f_19018_[p_19030_ % f_19018_.length];
    }

    @Nullable
    public static Difficulty m_19031_(String p_19032_) {
        for (Difficulty $$1 : Difficulty.values()) {
            if (!$$1.f_19020_.equals(p_19032_)) continue;
            return $$1;
        }
        return null;
    }

    public String m_19036_() {
        return this.f_19020_;
    }

    private static /* synthetic */ Difficulty[] m_146645_() {
        return new Difficulty[]{PEACEFUL, EASY, NORMAL, HARD};
    }

    static {
        $VALUES = Difficulty.m_146645_();
        f_19018_ = (Difficulty[])Arrays.stream(Difficulty.values()).sorted(Comparator.comparingInt(Difficulty::m_19028_)).toArray(Difficulty[]::new);
    }
}

