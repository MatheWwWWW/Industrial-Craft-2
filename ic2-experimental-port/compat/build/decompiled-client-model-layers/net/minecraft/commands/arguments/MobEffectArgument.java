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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

public class MobEffectArgument
implements ArgumentType<MobEffect> {
    private static final Collection<String> f_98423_ = Arrays.asList("spooky", "effect");
    public static final DynamicCommandExceptionType f_98422_ = new DynamicCommandExceptionType(p_98433_ -> Component.m_237110_("effect.effectNotFound", p_98433_));

    public static MobEffectArgument m_98426_() {
        return new MobEffectArgument();
    }

    public static MobEffect m_98429_(CommandContext<CommandSourceStack> p_98430_, String p_98431_) {
        return (MobEffect)p_98430_.getArgument(p_98431_, MobEffect.class);
    }

    public MobEffect parse(StringReader p_98428_) throws CommandSyntaxException {
        ResourceLocation $$1 = ResourceLocation.m_135818_(p_98428_);
        return Registry.f_122823_.m_6612_($$1).orElseThrow(() -> f_98422_.create((Object)$$1));
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_98438_, SuggestionsBuilder p_98439_) {
        return SharedSuggestionProvider.m_82926_(Registry.f_122823_.m_6566_(), p_98439_);
    }

    public Collection<String> getExamples() {
        return f_98423_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

