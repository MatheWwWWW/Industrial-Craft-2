/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.chat.contents;

import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;

public class KeybindResolver {
    static Function<String, Supplier<Component>> f_237359_ = p_237363_ -> () -> Component.m_237113_(p_237363_);

    public static void m_237364_(Function<String, Supplier<Component>> p_237365_) {
        f_237359_ = p_237365_;
    }
}

