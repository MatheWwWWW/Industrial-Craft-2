/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.regex.Matcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.BanIpCommands;
import net.minecraft.server.players.IpBanList;

public class PardonIpCommand {
    private static final SimpleCommandExceptionType f_138105_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.pardonip.invalid"));
    private static final SimpleCommandExceptionType f_138106_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.pardonip.failed"));

    public static void m_138108_(CommandDispatcher<CommandSourceStack> p_138109_) {
        p_138109_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("pardon-ip").requires(p_138116_ -> p_138116_.m_6761_(3))).then(Commands.m_82129_("target", StringArgumentType.word()).suggests((p_138113_, p_138114_) -> SharedSuggestionProvider.m_82967_(((CommandSourceStack)p_138113_.getSource()).m_81377_().m_6846_().m_11299_().m_5875_(), p_138114_)).executes(p_138111_ -> PardonIpCommand.m_138117_((CommandSourceStack)p_138111_.getSource(), StringArgumentType.getString((CommandContext)p_138111_, (String)"target")))));
    }

    private static int m_138117_(CommandSourceStack p_138118_, String p_138119_) throws CommandSyntaxException {
        Matcher $$2 = BanIpCommands.f_136523_.matcher(p_138119_);
        if (!$$2.matches()) {
            throw f_138105_.create();
        }
        IpBanList $$3 = p_138118_.m_81377_().m_6846_().m_11299_();
        if (!$$3.m_11039_(p_138119_)) {
            throw f_138106_.create();
        }
        $$3.m_11393_(p_138119_);
        p_138118_.m_81354_(Component.m_237110_("commands.pardonip.success", p_138119_), true);
        return 1;
    }
}

