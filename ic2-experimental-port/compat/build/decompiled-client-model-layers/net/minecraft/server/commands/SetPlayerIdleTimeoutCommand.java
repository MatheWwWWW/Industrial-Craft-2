/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class SetPlayerIdleTimeoutCommand {
    public static void m_138634_(CommandDispatcher<CommandSourceStack> p_138635_) {
        p_138635_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("setidletimeout").requires(p_138639_ -> p_138639_.m_6761_(3))).then(Commands.m_82129_("minutes", IntegerArgumentType.integer((int)0)).executes(p_138637_ -> SetPlayerIdleTimeoutCommand.m_138640_((CommandSourceStack)p_138637_.getSource(), IntegerArgumentType.getInteger((CommandContext)p_138637_, (String)"minutes")))));
    }

    private static int m_138640_(CommandSourceStack p_138641_, int p_138642_) {
        p_138641_.m_81377_().m_7196_(p_138642_);
        p_138641_.m_81354_(Component.m_237110_("commands.setidletimeout.success", p_138642_), true);
        return p_138642_;
    }
}

