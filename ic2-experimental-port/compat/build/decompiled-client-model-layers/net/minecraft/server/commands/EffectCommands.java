/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.BoolArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  javax.annotation.Nullable
 */
package net.minecraft.server.commands;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.MobEffectArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class EffectCommands {
    private static final SimpleCommandExceptionType f_136949_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.effect.give.failed"));
    private static final SimpleCommandExceptionType f_136950_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.effect.clear.everything.failed"));
    private static final SimpleCommandExceptionType f_136951_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.effect.clear.specific.failed"));

    public static void m_136953_(CommandDispatcher<CommandSourceStack> p_136954_) {
        p_136954_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("effect").requires(p_136958_ -> p_136958_.m_6761_(2))).then(((LiteralArgumentBuilder)Commands.m_82127_("clear").executes(p_136984_ -> EffectCommands.m_136959_((CommandSourceStack)p_136984_.getSource(), (Collection<? extends Entity>)ImmutableList.of((Object)((CommandSourceStack)p_136984_.getSource()).m_81374_())))).then(((RequiredArgumentBuilder)Commands.m_82129_("targets", EntityArgument.m_91460_()).executes(p_136982_ -> EffectCommands.m_136959_((CommandSourceStack)p_136982_.getSource(), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_136982_, "targets")))).then(Commands.m_82129_("effect", MobEffectArgument.m_98426_()).executes(p_136980_ -> EffectCommands.m_136962_((CommandSourceStack)p_136980_.getSource(), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_136980_, "targets"), MobEffectArgument.m_98429_((CommandContext<CommandSourceStack>)p_136980_, "effect"))))))).then(Commands.m_82127_("give").then(Commands.m_82129_("targets", EntityArgument.m_91460_()).then(((RequiredArgumentBuilder)Commands.m_82129_("effect", MobEffectArgument.m_98426_()).executes(p_136978_ -> EffectCommands.m_136966_((CommandSourceStack)p_136978_.getSource(), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_136978_, "targets"), MobEffectArgument.m_98429_((CommandContext<CommandSourceStack>)p_136978_, "effect"), null, 0, true))).then(((RequiredArgumentBuilder)Commands.m_82129_("seconds", IntegerArgumentType.integer((int)1, (int)1000000)).executes(p_136976_ -> EffectCommands.m_136966_((CommandSourceStack)p_136976_.getSource(), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_136976_, "targets"), MobEffectArgument.m_98429_((CommandContext<CommandSourceStack>)p_136976_, "effect"), IntegerArgumentType.getInteger((CommandContext)p_136976_, (String)"seconds"), 0, true))).then(((RequiredArgumentBuilder)Commands.m_82129_("amplifier", IntegerArgumentType.integer((int)0, (int)255)).executes(p_136974_ -> EffectCommands.m_136966_((CommandSourceStack)p_136974_.getSource(), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_136974_, "targets"), MobEffectArgument.m_98429_((CommandContext<CommandSourceStack>)p_136974_, "effect"), IntegerArgumentType.getInteger((CommandContext)p_136974_, (String)"seconds"), IntegerArgumentType.getInteger((CommandContext)p_136974_, (String)"amplifier"), true))).then(Commands.m_82129_("hideParticles", BoolArgumentType.bool()).executes(p_136956_ -> EffectCommands.m_136966_((CommandSourceStack)p_136956_.getSource(), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_136956_, "targets"), MobEffectArgument.m_98429_((CommandContext<CommandSourceStack>)p_136956_, "effect"), IntegerArgumentType.getInteger((CommandContext)p_136956_, (String)"seconds"), IntegerArgumentType.getInteger((CommandContext)p_136956_, (String)"amplifier"), !BoolArgumentType.getBool((CommandContext)p_136956_, (String)"hideParticles"))))))))));
    }

    private static int m_136966_(CommandSourceStack p_136967_, Collection<? extends Entity> p_136968_, MobEffect p_136969_, @Nullable Integer p_136970_, int p_136971_, boolean p_136972_) throws CommandSyntaxException {
        int $$10;
        int $$6 = 0;
        if (p_136970_ != null) {
            if (p_136969_.m_8093_()) {
                int $$7 = p_136970_;
            } else {
                int $$8 = p_136970_ * 20;
            }
        } else if (p_136969_.m_8093_()) {
            boolean $$9 = true;
        } else {
            $$10 = 600;
        }
        for (Entity entity : p_136968_) {
            MobEffectInstance $$12;
            if (!(entity instanceof LivingEntity) || !((LivingEntity)entity).m_147207_($$12 = new MobEffectInstance(p_136969_, $$10, p_136971_, false, p_136972_), p_136967_.m_81373_())) continue;
            ++$$6;
        }
        if ($$6 == 0) {
            throw f_136949_.create();
        }
        if (p_136968_.size() == 1) {
            p_136967_.m_81354_(Component.m_237110_("commands.effect.give.success.single", p_136969_.m_19482_(), p_136968_.iterator().next().m_5446_(), $$10 / 20), true);
        } else {
            p_136967_.m_81354_(Component.m_237110_("commands.effect.give.success.multiple", p_136969_.m_19482_(), p_136968_.size(), $$10 / 20), true);
        }
        return $$6;
    }

    private static int m_136959_(CommandSourceStack p_136960_, Collection<? extends Entity> p_136961_) throws CommandSyntaxException {
        int $$2 = 0;
        for (Entity entity : p_136961_) {
            if (!(entity instanceof LivingEntity) || !((LivingEntity)entity).m_21219_()) continue;
            ++$$2;
        }
        if ($$2 == 0) {
            throw f_136950_.create();
        }
        if (p_136961_.size() == 1) {
            p_136960_.m_81354_(Component.m_237110_("commands.effect.clear.everything.success.single", p_136961_.iterator().next().m_5446_()), true);
        } else {
            p_136960_.m_81354_(Component.m_237110_("commands.effect.clear.everything.success.multiple", p_136961_.size()), true);
        }
        return $$2;
    }

    private static int m_136962_(CommandSourceStack p_136963_, Collection<? extends Entity> p_136964_, MobEffect p_136965_) throws CommandSyntaxException {
        int $$3 = 0;
        for (Entity entity : p_136964_) {
            if (!(entity instanceof LivingEntity) || !((LivingEntity)entity).m_21195_(p_136965_)) continue;
            ++$$3;
        }
        if ($$3 == 0) {
            throw f_136951_.create();
        }
        if (p_136964_.size() == 1) {
            p_136963_.m_81354_(Component.m_237110_("commands.effect.clear.specific.success.single", p_136965_.m_19482_(), p_136964_.iterator().next().m_5446_()), true);
        } else {
            p_136963_.m_81354_(Component.m_237110_("commands.effect.clear.specific.success.multiple", p_136965_.m_19482_(), p_136964_.size()), true);
        }
        return $$3;
    }
}

