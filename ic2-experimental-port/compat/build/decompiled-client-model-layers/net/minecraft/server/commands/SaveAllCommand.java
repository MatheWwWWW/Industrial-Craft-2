/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;

public class SaveAllCommand {
    private static final SimpleCommandExceptionType f_138269_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.save.failed"));

    public static void m_138271_(CommandDispatcher<CommandSourceStack> p_138272_) {
        p_138272_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("save-all").requires(p_138276_ -> p_138276_.m_6761_(4))).executes(p_138281_ -> SaveAllCommand.m_138277_((CommandSourceStack)p_138281_.getSource(), false))).then(Commands.m_82127_("flush").executes(p_138274_ -> SaveAllCommand.m_138277_((CommandSourceStack)p_138274_.getSource(), true))));
    }

    private static int m_138277_(CommandSourceStack p_138278_, boolean p_138279_) throws CommandSyntaxException {
        p_138278_.m_81354_(Component.m_237115_("commands.save.saving"), false);
        MinecraftServer $$2 = p_138278_.m_81377_();
        boolean $$3 = $$2.m_195514_(true, p_138279_, true);
        if (!$$3) {
            throw f_138269_.create();
        }
        p_138278_.m_81354_(Component.m_237115_("commands.save.success"), true);
        return 1;
    }
}

