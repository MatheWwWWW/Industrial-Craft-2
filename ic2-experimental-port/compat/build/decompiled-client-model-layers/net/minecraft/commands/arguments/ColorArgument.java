/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 */
package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;

public class ColorArgument
implements ArgumentType<ChatFormatting> {
    private static final Collection<String> f_85460_ = Arrays.asList("red", "green");
    public static final DynamicCommandExceptionType f_85459_ = new DynamicCommandExceptionType(p_85470_ -> Component.m_237110_("argument.color.invalid", p_85470_));

    private ColorArgument() {
    }

    public static ColorArgument m_85463_() {
        return new ColorArgument();
    }

    public static ChatFormatting m_85466_(CommandContext<CommandSourceStack> p_85467_, String p_85468_) {
        return (ChatFormatting)p_85467_.getArgument(p_85468_, ChatFormatting.class);
    }

    public ChatFormatting parse(StringReader p_85465_) throws CommandSyntaxException {
        String $$1 = p_85465_.readUnquotedString();
        ChatFormatting $$2 = ChatFormatting.m_126657_($$1);
        if ($$2 == null || $$2.m_126661_()) {
            throw f_85459_.create((Object)$$1);
        }
        return $$2;
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_85473_, SuggestionsBuilder p_85474_) {
        return SharedSuggestionProvider.m_82970_(ChatFormatting.m_126653_(true, false), p_85474_);
    }

    public Collection<String> getExamples() {
        return f_85460_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

