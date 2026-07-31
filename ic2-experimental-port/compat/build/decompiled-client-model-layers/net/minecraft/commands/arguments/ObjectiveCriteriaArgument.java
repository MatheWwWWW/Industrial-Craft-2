/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 */
package net.minecraft.commands.arguments;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class ObjectiveCriteriaArgument
implements ArgumentType<ObjectiveCriteria> {
    private static final Collection<String> f_102552_ = Arrays.asList("foo", "foo.bar.baz", "minecraft:foo");
    public static final DynamicCommandExceptionType f_102551_ = new DynamicCommandExceptionType(p_102569_ -> Component.m_237110_("argument.criteria.invalid", p_102569_));

    private ObjectiveCriteriaArgument() {
    }

    public static ObjectiveCriteriaArgument m_102555_() {
        return new ObjectiveCriteriaArgument();
    }

    public static ObjectiveCriteria m_102565_(CommandContext<CommandSourceStack> p_102566_, String p_102567_) {
        return (ObjectiveCriteria)p_102566_.getArgument(p_102567_, ObjectiveCriteria.class);
    }

    public ObjectiveCriteria parse(StringReader p_102560_) throws CommandSyntaxException {
        int $$1 = p_102560_.getCursor();
        while (p_102560_.canRead() && p_102560_.peek() != ' ') {
            p_102560_.skip();
        }
        String $$2 = p_102560_.getString().substring($$1, p_102560_.getCursor());
        return ObjectiveCriteria.m_83614_($$2).orElseThrow(() -> {
            p_102560_.setCursor($$1);
            return f_102551_.create((Object)$$2);
        });
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_102572_, SuggestionsBuilder p_102573_) {
        ArrayList $$2 = Lists.newArrayList(ObjectiveCriteria.m_166115_());
        for (StatType statType : Registry.f_122867_) {
            for (Object $$4 : statType.m_12893_()) {
                String $$5 = this.m_102556_(statType, $$4);
                $$2.add($$5);
            }
        }
        return SharedSuggestionProvider.m_82970_($$2, p_102573_);
    }

    public <T> String m_102556_(StatType<T> p_102557_, Object p_102558_) {
        return Stat.m_12862_(p_102557_, p_102558_);
    }

    public Collection<String> getExamples() {
        return f_102552_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

