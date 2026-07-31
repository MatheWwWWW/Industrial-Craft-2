/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.saveddata.maps;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class MapDecoration {
    private final Type f_77791_;
    private final byte f_77792_;
    private final byte f_77793_;
    private final byte f_77794_;
    @Nullable
    private final Component f_77795_;

    public MapDecoration(Type p_77797_, byte p_77798_, byte p_77799_, byte p_77800_, @Nullable Component p_77801_) {
        this.f_77791_ = p_77797_;
        this.f_77792_ = p_77798_;
        this.f_77793_ = p_77799_;
        this.f_77794_ = p_77800_;
        this.f_77795_ = p_77801_;
    }

    public byte m_77802_() {
        return this.f_77791_.m_77853_();
    }

    public Type m_77803_() {
        return this.f_77791_;
    }

    public byte m_77804_() {
        return this.f_77792_;
    }

    public byte m_77805_() {
        return this.f_77793_;
    }

    public byte m_77806_() {
        return this.f_77794_;
    }

    public boolean m_77809_() {
        return this.f_77791_.m_77856_();
    }

    @Nullable
    public Component m_77810_() {
        return this.f_77795_;
    }

    public boolean equals(Object p_77808_) {
        if (this == p_77808_) {
            return true;
        }
        if (!(p_77808_ instanceof MapDecoration)) {
            return false;
        }
        MapDecoration $$1 = (MapDecoration)p_77808_;
        return this.f_77791_ == $$1.f_77791_ && this.f_77794_ == $$1.f_77794_ && this.f_77792_ == $$1.f_77792_ && this.f_77793_ == $$1.f_77793_ && Objects.equals(this.f_77795_, $$1.f_77795_);
    }

    public int hashCode() {
        int $$0 = this.f_77791_.m_77853_();
        $$0 = 31 * $$0 + this.f_77792_;
        $$0 = 31 * $$0 + this.f_77793_;
        $$0 = 31 * $$0 + this.f_77794_;
        $$0 = 31 * $$0 + Objects.hashCode(this.f_77795_);
        return $$0;
    }

    public static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type PLAYER = new Type(false, true);
        public static final /* enum */ Type FRAME = new Type(true, true);
        public static final /* enum */ Type RED_MARKER = new Type(false, true);
        public static final /* enum */ Type BLUE_MARKER = new Type(false, true);
        public static final /* enum */ Type TARGET_X = new Type(true, false);
        public static final /* enum */ Type TARGET_POINT = new Type(true, false);
        public static final /* enum */ Type PLAYER_OFF_MAP = new Type(false, true);
        public static final /* enum */ Type PLAYER_OFF_LIMITS = new Type(false, true);
        public static final /* enum */ Type MANSION = new Type(true, 5393476, false);
        public static final /* enum */ Type MONUMENT = new Type(true, 3830373, false);
        public static final /* enum */ Type BANNER_WHITE = new Type(true, true);
        public static final /* enum */ Type BANNER_ORANGE = new Type(true, true);
        public static final /* enum */ Type BANNER_MAGENTA = new Type(true, true);
        public static final /* enum */ Type BANNER_LIGHT_BLUE = new Type(true, true);
        public static final /* enum */ Type BANNER_YELLOW = new Type(true, true);
        public static final /* enum */ Type BANNER_LIME = new Type(true, true);
        public static final /* enum */ Type BANNER_PINK = new Type(true, true);
        public static final /* enum */ Type BANNER_GRAY = new Type(true, true);
        public static final /* enum */ Type BANNER_LIGHT_GRAY = new Type(true, true);
        public static final /* enum */ Type BANNER_CYAN = new Type(true, true);
        public static final /* enum */ Type BANNER_PURPLE = new Type(true, true);
        public static final /* enum */ Type BANNER_BLUE = new Type(true, true);
        public static final /* enum */ Type BANNER_BROWN = new Type(true, true);
        public static final /* enum */ Type BANNER_GREEN = new Type(true, true);
        public static final /* enum */ Type BANNER_RED = new Type(true, true);
        public static final /* enum */ Type BANNER_BLACK = new Type(true, true);
        public static final /* enum */ Type RED_X = new Type(true, false);
        private final byte f_77813_;
        private final boolean f_77814_;
        private final int f_77815_;
        private final boolean f_181294_;
        private static final /* synthetic */ Type[] $VALUES;

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String p_77860_) {
            return Enum.valueOf(Type.class, p_77860_);
        }

        private Type(boolean p_181304_, boolean p_181305_) {
            this(p_181304_, -1, p_181305_);
        }

        private Type(boolean p_181298_, int p_181299_, boolean p_181300_) {
            this.f_181294_ = p_181300_;
            this.f_77813_ = (byte)this.ordinal();
            this.f_77814_ = p_181298_;
            this.f_77815_ = p_181299_;
        }

        public byte m_77853_() {
            return this.f_77813_;
        }

        public boolean m_77856_() {
            return this.f_77814_;
        }

        public boolean m_77857_() {
            return this.f_77815_ >= 0;
        }

        public int m_77858_() {
            return this.f_77815_;
        }

        public static Type m_77854_(byte p_77855_) {
            return Type.values()[Mth.m_14045_(p_77855_, 0, Type.values().length - 1)];
        }

        public boolean m_181306_() {
            return this.f_181294_;
        }

        private static /* synthetic */ Type[] m_164760_() {
            return new Type[]{PLAYER, FRAME, RED_MARKER, BLUE_MARKER, TARGET_X, TARGET_POINT, PLAYER_OFF_MAP, PLAYER_OFF_LIMITS, MANSION, MONUMENT, BANNER_WHITE, BANNER_ORANGE, BANNER_MAGENTA, BANNER_LIGHT_BLUE, BANNER_YELLOW, BANNER_LIME, BANNER_PINK, BANNER_GRAY, BANNER_LIGHT_GRAY, BANNER_CYAN, BANNER_PURPLE, BANNER_BLUE, BANNER_BROWN, BANNER_GREEN, BANNER_RED, BANNER_BLACK, RED_X};
        }

        static {
            $VALUES = Type.m_164760_();
        }
    }
}

