/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.ParseResults
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.context.ParsedCommandNode
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.tree.CommandNode
 */
package net.minecraft.server.commands;

import com.google.common.collect.Iterables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.CommandNode;
import java.util.Map;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class HelpCommand {
    private static final SimpleCommandExceptionType f_137785_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.help.failed"));

    public static void m_137787_(CommandDispatcher<CommandSourceStack> p_137788_) {
        p_137788_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("help").executes(p_137794_ -> {
            Map $$2 = p_137788_.getSmartUsage((CommandNode)p_137788_.getRoot(), (Object)((CommandSourceStack)p_137794_.getSource()));
            for (String $$3 : $$2.values()) {
                ((CommandSourceStack)p_137794_.getSource()).m_81354_(Component.m_237113_("/" + $$3), false);
            }
            return $$2.size();
        })).then(Commands.m_82129_("command", StringArgumentType.greedyString()).executes(p_137791_ -> {
            ParseResults $$2 = p_137788_.parse(StringArgumentType.getString((CommandContext)p_137791_, (String)"command"), (Object)((CommandSourceStack)p_137791_.getSource()));
            if ($$2.getContext().getNodes().isEmpty()) {
                throw f_137785_.create();
            }
            Map $$3 = p_137788_.getSmartUsage(((ParsedCommandNode)Iterables.getLast((Iterable)$$2.getContext().getNodes())).getNode(), (Object)((CommandSourceStack)p_137791_.getSource()));
            for (String $$4 : $$3.values()) {
                ((CommandSourceStack)p_137791_.getSource()).m_81354_(Component.m_237113_("/" + $$2.getReader().getString() + " " + $$4), false);
            }
            return $$3.size();
        })));
    }
}

