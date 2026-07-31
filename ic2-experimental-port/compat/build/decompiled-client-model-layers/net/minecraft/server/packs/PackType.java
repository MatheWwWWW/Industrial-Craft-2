/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.bridge.game.GameVersion
 *  com.mojang.bridge.game.PackType
 */
package net.minecraft.server.packs;

import com.mojang.bridge.game.GameVersion;

public final class PackType
extends Enum<PackType> {
    public static final /* enum */ PackType CLIENT_RESOURCES = new PackType("assets", com.mojang.bridge.game.PackType.RESOURCE);
    public static final /* enum */ PackType SERVER_DATA = new PackType("data", com.mojang.bridge.game.PackType.DATA);
    private final String f_10298_;
    private final com.mojang.bridge.game.PackType f_143750_;
    private static final /* synthetic */ PackType[] $VALUES;

    public static PackType[] values() {
        return (PackType[])$VALUES.clone();
    }

    public static PackType valueOf(String p_10307_) {
        return Enum.valueOf(PackType.class, p_10307_);
    }

    private PackType(String p_143754_, com.mojang.bridge.game.PackType p_143755_) {
        this.f_10298_ = p_143754_;
        this.f_143750_ = p_143755_;
    }

    public String m_10305_() {
        return this.f_10298_;
    }

    public int m_143756_(GameVersion p_143757_) {
        return p_143757_.getPackVersion(this.f_143750_);
    }

    private static /* synthetic */ PackType[] m_143758_() {
        return new PackType[]{CLIENT_RESOURCES, SERVER_DATA};
    }

    static {
        $VALUES = PackType.m_143758_();
    }
}

