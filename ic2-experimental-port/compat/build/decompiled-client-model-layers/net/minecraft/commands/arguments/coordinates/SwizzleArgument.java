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
 */
package net.minecraft.commands.arguments.coordinates;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;

public class SwizzleArgument
implements ArgumentType<EnumSet<Direction.Axis>> {
    private static final Collection<String> f_120803_ = Arrays.asList("xyz", "x");
    private static final SimpleCommandExceptionType f_120804_ = new SimpleCommandExceptionType((Message)Component.m_237115_("arguments.swizzle.invalid"));

    public static SwizzleArgument m_120807_() {
        return new SwizzleArgument();
    }

    public static EnumSet<Direction.Axis> m_120810_(CommandContext<CommandSourceStack> p_120811_, String p_120812_) {
        return (EnumSet)p_120811_.getArgument(p_120812_, EnumSet.class);
    }

    /*
     * WARNING - void declaration
     */
    public EnumSet<Direction.Axis> parse(StringReader p_120809_) throws CommandSyntaxException {
        EnumSet<Direction.Axis> $$1 = EnumSet.noneOf(Direction.Axis.class);
        while (p_120809_.canRead() && p_120809_.peek() != ' ') {
            void $$6;
            char $$2 = p_120809_.read();
            switch ($$2) {
                case 'x': {
                    Direction.Axis $$3 = Direction.Axis.X;
                    break;
                }
                case 'y': {
                    Direction.Axis $$4 = Direction.Axis.Y;
                    break;
                }
                case 'z': {
                    Direction.Axis $$5 = Direction.Axis.Z;
                    break;
                }
                default: {
                    throw f_120804_.create();
                }
            }
            if ($$1.contains($$6)) {
                throw f_120804_.create();
            }
            $$1.add((Direction.Axis)$$6);
        }
        return $$1;
    }

    public Collection<String> getExamples() {
        return f_120803_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

