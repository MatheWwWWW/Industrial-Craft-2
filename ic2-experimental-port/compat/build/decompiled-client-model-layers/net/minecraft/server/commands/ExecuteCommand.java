/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.Command
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.ResultConsumer
 *  com.mojang.brigadier.arguments.DoubleArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.suggestion.SuggestionProvider
 *  com.mojang.brigadier.tree.CommandNode
 *  com.mojang.brigadier.tree.LiteralCommandNode
 */
package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.ResultConsumer;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.OptionalInt;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.IntFunction;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.commands.arguments.ObjectiveArgument;
import net.minecraft.commands.arguments.RangeArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.ScoreHolderArgument;
import net.minecraft.commands.arguments.blocks.BlockPredicateArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.RotationArgument;
import net.minecraft.commands.arguments.coordinates.SwizzleArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.bossevents.CustomBossEvent;
import net.minecraft.server.commands.BossBarCommands;
import net.minecraft.server.commands.data.DataAccessor;
import net.minecraft.server.commands.data.DataCommands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.PredicateManager;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Score;

public class ExecuteCommand {
    private static final int f_180148_ = 32768;
    private static final Dynamic2CommandExceptionType f_137030_ = new Dynamic2CommandExceptionType((p_137129_, p_137130_) -> Component.m_237110_("commands.execute.blocks.toobig", p_137129_, p_137130_));
    private static final SimpleCommandExceptionType f_137031_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.execute.conditional.fail"));
    private static final DynamicCommandExceptionType f_137032_ = new DynamicCommandExceptionType(p_137127_ -> Component.m_237110_("commands.execute.conditional.fail_count", p_137127_));
    private static final BinaryOperator<ResultConsumer<CommandSourceStack>> f_137033_ = (p_137045_, p_137046_) -> (p_180160_, p_180161_, p_180162_) -> {
        p_137045_.onCommandComplete(p_180160_, p_180161_, p_180162_);
        p_137046_.onCommandComplete(p_180160_, p_180161_, p_180162_);
    };
    private static final SuggestionProvider<CommandSourceStack> f_137034_ = (p_137062_, p_137063_) -> {
        PredicateManager $$2 = ((CommandSourceStack)p_137062_.getSource()).m_81377_().m_129899_();
        return SharedSuggestionProvider.m_82926_($$2.m_79232_(), p_137063_);
    };

