/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.util;

import com.mojang.logging.LogUtils;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import net.minecraft.util.TaskChainer;
import org.slf4j.Logger;

public class FutureChain
implements TaskChainer {
    private static final Logger f_241610_ = LogUtils.getLogger();
    private CompletableFuture<?> f_241623_ = CompletableFuture.completedFuture(null);
    private final Executor f_241655_;

    public FutureChain(Executor p_242395_) {
        this.f_241655_ = p_242395_;
    }

    @Override
    public void m_241849_(TaskChainer.DelayedTask p_242381_) {
        this.f_241623_ = ((CompletableFuture)this.f_241623_.thenComposeAsync(p_242302_ -> (CompletionStage)p_242381_.get(), this.f_241655_)).exceptionally(p_242215_ -> {
            if (p_242215_ instanceof CompletionException) {
                CompletionException $$1 = (CompletionException)p_242215_;
                p_242215_ = $$1.getCause();
            }
            if (p_242215_ instanceof CancellationException) {
                CancellationException $$2 = (CancellationException)p_242215_;
                throw $$2;
            }
            f_241610_.error("Chain link failed, continuing to next one", p_242215_);
            return null;
        });
    }
}

