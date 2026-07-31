/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

public class GiveCommand {
    public static final int f_180233_ = 100;

    public static void m_214445_(CommandDispatcher<CommandSourceStack> p_214446_, CommandBuildContext p_214447_) {
        p_214446_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("give").requires(p_137777_ -> p_137777_.m_6761_(2))).then(Commands.m_82129_("targets", EntityArgument.m_91470_()).then(((RequiredArgumentBuilder)Commands.m_82129_("item", ItemArgument.m_235279_(p_214447_)).executes(p_137784_ -> GiveCommand.m_137778_((CommandSourceStack)p_137784_.getSource(), ItemArgument.m_120963_(p_137784_, "item"), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_137784_, "targets"), 1))).then(Commands.m_82129_("count", IntegerArgumentType.integer((int)1)).executes(p_137775_ -> GiveCommand.m_137778_((CommandSourceStack)p_137775_.getSource(), ItemArgument.m_120963_(p_137775_, "item"), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_137775_, "targets"), IntegerArgumentType.getInteger((CommandContext)p_137775_, (String)"count")))))));
    }

    private static int m_137778_(CommandSourceStack p_137779_, ItemInput p_137780_, Collection<ServerPlayer> p_137781_, int p_137782_) throws CommandSyntaxException {
        int $$4 = p_137780_.m_120979_().m_41459_();
        int $$5 = $$4 * 100;
        if (p_137782_ > $$5) {
            p_137779_.m_81352_(Component.m_237110_("commands.give.failed.toomanyitems", $$5, p_137780_.m_120980_(p_137782_, false).m_41611_()));
            return 0;
        }
        for (ServerPlayer $$6 : p_137781_) {
            int $$7 = p_137782_;
            while ($$7 > 0) {
                int $$8 = Math.min($$4, $$7);
                $$7 -= $$8;
                ItemStack $$9 = p_137780_.m_120980_($$8, false);
                boolean $$10 = $$6.m_150109_().m_36054_($$9);
                if (!$$10 || !$$9.m_41619_()) {
                    ItemEntity $$11 = $$6.m_36176_($$9, false);
                    if ($$11 == null) continue;
                    $$11.m_32061_();
                    $$11.m_32047_($$6.m_20148_());
                    continue;
                }
                $$9.m_41764_(1);
                ItemEntity $$12 = $$6.m_36176_($$9, false);
                if ($$12 != null) {
                    $$12.m_32065_();
                }
                $$6.f_19853_.m_6263_(null, $$6.m_20185_(), $$6.m_20186_(), $$6.m_20189_(), SoundEvents.f_12019_, SoundSource.PLAYERS, 0.2f, (($$6.m_217043_().m_188501_() - $$6.m_217043_().m_188501_()) * 0.7f + 1.0f) * 2.0f);
                $$6.f_36096_.m_38946_();
            }
        }
        if (p_137781_.size() == 1) {
            p_137779_.m_81354_(Component.m_237110_("commands.give.success.single", p_137782_, p_137780_.m_120980_(p_137782_, false).m_41611_(), p_137781_.iterator().next().m_5446_()), true);
        } else {
            p_137779_.m_81354_(Component.m_237110_("commands.give.success.single", p_137782_, p_137780_.m_120980_(p_137782_, false).m_41611_(), p_137781_.size()), true);
        }
        return p_137781_.size();
    }
}

