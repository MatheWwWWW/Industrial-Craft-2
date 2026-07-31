/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.chat.contents;

import java.util.Optional;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.KeybindResolver;

public class KeybindContents
implements ComponentContents {
    private final String f_237344_;
    @Nullable
    private Supplier<Component> f_237345_;

    public KeybindContents(String p_237347_) {
        this.f_237344_ = p_237347_;
    }

    private Component m_237354_() {
        if (this.f_237345_ == null) {
            this.f_237345_ = KeybindResolver.f_237359_.apply(this.f_237344_);
        }
        return this.f_237345_.get();
    }

    @Override
    public <T> Optional<T> m_213874_(FormattedText.ContentConsumer<T> p_237350_) {
        return this.m_237354_().m_5651_(p_237350_);
    }

    @Override
    public <T> Optional<T> m_213724_(FormattedText.StyledContentConsumer<T> p_237352_, Style p_237353_) {
        return this.m_237354_().m_7451_(p_237352_, p_237353_);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object p_237356_) {
        if (this == p_237356_) {
            return true;
        }
        if (!(p_237356_ instanceof KeybindContents)) return false;
        KeybindContents $$1 = (KeybindContents)p_237356_;
        if (!this.f_237344_.equals($$1.f_237344_)) return false;
        return true;
    }

    public int hashCode() {
        return this.f_237344_.hashCode();
    }

    public String toString() {
        return "keybind{" + this.f_237344_ + "}";
    }

    public String m_237348_() {
        return this.f_237344_;
    }
}

