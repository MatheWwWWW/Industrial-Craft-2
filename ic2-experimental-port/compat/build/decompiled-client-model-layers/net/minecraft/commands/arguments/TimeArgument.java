/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 */
package net.minecraft.commands.arguments;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;

public class TimeArgument
implements ArgumentType<Integer> {
    private static final Collection<String> f_113031_ = Arrays.asList("0d", "0s", "0t", "0");
    private static final SimpleCommandExceptionType f_113032_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.time.invalid_unit"));
    private static final DynamicCommandExceptionType f_113033_ = new DynamicCommandExceptionType(p_113041_ -> Component.m_237110_("argument.time.invalid_tick_count", p_113041_));
    private static final Object2IntMap<String> f_113034_ = new Object2IntOpenHashMap();

    public static TimeArgument m_113037_() {
        return new TimeArgument();
    }

    public Integer parse(StringReader p_113039_) throws CommandSyntaxException {
        float $$1 = p_113039_.readFloat();
        String $$2 = p_113039_.readUnquotedString();
        int $$3 = f_113034_.getOrDefault((Object)$$2, 0);
        if ($$3 == 0) {
            throw f_113032_.create();
        }
        int $$4 = Math.round($$1 * (float)$$3);
        if ($$4 < 0) {
            throw f_113033_.create((Object)$$4);
        }
        return $$4;
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_113044_, SuggestionsBuilder p_113045_) {
        StringReader $$2 = new StringReader(p_113045_.getRemaining());
        try {
            $$2.readFloat();
        }
        catch (CommandSyntaxException $$3) {
            return p_113045_.buildFuture();
        }
        return SharedSuggestionProvider.m_82970_((Iterable<String>)f_113034_.keySet(), p_113045_.createOffset(p_113045_.getStart() + $$2.getCursor()));
    }

    public Collection<String> getExamples() {
        return f_113031_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    static {
        f_113034_.put((Object)"d", 24000);
        f_113034_.put((Object)"s", 20);
        f_113034_.put((Object)"t", 1);
        f_113034_.put((Object)"", 1);
    }
}

