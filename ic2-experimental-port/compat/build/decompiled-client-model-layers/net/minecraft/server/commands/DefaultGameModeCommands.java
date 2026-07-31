/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;

public class DefaultGameModeCommands {
    public static void m_136926_(CommandDispatcher<CommandSourceStack> p_136927_) {
        LiteralArgumentBuilder $$1 = (LiteralArgumentBuilder)Commands.m_82127_("defaultgamemode").requires(p_136929_ -> p_136929_.m_6761_(2));
        for (GameType $$2 : GameType.values()) {
            $$1.then(Commands.m_82127_($$2.m_46405_()).executes(p_136925_ -> DefaultGameModeCommands.m_136930_((CommandSourceStack)p_136925_.getSource(), $$2)));
        }
        p_136927_.register($$1);
    }

    private static int m_136930_(CommandSourceStack p_136931_, GameType p_136932_) {
        int $$2 = 0;
        MinecraftServer $$3 = p_136931_.m_81377_();
        $$3.m_7835_(p_136932_);
        GameType $$4 = $$3.m_142359_();
        if ($$4 != null) {
            for (ServerPlayer $$5 : $$3.m_6846_().m_11314_()) {
                if (!$$5.m_143403_($$4)) continue;
                ++$$2;
            }
        }
        p_136931_.m_81354_(Component.m_237110_("commands.defaultgamemode.success", p_136932_.m_151499_()), true);
        return $$2;
    }
}

