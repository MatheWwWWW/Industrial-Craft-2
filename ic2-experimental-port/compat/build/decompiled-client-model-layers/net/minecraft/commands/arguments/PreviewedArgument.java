/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.ParsedArgument
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.commands.arguments;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public interface PreviewedArgument<T>
extends ArgumentType<T> {
    @Nullable
    default public CompletableFuture<Component> m_242586_(CommandSourceStack p_242896_, ParsedArgument<CommandSourceStack, ?> p_242879_) throws CommandSyntaxException {
        if (this.m_213797_().isInstance(p_242879_.getResult())) {
            return this.m_213969_(p_242896_, this.m_213797_().cast(p_242879_.getResult()));
        }
        return null;
    }

    public CompletableFuture<Component> m_213969_(CommandSourceStack var1, T var2) throws CommandSyntaxException;

    public Class<T> m_213797_();
}

