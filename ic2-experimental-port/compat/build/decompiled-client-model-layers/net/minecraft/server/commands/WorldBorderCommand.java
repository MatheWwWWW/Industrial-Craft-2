/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.DoubleArgumentType
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
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Locale;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Vec2Argument;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.Vec2;

public class WorldBorderCommand {
    private static final SimpleCommandExceptionType f_139237_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.worldborder.center.failed"));
    private static final SimpleCommandExceptionType f_139238_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.worldborder.set.failed.nochange"));
    private static final SimpleCommandExceptionType f_139239_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.worldborder.set.failed.small"));
    private static final SimpleCommandExceptionType f_139240_ = new SimpleCommandExceptionType((Message)Component.m_237110_("commands.worldborder.set.failed.big", 5.9999968E7));
    private static final SimpleCommandExceptionType f_196554_ = new SimpleCommandExceptionType((Message)Component.m_237110_("commands.worldborder.set.failed.far", 2.9999984E7));
    private static final SimpleCommandExceptionType f_139241_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.worldborder.warning.time.failed"));
    private static final SimpleCommandExceptionType f_139242_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.worldborder.warning.distance.failed"));
    private static final SimpleCommandExceptionType f_139243_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.worldborder.damage.buffer.failed"));
    private static final SimpleCommandExceptionType f_139244_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.worldborder.damage.amount.failed"));

