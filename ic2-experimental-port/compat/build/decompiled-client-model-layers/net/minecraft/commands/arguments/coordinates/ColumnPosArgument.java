/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.ImmutableStringReader
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 */
package net.minecraft.commands.arguments.coordinates;

import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.commands.arguments.coordinates.WorldCoordinate;
import net.minecraft.commands.arguments.coordinates.WorldCoordinates;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ColumnPos;

public class ColumnPosArgument
implements ArgumentType<Coordinates> {
    private static final Collection<String> f_118986_ = Arrays.asList("0 0", "~ ~", "~1 ~-2", "^ ^", "^-1 ^0");
    public static final SimpleCommandExceptionType f_118985_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.pos2d.incomplete"));

    public static ColumnPosArgument m_118989_() {
        return new ColumnPosArgument();
    }

    public static ColumnPos m_118992_(CommandContext<CommandSourceStack> p_118993_, String p_118994_) {
        BlockPos $$2 = ((Coordinates)p_118993_.getArgument(p_118994_, Coordinates.class)).m_119568_((CommandSourceStack)p_118993_.getSource());
        return new ColumnPos($$2.m_123341_(), $$2.m_123343_());
    }

    public Coordinates parse(StringReader p_118991_) throws CommandSyntaxException {
        int $$1 = p_118991_.getCursor();
        if (!p_118991_.canRead()) {
            throw f_118985_.createWithContext((ImmutableStringReader)p_118991_);
        }
        WorldCoordinate $$2 = WorldCoordinate.m_120869_(p_118991_);
        if (!p_118991_.canRead() || p_118991_.peek() != ' ') {
            p_118991_.setCursor($$1);
            throw f_118985_.createWithContext((ImmutableStringReader)p_118991_);
        }
        p_118991_.skip();
        WorldCoordinate $$3 = WorldCoordinate.m_120869_(p_118991_);
        return new WorldCoordinates($$2, new WorldCoordinate(true, 0.0), $$3);
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_118997_, SuggestionsBuilder p_118998_) {
        if (p_118997_.getSource() instanceof SharedSuggestionProvider) {
            Collection<SharedSuggestionProvider.TextCoordinates> $$4;
            String $$2 = p_118998_.getRemaining();
            if (!$$2.isEmpty() && $$2.charAt(0) == '^') {
                Set<SharedSuggestionProvider.TextCoordinates> $$3 = Collections.singleton(SharedSuggestionProvider.TextCoordinates.f_82987_);
            } else {
                $$4 = ((SharedSuggestionProvider)p_118997_.getSource()).m_6265_();
            }
            return SharedSuggestionProvider.m_82976_($$2, $$4, p_118998_, Commands.m_82120_(this::parse));
        }
        return Suggestions.empty();
    }

    public Collection<String> getExamples() {
        return f_118986_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

