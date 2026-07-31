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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraft.gametest.framework.TestFunction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class TestFunctionArgument
implements ArgumentType<TestFunction> {
    private static final Collection<String> f_128085_ = Arrays.asList("techtests.piston", "techtests");

    public TestFunction parse(StringReader p_128090_) throws CommandSyntaxException {
        String $$1 = p_128090_.readUnquotedString();
        Optional<TestFunction> $$2 = GameTestRegistry.m_127679_($$1);
        if ($$2.isPresent()) {
            return $$2.get();
        }
        MutableComponent $$3 = Component.m_237113_("No such test: " + $$1);
        throw new CommandSyntaxException((CommandExceptionType)new SimpleCommandExceptionType((Message)$$3), (Message)$$3);
    }

    public static TestFunctionArgument m_128088_() {
        return new TestFunctionArgument();
    }

    public static TestFunction m_128091_(CommandContext<CommandSourceStack> p_128092_, String p_128093_) {
        return (TestFunction)p_128092_.getArgument(p_128093_, TestFunction.class);
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_128096_, SuggestionsBuilder p_128097_) {
        Stream<String> $$2 = GameTestRegistry.m_127658_().stream().map(TestFunction::m_128075_);
        return SharedSuggestionProvider.m_82981_($$2, p_128097_);
    }

    public Collection<String> getExamples() {
        return f_128085_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

