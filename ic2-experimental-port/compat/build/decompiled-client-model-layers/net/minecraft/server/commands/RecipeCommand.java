/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeCommand {
    private static final SimpleCommandExceptionType f_138197_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.recipe.give.failed"));
    private static final SimpleCommandExceptionType f_138198_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.recipe.take.failed"));

    public static void m_138200_(CommandDispatcher<CommandSourceStack> p_138201_) {
        p_138201_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("recipe").requires(p_138205_ -> p_138205_.m_6761_(2))).then(Commands.m_82127_("give").then(((RequiredArgumentBuilder)Commands.m_82129_("targets", EntityArgument.m_91470_()).then(Commands.m_82129_("recipe", ResourceLocationArgument.m_106984_()).suggests(SuggestionProviders.f_121642_).executes(p_138219_ -> RecipeCommand.m_138206_((CommandSourceStack)p_138219_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_138219_, "targets"), Collections.singleton(ResourceLocationArgument.m_106994_((CommandContext<CommandSourceStack>)p_138219_, "recipe")))))).then(Commands.m_82127_("*").executes(p_138217_ -> RecipeCommand.m_138206_((CommandSourceStack)p_138217_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_138217_, "targets"), ((CommandSourceStack)p_138217_.getSource()).m_81377_().m_129894_().m_44051_())))))).then(Commands.m_82127_("take").then(((RequiredArgumentBuilder)Commands.m_82129_("targets", EntityArgument.m_91470_()).then(Commands.m_82129_("recipe", ResourceLocationArgument.m_106984_()).suggests(SuggestionProviders.f_121642_).executes(p_138211_ -> RecipeCommand.m_138212_((CommandSourceStack)p_138211_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_138211_, "targets"), Collections.singleton(ResourceLocationArgument.m_106994_((CommandContext<CommandSourceStack>)p_138211_, "recipe")))))).then(Commands.m_82127_("*").executes(p_138203_ -> RecipeCommand.m_138212_((CommandSourceStack)p_138203_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_138203_, "targets"), ((CommandSourceStack)p_138203_.getSource()).m_81377_().m_129894_().m_44051_()))))));
    }

    private static int m_138206_(CommandSourceStack p_138207_, Collection<ServerPlayer> p_138208_, Collection<Recipe<?>> p_138209_) throws CommandSyntaxException {
        int $$3 = 0;
        for (ServerPlayer $$4 : p_138208_) {
            $$3 += $$4.m_7281_(p_138209_);
        }
        if ($$3 == 0) {
            throw f_138197_.create();
        }
        if (p_138208_.size() == 1) {
            p_138207_.m_81354_(Component.m_237110_("commands.recipe.give.success.single", p_138209_.size(), p_138208_.iterator().next().m_5446_()), true);
        } else {
            p_138207_.m_81354_(Component.m_237110_("commands.recipe.give.success.multiple", p_138209_.size(), p_138208_.size()), true);
        }
        return $$3;
    }

    private static int m_138212_(CommandSourceStack p_138213_, Collection<ServerPlayer> p_138214_, Collection<Recipe<?>> p_138215_) throws CommandSyntaxException {
        int $$3 = 0;
        for (ServerPlayer $$4 : p_138214_) {
            $$3 += $$4.m_7279_(p_138215_);
        }
        if ($$3 == 0) {
            throw f_138198_.create();
        }
        if (p_138214_.size() == 1) {
            p_138213_.m_81354_(Component.m_237110_("commands.recipe.take.success.single", p_138215_.size(), p_138214_.iterator().next().m_5446_()), true);
        } else {
            p_138213_.m_81354_(Component.m_237110_("commands.recipe.take.success.multiple", p_138215_.size(), p_138214_.size()), true);
        }
        return $$3;
    }
}

