/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.components.toasts;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class TutorialToast
implements Toast {
    public static final int f_169083_ = 154;
    public static final int f_169084_ = 1;
    public static final int f_169085_ = 3;
    public static final int f_169086_ = 28;
    private final Icons f_94949_;
    private final Component f_94950_;
    @Nullable
    private final Component f_94951_;
    private Toast.Visibility f_94952_ = Toast.Visibility.SHOW;
    private long f_94953_;
    private float f_94954_;
    private float f_94955_;
    private final boolean f_94956_;

    public TutorialToast(Icons p_94958_, Component p_94959_, @Nullable Component p_94960_, boolean p_94961_) {
        this.f_94949_ = p_94958_;
        this.f_94950_ = p_94959_;
        this.f_94951_ = p_94960_;
        this.f_94956_ = p_94961_;
    }

    @Override
    public Toast.Visibility m_7172_(PoseStack p_94965_, ToastComponent p_94966_, long p_94967_) {
        RenderSystem.m_157456_(0, f_94893_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        p_94966_.m_93228_(p_94965_, 0, 0, 0, 96, this.m_7828_(), this.m_94899_());
        this.f_94949_.m_94984_(p_94965_, p_94966_, 6, 6);
        if (this.f_94951_ == null) {
            p_94966_.m_94929_().f_91062_.m_92889_(p_94965_, this.f_94950_, 30.0f, 12.0f, -11534256);
        } else {
            p_94966_.m_94929_().f_91062_.m_92889_(p_94965_, this.f_94950_, 30.0f, 7.0f, -11534256);
            p_94966_.m_94929_().f_91062_.m_92889_(p_94965_, this.f_94951_, 30.0f, 18.0f, -16777216);
        }
        if (this.f_94956_) {
            int $$5;
            GuiComponent.m_93172_(p_94965_, 3, 28, 157, 29, -1);
            float $$3 = Mth.m_144920_(this.f_94954_, this.f_94955_, (float)(p_94967_ - this.f_94953_) / 100.0f);
            if (this.f_94955_ >= this.f_94954_) {
                int $$4 = -16755456;
            } else {
                $$5 = -11206656;
            }
            GuiComponent.m_93172_(p_94965_, 3, 28, (int)(3.0f + 154.0f * $$3), 29, $$5);
            this.f_94954_ = $$3;
            this.f_94953_ = p_94967_;
        }
        return this.f_94952_;
    }

    public void m_94968_() {
        this.f_94952_ = Toast.Visibility.HIDE;
    }

    public void m_94962_(float p_94963_) {
        this.f_94955_ = p_94963_;
    }

    public static final class Icons
    extends Enum<Icons> {
        public static final /* enum */ Icons MOVEMENT_KEYS = new Icons(0, 0);
        public static final /* enum */ Icons MOUSE = new Icons(1, 0);
        public static final /* enum */ Icons TREE = new Icons(2, 0);
        public static final /* enum */ Icons RECIPE_BOOK = new Icons(0, 1);
        public static final /* enum */ Icons WOODEN_PLANKS = new Icons(1, 1);
        public static final /* enum */ Icons SOCIAL_INTERACTIONS = new Icons(2, 1);
        public static final /* enum */ Icons RIGHT_CLICK = new Icons(3, 1);
        private final int f_94975_;
        private final int f_94976_;
        private static final /* synthetic */ Icons[] $VALUES;

        public static Icons[] values() {
            return (Icons[])$VALUES.clone();
        }

        public static Icons valueOf(String p_94990_) {
            return Enum.valueOf(Icons.class, p_94990_);
        }

        private Icons(int p_94982_, int p_94983_) {
            this.f_94975_ = p_94982_;
            this.f_94976_ = p_94983_;
        }

        public void m_94984_(PoseStack p_94985_, GuiComponent p_94986_, int p_94987_, int p_94988_) {
            RenderSystem.m_69478_();
            p_94986_.m_93228_(p_94985_, p_94987_, p_94988_, 176 + this.f_94975_ * 20, this.f_94976_ * 20, 20, 20);
            RenderSystem.m_69478_();
        }

        private static /* synthetic */ Icons[] m_169088_() {
            return new Icons[]{MOVEMENT_KEYS, MOUSE, TREE, RECIPE_BOOK, WOODEN_PLANKS, SOCIAL_INTERACTIONS, RIGHT_CLICK};
        }

        static {
            $VALUES = Icons.m_169088_();
        }
    }
}

