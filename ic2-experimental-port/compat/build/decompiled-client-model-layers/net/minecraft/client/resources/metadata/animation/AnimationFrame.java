/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resources.metadata.animation;

public class AnimationFrame {
    public static final int f_174855_ = -1;
    private final int f_119001_;
    private final int f_119002_;

    public AnimationFrame(int p_119004_) {
        this(p_119004_, -1);
    }

    public AnimationFrame(int p_119006_, int p_119007_) {
        this.f_119001_ = p_119006_;
        this.f_119002_ = p_119007_;
    }

    public int m_174856_(int p_174857_) {
        return this.f_119002_ == -1 ? p_174857_ : this.f_119002_;
    }

    public int m_119010_() {
        return this.f_119001_;
    }
}

