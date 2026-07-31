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
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockPredicateArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Clearable;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.ticks.LevelTicks;

public class CloneCommands {
    private static final int f_180030_ = 32768;
    private static final SimpleCommandExceptionType f_136723_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.clone.overlap"));
    private static final Dynamic2CommandExceptionType f_136724_ = new Dynamic2CommandExceptionType((p_136743_, p_136744_) -> Component.m_237110_("commands.clone.toobig", p_136743_, p_136744_));
    private static final SimpleCommandExceptionType f_136725_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.clone.failed"));
    public static final Predicate<BlockInWorld> f_136722_ = p_136762_ -> !p_136762_.m_61168_().m_60795_();

    public static void m_214423_(CommandDispatcher<CommandSourceStack> p_214424_, CommandBuildContext p_214425_) {
        p_214424_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("clone").requires(p_136734_ -> p_136734_.m_6761_(2))).then(Commands.m_82129_("begin", BlockPosArgument.m_118239_()).then(Commands.m_82129_("end", BlockPosArgument.m_118239_()).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("destination", BlockPosArgument.m_118239_()).executes(p_136778_ -> CloneCommands.m_136735_((CommandSourceStack)p_136778_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136778_, "begin"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136778_, "end"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136778_, "destination"), p_180041_ -> true, Mode.NORMAL))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("replace").executes(p_136776_ -> CloneCommands.m_136735_((CommandSourceStack)p_136776_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136776_, "begin"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136776_, "end"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136776_, "destination"), p_180039_ -> true, Mode.NORMAL))).then(Commands.m_82127_("force").executes(p_136774_ -> CloneCommands.m_136735_((CommandSourceStack)p_136774_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136774_, "begin"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136774_, "end"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136774_, "destination"), p_180037_ -> true, Mode.FORCE)))).then(Commands.m_82127_("move").executes(p_136772_ -> CloneCommands.m_136735_((CommandSourceStack)p_136772_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136772_, "begin"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136772_, "end"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136772_, "destination"), p_180035_ -> true, Mode.MOVE)))).then(Commands.m_82127_("normal").executes(p_136770_ -> CloneCommands.m_136735_((CommandSourceStack)p_136770_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136770_, "begin"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136770_, "end"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136770_, "destination"), p_180033_ -> true, Mode.NORMAL))))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("masked").executes(p_136768_ -> CloneCommands.m_136735_((CommandSourceStack)p_136768_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136768_, "begin"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136768_, "end"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136768_, "destination"), f_136722_, Mode.NORMAL))).then(Commands.m_82127_("force").executes(p_136766_ -> CloneCommands.m_136735_((CommandSourceStack)p_136766_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136766_, "begin"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136766_, "end"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136766_, "destination"), f_136722_, Mode.FORCE)))).then(Commands.m_82127_("move").executes(p_136764_ -> CloneCommands.m_136735_((CommandSourceStack)p_136764_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136764_, "begin"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136764_, "end"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136764_, "destination"), f_136722_, Mode.MOVE)))).then(Commands.m_82127_("normal").executes(p_136760_ -> CloneCommands.m_136735_((CommandSourceStack)p_136760_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136760_, "begin"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136760_, "end"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136760_, "destination"), f_136722_, Mode.NORMAL))))).then(Commands.m_82127_("filtered").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("filter", BlockPredicateArgument.m_234627_(p_214425_)).executes(p_136756_ -> CloneCommands.m_136735_((CommandSourceStack)p_136756_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136756_, "begin"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136756_, "end"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136756_, "destination"), BlockPredicateArgument.m_115573_((CommandContext<CommandSourceStack>)p_136756_, "filter"), Mode.NORMAL))).then(Commands.m_82127_("force").executes(p_136752_ -> CloneCommands.m_136735_((CommandSourceStack)p_136752_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136752_, "begin"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136752_, "end"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136752_, "destination"), BlockPredicateArgument.m_115573_((CommandContext<CommandSourceStack>)p_136752_, "filter"), Mode.FORCE)))).then(Commands.m_82127_("move").executes(p_136748_ -> CloneCommands.m_136735_((CommandSourceStack)p_136748_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136748_, "begin"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136748_, "end"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136748_, "destination"), BlockPredicateArgument.m_115573_((CommandContext<CommandSourceStack>)p_136748_, "filter"), Mode.MOVE)))).then(Commands.m_82127_("normal").executes(p_136732_ -> CloneCommands.m_136735_((CommandSourceStack)p_136732_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136732_, "begin"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136732_, "end"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_136732_, "destination"), BlockPredicateArgument.m_115573_((CommandContext<CommandSourceStack>)p_136732_, "filter"), Mode.NORMAL)))))))));
    }

    private static int m_136735_(CommandSourceStack p_136736_, BlockPos p_136737_, BlockPos p_136738_, BlockPos p_136739_, Predicate<BlockInWorld> p_136740_, Mode p_136741_) throws CommandSyntaxException {
        BoundingBox $$6 = BoundingBox.m_162375_(p_136737_, p_136738_);
        BlockPos $$7 = p_136739_.m_121955_($$6.m_71053_());
        BoundingBox $$8 = BoundingBox.m_162375_(p_136739_, $$7);
        if (!p_136741_.m_136796_() && $$8.m_71049_($$6)) {
            throw f_136723_.create();
        }
        int $$9 = $$6.m_71056_() * $$6.m_71057_() * $$6.m_71058_();
        if ($$9 > 32768) {
            throw f_136724_.create((Object)32768, (Object)$$9);
        }
        ServerLevel $$10 = p_136736_.m_81372_();
        if (!$$10.m_46832_(p_136737_, p_136738_) || !$$10.m_46832_(p_136739_, $$7)) {
            throw BlockPosArgument.f_118234_.create();
        }
        ArrayList $$11 = Lists.newArrayList();
        ArrayList $$12 = Lists.newArrayList();
        ArrayList $$13 = Lists.newArrayList();
        LinkedList $$14 = Lists.newLinkedList();
        BlockPos $$15 = new BlockPos($$8.m_162395_() - $$6.m_162395_(), $$8.m_162396_() - $$6.m_162396_(), $$8.m_162398_() - $$6.m_162398_());
        for (int $$16 = $$6.m_162398_(); $$16 <= $$6.m_162401_(); ++$$16) {
            for (int $$17 = $$6.m_162396_(); $$17 <= $$6.m_162400_(); ++$$17) {
                for (int $$18 = $$6.m_162395_(); $$18 <= $$6.m_162399_(); ++$$18) {
                    BlockPos $$19 = new BlockPos($$18, $$17, $$16);
                    BlockPos $$20 = $$19.m_121955_($$15);
                    BlockInWorld $$21 = new BlockInWorld($$10, $$19, false);
                    BlockState $$22 = $$21.m_61168_();
                    if (!p_136740_.test($$21)) continue;
                    BlockEntity $$23 = $$10.m_7702_($$19);
                    if ($$23 != null) {
                        CompoundTag $$24 = $$23.m_187482_();
                        $$12.add(new CloneBlockInfo($$20, $$22, $$24));
                        $$14.addLast($$19);
                        continue;
                    }
                    if ($$22.m_60804_($$10, $$19) || $$22.m_60838_($$10, $$19)) {
                        $$11.add(new CloneBlockInfo($$20, $$22, null));
                        $$14.addLast($$19);
                        continue;
                    }
                    $$13.add(new CloneBlockInfo($$20, $$22, null));
                    $$14.addFirst($$19);
                }
            }
        }
        if (p_136741_ == Mode.MOVE) {
            for (BlockPos $$25 : $$14) {
                BlockEntity $$26 = $$10.m_7702_($$25);
                Clearable.m_18908_($$26);
                $$10.m_7731_($$25, Blocks.f_50375_.m_49966_(), 2);
            }
            for (BlockPos $$27 : $$14) {
                $$10.m_7731_($$27, Blocks.f_50016_.m_49966_(), 3);
            }
        }
        ArrayList $$28 = Lists.newArrayList();
        $$28.addAll($$11);
        $$28.addAll($$12);
        $$28.addAll($$13);
        List $$29 = Lists.reverse((List)$$28);
        for (CloneBlockInfo $$30 : $$29) {
            BlockEntity $$31 = $$10.m_7702_($$30.f_136779_);
            Clearable.m_18908_($$31);
            $$10.m_7731_($$30.f_136779_, Blocks.f_50375_.m_49966_(), 2);
        }
        int $$32 = 0;
        for (CloneBlockInfo $$33 : $$28) {
            if (!$$10.m_7731_($$33.f_136779_, $$33.f_136780_, 2)) continue;
            ++$$32;
        }
        for (CloneBlockInfo $$34 : $$12) {
            BlockEntity $$35 = $$10.m_7702_($$34.f_136779_);
            if ($$34.f_136781_ != null && $$35 != null) {
                $$35.m_142466_($$34.f_136781_);
                $$35.m_6596_();
            }
            $$10.m_7731_($$34.f_136779_, $$34.f_136780_, 2);
        }
        for (CloneBlockInfo $$36 : $$29) {
            $$10.m_6289_($$36.f_136779_, $$36.f_136780_.m_60734_());
        }
        ((LevelTicks)$$10.m_183326_()).m_193242_($$6, $$15);
        if ($$32 == 0) {
            throw f_136725_.create();
        }
        p_136736_.m_81354_(Component.m_237110_("commands.clone.success", $$32), true);
        return $$32;
    }

    static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode FORCE = new Mode(true);
        public static final /* enum */ Mode MOVE = new Mode(true);
        public static final /* enum */ Mode NORMAL = new Mode(false);
        private final boolean f_136789_;
        private static final /* synthetic */ Mode[] $VALUES;

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        public static Mode valueOf(String p_136798_) {
            return Enum.valueOf(Mode.class, p_136798_);
        }

        private Mode(boolean p_136795_) {
            this.f_136789_ = p_136795_;
        }

        public boolean m_136796_() {
            return this.f_136789_;
        }

        private static /* synthetic */ Mode[] m_180042_() {
            return new Mode[]{FORCE, MOVE, NORMAL};
        }

        static {
            $VALUES = Mode.m_180042_();
        }
    }

    static class CloneBlockInfo {
        public final BlockPos f_136779_;
        public final BlockState f_136780_;
        @Nullable
        public final CompoundTag f_136781_;

        public CloneBlockInfo(BlockPos p_136783_, BlockState p_136784_, @Nullable CompoundTag p_136785_) {
            this.f_136779_ = p_136783_;
            this.f_136780_ = p_136784_;
            this.f_136781_ = p_136785_;
        }
    }
}

