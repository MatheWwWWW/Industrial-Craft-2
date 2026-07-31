/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class StopCommand {
    public static void m_138785_(CommandDispatcher<CommandSourceStack> p_138786_) {
        p_138786_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("stop").requires(p_138790_ -> p_138790_.m_6761_(4))).executes(p_138788_ -> {
            ((CommandSourceStack)p_138788_.getSource()).m_81354_(Component.m_237115_("commands.stop.stopping"), true);
            ((CommandSourceStack)p_138788_.getSource()).m_81377_().m_7570_(false);
            return 1;
        }));
    }
}

