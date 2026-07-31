/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.state.properties;

import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

public final class StructureMode
extends Enum<StructureMode>
implements StringRepresentable {
    public static final /* enum */ StructureMode SAVE = new StructureMode("save");
    public static final /* enum */ StructureMode LOAD = new StructureMode("load");
    public static final /* enum */ StructureMode CORNER = new StructureMode("corner");
    public static final /* enum */ StructureMode DATA = new StructureMode("data");
    private final String f_61802_;
    private final Component f_61803_;
    private static final /* synthetic */ StructureMode[] $VALUES;

    public static StructureMode[] values() {
        return (StructureMode[])$VALUES.clone();
    }

    public static StructureMode valueOf(String p_61813_) {
        return Enum.valueOf(StructureMode.class, p_61813_);
    }

    private StructureMode(String p_61809_) {
        this.f_61802_ = p_61809_;
        this.f_61803_ = Component.m_237115_("structure_block.mode_info." + p_61809_);
    }

    @Override
    public String m_7912_() {
        return this.f_61802_;
    }

    public Component m_61811_() {
        return this.f_61803_;
    }

    private static /* synthetic */ StructureMode[] m_156070_() {
        return new StructureMode[]{SAVE, LOAD, CORNER, DATA};
    }

    static {
        $VALUES = StructureMode.m_156070_();
    }
}

