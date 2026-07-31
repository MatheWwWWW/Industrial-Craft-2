/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.TimeArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

public class TimeCommand {
    public static void m_139071_(CommandDispatcher<CommandSourceStack> p_139072_) {
        p_139072_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("time").requires(p_139076_ -> p_139076_.m_6761_(2))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("set").then(Commands.m_82127_("day").executes(p_139101_ -> TimeCommand.m_139077_((CommandSourceStack)p_139101_.getSource(), 1000)))).then(Commands.m_82127_("noon").executes(p_139099_ -> TimeCommand.m_139077_((CommandSourceStack)p_139099_.getSource(), 6000)))).then(Commands.m_82127_("night").executes(p_139097_ -> TimeCommand.m_139077_((CommandSourceStack)p_139097_.getSource(), 13000)))).then(Commands.m_82127_("midnight").executes(p_139095_ -> TimeCommand.m_139077_((CommandSourceStack)p_139095_.getSource(), 18000)))).then(Commands.m_82129_("time", TimeArgument.m_113037_()).executes(p_139093_ -> TimeCommand.m_139077_((CommandSourceStack)p_139093_.getSource(), IntegerArgumentType.getInteger((CommandContext)p_139093_, (String)"time")))))).then(Commands.m_82127_("add").then(Commands.m_82129_("time", TimeArgument.m_113037_()).executes(p_139091_ -> TimeCommand.m_139082_((CommandSourceStack)p_139091_.getSource(), IntegerArgumentType.getInteger((CommandContext)p_139091_, (String)"time")))))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("query").then(Commands.m_82127_("daytime").executes(p_139086_ -> TimeCommand.m_139087_((CommandSourceStack)p_139086_.getSource(), TimeCommand.m_139069_(((CommandSourceStack)p_139086_.getSource()).m_81372_()))))).then(Commands.m_82127_("gametime").executes(p_139081_ -> TimeCommand.m_139087_((CommandSourceStack)p_139081_.getSource(), (int)(((CommandSourceStack)p_139081_.getSource()).m_81372_().m_46467_() % Integer.MAX_VALUE))))).then(Commands.m_82127_("day").executes(p_139074_ -> TimeCommand.m_139087_((CommandSourceStack)p_139074_.getSource(), (int)(((CommandSourceStack)p_139074_.getSource()).m_81372_().m_46468_() / 24000L % Integer.MAX_VALUE))))));
    }

    private static int m_139069_(ServerLevel p_139070_) {
        return (int)(p_139070_.m_46468_() % 24000L);
    }

    private static int m_139087_(CommandSourceStack p_139088_, int p_139089_) {
        p_139088_.m_81354_(Component.m_237110_("commands.time.query", p_139089_), false);
        return p_139089_;
    }

    public static int m_139077_(CommandSourceStack p_139078_, int p_139079_) {
        for (ServerLevel $$2 : p_139078_.m_81377_().m_129785_()) {
            $$2.m_8615_(p_139079_);
        }
        p_139078_.m_81354_(Component.m_237110_("commands.time.set", p_139079_), true);
        return TimeCommand.m_139069_(p_139078_.m_81372_());
    }

    public static int m_139082_(CommandSourceStack p_139083_, int p_139084_) {
        for (ServerLevel $$2 : p_139083_.m_81377_().m_129785_()) {
            $$2.m_8615_($$2.m_46468_() + (long)p_139084_);
        }
        int $$3 = TimeCommand.m_139069_(p_139083_.m_81372_());
        p_139083_.m_81354_(Component.m_237110_("commands.time.set", $$3), true);
        return $$3;
    }
}

