/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.FloatArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
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
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ParticleArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class ParticleCommand {
    private static final SimpleCommandExceptionType f_138120_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.particle.failed"));

    public static void m_138122_(CommandDispatcher<CommandSourceStack> p_138123_) {
        p_138123_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("particle").requires(p_138127_ -> p_138127_.m_6761_(2))).then(((RequiredArgumentBuilder)Commands.m_82129_("name", ParticleArgument.m_103931_()).executes(p_138148_ -> ParticleCommand.m_138128_((CommandSourceStack)p_138148_.getSource(), ParticleArgument.m_103937_((CommandContext<CommandSourceStack>)p_138148_, "name"), ((CommandSourceStack)p_138148_.getSource()).m_81371_(), Vec3.f_82478_, 0.0f, 0, false, ((CommandSourceStack)p_138148_.getSource()).m_81377_().m_6846_().m_11314_()))).then(((RequiredArgumentBuilder)Commands.m_82129_("pos", Vec3Argument.m_120841_()).executes(p_138146_ -> ParticleCommand.m_138128_((CommandSourceStack)p_138146_.getSource(), ParticleArgument.m_103937_((CommandContext<CommandSourceStack>)p_138146_, "name"), Vec3Argument.m_120844_((CommandContext<CommandSourceStack>)p_138146_, "pos"), Vec3.f_82478_, 0.0f, 0, false, ((CommandSourceStack)p_138146_.getSource()).m_81377_().m_6846_().m_11314_()))).then(Commands.m_82129_("delta", Vec3Argument.m_120847_(false)).then(Commands.m_82129_("speed", FloatArgumentType.floatArg((float)0.0f)).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("count", IntegerArgumentType.integer((int)0)).executes(p_138144_ -> ParticleCommand.m_138128_((CommandSourceStack)p_138144_.getSource(), ParticleArgument.m_103937_((CommandContext<CommandSourceStack>)p_138144_, "name"), Vec3Argument.m_120844_((CommandContext<CommandSourceStack>)p_138144_, "pos"), Vec3Argument.m_120844_((CommandContext<CommandSourceStack>)p_138144_, "delta"), FloatArgumentType.getFloat((CommandContext)p_138144_, (String)"speed"), IntegerArgumentType.getInteger((CommandContext)p_138144_, (String)"count"), false, ((CommandSourceStack)p_138144_.getSource()).m_81377_().m_6846_().m_11314_()))).then(((LiteralArgumentBuilder)Commands.m_82127_("force").executes(p_138142_ -> ParticleCommand.m_138128_((CommandSourceStack)p_138142_.getSource(), ParticleArgument.m_103937_((CommandContext<CommandSourceStack>)p_138142_, "name"), Vec3Argument.m_120844_((CommandContext<CommandSourceStack>)p_138142_, "pos"), Vec3Argument.m_120844_((CommandContext<CommandSourceStack>)p_138142_, "delta"), FloatArgumentType.getFloat((CommandContext)p_138142_, (String)"speed"), IntegerArgumentType.getInteger((CommandContext)p_138142_, (String)"count"), true, ((CommandSourceStack)p_138142_.getSource()).m_81377_().m_6846_().m_11314_()))).then(Commands.m_82129_("viewers", EntityArgument.m_91470_()).executes(p_138140_ -> ParticleCommand.m_138128_((CommandSourceStack)p_138140_.getSource(), ParticleArgument.m_103937_((CommandContext<CommandSourceStack>)p_138140_, "name"), Vec3Argument.m_120844_((CommandContext<CommandSourceStack>)p_138140_, "pos"), Vec3Argument.m_120844_((CommandContext<CommandSourceStack>)p_138140_, "delta"), FloatArgumentType.getFloat((CommandContext)p_138140_, (String)"speed"), IntegerArgumentType.getInteger((CommandContext)p_138140_, (String)"count"), true, EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_138140_, "viewers")))))).then(((LiteralArgumentBuilder)Commands.m_82127_("normal").executes(p_138138_ -> ParticleCommand.m_138128_((CommandSourceStack)p_138138_.getSource(), ParticleArgument.m_103937_((CommandContext<CommandSourceStack>)p_138138_, "name"), Vec3Argument.m_120844_((CommandContext<CommandSourceStack>)p_138138_, "pos"), Vec3Argument.m_120844_((CommandContext<CommandSourceStack>)p_138138_, "delta"), FloatArgumentType.getFloat((CommandContext)p_138138_, (String)"speed"), IntegerArgumentType.getInteger((CommandContext)p_138138_, (String)"count"), false, ((CommandSourceStack)p_138138_.getSource()).m_81377_().m_6846_().m_11314_()))).then(Commands.m_82129_("viewers", EntityArgument.m_91470_()).executes(p_138125_ -> ParticleCommand.m_138128_((CommandSourceStack)p_138125_.getSource(), ParticleArgument.m_103937_((CommandContext<CommandSourceStack>)p_138125_, "name"), Vec3Argument.m_120844_((CommandContext<CommandSourceStack>)p_138125_, "pos"), Vec3Argument.m_120844_((CommandContext<CommandSourceStack>)p_138125_, "delta"), FloatArgumentType.getFloat((CommandContext)p_138125_, (String)"speed"), IntegerArgumentType.getInteger((CommandContext)p_138125_, (String)"count"), false, EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_138125_, "viewers")))))))))));
    }

    private static int m_138128_(CommandSourceStack p_138129_, ParticleOptions p_138130_, Vec3 p_138131_, Vec3 p_138132_, float p_138133_, int p_138134_, boolean p_138135_, Collection<ServerPlayer> p_138136_) throws CommandSyntaxException {
        int $$8 = 0;
        for (ServerPlayer $$9 : p_138136_) {
            if (!p_138129_.m_81372_().m_8624_($$9, p_138130_, p_138135_, p_138131_.f_82479_, p_138131_.f_82480_, p_138131_.f_82481_, p_138134_, p_138132_.f_82479_, p_138132_.f_82480_, p_138132_.f_82481_, p_138133_)) continue;
            ++$$8;
        }
        if ($$8 == 0) {
            throw f_138120_.create();
        }
        p_138129_.m_81354_(Component.m_237110_("commands.particle.success", Registry.f_122829_.m_7981_(p_138130_.m_6012_()).toString()), true);
        return $$8;
    }
}

