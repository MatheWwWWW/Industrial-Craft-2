/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class LockIconButton
extends Button {
    private boolean f_94297_;

    public LockIconButton(int p_94299_, int p_94300_, Button.OnPress p_94301_) {
        super(p_94299_, p_94300_, 20, 20, Component.m_237115_("narrator.button.difficulty_lock"), p_94301_);
    }

    @Override
    protected MutableComponent m_5646_() {
        return CommonComponents.m_178398_(super.m_5646_(), this.m_94302_() ? Component.m_237115_("narrator.button.difficulty_lock.locked") : Component.m_237115_("narrator.button.difficulty_lock.unlocked"));
    }

    public boolean m_94302_() {
        return this.f_94297_;
    }

    public void m_94309_(boolean p_94310_) {
        this.f_94297_ = p_94310_;
    }

    @Override
    public void m_6303_(PoseStack p_94304_, int p_94305_, int p_94306_, float p_94307_) {
        Icon $$6;
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157456_(0, Button.f_93617_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        if (!this.f_93623_) {
            Icon $$4 = this.f_94297_ ? Icon.LOCKED_DISABLED : Icon.UNLOCKED_DISABLED;
        } else if (this.m_198029_()) {
            Icon $$5 = this.f_94297_ ? Icon.LOCKED_HOVER : Icon.UNLOCKED_HOVER;
        } else {
            $$6 = this.f_94297_ ? Icon.LOCKED : Icon.UNLOCKED;
        }
        this.m_93228_(p_94304_, this.f_93620_, this.f_93621_, $$6.m_94326_(), $$6.m_94327_(), this.f_93618_, this.f_93619_);
    }

    static final class Icon
    extends Enum<Icon> {
        public static final /* enum */ Icon LOCKED = new Icon(0, 146);
        public static final /* enum */ Icon LOCKED_HOVER = new Icon(0, 166);
        public static final /* enum */ Icon LOCKED_DISABLED = new Icon(0, 186);
        public static final /* enum */ Icon UNLOCKED = new Icon(20, 146);
        public static final /* enum */ Icon UNLOCKED_HOVER = new Icon(20, 166);
        public static final /* enum */ Icon UNLOCKED_DISABLED = new Icon(20, 186);
        private final int f_94317_;
        private final int f_94318_;
        private static final /* synthetic */ Icon[] $VALUES;

        public static Icon[] values() {
            return (Icon[])$VALUES.clone();
        }

        public static Icon valueOf(String p_94329_) {
            return Enum.valueOf(Icon.class, p_94329_);
        }

        private Icon(int p_94324_, int p_94325_) {
            this.f_94317_ = p_94324_;
            this.f_94318_ = p_94325_;
        }

        public int m_94326_() {
            return this.f_94317_;
        }

        public int m_94327_() {
            return this.f_94318_;
        }

        private static /* synthetic */ Icon[] m_169032_() {
            return new Icon[]{LOCKED, LOCKED_HOVER, LOCKED_DISABLED, UNLOCKED, UNLOCKED_HOVER, UNLOCKED_DISABLED};
        }

        static {
            $VALUES = Icon.m_169032_();
        }
    }
}

