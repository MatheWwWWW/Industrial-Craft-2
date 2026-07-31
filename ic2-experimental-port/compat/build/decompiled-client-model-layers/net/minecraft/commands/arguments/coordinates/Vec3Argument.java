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
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;

public class Vec3Argument
implements ArgumentType<Coordinates> {
    private static final Collection<String> f_120836_ = Arrays.asList("0 0 0", "~ ~ ~", "^ ^ ^", "^1 ^ ^-5", "0.1 -0.5 .9", "~0.5 ~1 ~-5");
    public static final SimpleCommandExceptionType f_120834_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.pos3d.incomplete"));
    public static final SimpleCommandExceptionType f_120835_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.pos.mixed"));
    private final boolean f_120837_;

    public Vec3Argument(boolean p_120840_) {
        this.f_120837_ = p_120840_;
    }

    public static Vec3Argument m_120841_() {
        return new Vec3Argument(true);
    }

    public static Vec3Argument m_120847_(boolean p_120848_) {
        return new Vec3Argument(p_120848_);
    }

    public static Vec3 m_120844_(CommandContext<CommandSourceStack> p_120845_, String p_120846_) {
        return ((Coordinates)p_120845_.getArgument(p_120846_, Coordinates.class)).m_6955_((CommandSourceStack)p_120845_.getSource());
    }

    public static Coordinates m_120849_(CommandContext<CommandSourceStack> p_120850_, String p_120851_) {
        return (Coordinates)p_120850_.getArgument(p_120851_, Coordinates.class);
    }

    public Coordinates parse(StringReader p_120843_) throws CommandSyntaxException {
        if (p_120843_.canRead() && p_120843_.peek() == '^') {
            return LocalCoordinates.m_119906_(p_120843_);
        }
        return WorldCoordinates.m_120889_(p_120843_, this.f_120837_);
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_120854_, SuggestionsBuilder p_120855_) {
        if (p_120854_.getSource() instanceof SharedSuggestionProvider) {
            Collection<SharedSuggestionProvider.TextCoordinates> $$4;
            String $$2 = p_120855_.getRemaining();
            if (!$$2.isEmpty() && $$2.charAt(0) == '^') {
                Set<SharedSuggestionProvider.TextCoordinates> $$3 = Collections.singleton(SharedSuggestionProvider.TextCoordinates.f_82987_);
            } else {
                $$4 = ((SharedSuggestionProvider)p_120854_.getSource()).m_6284_();
            }
            return SharedSuggestionProvider.m_82952_($$2, $$4, p_120855_, Commands.m_82120_(this::parse));
        }
        return Suggestions.empty();
    }

    public Collection<String> getExamples() {
        return f_120836_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

