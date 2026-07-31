/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.ParseResults
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerFunctionManager;

public class CommandFunction {
    private final Entry[] f_77976_;
    final ResourceLocation f_77977_;

    public CommandFunction(ResourceLocation p_77979_, Entry[] p_77980_) {
        this.f_77977_ = p_77979_;
        this.f_77976_ = p_77980_;
    }

    public ResourceLocation m_77981_() {
        return this.f_77977_;
    }

    public Entry[] m_77989_() {
        return this.f_77976_;
    }

    public static CommandFunction m_77984_(ResourceLocation p_77985_, CommandDispatcher<CommandSourceStack> p_77986_, CommandSourceStack p_77987_, List<String> p_77988_) {
        ArrayList $$4 = Lists.newArrayListWithCapacity((int)p_77988_.size());
        for (int $$5 = 0; $$5 < p_77988_.size(); ++$$5) {
            int $$6 = $$5 + 1;
            String $$7 = p_77988_.get($$5).trim();
            StringReader $$8 = new StringReader($$7);
            if (!$$8.canRead() || $$8.peek() == '#') continue;
            if ($$8.peek() == '/') {
                $$8.skip();
                if ($$8.peek() == '/') {
                    throw new IllegalArgumentException("Unknown or invalid command '" + $$7 + "' on line " + $$6 + " (if you intended to make a comment, use '#' not '//')");
                }
                String $$9 = $$8.readUnquotedString();
                throw new IllegalArgumentException("Unknown or invalid command '" + $$7 + "' on line " + $$6 + " (did you mean '" + $$9 + "'? Do not use a preceding forwards slash.)");
            }
            try {
                ParseResults $$10 = p_77986_.parse($$8, (Object)p_77987_);
                if ($$10.getReader().canRead()) {
                    throw Commands.m_82097_($$10);
                }
                $$4.add(new CommandEntry((ParseResults<CommandSourceStack>)$$10));
                continue;
            }
            catch (CommandSyntaxException $$11) {
                throw new IllegalArgumentException("Whilst parsing command on line " + $$6 + ": " + $$11.getMessage());
            }
        }
        return new CommandFunction(p_77985_, $$4.toArray(new Entry[0]));
    }

    @FunctionalInterface
    public static interface Entry {
        public void m_142134_(ServerFunctionManager var1, CommandSourceStack var2, Deque<ServerFunctionManager.QueuedCommand> var3, int var4, int var5, @Nullable ServerFunctionManager.TraceCallbacks var6) throws CommandSyntaxException;
    }

    public static class CommandEntry
    implements Entry {
        private final ParseResults<CommandSourceStack> f_78004_;

        public CommandEntry(ParseResults<CommandSourceStack> p_78006_) {
            this.f_78004_ = p_78006_;
        }

        @Override
        public void m_142134_(ServerFunctionManager p_164879_, CommandSourceStack p_164880_, Deque<ServerFunctionManager.QueuedCommand> p_164881_, int p_164882_, int p_164883_, @Nullable ServerFunctionManager.TraceCallbacks p_164884_) throws CommandSyntaxException {
            if (p_164884_ != null) {
                String $$6 = this.f_78004_.getReader().getString();
                p_164884_.m_142256_(p_164883_, $$6);
                int $$7 = this.m_164875_(p_164879_, p_164880_);
                p_164884_.m_142279_(p_164883_, $$6, $$7);
            } else {
                this.m_164875_(p_164879_, p_164880_);
            }
        }

        private int m_164875_(ServerFunctionManager p_164876_, CommandSourceStack p_164877_) throws CommandSyntaxException {
            return p_164876_.m_136127_().execute(Commands.m_242611_(this.f_78004_, p_242934_ -> p_164877_));
        }

        public String toString() {
            return this.f_78004_.getReader().getString();
        }
    }

    public static class CacheableFunction {
        public static final CacheableFunction f_77990_ = new CacheableFunction((ResourceLocation)null);
        @Nullable
        private final ResourceLocation f_77991_;
        private boolean f_77992_;
        private Optional<CommandFunction> f_77993_ = Optional.empty();

        public CacheableFunction(@Nullable ResourceLocation p_77998_) {
            this.f_77991_ = p_77998_;
        }

        public CacheableFunction(CommandFunction p_77996_) {
            this.f_77992_ = true;
            this.f_77991_ = null;
            this.f_77993_ = Optional.of(p_77996_);
        }

        public Optional<CommandFunction> m_78002_(ServerFunctionManager p_78003_) {
            if (!this.f_77992_) {
                if (this.f_77991_ != null) {
                    this.f_77993_ = p_78003_.m_136118_(this.f_77991_);
                }
                this.f_77992_ = true;
            }
            return this.f_77993_;
        }

        @Nullable
        public ResourceLocation m_77999_() {
            return this.f_77993_.map(p_78001_ -> p_78001_.f_77977_).orElse(this.f_77991_);
        }
    }

    public static class FunctionEntry
    implements Entry {
        private final CacheableFunction f_78017_;

        public FunctionEntry(CommandFunction p_78019_) {
            this.f_78017_ = new CacheableFunction(p_78019_);
        }

        @Override
        public void m_142134_(ServerFunctionManager p_164902_, CommandSourceStack p_164903_, Deque<ServerFunctionManager.QueuedCommand> p_164904_, int p_164905_, int p_164906_, @Nullable ServerFunctionManager.TraceCallbacks p_164907_) {
            Util.m_137521_(this.f_78017_.m_78002_(p_164902_), p_164900_ -> {
                Entry[] $$6 = p_164900_.m_77989_();
                if (p_164907_ != null) {
                    p_164907_.m_142147_(p_164906_, p_164900_.m_77981_(), $$6.length);
                }
                int $$7 = p_164905_ - p_164904_.size();
                int $$8 = Math.min($$6.length, $$7);
                for (int $$9 = $$8 - 1; $$9 >= 0; --$$9) {
                    p_164904_.addFirst(new ServerFunctionManager.QueuedCommand(p_164903_, p_164906_ + 1, $$6[$$9]));
                }
            }, () -> {
                if (p_164907_ != null) {
                    p_164907_.m_142147_(p_164906_, this.f_78017_.m_77999_(), -1);
                }
            });
        }

        public String toString() {
            return "function " + this.f_78017_.m_77999_();
        }
    }
}

