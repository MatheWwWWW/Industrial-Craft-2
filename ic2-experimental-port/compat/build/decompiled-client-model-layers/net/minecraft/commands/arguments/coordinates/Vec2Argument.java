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
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class Vec2Argument
implements ArgumentType<Coordinates> {
    private static final Collection<String> f_120817_ = Arrays.asList("0 0", "~ ~", "0.1 -0.5", "~1 ~-2");
    public static final SimpleCommandExceptionType f_120816_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.pos2d.incomplete"));
    private final boolean f_120818_;

    public Vec2Argument(boolean p_120821_) {
        this.f_120818_ = p_120821_;
    }

    public static Vec2Argument m_120822_() {
        return new Vec2Argument(true);
    }

    public static Vec2Argument m_174954_(boolean p_174955_) {
        return new Vec2Argument(p_174955_);
    }

    public static Vec2 m_120825_(CommandContext<CommandSourceStack> p_120826_, String p_120827_) {
        Vec3 $$2 = ((Coordinates)p_120826_.getArgument(p_120827_, Coordinates.class)).m_6955_((CommandSourceStack)p_120826_.getSource());
        return new Vec2((float)$$2.f_82479_, (float)$$2.f_82481_);
    }

    public Coordinates parse(StringReader p_120824_) throws CommandSyntaxException {
        int $$1 = p_120824_.getCursor();
        if (!p_120824_.canRead()) {
            throw f_120816_.createWithContext((ImmutableStringReader)p_120824_);
        }
        WorldCoordinate $$2 = WorldCoordinate.m_120871_(p_120824_, this.f_120818_);
        if (!p_120824_.canRead() || p_120824_.peek() != ' ') {
            p_120824_.setCursor($$1);
            throw f_120816_.createWithContext((ImmutableStringReader)p_120824_);
        }
        p_120824_.skip();
        WorldCoordinate $$3 = WorldCoordinate.m_120871_(p_120824_, this.f_120818_);
        return new WorldCoordinates($$2, new WorldCoordinate(true, 0.0), $$3);
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_120830_, SuggestionsBuilder p_120831_) {
        if (p_120830_.getSource() instanceof SharedSuggestionProvider) {
            Collection<SharedSuggestionProvider.TextCoordinates> $$4;
            String $$2 = p_120831_.getRemaining();
            if (!$$2.isEmpty() && $$2.charAt(0) == '^') {
                Set<SharedSuggestionProvider.TextCoordinates> $$3 = Collections.singleton(SharedSuggestionProvider.TextCoordinates.f_82987_);
            } else {
                $$4 = ((SharedSuggestionProvider)p_120830_.getSource()).m_6284_();
            }
            return SharedSuggestionProvider.m_82976_($$2, $$4, p_120831_, Commands.m_82120_(this::parse));
        }
        return Suggestions.empty();
    }

    public Collection<String> getExamples() {
        return f_120817_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

