/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.ParseResults
 *  com.mojang.brigadier.context.CommandContextBuilder
 *  com.mojang.brigadier.context.ParsedArgument
 *  com.mojang.brigadier.context.ParsedCommandNode
 *  com.mojang.brigadier.tree.ArgumentCommandNode
 *  com.mojang.brigadier.tree.CommandNode
 */
package net.minecraft.network.chat;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.commands.arguments.PreviewedArgument;

public record PreviewableCommand<S>(List<Argument<S>> f_242495_) {
    public static <S> PreviewableCommand<S> m_242644_(ParseResults<S> p_242945_) {
        CommandContextBuilder $$4;
        CommandContextBuilder $$1;
        CommandContextBuilder $$2 = $$1 = p_242945_.getContext();
        List<Argument<S>> $$3 = PreviewableCommand.m_242602_($$2);
        while (($$4 = $$2.getChild()) != null) {
            boolean $$5;
            boolean bl = $$5 = $$4.getRootNode() != $$1.getRootNode();
            if (!$$5) break;
            $$3.addAll(PreviewableCommand.m_242602_($$4));
            $$2 = $$4;
        }
        return new PreviewableCommand<S>($$3);
    }

    private static <S> List<Argument<S>> m_242602_(CommandContextBuilder<S> p_242893_) {
        ArrayList<Argument<S>> $$1 = new ArrayList<Argument<S>>();
        for (ParsedCommandNode $$2 : p_242893_.getNodes()) {
            ArgumentCommandNode $$3;
            CommandNode commandNode = $$2.getNode();
            if (!(commandNode instanceof ArgumentCommandNode) || !((commandNode = ($$3 = (ArgumentCommandNode)commandNode).getType()) instanceof PreviewedArgument)) continue;
            PreviewedArgument $$4 = (PreviewedArgument)commandNode;
            ParsedArgument $$5 = (ParsedArgument)p_242893_.getArguments().get($$3.getName());
            if ($$5 == null) continue;
            $$1.add(new Argument($$3, $$5, $$4));
        }
        return $$1;
    }

    public boolean m_242639_(CommandNode<?> p_242917_) {
        for (Argument<S> $$1 : this.f_242495_) {
            if ($$1.f_242492_() != p_242917_) continue;
            return true;
        }
        return false;
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{PreviewableCommand.class, "arguments", "f_242495_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{PreviewableCommand.class, "arguments", "f_242495_"}, this);
    }

    @Override
    public final boolean equals(Object p_242918_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{PreviewableCommand.class, "arguments", "f_242495_"}, this, p_242918_);
    }

    public record Argument<S>(ArgumentCommandNode<S, ?> f_242492_, ParsedArgument<S, ?> f_242493_, PreviewedArgument<?> f_242481_) {
        public String m_242628_() {
            return this.f_242492_.getName();
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Argument.class, "node;parsedValue;previewType", "f_242492_", "f_242493_", "f_242481_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Argument.class, "node;parsedValue;previewType", "f_242492_", "f_242493_", "f_242481_"}, this);
        }

        @Override
        public final boolean equals(Object p_242868_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Argument.class, "node;parsedValue;previewType", "f_242492_", "f_242493_", "f_242481_"}, this, p_242868_);
        }
    }
}

