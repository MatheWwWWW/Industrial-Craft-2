/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

public final class ClickType
extends Enum<ClickType> {
    public static final /* enum */ ClickType PICKUP = new ClickType();
    public static final /* enum */ ClickType QUICK_MOVE = new ClickType();
    public static final /* enum */ ClickType SWAP = new ClickType();
    public static final /* enum */ ClickType CLONE = new ClickType();
    public static final /* enum */ ClickType THROW = new ClickType();
    public static final /* enum */ ClickType QUICK_CRAFT = new ClickType();
    public static final /* enum */ ClickType PICKUP_ALL = new ClickType();
    private static final /* synthetic */ ClickType[] $VALUES;

    public static ClickType[] values() {
        return (ClickType[])$VALUES.clone();
    }

    public static ClickType valueOf(String p_39282_) {
        return Enum.valueOf(ClickType.class, p_39282_);
    }

    private static /* synthetic */ ClickType[] m_150523_() {
        return new ClickType[]{PICKUP, QUICK_MOVE, SWAP, CLONE, THROW, QUICK_CRAFT, PICKUP_ALL};
    }

    static {
        $VALUES = ClickType.m_150523_();
    }
}

