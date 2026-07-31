/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.brigadier.ImmutableStringReader
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
 *  com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  com.mojang.datafixers.util.Either
 *  javax.annotation.Nullable
 */
package net.minecraft.commands.arguments.blocks;

import com.google.common.collect.Maps;
import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.datafixers.util.Either;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

public class BlockStateParser {
    public static final SimpleCommandExceptionType f_116741_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.block.tag.disallowed"));
    public static final DynamicCommandExceptionType f_116742_ = new DynamicCommandExceptionType(p_116790_ -> Component.m_237110_("argument.block.id.invalid", p_116790_));
    public static final Dynamic2CommandExceptionType f_116743_ = new Dynamic2CommandExceptionType((p_116820_, p_116821_) -> Component.m_237110_("argument.block.property.unknown", p_116820_, p_116821_));
    public static final Dynamic2CommandExceptionType f_116744_ = new Dynamic2CommandExceptionType((p_116813_, p_116814_) -> Component.m_237110_("argument.block.property.duplicate", p_116814_, p_116813_));
    public static final Dynamic3CommandExceptionType f_116745_ = new Dynamic3CommandExceptionType((p_116795_, p_116796_, p_116797_) -> Component.m_237110_("argument.block.property.invalid", p_116795_, p_116797_, p_116796_));
    public static final Dynamic2CommandExceptionType f_116746_ = new Dynamic2CommandExceptionType((p_116792_, p_116793_) -> Component.m_237110_("argument.block.property.novalue", p_116792_, p_116793_));
    public static final SimpleCommandExceptionType f_116747_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.block.property.unclosed"));
    public static final DynamicCommandExceptionType f_234669_ = new DynamicCommandExceptionType(p_234709_ -> Component.m_237110_("arguments.block.tag.unknown", p_234709_));
    private static final char f_174101_ = '[';
    private static final char f_174102_ = '{';
    private static final char f_174103_ = ']';
    private static final char f_174104_ = '=';
    private static final char f_174105_ = ',';
    private static final char f_174106_ = '#';
    private static final Function<SuggestionsBuilder, CompletableFuture<Suggestions>> f_116748_ = SuggestionsBuilder::buildFuture;
    private final HolderLookup<Block> f_234670_;
    private final StringReader f_116749_;
    private final boolean f_116750_;
    private final boolean f_234671_;
    private final Map<Property<?>, Comparable<?>> f_116751_ = Maps.newHashMap();
    private final Map<String, String> f_116752_ = Maps.newHashMap();
    private ResourceLocation f_116753_ = new ResourceLocation("");
    @Nullable
    private StateDefinition<Block, BlockState> f_116754_;
    @Nullable
    private BlockState f_116755_;
    @Nullable
    private CompoundTag f_116756_;
    @Nullable
    private HolderSet<Block> f_116757_;
    private Function<SuggestionsBuilder, CompletableFuture<Suggestions>> f_116759_ = f_116748_;

    private BlockStateParser(HolderLookup<Block> p_234673_, StringReader p_234674_, boolean p_234675_, boolean p_234676_) {
        this.f_234670_ = p_234673_;
        this.f_116749_ = p_234674_;
        this.f_116750_ = p_234675_;
        this.f_234671_ = p_234676_;
    }

    public static BlockResult m_234704_(Registry<Block> p_234705_, String p_234706_, boolean p_234707_) throws CommandSyntaxException {
        return BlockStateParser.m_234700_(p_234705_, new StringReader(p_234706_), p_234707_);
    }

    public static BlockResult m_234700_(Registry<Block> p_234701_, StringReader p_234702_, boolean p_234703_) throws CommandSyntaxException {
        return BlockStateParser.m_234691_(HolderLookup.m_235701_(p_234701_), p_234702_, p_234703_);
    }

    public static BlockResult m_234691_(HolderLookup<Block> p_234692_, StringReader p_234693_, boolean p_234694_) throws CommandSyntaxException {
        int $$3 = p_234693_.getCursor();
        try {
            BlockStateParser $$4 = new BlockStateParser(p_234692_, p_234693_, false, p_234694_);
            $$4.m_234677_();
            return new BlockResult($$4.f_116755_, $$4.f_116751_, $$4.f_116756_);
        }
        catch (CommandSyntaxException $$5) {
            p_234693_.setCursor($$3);
            throw $$5;
        }
    }

    public static Either<BlockResult, TagResult> m_234724_(Registry<Block> p_234725_, String p_234726_, boolean p_234727_) throws CommandSyntaxException {
        return BlockStateParser.m_234720_(p_234725_, new StringReader(p_234726_), p_234727_);
    }

