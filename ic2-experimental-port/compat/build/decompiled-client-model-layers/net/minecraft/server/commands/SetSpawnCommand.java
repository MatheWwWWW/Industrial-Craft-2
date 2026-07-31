/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.AngleArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class SetSpawnCommand {
    public static void m_138643_(CommandDispatcher<CommandSourceStack> p_138644_) {
        p_138644_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("spawnpoint").requires(p_138648_ -> p_138648_.m_6761_(2))).executes(p_138659_ -> SetSpawnCommand.m_138649_((CommandSourceStack)p_138659_.getSource(), Collections.singleton(((CommandSourceStack)p_138659_.getSource()).m_81375_()), new BlockPos(((CommandSourceStack)p_138659_.getSource()).m_81371_()), 0.0f))).then(((RequiredArgumentBuilder)Commands.m_82129_("targets", EntityArgument.m_91470_()).executes(p_138657_ -> SetSpawnCommand.m_138649_((CommandSourceStack)p_138657_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_138657_, "targets"), new BlockPos(((CommandSourceStack)p_138657_.getSource()).m_81371_()), 0.0f))).then(((RequiredArgumentBuilder)Commands.m_82129_("pos", BlockPosArgument.m_118239_()).executes(p_138655_ -> SetSpawnCommand.m_138649_((CommandSourceStack)p_138655_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_138655_, "targets"), BlockPosArgument.m_174395_((CommandContext<CommandSourceStack>)p_138655_, "pos"), 0.0f))).then(Commands.m_82129_("angle", AngleArgument.m_83807_()).executes(p_138646_ -> SetSpawnCommand.m_138649_((CommandSourceStack)p_138646_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_138646_, "targets"), BlockPosArgument.m_174395_((CommandContext<CommandSourceStack>)p_138646_, "pos"), AngleArgument.m_83810_((CommandContext<CommandSourceStack>)p_138646_, "angle")))))));
    }

    private static int m_138649_(CommandSourceStack p_138650_, Collection<ServerPlayer> p_138651_, BlockPos p_138652_, float p_138653_) {
        ResourceKey<Level> $$4 = p_138650_.m_81372_().m_46472_();
        for (ServerPlayer $$5 : p_138651_) {
            $$5.m_9158_($$4, p_138652_, p_138653_, true, false);
        }
        String $$6 = $$4.m_135782_().toString();
        if (p_138651_.size() == 1) {
            p_138650_.m_81354_(Component.m_237110_("commands.spawnpoint.success.single", p_138652_.m_123341_(), p_138652_.m_123342_(), p_138652_.m_123343_(), Float.valueOf(p_138653_), $$6, p_138651_.iterator().next().m_5446_()), true);
        } else {
            p_138650_.m_81354_(Component.m_237110_("commands.spawnpoint.success.multiple", p_138652_.m_123341_(), p_138652_.m_123342_(), p_138652_.m_123343_(), Float.valueOf(p_138653_), $$6, p_138651_.size()), true);
        }
        return p_138651_.size();
    }
}

