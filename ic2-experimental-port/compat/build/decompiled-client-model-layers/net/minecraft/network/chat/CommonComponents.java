/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.chat;

import java.util.Arrays;
import java.util.Collection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;

public class CommonComponents {
    public static final Component f_237098_ = Component.m_237119_();
    public static final Component f_130653_ = Component.m_237115_("options.on");
    public static final Component f_130654_ = Component.m_237115_("options.off");
    public static final Component f_130655_ = Component.m_237115_("gui.done");
    public static final Component f_130656_ = Component.m_237115_("gui.cancel");
    public static final Component f_130657_ = Component.m_237115_("gui.yes");
    public static final Component f_130658_ = Component.m_237115_("gui.no");
    public static final Component f_130659_ = Component.m_237115_("gui.proceed");
    public static final Component f_130660_ = Component.m_237115_("gui.back");
    public static final Component f_238584_ = Component.m_237115_("gui.acknowledge");
    public static final Component f_130661_ = Component.m_237115_("connect.failed");
    public static final Component f_178388_ = Component.m_237113_("\n");
    public static final Component f_178389_ = Component.m_237113_(". ");
    public static final Component f_238772_ = Component.m_237113_("...");

    public static MutableComponent m_239422_(long p_239423_) {
        return Component.m_237110_("gui.days", p_239423_);
    }

    public static MutableComponent m_240041_(long p_240042_) {
        return Component.m_237110_("gui.hours", p_240042_);
    }

    public static MutableComponent m_239877_(long p_239878_) {
        return Component.m_237110_("gui.minutes", p_239878_);
    }

    public static Component m_130666_(boolean p_130667_) {
        return p_130667_ ? f_130653_ : f_130654_;
    }

    public static MutableComponent m_130663_(Component p_130664_, boolean p_130665_) {
        return Component.m_237110_(p_130665_ ? "options.on.composed" : "options.off.composed", p_130664_);
    }

    public static MutableComponent m_178393_(Component p_178394_, Component p_178395_) {
        return Component.m_237110_("options.generic_value", p_178394_, p_178395_);
    }

    public static MutableComponent m_178398_(Component p_178399_, Component p_178400_) {
        return Component.m_237119_().m_7220_(p_178399_).m_7220_(f_178389_).m_7220_(p_178400_);
    }

    public static Component m_178396_(Component ... p_178397_) {
        return CommonComponents.m_178391_(Arrays.asList(p_178397_));
    }

    public static Component m_178391_(Collection<? extends Component> p_178392_) {
        return ComponentUtils.m_178433_(p_178392_, f_178388_);
    }
}

