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
import net.minecraft.world.scores.PlayerTeam;

public class TeamArgument
implements ArgumentType<String> {
    private static final Collection<String> f_112084_ = Arrays.asList("foo", "123");
    private static final DynamicCommandExceptionType f_112085_ = new DynamicCommandExceptionType(p_112095_ -> Component.m_237110_("team.notFound", p_112095_));

    public static TeamArgument m_112088_() {
        return new TeamArgument();
    }

    public static PlayerTeam m_112091_(CommandContext<CommandSourceStack> p_112092_, String p_112093_) throws CommandSyntaxException {
        String $$2 = (String)p_112092_.getArgument(p_112093_, String.class);
        ServerScoreboard $$3 = ((CommandSourceStack)p_112092_.getSource()).m_81377_().m_129896_();
        PlayerTeam $$4 = $$3.m_83489_($$2);
        if ($$4 == null) {
            throw f_112085_.create((Object)$$2);
        }
        return $$4;
    }

    public String parse(StringReader p_112090_) throws CommandSyntaxException {
        return p_112090_.readUnquotedString();
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_112098_, SuggestionsBuilder p_112099_) {
        if (p_112098_.getSource() instanceof SharedSuggestionProvider) {
            return SharedSuggestionProvider.m_82970_(((SharedSuggestionProvider)p_112098_.getSource()).m_5983_(), p_112099_);
        }
        return Suggestions.empty();
    }

    public Collection<String> getExamples() {
        return f_112084_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

