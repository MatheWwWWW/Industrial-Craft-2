/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.packs.metadata.pack;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.metadata.pack.PackMetadataSectionSerializer;

public class PackMetadataSection {
    public static final PackMetadataSectionSerializer f_10366_ = new PackMetadataSectionSerializer();
    private final Component f_10367_;
    private final int f_10368_;

    public PackMetadataSection(Component p_10371_, int p_10372_) {
        this.f_10367_ = p_10371_;
        this.f_10368_ = p_10372_;
    }

    public Component m_10373_() {
        return this.f_10367_;
    }

    public int m_10374_() {
        return this.f_10368_;
    }
}