    public static void m_214434_(CommandDispatcher<CommandSourceStack> p_214435_, CommandBuildContext p_214436_) {
        LiteralCommandNode $$2 = p_214435_.register((LiteralArgumentBuilder)Commands.m_82127_("execute").requires(p_137197_ -> p_137197_.m_6761_(2)));
        p_214435_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("execute").requires(p_137103_ -> p_137103_.m_6761_(2))).then(Commands.m_82127_("run").redirect((CommandNode)p_214435_.getRoot()))).then(ExecuteCommand.m_214437_((CommandNode<CommandSourceStack>)$$2, Commands.m_82127_("if"), true, p_214436_))).then(ExecuteCommand.m_214437_((CommandNode<CommandSourceStack>)$$2, Commands.m_82127_("unless"), false, p_214436_))).then(Commands.m_82127_("as").then(Commands.m_82129_("targets", EntityArgument.m_91460_()).fork((CommandNode)$$2, p_137299_ -> {
            ArrayList $$1 = Lists.newArrayList();
            for (Entity entity : EntityArgument.m_91467_((CommandContext<CommandSourceStack>)p_137299_, "targets")) {
                $$1.add(((CommandSourceStack)p_137299_.getSource()).m_81329_(entity));
            }
            return $$1;
        })))).then(Commands.m_82127_("at").then(Commands.m_82129_("targets", EntityArgument.m_91460_()).fork((CommandNode)$$2, p_137297_ -> {
            ArrayList $$1 = Lists.newArrayList();
            for (Entity entity : EntityArgument.m_91467_((CommandContext<CommandSourceStack>)p_137297_, "targets")) {
                $$1.add(((CommandSourceStack)p_137297_.getSource()).m_81327_((ServerLevel)entity.f_19853_).m_81348_(entity.m_20182_()).m_81346_(entity.m_20155_()));
            }
            return $$1;
        })))).then(((LiteralArgumentBuilder)Commands.m_82127_("store").then(ExecuteCommand.m_137093_((LiteralCommandNode<CommandSourceStack>)$$2, Commands.m_82127_("result"), true))).then(ExecuteCommand.m_137093_((LiteralCommandNode<CommandSourceStack>)$$2, Commands.m_82127_("success"), false)))).then(((LiteralArgumentBuilder)Commands.m_82127_("positioned").then(Commands.m_82129_("pos", Vec3Argument.m_120841_()).redirect((CommandNode)$$2, p_137295_ -> ((CommandSourceStack)p_137295_.getSource()).m_81348_(Vec3Argument.m_120844_((CommandContext<CommandSourceStack>)p_137295_, "pos")).m_81350_(EntityAnchorArgument.Anchor.FEET)))).then(Commands.m_82127_("as").then(Commands.m_82129_("targets", EntityArgument.m_91460_()).fork((CommandNode)$$2, p_137293_ -> {
            ArrayList $$1 = Lists.newArrayList();
            for (Entity entity : EntityArgument.m_91467_((CommandContext<CommandSourceStack>)p_137293_, "targets")) {
                $$1.add(((CommandSourceStack)p_137293_.getSource()).m_81348_(entity.m_20182_()));
            }
            return $$1;
        }))))).then(((LiteralArgumentBuilder)Commands.m_82127_("rotated").then(Commands.m_82129_("rot", RotationArgument.m_120479_()).redirect((CommandNode)$$2, p_137291_ -> ((CommandSourceStack)p_137291_.getSource()).m_81346_(RotationArgument.m_120482_((CommandContext<CommandSourceStack>)p_137291_, "rot").m_6970_((CommandSourceStack)p_137291_.getSource()))))).then(Commands.m_82127_("as").then(Commands.m_82129_("targets", EntityArgument.m_91460_()).fork((CommandNode)$$2, p_137289_ -> {
            ArrayList $$1 = Lists.newArrayList();
            for (Entity entity : EntityArgument.m_91467_((CommandContext<CommandSourceStack>)p_137289_, "targets")) {
                $$1.add(((CommandSourceStack)p_137289_.getSource()).m_81346_(entity.m_20155_()));
            }
            return $$1;
        }))))).then(((LiteralArgumentBuilder)Commands.m_82127_("facing").then(Commands.m_82127_("entity").then(Commands.m_82129_("targets", EntityArgument.m_91460_()).then(Commands.m_82129_("anchor", EntityAnchorArgument.m_90350_()).fork((CommandNode)$$2, p_137287_ -> {
            ArrayList $$1 = Lists.newArrayList();
            EntityAnchorArgument.Anchor $$2 = EntityAnchorArgument.m_90353_((CommandContext<CommandSourceStack>)p_137287_, "anchor");
            for (Entity entity : EntityArgument.m_91467_((CommandContext<CommandSourceStack>)p_137287_, "targets")) {
                $$1.add(((CommandSourceStack)p_137287_.getSource()).m_81331_(entity, $$2));
            }
            return $$1;
        }))))).then(Commands.m_82129_("pos", Vec3Argument.m_120841_()).redirect((CommandNode)$$2, p_137285_ -> ((CommandSourceStack)p_137285_.getSource()).m_81364_(Vec3Argument.m_120844_((CommandContext<CommandSourceStack>)p_137285_, "pos")))))).then(Commands.m_82127_("align").then(Commands.m_82129_("axes", SwizzleArgument.m_120807_()).redirect((CommandNode)$$2, p_137283_ -> ((CommandSourceStack)p_137283_.getSource()).m_81348_(((CommandSourceStack)p_137283_.getSource()).m_81371_().m_82517_(SwizzleArgument.m_120810_((CommandContext<CommandSourceStack>)p_137283_, "axes"))))))).then(Commands.m_82127_("anchored").then(Commands.m_82129_("anchor", EntityAnchorArgument.m_90350_()).redirect((CommandNode)$$2, p_137281_ -> ((CommandSourceStack)p_137281_.getSource()).m_81350_(EntityAnchorArgument.m_90353_((CommandContext<CommandSourceStack>)p_137281_, "anchor")))))).then(Commands.m_82127_("in").then(Commands.m_82129_("dimension", DimensionArgument.m_88805_()).redirect((CommandNode)$$2, p_137279_ -> ((CommandSourceStack)p_137279_.getSource()).m_81327_(DimensionArgument.m_88808_((CommandContext<CommandSourceStack>)p_137279_, "dimension"))))));
    }

    private static ArgumentBuilder<CommandSourceStack, ?> m_137093_(LiteralCommandNode<CommandSourceStack> p_137094_, LiteralArgumentBuilder<CommandSourceStack> p_137095_, boolean p_137096_) {
        p_137095_.then(Commands.m_82127_("score").then(Commands.m_82129_("targets", ScoreHolderArgument.m_108239_()).suggests(ScoreHolderArgument.f_108210_).then(Commands.m_82129_("objective", ObjectiveArgument.m_101957_()).redirect(p_137094_, p_137271_ -> ExecuteCommand.m_137107_((CommandSourceStack)p_137271_.getSource(), ScoreHolderArgument.m_108246_((CommandContext<CommandSourceStack>)p_137271_, "targets"), ObjectiveArgument.m_101960_((CommandContext<CommandSourceStack>)p_137271_, "objective"), p_137096_)))));
        p_137095_.then(Commands.m_82127_("bossbar").then(((RequiredArgumentBuilder)Commands.m_82129_("id", ResourceLocationArgument.m_106984_()).suggests(BossBarCommands.f_136570_).then(Commands.m_82127_("value").redirect(p_137094_, p_137259_ -> ExecuteCommand.m_137112_((CommandSourceStack)p_137259_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_137259_), true, p_137096_)))).then(Commands.m_82127_("max").redirect(p_137094_, p_137247_ -> ExecuteCommand.m_137112_((CommandSourceStack)p_137247_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_137247_), false, p_137096_)))));
        for (DataCommands.DataProvider $$3 : DataCommands.f_139350_) {
            $$3.m_7621_((ArgumentBuilder<CommandSourceStack, ?>)p_137095_, p_137101_ -> p_137101_.then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("path", NbtPathArgument.m_99487_()).then(Commands.m_82127_("int").then(Commands.m_82129_("scale", DoubleArgumentType.doubleArg()).redirect((CommandNode)p_137094_, p_180216_ -> ExecuteCommand.m_137117_((CommandSourceStack)p_180216_.getSource(), $$3.m_7018_((CommandContext<CommandSourceStack>)p_180216_), NbtPathArgument.m_99498_((CommandContext<CommandSourceStack>)p_180216_, "path"), p_180219_ -> IntTag.m_128679_((int)((double)p_180219_ * DoubleArgumentType.getDouble((CommandContext)p_180216_, (String)"scale"))), p_137096_))))).then(Commands.m_82127_("float").then(Commands.m_82129_("scale", DoubleArgumentType.doubleArg()).redirect((CommandNode)p_137094_, p_180209_ -> ExecuteCommand.m_137117_((CommandSourceStack)p_180209_.getSource(), $$3.m_7018_((CommandContext<CommandSourceStack>)p_180209_), NbtPathArgument.m_99498_((CommandContext<CommandSourceStack>)p_180209_, "path"), p_180212_ -> FloatTag.m_128566_((float)((double)p_180212_ * DoubleArgumentType.getDouble((CommandContext)p_180209_, (String)"scale"))), p_137096_))))).then(Commands.m_82127_("short").then(Commands.m_82129_("scale", DoubleArgumentType.doubleArg()).redirect((CommandNode)p_137094_, p_180199_ -> ExecuteCommand.m_137117_((CommandSourceStack)p_180199_.getSource(), $$3.m_7018_((CommandContext<CommandSourceStack>)p_180199_), NbtPathArgument.m_99498_((CommandContext<CommandSourceStack>)p_180199_, "path"), p_180202_ -> ShortTag.m_129258_((short)((double)p_180202_ * DoubleArgumentType.getDouble((CommandContext)p_180199_, (String)"scale"))), p_137096_))))).then(Commands.m_82127_("long").then(Commands.m_82129_("scale", DoubleArgumentType.doubleArg()).redirect((CommandNode)p_137094_, p_180189_ -> ExecuteCommand.m_137117_((CommandSourceStack)p_180189_.getSource(), $$3.m_7018_((CommandContext<CommandSourceStack>)p_180189_), NbtPathArgument.m_99498_((CommandContext<CommandSourceStack>)p_180189_, "path"), p_180192_ -> LongTag.m_128882_((long)((double)p_180192_ * DoubleArgumentType.getDouble((CommandContext)p_180189_, (String)"scale"))), p_137096_))))).then(Commands.m_82127_("double").then(Commands.m_82129_("scale", DoubleArgumentType.doubleArg()).redirect((CommandNode)p_137094_, p_180179_ -> ExecuteCommand.m_137117_((CommandSourceStack)p_180179_.getSource(), $$3.m_7018_((CommandContext<CommandSourceStack>)p_180179_), NbtPathArgument.m_99498_((CommandContext<CommandSourceStack>)p_180179_, "path"), p_180182_ -> DoubleTag.m_128500_((double)p_180182_ * DoubleArgumentType.getDouble((CommandContext)p_180179_, (String)"scale")), p_137096_))))).then(Commands.m_82127_("byte").then(Commands.m_82129_("scale", DoubleArgumentType.doubleArg()).redirect((CommandNode)p_137094_, p_180156_ -> ExecuteCommand.m_137117_((CommandSourceStack)p_180156_.getSource(), $$3.m_7018_((CommandContext<CommandSourceStack>)p_180156_), NbtPathArgument.m_99498_((CommandContext<CommandSourceStack>)p_180156_, "path"), p_180165_ -> ByteTag.m_128266_((byte)((double)p_180165_ * DoubleArgumentType.getDouble((CommandContext)p_180156_, (String)"scale"))), p_137096_))))));
        }
        return p_137095_;
    }

    private static CommandSourceStack m_137107_(CommandSourceStack p_137108_, Collection<String> p_137109_, Objective p_137110_, boolean p_137111_) {
        ServerScoreboard $$4 = p_137108_.m_81377_().m_129896_();
        return p_137108_.m_81336_((ResultConsumer<CommandSourceStack>)((ResultConsumer)(p_137136_, p_137137_, p_137138_) -> {
            for (String $$7 : p_137109_) {
                Score $$8 = $$4.m_83471_($$7, p_137110_);
                int $$9 = p_137111_ ? p_137138_ : (p_137137_ ? 1 : 0);
                $$8.m_83402_($$9);
            }
        }), f_137033_);
    }

    private static CommandSourceStack m_137112_(CommandSourceStack p_137113_, CustomBossEvent p_137114_, boolean p_137115_, boolean p_137116_) {
        return p_137113_.m_81336_((ResultConsumer<CommandSourceStack>)((ResultConsumer)(p_137185_, p_137186_, p_137187_) -> {
            int $$6;
            int n = p_137116_ ? p_137187_ : ($$6 = p_137186_ ? 1 : 0);
            if (p_137115_) {
                p_137114_.m_136264_($$6);
            } else {
                p_137114_.m_136278_($$6);
            }
        }), f_137033_);
    }

    private static CommandSourceStack m_137117_(CommandSourceStack p_137118_, DataAccessor p_137119_, NbtPathArgument.NbtPath p_137120_, IntFunction<Tag> p_137121_, boolean p_137122_) {
        return p_137118_.m_81336_((ResultConsumer<CommandSourceStack>)((ResultConsumer)(p_137153_, p_137154_, p_137155_) -> {
            try {
                CompoundTag $$7 = p_137119_.m_6184_();
                int $$8 = p_137122_ ? p_137155_ : (p_137154_ ? 1 : 0);
                p_137120_.m_99645_($$7, () -> (Tag)p_137121_.apply($$8));
                p_137119_.m_7603_($$7);
            }
            catch (CommandSyntaxException commandSyntaxException) {
                // empty catch block
            }
        }), f_137033_);
    }

    private static ArgumentBuilder<CommandSourceStack, ?> m_214437_(CommandNode<CommandSourceStack> p_214438_, LiteralArgumentBuilder<CommandSourceStack> p_214439_, boolean p_214440_, CommandBuildContext p_214441_) {
        ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)p_214439_.then(Commands.m_82127_("block").then(Commands.m_82129_("pos", BlockPosArgument.m_118239_()).then(ExecuteCommand.m_137074_(p_214438_, Commands.m_82129_("block", BlockPredicateArgument.m_234627_(p_214441_)), p_214440_, p_137277_ -> BlockPredicateArgument.m_115573_((CommandContext<CommandSourceStack>)p_137277_, "block").test(new BlockInWorld(((CommandSourceStack)p_137277_.getSource()).m_81372_(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_137277_, "pos"), true))))))).then(Commands.m_82127_("score").then(Commands.m_82129_("target", ScoreHolderArgument.m_108217_()).suggests(ScoreHolderArgument.f_108210_).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("targetObjective", ObjectiveArgument.m_101957_()).then(Commands.m_82127_("=").then(Commands.m_82129_("source", ScoreHolderArgument.m_108217_()).suggests(ScoreHolderArgument.f_108210_).then(ExecuteCommand.m_137074_(p_214438_, Commands.m_82129_("sourceObjective", ObjectiveArgument.m_101957_()), p_214440_, p_137275_ -> ExecuteCommand.m_137064_((CommandContext<CommandSourceStack>)p_137275_, Integer::equals)))))).then(Commands.m_82127_("<").then(Commands.m_82129_("source", ScoreHolderArgument.m_108217_()).suggests(ScoreHolderArgument.f_108210_).then(ExecuteCommand.m_137074_(p_214438_, Commands.m_82129_("sourceObjective", ObjectiveArgument.m_101957_()), p_214440_, p_137273_ -> ExecuteCommand.m_137064_((CommandContext<CommandSourceStack>)p_137273_, (p_180204_, p_180205_) -> p_180204_ < p_180205_)))))).then(Commands.m_82127_("<=").then(Commands.m_82129_("source", ScoreHolderArgument.m_108217_()).suggests(ScoreHolderArgument.f_108210_).then(ExecuteCommand.m_137074_(p_214438_, Commands.m_82129_("sourceObjective", ObjectiveArgument.m_101957_()), p_214440_, p_137261_ -> ExecuteCommand.m_137064_((CommandContext<CommandSourceStack>)p_137261_, (p_180194_, p_180195_) -> p_180194_ <= p_180195_)))))).then(Commands.m_82127_(">").then(Commands.m_82129_("source", ScoreHolderArgument.m_108217_()).suggests(ScoreHolderArgument.f_108210_).then(ExecuteCommand.m_137074_(p_214438_, Commands.m_82129_("sourceObjective", ObjectiveArgument.m_101957_()), p_214440_, p_137249_ -> ExecuteCommand.m_137064_((CommandContext<CommandSourceStack>)p_137249_, (p_180184_, p_180185_) -> p_180184_ > p_180185_)))))).then(Commands.m_82127_(">=").then(Commands.m_82129_("source", ScoreHolderArgument.m_108217_()).suggests(ScoreHolderArgument.f_108210_).then(ExecuteCommand.m_137074_(p_214438_, Commands.m_82129_("sourceObjective", ObjectiveArgument.m_101957_()), p_214440_, p_137234_ -> ExecuteCommand.m_137064_((CommandContext<CommandSourceStack>)p_137234_, (p_180167_, p_180168_) -> p_180167_ >= p_180168_)))))).then(Commands.m_82127_("matches").then(ExecuteCommand.m_137074_(p_214438_, Commands.m_82129_("range", RangeArgument.m_105404_()), p_214440_, p_137216_ -> ExecuteCommand.m_137058_((CommandContext<CommandSourceStack>)p_137216_, RangeArgument.Ints.m_105419_((CommandContext<CommandSourceStack>)p_137216_, "range"))))))))).then(Commands.m_82127_("blocks").then(Commands.m_82129_("start", BlockPosArgument.m_118239_()).then(Commands.m_82129_("end", BlockPosArgument.m_118239_()).then(((RequiredArgumentBuilder)Commands.m_82129_("destination", BlockPosArgument.m_118239_()).then(ExecuteCommand.m_137079_(p_214438_, Commands.m_82127_("all"), p_214440_, false))).then(ExecuteCommand.m_137079_(p_214438_, Commands.m_82127_("masked"), p_214440_, true))))))).then(Commands.m_82127_("entity").then(((RequiredArgumentBuilder)Commands.m_82129_("entities", EntityArgument.m_91460_()).fork(p_214438_, p_137232_ -> ExecuteCommand.m_137070_((CommandContext<CommandSourceStack>)p_137232_, p_214440_, !EntityArgument.m_91467_((CommandContext<CommandSourceStack>)p_137232_, "entities").isEmpty()))).executes(ExecuteCommand.m_137166_(p_214440_, p_137189_ -> EntityArgument.m_91467_((CommandContext<CommandSourceStack>)p_137189_, "entities").size()))))).then(Commands.m_82127_("predicate").then(ExecuteCommand.m_137074_(p_214438_, Commands.m_82129_("predicate", ResourceLocationArgument.m_106984_()).suggests(f_137034_), p_214440_, p_137054_ -> ExecuteCommand.m_137104_((CommandSourceStack)p_137054_.getSource(), ResourceLocationArgument.m_107001_((CommandContext<CommandSourceStack>)p_137054_, "predicate")))));
        for (DataCommands.DataProvider $$4 : DataCommands.f_139351_) {
            p_214439_.then($$4.m_7621_((ArgumentBuilder<CommandSourceStack, ?>)Commands.m_82127_("data"), p_137092_ -> p_137092_.then(((RequiredArgumentBuilder)Commands.m_82129_("path", NbtPathArgument.m_99487_()).fork(p_214438_, p_180175_ -> ExecuteCommand.m_137070_((CommandContext<CommandSourceStack>)p_180175_, p_214440_, ExecuteCommand.m_137145_($$4.m_7018_((CommandContext<CommandSourceStack>)p_180175_), NbtPathArgument.m_99498_((CommandContext<CommandSourceStack>)p_180175_, "path")) > 0))).executes(ExecuteCommand.m_137166_(p_214440_, p_180152_ -> ExecuteCommand.m_137145_($$4.m_7018_((CommandContext<CommandSourceStack>)p_180152_), NbtPathArgument.m_99498_((CommandContext<CommandSourceStack>)p_180152_, "path")))))));
        }
        return p_214439_;
    }

    private static Command<CommandSourceStack> m_137166_(boolean p_137167_, CommandNumericPredicate p_137168_) {
        if (p_137167_) {
            return p_137203_ -> {
                int $$2 = p_137168_.m_137300_((CommandContext<CommandSourceStack>)p_137203_);
                if ($$2 > 0) {
                    ((CommandSourceStack)p_137203_.getSource()).m_81354_(Component.m_237110_("commands.execute.conditional.pass_count", $$2), false);
                    return $$2;
                }
                throw f_137031_.create();
            };
        }
        return p_137144_ -> {
            int $$2 = p_137168_.m_137300_((CommandContext<CommandSourceStack>)p_137144_);
            if ($$2 == 0) {
                ((CommandSourceStack)p_137144_.getSource()).m_81354_(Component.m_237115_("commands.execute.conditional.pass"), false);
                return 1;
            }
            throw f_137032_.create((Object)$$2);
        };
    }

    private static int m_137145_(DataAccessor p_137146_, NbtPathArgument.NbtPath p_137147_) throws CommandSyntaxException {
        return p_137147_.m_99643_(p_137146_.m_6184_());
    }

    private static boolean m_137064_(CommandContext<CommandSourceStack> p_137065_, BiPredicate<Integer, Integer> p_137066_) throws CommandSyntaxException {
        String $$2 = ScoreHolderArgument.m_108223_(p_137065_, "target");
        Objective $$3 = ObjectiveArgument.m_101960_(p_137065_, "targetObjective");
        String $$4 = ScoreHolderArgument.m_108223_(p_137065_, "source");
        Objective $$5 = ObjectiveArgument.m_101960_(p_137065_, "sourceObjective");
        ServerScoreboard $$6 = ((CommandSourceStack)p_137065_.getSource()).m_81377_().m_129896_();
        if (!$$6.m_83461_($$2, $$3) || !$$6.m_83461_($$4, $$5)) {
            return false;
        }
        Score $$7 = $$6.m_83471_($$2, $$3);
        Score $$8 = $$6.m_83471_($$4, $$5);
        return p_137066_.test($$7.m_83400_(), $$8.m_83400_());
    }

    private static boolean m_137058_(CommandContext<CommandSourceStack> p_137059_, MinMaxBounds.Ints p_137060_) throws CommandSyntaxException {
        String $$2 = ScoreHolderArgument.m_108223_(p_137059_, "target");
        Objective $$3 = ObjectiveArgument.m_101960_(p_137059_, "targetObjective");
        ServerScoreboard $$4 = ((CommandSourceStack)p_137059_.getSource()).m_81377_().m_129896_();
        if (!$$4.m_83461_($$2, $$3)) {
            return false;
        }
        return p_137060_.m_55390_($$4.m_83471_($$2, $$3).m_83400_());
    }

    private static boolean m_137104_(CommandSourceStack p_137105_, LootItemCondition p_137106_) {
        ServerLevel $$2 = p_137105_.m_81372_();
        LootContext.Builder $$3 = new LootContext.Builder($$2).m_78972_(LootContextParams.f_81460_, p_137105_.m_81371_()).m_78984_(LootContextParams.f_81455_, p_137105_.m_81373_());
        return p_137106_.test($$3.m_78975_(LootContextParamSets.f_81412_));
    }

    private static Collection<CommandSourceStack> m_137070_(CommandContext<CommandSourceStack> p_137071_, boolean p_137072_, boolean p_137073_) {
        if (p_137073_ == p_137072_) {
            return Collections.singleton((CommandSourceStack)p_137071_.getSource());
        }
        return Collections.emptyList();
    }

    private static ArgumentBuilder<CommandSourceStack, ?> m_137074_(CommandNode<CommandSourceStack> p_137075_, ArgumentBuilder<CommandSourceStack, ?> p_137076_, boolean p_137077_, CommandPredicate p_137078_) {
        return p_137076_.fork(p_137075_, p_137214_ -> ExecuteCommand.m_137070_((CommandContext<CommandSourceStack>)p_137214_, p_137077_, p_137078_.m_137302_((CommandContext<CommandSourceStack>)p_137214_))).executes(p_137172_ -> {
            if (p_137077_ == p_137078_.m_137302_((CommandContext<CommandSourceStack>)p_137172_)) {
                ((CommandSourceStack)p_137172_.getSource()).m_81354_(Component.m_237115_("commands.execute.conditional.pass"), false);
                return 1;
            }
            throw f_137031_.create();
        });
    }

    private static ArgumentBuilder<CommandSourceStack, ?> m_137079_(CommandNode<CommandSourceStack> p_137080_, ArgumentBuilder<CommandSourceStack, ?> p_137081_, boolean p_137082_, boolean p_137083_) {
        return p_137081_.fork(p_137080_, p_137180_ -> ExecuteCommand.m_137070_((CommandContext<CommandSourceStack>)p_137180_, p_137082_, ExecuteCommand.m_137220_((CommandContext<CommandSourceStack>)p_137180_, p_137083_).isPresent())).executes(p_137082_ ? p_137210_ -> ExecuteCommand.m_137067_((CommandContext<CommandSourceStack>)p_137210_, p_137083_) : p_137165_ -> ExecuteCommand.m_137193_((CommandContext<CommandSourceStack>)p_137165_, p_137083_));
    }

    private static int m_137067_(CommandContext<CommandSourceStack> p_137068_, boolean p_137069_) throws CommandSyntaxException {
        OptionalInt $$2 = ExecuteCommand.m_137220_(p_137068_, p_137069_);
        if ($$2.isPresent()) {
            ((CommandSourceStack)p_137068_.getSource()).m_81354_(Component.m_237110_("commands.execute.conditional.pass_count", $$2.getAsInt()), false);
            return $$2.getAsInt();
        }
        throw f_137031_.create();
    }

    private static int m_137193_(CommandContext<CommandSourceStack> p_137194_, boolean p_137195_) throws CommandSyntaxException {
        OptionalInt $$2 = ExecuteCommand.m_137220_(p_137194_, p_137195_);
        if ($$2.isPresent()) {
            throw f_137032_.create((Object)$$2.getAsInt());
        }
        ((CommandSourceStack)p_137194_.getSource()).m_81354_(Component.m_237115_("commands.execute.conditional.pass"), false);
        return 1;
    }

    private static OptionalInt m_137220_(CommandContext<CommandSourceStack> p_137221_, boolean p_137222_) throws CommandSyntaxException {
        return ExecuteCommand.m_137036_(((CommandSourceStack)p_137221_.getSource()).m_81372_(), BlockPosArgument.m_118242_(p_137221_, "start"), BlockPosArgument.m_118242_(p_137221_, "end"), BlockPosArgument.m_118242_(p_137221_, "destination"), p_137222_);
    }

    private static OptionalInt m_137036_(ServerLevel p_137037_, BlockPos p_137038_, BlockPos p_137039_, BlockPos p_137040_, boolean p_137041_) throws CommandSyntaxException {
        BoundingBox $$5 = BoundingBox.m_162375_(p_137038_, p_137039_);
        BoundingBox $$6 = BoundingBox.m_162375_(p_137040_, p_137040_.m_121955_($$5.m_71053_()));
        BlockPos $$7 = new BlockPos($$6.m_162395_() - $$5.m_162395_(), $$6.m_162396_() - $$5.m_162396_(), $$6.m_162398_() - $$5.m_162398_());
        int $$8 = $$5.m_71056_() * $$5.m_71057_() * $$5.m_71058_();
        if ($$8 > 32768) {
            throw f_137030_.create((Object)32768, (Object)$$8);
        }
        int $$9 = 0;
        for (int $$10 = $$5.m_162398_(); $$10 <= $$5.m_162401_(); ++$$10) {
            for (int $$11 = $$5.m_162396_(); $$11 <= $$5.m_162400_(); ++$$11) {
                for (int $$12 = $$5.m_162395_(); $$12 <= $$5.m_162399_(); ++$$12) {
                    BlockPos $$13 = new BlockPos($$12, $$11, $$10);
                    BlockPos $$14 = $$13.m_121955_($$7);
                    BlockState $$15 = p_137037_.m_8055_($$13);
                    if (p_137041_ && $$15.m_60713_(Blocks.f_50016_)) continue;
                    if ($$15 != p_137037_.m_8055_($$14)) {
                        return OptionalInt.empty();
                    }
                    BlockEntity $$16 = p_137037_.m_7702_($$13);
                    BlockEntity $$17 = p_137037_.m_7702_($$14);
                    if ($$16 != null) {
                        CompoundTag $$19;
                        if ($$17 == null) {
                            return OptionalInt.empty();
                        }
                        if ($$17.m_58903_() != $$16.m_58903_()) {
                            return OptionalInt.empty();
                        }
                        CompoundTag $$18 = $$16.m_187482_();
                        if (!$$18.equals($$19 = $$17.m_187482_())) {
                            return OptionalInt.empty();
                        }
                    }
                    ++$$9;
                }
            }
        }
        return OptionalInt.of($$9);
    }

    @FunctionalInterface
    static interface CommandPredicate {
        public boolean m_137302_(CommandContext<CommandSourceStack> var1) throws CommandSyntaxException;
    }

    @FunctionalInterface
    static interface CommandNumericPredicate {
        public int m_137300_(CommandContext<CommandSourceStack> var1) throws CommandSyntaxException;
    }
}

