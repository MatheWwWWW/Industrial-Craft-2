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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;

public class CompoundTagArgument
implements ArgumentType<CompoundTag> {
    private static final Collection<String> f_87654_ = Arrays.asList("{}", "{foo=bar}");

    private CompoundTagArgument() {
    }

    public static CompoundTagArgument m_87657_() {
        return new CompoundTagArgument();
    }

    public static <S> CompoundTag m_87660_(CommandContext<S> p_87661_, String p_87662_) {
        return (CompoundTag)p_87661_.getArgument(p_87662_, CompoundTag.class);
    }

    public CompoundTag parse(StringReader p_87659_) throws CommandSyntaxException {
        return new TagParser(p_87659_).m_129373_();
    }

    public Collection<String> getExamples() {
        return f_87654_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

