/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.util;

import com.mojang.logging.LogUtils;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import org.slf4j.Logger;

@FunctionalInterface
public interface TaskChainer {
    public static final Logger f_241683_ = LogUtils.getLogger();
    public static final TaskChainer f_241608_ = p_242298_ -> ((CompletableFuture)p_242298_.get()).exceptionally(p_242314_ -> {
        f_241683_.error("Task failed", p_242314_);
        return null;
    });

    public void m_241849_(DelayedTask var1);

    public static interface DelayedTask
    extends Supplier<CompletableFuture<?>> {
    }
}

