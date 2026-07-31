/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  javax.annotation.Nullable
 */
package net.minecraft.server.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.UserBanList;
import net.minecraft.server.players.UserBanListEntry;

public class BanPlayerCommands {
    private static final SimpleCommandExceptionType f_136556_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.ban.failed"));

    public static void m_136558_(CommandDispatcher<CommandSourceStack> p_136559_) {
        p_136559_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("ban").requires(p_136563_ -> p_136563_.m_6761_(3))).then(((RequiredArgumentBuilder)Commands.m_82129_("targets", GameProfileArgument.m_94584_()).executes(p_136569_ -> BanPlayerCommands.m_136564_((CommandSourceStack)p_136569_.getSource(), GameProfileArgument.m_94590_((CommandContext<CommandSourceStack>)p_136569_, "targets"), null))).then(Commands.m_82129_("reason", MessageArgument.m_96832_()).executes(p_136561_ -> BanPlayerCommands.m_136564_((CommandSourceStack)p_136561_.getSource(), GameProfileArgument.m_94590_((CommandContext<CommandSourceStack>)p_136561_, "targets"), MessageArgument.m_96835_((CommandContext<CommandSourceStack>)p_136561_, "reason"))))));
    }

    private static int m_136564_(CommandSourceStack p_136565_, Collection<GameProfile> p_136566_, @Nullable Component p_136567_) throws CommandSyntaxException {
        UserBanList $$3 = p_136565_.m_81377_().m_6846_().m_11295_();
        int $$4 = 0;
        for (GameProfile $$5 : p_136566_) {
            if ($$3.m_11406_($$5)) continue;
            UserBanListEntry $$6 = new UserBanListEntry($$5, null, p_136565_.m_81368_(), null, p_136567_ == null ? null : p_136567_.getString());
            $$3.m_11381_($$6);
            ++$$4;
            p_136565_.m_81354_(Component.m_237110_("commands.ban.success", ComponentUtils.m_130727_($$5), $$6.m_10962_()), true);
            ServerPlayer $$7 = p_136565_.m_81377_().m_6846_().m_11259_($$5.getId());
            if ($$7 == null) continue;
            $$7.f_8906_.m_9942_(Component.m_237115_("multiplayer.disconnect.banned"));
        }
        if ($$4 == 0) {
            throw f_136556_.create();
        }
        return $$4;
    }
}

