/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.GameRules;

public class GameRuleCommand {
    public static void m_137744_(CommandDispatcher<CommandSourceStack> p_137745_) {
        final LiteralArgumentBuilder $$1 = (LiteralArgumentBuilder)Commands.m_82127_("gamerule").requires(p_137750_ -> p_137750_.m_6761_(2));
        GameRules.m_46164_(new GameRules.GameRuleTypeVisitor(){

            @Override
            public <T extends GameRules.Value<T>> void m_6889_(GameRules.Key<T> p_137764_, GameRules.Type<T> p_137765_) {
                $$1.then(((LiteralArgumentBuilder)Commands.m_82127_(p_137764_.m_46328_()).executes(p_137771_ -> GameRuleCommand.m_137757_((CommandSourceStack)p_137771_.getSource(), p_137764_))).then(p_137765_.m_46358_("value").executes(p_137768_ -> GameRuleCommand.m_137754_((CommandContext<CommandSourceStack>)p_137768_, p_137764_))));
            }
        });
        p_137745_.register($$1);
    }

    static <T extends GameRules.Value<T>> int m_137754_(CommandContext<CommandSourceStack> p_137755_, GameRules.Key<T> p_137756_) {
        CommandSourceStack $$2 = (CommandSourceStack)p_137755_.getSource();
        T $$3 = $$2.m_81377_().m_129900_().m_46170_(p_137756_);
        ((GameRules.Value)$$3).m_46370_(p_137755_, "value");
        $$2.m_81354_(Component.m_237110_("commands.gamerule.set", p_137756_.m_46328_(), ((GameRules.Value)$$3).toString()), true);
        return ((GameRules.Value)$$3).m_6855_();
    }

    static <T extends GameRules.Value<T>> int m_137757_(CommandSourceStack p_137758_, GameRules.Key<T> p_137759_) {
        T $$2 = p_137758_.m_81377_().m_129900_().m_46170_(p_137759_);
        p_137758_.m_81354_(Component.m_237110_("commands.gamerule.query", p_137759_.m_46328_(), ((GameRules.Value)$$2).toString()), false);
        return ((GameRules.Value)$$2).m_6855_();
    }
}

