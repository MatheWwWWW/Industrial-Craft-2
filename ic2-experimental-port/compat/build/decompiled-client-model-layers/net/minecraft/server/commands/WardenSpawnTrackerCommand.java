/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 */
package net.minecraft.server.commands;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class WardenSpawnTrackerCommand {
    public static void m_214773_(CommandDispatcher<CommandSourceStack> p_214774_) {
        p_214774_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("warden_spawn_tracker").requires(p_214778_ -> p_214778_.m_6761_(2))).then(Commands.m_82127_("clear").executes(p_214787_ -> WardenSpawnTrackerCommand.m_214779_((CommandSourceStack)p_214787_.getSource(), (Collection<? extends Player>)ImmutableList.of((Object)((CommandSourceStack)p_214787_.getSource()).m_81375_()))))).then(Commands.m_82127_("set").then(Commands.m_82129_("warning_level", IntegerArgumentType.integer((int)0, (int)4)).executes(p_214776_ -> WardenSpawnTrackerCommand.m_214782_((CommandSourceStack)p_214776_.getSource(), (Collection<? extends Player>)ImmutableList.of((Object)((CommandSourceStack)p_214776_.getSource()).m_81375_()), IntegerArgumentType.getInteger((CommandContext)p_214776_, (String)"warning_level"))))));
    }

    private static int m_214782_(CommandSourceStack p_214783_, Collection<? extends Player> p_214784_, int p_214785_) {
        for (Player player : p_214784_) {
            player.m_219758_().m_219572_(p_214785_);
        }
        if (p_214784_.size() == 1) {
            p_214783_.m_81354_(Component.m_237110_("commands.warden_spawn_tracker.set.success.single", p_214784_.iterator().next().m_5446_()), true);
        } else {
            p_214783_.m_81354_(Component.m_237110_("commands.warden_spawn_tracker.set.success.multiple", p_214784_.size()), true);
        }
        return p_214784_.size();
    }

    private static int m_214779_(CommandSourceStack p_214780_, Collection<? extends Player> p_214781_) {
        for (Player player : p_214781_) {
            player.m_219758_().m_219593_();
        }
        if (p_214781_.size() == 1) {
            p_214780_.m_81354_(Component.m_237110_("commands.warden_spawn_tracker.clear.success.single", p_214781_.iterator().next().m_5446_()), true);
        } else {
            p_214780_.m_81354_(Component.m_237110_("commands.warden_spawn_tracker.clear.success.multiple", p_214781_.size()), true);
        }
        return p_214781_.size();
    }
}

