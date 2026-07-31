/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 */
package net.minecraft.commands.arguments.item;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

public class ItemArgument
implements ArgumentType<ItemInput> {
    private static final Collection<String> f_120957_ = Arrays.asList("stick", "minecraft:stick", "stick{foo=bar}");
    private final HolderLookup<Item> f_235276_;

    public ItemArgument(CommandBuildContext p_235278_) {
        this.f_235276_ = p_235278_.m_227133_(Registry.f_122904_);
    }

    public static ItemArgument m_235279_(CommandBuildContext p_235280_) {
        return new ItemArgument(p_235280_);
    }

    public ItemInput parse(StringReader p_120962_) throws CommandSyntaxException {
        ItemParser.ItemResult $$1 = ItemParser.m_235305_(this.f_235276_, p_120962_);
        return new ItemInput($$1.f_235328_(), $$1.f_235329_());
    }

    public static <S> ItemInput m_120963_(CommandContext<S> p_120964_, String p_120965_) {
        return (ItemInput)p_120964_.getArgument(p_120965_, ItemInput.class);
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_120968_, SuggestionsBuilder p_120969_) {
        return ItemParser.m_235308_(this.f_235276_, p_120969_, false);
    }

    public Collection<String> getExamples() {
        return f_120957_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

