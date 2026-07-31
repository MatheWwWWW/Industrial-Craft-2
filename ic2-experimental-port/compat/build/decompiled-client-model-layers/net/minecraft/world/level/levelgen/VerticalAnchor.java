/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Either
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.function.Function;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.WorldGenerationContext;

public interface VerticalAnchor {
    public static final Codec<VerticalAnchor> f_158914_ = ExtraCodecs.m_144639_(Absolute.f_158944_, ExtraCodecs.m_144639_(AboveBottom.f_158937_, BelowTop.f_158951_)).xmap(VerticalAnchor::m_158924_, VerticalAnchor::m_158926_);
    public static final VerticalAnchor f_158915_ = VerticalAnchor.m_158930_(0);
    public static final VerticalAnchor f_158916_ = VerticalAnchor.m_158935_(0);

    public static VerticalAnchor m_158922_(int p_158923_) {
        return new Absolute(p_158923_);
    }

    public static VerticalAnchor m_158930_(int p_158931_) {
        return new AboveBottom(p_158931_);
    }

    public static VerticalAnchor m_158935_(int p_158936_) {
        return new BelowTop(p_158936_);
    }

    public static VerticalAnchor m_158921_() {
        return f_158915_;
    }

    public static VerticalAnchor m_158929_() {
        return f_158916_;
    }

    private static VerticalAnchor m_158924_(Either<Absolute, Either<AboveBottom, BelowTop>> p_158925_) {
        return (VerticalAnchor)p_158925_.map(Function.identity(), p_209698_ -> (Record)p_209698_.map(Function.identity(), Function.identity()));
    }

    private static Either<Absolute, Either<AboveBottom, BelowTop>> m_158926_(VerticalAnchor p_158927_) {
        if (p_158927_ instanceof Absolute) {
            return Either.left((Object)((Absolute)p_158927_));
        }
        return Either.right((Object)(p_158927_ instanceof AboveBottom ? Either.left((Object)((AboveBottom)p_158927_)) : Either.right((Object)((BelowTop)p_158927_))));
    }

    public int m_142322_(WorldGenerationContext var1);

    public record Absolute(int f_209704_) implements VerticalAnchor
    {
        public static final Codec<Absolute> f_158944_ = Codec.intRange((int)DimensionType.f_156653_, (int)DimensionType.f_156652_).fieldOf("absolute").xmap(Absolute::new, Absolute::f_209704_).codec();

        @Override
        public int m_142322_(WorldGenerationContext p_158949_) {
            return this.f_209704_;
        }

        @Override
        public String toString() {
            return this.f_209704_ + " absolute";
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Absolute.class, "y", "f_209704_"}, this);
        }

        @Override
        public final boolean equals(Object p_209707_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Absolute.class, "y", "f_209704_"}, this, p_209707_);
        }
    }

    public record AboveBottom(int f_209699_) implements VerticalAnchor
    {
        public static final Codec<AboveBottom> f_158937_ = Codec.intRange((int)DimensionType.f_156653_, (int)DimensionType.f_156652_).fieldOf("above_bottom").xmap(AboveBottom::new, AboveBottom::f_209699_).codec();

        @Override
        public int m_142322_(WorldGenerationContext p_158942_) {
            return p_158942_.m_142201_() + this.f_209699_;
        }

        @Override
        public String toString() {
            return this.f_209699_ + " above bottom";
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{AboveBottom.class, "offset", "f_209699_"}, this);
        }

        @Override
        public final boolean equals(Object p_209702_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{AboveBottom.class, "offset", "f_209699_"}, this, p_209702_);
        }
    }

    public record BelowTop(int f_209709_) implements VerticalAnchor
    {
        public static final Codec<BelowTop> f_158951_ = Codec.intRange((int)DimensionType.f_156653_, (int)DimensionType.f_156652_).fieldOf("below_top").xmap(BelowTop::new, BelowTop::f_209709_).codec();

        @Override
        public int m_142322_(WorldGenerationContext p_158956_) {
            return p_158956_.m_142208_() - 1 + p_158956_.m_142201_() - this.f_209709_;
        }

        @Override
        public String toString() {
            return this.f_209709_ + " below top";
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{BelowTop.class, "offset", "f_209709_"}, this);
        }

        @Override
        public final boolean equals(Object p_209712_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{BelowTop.class, "offset", "f_209709_"}, this, p_209712_);
        }
    }
}

