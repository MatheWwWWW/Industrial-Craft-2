/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.raid.Raids;

public class RaidCommand {
    public static void m_180468_(CommandDispatcher<CommandSourceStack> p_180469_) {
        p_180469_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("raid").requires(p_180498_ -> p_180498_.m_6761_(3))).then(Commands.m_82127_("start").then(Commands.m_82129_("omenlvl", IntegerArgumentType.integer((int)0)).executes(p_180502_ -> RaidCommand.m_180484_((CommandSourceStack)p_180502_.getSource(), IntegerArgumentType.getInteger((CommandContext)p_180502_, (String)"omenlvl")))))).then(Commands.m_82127_("stop").executes(p_180500_ -> RaidCommand.m_180489_((CommandSourceStack)p_180500_.getSource())))).then(Commands.m_82127_("check").executes(p_180496_ -> RaidCommand.m_180493_((CommandSourceStack)p_180496_.getSource())))).then(Commands.m_82127_("sound").then(Commands.m_82129_("type", ComponentArgument.m_87114_()).executes(p_180492_ -> RaidCommand.m_180477_((CommandSourceStack)p_180492_.getSource(), ComponentArgument.m_87117_((CommandContext<CommandSourceStack>)p_180492_, "type")))))).then(Commands.m_82127_("spawnleader").executes(p_180488_ -> RaidCommand.m_180482_((CommandSourceStack)p_180488_.getSource())))).then(Commands.m_82127_("setomen").then(Commands.m_82129_("level", IntegerArgumentType.integer((int)0)).executes(p_180481_ -> RaidCommand.m_180474_((CommandSourceStack)p_180481_.getSource(), IntegerArgumentType.getInteger((CommandContext)p_180481_, (String)"level")))))).then(Commands.m_82127_("glow").executes(p_180471_ -> RaidCommand.m_180472_((CommandSourceStack)p_180471_.getSource()))));
    }

    private static int m_180472_(CommandSourceStack p_180473_) throws CommandSyntaxException {
        Raid $$1 = RaidCommand.m_180466_(p_180473_.m_81375_());
        if ($$1 != null) {
            Set<Raider> $$2 = $$1.m_150221_();
            for (Raider $$3 : $$2) {
                $$3.m_7292_(new MobEffectInstance(MobEffects.f_19619_, 1000, 1));
            }
        }
        return 1;
    }

    private static int m_180474_(CommandSourceStack p_180475_, int p_180476_) throws CommandSyntaxException {
        Raid $$2 = RaidCommand.m_180466_(p_180475_.m_81375_());
        if ($$2 != null) {
            int $$3 = $$2.m_37772_();
            if (p_180476_ > $$3) {
                p_180475_.m_81352_(Component.m_237113_("Sorry, the max bad omen level you can set is " + $$3));
            } else {
                int $$4 = $$2.m_37773_();
                $$2.m_150218_(p_180476_);
                p_180475_.m_81354_(Component.m_237113_("Changed village's bad omen level from " + $$4 + " to " + p_180476_), false);
            }
        } else {
            p_180475_.m_81352_(Component.m_237113_("No raid found here"));
        }
        return 1;
    }

    private static int m_180482_(CommandSourceStack p_180483_) {
        p_180483_.m_81354_(Component.m_237113_("Spawned a raid captain"), false);
        Raider $$1 = EntityType.f_20513_.m_20615_(p_180483_.m_81372_());
        $$1.m_33075_(true);
        $$1.m_8061_(EquipmentSlot.HEAD, Raid.m_37779_());
        $$1.m_6034_(p_180483_.m_81371_().f_82479_, p_180483_.m_81371_().f_82480_, p_180483_.m_81371_().f_82481_);
        $$1.m_6518_(p_180483_.m_81372_(), p_180483_.m_81372_().m_6436_(new BlockPos(p_180483_.m_81371_())), MobSpawnType.COMMAND, null, null);
        p_180483_.m_81372_().m_47205_($$1);
        return 1;
    }

    private static int m_180477_(CommandSourceStack p_180478_, Component p_180479_) {
        if (p_180479_ != null && p_180479_.getString().equals("local")) {
            p_180478_.m_81372_().m_5594_(null, new BlockPos(p_180478_.m_81371_().m_82520_(5.0, 0.0, 0.0)), SoundEvents.f_12355_, SoundSource.NEUTRAL, 2.0f, 1.0f);
        }
        return 1;
    }

    private static int m_180484_(CommandSourceStack p_180485_, int p_180486_) throws CommandSyntaxException {
        ServerPlayer $$2 = p_180485_.m_81375_();
        BlockPos $$3 = $$2.m_20183_();
        if ($$2.m_9236_().m_8843_($$3)) {
            p_180485_.m_81352_(Component.m_237113_("Raid already started close by"));
            return -1;
        }
        Raids $$4 = $$2.m_9236_().m_8905_();
        Raid $$5 = $$4.m_37963_($$2);
        if ($$5 != null) {
            $$5.m_150218_(p_180486_);
            $$4.m_77762_();
            p_180485_.m_81354_(Component.m_237113_("Created a raid in your local village"), false);
        } else {
            p_180485_.m_81352_(Component.m_237113_("Failed to create a raid in your local village"));
        }
        return 1;
    }

    private static int m_180489_(CommandSourceStack p_180490_) throws CommandSyntaxException {
        ServerPlayer $$1 = p_180490_.m_81375_();
        BlockPos $$2 = $$1.m_20183_();
        Raid $$3 = $$1.m_9236_().m_8832_($$2);
        if ($$3 != null) {
            $$3.m_37774_();
            p_180490_.m_81354_(Component.m_237113_("Stopped raid"), false);
            return 1;
        }
        p_180490_.m_81352_(Component.m_237113_("No raid here"));
        return -1;
    }

    private static int m_180493_(CommandSourceStack p_180494_) throws CommandSyntaxException {
        Raid $$1 = RaidCommand.m_180466_(p_180494_.m_81375_());
        if ($$1 != null) {
            StringBuilder $$2 = new StringBuilder();
            $$2.append("Found a started raid! ");
            p_180494_.m_81354_(Component.m_237113_($$2.toString()), false);
            $$2 = new StringBuilder();
            $$2.append("Num groups spawned: ");
            $$2.append($$1.m_37771_());
            $$2.append(" Bad omen level: ");
            $$2.append($$1.m_37773_());
            $$2.append(" Num mobs: ");
            $$2.append($$1.m_37778_());
            $$2.append(" Raid health: ");
            $$2.append($$1.m_37777_());
            $$2.append(" / ");
            $$2.append($$1.m_150220_());
            p_180494_.m_81354_(Component.m_237113_($$2.toString()), false);
            return 1;
        }
        p_180494_.m_81352_(Component.m_237113_("Found no started raids"));
        return 0;
    }

    @Nullable
    private static Raid m_180466_(ServerPlayer p_180467_) {
        return p_180467_.m_9236_().m_8832_(p_180467_.m_20183_());
    }
}

