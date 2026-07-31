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
import net.minecraft.server.players.PlayerList;

public class DeOpCommands {
    private static final SimpleCommandExceptionType f_136886_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.deop.failed"));

    public static void m_136888_(CommandDispatcher<CommandSourceStack> p_136889_) {
        p_136889_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("deop").requires(p_136896_ -> p_136896_.m_6761_(3))).then(Commands.m_82129_("targets", GameProfileArgument.m_94584_()).suggests((p_136893_, p_136894_) -> SharedSuggestionProvider.m_82967_(((CommandSourceStack)p_136893_.getSource()).m_81377_().m_6846_().m_11308_(), p_136894_)).executes(p_136891_ -> DeOpCommands.m_136897_((CommandSourceStack)p_136891_.getSource(), GameProfileArgument.m_94590_((CommandContext<CommandSourceStack>)p_136891_, "targets")))));
    }

    private static int m_136897_(CommandSourceStack p_136898_, Collection<GameProfile> p_136899_) throws CommandSyntaxException {
        PlayerList $$2 = p_136898_.m_81377_().m_6846_();
        int $$3 = 0;
        for (GameProfile $$4 : p_136899_) {
            if (!$$2.m_11303_($$4)) continue;
            $$2.m_5750_($$4);
            ++$$3;
            p_136898_.m_81354_(Component.m_237110_("commands.deop.success", p_136899_.iterator().next().getName()), true);
        }
        if ($$3 == 0) {
            throw f_136886_.create();
        }
        p_136898_.m_81377_().m_129849_(p_136898_);
        return $$3;
    }
}

