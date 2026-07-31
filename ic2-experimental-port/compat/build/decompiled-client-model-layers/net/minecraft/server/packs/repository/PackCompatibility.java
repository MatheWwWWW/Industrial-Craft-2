/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.packs.repository;

import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;

public final class PackCompatibility
extends Enum<PackCompatibility> {
    public static final /* enum */ PackCompatibility TOO_OLD = new PackCompatibility("old");
    public static final /* enum */ PackCompatibility TOO_NEW = new PackCompatibility("new");
    public static final /* enum */ PackCompatibility COMPATIBLE = new PackCompatibility("compatible");
    private final Component f_10481_;
    private final Component f_10482_;
    private static final /* synthetic */ PackCompatibility[] $VALUES;

    public static PackCompatibility[] values() {
        return (PackCompatibility[])$VALUES.clone();
    }

    public static PackCompatibility valueOf(String p_10495_) {
        return Enum.valueOf(PackCompatibility.class, p_10495_);
    }

    private PackCompatibility(String p_10488_) {
        this.f_10481_ = Component.m_237115_("pack.incompatible." + p_10488_).m_130940_(ChatFormatting.GRAY);
        this.f_10482_ = Component.m_237115_("pack.incompatible.confirm." + p_10488_);
    }

    public boolean m_10489_() {
        return this == COMPATIBLE;
    }

    public static PackCompatibility m_143882_(int p_143883_, PackType p_143884_) {
        int $$2 = p_143884_.m_143756_(SharedConstants.m_183709_());
        if (p_143883_ < $$2) {
            return TOO_OLD;
        }
        if (p_143883_ > $$2) {
            return TOO_NEW;
        }
        return COMPATIBLE;
    }

    public static PackCompatibility m_143885_(PackMetadataSection p_143886_, PackType p_143887_) {
        return PackCompatibility.m_143882_(p_143886_.m_10374_(), p_143887_);
    }

    public Component m_10492_() {
        return this.f_10481_;
    }

    public Component m_10493_() {
        return this.f_10482_;
    }

    private static /* synthetic */ PackCompatibility[] m_143888_() {
        return new PackCompatibility[]{TOO_OLD, TOO_NEW, COMPATIBLE};
    }

    static {
        $VALUES = PackCompatibility.m_143888_();
    }
}

