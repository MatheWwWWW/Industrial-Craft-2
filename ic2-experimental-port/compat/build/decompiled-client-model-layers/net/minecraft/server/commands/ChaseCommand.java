/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.BiMap
 *  com.google.common.collect.ImmutableBiMap
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  javax.annotation.Nullable
 */
package net.minecraft.server.commands;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.chase.ChaseClient;
import net.minecraft.server.chase.ChaseServer;
import net.minecraft.world.level.Level;

public class ChaseCommand {
    private static final String f_196069_ = "localhost";
    private static final String f_196070_ = "0.0.0.0";
    private static final int f_196071_ = 10000;
    private static final int f_196072_ = 100;
    public static BiMap<String, ResourceKey<Level>> f_196068_ = ImmutableBiMap.of((Object)"o", Level.f_46428_, (Object)"n", Level.f_46429_, (Object)"e", Level.f_46430_);
    @Nullable
    private static ChaseServer f_196073_;
    @Nullable
    private static ChaseClient f_196074_;

    public static void m_196077_(CommandDispatcher<CommandSourceStack> p_196078_) {
        p_196078_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("chase").then(((LiteralArgumentBuilder)Commands.m_82127_("follow").then(((RequiredArgumentBuilder)Commands.m_82129_("host", StringArgumentType.string()).executes(p_196104_ -> ChaseCommand.m_196091_((CommandSourceStack)p_196104_.getSource(), StringArgumentType.getString((CommandContext)p_196104_, (String)"host"), 10000))).then(Commands.m_82129_("port", IntegerArgumentType.integer((int)1, (int)65535)).executes(p_196102_ -> ChaseCommand.m_196091_((CommandSourceStack)p_196102_.getSource(), StringArgumentType.getString((CommandContext)p_196102_, (String)"host"), IntegerArgumentType.getInteger((CommandContext)p_196102_, (String)"port")))))).executes(p_196100_ -> ChaseCommand.m_196091_((CommandSourceStack)p_196100_.getSource(), f_196069_, 10000)))).then(((LiteralArgumentBuilder)Commands.m_82127_("lead").then(((RequiredArgumentBuilder)Commands.m_82129_("bind_address", StringArgumentType.string()).executes(p_196098_ -> ChaseCommand.m_196083_((CommandSourceStack)p_196098_.getSource(), StringArgumentType.getString((CommandContext)p_196098_, (String)"bind_address"), 10000))).then(Commands.m_82129_("port", IntegerArgumentType.integer((int)1024, (int)65535)).executes(p_196096_ -> ChaseCommand.m_196083_((CommandSourceStack)p_196096_.getSource(), StringArgumentType.getString((CommandContext)p_196096_, (String)"bind_address"), IntegerArgumentType.getInteger((CommandContext)p_196096_, (String)"port")))))).executes(p_196088_ -> ChaseCommand.m_196083_((CommandSourceStack)p_196088_.getSource(), f_196070_, 10000)))).then(Commands.m_82127_("stop").executes(p_196080_ -> ChaseCommand.m_196081_((CommandSourceStack)p_196080_.getSource()))));
    }

    private static int m_196081_(CommandSourceStack p_196082_) {
        if (f_196074_ != null) {
            f_196074_.m_196000_();
            p_196082_.m_81354_(Component.m_237113_("You have now stopped chasing"), false);
            f_196074_ = null;
        }
        if (f_196073_ != null) {
            f_196073_.m_196040_();
            p_196082_.m_81354_(Component.m_237113_("You are no longer being chased"), false);
            f_196073_ = null;
        }
        return 0;
    }

    private static boolean m_196089_(CommandSourceStack p_196090_) {
        if (f_196073_ != null) {
            p_196090_.m_81352_(Component.m_237113_("Chase server is already running. Stop it using /chase stop"));
            return true;
        }
        if (f_196074_ != null) {
            p_196090_.m_81352_(Component.m_237113_("You are already chasing someone. Stop it using /chase stop"));
            return true;
        }
        return false;
    }

    private static int m_196083_(CommandSourceStack p_196084_, String p_196085_, int p_196086_) {
        if (ChaseCommand.m_196089_(p_196084_)) {
            return 0;
        }
        f_196073_ = new ChaseServer(p_196085_, p_196086_, p_196084_.m_81377_().m_6846_(), 100);
        try {
            f_196073_.m_196036_();
            p_196084_.m_81354_(Component.m_237113_("Chase server is now running on port " + p_196086_ + ". Clients can follow you using /chase follow <ip> <port>"), false);
        }
        catch (IOException $$3) {
            $$3.printStackTrace();
            p_196084_.m_81352_(Component.m_237113_("Failed to start chase server on port " + p_196086_));
            f_196073_ = null;
        }
        return 0;
    }

    private static int m_196091_(CommandSourceStack p_196092_, String p_196093_, int p_196094_) {
        if (ChaseCommand.m_196089_(p_196092_)) {
            return 0;
        }
        f_196074_ = new ChaseClient(p_196093_, p_196094_, p_196092_.m_81377_());
        f_196074_.m_195993_();
        p_196092_.m_81354_(Component.m_237113_("You are now chasing " + p_196093_ + ":" + p_196094_ + ". If that server does '/chase lead' then you will automatically go to the same position. Use '/chase stop' to stop chasing."), false);
        return 0;
    }
}

