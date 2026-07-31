/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;

public class SeedCommand {
    public static void m_138589_(CommandDispatcher<CommandSourceStack> p_138590_, boolean p_138591_) {
        p_138590_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("seed").requires(p_138596_ -> !p_138591_ || p_138596_.m_6761_(2))).executes(p_138593_ -> {
            long $$1 = ((CommandSourceStack)p_138593_.getSource()).m_81372_().m_7328_();
            MutableComponent $$2 = ComponentUtils.m_130748_(Component.m_237113_(String.valueOf($$1)).m_130938_(p_180514_ -> p_180514_.m_131140_(ChatFormatting.GREEN).m_131142_(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, String.valueOf($$1))).m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, Component.m_237115_("chat.copy.click"))).m_131138_(String.valueOf($$1))));
            ((CommandSourceStack)p_138593_.getSource()).m_81354_(Component.m_237110_("commands.seed.success", $$2), false);
            return (int)$$1;
        }));
    }
}

