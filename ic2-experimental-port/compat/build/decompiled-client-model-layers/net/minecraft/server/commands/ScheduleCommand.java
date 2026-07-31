/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.suggestion.SuggestionProvider
 *  com.mojang.datafixers.util.Either
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import net.minecraft.commands.CommandFunction;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.TimeArgument;
import net.minecraft.commands.arguments.item.FunctionArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.commands.FunctionCommand;
import net.minecraft.world.level.timers.FunctionCallback;
import net.minecraft.world.level.timers.FunctionTagCallback;
import net.minecraft.world.level.timers.TimerQueue;

public class ScheduleCommand {
    private static final SimpleCommandExceptionType f_138415_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.schedule.same_tick"));
    private static final DynamicCommandExceptionType f_138416_ = new DynamicCommandExceptionType(p_138437_ -> Component.m_237110_("commands.schedule.cleared.failure", p_138437_));
    private static final SuggestionProvider<CommandSourceStack> f_138417_ = (p_138424_, p_138425_) -> SharedSuggestionProvider.m_82970_(((CommandSourceStack)p_138424_.getSource()).m_81377_().m_129910_().m_5996_().m_7540_().m_82251_(), p_138425_);

    public static void m_138419_(CommandDispatcher<CommandSourceStack> p_138420_) {
        p_138420_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("schedule").requires(p_138427_ -> p_138427_.m_6761_(2))).then(Commands.m_82127_("function").then(Commands.m_82129_("function", FunctionArgument.m_120907_()).suggests(FunctionCommand.f_137712_).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("time", TimeArgument.m_113037_()).executes(p_138459_ -> ScheduleCommand.m_138428_((CommandSourceStack)p_138459_.getSource(), FunctionArgument.m_120920_((CommandContext<CommandSourceStack>)p_138459_, "function"), IntegerArgumentType.getInteger((CommandContext)p_138459_, (String)"time"), true))).then(Commands.m_82127_("append").executes(p_138457_ -> ScheduleCommand.m_138428_((CommandSourceStack)p_138457_.getSource(), FunctionArgument.m_120920_((CommandContext<CommandSourceStack>)p_138457_, "function"), IntegerArgumentType.getInteger((CommandContext)p_138457_, (String)"time"), false)))).then(Commands.m_82127_("replace").executes(p_138455_ -> ScheduleCommand.m_138428_((CommandSourceStack)p_138455_.getSource(), FunctionArgument.m_120920_((CommandContext<CommandSourceStack>)p_138455_, "function"), IntegerArgumentType.getInteger((CommandContext)p_138455_, (String)"time"), true))))))).then(Commands.m_82127_("clear").then(Commands.m_82129_("function", StringArgumentType.greedyString()).suggests(f_138417_).executes(p_138422_ -> ScheduleCommand.m_138433_((CommandSourceStack)p_138422_.getSource(), StringArgumentType.getString((CommandContext)p_138422_, (String)"function"))))));
    }

    private static int m_138428_(CommandSourceStack p_138429_, Pair<ResourceLocation, Either<CommandFunction, Collection<CommandFunction>>> p_138430_, int p_138431_, boolean p_138432_) throws CommandSyntaxException {
        if (p_138431_ == 0) {
            throw f_138415_.create();
        }
        long $$4 = p_138429_.m_81372_().m_46467_() + (long)p_138431_;
        ResourceLocation $$5 = (ResourceLocation)p_138430_.getFirst();
        TimerQueue<MinecraftServer> $$6 = p_138429_.m_81377_().m_129910_().m_5996_().m_7540_();
        ((Either)p_138430_.getSecond()).ifLeft(p_138453_ -> {
            String $$7 = $$5.toString();
            if (p_138432_) {
                $$6.m_82259_($$7);
            }
            $$6.m_82261_($$7, $$4, new FunctionCallback($$5));
            p_138429_.m_81354_(Component.m_237110_("commands.schedule.created.function", $$5, p_138431_, $$4), true);
        }).ifRight(p_214729_ -> {
            String $$7 = "#" + $$5;
            if (p_138432_) {
                $$6.m_82259_($$7);
            }
            $$6.m_82261_($$7, $$4, new FunctionTagCallback($$5));
            p_138429_.m_81354_(Component.m_237110_("commands.schedule.created.tag", $$5, p_138431_, $$4), true);
        });
        return Math.floorMod($$4, Integer.MAX_VALUE);
    }

    private static int m_138433_(CommandSourceStack p_138434_, String p_138435_) throws CommandSyntaxException {
        int $$2 = p_138434_.m_81377_().m_129910_().m_5996_().m_7540_().m_82259_(p_138435_);
        if ($$2 == 0) {
            throw f_138416_.create((Object)p_138435_);
        }
        p_138434_.m_81354_(Component.m_237110_("commands.schedule.cleared.success", $$2, p_138435_), true);
        return $$2;
    }
}

