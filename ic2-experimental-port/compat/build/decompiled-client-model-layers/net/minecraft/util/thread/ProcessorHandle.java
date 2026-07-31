/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Either
 */
package net.minecraft.util.thread;

import com.mojang.datafixers.util.Either;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public interface ProcessorHandle<Msg>
extends AutoCloseable {
    public String m_7326_();

    public void m_6937_(Msg var1);

    @Override
    default public void close() {
    }

    default public <Source> CompletableFuture<Source> m_18720_(Function<? super ProcessorHandle<Source>, ? extends Msg> p_18721_) {
        CompletableFuture $$1 = new CompletableFuture();
        Msg $$2 = p_18721_.apply(ProcessorHandle.m_18714_("ask future procesor handle", $$1::complete));
        this.m_6937_($$2);
        return $$1;
    }

    default public <Source> CompletableFuture<Source> m_18722_(Function<? super ProcessorHandle<Either<Source, Exception>>, ? extends Msg> p_18723_) {
        CompletableFuture $$1 = new CompletableFuture();
        Msg $$2 = p_18723_.apply(ProcessorHandle.m_18714_("ask future procesor handle", p_18719_ -> {
            p_18719_.ifLeft($$1::complete);
            p_18719_.ifRight($$1::completeExceptionally);
        }));
        this.m_6937_($$2);
        return $$1;
    }

    public static <Msg> ProcessorHandle<Msg> m_18714_(final String p_18715_, final Consumer<Msg> p_18716_) {
        return new ProcessorHandle<Msg>(){

            @Override
            public String m_7326_() {
                return p_18715_;
            }

            @Override
            public void m_6937_(Msg p_18731_) {
                p_18716_.accept(p_18731_);
            }

            public String toString() {
                return p_18715_;
            }
        };
    }
}

