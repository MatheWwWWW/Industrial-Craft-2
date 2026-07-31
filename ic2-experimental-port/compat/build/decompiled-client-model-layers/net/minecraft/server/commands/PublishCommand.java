/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.util.HttpUtil;

public class PublishCommand {
    private static final SimpleCommandExceptionType f_138181_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.publish.failed"));
    private static final DynamicCommandExceptionType f_138182_ = new DynamicCommandExceptionType(p_138194_ -> Component.m_237110_("commands.publish.alreadyPublished", p_138194_));

    public static void m_138184_(CommandDispatcher<CommandSourceStack> p_138185_) {
        p_138185_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("publish").requires(p_138189_ -> p_138189_.m_6761_(4))).executes(p_138196_ -> PublishCommand.m_138190_((CommandSourceStack)p_138196_.getSource(), HttpUtil.m_13939_()))).then(Commands.m_82129_("port", IntegerArgumentType.integer((int)0, (int)65535)).executes(p_138187_ -> PublishCommand.m_138190_((CommandSourceStack)p_138187_.getSource(), IntegerArgumentType.getInteger((CommandContext)p_138187_, (String)"port")))));
    }

    private static int m_138190_(CommandSourceStack p_138191_, int p_138192_) throws CommandSyntaxException {
        if (p_138191_.m_81377_().m_6992_()) {
            throw f_138182_.create((Object)p_138191_.m_81377_().m_7010_());
        }
        if (!p_138191_.m_81377_().m_7386_(null, false, p_138192_)) {
            throw f_138181_.create();
        }
        p_138191_.m_81354_(Component.m_237110_("commands.publish.success", p_138192_), true);
        return p_138192_;
    }
}

