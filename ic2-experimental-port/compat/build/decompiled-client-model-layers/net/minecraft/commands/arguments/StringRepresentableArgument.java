/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonPrimitive
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 */
package net.minecraft.commands.arguments;

import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

public class StringRepresentableArgument<T extends Enum<T>>
implements ArgumentType<T> {
    private static final DynamicCommandExceptionType f_234055_ = new DynamicCommandExceptionType(p_234071_ -> Component.m_237110_("argument.enum.invalid", p_234071_));
    private final Codec<T> f_234056_;
    private final Supplier<T[]> f_234057_;

    protected StringRepresentableArgument(Codec<T> p_234060_, Supplier<T[]> p_234061_) {
        this.f_234056_ = p_234060_;
        this.f_234057_ = p_234061_;
    }

    public T parse(StringReader p_234063_) throws CommandSyntaxException {
        String $$1 = p_234063_.readUnquotedString();
        return (T)((Enum)this.f_234056_.parse((DynamicOps)JsonOps.INSTANCE, (Object)new JsonPrimitive($$1)).result().orElseThrow(() -> f_234055_.create((Object)$$1)));
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_234074_, SuggestionsBuilder p_234075_) {
        return SharedSuggestionProvider.m_82970_(Arrays.stream((Enum[])this.f_234057_.get()).map(p_234069_ -> ((StringRepresentable)p_234069_).m_7912_()).collect(Collectors.toList()), p_234075_);
    }

    public Collection<String> getExamples() {
        return Arrays.stream((Enum[])this.f_234057_.get()).map(p_234065_ -> ((StringRepresentable)p_234065_).m_7912_()).limit(2L).collect(Collectors.toList());
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

