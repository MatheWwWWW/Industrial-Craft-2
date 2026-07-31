/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.data.models.model;

import javax.annotation.Nullable;

public final class TextureSlot {
    public static final TextureSlot f_125867_ = TextureSlot.m_125898_("all");
    public static final TextureSlot f_125868_ = TextureSlot.m_125900_("texture", f_125867_);
    public static final TextureSlot f_125869_ = TextureSlot.m_125900_("particle", f_125868_);
    public static final TextureSlot f_125870_ = TextureSlot.m_125900_("end", f_125867_);
    public static final TextureSlot f_125871_ = TextureSlot.m_125900_("bottom", f_125870_);
    public static final TextureSlot f_125872_ = TextureSlot.m_125900_("top", f_125870_);
    public static final TextureSlot f_125873_ = TextureSlot.m_125900_("front", f_125867_);
    public static final TextureSlot f_125874_ = TextureSlot.m_125900_("back", f_125867_);
    public static final TextureSlot f_125875_ = TextureSlot.m_125900_("side", f_125867_);
    public static final TextureSlot f_125876_ = TextureSlot.m_125900_("north", f_125875_);
    public static final TextureSlot f_125877_ = TextureSlot.m_125900_("south", f_125875_);
    public static final TextureSlot f_125878_ = TextureSlot.m_125900_("east", f_125875_);
    public static final TextureSlot f_125879_ = TextureSlot.m_125900_("west", f_125875_);
    public static final TextureSlot f_125880_ = TextureSlot.m_125898_("up");
    public static final TextureSlot f_125881_ = TextureSlot.m_125898_("down");
    public static final TextureSlot f_125882_ = TextureSlot.m_125898_("cross");
    public static final TextureSlot f_125883_ = TextureSlot.m_125898_("plant");
    public static final TextureSlot f_125884_ = TextureSlot.m_125900_("wall", f_125867_);
    public static final TextureSlot f_125885_ = TextureSlot.m_125898_("rail");
    public static final TextureSlot f_125886_ = TextureSlot.m_125898_("wool");
    public static final TextureSlot f_125887_ = TextureSlot.m_125898_("pattern");
    public static final TextureSlot f_125888_ = TextureSlot.m_125898_("pane");
    public static final TextureSlot f_125889_ = TextureSlot.m_125898_("edge");
    public static final TextureSlot f_125890_ = TextureSlot.m_125898_("fan");
    public static final TextureSlot f_125891_ = TextureSlot.m_125898_("stem");
    public static final TextureSlot f_125892_ = TextureSlot.m_125898_("upperstem");
    public static final TextureSlot f_125856_ = TextureSlot.m_125898_("crop");
    public static final TextureSlot f_125857_ = TextureSlot.m_125898_("dirt");
    public static final TextureSlot f_125858_ = TextureSlot.m_125898_("fire");
    public static final TextureSlot f_125859_ = TextureSlot.m_125898_("lantern");
    public static final TextureSlot f_125860_ = TextureSlot.m_125898_("platform");
    public static final TextureSlot f_125861_ = TextureSlot.m_125898_("unsticky");
    public static final TextureSlot f_125862_ = TextureSlot.m_125898_("torch");
    public static final TextureSlot f_125863_ = TextureSlot.m_125898_("layer0");
    public static final TextureSlot f_125864_ = TextureSlot.m_125898_("lit_log");
    public static final TextureSlot f_176490_ = TextureSlot.m_125898_("candle");
    public static final TextureSlot f_176491_ = TextureSlot.m_125898_("inside");
    public static final TextureSlot f_176492_ = TextureSlot.m_125898_("content");
    public static final TextureSlot f_236352_ = TextureSlot.m_125898_("inner_top");
    private final String f_125865_;
    @Nullable
    private final TextureSlot f_125866_;

    private static TextureSlot m_125898_(String p_125899_) {
        return new TextureSlot(p_125899_, null);
    }

    private static TextureSlot m_125900_(String p_125901_, TextureSlot p_125902_) {
        return new TextureSlot(p_125901_, p_125902_);
    }

    private TextureSlot(String p_125895_, @Nullable TextureSlot p_125896_) {
        this.f_125865_ = p_125895_;
        this.f_125866_ = p_125896_;
    }

    public String m_125897_() {
        return this.f_125865_;
    }

    @Nullable
    public TextureSlot m_125903_() {
        return this.f_125866_;
    }

    public String toString() {
        return "#" + this.f_125865_;
    }
}

