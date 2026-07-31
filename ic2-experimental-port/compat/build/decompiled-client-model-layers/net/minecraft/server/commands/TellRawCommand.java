/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.server.level.ServerPlayer;

public class TellRawCommand {
    public static void m_139063_(CommandDispatcher<CommandSourceStack> p_139064_) {
        p_139064_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("tellraw").requires(p_139068_ -> p_139068_.m_6761_(2))).then(Commands.m_82129_("targets", EntityArgument.m_91470_()).then(Commands.m_82129_("message", ComponentArgument.m_87114_()).executes(p_139066_ -> {
            int $$1 = 0;
            for (ServerPlayer $$2 : EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_139066_, "targets")) {
                $$2.m_240418_(ComponentUtils.m_130731_((CommandSourceStack)p_139066_.getSource(), ComponentArgument.m_87117_((CommandContext<CommandSourceStack>)p_139066_, "message"), $$2, 0), false);
                ++$$1;
            }
            return $$1;
        }))));
    }
}

