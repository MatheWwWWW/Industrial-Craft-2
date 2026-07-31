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
package net.minecraft.commands.arguments.coordinates;

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
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.commands.arguments.coordinates.WorldCoordinate;
import net.minecraft.commands.arguments.coordinates.WorldCoordinates;
import net.minecraft.network.chat.Component;

public class RotationArgument
implements ArgumentType<Coordinates> {
    private static final Collection<String> f_120476_ = Arrays.asList("0 0", "~ ~", "~-5 ~5");
    public static final SimpleCommandExceptionType f_120475_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.rotation.incomplete"));

    public static RotationArgument m_120479_() {
        return new RotationArgument();
    }

    public static Coordinates m_120482_(CommandContext<CommandSourceStack> p_120483_, String p_120484_) {
        return (Coordinates)p_120483_.getArgument(p_120484_, Coordinates.class);
    }

    public Coordinates parse(StringReader p_120481_) throws CommandSyntaxException {
        int $$1 = p_120481_.getCursor();
        if (!p_120481_.canRead()) {
            throw f_120475_.createWithContext((ImmutableStringReader)p_120481_);
        }
        WorldCoordinate $$2 = WorldCoordinate.m_120871_(p_120481_, false);
        if (!p_120481_.canRead() || p_120481_.peek() != ' ') {
            p_120481_.setCursor($$1);
            throw f_120475_.createWithContext((ImmutableStringReader)p_120481_);
        }
        p_120481_.skip();
        WorldCoordinate $$3 = WorldCoordinate.m_120871_(p_120481_, false);
        return new WorldCoordinates($$3, $$2, new WorldCoordinate(true, 0.0));
    }

    public Collection<String> getExamples() {
        return f_120476_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

