/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.tree.CommandNode
 *  com.mojang.brigadier.tree.LiteralCommandNode
 *  javax.annotation.Nullable
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.commands.arguments.coordinates.RotationArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.arguments.coordinates.WorldCoordinates;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class TeleportCommand {
    private static final SimpleCommandExceptionType f_139006_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.teleport.invalidPosition"));

    public static void m_139008_(CommandDispatcher<CommandSourceStack> p_139009_) {
        LiteralCommandNode $$1 = p_139009_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("teleport").requires(p_139039_ -> p_139039_.m_6761_(2))).then(Commands.m_82129_("location", Vec3Argument.m_120841_()).executes(p_139051_ -> TeleportCommand.m_139025_((CommandSourceStack)p_139051_.getSource(), Collections.singleton(((CommandSourceStack)p_139051_.getSource()).m_81374_()), ((CommandSourceStack)p_139051_.getSource()).m_81372_(), Vec3Argument.m_120849_((CommandContext<CommandSourceStack>)p_139051_, "location"), WorldCoordinates.m_120898_(), null)))).then(Commands.m_82129_("destination", EntityArgument.m_91449_()).executes(p_139049_ -> TeleportCommand.m_139032_((CommandSourceStack)p_139049_.getSource(), Collections.singleton(((CommandSourceStack)p_139049_.getSource()).m_81374_()), EntityArgument.m_91452_((CommandContext<CommandSourceStack>)p_139049_, "destination"))))).then(((RequiredArgumentBuilder)Commands.m_82129_("targets", EntityArgument.m_91460_()).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("location", Vec3Argument.m_120841_()).executes(p_139047_ -> TeleportCommand.m_139025_((CommandSourceStack)p_139047_.getSource(), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_139047_, "targets"), ((CommandSourceStack)p_139047_.getSource()).m_81372_(), Vec3Argument.m_120849_((CommandContext<CommandSourceStack>)p_139047_, "location"), null, null))).then(Commands.m_82129_("rotation", RotationArgument.m_120479_()).executes(p_139045_ -> TeleportCommand.m_139025_((CommandSourceStack)p_139045_.getSource(), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_139045_, "targets"), ((CommandSourceStack)p_139045_.getSource()).m_81372_(), Vec3Argument.m_120849_((CommandContext<CommandSourceStack>)p_139045_, "location"), RotationArgument.m_120482_((CommandContext<CommandSourceStack>)p_139045_, "rotation"), null)))).then(((LiteralArgumentBuilder)Commands.m_82127_("facing").then(Commands.m_82127_("entity").then(((RequiredArgumentBuilder)Commands.m_82129_("facingEntity", EntityArgument.m_91449_()).executes(p_139043_ -> TeleportCommand.m_139025_((CommandSourceStack)p_139043_.getSource(), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_139043_, "targets"), ((CommandSourceStack)p_139043_.getSource()).m_81372_(), Vec3Argument.m_120849_((CommandContext<CommandSourceStack>)p_139043_, "location"), null, new LookAt(EntityArgument.m_91452_((CommandContext<CommandSourceStack>)p_139043_, "facingEntity"), EntityAnchorArgument.Anchor.FEET)))).then(Commands.m_82129_("facingAnchor", EntityAnchorArgument.m_90350_()).executes(p_139041_ -> TeleportCommand.m_139025_((CommandSourceStack)p_139041_.getSource(), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_139041_, "targets"), ((CommandSourceStack)p_139041_.getSource()).m_81372_(), Vec3Argument.m_120849_((CommandContext<CommandSourceStack>)p_139041_, "location"), null, new LookAt(EntityArgument.m_91452_((CommandContext<CommandSourceStack>)p_139041_, "facingEntity"), EntityAnchorArgument.m_90353_((CommandContext<CommandSourceStack>)p_139041_, "facingAnchor")))))))).then(Commands.m_82129_("facingLocation", Vec3Argument.m_120841_()).executes(p_139037_ -> TeleportCommand.m_139025_((CommandSourceStack)p_139037_.getSource(), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_139037_, "targets"), ((CommandSourceStack)p_139037_.getSource()).m_81372_(), Vec3Argument.m_120849_((CommandContext<CommandSourceStack>)p_139037_, "location"), null, new LookAt(Vec3Argument.m_120844_((CommandContext<CommandSourceStack>)p_139037_, "facingLocation")))))))).then(Commands.m_82129_("destination", EntityArgument.m_91449_()).executes(p_139011_ -> TeleportCommand.m_139032_((CommandSourceStack)p_139011_.getSource(), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_139011_, "targets"), EntityArgument.m_91452_((CommandContext<CommandSourceStack>)p_139011_, "destination"))))));
        p_139009_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("tp").requires(p_139013_ -> p_139013_.m_6761_(2))).redirect((CommandNode)$$1));
    }

    private static int m_139032_(CommandSourceStack p_139033_, Collection<? extends Entity> p_139034_, Entity p_139035_) throws CommandSyntaxException {
        for (Entity entity : p_139034_) {
            TeleportCommand.m_139014_(p_139033_, entity, (ServerLevel)p_139035_.f_19853_, p_139035_.m_20185_(), p_139035_.m_20186_(), p_139035_.m_20189_(), EnumSet.noneOf(ClientboundPlayerPositionPacket.RelativeArgument.class), p_139035_.m_146908_(), p_139035_.m_146909_(), null);
        }
        if (p_139034_.size() == 1) {
            p_139033_.m_81354_(Component.m_237110_("commands.teleport.success.entity.single", p_139034_.iterator().next().m_5446_(), p_139035_.m_5446_()), true);
        } else {
            p_139033_.m_81354_(Component.m_237110_("commands.teleport.success.entity.multiple", p_139034_.size(), p_139035_.m_5446_()), true);
        }
        return p_139034_.size();
    }

    private static int m_139025_(CommandSourceStack p_139026_, Collection<? extends Entity> p_139027_, ServerLevel p_139028_, Coordinates p_139029_, @Nullable Coordinates p_139030_, @Nullable LookAt p_139031_) throws CommandSyntaxException {
        Vec3 $$6 = p_139029_.m_6955_(p_139026_);
        Vec2 $$7 = p_139030_ == null ? null : p_139030_.m_6970_(p_139026_);
        EnumSet<ClientboundPlayerPositionPacket.RelativeArgument> $$8 = EnumSet.noneOf(ClientboundPlayerPositionPacket.RelativeArgument.class);
        if (p_139029_.m_6888_()) {
            $$8.add(ClientboundPlayerPositionPacket.RelativeArgument.X);
        }
        if (p_139029_.m_6892_()) {
            $$8.add(ClientboundPlayerPositionPacket.RelativeArgument.Y);
        }
        if (p_139029_.m_6900_()) {
            $$8.add(ClientboundPlayerPositionPacket.RelativeArgument.Z);
        }
        if (p_139030_ == null) {
            $$8.add(ClientboundPlayerPositionPacket.RelativeArgument.X_ROT);
            $$8.add(ClientboundPlayerPositionPacket.RelativeArgument.Y_ROT);
        } else {
            if (p_139030_.m_6888_()) {
                $$8.add(ClientboundPlayerPositionPacket.RelativeArgument.X_ROT);
            }
            if (p_139030_.m_6892_()) {
                $$8.add(ClientboundPlayerPositionPacket.RelativeArgument.Y_ROT);
            }
        }
        for (Entity entity : p_139027_) {
            if (p_139030_ == null) {
                TeleportCommand.m_139014_(p_139026_, entity, p_139028_, $$6.f_82479_, $$6.f_82480_, $$6.f_82481_, $$8, entity.m_146908_(), entity.m_146909_(), p_139031_);
                continue;
            }
            TeleportCommand.m_139014_(p_139026_, entity, p_139028_, $$6.f_82479_, $$6.f_82480_, $$6.f_82481_, $$8, $$7.f_82471_, $$7.f_82470_, p_139031_);
        }
        if (p_139027_.size() == 1) {
            p_139026_.m_81354_(Component.m_237110_("commands.teleport.success.location.single", p_139027_.iterator().next().m_5446_(), TeleportCommand.m_142775_($$6.f_82479_), TeleportCommand.m_142775_($$6.f_82480_), TeleportCommand.m_142775_($$6.f_82481_)), true);
        } else {
            p_139026_.m_81354_(Component.m_237110_("commands.teleport.success.location.multiple", p_139027_.size(), TeleportCommand.m_142775_($$6.f_82479_), TeleportCommand.m_142775_($$6.f_82480_), TeleportCommand.m_142775_($$6.f_82481_)), true);
        }
        return p_139027_.size();
    }

    private static String m_142775_(double p_142776_) {
        return String.format(Locale.ROOT, "%f", p_142776_);
    }

    private static void m_139014_(CommandSourceStack p_139015_, Entity p_139016_, ServerLevel p_139017_, double p_139018_, double p_139019_, double p_139020_, Set<ClientboundPlayerPositionPacket.RelativeArgument> p_139021_, float p_139022_, float p_139023_, @Nullable LookAt p_139024_) throws CommandSyntaxException {
        BlockPos $$10 = new BlockPos(p_139018_, p_139019_, p_139020_);
        if (!Level.m_46741_($$10)) {
            throw f_139006_.create();
        }
        float $$11 = Mth.m_14177_(p_139022_);
        float $$12 = Mth.m_14177_(p_139023_);
        if (p_139016_ instanceof ServerPlayer) {
            ChunkPos $$13 = new ChunkPos(new BlockPos(p_139018_, p_139019_, p_139020_));
            p_139017_.m_7726_().m_8387_(TicketType.f_9448_, $$13, 1, p_139016_.m_19879_());
            p_139016_.m_8127_();
            if (((ServerPlayer)p_139016_).m_5803_()) {
                ((ServerPlayer)p_139016_).m_6145_(true, true);
            }
            if (p_139017_ == p_139016_.f_19853_) {
                ((ServerPlayer)p_139016_).f_8906_.m_9780_(p_139018_, p_139019_, p_139020_, $$11, $$12, p_139021_);
            } else {
                ((ServerPlayer)p_139016_).m_8999_(p_139017_, p_139018_, p_139019_, p_139020_, $$11, $$12);
            }
            p_139016_.m_5616_($$11);
        } else {
            float $$14 = Mth.m_14036_($$12, -90.0f, 90.0f);
            if (p_139017_ == p_139016_.f_19853_) {
                p_139016_.m_7678_(p_139018_, p_139019_, p_139020_, $$11, $$14);
                p_139016_.m_5616_($$11);
            } else {
                p_139016_.m_19877_();
                Entity $$15 = p_139016_;
                p_139016_ = $$15.m_6095_().m_20615_(p_139017_);
                if (p_139016_ != null) {
                    p_139016_.m_20361_($$15);
                    p_139016_.m_7678_(p_139018_, p_139019_, p_139020_, $$11, $$14);
                    p_139016_.m_5616_($$11);
                    $$15.m_142467_(Entity.RemovalReason.CHANGED_DIMENSION);
                    p_139017_.m_143334_(p_139016_);
                } else {
                    return;
                }
            }
        }
        if (p_139024_ != null) {
            p_139024_.m_139060_(p_139015_, p_139016_);
        }
        if (!(p_139016_ instanceof LivingEntity) || !((LivingEntity)p_139016_).m_21255_()) {
            p_139016_.m_20256_(p_139016_.m_20184_().m_82542_(1.0, 0.0, 1.0));
            p_139016_.m_6853_(true);
        }
        if (p_139016_ instanceof PathfinderMob) {
            ((PathfinderMob)p_139016_).m_21573_().m_26573_();
        }
    }

    static class LookAt {
        private final Vec3 f_139052_;
        private final Entity f_139053_;
        private final EntityAnchorArgument.Anchor f_139054_;

        public LookAt(Entity p_139056_, EntityAnchorArgument.Anchor p_139057_) {
            this.f_139053_ = p_139056_;
            this.f_139054_ = p_139057_;
            this.f_139052_ = p_139057_.m_90377_(p_139056_);
        }

        public LookAt(Vec3 p_139059_) {
            this.f_139053_ = null;
            this.f_139052_ = p_139059_;
            this.f_139054_ = null;
        }

        public void m_139060_(CommandSourceStack p_139061_, Entity p_139062_) {
            if (this.f_139053_ != null) {
                if (p_139062_ instanceof ServerPlayer) {
                    ((ServerPlayer)p_139062_).m_9107_(p_139061_.m_81378_(), this.f_139053_, this.f_139054_);
                } else {
                    p_139062_.m_7618_(p_139061_.m_81378_(), this.f_139052_);
                }
            } else {
                p_139062_.m_7618_(p_139061_.m_81378_(), this.f_139052_);
            }
        }
    }
}

