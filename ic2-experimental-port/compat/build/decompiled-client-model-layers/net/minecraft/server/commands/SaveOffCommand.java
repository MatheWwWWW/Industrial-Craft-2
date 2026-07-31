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

public class SaveOffCommand {
    private static final SimpleCommandExceptionType f_138282_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.save.alreadyOff"));

    public static void m_138284_(CommandDispatcher<CommandSourceStack> p_138285_) {
        p_138285_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("save-off").requires(p_138289_ -> p_138289_.m_6761_(4))).executes(p_138287_ -> {
            CommandSourceStack $$1 = (CommandSourceStack)p_138287_.getSource();
            boolean $$2 = false;
            for (ServerLevel $$3 : $$1.m_81377_().m_129785_()) {
                if ($$3 == null || $$3.f_8564_) continue;
                $$3.f_8564_ = true;
                $$2 = true;
            }
            if (!$$2) {
                throw f_138282_.create();
            }
            $$1.m_81354_(Component.m_237115_("commands.save.disabled"), true);
            return 1;
        }));
    }
}