    public static void m_139246_(CommandDispatcher<CommandSourceStack> p_139247_) {
        p_139247_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("worldborder").requires(p_139268_ -> p_139268_.m_6761_(2))).then(Commands.m_82127_("add").then(((RequiredArgumentBuilder)Commands.m_82129_("distance", DoubleArgumentType.doubleArg((double)-5.9999968E7, (double)5.9999968E7)).executes(p_139290_ -> WorldBorderCommand.m_139252_((CommandSourceStack)p_139290_.getSource(), ((CommandSourceStack)p_139290_.getSource()).m_81372_().m_6857_().m_61959_() + DoubleArgumentType.getDouble((CommandContext)p_139290_, (String)"distance"), 0L))).then(Commands.m_82129_("time", IntegerArgumentType.integer((int)0)).executes(p_139288_ -> WorldBorderCommand.m_139252_((CommandSourceStack)p_139288_.getSource(), ((CommandSourceStack)p_139288_.getSource()).m_81372_().m_6857_().m_61959_() + DoubleArgumentType.getDouble((CommandContext)p_139288_, (String)"distance"), ((CommandSourceStack)p_139288_.getSource()).m_81372_().m_6857_().m_61960_() + (long)IntegerArgumentType.getInteger((CommandContext)p_139288_, (String)"time") * 1000L)))))).then(Commands.m_82127_("set").then(((RequiredArgumentBuilder)Commands.m_82129_("distance", DoubleArgumentType.doubleArg((double)-5.9999968E7, (double)5.9999968E7)).executes(p_139286_ -> WorldBorderCommand.m_139252_((CommandSourceStack)p_139286_.getSource(), DoubleArgumentType.getDouble((CommandContext)p_139286_, (String)"distance"), 0L))).then(Commands.m_82129_("time", IntegerArgumentType.integer((int)0)).executes(p_139284_ -> WorldBorderCommand.m_139252_((CommandSourceStack)p_139284_.getSource(), DoubleArgumentType.getDouble((CommandContext)p_139284_, (String)"distance"), (long)IntegerArgumentType.getInteger((CommandContext)p_139284_, (String)"time") * 1000L)))))).then(Commands.m_82127_("center").then(Commands.m_82129_("pos", Vec2Argument.m_120822_()).executes(p_139282_ -> WorldBorderCommand.m_139262_((CommandSourceStack)p_139282_.getSource(), Vec2Argument.m_120825_((CommandContext<CommandSourceStack>)p_139282_, "pos")))))).then(((LiteralArgumentBuilder)Commands.m_82127_("damage").then(Commands.m_82127_("amount").then(Commands.m_82129_("damagePerBlock", FloatArgumentType.floatArg((float)0.0f)).executes(p_139280_ -> WorldBorderCommand.m_139269_((CommandSourceStack)p_139280_.getSource(), FloatArgumentType.getFloat((CommandContext)p_139280_, (String)"damagePerBlock")))))).then(Commands.m_82127_("buffer").then(Commands.m_82129_("distance", FloatArgumentType.floatArg((float)0.0f)).executes(p_139278_ -> WorldBorderCommand.m_139256_((CommandSourceStack)p_139278_.getSource(), FloatArgumentType.getFloat((CommandContext)p_139278_, (String)"distance"))))))).then(Commands.m_82127_("get").executes(p_139276_ -> WorldBorderCommand.m_139250_((CommandSourceStack)p_139276_.getSource())))).then(((LiteralArgumentBuilder)Commands.m_82127_("warning").then(Commands.m_82127_("distance").then(Commands.m_82129_("distance", IntegerArgumentType.integer((int)0)).executes(p_139266_ -> WorldBorderCommand.m_139272_((CommandSourceStack)p_139266_.getSource(), IntegerArgumentType.getInteger((CommandContext)p_139266_, (String)"distance")))))).then(Commands.m_82127_("time").then(Commands.m_82129_("time", IntegerArgumentType.integer((int)0)).executes(p_139249_ -> WorldBorderCommand.m_139259_((CommandSourceStack)p_139249_.getSource(), IntegerArgumentType.getInteger((CommandContext)p_139249_, (String)"time")))))));
    }

    private static int m_139256_(CommandSourceStack p_139257_, float p_139258_) throws CommandSyntaxException {
        WorldBorder $$2 = p_139257_.m_81377_().m_129783_().m_6857_();
        if ($$2.m_61964_() == (double)p_139258_) {
            throw f_139243_.create();
        }
        $$2.m_61939_(p_139258_);
        p_139257_.m_81354_(Component.m_237110_("commands.worldborder.damage.buffer.success", String.format(Locale.ROOT, "%.2f", Float.valueOf(p_139258_))), true);
        return (int)p_139258_;
    }

    private static int m_139269_(CommandSourceStack p_139270_, float p_139271_) throws CommandSyntaxException {
        WorldBorder $$2 = p_139270_.m_81377_().m_129783_().m_6857_();
        if ($$2.m_61965_() == (double)p_139271_) {
            throw f_139244_.create();
        }
        $$2.m_61947_(p_139271_);
        p_139270_.m_81354_(Component.m_237110_("commands.worldborder.damage.amount.success", String.format(Locale.ROOT, "%.2f", Float.valueOf(p_139271_))), true);
        return (int)p_139271_;
    }

    private static int m_139259_(CommandSourceStack p_139260_, int p_139261_) throws CommandSyntaxException {
        WorldBorder $$2 = p_139260_.m_81377_().m_129783_().m_6857_();
        if ($$2.m_61967_() == p_139261_) {
            throw f_139241_.create();
        }
        $$2.m_61944_(p_139261_);
        p_139260_.m_81354_(Component.m_237110_("commands.worldborder.warning.time.success", p_139261_), true);
        return p_139261_;
    }

    private static int m_139272_(CommandSourceStack p_139273_, int p_139274_) throws CommandSyntaxException {
        WorldBorder $$2 = p_139273_.m_81377_().m_129783_().m_6857_();
        if ($$2.m_61968_() == p_139274_) {
            throw f_139242_.create();
        }
        $$2.m_61952_(p_139274_);
        p_139273_.m_81354_(Component.m_237110_("commands.worldborder.warning.distance.success", p_139274_), true);
        return p_139274_;
    }

    private static int m_139250_(CommandSourceStack p_139251_) {
        double $$1 = p_139251_.m_81377_().m_129783_().m_6857_().m_61959_();
        p_139251_.m_81354_(Component.m_237110_("commands.worldborder.get", String.format(Locale.ROOT, "%.0f", $$1)), false);
        return Mth.m_14107_($$1 + 0.5);
    }

    private static int m_139262_(CommandSourceStack p_139263_, Vec2 p_139264_) throws CommandSyntaxException {
        WorldBorder $$2 = p_139263_.m_81377_().m_129783_().m_6857_();
        if ($$2.m_6347_() == (double)p_139264_.f_82470_ && $$2.m_6345_() == (double)p_139264_.f_82471_) {
            throw f_139237_.create();
        }
        if ((double)Math.abs(p_139264_.f_82470_) > 2.9999984E7 || (double)Math.abs(p_139264_.f_82471_) > 2.9999984E7) {
            throw f_196554_.create();
        }
        $$2.m_61949_(p_139264_.f_82470_, p_139264_.f_82471_);
        p_139263_.m_81354_(Component.m_237110_("commands.worldborder.center.success", String.format(Locale.ROOT, "%.2f", Float.valueOf(p_139264_.f_82470_)), String.format(Locale.ROOT, "%.2f", Float.valueOf(p_139264_.f_82471_))), true);
        return 0;
    }

    private static int m_139252_(CommandSourceStack p_139253_, double p_139254_, long p_139255_) throws CommandSyntaxException {
        WorldBorder $$3 = p_139253_.m_81377_().m_129783_().m_6857_();
        double $$4 = $$3.m_61959_();
        if ($$4 == p_139254_) {
            throw f_139238_.create();
        }
        if (p_139254_ < 1.0) {
            throw f_139239_.create();
        }
        if (p_139254_ > 5.9999968E7) {
            throw f_139240_.create();
        }
        if (p_139255_ > 0L) {
            $$3.m_61919_($$4, p_139254_, p_139255_);
            if (p_139254_ > $$4) {
                p_139253_.m_81354_(Component.m_237110_("commands.worldborder.set.grow", String.format(Locale.ROOT, "%.1f", p_139254_), Long.toString(p_139255_ / 1000L)), true);
            } else {
                p_139253_.m_81354_(Component.m_237110_("commands.worldborder.set.shrink", String.format(Locale.ROOT, "%.1f", p_139254_), Long.toString(p_139255_ / 1000L)), true);
            }
        } else {
            $$3.m_61917_(p_139254_);
            p_139253_.m_81354_(Component.m_237110_("commands.worldborder.set.immediate", String.format(Locale.ROOT, "%.1f", p_139254_)), true);
        }
        return (int)(p_139254_ - $$4);
    }
}

