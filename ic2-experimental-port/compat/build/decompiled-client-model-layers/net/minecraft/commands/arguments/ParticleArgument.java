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
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ParticleArgument
implements ArgumentType<ParticleOptions> {
    private static final Collection<String> f_103928_ = Arrays.asList("foo", "foo:bar", "particle with options");
    public static final DynamicCommandExceptionType f_103927_ = new DynamicCommandExceptionType(p_103941_ -> Component.m_237110_("particle.notFound", p_103941_));

    public static ParticleArgument m_103931_() {
        return new ParticleArgument();
    }

    public static ParticleOptions m_103937_(CommandContext<CommandSourceStack> p_103938_, String p_103939_) {
        return (ParticleOptions)p_103938_.getArgument(p_103939_, ParticleOptions.class);
    }

    public ParticleOptions parse(StringReader p_103933_) throws CommandSyntaxException {
        return ParticleArgument.m_103944_(p_103933_);
    }

    public Collection<String> getExamples() {
        return f_103928_;
    }

    public static ParticleOptions m_103944_(StringReader p_103945_) throws CommandSyntaxException {
        ResourceLocation $$1 = ResourceLocation.m_135818_(p_103945_);
        ParticleType<?> $$2 = Registry.f_122829_.m_6612_($$1).orElseThrow(() -> f_103927_.create((Object)$$1));
        return ParticleArgument.m_103934_(p_103945_, $$2);
    }

    private static <T extends ParticleOptions> T m_103934_(StringReader p_103935_, ParticleType<T> p_103936_) throws CommandSyntaxException {
        return p_103936_.m_123743_().m_5739_(p_103936_, p_103935_);
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_103948_, SuggestionsBuilder p_103949_) {
        return SharedSuggestionProvider.m_82926_(Registry.f_122829_.m_6566_(), p_103949_);
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

