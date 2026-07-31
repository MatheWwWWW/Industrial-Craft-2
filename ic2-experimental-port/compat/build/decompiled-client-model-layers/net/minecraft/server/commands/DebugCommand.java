/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.logging.LogUtils;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Collection;
import java.util.Locale;
import net.minecraft.Util;
import net.minecraft.commands.CommandFunction;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.item.FunctionArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerFunctionManager;
import net.minecraft.server.commands.FunctionCommand;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.profiling.ProfileResults;
import org.slf4j.Logger;

public class DebugCommand {
    private static final Logger f_136900_ = LogUtils.getLogger();
    private static final SimpleCommandExceptionType f_136901_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.debug.notRunning"));
    private static final SimpleCommandExceptionType f_136902_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.debug.alreadyRunning"));

    public static void m_136905_(CommandDispatcher<CommandSourceStack> p_136906_) {
        p_136906_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("debug").requires(p_180073_ -> p_180073_.m_6761_(3))).then(Commands.m_82127_("start").executes(p_180069_ -> DebugCommand.m_136909_((CommandSourceStack)p_180069_.getSource())))).then(Commands.m_82127_("stop").executes(p_136918_ -> DebugCommand.m_136915_((CommandSourceStack)p_136918_.getSource())))).then(((LiteralArgumentBuilder)Commands.m_82127_("function").requires(p_180071_ -> p_180071_.m_6761_(3))).then(Commands.m_82129_("name", FunctionArgument.m_120907_()).suggests(FunctionCommand.f_137712_).executes(p_136908_ -> DebugCommand.m_180065_((CommandSourceStack)p_136908_.getSource(), FunctionArgument.m_120910_((CommandContext<CommandSourceStack>)p_136908_, "name"))))));
    }

    private static int m_136909_(CommandSourceStack p_136910_) throws CommandSyntaxException {
        MinecraftServer $$1 = p_136910_.m_81377_();
        if ($$1.m_177942_()) {
            throw f_136902_.create();
        }
        $$1.m_177943_();
        p_136910_.m_81354_(Component.m_237115_("commands.debug.started"), true);
        return 0;
    }

    private static int m_136915_(CommandSourceStack p_136916_) throws CommandSyntaxException {
        MinecraftServer $$1 = p_136916_.m_81377_();
        if (!$$1.m_177942_()) {
            throw f_136901_.create();
        }
        ProfileResults $$2 = $$1.m_177944_();
        double $$3 = (double)$$2.m_18577_() / (double)TimeUtil.f_145016_;
        double $$4 = (double)$$2.m_7315_() / $$3;
        p_136916_.m_81354_(Component.m_237110_("commands.debug.stopped", String.format(Locale.ROOT, "%.2f", $$3), $$2.m_7315_(), String.format(Locale.ROOT, "%.2f", $$4)), true);
        return (int)$$4;
    }

    private static int m_180065_(CommandSourceStack p_180066_, Collection<CommandFunction> p_180067_) {
        int $$2 = 0;
        MinecraftServer $$3 = p_180066_.m_81377_();
        String $$4 = "debug-trace-" + Util.m_241986_() + ".txt";
        try {
            Path $$5 = $$3.m_129971_("debug").toPath();
            Files.createDirectories($$5, new FileAttribute[0]);
            try (BufferedWriter $$6 = Files.newBufferedWriter($$5.resolve($$4), StandardCharsets.UTF_8, new OpenOption[0]);){
                PrintWriter $$7 = new PrintWriter($$6);
                for (CommandFunction $$8 : p_180067_) {
                    $$7.println($$8.m_77981_());
                    Tracer $$9 = new Tracer($$7);
                    $$2 += p_180066_.m_81377_().m_129890_().m_179960_($$8, p_180066_.m_165484_($$9).m_81358_(2), $$9);
                }
            }
        }
        catch (IOException | UncheckedIOException $$10) {
            f_136900_.warn("Tracing failed", (Throwable)$$10);
            p_180066_.m_81352_(Component.m_237115_("commands.debug.function.traceFailed"));
        }
        if (p_180067_.size() == 1) {
            p_180066_.m_81354_(Component.m_237110_("commands.debug.function.success.single", $$2, p_180067_.iterator().next().m_77981_(), $$4), true);
        } else {
            p_180066_.m_81354_(Component.m_237110_("commands.debug.function.success.multiple", $$2, p_180067_.size(), $$4), true);
        }
        return $$2;
    }

    static class Tracer
    implements ServerFunctionManager.TraceCallbacks,
    CommandSource {
        public static final int f_180074_ = 1;
        private final PrintWriter f_180075_;
        private int f_180076_;
        private boolean f_180077_;

        Tracer(PrintWriter p_180079_) {
            this.f_180075_ = p_180079_;
        }

        private void m_180081_(int p_180082_) {
            this.m_180097_(p_180082_);
            this.f_180076_ = p_180082_;
        }

        private void m_180097_(int p_180098_) {
            for (int $$1 = 0; $$1 < p_180098_ + 1; ++$$1) {
                this.f_180075_.write("    ");
            }
        }

        private void m_180103_() {
            if (this.f_180077_) {
                this.f_180075_.println();
                this.f_180077_ = false;
            }
        }

        @Override
        public void m_142256_(int p_180084_, String p_180085_) {
            this.m_180103_();
            this.m_180081_(p_180084_);
            this.f_180075_.print("[C] ");
            this.f_180075_.print(p_180085_);
            this.f_180077_ = true;
        }

        @Override
        public void m_142279_(int p_180087_, String p_180088_, int p_180089_) {
            if (this.f_180077_) {
                this.f_180075_.print(" -> ");
                this.f_180075_.println(p_180089_);
                this.f_180077_ = false;
            } else {
                this.m_180081_(p_180087_);
                this.f_180075_.print("[R = ");
                this.f_180075_.print(p_180089_);
                this.f_180075_.print("] ");
                this.f_180075_.println(p_180088_);
            }
        }

        @Override
        public void m_142147_(int p_180091_, ResourceLocation p_180092_, int p_180093_) {
            this.m_180103_();
            this.m_180081_(p_180091_);
            this.f_180075_.print("[F] ");
            this.f_180075_.print(p_180092_);
            this.f_180075_.print(" size=");
            this.f_180075_.println(p_180093_);
        }

        @Override
        public void m_142255_(int p_180100_, String p_180101_) {
            this.m_180103_();
            this.m_180081_(p_180100_ + 1);
            this.f_180075_.print("[E] ");
            this.f_180075_.print(p_180101_);
        }

        @Override
        public void m_213846_(Component p_214427_) {
            this.m_180103_();
            this.m_180097_(this.f_180076_ + 1);
            this.f_180075_.print("[M] ");
            this.f_180075_.println(p_214427_.getString());
        }

        @Override
        public boolean m_6999_() {
            return true;
        }

        @Override
        public boolean m_7028_() {
            return true;
        }

        @Override
        public boolean m_6102_() {
            return false;
        }

        @Override
        public boolean m_142559_() {
            return true;
        }
    }
}

