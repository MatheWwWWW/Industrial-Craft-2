/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Queues
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.components.toasts;

import com.google.common.collect.Queues;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Deque;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.util.Mth;

public class ToastComponent
extends GuiComponent {
    private static final int f_243005_ = 5;
    private static final int f_243024_ = -1;
    final Minecraft f_94914_;
    private final List<ToastInstance<?>> f_94915_ = new ArrayList();
    private final BitSet f_242998_ = new BitSet(5);
    private final Deque<Toast> f_94916_ = Queues.newArrayDeque();

    public ToastComponent(Minecraft p_94918_) {
        this.f_94914_ = p_94918_;
    }

    public void m_94920_(PoseStack p_94921_) {
        if (this.f_94914_.f_91066_.f_92062_) {
            return;
        }
        int $$1 = this.f_94914_.m_91268_().m_85445_();
        this.f_94915_.removeIf(p_243286_ -> {
            if (p_243286_ != null && p_243286_.m_243117_($$1, p_94921_)) {
                this.f_242998_.clear(p_243286_.f_242993_, p_243286_.f_242993_ + p_243286_.f_243000_);
                return true;
            }
            return false;
        });
        if (!this.f_94916_.isEmpty() && this.m_243097_() > 0) {
            this.f_94916_.removeIf(p_243239_ -> {
                int $$1 = p_243239_.m_243110_();
                int $$2 = this.m_243100_($$1);
                if ($$2 != -1) {
                    this.f_94915_.add(new ToastInstance(this, p_243239_, $$2, $$1));
                    this.f_242998_.set($$2, $$2 + $$1);
                    return true;
                }
                return false;
            });
        }
    }

    private int m_243100_(int p_243272_) {
        if (this.m_243097_() >= p_243272_) {
            int $$1 = 0;
            for (int $$2 = 0; $$2 < 5; ++$$2) {
                if (this.f_242998_.get($$2)) {
                    $$1 = 0;
                    continue;
                }
                if (++$$1 != p_243272_) continue;
                return $$2 + 1 - $$1;
            }
        }
        return -1;
    }

    private int m_243097_() {
        return 5 - this.f_242998_.cardinality();
    }

    @Nullable
    public <T extends Toast> T m_94926_(Class<? extends T> p_94927_, Object p_94928_) {
        for (ToastInstance<?> $$2 : this.f_94915_) {
            if ($$2 == null || !p_94927_.isAssignableFrom($$2.m_94942_().getClass()) || !$$2.m_94942_().m_7283_().equals(p_94928_)) continue;
            return (T)$$2.m_94942_();
        }
        for (Toast $$3 : this.f_94916_) {
            if (!p_94927_.isAssignableFrom($$3.getClass()) || !$$3.m_7283_().equals(p_94928_)) continue;
            return (T)$$3;
        }
        return null;
    }

    public void m_94919_() {
        this.f_242998_.clear();
        this.f_94915_.clear();
        this.f_94916_.clear();
    }

    public void m_94922_(Toast p_94923_) {
        this.f_94916_.add(p_94923_);
    }

    public Minecraft m_94929_() {
        return this.f_94914_;
    }

    class ToastInstance<T extends Toast> {
        private static final long f_169082_ = 600L;
        private final T f_94931_;
        final int f_242993_;
        final int f_243000_;
        private long f_94932_ = -1L;
        private long f_94933_ = -1L;
        private Toast.Visibility f_94934_ = Toast.Visibility.SHOW;
        final /* synthetic */ ToastComponent f_94930_;

        /*
         * WARNING - Possible parameter corruption
         */
        ToastInstance(T p_243319_, int p_243300_, int p_243224_) {
            this.f_94930_ = (ToastComponent)n;
            this.f_94931_ = p_243319_;
            this.f_242993_ = p_243300_;
            this.f_243000_ = p_243224_;
        }

        public T m_94942_() {
            return this.f_94931_;
        }

        private float m_94947_(long p_94948_) {
            float $$1 = Mth.m_14036_((float)(p_94948_ - this.f_94932_) / 600.0f, 0.0f, 1.0f);
            $$1 *= $$1;
            if (this.f_94934_ == Toast.Visibility.HIDE) {
                return 1.0f - $$1;
            }
            return $$1;
        }

        public boolean m_243117_(int p_243301_, PoseStack p_243329_) {
            long $$2 = Util.m_137550_();
            if (this.f_94932_ == -1L) {
                this.f_94932_ = $$2;
                this.f_94934_.m_94909_(this.f_94930_.f_94914_.m_91106_());
            }
            if (this.f_94934_ == Toast.Visibility.SHOW && $$2 - this.f_94932_ <= 600L) {
                this.f_94933_ = $$2;
            }
            PoseStack $$3 = RenderSystem.m_157191_();
            $$3.m_85836_();
            $$3.m_85837_((float)p_243301_ - (float)this.f_94931_.m_7828_() * this.m_94947_($$2), this.f_242993_ * 32, 800.0);
            RenderSystem.m_157182_();
            Toast.Visibility $$4 = this.f_94931_.m_7172_(p_243329_, this.f_94930_, $$2 - this.f_94933_);
            $$3.m_85849_();
            RenderSystem.m_157182_();
            if ($$4 != this.f_94934_) {
                this.f_94932_ = $$2 - (long)((int)((1.0f - this.m_94947_($$2)) * 600.0f));
                this.f_94934_ = $$4;
                this.f_94934_.m_94909_(this.f_94930_.f_94914_.m_91106_());
            }
            return this.f_94934_ == Toast.Visibility.HIDE && $$2 - this.f_94932_ > 600L;
        }
    }
}

