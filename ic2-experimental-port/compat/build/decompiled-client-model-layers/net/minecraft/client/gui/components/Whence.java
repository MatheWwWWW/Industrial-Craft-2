/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.components;

public final class Whence
extends Enum<Whence> {
    public static final /* enum */ Whence ABSOLUTE = new Whence();
    public static final /* enum */ Whence RELATIVE = new Whence();
    public static final /* enum */ Whence END = new Whence();
    private static final /* synthetic */ Whence[] $VALUES;

    public static Whence[] values() {
        return (Whence[])$VALUES.clone();
    }

    public static Whence valueOf(String p_239772_) {
        return Enum.valueOf(Whence.class, p_239772_);
    }

    private static /* synthetic */ Whence[] m_239202_() {
        return new Whence[]{ABSOLUTE, RELATIVE, END};
    }

    static {
        $VALUES = Whence.m_239202_();
    }
}

