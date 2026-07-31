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
 *  javax.annotation.Nullable
 */
package net.minecraft.commands.arguments.blocks;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.properties.Property;

public class BlockPredicateArgument
implements ArgumentType<Result> {
    private static final Collection<String> f_115566_ = Arrays.asList("stone", "minecraft:stone", "stone[foo=bar]", "#stone", "#stone[foo=bar]{baz=nbt}");
    private final HolderLookup<Block> f_234624_;

    public BlockPredicateArgument(CommandBuildContext p_234626_) {
        this.f_234624_ = p_234626_.m_227133_(Registry.f_122901_);
    }

    public static BlockPredicateArgument m_234627_(CommandBuildContext p_234628_) {
        return new BlockPredicateArgument(p_234628_);
    }

    public Result parse(StringReader p_115572_) throws CommandSyntaxException {
        return BlockPredicateArgument.m_234633_(this.f_234624_, p_115572_);
    }

    public static Result m_234633_(HolderLookup<Block> p_234634_, StringReader p_234635_) throws CommandSyntaxException {
        return (Result)BlockStateParser.m_234716_(p_234634_, p_234635_, true).map(p_234630_ -> new BlockPredicate(p_234630_.f_234748_(), p_234630_.f_234749_().keySet(), p_234630_.f_234750_()), p_234632_ -> new TagPredicate(p_234632_.f_234762_(), p_234632_.f_234763_(), p_234632_.f_234764_()));
    }

    public static Predicate<BlockInWorld> m_115573_(CommandContext<CommandSourceStack> p_115574_, String p_115575_) throws CommandSyntaxException {
        return (Predicate)p_115574_.getArgument(p_115575_, Result.class);
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_115587_, SuggestionsBuilder p_115588_) {
        return BlockStateParser.m_234695_(this.f_234624_, p_115588_, true, true);
    }

    public Collection<String> getExamples() {
        return f_115566_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    public static interface Result
    extends Predicate<BlockInWorld> {
        public boolean m_183631_();
    }

    static class TagPredicate
    implements Result {
        private final HolderSet<Block> f_115604_;
        @Nullable
        private final CompoundTag f_115605_;
        private final Map<String, String> f_115606_;

        TagPredicate(HolderSet<Block> p_234637_, Map<String, String> p_234638_, @Nullable CompoundTag p_234639_) {
            this.f_115604_ = p_234637_;
            this.f_115606_ = p_234638_;
            this.f_115605_ = p_234639_;
        }

        @Override
        public boolean test(BlockInWorld p_115617_) {
            BlockState $$1 = p_115617_.m_61168_();
            if (!$$1.m_204341_(this.f_115604_)) {
                return false;
            }
            for (Map.Entry<String, String> $$2 : this.f_115606_.entrySet()) {
                Property<?> $$3 = $$1.m_60734_().m_49965_().m_61081_($$2.getKey());
                if ($$3 == null) {
                    return false;
                }
                Comparable $$4 = $$3.m_6215_($$2.getValue()).orElse(null);
                if ($$4 == null) {
                    return false;
                }
                if ($$1.m_61143_($$3) == $$4) continue;
                return false;
            }
            if (this.f_115605_ != null) {
                BlockEntity $$5 = p_115617_.m_61174_();
                return $$5 != null && NbtUtils.m_129235_(this.f_115605_, $$5.m_187480_(), true);
            }
            return true;
        }

        @Override
        public boolean m_183631_() {
            return this.f_115605_ != null;
        }

        @Override
        public /* synthetic */ boolean test(Object object) {
            return this.test((BlockInWorld)object);
        }
    }

    static class BlockPredicate
    implements Result {
        private final BlockState f_115591_;
        private final Set<Property<?>> f_115592_;
        @Nullable
        private final CompoundTag f_115593_;

        public BlockPredicate(BlockState p_115595_, Set<Property<?>> p_115596_, @Nullable CompoundTag p_115597_) {
            this.f_115591_ = p_115595_;
            this.f_115592_ = p_115596_;
            this.f_115593_ = p_115597_;
        }

        @Override
        public boolean test(BlockInWorld p_115599_) {
            BlockState $$1 = p_115599_.m_61168_();
            if (!$$1.m_60713_(this.f_115591_.m_60734_())) {
                return false;
            }
            for (Property<?> $$2 : this.f_115592_) {
                if ($$1.m_61143_($$2) == this.f_115591_.m_61143_($$2)) continue;
                return false;
            }
            if (this.f_115593_ != null) {
                BlockEntity $$3 = p_115599_.m_61174_();
                return $$3 != null && NbtUtils.m_129235_(this.f_115593_, $$3.m_187480_(), true);
            }
            return true;
        }

        @Override
        public boolean m_183631_() {
            return this.f_115593_ != null;
        }

        @Override
        public /* synthetic */ boolean test(Object object) {
            return this.test((BlockInWorld)object);
        }
    }
}