    public static Either<BlockResult, TagResult> m_234720_(Registry<Block> p_234721_, StringReader p_234722_, boolean p_234723_) throws CommandSyntaxException {
        return BlockStateParser.m_234716_(HolderLookup.m_235701_(p_234721_), new StringReader(p_234722_), p_234723_);
    }

    public static Either<BlockResult, TagResult> m_234716_(HolderLookup<Block> p_234717_, StringReader p_234718_, boolean p_234719_) throws CommandSyntaxException {
        int $$3 = p_234718_.getCursor();
        try {
            BlockStateParser $$4 = new BlockStateParser(p_234717_, p_234718_, true, p_234719_);
            $$4.m_234677_();
            if ($$4.f_116757_ != null) {
                return Either.right((Object)new TagResult($$4.f_116757_, $$4.f_116752_, $$4.f_116756_));
            }
            return Either.left((Object)new BlockResult($$4.f_116755_, $$4.f_116751_, $$4.f_116756_));
        }
        catch (CommandSyntaxException $$5) {
            p_234718_.setCursor($$3);
            throw $$5;
        }
    }

    public static CompletableFuture<Suggestions> m_234695_(HolderLookup<Block> p_234696_, SuggestionsBuilder p_234697_, boolean p_234698_, boolean p_234699_) {
        StringReader $$4 = new StringReader(p_234697_.getInput());
        $$4.setCursor(p_234697_.getStart());
        BlockStateParser $$5 = new BlockStateParser(p_234696_, $$4, p_234698_, p_234699_);
        try {
            $$5.m_234677_();
        }
        catch (CommandSyntaxException commandSyntaxException) {
            // empty catch block
        }
        return $$5.f_116759_.apply(p_234697_.createOffset($$4.getCursor()));
    }

    private void m_234677_() throws CommandSyntaxException {
        this.f_116759_ = this.f_116750_ ? this::m_234746_ : this::m_234744_;
        if (this.f_116749_.canRead() && this.f_116749_.peek() == '#') {
            this.m_116830_();
            this.f_116759_ = this::m_234738_;
            if (this.f_116749_.canRead() && this.f_116749_.peek() == '[') {
                this.m_116838_();
                this.f_116759_ = this::m_234732_;
            }
        } else {
            this.m_116826_();
            this.f_116759_ = this::m_234740_;
            if (this.f_116749_.canRead() && this.f_116749_.peek() == '[') {
                this.m_116834_();
                this.f_116759_ = this::m_234732_;
            }
        }
        if (this.f_234671_ && this.f_116749_.canRead() && this.f_116749_.peek() == '{') {
            this.f_116759_ = f_116748_;
            this.m_116842_();
        }
    }

    private CompletableFuture<Suggestions> m_234683_(SuggestionsBuilder p_234684_) {
        if (p_234684_.getRemaining().isEmpty()) {
            p_234684_.suggest(String.valueOf(']'));
        }
        return this.m_234728_(p_234684_);
    }

    private CompletableFuture<Suggestions> m_234714_(SuggestionsBuilder p_234715_) {
        if (p_234715_.getRemaining().isEmpty()) {
            p_234715_.suggest(String.valueOf(']'));
        }
        return this.m_234730_(p_234715_);
    }

    private CompletableFuture<Suggestions> m_234728_(SuggestionsBuilder p_234729_) {
        String $$1 = p_234729_.getRemaining().toLowerCase(Locale.ROOT);
        for (Property<?> $$2 : this.f_116755_.m_61147_()) {
            if (this.f_116751_.containsKey($$2) || !$$2.m_61708_().startsWith($$1)) continue;
            p_234729_.suggest($$2.m_61708_() + "=");
        }
        return p_234729_.buildFuture();
    }

    private CompletableFuture<Suggestions> m_234730_(SuggestionsBuilder p_234731_) {
        String $$1 = p_234731_.getRemaining().toLowerCase(Locale.ROOT);
        if (this.f_116757_ != null) {
            for (Holder holder : this.f_116757_) {
                for (Property<?> $$3 : ((Block)holder.m_203334_()).m_49965_().m_61092_()) {
                    if (this.f_116752_.containsKey($$3.m_61708_()) || !$$3.m_61708_().startsWith($$1)) continue;
                    p_234731_.suggest($$3.m_61708_() + "=");
                }
            }
        }
        return p_234731_.buildFuture();
    }

