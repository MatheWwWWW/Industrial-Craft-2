/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 */
package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ObjectiveArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class TriggerCommand {
    private static final SimpleCommandExceptionType f_139135_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.trigger.failed.unprimed"));
    private static final SimpleCommandExceptionType f_139136_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.trigger.failed.invalid"));

    public static void m_139141_(CommandDispatcher<CommandSourceStack> p_139142_) {
        p_139142_.register((LiteralArgumentBuilder)Commands.m_82127_("trigger").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("objective", ObjectiveArgument.m_101957_()).suggests((p_139146_, p_139147_) -> TriggerCommand.m_139148_((CommandSourceStack)p_139146_.getSource(), p_139147_)).executes(p_139165_ -> TriggerCommand.m_139151_((CommandSourceStack)p_139165_.getSource(), TriggerCommand.m_139138_(((CommandSourceStack)p_139165_.getSource()).m_81375_(), ObjectiveArgument.m_101960_((CommandContext<CommandSourceStack>)p_139165_, "objective"))))).then(Commands.m_82127_("add").then(Commands.m_82129_("value", IntegerArgumentType.integer()).executes(p_139159_ -> TriggerCommand.m_139154_((CommandSourceStack)p_139159_.getSource(), TriggerCommand.m_139138_(((CommandSourceStack)p_139159_.getSource()).m_81375_(), ObjectiveArgument.m_101960_((CommandContext<CommandSourceStack>)p_139159_, "objective")), IntegerArgumentType.getInteger((CommandContext)p_139159_, (String)"value")))))).then(Commands.m_82127_("set").then(Commands.m_82129_("value", IntegerArgumentType.integer()).executes(p_139144_ -> TriggerCommand.m_139160_((CommandSourceStack)p_139144_.getSource(), TriggerCommand.m_139138_(((CommandSourceStack)p_139144_.getSource()).m_81375_(), ObjectiveArgument.m_101960_((CommandContext<CommandSourceStack>)p_139144_, "objective")), IntegerArgumentType.getInteger((CommandContext)p_139144_, (String)"value")))))));
    }

    public static CompletableFuture<Suggestions> m_139148_(CommandSourceStack p_139149_, SuggestionsBuilder p_139150_) {
        Entity $$2 = p_139149_.m_81373_();
        ArrayList $$3 = Lists.newArrayList();
        if ($$2 != null) {
            ServerScoreboard $$4 = p_139149_.m_81377_().m_129896_();
            String $$5 = $$2.m_6302_();
            for (Objective $$6 : $$4.m_83466_()) {
                Score $$7;
                if ($$6.m_83321_() != ObjectiveCriteria.f_83589_ || !$$4.m_83461_($$5, $$6) || ($$7 = $$4.m_83471_($$5, $$6)).m_83407_()) continue;
                $$3.add($$6.m_83320_());
            }
        }
        return SharedSuggestionProvider.m_82970_($$3, p_139150_);
    }

    private static int m_139154_(CommandSourceStack p_139155_, Score p_139156_, int p_139157_) {
        p_139156_.m_83393_(p_139157_);
        p_139155_.m_81354_(Component.m_237110_("commands.trigger.add.success", p_139156_.m_83404_().m_83323_(), p_139157_), true);
        return p_139156_.m_83400_();
    }

    private static int m_139160_(CommandSourceStack p_139161_, Score p_139162_, int p_139163_) {
        p_139162_.m_83402_(p_139163_);
        p_139161_.m_81354_(Component.m_237110_("commands.trigger.set.success", p_139162_.m_83404_().m_83323_(), p_139163_), true);
        return p_139163_;
    }

    private static int m_139151_(CommandSourceStack p_139152_, Score p_139153_) {
        p_139153_.m_83393_(1);
        p_139152_.m_81354_(Component.m_237110_("commands.trigger.simple.success", p_139153_.m_83404_().m_83323_()), true);
        return p_139153_.m_83400_();
    }

    private static Score m_139138_(ServerPlayer p_139139_, Objective p_139140_) throws CommandSyntaxException {
        String $$3;
        if (p_139140_.m_83321_() != ObjectiveCriteria.f_83589_) {
            throw f_139136_.create();
        }
        Scoreboard $$2 = p_139139_.m_36329_();
        if (!$$2.m_83461_($$3 = p_139139_.m_6302_(), p_139140_)) {
            throw f_139135_.create();
        }
        Score $$4 = $$2.m_83471_($$3, p_139140_);
        if ($$4.m_83407_()) {
            throw f_139135_.create();
        }
        $$4.m_83398_(true);
        return $$4;
    }
}

