/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 */
package net.minecraft.server.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.server.players.UserBanList;

public class PardonCommand {
    private static final SimpleCommandExceptionType f_138091_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.pardon.failed"));

    public static void m_138093_(CommandDispatcher<CommandSourceStack> p_138094_) {
        p_138094_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("pardon").requires(p_138101_ -> p_138101_.m_6761_(3))).then(Commands.m_82129_("targets", GameProfileArgument.m_94584_()).suggests((p_138098_, p_138099_) -> SharedSuggestionProvider.m_82967_(((CommandSourceStack)p_138098_.getSource()).m_81377_().m_6846_().m_11295_().m_5875_(), p_138099_)).executes(p_138096_ -> PardonCommand.m_138102_((CommandSourceStack)p_138096_.getSource(), GameProfileArgument.m_94590_((CommandContext<CommandSourceStack>)p_138096_, "targets")))));
    }

    private static int m_138102_(CommandSourceStack p_138103_, Collection<GameProfile> p_138104_) throws CommandSyntaxException {
        UserBanList $$2 = p_138103_.m_81377_().m_6846_().m_11295_();
        int $$3 = 0;
        for (GameProfile $$4 : p_138104_) {
            if (!$$2.m_11406_($$4)) continue;
            $$2.m_11393_($$4);
            ++$$3;
            p_138103_.m_81354_(Component.m_237110_("commands.pardon.success", ComponentUtils.m_130727_($$4)), true);
        }
        if ($$3 == 0) {
            throw f_138091_.create();
        }
        return $$3;
    }
}

