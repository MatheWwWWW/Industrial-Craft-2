/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonObject
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.suggestion.SuggestionProvider
 */
package net.minecraft.commands.arguments;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;

public class ScoreHolderArgument
implements ArgumentType<Result> {
    public static final SuggestionProvider<CommandSourceStack> f_108210_ = (p_108221_, p_108222_) -> {
        StringReader $$2 = new StringReader(p_108222_.getInput());
        $$2.setCursor(p_108222_.getStart());
        EntitySelectorParser $$3 = new EntitySelectorParser($$2);
        try {
            $$3.m_121377_();
        }
        catch (CommandSyntaxException commandSyntaxException) {
            // empty catch block
        }
        return $$3.m_121249_(p_108222_, p_171606_ -> SharedSuggestionProvider.m_82970_(((CommandSourceStack)p_108221_.getSource()).m_5982_(), p_171606_));
    };
    private static final Collection<String> f_108211_ = Arrays.asList("Player", "0123", "*", "@e");
    private static final SimpleCommandExceptionType f_108212_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.scoreHolder.empty"));
    final boolean f_108213_;

    public ScoreHolderArgument(boolean p_108216_) {
        this.f_108213_ = p_108216_;
    }

    public static String m_108223_(CommandContext<CommandSourceStack> p_108224_, String p_108225_) throws CommandSyntaxException {
        return ScoreHolderArgument.m_108243_(p_108224_, p_108225_).iterator().next();
    }

    public static Collection<String> m_108243_(CommandContext<CommandSourceStack> p_108244_, String p_108245_) throws CommandSyntaxException {
        return ScoreHolderArgument.m_108226_(p_108244_, p_108245_, Collections::emptyList);
    }

    public static Collection<String> m_108246_(CommandContext<CommandSourceStack> p_108247_, String p_108248_) throws CommandSyntaxException {
        return ScoreHolderArgument.m_108226_(p_108247_, p_108248_, ((CommandSourceStack)p_108247_.getSource()).m_81377_().m_129896_()::m_83482_);
    }

    public static Collection<String> m_108226_(CommandContext<CommandSourceStack> p_108227_, String p_108228_, Supplier<Collection<String>> p_108229_) throws CommandSyntaxException {
        Collection<String> $$3 = ((Result)p_108227_.getArgument(p_108228_, Result.class)).m_6582_((CommandSourceStack)p_108227_.getSource(), p_108229_);
        if ($$3.isEmpty()) {
            throw EntityArgument.f_91439_.create();
        }
        return $$3;
    }

    public static ScoreHolderArgument m_108217_() {
        return new ScoreHolderArgument(false);
    }

    public static ScoreHolderArgument m_108239_() {
        return new ScoreHolderArgument(true);
    }

    public Result parse(StringReader p_108219_) throws CommandSyntaxException {
        if (p_108219_.canRead() && p_108219_.peek() == '@') {
            EntitySelectorParser $$1 = new EntitySelectorParser(p_108219_);
            EntitySelector $$2 = $$1.m_121377_();
            if (!this.f_108213_ && $$2.m_121138_() > 1) {
                throw EntityArgument.f_91436_.create();
            }
            return new SelectorResult($$2);
        }
        int $$3 = p_108219_.getCursor();
        while (p_108219_.canRead() && p_108219_.peek() != ' ') {
            p_108219_.skip();
        }
        String $$4 = p_108219_.getString().substring($$3, p_108219_.getCursor());
        if ($$4.equals("*")) {
            return (p_108231_, p_108232_) -> {
                Collection $$2 = (Collection)p_108232_.get();
                if ($$2.isEmpty()) {
                    throw f_108212_.create();
                }
                return $$2;
            };
        }
        Set<String> $$5 = Collections.singleton($$4);
        return (p_108237_, p_108238_) -> $$5;
    }

    public Collection<String> getExamples() {
        return f_108211_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    @FunctionalInterface
    public static interface Result {
        public Collection<String> m_6582_(CommandSourceStack var1, Supplier<Collection<String>> var2) throws CommandSyntaxException;
    }

    public static class SelectorResult
    implements Result {
        private final EntitySelector f_108254_;

        public SelectorResult(EntitySelector p_108256_) {
            this.f_108254_ = p_108256_;
        }

        @Override
        public Collection<String> m_6582_(CommandSourceStack p_108258_, Supplier<Collection<String>> p_108259_) throws CommandSyntaxException {
            List<? extends Entity> $$2 = this.f_108254_.m_121160_(p_108258_);
            if ($$2.isEmpty()) {
                throw EntityArgument.f_91439_.create();
            }
            ArrayList $$3 = Lists.newArrayList();
            for (Entity entity : $$2) {
                $$3.add(entity.m_6302_());
            }
            return $$3;
        }
    }

    public static class Info
    implements ArgumentTypeInfo<ScoreHolderArgument, Template> {
        private static final byte f_233461_ = 1;

        @Override
        public void m_214155_(Template p_233469_, FriendlyByteBuf p_233470_) {
            int $$2 = 0;
            if (p_233469_.f_233484_) {
                $$2 |= 1;
            }
            p_233470_.writeByte($$2);
        }

        @Override
        public Template m_213618_(FriendlyByteBuf p_233480_) {
            byte $$1 = p_233480_.readByte();
            boolean $$2 = ($$1 & 1) != 0;
            return new Template($$2);
        }

        @Override
        public void m_213719_(Template p_233466_, JsonObject p_233467_) {
            p_233467_.addProperty("amount", p_233466_.f_233484_ ? "multiple" : "single");
        }

        @Override
        public Template m_214163_(ScoreHolderArgument p_233472_) {
            return new Template(p_233472_.f_108213_);
        }

        @Override
        public /* synthetic */ ArgumentTypeInfo.Template m_213618_(FriendlyByteBuf friendlyByteBuf) {
            return this.m_213618_(friendlyByteBuf);
        }

        public final class Template
        implements ArgumentTypeInfo.Template<ScoreHolderArgument> {
            final boolean f_233484_;

            Template(boolean p_233487_) {
                this.f_233484_ = p_233487_;
            }

            @Override
            public ScoreHolderArgument m_213879_(CommandBuildContext p_233490_) {
                return new ScoreHolderArgument(this.f_233484_);
            }

            @Override
            public ArgumentTypeInfo<ScoreHolderArgument, ?> m_213709_() {
                return Info.this;
            }

            @Override
            public /* synthetic */ ArgumentType m_213879_(CommandBuildContext commandBuildContext) {
                return this.m_213879_(commandBuildContext);
            }
        }
    }
}

