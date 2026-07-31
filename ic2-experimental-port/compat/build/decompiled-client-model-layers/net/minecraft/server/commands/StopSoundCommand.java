/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  javax.annotation.Nullable
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;

public class StopSoundCommand {
    public static void m_138794_(CommandDispatcher<CommandSourceStack> p_138795_) {
        RequiredArgumentBuilder $$1 = (RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("targets", EntityArgument.m_91470_()).executes(p_138809_ -> StopSoundCommand.m_138800_((CommandSourceStack)p_138809_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_138809_, "targets"), null, null))).then(Commands.m_82127_("*").then(Commands.m_82129_("sound", ResourceLocationArgument.m_106984_()).suggests(SuggestionProviders.f_121643_).executes(p_138797_ -> StopSoundCommand.m_138800_((CommandSourceStack)p_138797_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_138797_, "targets"), null, ResourceLocationArgument.m_107011_((CommandContext<CommandSourceStack>)p_138797_, "sound")))));
        for (SoundSource $$2 : SoundSource.values()) {
            $$1.then(((LiteralArgumentBuilder)Commands.m_82127_($$2.m_12676_()).executes(p_138807_ -> StopSoundCommand.m_138800_((CommandSourceStack)p_138807_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_138807_, "targets"), $$2, null))).then(Commands.m_82129_("sound", ResourceLocationArgument.m_106984_()).suggests(SuggestionProviders.f_121643_).executes(p_138793_ -> StopSoundCommand.m_138800_((CommandSourceStack)p_138793_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_138793_, "targets"), $$2, ResourceLocationArgument.m_107011_((CommandContext<CommandSourceStack>)p_138793_, "sound")))));
        }
        p_138795_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("stopsound").requires(p_138799_ -> p_138799_.m_6761_(2))).then((ArgumentBuilder)$$1));
    }

    private static int m_138800_(CommandSourceStack p_138801_, Collection<ServerPlayer> p_138802_, @Nullable SoundSource p_138803_, @Nullable ResourceLocation p_138804_) {
        ClientboundStopSoundPacket $$4 = new ClientboundStopSoundPacket(p_138804_, p_138803_);
        for (ServerPlayer $$5 : p_138802_) {
            $$5.f_8906_.m_9829_($$4);
        }
        if (p_138803_ != null) {
            if (p_138804_ != null) {
                p_138801_.m_81354_(Component.m_237110_("commands.stopsound.success.source.sound", p_138804_, p_138803_.m_12676_()), true);
            } else {
                p_138801_.m_81354_(Component.m_237110_("commands.stopsound.success.source.any", p_138803_.m_12676_()), true);
            }
        } else if (p_138804_ != null) {
            p_138801_.m_81354_(Component.m_237110_("commands.stopsound.success.sourceless.sound", p_138804_), true);
        } else {
            p_138801_.m_81354_(Component.m_237115_("commands.stopsound.success.sourceless.any"), true);
        }
        return p_138802_.size();
    }
}

