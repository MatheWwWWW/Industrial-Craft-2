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
import net.minecraft.world.item.enchantment.Enchantment;

public class ItemEnchantmentArgument
implements ArgumentType<Enchantment> {
    private static final Collection<String> f_95257_ = Arrays.asList("unbreaking", "silk_touch");
    public static final DynamicCommandExceptionType f_95256_ = new DynamicCommandExceptionType(p_95267_ -> Component.m_237110_("enchantment.unknown", p_95267_));

    public static ItemEnchantmentArgument m_95260_() {
        return new ItemEnchantmentArgument();
    }

    public static Enchantment m_95263_(CommandContext<CommandSourceStack> p_95264_, String p_95265_) {
        return (Enchantment)p_95264_.getArgument(p_95265_, Enchantment.class);
    }

    public Enchantment parse(StringReader p_95262_) throws CommandSyntaxException {
        ResourceLocation $$1 = ResourceLocation.m_135818_(p_95262_);
        return Registry.f_122825_.m_6612_($$1).orElseThrow(() -> f_95256_.create((Object)$$1));
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_95272_, SuggestionsBuilder p_95273_) {
        return SharedSuggestionProvider.m_82926_(Registry.f_122825_.m_6566_(), p_95273_);
    }

    public Collection<String> getExamples() {
        return f_95257_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

