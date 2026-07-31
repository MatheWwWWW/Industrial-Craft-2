/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;

public class GameModeCommand {
    public static final int f_180230_ = 2;

    public static void m_137729_(CommandDispatcher<CommandSourceStack> p_137730_) {
        LiteralArgumentBuilder $$1 = (LiteralArgumentBuilder)Commands.m_82127_("gamemode").requires(p_137736_ -> p_137736_.m_6761_(2));
        for (GameType $$2 : GameType.values()) {
            $$1.then(((LiteralArgumentBuilder)Commands.m_82127_($$2.m_46405_()).executes(p_137743_ -> GameModeCommand.m_137731_((CommandContext<CommandSourceStack>)p_137743_, Collections.singleton(((CommandSourceStack)p_137743_.getSource()).m_81375_()), $$2))).then(Commands.m_82129_("target", EntityArgument.m_91470_()).executes(p_137728_ -> GameModeCommand.m_137731_((CommandContext<CommandSourceStack>)p_137728_, EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_137728_, "target"), $$2))));
        }
        p_137730_.register($$1);
    }

    private static void m_137737_(CommandSourceStack p_137738_, ServerPlayer p_137739_, GameType p_137740_) {
        MutableComponent $$3 = Component.m_237115_("gameMode." + p_137740_.m_46405_());
        if (p_137738_.m_81373_() == p_137739_) {
            p_137738_.m_81354_(Component.m_237110_("commands.gamemode.success.self", $$3), true);
        } else {
            if (p_137738_.m_81372_().m_46469_().m_46207_(GameRules.f_46144_)) {
                p_137739_.m_213846_(Component.m_237110_("gameMode.changed", $$3));
            }
            p_137738_.m_81354_(Component.m_237110_("commands.gamemode.success.other", p_137739_.m_5446_(), $$3), true);
        }
    }

    private static int m_137731_(CommandContext<CommandSourceStack> p_137732_, Collection<ServerPlayer> p_137733_, GameType p_137734_) {
        int $$3 = 0;
        for (ServerPlayer $$4 : p_137733_) {
            if (!$$4.m_143403_(p_137734_)) continue;
            GameModeCommand.m_137737_((CommandSourceStack)p_137732_.getSource(), $$4, p_137734_);
            ++$$3;
        }
        return $$3;
    }
}

