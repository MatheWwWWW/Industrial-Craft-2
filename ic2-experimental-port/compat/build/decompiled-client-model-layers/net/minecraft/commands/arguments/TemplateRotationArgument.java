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
import net.minecraft.world.level.block.Rotation;

public class TemplateRotationArgument
extends StringRepresentableArgument<Rotation> {
    private TemplateRotationArgument() {
        super(Rotation.f_221983_, Rotation::values);
    }

    public static TemplateRotationArgument m_234414_() {
        return new TemplateRotationArgument();
    }

    public static Rotation m_234415_(CommandContext<CommandSourceStack> p_234416_, String p_234417_) {
        return (Rotation)p_234416_.getArgument(p_234417_, Rotation.class);
    }
}

