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
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;

public class NbtTagArgument
implements ArgumentType<Tag> {
    private static final Collection<String> f_100656_ = Arrays.asList("0", "0b", "0l", "0.0", "\"foo\"", "{foo=bar}", "[0]");

    private NbtTagArgument() {
    }

    public static NbtTagArgument m_100659_() {
        return new NbtTagArgument();
    }

    public static <S> Tag m_100662_(CommandContext<S> p_100663_, String p_100664_) {
        return (Tag)p_100663_.getArgument(p_100664_, Tag.class);
    }

    public Tag parse(StringReader p_100661_) throws CommandSyntaxException {
        return new TagParser(p_100661_).m_129371_();
    }

    public Collection<String> getExamples() {
        return f_100656_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

