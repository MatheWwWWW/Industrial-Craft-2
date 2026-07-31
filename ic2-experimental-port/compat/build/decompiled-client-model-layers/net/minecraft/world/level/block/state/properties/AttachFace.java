/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public final class AttachFace
extends Enum<AttachFace>
implements StringRepresentable {
    public static final /* enum */ AttachFace FLOOR = new AttachFace("floor");
    public static final /* enum */ AttachFace WALL = new AttachFace("wall");
    public static final /* enum */ AttachFace CEILING = new AttachFace("ceiling");
    private final String f_61305_;
    private static final /* synthetic */ AttachFace[] $VALUES;

    public static AttachFace[] values() {
        return (AttachFace[])$VALUES.clone();
    }

    public static AttachFace valueOf(String p_61314_) {
        return Enum.valueOf(AttachFace.class, p_61314_);
    }

    private AttachFace(String p_61311_) {
        this.f_61305_ = p_61311_;
    }

    @Override
    public String m_7912_() {
        return this.f_61305_;
    }

    private static /* synthetic */ AttachFace[] m_155973_() {
        return new AttachFace[]{FLOOR, WALL, CEILING};
    }

    static {
        $VALUES = AttachFace.m_155973_();
    }
}