    private CompletableFuture<Suggestions> m_234732_(SuggestionsBuilder p_234733_) {
        if (p_234733_.getRemaining().isEmpty() && this.m_234713_()) {
            p_234733_.suggest(String.valueOf('{'));
        }
        return p_234733_.buildFuture();
    }

    private boolean m_234713_() {
        if (this.f_116755_ != null) {
            return this.f_116755_.m_155947_();
        }
        if (this.f_116757_ != null) {
            for (Holder holder : this.f_116757_) {
                if (!((Block)holder.m_203334_()).m_49966_().m_155947_()) continue;
                return true;
            }
        }
        return false;
    }

    private CompletableFuture<Suggestions> m_234734_(SuggestionsBuilder p_234735_) {
        if (p_234735_.getRemaining().isEmpty()) {
            p_234735_.suggest(String.valueOf('='));
        }
        return p_234735_.buildFuture();
    }

    private CompletableFuture<Suggestions> m_234736_(SuggestionsBuilder p_234737_) {
        if (p_234737_.getRemaining().isEmpty()) {
            p_234737_.suggest(String.valueOf(']'));
        }
        if (p_234737_.getRemaining().isEmpty() && this.f_116751_.size() < this.f_116755_.m_61147_().size()) {
            p_234737_.suggest(String.valueOf(','));
        }
        return p_234737_.buildFuture();
    }

    private static <T extends Comparable<T>> SuggestionsBuilder m_116786_(SuggestionsBuilder p_116787_, Property<T> p_116788_) {
        for (Comparable $$2 : p_116788_.m_6908_()) {
            if ($$2 instanceof Integer) {
                Integer $$3 = (Integer)$$2;
                p_116787_.suggest($$3.intValue());
                continue;
            }
            p_116787_.suggest(p_116788_.m_6940_($$2));
        }
        return p_116787_;
    }

    private CompletableFuture<Suggestions> m_234685_(SuggestionsBuilder p_234686_, String p_234687_) {
        boolean $$2 = false;
        if (this.f_116757_ != null) {
            block0: for (Holder holder : this.f_116757_) {
                Block $$4 = (Block)holder.m_203334_();
                Property<?> $$5 = $$4.m_49965_().m_61081_(p_234687_);
                if ($$5 != null) {
                    BlockStateParser.m_116786_(p_234686_, $$5);
                }
                if ($$2) continue;
                for (Property<?> $$6 : $$4.m_49965_().m_61092_()) {
                    if (this.f_116752_.containsKey($$6.m_61708_())) continue;
                    $$2 = true;
                    continue block0;
                }
            }
        }
        if ($$2) {
            p_234686_.suggest(String.valueOf(','));
        }
        p_234686_.suggest(String.valueOf(']'));
        return p_234686_.buildFuture();
    }

    private CompletableFuture<Suggestions> m_234738_(SuggestionsBuilder p_234739_) {
        if (p_234739_.getRemaining().isEmpty() && this.f_116757_ != null) {
            Holder $$3;
            Block $$4;
            boolean $$1 = false;
            boolean $$2 = false;
            Iterator iterator = this.f_116757_.iterator();
            while (!(!iterator.hasNext() || ($$1 |= !($$4 = (Block)($$3 = (Holder)iterator.next()).m_203334_()).m_49965_().m_61092_().isEmpty()) && ($$2 |= $$4.m_49966_().m_155947_()))) {
            }
            if ($$1) {
                p_234739_.suggest(String.valueOf('['));
            }
            if ($$2) {
                p_234739_.suggest(String.valueOf('{'));
            }
        }
        return p_234739_.buildFuture();
    }

    private CompletableFuture<Suggestions> m_234740_(SuggestionsBuilder p_234741_) {
        if (p_234741_.getRemaining().isEmpty()) {
            if (!this.f_116754_.m_61092_().isEmpty()) {
                p_234741_.suggest(String.valueOf('['));
            }
            if (this.f_116755_.m_155947_()) {
                p_234741_.suggest(String.valueOf('{'));
            }
        }
        return p_234741_.buildFuture();
    }

    private CompletableFuture<Suggestions> m_234742_(SuggestionsBuilder p_234743_) {
        return SharedSuggestionProvider.m_205106_(this.f_234670_.m_214063_().map(TagKey::f_203868_), p_234743_, String.valueOf('#'));
    }

    private CompletableFuture<Suggestions> m_234744_(SuggestionsBuilder p_234745_) {
        return SharedSuggestionProvider.m_82957_(this.f_234670_.m_214062_().map(ResourceKey::m_135782_), p_234745_);
    }

