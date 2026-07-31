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
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.AngleArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

public class SetWorldSpawnCommand {
    public static void m_138660_(CommandDispatcher<CommandSourceStack> p_138661_) {
        p_138661_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("setworldspawn").requires(p_138665_ -> p_138665_.m_6761_(2))).executes(p_138673_ -> SetWorldSpawnCommand.m_138666_((CommandSourceStack)p_138673_.getSource(), new BlockPos(((CommandSourceStack)p_138673_.getSource()).m_81371_()), 0.0f))).then(((RequiredArgumentBuilder)Commands.m_82129_("pos", BlockPosArgument.m_118239_()).executes(p_138671_ -> SetWorldSpawnCommand.m_138666_((CommandSourceStack)p_138671_.getSource(), BlockPosArgument.m_174395_((CommandContext<CommandSourceStack>)p_138671_, "pos"), 0.0f))).then(Commands.m_82129_("angle", AngleArgument.m_83807_()).executes(p_138663_ -> SetWorldSpawnCommand.m_138666_((CommandSourceStack)p_138663_.getSource(), BlockPosArgument.m_174395_((CommandContext<CommandSourceStack>)p_138663_, "pos"), AngleArgument.m_83810_((CommandContext<CommandSourceStack>)p_138663_, "angle"))))));
    }

    private static int m_138666_(CommandSourceStack p_138667_, BlockPos p_138668_, float p_138669_) {
        p_138667_.m_81372_().m_8733_(p_138668_, p_138669_);
        p_138667_.m_81354_(Component.m_237110_("commands.setworldspawn.success", p_138668_.m_123341_(), p_138668_.m_123342_(), p_138668_.m_123343_(), Float.valueOf(p_138669_)), true);
        return 1;
    }
}

