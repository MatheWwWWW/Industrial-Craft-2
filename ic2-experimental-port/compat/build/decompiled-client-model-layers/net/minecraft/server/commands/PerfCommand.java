/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.logging.LogUtils
 *  org.apache.commons.io.FileUtils
 *  org.slf4j.Logger
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.logging.LogUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.function.Consumer;
import net.minecraft.FileUtil;
import net.minecraft.SharedConstants;
import net.minecraft.SystemReport;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.FileZipper;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.profiling.EmptyProfileResults;
import net.minecraft.util.profiling.ProfileResults;
import net.minecraft.util.profiling.metrics.storage.MetricsPersister;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;

public class PerfCommand {
    private static final Logger f_180432_ = LogUtils.getLogger();
    private static final SimpleCommandExceptionType f_180433_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.perf.notRunning"));
    private static final SimpleCommandExceptionType f_180434_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.perf.alreadyRunning"));

    public static void m_180437_(CommandDispatcher<CommandSourceStack> p_180438_) {
        p_180438_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("perf").requires(p_180462_ -> p_180462_.m_6761_(4))).then(Commands.m_82127_("start").executes(p_180455_ -> PerfCommand.m_180441_((CommandSourceStack)p_180455_.getSource())))).then(Commands.m_82127_("stop").executes(p_180440_ -> PerfCommand.m_180456_((CommandSourceStack)p_180440_.getSource()))));
    }

    private static int m_180441_(CommandSourceStack p_180442_) throws CommandSyntaxException {
        MinecraftServer $$1 = p_180442_.m_81377_();
        if ($$1.m_177927_()) {
            throw f_180434_.create();
        }
        Consumer<ProfileResults> $$2 = p_180460_ -> PerfCommand.m_180443_(p_180442_, p_180460_);
        Consumer<Path> $$3 = p_180453_ -> PerfCommand.m_180446_(p_180442_, p_180453_, $$1);
        $$1.m_177923_($$2, $$3);
        p_180442_.m_81354_(Component.m_237115_("commands.perf.started"), false);
        return 0;
    }

    private static int m_180456_(CommandSourceStack p_180457_) throws CommandSyntaxException {
        MinecraftServer $$1 = p_180457_.m_81377_();
        if (!$$1.m_177927_()) {
            throw f_180433_.create();
        }
        $$1.m_177929_();
        return 0;
    }

    /*
     * WARNING - void declaration
     */
    private static void m_180446_(CommandSourceStack p_180447_, Path p_180448_, MinecraftServer p_180449_) {
        void $$6;
        String $$3 = String.format(Locale.ROOT, "%s-%s-%s", Util.m_241986_(), p_180449_.m_129910_().m_5462_(), SharedConstants.m_183709_().getId());
        try {
            String $$4 = FileUtil.m_133730_(MetricsPersister.f_146209_, $$3, ".zip");
        }
        catch (IOException $$5) {
            p_180447_.m_81352_(Component.m_237115_("commands.perf.reportFailed"));
            f_180432_.error("Failed to create report name", (Throwable)$$5);
            return;
        }
        try (FileZipper $$7 = new FileZipper(MetricsPersister.f_146209_.resolve((String)$$6));){
            $$7.m_144703_(Paths.get("system.txt", new String[0]), p_180449_.m_177935_(new SystemReport()).m_143515_());
            $$7.m_144698_(p_180448_);
        }
        try {
            FileUtils.forceDelete((File)p_180448_.toFile());
        }
        catch (IOException $$8) {
            f_180432_.warn("Failed to delete temporary profiling file {}", (Object)p_180448_, (Object)$$8);
        }
        p_180447_.m_81354_(Component.m_237110_("commands.perf.reportSaved", $$6), false);
    }

    private static void m_180443_(CommandSourceStack p_180444_, ProfileResults p_180445_) {
        if (p_180445_ == EmptyProfileResults.f_18441_) {
            return;
        }
        int $$2 = p_180445_.m_7315_();
        double $$3 = (double)p_180445_.m_18577_() / (double)TimeUtil.f_145016_;
        p_180444_.m_81354_(Component.m_237110_("commands.perf.stopped", String.format(Locale.ROOT, "%.2f", $$3), $$2, String.format(Locale.ROOT, "%.2f", (double)$$2 / $$3)), false);
    }
}

