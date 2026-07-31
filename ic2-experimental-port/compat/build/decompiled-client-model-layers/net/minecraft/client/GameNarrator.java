/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  com.mojang.text2speech.Narrator
 *  org.slf4j.Logger
 */
package net.minecraft.client;

import com.mojang.logging.LogUtils;
import com.mojang.text2speech.Narrator;
import java.util.function.Supplier;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.NarratorStatus;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

public class GameNarrator {
    public static final Component f_93310_ = CommonComponents.f_237098_;
    private static final Logger f_93311_ = LogUtils.getLogger();
    private final Minecraft f_240371_;
    private final Narrator f_93313_ = Narrator.getNarrator();

    public GameNarrator(Minecraft p_240577_) {
        this.f_240371_ = p_240577_;
    }

    public void m_240462_(Supplier<Component> p_240600_) {
        if (this.m_93330_().m_240504_()) {
            String $$1 = p_240600_.get().getString();
            this.m_168787_($$1);
            this.f_93313_.say($$1, false);
        }
    }

    public void m_168785_(Component p_168786_) {
        this.m_93319_(p_168786_.getString());
    }

    public void m_93319_(String p_93320_) {
        if (this.m_93330_().m_240472_() && !p_93320_.isEmpty()) {
            this.m_168787_(p_93320_);
            if (this.f_93313_.active()) {
                this.f_93313_.clear();
                this.f_93313_.say(p_93320_, true);
            }
        }
    }

    private NarratorStatus m_93330_() {
        return this.f_240371_.f_91066_.m_231930_().m_231551_();
    }

    private void m_168787_(String p_168788_) {
        if (SharedConstants.f_136183_) {
            f_93311_.debug("Narrating: {}", (Object)p_168788_.replaceAll("\n", "\\\\n"));
        }
    }

    public void m_93317_(NarratorStatus p_93318_) {
        this.m_93328_();
        this.f_93313_.say(Component.m_237115_("options.narrator").m_130946_(" : ").m_7220_(p_93318_.m_91621_()).getString(), true);
        ToastComponent $$1 = Minecraft.m_91087_().m_91300_();
        if (this.f_93313_.active()) {
            if (p_93318_ == NarratorStatus.OFF) {
                SystemToast.m_94869_($$1, SystemToast.SystemToastIds.NARRATOR_TOGGLE, Component.m_237115_("narrator.toast.disabled"), null);
            } else {
                SystemToast.m_94869_($$1, SystemToast.SystemToastIds.NARRATOR_TOGGLE, Component.m_237115_("narrator.toast.enabled"), p_93318_.m_91621_());
            }
        } else {
            SystemToast.m_94869_($$1, SystemToast.SystemToastIds.NARRATOR_TOGGLE, Component.m_237115_("narrator.toast.disabled"), Component.m_237115_("options.narrator.notavailable"));
        }
    }

    public boolean m_93316_() {
        return this.f_93313_.active();
    }

    public void m_93328_() {
        if (this.m_93330_() == NarratorStatus.OFF || !this.f_93313_.active()) {
            return;
        }
        this.f_93313_.clear();
    }

    public void m_93329_() {
        this.f_93313_.destroy();
    }
}