    private CompletableFuture<Suggestions> m_234746_(SuggestionsBuilder p_234747_) {
        this.m_234742_(p_234747_);
        this.m_234744_(p_234747_);
        return p_234747_.buildFuture();
    }

    private void m_116826_() throws CommandSyntaxException {
        int $$0 = this.f_116749_.getCursor();
        this.f_116753_ = ResourceLocation.m_135818_(this.f_116749_);
        Block $$1 = this.f_234670_.m_213562_(ResourceKey.m_135785_(Registry.f_122901_, this.f_116753_)).orElseThrow(() -> {
            this.f_116749_.setCursor($$0);
            return f_116742_.createWithContext((ImmutableStringReader)this.f_116749_, (Object)this.f_116753_.toString());
        }).m_203334_();
        this.f_116754_ = $$1.m_49965_();
        this.f_116755_ = $$1.m_49966_();
    }

    private void m_116830_() throws CommandSyntaxException {
        if (!this.f_116750_) {
            throw f_116741_.createWithContext((ImmutableStringReader)this.f_116749_);
        }
        int $$0 = this.f_116749_.getCursor();
        this.f_116749_.expect('#');
        this.f_116759_ = this::m_234742_;
        ResourceLocation $$1 = ResourceLocation.m_135818_(this.f_116749_);
        this.f_116757_ = this.f_234670_.m_213810_(TagKey.m_203882_(Registry.f_122901_, $$1)).orElseThrow(() -> {
            this.f_116749_.setCursor($$0);
            return f_234669_.createWithContext((ImmutableStringReader)this.f_116749_, (Object)$$1.toString());
        });
    }

    private void m_116834_() throws CommandSyntaxException {
        this.f_116749_.skip();
        this.f_116759_ = this::m_234683_;
        this.f_116749_.skipWhitespace();
        while (this.f_116749_.canRead() && this.f_116749_.peek() != ']') {
            this.f_116749_.skipWhitespace();
            int $$0 = this.f_116749_.getCursor();
            String $$1 = this.f_116749_.readString();
            Property<?> $$2 = this.f_116754_.m_61081_($$1);
            if ($$2 == null) {
                this.f_116749_.setCursor($$0);
                throw f_116743_.createWithContext((ImmutableStringReader)this.f_116749_, (Object)this.f_116753_.toString(), (Object)$$1);
            }
            if (this.f_116751_.containsKey($$2)) {
                this.f_116749_.setCursor($$0);
                throw f_116744_.createWithContext((ImmutableStringReader)this.f_116749_, (Object)this.f_116753_.toString(), (Object)$$1);
            }
            this.f_116749_.skipWhitespace();
            this.f_116759_ = this::m_234734_;
            if (!this.f_116749_.canRead() || this.f_116749_.peek() != '=') {
                throw f_116746_.createWithContext((ImmutableStringReader)this.f_116749_, (Object)this.f_116753_.toString(), (Object)$$1);
            }
            this.f_116749_.skip();
            this.f_116749_.skipWhitespace();
            this.f_116759_ = p_234690_ -> BlockStateParser.m_116786_(p_234690_, $$2).buildFuture();
            int $$3 = this.f_116749_.getCursor();
            this.m_116775_($$2, this.f_116749_.readString(), $$3);
            this.f_116759_ = this::m_234736_;
            this.f_116749_.skipWhitespace();
            if (!this.f_116749_.canRead()) continue;
            if (this.f_116749_.peek() == ',') {
                this.f_116749_.skip();
                this.f_116759_ = this::m_234728_;
                continue;
            }
            if (this.f_116749_.peek() == ']') break;
            throw f_116747_.createWithContext((ImmutableStringReader)this.f_116749_);
        }
        if (!this.f_116749_.canRead()) {
            throw f_116747_.createWithContext((ImmutableStringReader)this.f_116749_);
        }
        this.f_116749_.skip();
    }

