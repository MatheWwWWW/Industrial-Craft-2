/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.util.Mth;

public class PanoramaRenderer {
    private final Minecraft f_109998_;
    private final CubeMap f_109999_;
    private float f_110000_;

    public PanoramaRenderer(CubeMap p_110002_) {
        this.f_109999_ = p_110002_;
        this.f_109998_ = Minecraft.m_91087_();
    }

    public void m_110003_(float p_110004_, float p_110005_) {
        this.f_110000_ += p_110004_;
        this.f_109999_.m_108849_(this.f_109998_, Mth.m_14031_(this.f_110000_ * 0.001f) * 5.0f + 25.0f, -this.f_110000_ * 0.1f, p_110005_);
    }
}

