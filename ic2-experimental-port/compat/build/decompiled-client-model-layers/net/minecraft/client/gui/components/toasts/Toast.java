/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.components.toasts;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;

public interface Toast {
    public static final ResourceLocation f_94893_ = new ResourceLocation("textures/gui/toasts.png");
    public static final Object f_94894_ = new Object();
    public static final int f_243003_ = 32;

    public Visibility m_7172_(PoseStack var1, ToastComponent var2, long var3);

    default public Object m_7283_() {
        return f_94894_;
    }

    default public int m_7828_() {
        return 160;
    }

    default public int m_94899_() {
        return 32;
    }

    default public int m_243110_() {
        return Mth.m_184652_(this.m_94899_(), 32);
    }

    public static final class Visibility
    extends Enum<Visibility> {
        public static final /* enum */ Visibility SHOW = new Visibility(SoundEvents.f_12497_);
        public static final /* enum */ Visibility HIDE = new Visibility(SoundEvents.f_12498_);
        private final SoundEvent f_94902_;
        private static final /* synthetic */ Visibility[] $VALUES;

        public static Visibility[] values() {
            return (Visibility[])$VALUES.clone();
        }

        public static Visibility valueOf(String p_94912_) {
            return Enum.valueOf(Visibility.class, p_94912_);
        }

        private Visibility(SoundEvent p_94908_) {
            this.f_94902_ = p_94908_;
        }

        public void m_94909_(SoundManager p_94910_) {
            p_94910_.m_120367_(SimpleSoundInstance.m_119755_(this.f_94902_, 1.0f, 1.0f));
        }

        private static /* synthetic */ Visibility[] m_169080_() {
            return new Visibility[]{SHOW, HIDE};
        }

        static {
            $VALUES = Visibility.m_169080_();
        }
    }
}

