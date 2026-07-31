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
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.function.Function;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.server.level.ServerPlayer;

public class TitleCommand {
    public static void m_139102_(CommandDispatcher<CommandSourceStack> p_139103_) {
        p_139103_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("title").requires(p_139107_ -> p_139107_.m_6761_(2))).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("targets", EntityArgument.m_91470_()).then(Commands.m_82127_("clear").executes(p_139134_ -> TitleCommand.m_139108_((CommandSourceStack)p_139134_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_139134_, "targets"))))).then(Commands.m_82127_("reset").executes(p_139132_ -> TitleCommand.m_139124_((CommandSourceStack)p_139132_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_139132_, "targets"))))).then(Commands.m_82127_("title").then(Commands.m_82129_("title", ComponentArgument.m_87114_()).executes(p_139130_ -> TitleCommand.m_142780_((CommandSourceStack)p_139130_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_139130_, "targets"), ComponentArgument.m_87117_((CommandContext<CommandSourceStack>)p_139130_, "title"), "title", ClientboundSetTitleTextPacket::new))))).then(Commands.m_82127_("subtitle").then(Commands.m_82129_("title", ComponentArgument.m_87114_()).executes(p_139128_ -> TitleCommand.m_142780_((CommandSourceStack)p_139128_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_139128_, "targets"), ComponentArgument.m_87117_((CommandContext<CommandSourceStack>)p_139128_, "title"), "subtitle", ClientboundSetSubtitleTextPacket::new))))).then(Commands.m_82127_("actionbar").then(Commands.m_82129_("title", ComponentArgument.m_87114_()).executes(p_139123_ -> TitleCommand.m_142780_((CommandSourceStack)p_139123_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_139123_, "targets"), ComponentArgument.m_87117_((CommandContext<CommandSourceStack>)p_139123_, "title"), "actionbar", ClientboundSetActionBarTextPacket::new))))).then(Commands.m_82127_("times").then(Commands.m_82129_("fadeIn", IntegerArgumentType.integer((int)0)).then(Commands.m_82129_("stay", IntegerArgumentType.integer((int)0)).then(Commands.m_82129_("fadeOut", IntegerArgumentType.integer((int)0)).executes(p_139105_ -> TitleCommand.m_139111_((CommandSourceStack)p_139105_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_139105_, "targets"), IntegerArgumentType.getInteger((CommandContext)p_139105_, (String)"fadeIn"), IntegerArgumentType.getInteger((CommandContext)p_139105_, (String)"stay"), IntegerArgumentType.getInteger((CommandContext)p_139105_, (String)"fadeOut")))))))));
    }

    private static int m_139108_(CommandSourceStack p_139109_, Collection<ServerPlayer> p_139110_) {
        ClientboundClearTitlesPacket $$2 = new ClientboundClearTitlesPacket(false);
        for (ServerPlayer $$3 : p_139110_) {
            $$3.f_8906_.m_9829_($$2);
        }
        if (p_139110_.size() == 1) {
            p_139109_.m_81354_(Component.m_237110_("commands.title.cleared.single", p_139110_.iterator().next().m_5446_()), true);
        } else {
            p_139109_.m_81354_(Component.m_237110_("commands.title.cleared.multiple", p_139110_.size()), true);
        }
        return p_139110_.size();
    }

    private static int m_139124_(CommandSourceStack p_139125_, Collection<ServerPlayer> p_139126_) {
        ClientboundClearTitlesPacket $$2 = new ClientboundClearTitlesPacket(true);
        for (ServerPlayer $$3 : p_139126_) {
            $$3.f_8906_.m_9829_($$2);
        }
        if (p_139126_.size() == 1) {
            p_139125_.m_81354_(Component.m_237110_("commands.title.reset.single", p_139126_.iterator().next().m_5446_()), true);
        } else {
            p_139125_.m_81354_(Component.m_237110_("commands.title.reset.multiple", p_139126_.size()), true);
        }
        return p_139126_.size();
    }

    private static int m_142780_(CommandSourceStack p_142781_, Collection<ServerPlayer> p_142782_, Component p_142783_, String p_142784_, Function<Component, Packet<?>> p_142785_) throws CommandSyntaxException {
        for (ServerPlayer $$5 : p_142782_) {
            $$5.f_8906_.m_9829_(p_142785_.apply(ComponentUtils.m_130731_(p_142781_, p_142783_, $$5, 0)));
        }
        if (p_142782_.size() == 1) {
            p_142781_.m_81354_(Component.m_237110_("commands.title.show." + p_142784_ + ".single", p_142782_.iterator().next().m_5446_()), true);
        } else {
            p_142781_.m_81354_(Component.m_237110_("commands.title.show." + p_142784_ + ".multiple", p_142782_.size()), true);
        }
        return p_142782_.size();
    }

    private static int m_139111_(CommandSourceStack p_139112_, Collection<ServerPlayer> p_139113_, int p_139114_, int p_139115_, int p_139116_) {
        ClientboundSetTitlesAnimationPacket $$5 = new ClientboundSetTitlesAnimationPacket(p_139114_, p_139115_, p_139116_);
        for (ServerPlayer $$6 : p_139113_) {
            $$6.f_8906_.m_9829_($$5);
        }
        if (p_139113_.size() == 1) {
            p_139112_.m_81354_(Component.m_237110_("commands.title.times.single", p_139113_.iterator().next().m_5446_()), true);
        } else {
            p_139112_.m_81354_(Component.m_237110_("commands.title.times.multiple", p_139113_.size()), true);
        }
        return p_139113_.size();
    }
}

