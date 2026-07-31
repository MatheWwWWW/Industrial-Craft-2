/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 */
package net.minecraft.commands;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.chat.Component;

public class CommandRuntimeException
extends RuntimeException {
    private final Component f_79223_;

    public CommandRuntimeException(Component p_79225_) {
        super(p_79225_.getString(), null, CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES, CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES);
        this.f_79223_ = p_79225_;
    }

    public Component m_79226_() {
        return this.f_79223_;
    }
}

