/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 */
package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.commands.CommandSourceStack;

public interface RangeArgument<T extends MinMaxBounds<?>>
extends ArgumentType<T> {
    public static Ints m_105404_() {
        return new Ints();
    }

    public static Floats m_105405_() {
        return new Floats();
    }

    public static class Ints
    implements RangeArgument<MinMaxBounds.Ints> {
        private static final Collection<String> f_105414_ = Arrays.asList("0..5", "0", "-5", "-100..", "..100");

        public static MinMaxBounds.Ints m_105419_(CommandContext<CommandSourceStack> p_105420_, String p_105421_) {
            return (MinMaxBounds.Ints)p_105420_.getArgument(p_105421_, MinMaxBounds.Ints.class);
        }

        public MinMaxBounds.Ints parse(StringReader p_105418_) throws CommandSyntaxException {
            return MinMaxBounds.Ints.m_55375_(p_105418_);
        }

        public Collection<String> getExamples() {
            return f_105414_;
        }

        public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
            return this.parse(stringReader);
        }
    }

    public static class Floats
    implements RangeArgument<MinMaxBounds.Doubles> {
        private static final Collection<String> f_105406_ = Arrays.asList("0..5.2", "0", "-5.4", "-100.76..", "..100");

        public static MinMaxBounds.Doubles m_170804_(CommandContext<CommandSourceStack> p_170805_, String p_170806_) {
            return (MinMaxBounds.Doubles)p_170805_.getArgument(p_170806_, MinMaxBounds.Doubles.class);
        }

        public MinMaxBounds.Doubles parse(StringReader p_170803_) throws CommandSyntaxException {
            return MinMaxBounds.Doubles.m_154793_(p_170803_);
        }

        public Collection<String> getExamples() {
            return f_105406_;
        }

        public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
            return this.parse(stringReader);
        }
    }
}

