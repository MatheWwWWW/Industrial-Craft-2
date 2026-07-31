/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandExceptionType
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 */
package net.minecraft.gametest.framework;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class TestClassNameArgument
implements ArgumentType<String> {
    private static final Collection<String> f_127914_ = Arrays.asList("techtests", "mobtests");

    public String parse(StringReader p_127919_) throws CommandSyntaxException {
        String $$1 = p_127919_.readUnquotedString();
        if (GameTestRegistry.m_127670_($$1)) {
            return $$1;
        }
        MutableComponent $$2 = Component.m_237113_("No such test class: " + $$1);
        throw new CommandSyntaxException((CommandExceptionType)new SimpleCommandExceptionType((Message)$$2), (Message)$$2);
    }

    public static TestClassNameArgument m_127917_() {
        return new TestClassNameArgument();
    }

    public static String m_127920_(CommandContext<CommandSourceStack> p_127921_, String p_127922_) {
        return (String)p_127921_.getArgument(p_127922_, String.class);
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_127925_, SuggestionsBuilder p_127926_) {
        return SharedSuggestionProvider.m_82981_(GameTestRegistry.m_127669_().stream(), p_127926_);
    }

    public Collection<String> getExamples() {
        return f_127914_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

