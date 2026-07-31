/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.ImmutableStringReader
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  com.mojang.datafixers.util.Either
 *  javax.annotation.Nullable
 */
package net.minecraft.commands.arguments.item;

import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.datafixers.util.Either;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ItemParser {
    private static final SimpleCommandExceptionType f_120991_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.item.tag.disallowed"));
    private static final DynamicCommandExceptionType f_120992_ = new DynamicCommandExceptionType(p_121013_ -> Component.m_237110_("argument.item.id.invalid", p_121013_));
    private static final DynamicCommandExceptionType f_235286_ = new DynamicCommandExceptionType(p_235313_ -> Component.m_237110_("arguments.item.tag.unknown", p_235313_));
    private static final char f_175091_ = '{';
    private static final char f_175092_ = '#';
    private static final Function<SuggestionsBuilder, CompletableFuture<Suggestions>> f_120993_ = SuggestionsBuilder::buildFuture;
    private final HolderLookup<Item> f_235287_;
    private final StringReader f_120994_;
    private final boolean f_235288_;
    private Either<Holder<Item>, HolderSet<Item>> f_235289_;
    @Nullable
    private CompoundTag f_120998_;
    private Function<SuggestionsBuilder, CompletableFuture<Suggestions>> f_121001_ = f_120993_;

    private ItemParser(HolderLookup<Item> p_235291_, StringReader p_235292_, boolean p_235293_) {
        this.f_235287_ = p_235291_;
        this.f_120994_ = p_235292_;
        this.f_235288_ = p_235293_;
    }

    public static ItemResult m_235305_(HolderLookup<Item> p_235306_, StringReader p_235307_) throws CommandSyntaxException {
        int $$2 = p_235307_.getCursor();
        try {
            ItemParser $$3 = new ItemParser(p_235306_, p_235307_, false);
            $$3.m_235324_();
            Holder $$4 = (Holder)$$3.f_235289_.left().orElseThrow(() -> new IllegalStateException("Parser returned unexpected tag name"));
            return new ItemResult($$4, $$3.f_120998_);
        }
        catch (CommandSyntaxException $$5) {
            p_235307_.setCursor($$2);
            throw $$5;
        }
    }

    public static Either<ItemResult, TagResult> m_235319_(HolderLookup<Item> p_235320_, StringReader p_235321_) throws CommandSyntaxException {
        int $$2 = p_235321_.getCursor();
        try {
            ItemParser $$3 = new ItemParser(p_235320_, p_235321_, true);
            $$3.m_235324_();
            return $$3.f_235289_.mapBoth(p_235301_ -> new ItemResult((Holder<Item>)p_235301_, p_235300_.f_120998_), p_235304_ -> new TagResult((HolderSet<Item>)p_235304_, p_235303_.f_120998_));
        }
        catch (CommandSyntaxException $$4) {
            p_235321_.setCursor($$2);
            throw $$4;
        }
    }

    public static CompletableFuture<Suggestions> m_235308_(HolderLookup<Item> p_235309_, SuggestionsBuilder p_235310_, boolean p_235311_) {
        StringReader $$3 = new StringReader(p_235310_.getInput());
        $$3.setCursor(p_235310_.getStart());
        ItemParser $$4 = new ItemParser(p_235309_, $$3, p_235311_);
        try {
            $$4.m_235324_();
        }
        catch (CommandSyntaxException commandSyntaxException) {
            // empty catch block
        }
        return $$4.f_121001_.apply(p_235310_.createOffset($$3.getCursor()));
    }

    private void m_121026_() throws CommandSyntaxException {
        int $$0 = this.f_120994_.getCursor();
        ResourceLocation $$1 = ResourceLocation.m_135818_(this.f_120994_);
        Optional<Holder<Item>> $$2 = this.f_235287_.m_213562_(ResourceKey.m_135785_(Registry.f_122904_, $$1));
        this.f_235289_ = Either.left($$2.orElseThrow(() -> {
            this.f_120994_.setCursor($$0);
            return f_120992_.createWithContext((ImmutableStringReader)this.f_120994_, (Object)$$1);
        }));
    }

    private void m_121030_() throws CommandSyntaxException {
        if (!this.f_235288_) {
            throw f_120991_.createWithContext((ImmutableStringReader)this.f_120994_);
        }
        int $$0 = this.f_120994_.getCursor();
        this.f_120994_.expect('#');
        this.f_121001_ = this::m_235317_;
        ResourceLocation $$1 = ResourceLocation.m_135818_(this.f_120994_);
        Optional<HolderSet<Item>> $$2 = this.f_235287_.m_213810_(TagKey.m_203882_(Registry.f_122904_, $$1));
        this.f_235289_ = Either.right($$2.orElseThrow(() -> {
            this.f_120994_.setCursor($$0);
            return f_235286_.createWithContext((ImmutableStringReader)this.f_120994_, (Object)$$1);
        }));
    }

    private void m_121031_() throws CommandSyntaxException {
        this.f_120998_ = new TagParser(this.f_120994_).m_129373_();
    }

    private void m_235324_() throws CommandSyntaxException {
        this.f_121001_ = this.f_235288_ ? this::m_235325_ : this::m_235322_;
        if (this.f_120994_.canRead() && this.f_120994_.peek() == '#') {
            this.m_121030_();
        } else {
            this.m_121026_();
        }
        this.f_121001_ = this::m_235297_;
        if (this.f_120994_.canRead() && this.f_120994_.peek() == '{') {
            this.f_121001_ = f_120993_;
            this.m_121031_();
        }
    }

    private CompletableFuture<Suggestions> m_235297_(SuggestionsBuilder p_235298_) {
        if (p_235298_.getRemaining().isEmpty()) {
            p_235298_.suggest(String.valueOf('{'));
        }
        return p_235298_.buildFuture();
    }

    private CompletableFuture<Suggestions> m_235317_(SuggestionsBuilder p_235318_) {
        return SharedSuggestionProvider.m_205106_(this.f_235287_.m_214063_().map(TagKey::f_203868_), p_235318_, String.valueOf('#'));
    }

    private CompletableFuture<Suggestions> m_235322_(SuggestionsBuilder p_235323_) {
        return SharedSuggestionProvider.m_82957_(this.f_235287_.m_214062_().map(ResourceKey::m_135782_), p_235323_);
    }

    private CompletableFuture<Suggestions> m_235325_(SuggestionsBuilder p_235326_) {
        this.m_235317_(p_235326_);
        return this.m_235322_(p_235326_);
    }

    public record ItemResult(Holder<Item> f_235328_, @Nullable CompoundTag f_235329_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{ItemResult.class, "item;nbt", "f_235328_", "f_235329_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ItemResult.class, "item;nbt", "f_235328_", "f_235329_"}, this);
        }

        @Override
        public final boolean equals(Object p_235336_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ItemResult.class, "item;nbt", "f_235328_", "f_235329_"}, this, p_235336_);
        }
    }

    public record TagResult(HolderSet<Item> f_235339_, @Nullable CompoundTag f_235340_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{TagResult.class, "tag;nbt", "f_235339_", "f_235340_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{TagResult.class, "tag;nbt", "f_235339_", "f_235340_"}, this);
        }

        @Override
        public final boolean equals(Object p_235347_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{TagResult.class, "tag;nbt", "f_235339_", "f_235340_"}, this, p_235347_);
        }
    }
}

