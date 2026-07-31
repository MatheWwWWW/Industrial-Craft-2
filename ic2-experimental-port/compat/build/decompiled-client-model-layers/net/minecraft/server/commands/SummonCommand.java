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
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.arguments.EntitySummonArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SummonCommand {
    private static final SimpleCommandExceptionType f_138810_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.summon.failed"));
    private static final SimpleCommandExceptionType f_138811_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.summon.failed.uuid"));
    private static final SimpleCommandExceptionType f_138812_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.summon.invalidPosition"));

    public static void m_138814_(CommandDispatcher<CommandSourceStack> p_138815_) {
        p_138815_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("summon").requires(p_138819_ -> p_138819_.m_6761_(2))).then(((RequiredArgumentBuilder)Commands.m_82129_("entity", EntitySummonArgument.m_93335_()).suggests(SuggestionProviders.f_121645_).executes(p_138832_ -> SummonCommand.m_138820_((CommandSourceStack)p_138832_.getSource(), EntitySummonArgument.m_93338_((CommandContext<CommandSourceStack>)p_138832_, "entity"), ((CommandSourceStack)p_138832_.getSource()).m_81371_(), new CompoundTag(), true))).then(((RequiredArgumentBuilder)Commands.m_82129_("pos", Vec3Argument.m_120841_()).executes(p_138830_ -> SummonCommand.m_138820_((CommandSourceStack)p_138830_.getSource(), EntitySummonArgument.m_93338_((CommandContext<CommandSourceStack>)p_138830_, "entity"), Vec3Argument.m_120844_((CommandContext<CommandSourceStack>)p_138830_, "pos"), new CompoundTag(), true))).then(Commands.m_82129_("nbt", CompoundTagArgument.m_87657_()).executes(p_138817_ -> SummonCommand.m_138820_((CommandSourceStack)p_138817_.getSource(), EntitySummonArgument.m_93338_((CommandContext<CommandSourceStack>)p_138817_, "entity"), Vec3Argument.m_120844_((CommandContext<CommandSourceStack>)p_138817_, "pos"), CompoundTagArgument.m_87660_(p_138817_, "nbt"), false))))));
    }

    private static int m_138820_(CommandSourceStack p_138821_, ResourceLocation p_138822_, Vec3 p_138823_, CompoundTag p_138824_, boolean p_138825_) throws CommandSyntaxException {
        BlockPos $$5 = new BlockPos(p_138823_);
        if (!Level.m_46741_($$5)) {
            throw f_138812_.create();
        }
        CompoundTag $$6 = p_138824_.m_6426_();
        $$6.m_128359_("id", p_138822_.toString());
        ServerLevel $$7 = p_138821_.m_81372_();
        Entity $$8 = EntityType.m_20645_($$6, $$7, p_138828_ -> {
            p_138828_.m_7678_(p_138827_.f_82479_, p_138827_.f_82480_, p_138827_.f_82481_, p_138828_.m_146908_(), p_138828_.m_146909_());
            return p_138828_;
        });
        if ($$8 == null) {
            throw f_138810_.create();
        }
        if (p_138825_ && $$8 instanceof Mob) {
            ((Mob)$$8).m_6518_(p_138821_.m_81372_(), p_138821_.m_81372_().m_6436_($$8.m_20183_()), MobSpawnType.COMMAND, null, null);
        }
        if (!$$7.m_8860_($$8)) {
            throw f_138811_.create();
        }
        p_138821_.m_81354_(Component.m_237110_("commands.summon.success", $$8.m_5446_()), true);
        return 1;
    }
}

