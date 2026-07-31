/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 */
package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.ObjectiveArgument;
import net.minecraft.commands.arguments.ObjectiveCriteriaArgument;
import net.minecraft.commands.arguments.OperationArgument;
import net.minecraft.commands.arguments.ScoreHolderArgument;
import net.minecraft.commands.arguments.ScoreboardSlotArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class ScoreboardCommand {
    private static final SimpleCommandExceptionType f_138460_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.scoreboard.objectives.add.duplicate"));
    private static final SimpleCommandExceptionType f_138461_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.scoreboard.objectives.display.alreadyEmpty"));
    private static final SimpleCommandExceptionType f_138462_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.scoreboard.objectives.display.alreadySet"));
    private static final SimpleCommandExceptionType f_138463_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.scoreboard.players.enable.failed"));
    private static final SimpleCommandExceptionType f_138464_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.scoreboard.players.enable.invalid"));
    private static final Dynamic2CommandExceptionType f_138465_ = new Dynamic2CommandExceptionType((p_138534_, p_138535_) -> Component.m_237110_("commands.scoreboard.players.get.null", p_138534_, p_138535_));

    public static void m_138468_(CommandDispatcher<CommandSourceStack> p_138469_) {
        p_138469_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("scoreboard").requires(p_138552_ -> p_138552_.m_6761_(2))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("objectives").then(Commands.m_82127_("list").executes(p_138585_ -> ScoreboardCommand.m_138538_((CommandSourceStack)p_138585_.getSource())))).then(Commands.m_82127_("add").then(Commands.m_82129_("objective", StringArgumentType.word()).then(((RequiredArgumentBuilder)Commands.m_82129_("criteria", ObjectiveCriteriaArgument.m_102555_()).executes(p_138583_ -> ScoreboardCommand.m_138502_((CommandSourceStack)p_138583_.getSource(), StringArgumentType.getString((CommandContext)p_138583_, (String)"objective"), ObjectiveCriteriaArgument.m_102565_((CommandContext<CommandSourceStack>)p_138583_, "criteria"), Component.m_237113_(StringArgumentType.getString((CommandContext)p_138583_, (String)"objective"))))).then(Commands.m_82129_("displayName", ComponentArgument.m_87114_()).executes(p_138581_ -> ScoreboardCommand.m_138502_((CommandSourceStack)p_138581_.getSource(), StringArgumentType.getString((CommandContext)p_138581_, (String)"objective"), ObjectiveCriteriaArgument.m_102565_((CommandContext<CommandSourceStack>)p_138581_, "criteria"), ComponentArgument.m_87117_((CommandContext<CommandSourceStack>)p_138581_, "displayName")))))))).then(Commands.m_82127_("modify").then(((RequiredArgumentBuilder)Commands.m_82129_("objective", ObjectiveArgument.m_101957_()).then(Commands.m_82127_("displayname").then(Commands.m_82129_("displayName", ComponentArgument.m_87114_()).executes(p_138579_ -> ScoreboardCommand.m_138491_((CommandSourceStack)p_138579_.getSource(), ObjectiveArgument.m_101960_((CommandContext<CommandSourceStack>)p_138579_, "objective"), ComponentArgument.m_87117_((CommandContext<CommandSourceStack>)p_138579_, "displayName")))))).then(ScoreboardCommand.m_138467_())))).then(Commands.m_82127_("remove").then(Commands.m_82129_("objective", ObjectiveArgument.m_101957_()).executes(p_138577_ -> ScoreboardCommand.m_138484_((CommandSourceStack)p_138577_.getSource(), ObjectiveArgument.m_101960_((CommandContext<CommandSourceStack>)p_138577_, "objective")))))).then(Commands.m_82127_("setdisplay").then(((RequiredArgumentBuilder)Commands.m_82129_("slot", ScoreboardSlotArgument.m_109196_()).executes(p_138575_ -> ScoreboardCommand.m_138477_((CommandSourceStack)p_138575_.getSource(), ScoreboardSlotArgument.m_109199_((CommandContext<CommandSourceStack>)p_138575_, "slot")))).then(Commands.m_82129_("objective", ObjectiveArgument.m_101957_()).executes(p_138573_ -> ScoreboardCommand.m_138480_((CommandSourceStack)p_138573_.getSource(), ScoreboardSlotArgument.m_109199_((CommandContext<CommandSourceStack>)p_138573_, "slot"), ObjectiveArgument.m_101960_((CommandContext<CommandSourceStack>)p_138573_, "objective")))))))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("players").then(((LiteralArgumentBuilder)Commands.m_82127_("list").executes(p_138571_ -> ScoreboardCommand.m_138475_((CommandSourceStack)p_138571_.getSource()))).then(Commands.m_82129_("target", ScoreHolderArgument.m_108217_()).suggests(ScoreHolderArgument.f_108210_).executes(p_138569_ -> ScoreboardCommand.m_138495_((CommandSourceStack)p_138569_.getSource(), ScoreHolderArgument.m_108223_((CommandContext<CommandSourceStack>)p_138569_, "target")))))).then(Commands.m_82127_("set").then(Commands.m_82129_("targets", ScoreHolderArgument.m_108239_()).suggests(ScoreHolderArgument.f_108210_).then(Commands.m_82129_("objective", ObjectiveArgument.m_101957_()).then(Commands.m_82129_("score", IntegerArgumentType.integer()).executes(p_138567_ -> ScoreboardCommand.m_138518_((CommandSourceStack)p_138567_.getSource(), ScoreHolderArgument.m_108246_((CommandContext<CommandSourceStack>)p_138567_, "targets"), ObjectiveArgument.m_101965_((CommandContext<CommandSourceStack>)p_138567_, "objective"), IntegerArgumentType.getInteger((CommandContext)p_138567_, (String)"score")))))))).then(Commands.m_82127_("get").then(Commands.m_82129_("target", ScoreHolderArgument.m_108217_()).suggests(ScoreHolderArgument.f_108210_).then(Commands.m_82129_("objective", ObjectiveArgument.m_101957_()).executes(p_138565_ -> ScoreboardCommand.m_138498_((CommandSourceStack)p_138565_.getSource(), ScoreHolderArgument.m_108223_((CommandContext<CommandSourceStack>)p_138565_, "target"), ObjectiveArgument.m_101960_((CommandContext<CommandSourceStack>)p_138565_, "objective"))))))).then(Commands.m_82127_("add").then(Commands.m_82129_("targets", ScoreHolderArgument.m_108239_()).suggests(ScoreHolderArgument.f_108210_).then(Commands.m_82129_("objective", ObjectiveArgument.m_101957_()).then(Commands.m_82129_("score", IntegerArgumentType.integer((int)0)).executes(p_138563_ -> ScoreboardCommand.m_138544_((CommandSourceStack)p_138563_.getSource(), ScoreHolderArgument.m_108246_((CommandContext<CommandSourceStack>)p_138563_, "targets"), ObjectiveArgument.m_101965_((CommandContext<CommandSourceStack>)p_138563_, "objective"), IntegerArgumentType.getInteger((CommandContext)p_138563_, (String)"score")))))))).then(Commands.m_82127_("remove").then(Commands.m_82129_("targets", ScoreHolderArgument.m_108239_()).suggests(ScoreHolderArgument.f_108210_).then(Commands.m_82129_("objective", ObjectiveArgument.m_101957_()).then(Commands.m_82129_("score", IntegerArgumentType.integer((int)0)).executes(p_138561_ -> ScoreboardCommand.m_138553_((CommandSourceStack)p_138561_.getSource(), ScoreHolderArgument.m_108246_((CommandContext<CommandSourceStack>)p_138561_, "targets"), ObjectiveArgument.m_101965_((CommandContext<CommandSourceStack>)p_138561_, "objective"), IntegerArgumentType.getInteger((CommandContext)p_138561_, (String)"score")))))))).then(Commands.m_82127_("reset").then(((RequiredArgumentBuilder)Commands.m_82129_("targets", ScoreHolderArgument.m_108239_()).suggests(ScoreHolderArgument.f_108210_).executes(p_138559_ -> ScoreboardCommand.m_138507_((CommandSourceStack)p_138559_.getSource(), ScoreHolderArgument.m_108246_((CommandContext<CommandSourceStack>)p_138559_, "targets")))).then(Commands.m_82129_("objective", ObjectiveArgument.m_101957_()).executes(p_138550_ -> ScoreboardCommand.m_138540_((CommandSourceStack)p_138550_.getSource(), ScoreHolderArgument.m_108246_((CommandContext<CommandSourceStack>)p_138550_, "targets"), ObjectiveArgument.m_101960_((CommandContext<CommandSourceStack>)p_138550_, "objective"))))))).then(Commands.m_82127_("enable").then(Commands.m_82129_("targets", ScoreHolderArgument.m_108239_()).suggests(ScoreHolderArgument.f_108210_).then(Commands.m_82129_("objective", ObjectiveArgument.m_101957_()).suggests((p_138473_, p_138474_) -> ScoreboardCommand.m_138510_((CommandSourceStack)p_138473_.getSource(), ScoreHolderArgument.m_108246_((CommandContext<CommandSourceStack>)p_138473_, "targets"), p_138474_)).executes(p_138537_ -> ScoreboardCommand.m_138514_((CommandSourceStack)p_138537_.getSource(), ScoreHolderArgument.m_108246_((CommandContext<CommandSourceStack>)p_138537_, "targets"), ObjectiveArgument.m_101960_((CommandContext<CommandSourceStack>)p_138537_, "objective"))))))).then(Commands.m_82127_("operation").then(Commands.m_82129_("targets", ScoreHolderArgument.m_108239_()).suggests(ScoreHolderArgument.f_108210_).then(Commands.m_82129_("targetObjective", ObjectiveArgument.m_101957_()).then(Commands.m_82129_("operation", OperationArgument.m_103269_()).then(Commands.m_82129_("source", ScoreHolderArgument.m_108239_()).suggests(ScoreHolderArgument.f_108210_).then(Commands.m_82129_("sourceObjective", ObjectiveArgument.m_101957_()).executes(p_138471_ -> ScoreboardCommand.m_138523_((CommandSourceStack)p_138471_.getSource(), ScoreHolderArgument.m_108246_((CommandContext<CommandSourceStack>)p_138471_, "targets"), ObjectiveArgument.m_101965_((CommandContext<CommandSourceStack>)p_138471_, "targetObjective"), OperationArgument.m_103275_((CommandContext<CommandSourceStack>)p_138471_, "operation"), ScoreHolderArgument.m_108246_((CommandContext<CommandSourceStack>)p_138471_, "source"), ObjectiveArgument.m_101960_((CommandContext<CommandSourceStack>)p_138471_, "sourceObjective")))))))))));
    }

    private static LiteralArgumentBuilder<CommandSourceStack> m_138467_() {
        LiteralArgumentBuilder<CommandSourceStack> $$0 = Commands.m_82127_("rendertype");
        for (ObjectiveCriteria.RenderType $$1 : ObjectiveCriteria.RenderType.values()) {
            $$0.then(Commands.m_82127_($$1.m_83633_()).executes(p_138532_ -> ScoreboardCommand.m_138487_((CommandSourceStack)p_138532_.getSource(), ObjectiveArgument.m_101960_((CommandContext<CommandSourceStack>)p_138532_, "objective"), $$1)));
        }
        return $$0;
    }

    private static CompletableFuture<Suggestions> m_138510_(CommandSourceStack p_138511_, Collection<String> p_138512_, SuggestionsBuilder p_138513_) {
        ArrayList $$3 = Lists.newArrayList();
        ServerScoreboard $$4 = p_138511_.m_81377_().m_129896_();
        for (Objective $$5 : $$4.m_83466_()) {
            if ($$5.m_83321_() != ObjectiveCriteria.f_83589_) continue;
            boolean $$6 = false;
            for (String $$7 : p_138512_) {
                if ($$4.m_83461_($$7, $$5) && !$$4.m_83471_($$7, $$5).m_83407_()) continue;
                $$6 = true;
                break;
            }
            if (!$$6) continue;
            $$3.add($$5.m_83320_());
        }
        return SharedSuggestionProvider.m_82970_($$3, p_138513_);
    }

    private static int m_138498_(CommandSourceStack p_138499_, String p_138500_, Objective p_138501_) throws CommandSyntaxException {
        ServerScoreboard $$3 = p_138499_.m_81377_().m_129896_();
        if (!$$3.m_83461_(p_138500_, p_138501_)) {
            throw f_138465_.create((Object)p_138501_.m_83320_(), (Object)p_138500_);
        }
        Score $$4 = $$3.m_83471_(p_138500_, p_138501_);
        p_138499_.m_81354_(Component.m_237110_("commands.scoreboard.players.get.success", p_138500_, $$4.m_83400_(), p_138501_.m_83323_()), false);
        return $$4.m_83400_();
    }

    private static int m_138523_(CommandSourceStack p_138524_, Collection<String> p_138525_, Objective p_138526_, OperationArgument.Operation p_138527_, Collection<String> p_138528_, Objective p_138529_) throws CommandSyntaxException {
        ServerScoreboard $$6 = p_138524_.m_81377_().m_129896_();
        int $$7 = 0;
        for (String $$8 : p_138525_) {
            Score $$9 = $$6.m_83471_($$8, p_138526_);
            for (String $$10 : p_138528_) {
                Score $$11 = $$6.m_83471_($$10, p_138529_);
                p_138527_.m_6407_($$9, $$11);
            }
            $$7 += $$9.m_83400_();
        }
        if (p_138525_.size() == 1) {
            p_138524_.m_81354_(Component.m_237110_("commands.scoreboard.players.operation.success.single", p_138526_.m_83323_(), p_138525_.iterator().next(), $$7), true);
        } else {
            p_138524_.m_81354_(Component.m_237110_("commands.scoreboard.players.operation.success.multiple", p_138526_.m_83323_(), p_138525_.size()), true);
        }
        return $$7;
    }

    private static int m_138514_(CommandSourceStack p_138515_, Collection<String> p_138516_, Objective p_138517_) throws CommandSyntaxException {
        if (p_138517_.m_83321_() != ObjectiveCriteria.f_83589_) {
            throw f_138464_.create();
        }
        ServerScoreboard $$3 = p_138515_.m_81377_().m_129896_();
        int $$4 = 0;
        for (String $$5 : p_138516_) {
            Score $$6 = $$3.m_83471_($$5, p_138517_);
            if (!$$6.m_83407_()) continue;
            $$6.m_83398_(false);
            ++$$4;
        }
        if ($$4 == 0) {
            throw f_138463_.create();
        }
        if (p_138516_.size() == 1) {
            p_138515_.m_81354_(Component.m_237110_("commands.scoreboard.players.enable.success.single", p_138517_.m_83323_(), p_138516_.iterator().next()), true);
        } else {
            p_138515_.m_81354_(Component.m_237110_("commands.scoreboard.players.enable.success.multiple", p_138517_.m_83323_(), p_138516_.size()), true);
        }
        return $$4;
    }

    private static int m_138507_(CommandSourceStack p_138508_, Collection<String> p_138509_) {
        ServerScoreboard $$2 = p_138508_.m_81377_().m_129896_();
        for (String $$3 : p_138509_) {
            $$2.m_83479_($$3, null);
        }
        if (p_138509_.size() == 1) {
            p_138508_.m_81354_(Component.m_237110_("commands.scoreboard.players.reset.all.single", p_138509_.iterator().next()), true);
        } else {
            p_138508_.m_81354_(Component.m_237110_("commands.scoreboard.players.reset.all.multiple", p_138509_.size()), true);
        }
        return p_138509_.size();
    }

    private static int m_138540_(CommandSourceStack p_138541_, Collection<String> p_138542_, Objective p_138543_) {
        ServerScoreboard $$3 = p_138541_.m_81377_().m_129896_();
        for (String $$4 : p_138542_) {
            $$3.m_83479_($$4, p_138543_);
        }
        if (p_138542_.size() == 1) {
            p_138541_.m_81354_(Component.m_237110_("commands.scoreboard.players.reset.specific.single", p_138543_.m_83323_(), p_138542_.iterator().next()), true);
        } else {
            p_138541_.m_81354_(Component.m_237110_("commands.scoreboard.players.reset.specific.multiple", p_138543_.m_83323_(), p_138542_.size()), true);
        }
        return p_138542_.size();
    }

    private static int m_138518_(CommandSourceStack p_138519_, Collection<String> p_138520_, Objective p_138521_, int p_138522_) {
        ServerScoreboard $$4 = p_138519_.m_81377_().m_129896_();
        for (String $$5 : p_138520_) {
            Score $$6 = $$4.m_83471_($$5, p_138521_);
            $$6.m_83402_(p_138522_);
        }
        if (p_138520_.size() == 1) {
            p_138519_.m_81354_(Component.m_237110_("commands.scoreboard.players.set.success.single", p_138521_.m_83323_(), p_138520_.iterator().next(), p_138522_), true);
        } else {
            p_138519_.m_81354_(Component.m_237110_("commands.scoreboard.players.set.success.multiple", p_138521_.m_83323_(), p_138520_.size(), p_138522_), true);
        }
        return p_138522_ * p_138520_.size();
    }

    private static int m_138544_(CommandSourceStack p_138545_, Collection<String> p_138546_, Objective p_138547_, int p_138548_) {
        ServerScoreboard $$4 = p_138545_.m_81377_().m_129896_();
        int $$5 = 0;
        for (String $$6 : p_138546_) {
            Score $$7 = $$4.m_83471_($$6, p_138547_);
            $$7.m_83402_($$7.m_83400_() + p_138548_);
            $$5 += $$7.m_83400_();
        }
        if (p_138546_.size() == 1) {
            p_138545_.m_81354_(Component.m_237110_("commands.scoreboard.players.add.success.single", p_138548_, p_138547_.m_83323_(), p_138546_.iterator().next(), $$5), true);
        } else {
            p_138545_.m_81354_(Component.m_237110_("commands.scoreboard.players.add.success.multiple", p_138548_, p_138547_.m_83323_(), p_138546_.size()), true);
        }
        return $$5;
    }

    private static int m_138553_(CommandSourceStack p_138554_, Collection<String> p_138555_, Objective p_138556_, int p_138557_) {
        ServerScoreboard $$4 = p_138554_.m_81377_().m_129896_();
        int $$5 = 0;
        for (String $$6 : p_138555_) {
            Score $$7 = $$4.m_83471_($$6, p_138556_);
            $$7.m_83402_($$7.m_83400_() - p_138557_);
            $$5 += $$7.m_83400_();
        }
        if (p_138555_.size() == 1) {
            p_138554_.m_81354_(Component.m_237110_("commands.scoreboard.players.remove.success.single", p_138557_, p_138556_.m_83323_(), p_138555_.iterator().next(), $$5), true);
        } else {
            p_138554_.m_81354_(Component.m_237110_("commands.scoreboard.players.remove.success.multiple", p_138557_, p_138556_.m_83323_(), p_138555_.size()), true);
        }
        return $$5;
    }

    private static int m_138475_(CommandSourceStack p_138476_) {
        Collection<String> $$1 = p_138476_.m_81377_().m_129896_().m_83482_();
        if ($$1.isEmpty()) {
            p_138476_.m_81354_(Component.m_237115_("commands.scoreboard.players.list.empty"), false);
        } else {
            p_138476_.m_81354_(Component.m_237110_("commands.scoreboard.players.list.success", $$1.size(), ComponentUtils.m_130743_($$1)), false);
        }
        return $$1.size();
    }

    private static int m_138495_(CommandSourceStack p_138496_, String p_138497_) {
        Map<Objective, Score> $$2 = p_138496_.m_81377_().m_129896_().m_83483_(p_138497_);
        if ($$2.isEmpty()) {
            p_138496_.m_81354_(Component.m_237110_("commands.scoreboard.players.list.entity.empty", p_138497_), false);
        } else {
            p_138496_.m_81354_(Component.m_237110_("commands.scoreboard.players.list.entity.success", p_138497_, $$2.size()), false);
            for (Map.Entry<Objective, Score> $$3 : $$2.entrySet()) {
                p_138496_.m_81354_(Component.m_237110_("commands.scoreboard.players.list.entity.entry", $$3.getKey().m_83323_(), $$3.getValue().m_83400_()), false);
            }
        }
        return $$2.size();
    }

    private static int m_138477_(CommandSourceStack p_138478_, int p_138479_) throws CommandSyntaxException {
        ServerScoreboard $$2 = p_138478_.m_81377_().m_129896_();
        if ($$2.m_83416_(p_138479_) == null) {
            throw f_138461_.create();
        }
        ((Scoreboard)$$2).m_7136_(p_138479_, null);
        p_138478_.m_81354_(Component.m_237110_("commands.scoreboard.objectives.display.cleared", Scoreboard.m_83494_()[p_138479_]), true);
        return 0;
    }

    private static int m_138480_(CommandSourceStack p_138481_, int p_138482_, Objective p_138483_) throws CommandSyntaxException {
        ServerScoreboard $$3 = p_138481_.m_81377_().m_129896_();
        if ($$3.m_83416_(p_138482_) == p_138483_) {
            throw f_138462_.create();
        }
        ((Scoreboard)$$3).m_7136_(p_138482_, p_138483_);
        p_138481_.m_81354_(Component.m_237110_("commands.scoreboard.objectives.display.set", Scoreboard.m_83494_()[p_138482_], p_138483_.m_83322_()), true);
        return 0;
    }

    private static int m_138491_(CommandSourceStack p_138492_, Objective p_138493_, Component p_138494_) {
        if (!p_138493_.m_83322_().equals(p_138494_)) {
            p_138493_.m_83316_(p_138494_);
            p_138492_.m_81354_(Component.m_237110_("commands.scoreboard.objectives.modify.displayname", p_138493_.m_83320_(), p_138493_.m_83323_()), true);
        }
        return 0;
    }

    private static int m_138487_(CommandSourceStack p_138488_, Objective p_138489_, ObjectiveCriteria.RenderType p_138490_) {
        if (p_138489_.m_83324_() != p_138490_) {
            p_138489_.m_83314_(p_138490_);
            p_138488_.m_81354_(Component.m_237110_("commands.scoreboard.objectives.modify.rendertype", p_138489_.m_83323_()), true);
        }
        return 0;
    }

    private static int m_138484_(CommandSourceStack p_138485_, Objective p_138486_) {
        ServerScoreboard $$2 = p_138485_.m_81377_().m_129896_();
        $$2.m_83502_(p_138486_);
        p_138485_.m_81354_(Component.m_237110_("commands.scoreboard.objectives.remove.success", p_138486_.m_83323_()), true);
        return $$2.m_83466_().size();
    }

    private static int m_138502_(CommandSourceStack p_138503_, String p_138504_, ObjectiveCriteria p_138505_, Component p_138506_) throws CommandSyntaxException {
        ServerScoreboard $$4 = p_138503_.m_81377_().m_129896_();
        if ($$4.m_83477_(p_138504_) != null) {
            throw f_138460_.create();
        }
        $$4.m_83436_(p_138504_, p_138505_, p_138506_, p_138505_.m_83622_());
        Objective $$5 = $$4.m_83477_(p_138504_);
        p_138503_.m_81354_(Component.m_237110_("commands.scoreboard.objectives.add.success", $$5.m_83323_()), true);
        return $$4.m_83466_().size();
    }

    private static int m_138538_(CommandSourceStack p_138539_) {
        Collection<Objective> $$1 = p_138539_.m_81377_().m_129896_().m_83466_();
        if ($$1.isEmpty()) {
            p_138539_.m_81354_(Component.m_237115_("commands.scoreboard.objectives.list.empty"), false);
        } else {
            p_138539_.m_81354_(Component.m_237110_("commands.scoreboard.objectives.list.success", $$1.size(), ComponentUtils.m_178440_($$1, Objective::m_83323_)), false);
        }
        return $$1.size();
    }
}

