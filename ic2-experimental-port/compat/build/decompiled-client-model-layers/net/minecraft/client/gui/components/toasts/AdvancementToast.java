/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.components.toasts;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

public class AdvancementToast
implements Toast {
    private final Advancement f_94795_;
    private boolean f_94796_;

    public AdvancementToast(Advancement p_94798_) {
        this.f_94795_ = p_94798_;
    }

    @Override
    public Toast.Visibility m_7172_(PoseStack p_94800_, ToastComponent p_94801_, long p_94802_) {
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157456_(0, f_94893_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        DisplayInfo $$3 = this.f_94795_.m_138320_();
        p_94801_.m_93228_(p_94800_, 0, 0, 0, 0, this.m_7828_(), this.m_94899_());
        if ($$3 != null) {
            int $$5;
            List<FormattedCharSequence> $$4 = p_94801_.m_94929_().f_91062_.m_92923_($$3.m_14977_(), 125);
            int n = $$5 = $$3.m_14992_() == FrameType.CHALLENGE ? 0xFF88FF : 0xFFFF00;
            if ($$4.size() == 1) {
                p_94801_.m_94929_().f_91062_.m_92889_(p_94800_, $$3.m_14992_().m_15553_(), 30.0f, 7.0f, $$5 | 0xFF000000);
                p_94801_.m_94929_().f_91062_.m_92877_(p_94800_, $$4.get(0), 30.0f, 18.0f, -1);
            } else {
                int $$6 = 1500;
                float $$7 = 300.0f;
                if (p_94802_ < 1500L) {
                    int $$8 = Mth.m_14143_(Mth.m_14036_((float)(1500L - p_94802_) / 300.0f, 0.0f, 1.0f) * 255.0f) << 24 | 0x4000000;
                    p_94801_.m_94929_().f_91062_.m_92889_(p_94800_, $$3.m_14992_().m_15553_(), 30.0f, 11.0f, $$5 | $$8);
                } else {
                    int $$9 = Mth.m_14143_(Mth.m_14036_((float)(p_94802_ - 1500L) / 300.0f, 0.0f, 1.0f) * 252.0f) << 24 | 0x4000000;
                    int $$10 = this.m_94899_() / 2 - $$4.size() * p_94801_.m_94929_().f_91062_.f_92710_ / 2;
                    for (FormattedCharSequence $$11 : $$4) {
                        p_94801_.m_94929_().f_91062_.m_92877_(p_94800_, $$11, 30.0f, $$10, 0xFFFFFF | $$9);
                        $$10 += p_94801_.m_94929_().f_91062_.f_92710_;
                    }
                }
            }
            if (!this.f_94796_ && p_94802_ > 0L) {
                this.f_94796_ = true;
                if ($$3.m_14992_() == FrameType.CHALLENGE) {
                    p_94801_.m_94929_().m_91106_().m_120367_(SimpleSoundInstance.m_119755_(SoundEvents.f_12496_, 1.0f, 1.0f));
                }
            }
            p_94801_.m_94929_().m_91291_().m_115218_($$3.m_14990_(), 8, 8);
            return p_94802_ >= 5000L ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
        }
        return Toast.Visibility.HIDE;
    }
}

