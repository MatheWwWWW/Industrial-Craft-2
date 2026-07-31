/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.datafixers.util.Either
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.commands.arguments.item;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.commands.CommandFunction;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class FunctionArgument
implements ArgumentType<Result> {
    private static final Collection<String> f_120902_ = Arrays.asList("foo", "foo:bar", "#foo");
    private static final DynamicCommandExceptionType f_120903_ = new DynamicCommandExceptionType(p_120927_ -> Component.m_237110_("arguments.function.tag.unknown", p_120927_));
    private static final DynamicCommandExceptionType f_120904_ = new DynamicCommandExceptionType(p_120917_ -> Component.m_237110_("arguments.function.unknown", p_120917_));

    public static FunctionArgument m_120907_() {
        return new FunctionArgument();
    }

    public Result parse(StringReader p_120909_) throws CommandSyntaxException {
        if (p_120909_.canRead() && p_120909_.peek() == '#') {
            p_120909_.skip();
            final ResourceLocation $$1 = ResourceLocation.m_135818_(p_120909_);
            return new Result(){

                @Override
                public Collection<CommandFunction> m_7588_(CommandContext<CommandSourceStack> p_120943_) throws CommandSyntaxException {
                    return FunctionArgument.m_235273_(p_120943_, $$1);
                }

                @Override
                public Pair<ResourceLocation, Either<CommandFunction, Collection<CommandFunction>>> m_5911_(CommandContext<CommandSourceStack> p_120945_) throws CommandSyntaxException {
                    return Pair.of((Object)$$1, (Object)Either.right(FunctionArgument.m_235273_(p_120945_, $$1)));
                }
            };
        }
        final ResourceLocation $$2 = ResourceLocation.m_135818_(p_120909_);
        return new Result(){

            @Override
            public Collection<CommandFunction> m_7588_(CommandContext<CommandSourceStack> p_120952_) throws CommandSyntaxException {
                return Collections.singleton(FunctionArgument.m_120928_(p_120952_, $$2));
            }

            @Override
            public Pair<ResourceLocation, Either<CommandFunction, Collection<CommandFunction>>> m_5911_(CommandContext<CommandSourceStack> p_120954_) throws CommandSyntaxException {
                return Pair.of((Object)$$2, (Object)Either.left((Object)FunctionArgument.m_120928_(p_120954_, $$2)));
            }
        };
    }

    static CommandFunction m_120928_(CommandContext<CommandSourceStack> p_120929_, ResourceLocation p_120930_) throws CommandSyntaxException {
        return ((CommandSourceStack)p_120929_.getSource()).m_81377_().m_129890_().m_136118_(p_120930_).orElseThrow(() -> f_120904_.create((Object)p_120930_.toString()));
    }

    static Collection<CommandFunction> m_235273_(CommandContext<CommandSourceStack> p_235274_, ResourceLocation p_235275_) throws CommandSyntaxException {
        Collection<CommandFunction> $$2 = ((CommandSourceStack)p_235274_.getSource()).m_81377_().m_129890_().m_214331_(p_235275_);
        if ($$2 == null) {
            throw f_120903_.create((Object)p_235275_.toString());
        }
        return $$2;
    }

    public static Collection<CommandFunction> m_120910_(CommandContext<CommandSourceStack> p_120911_, String p_120912_) throws CommandSyntaxException {
        return ((Result)p_120911_.getArgument(p_120912_, Result.class)).m_7588_(p_120911_);
    }

    public static Pair<ResourceLocation, Either<CommandFunction, Collection<CommandFunction>>> m_120920_(CommandContext<CommandSourceStack> p_120921_, String p_120922_) throws CommandSyntaxException {
        return ((Result)p_120921_.getArgument(p_120922_, Result.class)).m_5911_(p_120921_);
    }

    public Collection<String> getExamples() {
        return f_120902_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    public static interface Result {
        public Collection<CommandFunction> m_7588_(CommandContext<CommandSourceStack> var1) throws CommandSyntaxException;

        public Pair<ResourceLocation, Either<CommandFunction, Collection<CommandFunction>>> m_5911_(CommandContext<CommandSourceStack> var1) throws CommandSyntaxException;
    }
}

