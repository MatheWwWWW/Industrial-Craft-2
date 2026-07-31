/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  javax.annotation.Nullable
 */
package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.commands.arguments.blocks.BlockPredicateArgument;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.SetBlockCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Clearable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class FillCommand {
    private static final int f_180222_ = 32768;
    private static final Dynamic2CommandExceptionType f_137372_ = new Dynamic2CommandExceptionType((p_137392_, p_137393_) -> Component.m_237110_("commands.fill.toobig", p_137392_, p_137393_));
    static final BlockInput f_137373_ = new BlockInput(Blocks.f_50016_.m_49966_(), Collections.emptySet(), null);
    private static final SimpleCommandExceptionType f_137374_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.fill.failed"));

    public static void m_214442_(CommandDispatcher<CommandSourceStack> p_214443_, CommandBuildContext p_214444_) {
        p_214443_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("fill").requires(p_137384_ -> p_137384_.m_6761_(2))).then(Commands.m_82129_("from", BlockPosArgument.m_118239_()).then(Commands.m_82129_("to", BlockPosArgument.m_118239_()).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("block", BlockStateArgument.m_234650_(p_214444_)).executes(p_137405_ -> FillCommand.m_137385_((CommandSourceStack)p_137405_.getSource(), BoundingBox.m_162375_(BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_137405_, "from"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_137405_, "to")), BlockStateArgument.m_116123_((CommandContext<CommandSourceStack>)p_137405_, "block"), Mode.REPLACE, null))).then(((LiteralArgumentBuilder)Commands.m_82127_("replace").executes(p_137403_ -> FillCommand.m_137385_((CommandSourceStack)p_137403_.getSource(), BoundingBox.m_162375_(BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_137403_, "from"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_137403_, "to")), BlockStateArgument.m_116123_((CommandContext<CommandSourceStack>)p_137403_, "block"), Mode.REPLACE, null))).then(Commands.m_82129_("filter", BlockPredicateArgument.m_234627_(p_214444_)).executes(p_137401_ -> FillCommand.m_137385_((CommandSourceStack)p_137401_.getSource(), BoundingBox.m_162375_(BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_137401_, "from"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_137401_, "to")), BlockStateArgument.m_116123_((CommandContext<CommandSourceStack>)p_137401_, "block"), Mode.REPLACE, BlockPredicateArgument.m_115573_((CommandContext<CommandSourceStack>)p_137401_, "filter")))))).then(Commands.m_82127_("keep").executes(p_137399_ -> FillCommand.m_137385_((CommandSourceStack)p_137399_.getSource(), BoundingBox.m_162375_(BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_137399_, "from"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_137399_, "to")), BlockStateArgument.m_116123_((CommandContext<CommandSourceStack>)p_137399_, "block"), Mode.REPLACE, p_180225_ -> p_180225_.m_61175_().m_46859_(p_180225_.m_61176_()))))).then(Commands.m_82127_("outline").executes(p_137397_ -> FillCommand.m_137385_((CommandSourceStack)p_137397_.getSource(), BoundingBox.m_162375_(BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_137397_, "from"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_137397_, "to")), BlockStateArgument.m_116123_((CommandContext<CommandSourceStack>)p_137397_, "block"), Mode.OUTLINE, null)))).then(Commands.m_82127_("hollow").executes(p_137395_ -> FillCommand.m_137385_((CommandSourceStack)p_137395_.getSource(), BoundingBox.m_162375_(BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_137395_, "from"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_137395_, "to")), BlockStateArgument.m_116123_((CommandContext<CommandSourceStack>)p_137395_, "block"), Mode.HOLLOW, null)))).then(Commands.m_82127_("destroy").executes(p_137382_ -> FillCommand.m_137385_((CommandSourceStack)p_137382_.getSource(), BoundingBox.m_162375_(BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_137382_, "from"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_137382_, "to")), BlockStateArgument.m_116123_((CommandContext<CommandSourceStack>)p_137382_, "block"), Mode.DESTROY, null)))))));
    }

    private static int m_137385_(CommandSourceStack p_137386_, BoundingBox p_137387_, BlockInput p_137388_, Mode p_137389_, @Nullable Predicate<BlockInWorld> p_137390_) throws CommandSyntaxException {
        int $$5 = p_137387_.m_71056_() * p_137387_.m_71057_() * p_137387_.m_71058_();
        if ($$5 > 32768) {
            throw f_137372_.create((Object)32768, (Object)$$5);
        }
        ArrayList $$6 = Lists.newArrayList();
        ServerLevel $$7 = p_137386_.m_81372_();
        int $$8 = 0;
        for (BlockPos $$9 : BlockPos.m_121976_(p_137387_.m_162395_(), p_137387_.m_162396_(), p_137387_.m_162398_(), p_137387_.m_162399_(), p_137387_.m_162400_(), p_137387_.m_162401_())) {
            BlockInput $$10;
            if (p_137390_ != null && !p_137390_.test(new BlockInWorld($$7, $$9, true)) || ($$10 = p_137389_.f_137410_.m_138619_(p_137387_, $$9, p_137388_, $$7)) == null) continue;
            BlockEntity $$11 = $$7.m_7702_($$9);
            Clearable.m_18908_($$11);
            if (!$$10.m_114670_($$7, $$9, 2)) continue;
            $$6.add($$9.m_7949_());
            ++$$8;
        }
        for (BlockPos $$12 : $$6) {
            Block $$13 = $$7.m_8055_($$12).m_60734_();
            $$7.m_6289_($$12, $$13);
        }
        if ($$8 == 0) {
            throw f_137374_.create();
        }
        p_137386_.m_81354_(Component.m_237110_("commands.fill.success", $$8), true);
        return $$8;
    }

    static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode REPLACE = new Mode((p_137433_, p_137434_, p_137435_, p_137436_) -> p_137435_);
        public static final /* enum */ Mode OUTLINE = new Mode((p_137428_, p_137429_, p_137430_, p_137431_) -> {
            if (p_137429_.m_123341_() == p_137428_.m_162395_() || p_137429_.m_123341_() == p_137428_.m_162399_() || p_137429_.m_123342_() == p_137428_.m_162396_() || p_137429_.m_123342_() == p_137428_.m_162400_() || p_137429_.m_123343_() == p_137428_.m_162398_() || p_137429_.m_123343_() == p_137428_.m_162401_()) {
                return p_137430_;
            }
            return null;
        });
        public static final /* enum */ Mode HOLLOW = new Mode((p_137423_, p_137424_, p_137425_, p_137426_) -> {
            if (p_137424_.m_123341_() == p_137423_.m_162395_() || p_137424_.m_123341_() == p_137423_.m_162399_() || p_137424_.m_123342_() == p_137423_.m_162396_() || p_137424_.m_123342_() == p_137423_.m_162400_() || p_137424_.m_123343_() == p_137423_.m_162398_() || p_137424_.m_123343_() == p_137423_.m_162401_()) {
                return p_137425_;
            }
            return f_137373_;
        });
        public static final /* enum */ Mode DESTROY = new Mode((p_137418_, p_137419_, p_137420_, p_137421_) -> {
            p_137421_.m_46961_(p_137419_, true);
            return p_137420_;
        });
        public final SetBlockCommand.Filter f_137410_;
        private static final /* synthetic */ Mode[] $VALUES;

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        public static Mode valueOf(String p_137438_) {
            return Enum.valueOf(Mode.class, p_137438_);
        }

        private Mode(SetBlockCommand.Filter p_137416_) {
            this.f_137410_ = p_137416_;
        }

        private static /* synthetic */ Mode[] m_180226_() {
            return new Mode[]{REPLACE, OUTLINE, HOLLOW, DESTROY};
        }

        static {
            $VALUES = Mode.m_180226_();
        }
    }
}

