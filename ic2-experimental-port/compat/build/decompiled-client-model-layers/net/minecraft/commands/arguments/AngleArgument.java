/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.ImmutableStringReader
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 */
package net.minecraft.commands.arguments;

import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.WorldCoordinate;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class AngleArgument
implements ArgumentType<SingleAngle> {
    private static final Collection<String> f_83804_ = Arrays.asList("0", "~", "~-5");
    public static final SimpleCommandExceptionType f_83803_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.angle.incomplete"));
    public static final SimpleCommandExceptionType f_166217_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.angle.invalid"));

    public static AngleArgument m_83807_() {
        return new AngleArgument();
    }

    public static float m_83810_(CommandContext<CommandSourceStack> p_83811_, String p_83812_) {
        return ((SingleAngle)p_83811_.getArgument(p_83812_, SingleAngle.class)).m_83825_((CommandSourceStack)p_83811_.getSource());
    }

    public SingleAngle parse(StringReader p_83809_) throws CommandSyntaxException {
        float $$2;
        if (!p_83809_.canRead()) {
            throw f_83803_.createWithContext((ImmutableStringReader)p_83809_);
        }
        boolean $$1 = WorldCoordinate.m_120874_(p_83809_);
        float f = $$2 = p_83809_.canRead() && p_83809_.peek() != ' ' ? p_83809_.readFloat() : 0.0f;
        if (Float.isNaN($$2) || Float.isInfinite($$2)) {
            throw f_166217_.createWithContext((ImmutableStringReader)p_83809_);
        }
        return new SingleAngle($$2, $$1);
    }

    public Collection<String> getExamples() {
        return f_83804_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    public static final class SingleAngle {
        private final float f_83816_;
        private final boolean f_83817_;

        SingleAngle(float p_83819_, boolean p_83820_) {
            this.f_83816_ = p_83819_;
            this.f_83817_ = p_83820_;
        }

        public float m_83825_(CommandSourceStack p_83826_) {
            return Mth.m_14177_(this.f_83817_ ? this.f_83816_ + p_83826_.m_81376_().f_82471_ : this.f_83816_);
        }
    }
}

