/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.texture.DynamicTexture;

public class OverlayTexture
implements AutoCloseable {
    private static final int f_174694_ = 16;
    public static final int f_174691_ = 0;
    public static final int f_174692_ = 3;
    public static final int f_174693_ = 10;
    public static final int f_118083_ = OverlayTexture.m_118093_(0, 10);
    private final DynamicTexture f_118084_ = new DynamicTexture(16, 16, false);

    public OverlayTexture() {
        NativeImage $$0 = this.f_118084_.m_117991_();
        for (int $$1 = 0; $$1 < 16; ++$$1) {
            for (int $$2 = 0; $$2 < 16; ++$$2) {
                if ($$1 < 8) {
                    $$0.m_84988_($$2, $$1, -1308622593);
                    continue;
                }
                int $$3 = (int)((1.0f - (float)$$2 / 15.0f * 0.75f) * 255.0f);
                $$0.m_84988_($$2, $$1, $$3 << 24 | 0xFFFFFF);
            }
        }
        RenderSystem.m_69388_(33985);
        this.f_118084_.m_117966_();
        $$0.m_85013_(0, 0, 0, 0, 0, $$0.m_84982_(), $$0.m_85084_(), false, true, false, false);
        RenderSystem.m_69388_(33984);
    }

    @Override
    public void close() {
        this.f_118084_.close();
    }

    public void m_118087_() {
        RenderSystem.m_69920_(this.f_118084_::m_117963_, 16);
    }

    public static int m_118088_(float p_118089_) {
        return (int)(p_118089_ * 15.0f);
    }

    public static int m_118096_(boolean p_118097_) {
        return p_118097_ ? 3 : 10;
    }

    public static int m_118093_(int p_118094_, int p_118095_) {
        return p_118094_ | p_118095_ << 16;
    }

    public static int m_118090_(float p_118091_, boolean p_118092_) {
        return OverlayTexture.m_118093_(OverlayTexture.m_118088_(p_118091_), OverlayTexture.m_118096_(p_118092_));
    }

    public void m_118098_() {
        RenderSystem.m_69936_();
    }
}

