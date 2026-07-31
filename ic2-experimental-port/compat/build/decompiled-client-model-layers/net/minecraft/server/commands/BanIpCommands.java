/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  javax.annotation.Nullable
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.IpBanList;
import net.minecraft.server.players.IpBanListEntry;

public class BanIpCommands {
    public static final Pattern f_136523_ = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    private static final SimpleCommandExceptionType f_136524_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.banip.invalid"));
    private static final SimpleCommandExceptionType f_136525_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.banip.failed"));

    public static void m_136527_(CommandDispatcher<CommandSourceStack> p_136528_) {
        p_136528_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("ban-ip").requires(p_136532_ -> p_136532_.m_6761_(3))).then(((RequiredArgumentBuilder)Commands.m_82129_("target", StringArgumentType.word()).executes(p_136538_ -> BanIpCommands.m_136533_((CommandSourceStack)p_136538_.getSource(), StringArgumentType.getString((CommandContext)p_136538_, (String)"target"), null))).then(Commands.m_82129_("reason", MessageArgument.m_96832_()).executes(p_136530_ -> BanIpCommands.m_136533_((CommandSourceStack)p_136530_.getSource(), StringArgumentType.getString((CommandContext)p_136530_, (String)"target"), MessageArgument.m_96835_((CommandContext<CommandSourceStack>)p_136530_, "reason"))))));
    }

    private static int m_136533_(CommandSourceStack p_136534_, String p_136535_, @Nullable Component p_136536_) throws CommandSyntaxException {
        Matcher $$3 = f_136523_.matcher(p_136535_);
        if ($$3.matches()) {
            return BanIpCommands.m_136539_(p_136534_, p_136535_, p_136536_);
        }
        ServerPlayer $$4 = p_136534_.m_81377_().m_6846_().m_11255_(p_136535_);
        if ($$4 != null) {
            return BanIpCommands.m_136539_(p_136534_, $$4.m_9239_(), p_136536_);
        }
        throw f_136524_.create();
    }

    private static int m_136539_(CommandSourceStack p_136540_, String p_136541_, @Nullable Component p_136542_) throws CommandSyntaxException {
        IpBanList $$3 = p_136540_.m_81377_().m_6846_().m_11299_();
        if ($$3.m_11039_(p_136541_)) {
            throw f_136525_.create();
        }
        List<ServerPlayer> $$4 = p_136540_.m_81377_().m_6846_().m_11282_(p_136541_);
        IpBanListEntry $$5 = new IpBanListEntry(p_136541_, null, p_136540_.m_81368_(), null, p_136542_ == null ? null : p_136542_.getString());
        $$3.m_11381_($$5);
        p_136540_.m_81354_(Component.m_237110_("commands.banip.success", p_136541_, $$5.m_10962_()), true);
        if (!$$4.isEmpty()) {
            p_136540_.m_81354_(Component.m_237110_("commands.banip.info", $$4.size(), EntitySelector.m_175103_($$4)), true);
        }
        for (ServerPlayer $$6 : $$4) {
            $$6.f_8906_.m_9942_(Component.m_237115_("multiplayer.disconnect.ip_banned"));
        }
        return $$4.size();
    }
}

