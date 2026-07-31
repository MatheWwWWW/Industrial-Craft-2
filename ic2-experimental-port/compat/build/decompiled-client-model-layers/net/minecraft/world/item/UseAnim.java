/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

public final class UseAnim
extends Enum<UseAnim> {
    public static final /* enum */ UseAnim NONE = new UseAnim();
    public static final /* enum */ UseAnim EAT = new UseAnim();
    public static final /* enum */ UseAnim DRINK = new UseAnim();
    public static final /* enum */ UseAnim BLOCK = new UseAnim();
    public static final /* enum */ UseAnim BOW = new UseAnim();
    public static final /* enum */ UseAnim SPEAR = new UseAnim();
    public static final /* enum */ UseAnim CROSSBOW = new UseAnim();
    public static final /* enum */ UseAnim SPYGLASS = new UseAnim();
    public static final /* enum */ UseAnim TOOT_HORN = new UseAnim();
    private static final /* synthetic */ UseAnim[] $VALUES;

    public static UseAnim[] values() {
        return (UseAnim[])$VALUES.clone();
    }

    public static UseAnim valueOf(String p_43433_) {
        return Enum.valueOf(UseAnim.class, p_43433_);
    }

    private static /* synthetic */ UseAnim[] m_151234_() {
        return new UseAnim[]{NONE, EAT, DRINK, BLOCK, BOW, SPEAR, CROSSBOW, SPYGLASS, TOOT_HORN};
    }

    static {
        $VALUES = UseAnim.m_151234_();
    }
}

