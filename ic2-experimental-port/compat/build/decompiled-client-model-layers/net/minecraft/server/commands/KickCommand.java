/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class KickCommand {
    public static void m_137795_(CommandDispatcher<CommandSourceStack> p_137796_) {
        p_137796_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("kick").requires(p_137800_ -> p_137800_.m_6761_(3))).then(((RequiredArgumentBuilder)Commands.m_82129_("targets", EntityArgument.m_91470_()).executes(p_137806_ -> KickCommand.m_137801_((CommandSourceStack)p_137806_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_137806_, "targets"), Component.m_237115_("multiplayer.disconnect.kicked")))).then(Commands.m_82129_("reason", MessageArgument.m_96832_()).executes(p_137798_ -> KickCommand.m_137801_((CommandSourceStack)p_137798_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_137798_, "targets"), MessageArgument.m_96835_((CommandContext<CommandSourceStack>)p_137798_, "reason"))))));
    }

    private static int m_137801_(CommandSourceStack p_137802_, Collection<ServerPlayer> p_137803_, Component p_137804_) {
        for (ServerPlayer $$3 : p_137803_) {
            $$3.f_8906_.m_9942_(p_137804_);
            p_137802_.m_81354_(Component.m_237110_("commands.kick.success", $$3.m_5446_(), p_137804_), true);
        }
        return p_137803_.size();
    }
}

