/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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
import net.minecraft.commands.arguments.coordinates.LocalCoordinates;
import net.minecraft.commands.arguments.coordinates.WorldCoordinates;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public class BlockPosArgument
implements ArgumentType<Coordinates> {
    private static final Collection<String> f_118236_ = Arrays.asList("0 0 0", "~ ~ ~", "^ ^ ^", "^1 ^ ^-5", "~0.5 ~1 ~-5");
    public static final SimpleCommandExceptionType f_118234_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.pos.unloaded"));
    public static final SimpleCommandExceptionType f_118235_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.pos.outofworld"));
    public static final SimpleCommandExceptionType f_174394_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.pos.outofbounds"));

    public static BlockPosArgument m_118239_() {
        return new BlockPosArgument();
    }

    public static BlockPos m_118242_(CommandContext<CommandSourceStack> p_118243_, String p_118244_) throws CommandSyntaxException {
        BlockPos $$2 = ((Coordinates)p_118243_.getArgument(p_118244_, Coordinates.class)).m_119568_((CommandSourceStack)p_118243_.getSource());
        if (!((CommandSourceStack)p_118243_.getSource()).m_81372_().m_46805_($$2)) {
            throw f_118234_.create();
        }
        if (!((CommandSourceStack)p_118243_.getSource()).m_81372_().m_46739_($$2)) {
            throw f_118235_.create();
        }
        return $$2;
    }

    public static BlockPos m_174395_(CommandContext<CommandSourceStack> p_174396_, String p_174397_) throws CommandSyntaxException {
        BlockPos $$2 = ((Coordinates)p_174396_.getArgument(p_174397_, Coordinates.class)).m_119568_((CommandSourceStack)p_174396_.getSource());
        if (!Level.m_46741_($$2)) {
            throw f_174394_.create();
        }
        return $$2;
    }

    public Coordinates parse(StringReader p_118241_) throws CommandSyntaxException {
        if (p_118241_.canRead() && p_118241_.peek() == '^') {
            return LocalCoordinates.m_119906_(p_118241_);
        }
        return WorldCoordinates.m_120887_(p_118241_);
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_118250_, SuggestionsBuilder p_118251_) {
        if (p_118250_.getSource() instanceof SharedSuggestionProvider) {
            Collection<SharedSuggestionProvider.TextCoordinates> $$4;
            String $$2 = p_118251_.getRemaining();
            if (!$$2.isEmpty() && $$2.charAt(0) == '^') {
                Set<SharedSuggestionProvider.TextCoordinates> $$3 = Collections.singleton(SharedSuggestionProvider.TextCoordinates.f_82987_);
            } else {
                $$4 = ((SharedSuggestionProvider)p_118250_.getSource()).m_6265_();
            }
            return SharedSuggestionProvider.m_82952_($$2, $$4, p_118251_, Commands.m_82120_(this::parse));
        }
        return Suggestions.empty();
    }

    public Collection<String> getExamples() {
        return f_118236_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

