/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 */
package net.minecraft.server.commands;

import com.google.common.collect.Sets;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.HashSet;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.world.entity.Entity;

public class TagCommand {
    private static final SimpleCommandExceptionType f_138833_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.tag.add.failed"));
    private static final SimpleCommandExceptionType f_138834_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.tag.remove.failed"));

    public static void m_138836_(CommandDispatcher<CommandSourceStack> p_138837_) {
        p_138837_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("tag").requires(p_138844_ -> p_138844_.m_6761_(2))).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("targets", EntityArgument.m_91460_()).then(Commands.m_82127_("add").then(Commands.m_82129_("name", StringArgumentType.word()).executes(p_138861_ -> TagCommand.m_138848_((CommandSourceStack)p_138861_.getSource(), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_138861_, "targets"), StringArgumentType.getString((CommandContext)p_138861_, (String)"name")))))).then(Commands.m_82127_("remove").then(Commands.m_82129_("name", StringArgumentType.word()).suggests((p_138841_, p_138842_) -> SharedSuggestionProvider.m_82970_(TagCommand.m_138852_(EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_138841_, "targets")), p_138842_)).executes(p_138855_ -> TagCommand.m_138856_((CommandSourceStack)p_138855_.getSource(), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_138855_, "targets"), StringArgumentType.getString((CommandContext)p_138855_, (String)"name")))))).then(Commands.m_82127_("list").executes(p_138839_ -> TagCommand.m_138845_((CommandSourceStack)p_138839_.getSource(), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_138839_, "targets"))))));
    }

    private static Collection<String> m_138852_(Collection<? extends Entity> p_138853_) {
        HashSet $$1 = Sets.newHashSet();
        for (Entity entity : p_138853_) {
            $$1.addAll(entity.m_19880_());
        }
        return $$1;
    }

    private static int m_138848_(CommandSourceStack p_138849_, Collection<? extends Entity> p_138850_, String p_138851_) throws CommandSyntaxException {
        int $$3 = 0;
        for (Entity entity : p_138850_) {
            if (!entity.m_20049_(p_138851_)) continue;
            ++$$3;
        }
        if ($$3 == 0) {
            throw f_138833_.create();
        }
        if (p_138850_.size() == 1) {
            p_138849_.m_81354_(Component.m_237110_("commands.tag.add.success.single", p_138851_, p_138850_.iterator().next().m_5446_()), true);
        } else {
            p_138849_.m_81354_(Component.m_237110_("commands.tag.add.success.multiple", p_138851_, p_138850_.size()), true);
        }
        return $$3;
    }

    private static int m_138856_(CommandSourceStack p_138857_, Collection<? extends Entity> p_138858_, String p_138859_) throws CommandSyntaxException {
        int $$3 = 0;
        for (Entity entity : p_138858_) {
            if (!entity.m_20137_(p_138859_)) continue;
            ++$$3;
        }
        if ($$3 == 0) {
            throw f_138834_.create();
        }
        if (p_138858_.size() == 1) {
            p_138857_.m_81354_(Component.m_237110_("commands.tag.remove.success.single", p_138859_, p_138858_.iterator().next().m_5446_()), true);
        } else {
            p_138857_.m_81354_(Component.m_237110_("commands.tag.remove.success.multiple", p_138859_, p_138858_.size()), true);
        }
        return $$3;
    }

    private static int m_138845_(CommandSourceStack p_138846_, Collection<? extends Entity> p_138847_) {
        HashSet $$2 = Sets.newHashSet();
        for (Entity entity : p_138847_) {
            $$2.addAll(entity.m_19880_());
        }
        if (p_138847_.size() == 1) {
            Entity $$4 = p_138847_.iterator().next();
            if ($$2.isEmpty()) {
                p_138846_.m_81354_(Component.m_237110_("commands.tag.list.single.empty", $$4.m_5446_()), false);
            } else {
                p_138846_.m_81354_(Component.m_237110_("commands.tag.list.single.success", $$4.m_5446_(), $$2.size(), ComponentUtils.m_130743_($$2)), false);
            }
        } else if ($$2.isEmpty()) {
            p_138846_.m_81354_(Component.m_237110_("commands.tag.list.multiple.empty", p_138847_.size()), false);
        } else {
            p_138846_.m_81354_(Component.m_237110_("commands.tag.list.multiple.success", p_138847_.size(), $$2.size(), ComponentUtils.m_130743_($$2)), false);
        }
        return $$2.size();
    }
}

