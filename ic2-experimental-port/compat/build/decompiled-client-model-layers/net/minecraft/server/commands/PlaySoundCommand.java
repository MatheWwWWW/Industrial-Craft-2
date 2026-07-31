/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.FloatArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundCustomSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;

public class PlaySoundCommand {
    private static final SimpleCommandExceptionType f_138149_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.playsound.failed"));

    public static void m_138156_(CommandDispatcher<CommandSourceStack> p_138157_) {
        RequiredArgumentBuilder $$1 = Commands.m_82129_("sound", ResourceLocationArgument.m_106984_()).suggests(SuggestionProviders.f_121643_);
        for (SoundSource $$2 : SoundSource.values()) {
            $$1.then(PlaySoundCommand.m_138151_($$2));
        }
        p_138157_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("playsound").requires(p_138159_ -> p_138159_.m_6761_(2))).then((ArgumentBuilder)$$1));
    }

    private static LiteralArgumentBuilder<CommandSourceStack> m_138151_(SoundSource p_138152_) {
        return (LiteralArgumentBuilder)Commands.m_82127_(p_138152_.m_12676_()).then(((RequiredArgumentBuilder)Commands.m_82129_("targets", EntityArgument.m_91470_()).executes(p_138180_ -> PlaySoundCommand.m_138160_((CommandSourceStack)p_138180_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_138180_, "targets"), ResourceLocationArgument.m_107011_((CommandContext<CommandSourceStack>)p_138180_, "sound"), p_138152_, ((CommandSourceStack)p_138180_.getSource()).m_81371_(), 1.0f, 1.0f, 0.0f))).then(((RequiredArgumentBuilder)Commands.m_82129_("pos", Vec3Argument.m_120841_()).executes(p_138177_ -> PlaySoundCommand.m_138160_((CommandSourceStack)p_138177_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_138177_, "targets"), ResourceLocationArgument.m_107011_((CommandContext<CommandSourceStack>)p_138177_, "sound"), p_138152_, Vec3Argument.m_120844_((CommandContext<CommandSourceStack>)p_138177_, "pos"), 1.0f, 1.0f, 0.0f))).then(((RequiredArgumentBuilder)Commands.m_82129_("volume", FloatArgumentType.floatArg((float)0.0f)).executes(p_138174_ -> PlaySoundCommand.m_138160_((CommandSourceStack)p_138174_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_138174_, "targets"), ResourceLocationArgument.m_107011_((CommandContext<CommandSourceStack>)p_138174_, "sound"), p_138152_, Vec3Argument.m_120844_((CommandContext<CommandSourceStack>)p_138174_, "pos"), ((Float)p_138174_.getArgument("volume", Float.class)).floatValue(), 1.0f, 0.0f))).then(((RequiredArgumentBuilder)Commands.m_82129_("pitch", FloatArgumentType.floatArg((float)0.0f, (float)2.0f)).executes(p_138171_ -> PlaySoundCommand.m_138160_((CommandSourceStack)p_138171_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_138171_, "targets"), ResourceLocationArgument.m_107011_((CommandContext<CommandSourceStack>)p_138171_, "sound"), p_138152_, Vec3Argument.m_120844_((CommandContext<CommandSourceStack>)p_138171_, "pos"), ((Float)p_138171_.getArgument("volume", Float.class)).floatValue(), ((Float)p_138171_.getArgument("pitch", Float.class)).floatValue(), 0.0f))).then(Commands.m_82129_("minVolume", FloatArgumentType.floatArg((float)0.0f, (float)1.0f)).executes(p_138155_ -> PlaySoundCommand.m_138160_((CommandSourceStack)p_138155_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_138155_, "targets"), ResourceLocationArgument.m_107011_((CommandContext<CommandSourceStack>)p_138155_, "sound"), p_138152_, Vec3Argument.m_120844_((CommandContext<CommandSourceStack>)p_138155_, "pos"), ((Float)p_138155_.getArgument("volume", Float.class)).floatValue(), ((Float)p_138155_.getArgument("pitch", Float.class)).floatValue(), ((Float)p_138155_.getArgument("minVolume", Float.class)).floatValue())))))));
    }

    private static int m_138160_(CommandSourceStack p_138161_, Collection<ServerPlayer> p_138162_, ResourceLocation p_138163_, SoundSource p_138164_, Vec3 p_138165_, float p_138166_, float p_138167_, float p_138168_) throws CommandSyntaxException {
        double $$8 = Math.pow(p_138166_ > 1.0f ? (double)(p_138166_ * 16.0f) : 16.0, 2.0);
        int $$9 = 0;
        long $$10 = p_138161_.m_81372_().m_213780_().m_188505_();
        for (ServerPlayer $$11 : p_138162_) {
            double $$12 = p_138165_.f_82479_ - $$11.m_20185_();
            double $$13 = p_138165_.f_82480_ - $$11.m_20186_();
            double $$14 = p_138165_.f_82481_ - $$11.m_20189_();
            double $$15 = $$12 * $$12 + $$13 * $$13 + $$14 * $$14;
            Vec3 $$16 = p_138165_;
            float $$17 = p_138166_;
            if ($$15 > $$8) {
                if (p_138168_ <= 0.0f) continue;
                double $$18 = Math.sqrt($$15);
                $$16 = new Vec3($$11.m_20185_() + $$12 / $$18 * 2.0, $$11.m_20186_() + $$13 / $$18 * 2.0, $$11.m_20189_() + $$14 / $$18 * 2.0);
                $$17 = p_138168_;
            }
            $$11.f_8906_.m_9829_(new ClientboundCustomSoundPacket(p_138163_, p_138164_, $$16, $$17, p_138167_, $$10));
            ++$$9;
        }
        if ($$9 == 0) {
            throw f_138149_.create();
        }
        if (p_138162_.size() == 1) {
            p_138161_.m_81354_(Component.m_237110_("commands.playsound.success.single", p_138163_, p_138162_.iterator().next().m_5446_()), true);
        } else {
            p_138161_.m_81354_(Component.m_237110_("commands.playsound.success.multiple", p_138163_, p_138162_.size()), true);
        }
        return $$9;
    }
}

