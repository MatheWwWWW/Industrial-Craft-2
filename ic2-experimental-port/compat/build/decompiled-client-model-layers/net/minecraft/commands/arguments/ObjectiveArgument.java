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
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.world.scores.Objective;

public class ObjectiveArgument
implements ArgumentType<String> {
    private static final Collection<String> f_101952_ = Arrays.asList("foo", "*", "012");
    private static final DynamicCommandExceptionType f_101953_ = new DynamicCommandExceptionType(p_101971_ -> Component.m_237110_("arguments.objective.notFound", p_101971_));
    private static final DynamicCommandExceptionType f_101954_ = new DynamicCommandExceptionType(p_101969_ -> Component.m_237110_("arguments.objective.readonly", p_101969_));

    public static ObjectiveArgument m_101957_() {
        return new ObjectiveArgument();
    }

    public static Objective m_101960_(CommandContext<CommandSourceStack> p_101961_, String p_101962_) throws CommandSyntaxException {
        String $$2 = (String)p_101961_.getArgument(p_101962_, String.class);
        ServerScoreboard $$3 = ((CommandSourceStack)p_101961_.getSource()).m_81377_().m_129896_();
        Objective $$4 = $$3.m_83477_($$2);
        if ($$4 == null) {
            throw f_101953_.create((Object)$$2);
        }
        return $$4;
    }

    public static Objective m_101965_(CommandContext<CommandSourceStack> p_101966_, String p_101967_) throws CommandSyntaxException {
        Objective $$2 = ObjectiveArgument.m_101960_(p_101966_, p_101967_);
        if ($$2.m_83321_().m_83621_()) {
            throw f_101954_.create((Object)$$2.m_83320_());
        }
        return $$2;
    }

    public String parse(StringReader p_101959_) throws CommandSyntaxException {
        return p_101959_.readUnquotedString();
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_101974_, SuggestionsBuilder p_101975_) {
        Object $$2 = p_101974_.getSource();
        if ($$2 instanceof CommandSourceStack) {
            CommandSourceStack $$3 = (CommandSourceStack)$$2;
            return SharedSuggestionProvider.m_82970_($$3.m_81377_().m_129896_().m_83474_(), p_101975_);
        }
        if ($$2 instanceof SharedSuggestionProvider) {
            SharedSuggestionProvider $$4 = (SharedSuggestionProvider)$$2;
            return $$4.m_212155_(p_101974_);
        }
        return Suggestions.empty();
    }

    public Collection<String> getExamples() {
        return f_101952_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

