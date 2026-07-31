/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.components.toasts;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

public class SystemToast
implements Toast {
    private static final int f_169078_ = 200;
    private static final int f_243015_ = 12;
    private static final int f_243021_ = 10;
    private final SystemToastIds f_94820_;
    private Component f_94821_;
    private List<FormattedCharSequence> f_94822_;
    private long f_94823_;
    private boolean f_94824_;
    private final int f_94825_;

    public SystemToast(SystemToastIds p_94832_, Component p_94833_, @Nullable Component p_94834_) {
        this(p_94832_, p_94833_, (List<FormattedCharSequence>)SystemToast.m_94860_(p_94834_), Math.max(160, 30 + Math.max(Minecraft.m_91087_().f_91062_.m_92852_(p_94833_), p_94834_ == null ? 0 : Minecraft.m_91087_().f_91062_.m_92852_(p_94834_))));
    }

    public static SystemToast m_94847_(Minecraft p_94848_, SystemToastIds p_94849_, Component p_94850_, Component p_94851_) {
        Font $$4 = p_94848_.f_91062_;
        List<FormattedCharSequence> $$5 = $$4.m_92923_(p_94851_, 200);
        int $$6 = Math.max(200, $$5.stream().mapToInt($$4::m_92724_).max().orElse(200));
        return new SystemToast(p_94849_, p_94850_, $$5, $$6 + 30);
    }

    private SystemToast(SystemToastIds p_94827_, Component p_94828_, List<FormattedCharSequence> p_94829_, int p_94830_) {
        this.f_94820_ = p_94827_;
        this.f_94821_ = p_94828_;
        this.f_94822_ = p_94829_;
        this.f_94825_ = p_94830_;
    }

    private static ImmutableList<FormattedCharSequence> m_94860_(@Nullable Component p_94861_) {
        return p_94861_ == null ? ImmutableList.of() : ImmutableList.of((Object)p_94861_.m_7532_());
    }

    @Override
    public int m_7828_() {
        return this.f_94825_;
    }

    @Override
    public int m_94899_() {
        return 20 + this.f_94822_.size() * 12;
    }

    @Override
    public Toast.Visibility m_7172_(PoseStack p_94844_, ToastComponent p_94845_, long p_94846_) {
        if (this.f_94824_) {
            this.f_94823_ = p_94846_;
            this.f_94824_ = false;
        }
        RenderSystem.m_157456_(0, f_94893_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        int $$3 = this.m_7828_();
        if ($$3 == 160 && this.f_94822_.size() <= 1) {
            p_94845_.m_93228_(p_94844_, 0, 0, 0, 64, $$3, this.m_94899_());
        } else {
            int $$4 = this.m_94899_();
            int $$5 = 28;
            int $$6 = Math.min(4, $$4 - 28);
            this.m_94836_(p_94844_, p_94845_, $$3, 0, 0, 28);
            for (int $$7 = 28; $$7 < $$4 - $$6; $$7 += 10) {
                this.m_94836_(p_94844_, p_94845_, $$3, 16, $$7, Math.min(16, $$4 - $$7 - $$6));
            }
            this.m_94836_(p_94844_, p_94845_, $$3, 32 - $$6, $$4 - $$6, $$6);
        }
        if (this.f_94822_ == null) {
            p_94845_.m_94929_().f_91062_.m_92889_(p_94844_, this.f_94821_, 18.0f, 12.0f, -256);
        } else {
            p_94845_.m_94929_().f_91062_.m_92889_(p_94844_, this.f_94821_, 18.0f, 7.0f, -256);
            for (int $$8 = 0; $$8 < this.f_94822_.size(); ++$$8) {
                p_94845_.m_94929_().f_91062_.m_92877_(p_94844_, this.f_94822_.get($$8), 18.0f, 18 + $$8 * 12, -1);
            }
        }
        return p_94846_ - this.f_94823_ < this.f_94820_.f_232547_ ? Toast.Visibility.SHOW : Toast.Visibility.HIDE;
    }

    private void m_94836_(PoseStack p_94837_, ToastComponent p_94838_, int p_94839_, int p_94840_, int p_94841_, int p_94842_) {
        int $$6 = p_94840_ == 0 ? 20 : 5;
        int $$7 = Math.min(60, p_94839_ - $$6);
        p_94838_.m_93228_(p_94837_, 0, p_94841_, 0, 64 + p_94840_, $$6, p_94842_);
        for (int $$8 = $$6; $$8 < p_94839_ - $$7; $$8 += 64) {
            p_94838_.m_93228_(p_94837_, $$8, p_94841_, 32, 64 + p_94840_, Math.min(64, p_94839_ - $$8 - $$7), p_94842_);
        }
        p_94838_.m_93228_(p_94837_, p_94839_ - $$7, p_94841_, 160 - $$7, 64 + p_94840_, $$7, p_94842_);
    }

    public void m_94862_(Component p_94863_, @Nullable Component p_94864_) {
        this.f_94821_ = p_94863_;
        this.f_94822_ = SystemToast.m_94860_(p_94864_);
        this.f_94824_ = true;
    }

    public SystemToastIds m_7283_() {
        return this.f_94820_;
    }

    public static void m_94855_(ToastComponent p_94856_, SystemToastIds p_94857_, Component p_94858_, @Nullable Component p_94859_) {
        p_94856_.m_94922_(new SystemToast(p_94857_, p_94858_, p_94859_));
    }

    public static void m_94869_(ToastComponent p_94870_, SystemToastIds p_94871_, Component p_94872_, @Nullable Component p_94873_) {
        SystemToast $$4 = p_94870_.m_94926_(SystemToast.class, (Object)p_94871_);
        if ($$4 == null) {
            SystemToast.m_94855_(p_94870_, p_94871_, p_94872_, p_94873_);
        } else {
            $$4.m_94862_(p_94872_, p_94873_);
        }
    }

    public static void m_94852_(Minecraft p_94853_, String p_94854_) {
        SystemToast.m_94855_(p_94853_.m_91300_(), SystemToastIds.WORLD_ACCESS_FAILURE, Component.m_237115_("selectWorld.access_failure"), Component.m_237113_(p_94854_));
    }

    public static void m_94866_(Minecraft p_94867_, String p_94868_) {
        SystemToast.m_94855_(p_94867_.m_91300_(), SystemToastIds.WORLD_ACCESS_FAILURE, Component.m_237115_("selectWorld.delete_failure"), Component.m_237113_(p_94868_));
    }

    public static void m_94875_(Minecraft p_94876_, String p_94877_) {
        SystemToast.m_94855_(p_94876_.m_91300_(), SystemToastIds.PACK_COPY_FAILURE, Component.m_237115_("pack.copyFailure"), Component.m_237113_(p_94877_));
    }

    @Override
    public /* synthetic */ Object m_7283_() {
        return this.m_7283_();
    }

    public static final class SystemToastIds
    extends Enum<SystemToastIds> {
        public static final /* enum */ SystemToastIds TUTORIAL_HINT = new SystemToastIds();
        public static final /* enum */ SystemToastIds NARRATOR_TOGGLE = new SystemToastIds();
        public static final /* enum */ SystemToastIds WORLD_BACKUP = new SystemToastIds();
        public static final /* enum */ SystemToastIds WORLD_GEN_SETTINGS_TRANSFER = new SystemToastIds();
        public static final /* enum */ SystemToastIds PACK_LOAD_FAILURE = new SystemToastIds();
        public static final /* enum */ SystemToastIds WORLD_ACCESS_FAILURE = new SystemToastIds();
        public static final /* enum */ SystemToastIds PACK_COPY_FAILURE = new SystemToastIds();
        public static final /* enum */ SystemToastIds PERIODIC_NOTIFICATION = new SystemToastIds();
        public static final /* enum */ SystemToastIds CHAT_PREVIEW_WARNING = new SystemToastIds(10000L);
        public static final /* enum */ SystemToastIds UNSECURE_SERVER_WARNING = new SystemToastIds(10000L);
        final long f_232547_;
        private static final /* synthetic */ SystemToastIds[] $VALUES;

        public static SystemToastIds[] values() {
            return (SystemToastIds[])$VALUES.clone();
        }

        public static SystemToastIds valueOf(String p_94891_) {
            return Enum.valueOf(SystemToastIds.class, p_94891_);
        }

        private SystemToastIds(long p_232551_) {
            this.f_232547_ = p_232551_;
        }

        private SystemToastIds() {
            this(5000L);
        }

        private static /* synthetic */ SystemToastIds[] m_169079_() {
            return new SystemToastIds[]{TUTORIAL_HINT, NARRATOR_TOGGLE, WORLD_BACKUP, WORLD_GEN_SETTINGS_TRANSFER, PACK_LOAD_FAILURE, WORLD_ACCESS_FAILURE, PACK_COPY_FAILURE, PERIODIC_NOTIFICATION, CHAT_PREVIEW_WARNING, UNSECURE_SERVER_WARNING};
        }

        static {
            $VALUES = SystemToastIds.m_169079_();
        }
    }
}

