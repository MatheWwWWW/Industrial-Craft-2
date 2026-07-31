/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 */
package net.minecraft.commands.arguments;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.scores.Score;

public class OperationArgument
implements ArgumentType<Operation> {
    private static final Collection<String> f_103264_ = Arrays.asList("=", ">", "<");
    private static final SimpleCommandExceptionType f_103265_ = new SimpleCommandExceptionType((Message)Component.m_237115_("arguments.operation.invalid"));
    private static final SimpleCommandExceptionType f_103266_ = new SimpleCommandExceptionType((Message)Component.m_237115_("arguments.operation.div0"));

    public static OperationArgument m_103269_() {
        return new OperationArgument();
    }

    public static Operation m_103275_(CommandContext<CommandSourceStack> p_103276_, String p_103277_) {
        return (Operation)p_103276_.getArgument(p_103277_, Operation.class);
    }

    public Operation parse(StringReader p_103274_) throws CommandSyntaxException {
        if (p_103274_.canRead()) {
            int $$1 = p_103274_.getCursor();
            while (p_103274_.canRead() && p_103274_.peek() != ' ') {
                p_103274_.skip();
            }
            return OperationArgument.m_103281_(p_103274_.getString().substring($$1, p_103274_.getCursor()));
        }
        throw f_103265_.create();
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_103302_, SuggestionsBuilder p_103303_) {
        return SharedSuggestionProvider.m_82967_(new String[]{"=", "+=", "-=", "*=", "/=", "%=", "<", ">", "><"}, p_103303_);
    }

    public Collection<String> getExamples() {
        return f_103264_;
    }

    private static Operation m_103281_(String p_103282_) throws CommandSyntaxException {
        if (p_103282_.equals("><")) {
            return (p_103279_, p_103280_) -> {
                int $$2 = p_103279_.m_83400_();
                p_103279_.m_83402_(p_103280_.m_83400_());
                p_103280_.m_83402_($$2);
            };
        }
        return OperationArgument.m_103286_(p_103282_);
    }

    private static SimpleOperation m_103286_(String p_103287_) throws CommandSyntaxException {
        switch (p_103287_) {
            case "=": {
                return (p_103298_, p_103299_) -> p_103299_;
            }
            case "+=": {
                return (p_103295_, p_103296_) -> p_103295_ + p_103296_;
            }
            case "-=": {
                return (p_103292_, p_103293_) -> p_103292_ - p_103293_;
            }
            case "*=": {
                return (p_103289_, p_103290_) -> p_103289_ * p_103290_;
            }
            case "/=": {
                return (p_103284_, p_103285_) -> {
                    if (p_103285_ == 0) {
                        throw f_103266_.create();
                    }
                    return Mth.m_14042_(p_103284_, p_103285_);
                };
            }
            case "%=": {
                return (p_103271_, p_103272_) -> {
                    if (p_103272_ == 0) {
                        throw f_103266_.create();
                    }
                    return Mth.m_14100_(p_103271_, p_103272_);
                };
            }
            case "<": {
                return Math::min;
            }
            case ">": {
                return Math::max;
            }
        }
        throw f_103265_.create();
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    @FunctionalInterface
    public static interface Operation {
        public void m_6407_(Score var1, Score var2) throws CommandSyntaxException;
    }

    @FunctionalInterface
    static interface SimpleOperation
    extends Operation {
        public int m_103308_(int var1, int var2) throws CommandSyntaxException;

        @Override
        default public void m_6407_(Score p_103312_, Score p_103313_) throws CommandSyntaxException {
            p_103312_.m_83402_(this.m_103308_(p_103312_.m_83400_(), p_103313_.m_83400_()));
        }
    }
}

