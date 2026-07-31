/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.chat.contents;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;

public record LiteralContents(String f_237368_) implements ComponentContents
{
    @Override
    public <T> Optional<T> m_213874_(FormattedText.ContentConsumer<T> p_237373_) {
        return p_237373_.m_130809_(this.f_237368_);
    }

    @Override
    public <T> Optional<T> m_213724_(FormattedText.StyledContentConsumer<T> p_237375_, Style p_237376_) {
        return p_237375_.m_7164_(p_237376_, this.f_237368_);
    }

    @Override
    public String toString() {
        return "literal{" + this.f_237368_ + "}";
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{LiteralContents.class, "text", "f_237368_"}, this);
    }

    @Override
    public final boolean equals(Object p_237378_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{LiteralContents.class, "text", "f_237368_"}, this, p_237378_);
    }
}

