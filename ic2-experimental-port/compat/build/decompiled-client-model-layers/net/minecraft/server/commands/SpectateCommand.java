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
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  javax.annotation.Nullable
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameType;

public class SpectateCommand {
    private static final SimpleCommandExceptionType f_138674_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.spectate.self"));
    private static final DynamicCommandExceptionType f_138675_ = new DynamicCommandExceptionType(p_138688_ -> Component.m_237110_("commands.spectate.not_spectator", p_138688_));

    public static void m_138677_(CommandDispatcher<CommandSourceStack> p_138678_) {
        p_138678_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("spectate").requires(p_138682_ -> p_138682_.m_6761_(2))).executes(p_138692_ -> SpectateCommand.m_138683_((CommandSourceStack)p_138692_.getSource(), null, ((CommandSourceStack)p_138692_.getSource()).m_81375_()))).then(((RequiredArgumentBuilder)Commands.m_82129_("target", EntityArgument.m_91449_()).executes(p_138690_ -> SpectateCommand.m_138683_((CommandSourceStack)p_138690_.getSource(), EntityArgument.m_91452_((CommandContext<CommandSourceStack>)p_138690_, "target"), ((CommandSourceStack)p_138690_.getSource()).m_81375_()))).then(Commands.m_82129_("player", EntityArgument.m_91466_()).executes(p_138680_ -> SpectateCommand.m_138683_((CommandSourceStack)p_138680_.getSource(), EntityArgument.m_91452_((CommandContext<CommandSourceStack>)p_138680_, "target"), EntityArgument.m_91474_((CommandContext<CommandSourceStack>)p_138680_, "player"))))));
    }

    private static int m_138683_(CommandSourceStack p_138684_, @Nullable Entity p_138685_, ServerPlayer p_138686_) throws CommandSyntaxException {
        if (p_138686_ == p_138685_) {
            throw f_138674_.create();
        }
        if (p_138686_.f_8941_.m_9290_() != GameType.SPECTATOR) {
            throw f_138675_.create((Object)p_138686_.m_5446_());
        }
        p_138686_.m_9213_(p_138685_);
        if (p_138685_ != null) {
            p_138684_.m_81354_(Component.m_237110_("commands.spectate.success.started", p_138685_.m_5446_()), false);
        } else {
            p_138684_.m_81354_(Component.m_237115_("commands.spectate.success.stopped"), false);
        }
        return 1;
    }
}

