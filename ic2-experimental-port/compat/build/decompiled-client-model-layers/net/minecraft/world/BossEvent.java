/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world;

import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public abstract class BossEvent {
    private final UUID f_18847_;
    protected Component f_18840_;
    protected float f_146638_;
    protected BossBarColor f_18842_;
    protected BossBarOverlay f_18843_;
    protected boolean f_18844_;
    protected boolean f_18845_;
    protected boolean f_18846_;

    public BossEvent(UUID p_18849_, Component p_18850_, BossBarColor p_18851_, BossBarOverlay p_18852_) {
        this.f_18847_ = p_18849_;
        this.f_18840_ = p_18850_;
        this.f_18842_ = p_18851_;
        this.f_18843_ = p_18852_;
        this.f_146638_ = 1.0f;
    }

    public UUID m_18860_() {
        return this.f_18847_;
    }

    public Component m_18861_() {
        return this.f_18840_;
    }

    public void m_6456_(Component p_18856_) {
        this.f_18840_ = p_18856_;
    }

    public float m_142717_() {
        return this.f_146638_;
    }

    public void m_142711_(float p_146639_) {
        this.f_146638_ = p_146639_;
    }

    public BossBarColor m_18862_() {
        return this.f_18842_;
    }

    public void m_6451_(BossBarColor p_18854_) {
        this.f_18842_ = p_18854_;
    }

    public BossBarOverlay m_18863_() {
        return this.f_18843_;
    }

    public void m_5648_(BossBarOverlay p_18855_) {
        this.f_18843_ = p_18855_;
    }

    public boolean m_18864_() {
        return this.f_18844_;
    }

    public BossEvent m_7003_(boolean p_18857_) {
        this.f_18844_ = p_18857_;
        return this;
    }

    public boolean m_18865_() {
        return this.f_18845_;
    }

    public BossEvent m_7005_(boolean p_18858_) {
        this.f_18845_ = p_18858_;
        return this;
    }

    public BossEvent m_7006_(boolean p_18859_) {
        this.f_18846_ = p_18859_;
        return this;
    }

    public boolean m_18866_() {
        return this.f_18846_;
    }

    public static final class BossBarColor
    extends Enum<BossBarColor> {
        public static final /* enum */ BossBarColor PINK = new BossBarColor("pink", ChatFormatting.RED);
        public static final /* enum */ BossBarColor BLUE = new BossBarColor("blue", ChatFormatting.BLUE);
        public static final /* enum */ BossBarColor RED = new BossBarColor("red", ChatFormatting.DARK_RED);
        public static final /* enum */ BossBarColor GREEN = new BossBarColor("green", ChatFormatting.GREEN);
        public static final /* enum */ BossBarColor YELLOW = new BossBarColor("yellow", ChatFormatting.YELLOW);
        public static final /* enum */ BossBarColor PURPLE = new BossBarColor("purple", ChatFormatting.DARK_BLUE);
        public static final /* enum */ BossBarColor WHITE = new BossBarColor("white", ChatFormatting.WHITE);
        private final String f_18874_;
        private final ChatFormatting f_18875_;
        private static final /* synthetic */ BossBarColor[] $VALUES;

        public static BossBarColor[] values() {
            return (BossBarColor[])$VALUES.clone();
        }

        public static BossBarColor valueOf(String p_18888_) {
            return Enum.valueOf(BossBarColor.class, p_18888_);
        }

        private BossBarColor(String p_18881_, ChatFormatting p_18882_) {
            this.f_18874_ = p_18881_;
            this.f_18875_ = p_18882_;
        }

        public ChatFormatting m_18883_() {
            return this.f_18875_;
        }

        public String m_18886_() {
            return this.f_18874_;
        }

        public static BossBarColor m_18884_(String p_18885_) {
            for (BossBarColor $$1 : BossBarColor.values()) {
                if (!$$1.f_18874_.equals(p_18885_)) continue;
                return $$1;
            }
            return WHITE;
        }

        private static /* synthetic */ BossBarColor[] m_146640_() {
            return new BossBarColor[]{PINK, BLUE, RED, GREEN, YELLOW, PURPLE, WHITE};
        }

        static {
            $VALUES = BossBarColor.m_146640_();
        }
    }

    public static final class BossBarOverlay
    extends Enum<BossBarOverlay> {
        public static final /* enum */ BossBarOverlay PROGRESS = new BossBarOverlay("progress");
        public static final /* enum */ BossBarOverlay NOTCHED_6 = new BossBarOverlay("notched_6");
        public static final /* enum */ BossBarOverlay NOTCHED_10 = new BossBarOverlay("notched_10");
        public static final /* enum */ BossBarOverlay NOTCHED_12 = new BossBarOverlay("notched_12");
        public static final /* enum */ BossBarOverlay NOTCHED_20 = new BossBarOverlay("notched_20");
        private final String f_18895_;
        private static final /* synthetic */ BossBarOverlay[] $VALUES;

        public static BossBarOverlay[] values() {
            return (BossBarOverlay[])$VALUES.clone();
        }

        public static BossBarOverlay valueOf(String p_18906_) {
            return Enum.valueOf(BossBarOverlay.class, p_18906_);
        }

        private BossBarOverlay(String p_18901_) {
            this.f_18895_ = p_18901_;
        }

        public String m_18902_() {
            return this.f_18895_;
        }

        public static BossBarOverlay m_18903_(String p_18904_) {
            for (BossBarOverlay $$1 : BossBarOverlay.values()) {
                if (!$$1.f_18895_.equals(p_18904_)) continue;
                return $$1;
            }
            return PROGRESS;
        }

        private static /* synthetic */ BossBarOverlay[] m_146641_() {
            return new BossBarOverlay[]{PROGRESS, NOTCHED_6, NOTCHED_10, NOTCHED_12, NOTCHED_20};
        }

        static {
            $VALUES = BossBarOverlay.m_146641_();
        }
    }
}

