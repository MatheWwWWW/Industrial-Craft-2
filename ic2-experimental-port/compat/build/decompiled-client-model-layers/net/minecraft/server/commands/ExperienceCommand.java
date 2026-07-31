/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.tree.CommandNode
 *  com.mojang.brigadier.tree.LiteralCommandNode
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntFunction;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class ExperienceCommand {
    private static final SimpleCommandExceptionType f_137304_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.experience.set.points.invalid"));

    public static void m_137306_(CommandDispatcher<CommandSourceStack> p_137307_) {
        LiteralCommandNode $$1 = p_137307_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("experience").requires(p_137324_ -> p_137324_.m_6761_(2))).then(Commands.m_82127_("add").then(Commands.m_82129_("targets", EntityArgument.m_91470_()).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("amount", IntegerArgumentType.integer()).executes(p_137341_ -> ExperienceCommand.m_137316_((CommandSourceStack)p_137341_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_137341_, "targets"), IntegerArgumentType.getInteger((CommandContext)p_137341_, (String)"amount"), Type.POINTS))).then(Commands.m_82127_("points").executes(p_137339_ -> ExperienceCommand.m_137316_((CommandSourceStack)p_137339_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_137339_, "targets"), IntegerArgumentType.getInteger((CommandContext)p_137339_, (String)"amount"), Type.POINTS)))).then(Commands.m_82127_("levels").executes(p_137337_ -> ExperienceCommand.m_137316_((CommandSourceStack)p_137337_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_137337_, "targets"), IntegerArgumentType.getInteger((CommandContext)p_137337_, (String)"amount"), Type.LEVELS))))))).then(Commands.m_82127_("set").then(Commands.m_82129_("targets", EntityArgument.m_91470_()).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("amount", IntegerArgumentType.integer((int)0)).executes(p_137335_ -> ExperienceCommand.m_137325_((CommandSourceStack)p_137335_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_137335_, "targets"), IntegerArgumentType.getInteger((CommandContext)p_137335_, (String)"amount"), Type.POINTS))).then(Commands.m_82127_("points").executes(p_137333_ -> ExperienceCommand.m_137325_((CommandSourceStack)p_137333_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_137333_, "targets"), IntegerArgumentType.getInteger((CommandContext)p_137333_, (String)"amount"), Type.POINTS)))).then(Commands.m_82127_("levels").executes(p_137331_ -> ExperienceCommand.m_137325_((CommandSourceStack)p_137331_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_137331_, "targets"), IntegerArgumentType.getInteger((CommandContext)p_137331_, (String)"amount"), Type.LEVELS))))))).then(Commands.m_82127_("query").then(((RequiredArgumentBuilder)Commands.m_82129_("targets", EntityArgument.m_91466_()).then(Commands.m_82127_("points").executes(p_137322_ -> ExperienceCommand.m_137312_((CommandSourceStack)p_137322_.getSource(), EntityArgument.m_91474_((CommandContext<CommandSourceStack>)p_137322_, "targets"), Type.POINTS)))).then(Commands.m_82127_("levels").executes(p_137309_ -> ExperienceCommand.m_137312_((CommandSourceStack)p_137309_.getSource(), EntityArgument.m_91474_((CommandContext<CommandSourceStack>)p_137309_, "targets"), Type.LEVELS))))));
        p_137307_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("xp").requires(p_137311_ -> p_137311_.m_6761_(2))).redirect((CommandNode)$$1));
    }

    private static int m_137312_(CommandSourceStack p_137313_, ServerPlayer p_137314_, Type p_137315_) {
        int $$3 = p_137315_.f_137347_.applyAsInt(p_137314_);
        p_137313_.m_81354_(Component.m_237110_("commands.experience.query." + p_137315_.f_137346_, p_137314_.m_5446_(), $$3), false);
        return $$3;
    }

    private static int m_137316_(CommandSourceStack p_137317_, Collection<? extends ServerPlayer> p_137318_, int p_137319_, Type p_137320_) {
        for (ServerPlayer serverPlayer : p_137318_) {
            p_137320_.f_137344_.accept(serverPlayer, p_137319_);
        }
        if (p_137318_.size() == 1) {
            p_137317_.m_81354_(Component.m_237110_("commands.experience.add." + p_137320_.f_137346_ + ".success.single", p_137319_, p_137318_.iterator().next().m_5446_()), true);
        } else {
            p_137317_.m_81354_(Component.m_237110_("commands.experience.add." + p_137320_.f_137346_ + ".success.multiple", p_137319_, p_137318_.size()), true);
        }
        return p_137318_.size();
    }

    private static int m_137325_(CommandSourceStack p_137326_, Collection<? extends ServerPlayer> p_137327_, int p_137328_, Type p_137329_) throws CommandSyntaxException {
        int $$4 = 0;
        for (ServerPlayer serverPlayer : p_137327_) {
            if (!p_137329_.f_137345_.test(serverPlayer, p_137328_)) continue;
            ++$$4;
        }
        if ($$4 == 0) {
            throw f_137304_.create();
        }
        if (p_137327_.size() == 1) {
            p_137326_.m_81354_(Component.m_237110_("commands.experience.set." + p_137329_.f_137346_ + ".success.single", p_137328_, p_137327_.iterator().next().m_5446_()), true);
        } else {
            p_137326_.m_81354_(Component.m_237110_("commands.experience.set." + p_137329_.f_137346_ + ".success.multiple", p_137328_, p_137327_.size()), true);
        }
        return p_137327_.size();
    }

    static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type POINTS = new Type("points", Player::m_6756_, (p_137367_, p_137368_) -> {
            if (p_137368_ >= p_137367_.m_36323_()) {
                return false;
            }
            p_137367_.m_8985_((int)p_137368_);
            return true;
        }, p_137365_ -> Mth.m_14143_(p_137365_.f_36080_ * (float)p_137365_.m_36323_()));
        public static final /* enum */ Type LEVELS = new Type("levels", ServerPlayer::m_6749_, (p_137360_, p_137361_) -> {
            p_137360_.m_9174_((int)p_137361_);
            return true;
        }, p_137358_ -> p_137358_.f_36078_);
        public final BiConsumer<ServerPlayer, Integer> f_137344_;
        public final BiPredicate<ServerPlayer, Integer> f_137345_;
        public final String f_137346_;
        final ToIntFunction<ServerPlayer> f_137347_;
        private static final /* synthetic */ Type[] $VALUES;

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String p_137370_) {
            return Enum.valueOf(Type.class, p_137370_);
        }

        private Type(String p_137353_, BiConsumer<ServerPlayer, Integer> p_137354_, BiPredicate<ServerPlayer, Integer> p_137355_, ToIntFunction<ServerPlayer> p_137356_) {
            this.f_137344_ = p_137354_;
            this.f_137346_ = p_137353_;
            this.f_137345_ = p_137355_;
            this.f_137347_ = p_137356_;
        }

        private static /* synthetic */ Type[] m_180221_() {
            return new Type[]{POINTS, LEVELS};
        }

        static {
            $VALUES = Type.m_180221_();
        }
    }
}