    private void m_116838_() throws CommandSyntaxException {
        this.f_116749_.skip();
        this.f_116759_ = this::m_234714_;
        int $$0 = -1;
        this.f_116749_.skipWhitespace();
        while (this.f_116749_.canRead() && this.f_116749_.peek() != ']') {
            this.f_116749_.skipWhitespace();
            int $$1 = this.f_116749_.getCursor();
            String $$2 = this.f_116749_.readString();
            if (this.f_116752_.containsKey($$2)) {
                this.f_116749_.setCursor($$1);
                throw f_116744_.createWithContext((ImmutableStringReader)this.f_116749_, (Object)this.f_116753_.toString(), (Object)$$2);
            }
            this.f_116749_.skipWhitespace();
            if (!this.f_116749_.canRead() || this.f_116749_.peek() != '=') {
                this.f_116749_.setCursor($$1);
                throw f_116746_.createWithContext((ImmutableStringReader)this.f_116749_, (Object)this.f_116753_.toString(), (Object)$$2);
            }
            this.f_116749_.skip();
            this.f_116749_.skipWhitespace();
            this.f_116759_ = p_234712_ -> this.m_234685_((SuggestionsBuilder)p_234712_, $$2);
            $$0 = this.f_116749_.getCursor();
            String $$3 = this.f_116749_.readString();
            this.f_116752_.put($$2, $$3);
            this.f_116749_.skipWhitespace();
            if (!this.f_116749_.canRead()) continue;
            $$0 = -1;
            if (this.f_116749_.peek() == ',') {
                this.f_116749_.skip();
                this.f_116759_ = this::m_234730_;
                continue;
            }
            if (this.f_116749_.peek() == ']') break;
            throw f_116747_.createWithContext((ImmutableStringReader)this.f_116749_);
        }
        if (!this.f_116749_.canRead()) {
            if ($$0 >= 0) {
                this.f_116749_.setCursor($$0);
            }
            throw f_116747_.createWithContext((ImmutableStringReader)this.f_116749_);
        }
        this.f_116749_.skip();
    }

    private void m_116842_() throws CommandSyntaxException {
        this.f_116756_ = new TagParser(this.f_116749_).m_129373_();
    }

    private <T extends Comparable<T>> void m_116775_(Property<T> p_116776_, String p_116777_, int p_116778_) throws CommandSyntaxException {
        Optional<T> $$3 = p_116776_.m_6215_(p_116777_);
        if (!$$3.isPresent()) {
            this.f_116749_.setCursor(p_116778_);
            throw f_116745_.createWithContext((ImmutableStringReader)this.f_116749_, (Object)this.f_116753_.toString(), (Object)p_116776_.m_61708_(), (Object)p_116777_);
        }
        this.f_116755_ = (BlockState)this.f_116755_.m_61124_(p_116776_, (Comparable)$$3.get());
        this.f_116751_.put(p_116776_, (Comparable)$$3.get());
    }

    public static String m_116769_(BlockState p_116770_) {
        StringBuilder $$1 = new StringBuilder(p_116770_.m_222976_().m_203543_().map(p_234682_ -> p_234682_.m_135782_().toString()).orElse("air"));
        if (!p_116770_.m_61147_().isEmpty()) {
            $$1.append('[');
            boolean $$2 = false;
            for (Map.Entry $$3 : p_116770_.m_61148_().entrySet()) {
                if ($$2) {
                    $$1.append(',');
                }
                BlockStateParser.m_116802_($$1, (Property)$$3.getKey(), (Comparable)$$3.getValue());
                $$2 = true;
            }
            $$1.append(']');
        }
        return $$1.toString();
    }

    private static <T extends Comparable<T>> void m_116802_(StringBuilder p_116803_, Property<T> p_116804_, Comparable<?> p_116805_) {
        p_116803_.append(p_116804_.m_61708_());
        p_116803_.append('=');
        p_116803_.append(p_116804_.m_6940_(p_116805_));
    }

    public record BlockResult(BlockState f_234748_, Map<Property<?>, Comparable<?>> f_234749_, @Nullable CompoundTag f_234750_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{BlockResult.class, "blockState;properties;nbt", "f_234748_", "f_234749_", "f_234750_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{BlockResult.class, "blockState;properties;nbt", "f_234748_", "f_234749_", "f_234750_"}, this);
        }

        @Override
        public final boolean equals(Object p_234759_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{BlockResult.class, "blockState;properties;nbt", "f_234748_", "f_234749_", "f_234750_"}, this, p_234759_);
        }
    }

    public record TagResult(HolderSet<Block> f_234762_, Map<String, String> f_234763_, @Nullable CompoundTag f_234764_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{TagResult.class, "tag;vagueProperties;nbt", "f_234762_", "f_234763_", "f_234764_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{TagResult.class, "tag;vagueProperties;nbt", "f_234762_", "f_234763_", "f_234764_"}, this);
        }

        @Override
        public final boolean equals(Object p_234773_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{TagResult.class, "tag;vagueProperties;nbt", "f_234762_", "f_234763_", "f_234764_"}, this, p_234773_);
        }
    }
}

