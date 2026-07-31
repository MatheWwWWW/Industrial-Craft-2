/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.context.ParsedArgument
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.commands.arguments;

import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.datafixers.util.Pair;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.commands.arguments.PreviewedArgument;
import net.minecraft.commands.arguments.SignedArgument;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.PreviewableCommand;

public record ArgumentSignatures(List<Entry> f_240908_) {
    public static final ArgumentSignatures f_240907_ = new ArgumentSignatures(List.of());
    private static final int f_231046_ = 8;
    private static final int f_231047_ = 16;

    public ArgumentSignatures(FriendlyByteBuf p_231052_) {
        this(p_231052_.m_236838_(FriendlyByteBuf.m_182695_(ArrayList::new, 8), Entry::new));
    }

    public MessageSignature m_240943_(String p_241493_) {
        for (Entry $$1 : this.f_240908_) {
            if (!$$1.f_240910_.equals(p_241493_)) continue;
            return $$1.f_240879_;
        }
        return MessageSignature.f_240860_;
    }

    public void m_231061_(FriendlyByteBuf p_231062_) {
        p_231062_.m_236828_(this.f_240908_, (p_241214_, p_241215_) -> p_241215_.m_241062_((FriendlyByteBuf)((Object)p_241214_)));
    }

    public static boolean m_242625_(PreviewableCommand<?> p_242912_) {
        return p_242912_.f_242495_().stream().anyMatch(p_242699_ -> p_242699_.f_242481_() instanceof SignedArgument);
    }

    public static ArgumentSignatures m_242588_(PreviewableCommand<?> p_242877_, Signer p_242891_) {
        List<Entry> $$2 = ArgumentSignatures.m_242641_(p_242877_).stream().map(p_242081_ -> {
            MessageSignature $$2 = p_242891_.m_241068_((String)p_242081_.getFirst(), (String)p_242081_.getSecond());
            return new Entry((String)p_242081_.getFirst(), $$2);
        }).toList();
        return new ArgumentSignatures($$2);
    }

    public static List<Pair<String, String>> m_242641_(PreviewableCommand<?> p_242870_) {
        ArrayList<Pair<String, String>> $$1 = new ArrayList<Pair<String, String>>();
        for (PreviewableCommand.Argument<?> $$2 : p_242870_.f_242495_()) {
            PreviewedArgument<?> previewedArgument = $$2.f_242481_();
            if (!(previewedArgument instanceof SignedArgument)) continue;
            SignedArgument $$3 = (SignedArgument)previewedArgument;
            String $$4 = ArgumentSignatures.m_241949_($$3, $$2.f_242493_());
            $$1.add((Pair<String, String>)Pair.of((Object)$$2.m_242628_(), (Object)$$4));
        }
        return $$1;
    }

    private static <T> String m_241949_(SignedArgument<T> p_242374_, ParsedArgument<?, ?> p_242172_) {
        return p_242374_.m_213813_(p_242172_.getResult());
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ArgumentSignatures.class, "entries", "f_240908_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ArgumentSignatures.class, "entries", "f_240908_"}, this);
    }

    @Override
    public final boolean equals(Object p_231071_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ArgumentSignatures.class, "entries", "f_240908_"}, this, p_231071_);
    }

    public record Entry(String f_240910_, MessageSignature f_240879_) {
        public Entry(FriendlyByteBuf p_241305_) {
            this(p_241305_.m_130136_(16), new MessageSignature(p_241305_));
        }

        public void m_241062_(FriendlyByteBuf p_241403_) {
            p_241403_.m_130072_(this.f_240910_, 16);
            this.f_240879_.m_241011_(p_241403_);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Entry.class, "name;signature", "f_240910_", "f_240879_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Entry.class, "name;signature", "f_240910_", "f_240879_"}, this);
        }

        @Override
        public final boolean equals(Object p_241409_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Entry.class, "name;signature", "f_240910_", "f_240879_"}, this, p_241409_);
        }
    }

    @FunctionalInterface
    public static interface Signer {
        public MessageSignature m_241068_(String var1, String var2);
    }
}

