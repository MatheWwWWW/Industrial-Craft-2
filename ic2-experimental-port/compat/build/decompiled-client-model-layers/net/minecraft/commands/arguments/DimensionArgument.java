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
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class DimensionArgument
implements ArgumentType<ResourceLocation> {
    private static final Collection<String> f_88801_ = Stream.of(Level.f_46428_, Level.f_46429_).map(p_88814_ -> p_88814_.m_135782_().toString()).collect(Collectors.toList());
    private static final DynamicCommandExceptionType f_88802_ = new DynamicCommandExceptionType(p_88812_ -> Component.m_237110_("argument.dimension.invalid", p_88812_));

    public ResourceLocation parse(StringReader p_88807_) throws CommandSyntaxException {
        return ResourceLocation.m_135818_(p_88807_);
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_88817_, SuggestionsBuilder p_88818_) {
        if (p_88817_.getSource() instanceof SharedSuggestionProvider) {
            return SharedSuggestionProvider.m_82957_(((SharedSuggestionProvider)p_88817_.getSource()).m_6553_().stream().map(ResourceKey::m_135782_), p_88818_);
        }
        return Suggestions.empty();
    }

    public Collection<String> getExamples() {
        return f_88801_;
    }

    public static DimensionArgument m_88805_() {
        return new DimensionArgument();
    }

    public static ServerLevel m_88808_(CommandContext<CommandSourceStack> p_88809_, String p_88810_) throws CommandSyntaxException {
        ResourceLocation $$2 = (ResourceLocation)p_88809_.getArgument(p_88810_, ResourceLocation.class);
        ResourceKey<Level> $$3 = ResourceKey.m_135785_(Registry.f_122819_, $$2);
        ServerLevel $$4 = ((CommandSourceStack)p_88809_.getSource()).m_81377_().m_129880_($$3);
        if ($$4 == null) {
            throw f_88802_.create((Object)$$2);
        }
        return $$4;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

