/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resources.metadata.texture;

import net.minecraft.client.resources.metadata.texture.TextureMetadataSectionSerializer;

public class TextureMetadataSection {
    public static final TextureMetadataSectionSerializer f_119108_ = new TextureMetadataSectionSerializer();
    public static final boolean f_174870_ = false;
    public static final boolean f_174871_ = false;
    private final boolean f_119109_;
    private final boolean f_119110_;

    public TextureMetadataSection(boolean p_119113_, boolean p_119114_) {
        this.f_119109_ = p_119113_;
        this.f_119110_ = p_119114_;
    }

    public boolean m_119115_() {
        return this.f_119109_;
    }

    public boolean m_119116_() {
        return this.f_119110_;
    }
}

