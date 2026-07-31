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
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.players.PlayerList;

public class SayCommand {
    public static void m_138409_(CommandDispatcher<CommandSourceStack> p_138410_) {
        p_138410_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("say").requires(p_138414_ -> p_138414_.m_6761_(2))).then(Commands.m_82129_("message", MessageArgument.m_96832_()).executes(p_214721_ -> {
            MessageArgument.ChatMessage $$1 = MessageArgument.m_232163_((CommandContext<CommandSourceStack>)p_214721_, "message");
            CommandSourceStack $$2 = (CommandSourceStack)p_214721_.getSource();
            PlayerList $$3 = $$2.m_81377_().m_6846_();
            $$1.m_241987_($$2, p_214719_ -> $$3.m_243063_((PlayerChatMessage)p_214719_, $$2, ChatType.m_241073_(ChatType.f_237006_, $$2)));
            return 1;
        })));
    }
}

