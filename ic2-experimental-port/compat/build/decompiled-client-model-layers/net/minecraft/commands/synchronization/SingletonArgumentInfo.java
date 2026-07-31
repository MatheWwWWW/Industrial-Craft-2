/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.mojang.brigadier.arguments.ArgumentType
 */
package net.minecraft.commands.synchronization;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.FriendlyByteBuf;

public class SingletonArgumentInfo<A extends ArgumentType<?>>
implements ArgumentTypeInfo<A, Template> {
    private final Template f_235432_;

    private SingletonArgumentInfo(Function<CommandBuildContext, A> p_235434_) {
        this.f_235432_ = new Template(p_235434_);
    }

    public static <T extends ArgumentType<?>> SingletonArgumentInfo<T> m_235451_(Supplier<T> p_235452_) {
        return new SingletonArgumentInfo<ArgumentType>(p_235455_ -> (ArgumentType)p_235452_.get());
    }

    public static <T extends ArgumentType<?>> SingletonArgumentInfo<T> m_235449_(Function<CommandBuildContext, T> p_235450_) {
        return new SingletonArgumentInfo<T>(p_235450_);
    }

    @Override
    public void m_214155_(Template p_235447_, FriendlyByteBuf p_235448_) {
    }

    @Override
    public void m_213719_(Template p_235444_, JsonObject p_235445_) {
    }

    @Override
    public Template m_213618_(FriendlyByteBuf p_235457_) {
        return this.f_235432_;
    }

    @Override
    public Template m_214163_(A p_235459_) {
        return this.f_235432_;
    }

    @Override
    public /* synthetic */ ArgumentTypeInfo.Template m_214163_(ArgumentType argumentType) {
        return this.m_214163_(argumentType);
    }

    @Override
    public /* synthetic */ ArgumentTypeInfo.Template m_213618_(FriendlyByteBuf friendlyByteBuf) {
        return this.m_213618_(friendlyByteBuf);
    }

    public final class Template
    implements ArgumentTypeInfo.Template<A> {
        private final Function<CommandBuildContext, A> f_235463_;

        public Template(Function<CommandBuildContext, A> p_235466_) {
            this.f_235463_ = p_235466_;
        }

        @Override
        public A m_213879_(CommandBuildContext p_235469_) {
            return (ArgumentType)this.f_235463_.apply(p_235469_);
        }

        @Override
        public ArgumentTypeInfo<A, ?> m_213709_() {
            return SingletonArgumentInfo.this;
        }
    }
}

