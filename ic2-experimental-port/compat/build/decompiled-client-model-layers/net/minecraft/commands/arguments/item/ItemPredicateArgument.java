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
 *  com.mojang.datafixers.util.Either
 *  javax.annotation.Nullable
 */
package net.minecraft.commands.arguments.item;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.datafixers.util.Either;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemPredicateArgument
implements ArgumentType<Result> {
    private static final Collection<String> f_121033_ = Arrays.asList("stick", "minecraft:stick", "#stick", "#stick{foo=bar}");
    private final HolderLookup<Item> f_235350_;

    public ItemPredicateArgument(CommandBuildContext p_235352_) {
        this.f_235350_ = p_235352_.m_227133_(Registry.f_122904_);
    }

    public static ItemPredicateArgument m_235353_(CommandBuildContext p_235354_) {
        return new ItemPredicateArgument(p_235354_);
    }

    public Result parse(StringReader p_121039_) throws CommandSyntaxException {
        Either<ItemParser.ItemResult, ItemParser.TagResult> $$1 = ItemParser.m_235319_(this.f_235350_, p_121039_);
        return (Result)$$1.map(p_235356_ -> ItemPredicateArgument.m_235365_(p_235359_ -> p_235359_ == p_235356_.f_235328_(), p_235356_.f_235329_()), p_235361_ -> ItemPredicateArgument.m_235365_(p_235361_.f_235339_()::m_203333_, p_235361_.f_235340_()));
    }

    public static Predicate<ItemStack> m_121040_(CommandContext<CommandSourceStack> p_121041_, String p_121042_) {
        return (Predicate)p_121041_.getArgument(p_121042_, Result.class);
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_121054_, SuggestionsBuilder p_121055_) {
        return ItemParser.m_235308_(this.f_235350_, p_121055_, true);
    }

    public Collection<String> getExamples() {
        return f_121033_;
    }

    private static Result m_235365_(Predicate<Holder<Item>> p_235366_, @Nullable CompoundTag p_235367_) {
        return p_235367_ != null ? p_235371_ -> p_235371_.m_220167_(p_235366_) && NbtUtils.m_129235_(p_235367_, p_235371_.m_41783_(), true) : p_235364_ -> p_235364_.m_220167_(p_235366_);
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    public static interface Result
    extends Predicate<ItemStack> {
    }
}

