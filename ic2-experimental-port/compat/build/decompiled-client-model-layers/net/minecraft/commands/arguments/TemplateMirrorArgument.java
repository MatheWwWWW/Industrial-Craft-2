/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.context.CommandContext
 */
package net.minecraft.commands.arguments;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.StringRepresentableArgument;
import net.minecraft.world.level.block.Mirror;

public class TemplateMirrorArgument
extends StringRepresentableArgument<Mirror> {
    private TemplateMirrorArgument() {
        super(Mirror.f_221524_, Mirror::values);
    }

    public static StringRepresentableArgument<Mirror> m_234343_() {
        return new TemplateMirrorArgument();
    }

    public static Mirror m_234344_(CommandContext<CommandSourceStack> p_234345_, String p_234346_) {
        return (Mirror)p_234345_.getArgument(p_234346_, Mirror.class);
    }
}

