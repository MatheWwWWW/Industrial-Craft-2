/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.nio.file.Path;
import java.nio.file.Paths;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.profiling.jfr.Environment;
import net.minecraft.util.profiling.jfr.JvmProfiler;

public class JfrCommand {
    private static final SimpleCommandExceptionType f_183641_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.jfr.start.failed"));
    private static final DynamicCommandExceptionType f_183642_ = new DynamicCommandExceptionType(p_183652_ -> Component.m_237110_("commands.jfr.dump.failed", p_183652_));

    private JfrCommand() {
    }

    public static void m_183645_(CommandDispatcher<CommandSourceStack> p_183646_) {
        p_183646_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("jfr").requires(p_183661_ -> p_183661_.m_6761_(4))).then(Commands.m_82127_("start").executes(p_183657_ -> JfrCommand.m_183649_((CommandSourceStack)p_183657_.getSource())))).then(Commands.m_82127_("stop").executes(p_183648_ -> JfrCommand.m_183658_((CommandSourceStack)p_183648_.getSource()))));
    }

    private static int m_183649_(CommandSourceStack p_183650_) throws CommandSyntaxException {
        Environment $$1 = Environment.m_185278_(p_183650_.m_81377_());
        if (!JvmProfiler.f_185340_.m_183425_($$1)) {
            throw f_183641_.create();
        }
        p_183650_.m_81354_(Component.m_237115_("commands.jfr.started"), false);
        return 1;
    }

    private static int m_183658_(CommandSourceStack p_183659_) throws CommandSyntaxException {
        try {
            Path $$1 = Paths.get(".", new String[0]).relativize(JvmProfiler.f_185340_.m_183243_().normalize());
            Path $$2 = !p_183659_.m_81377_().m_6992_() || SharedConstants.f_136183_ ? $$1.toAbsolutePath() : $$1;
            MutableComponent $$3 = Component.m_237113_($$1.toString()).m_130940_(ChatFormatting.UNDERLINE).m_130938_(p_183655_ -> p_183655_.m_131142_(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, $$2.toString())).m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, Component.m_237115_("chat.copy.click"))));
            p_183659_.m_81354_(Component.m_237110_("commands.jfr.stopped", $$3), false);
            return 1;
        }
        catch (Throwable $$4) {
            throw f_183642_.create((Object)$$4.getMessage());
        }
    }
}

