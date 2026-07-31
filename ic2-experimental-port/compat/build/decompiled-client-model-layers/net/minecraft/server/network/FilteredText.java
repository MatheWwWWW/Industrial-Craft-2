/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.server.network;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.network.chat.FilterMask;

public record FilteredText(String f_215168_, FilterMask f_243010_) {
    public static final FilteredText f_243020_ = FilteredText.m_243054_("");

    public static FilteredText m_243054_(String p_243257_) {
        return new FilteredText(p_243257_, FilterMask.f_242999_);
    }

    public static FilteredText m_243131_(String p_243261_) {
        return new FilteredText(p_243261_, FilterMask.f_243007_);
    }

    @Nullable
    public String m_243090_() {
        return this.f_243010_.m_243114_(this.f_215168_);
    }

    public String m_243113_() {
        return Objects.requireNonNullElse(this.m_243090_(), "");
    }

    public boolean m_215174_() {
        return !this.f_243010_.m_243095_();
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{FilteredText.class, "raw;mask", "f_215168_", "f_243010_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{FilteredText.class, "raw;mask", "f_215168_", "f_243010_"}, this);
    }

    @Override
    public final boolean equals(Object p_215193_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{FilteredText.class, "raw;mask", "f_215168_", "f_243010_"}, this, p_215193_);
    }
}

