/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 */
package net.minecraft.server.commands;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;

public class KillCommand {
    public static void m_137807_(CommandDispatcher<CommandSourceStack> p_137808_) {
        p_137808_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("kill").requires(p_137812_ -> p_137812_.m_6761_(2))).executes(p_137817_ -> KillCommand.m_137813_((CommandSourceStack)p_137817_.getSource(), (Collection<? extends Entity>)ImmutableList.of((Object)((CommandSourceStack)p_137817_.getSource()).m_81374_())))).then(Commands.m_82129_("targets", EntityArgument.m_91460_()).executes(p_137810_ -> KillCommand.m_137813_((CommandSourceStack)p_137810_.getSource(), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_137810_, "targets")))));
    }

    private static int m_137813_(CommandSourceStack p_137814_, Collection<? extends Entity> p_137815_) {
        for (Entity entity : p_137815_) {
            entity.m_6074_();
        }
        if (p_137815_.size() == 1) {
            p_137814_.m_81354_(Component.m_237110_("commands.kill.success.single", p_137815_.iterator().next().m_5446_()), true);
        } else {
            p_137814_.m_81354_(Component.m_237110_("commands.kill.success.multiple", p_137815_.size()), true);
        }
        return p_137815_.size();
    }
}

