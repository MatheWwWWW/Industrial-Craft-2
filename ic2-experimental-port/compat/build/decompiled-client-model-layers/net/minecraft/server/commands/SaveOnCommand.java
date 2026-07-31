/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

public class SaveOnCommand {
    private static final SimpleCommandExceptionType f_138290_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.save.alreadyOn"));

    public static void m_138292_(CommandDispatcher<CommandSourceStack> p_138293_) {
        p_138293_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("save-on").requires(p_138297_ -> p_138297_.m_6761_(4))).executes(p_138295_ -> {
            CommandSourceStack $$1 = (CommandSourceStack)p_138295_.getSource();
            boolean $$2 = false;
            for (ServerLevel $$3 : $$1.m_81377_().m_129785_()) {
                if ($$3 == null || !$$3.f_8564_) continue;
                $$3.f_8564_ = false;
                $$2 = true;
            }
            if (!$$2) {
                throw f_138290_.create();
            }
            $$1.m_81354_(Component.m_237115_("commands.save.enabled"), true);
            return 1;
        }));
    }
}

