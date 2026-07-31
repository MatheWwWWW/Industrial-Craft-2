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
import java.util.List;
import java.util.function.Function;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;

public class ListPlayersCommand {
    public static void m_137820_(CommandDispatcher<CommandSourceStack> p_137821_) {
        p_137821_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("list").executes(p_137830_ -> ListPlayersCommand.m_137824_((CommandSourceStack)p_137830_.getSource()))).then(Commands.m_82127_("uuids").executes(p_137823_ -> ListPlayersCommand.m_137831_((CommandSourceStack)p_137823_.getSource()))));
    }

    private static int m_137824_(CommandSourceStack p_137825_) {
        return ListPlayersCommand.m_137826_(p_137825_, Player::m_5446_);
    }

    private static int m_137831_(CommandSourceStack p_137832_) {
        return ListPlayersCommand.m_137826_(p_137832_, p_137819_ -> Component.m_237110_("commands.list.nameAndId", p_137819_.m_7755_(), p_137819_.m_36316_().getId()));
    }

    private static int m_137826_(CommandSourceStack p_137827_, Function<ServerPlayer, Component> p_137828_) {
        PlayerList $$2 = p_137827_.m_81377_().m_6846_();
        List<ServerPlayer> $$3 = $$2.m_11314_();
        Component $$4 = ComponentUtils.m_178440_($$3, p_137828_);
        p_137827_.m_81354_(Component.m_237110_("commands.list.players", $$3.size(), $$2.m_11310_(), $$4), false);
        return $$3.size();
    }
}

