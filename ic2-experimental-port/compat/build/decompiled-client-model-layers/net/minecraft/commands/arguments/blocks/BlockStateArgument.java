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
package net.minecraft.commands.arguments.blocks;

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
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;

public class BlockStateArgument
implements ArgumentType<BlockInput> {
    private static final Collection<String> f_116117_ = Arrays.asList("stone", "minecraft:stone", "stone[foo=bar]", "foo{bar=baz}");
    private final HolderLookup<Block> f_234647_;

    public BlockStateArgument(CommandBuildContext p_234649_) {
        this.f_234647_ = p_234649_.m_227133_(Registry.f_122901_);
    }

    public static BlockStateArgument m_234650_(CommandBuildContext p_234651_) {
        return new BlockStateArgument(p_234651_);
    }

    public BlockInput parse(StringReader p_116122_) throws CommandSyntaxException {
        BlockStateParser.BlockResult $$1 = BlockStateParser.m_234691_(this.f_234647_, p_116122_, true);
        return new BlockInput($$1.f_234748_(), $$1.f_234749_().keySet(), $$1.f_234750_());
    }

    public static BlockInput m_116123_(CommandContext<CommandSourceStack> p_116124_, String p_116125_) {
        return (BlockInput)p_116124_.getArgument(p_116125_, BlockInput.class);
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_116128_, SuggestionsBuilder p_116129_) {
        return BlockStateParser.m_234695_(this.f_234647_, p_116129_, false, true);
    }

    public Collection<String> getExamples() {
        return f_116117_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

