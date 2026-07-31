/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.item.ItemPredicateArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class ClearInventoryCommands {
    private static final DynamicCommandExceptionType f_136694_ = new DynamicCommandExceptionType(p_136717_ -> Component.m_237110_("clear.failed.single", p_136717_));
    private static final DynamicCommandExceptionType f_136695_ = new DynamicCommandExceptionType(p_136711_ -> Component.m_237110_("clear.failed.multiple", p_136711_));

    public static void m_214420_(CommandDispatcher<CommandSourceStack> p_214421_, CommandBuildContext p_214422_) {
        p_214421_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("clear").requires(p_136704_ -> p_136704_.m_6761_(2))).executes(p_136721_ -> ClearInventoryCommands.m_136705_((CommandSourceStack)p_136721_.getSource(), Collections.singleton(((CommandSourceStack)p_136721_.getSource()).m_81375_()), p_180029_ -> true, -1))).then(((RequiredArgumentBuilder)Commands.m_82129_("targets", EntityArgument.m_91470_()).executes(p_136719_ -> ClearInventoryCommands.m_136705_((CommandSourceStack)p_136719_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_136719_, "targets"), p_180027_ -> true, -1))).then(((RequiredArgumentBuilder)Commands.m_82129_("item", ItemPredicateArgument.m_235353_(p_214422_)).executes(p_136715_ -> ClearInventoryCommands.m_136705_((CommandSourceStack)p_136715_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_136715_, "targets"), ItemPredicateArgument.m_121040_((CommandContext<CommandSourceStack>)p_136715_, "item"), -1))).then(Commands.m_82129_("maxCount", IntegerArgumentType.integer((int)0)).executes(p_136702_ -> ClearInventoryCommands.m_136705_((CommandSourceStack)p_136702_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_136702_, "targets"), ItemPredicateArgument.m_121040_((CommandContext<CommandSourceStack>)p_136702_, "item"), IntegerArgumentType.getInteger((CommandContext)p_136702_, (String)"maxCount")))))));
    }

    private static int m_136705_(CommandSourceStack p_136706_, Collection<ServerPlayer> p_136707_, Predicate<ItemStack> p_136708_, int p_136709_) throws CommandSyntaxException {
        int $$4 = 0;
        for (ServerPlayer $$5 : p_136707_) {
            $$4 += $$5.m_150109_().m_36022_(p_136708_, p_136709_, $$5.f_36095_.m_39730_());
            $$5.f_36096_.m_38946_();
            $$5.f_36095_.m_6199_($$5.m_150109_());
        }
        if ($$4 == 0) {
            if (p_136707_.size() == 1) {
                throw f_136694_.create((Object)p_136707_.iterator().next().m_7755_());
            }
            throw f_136695_.create((Object)p_136707_.size());
        }
        if (p_136709_ == 0) {
            if (p_136707_.size() == 1) {
                p_136706_.m_81354_(Component.m_237110_("commands.clear.test.single", $$4, p_136707_.iterator().next().m_5446_()), true);
            } else {
                p_136706_.m_81354_(Component.m_237110_("commands.clear.test.multiple", $$4, p_136707_.size()), true);
            }
        } else if (p_136707_.size() == 1) {
            p_136706_.m_81354_(Component.m_237110_("commands.clear.success.single", $$4, p_136707_.iterator().next().m_5446_()), true);
        } else {
            p_136706_.m_81354_(Component.m_237110_("commands.clear.success.multiple", $$4, p_136707_.size()), true);
        }
        return $$4;
    }
}

