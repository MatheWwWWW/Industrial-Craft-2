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

public class OpCommand {
    private static final SimpleCommandExceptionType f_138072_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.op.failed"));

    public static void m_138079_(CommandDispatcher<CommandSourceStack> p_138080_) {
        p_138080_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("op").requires(p_138087_ -> p_138087_.m_6761_(3))).then(Commands.m_82129_("targets", GameProfileArgument.m_94584_()).suggests((p_138084_, p_138085_) -> {
            PlayerList $$2 = ((CommandSourceStack)p_138084_.getSource()).m_81377_().m_6846_();
            return SharedSuggestionProvider.m_82981_($$2.m_11314_().stream().filter(p_180428_ -> !$$2.m_11303_(p_180428_.m_36316_())).map(p_180425_ -> p_180425_.m_36316_().getName()), p_138085_);
        }).executes(p_138082_ -> OpCommand.m_138088_((CommandSourceStack)p_138082_.getSource(), GameProfileArgument.m_94590_((CommandContext<CommandSourceStack>)p_138082_, "targets")))));
    }

    private static int m_138088_(CommandSourceStack p_138089_, Collection<GameProfile> p_138090_) throws CommandSyntaxException {
        PlayerList $$2 = p_138089_.m_81377_().m_6846_();
        int $$3 = 0;
        for (GameProfile $$4 : p_138090_) {
            if ($$2.m_11303_($$4)) continue;
            $$2.m_5749_($$4);
            ++$$3;
            p_138089_.m_81354_(Component.m_237110_("commands.op.success", p_138090_.iterator().next().getName()), true);
        }
        if ($$3 == 0) {
            throw f_138072_.create();
        }
        return $$3;
    }
}

