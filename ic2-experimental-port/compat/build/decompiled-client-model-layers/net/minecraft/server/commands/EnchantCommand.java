/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ItemEnchantmentArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class EnchantCommand {
    private static final DynamicCommandExceptionType f_137002_ = new DynamicCommandExceptionType(p_137029_ -> Component.m_237110_("commands.enchant.failed.entity", p_137029_));
    private static final DynamicCommandExceptionType f_137003_ = new DynamicCommandExceptionType(p_137027_ -> Component.m_237110_("commands.enchant.failed.itemless", p_137027_));
    private static final DynamicCommandExceptionType f_137004_ = new DynamicCommandExceptionType(p_137020_ -> Component.m_237110_("commands.enchant.failed.incompatible", p_137020_));
    private static final Dynamic2CommandExceptionType f_137005_ = new Dynamic2CommandExceptionType((p_137022_, p_137023_) -> Component.m_237110_("commands.enchant.failed.level", p_137022_, p_137023_));
    private static final SimpleCommandExceptionType f_137006_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.enchant.failed"));

    public static void m_137008_(CommandDispatcher<CommandSourceStack> p_137009_) {
        p_137009_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("enchant").requires(p_137013_ -> p_137013_.m_6761_(2))).then(Commands.m_82129_("targets", EntityArgument.m_91460_()).then(((RequiredArgumentBuilder)Commands.m_82129_("enchantment", ItemEnchantmentArgument.m_95260_()).executes(p_137025_ -> EnchantCommand.m_137014_((CommandSourceStack)p_137025_.getSource(), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_137025_, "targets"), ItemEnchantmentArgument.m_95263_((CommandContext<CommandSourceStack>)p_137025_, "enchantment"), 1))).then(Commands.m_82129_("level", IntegerArgumentType.integer((int)0)).executes(p_137011_ -> EnchantCommand.m_137014_((CommandSourceStack)p_137011_.getSource(), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_137011_, "targets"), ItemEnchantmentArgument.m_95263_((CommandContext<CommandSourceStack>)p_137011_, "enchantment"), IntegerArgumentType.getInteger((CommandContext)p_137011_, (String)"level")))))));
    }

    private static int m_137014_(CommandSourceStack p_137015_, Collection<? extends Entity> p_137016_, Enchantment p_137017_, int p_137018_) throws CommandSyntaxException {
        if (p_137018_ > p_137017_.m_6586_()) {
            throw f_137005_.create((Object)p_137018_, (Object)p_137017_.m_6586_());
        }
        int $$4 = 0;
        for (Entity entity : p_137016_) {
            if (entity instanceof LivingEntity) {
                LivingEntity $$6 = (LivingEntity)entity;
                ItemStack $$7 = $$6.m_21205_();
                if (!$$7.m_41619_()) {
                    if (p_137017_.m_6081_($$7) && EnchantmentHelper.m_44859_(EnchantmentHelper.m_44831_($$7).keySet(), p_137017_)) {
                        $$7.m_41663_(p_137017_, p_137018_);
                        ++$$4;
                        continue;
                    }
                    if (p_137016_.size() != 1) continue;
                    throw f_137004_.create((Object)$$7.m_41720_().m_7626_($$7).getString());
                }
                if (p_137016_.size() != 1) continue;
                throw f_137003_.create((Object)$$6.m_7755_().getString());
            }
            if (p_137016_.size() != 1) continue;
            throw f_137002_.create((Object)entity.m_7755_().getString());
        }
        if ($$4 == 0) {
            throw f_137006_.create();
        }
        if (p_137016_.size() == 1) {
            p_137015_.m_81354_(Component.m_237110_("commands.enchant.success.single", p_137017_.m_44700_(p_137018_), p_137016_.iterator().next().m_5446_()), true);
        } else {
            p_137015_.m_81354_(Component.m_237110_("commands.enchant.success.multiple", p_137017_.m_44700_(p_137018_), p_137016_.size()), true);
        }
        return $$4;
    }
}

