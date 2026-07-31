/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;

public class BakedQuad {
    protected final int[] f_111292_;
    protected final int f_111293_;
    protected final Direction f_111294_;
    protected final TextureAtlasSprite f_111295_;
    private final boolean f_111296_;

    public BakedQuad(int[] p_111298_, int p_111299_, Direction p_111300_, TextureAtlasSprite p_111301_, boolean p_111302_) {
        this.f_111292_ = p_111298_;
        this.f_111293_ = p_111299_;
        this.f_111294_ = p_111300_;
        this.f_111295_ = p_111301_;
        this.f_111296_ = p_111302_;
    }

    public TextureAtlasSprite m_173410_() {
        return this.f_111295_;
    }

    public int[] m_111303_() {
        return this.f_111292_;
    }

    public boolean m_111304_() {
        return this.f_111293_ != -1;
    }

    public int m_111305_() {
        return this.f_111293_;
    }

    public Direction m_111306_() {
        return this.f_111294_;
    }

    public boolean m_111307_() {
        return this.f_111296_;
    }
}

