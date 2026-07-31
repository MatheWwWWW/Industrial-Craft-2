/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Difficulty;

public class DifficultyCommand {
    private static final DynamicCommandExceptionType f_136933_ = new DynamicCommandExceptionType(p_136948_ -> Component.m_237110_("commands.difficulty.failure", p_136948_));

    public static void m_136938_(CommandDispatcher<CommandSourceStack> p_136939_) {
        LiteralArgumentBuilder<CommandSourceStack> $$1 = Commands.m_82127_("difficulty");
        for (Difficulty $$2 : Difficulty.values()) {
            $$1.then(Commands.m_82127_($$2.m_19036_()).executes(p_136937_ -> DifficultyCommand.m_136944_((CommandSourceStack)p_136937_.getSource(), $$2)));
        }
        p_136939_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)$$1.requires(p_136943_ -> p_136943_.m_6761_(2))).executes(p_136941_ -> {
            Difficulty $$1 = ((CommandSourceStack)p_136941_.getSource()).m_81372_().m_46791_();
            ((CommandSourceStack)p_136941_.getSource()).m_81354_(Component.m_237110_("commands.difficulty.query", $$1.m_19033_()), false);
            return $$1.m_19028_();
        }));
    }

    public static int m_136944_(CommandSourceStack p_136945_, Difficulty p_136946_) throws CommandSyntaxException {
        MinecraftServer $$2 = p_136945_.m_81377_();
        if ($$2.m_129910_().m_5472_() == p_136946_) {
            throw f_136933_.create((Object)p_136946_.m_19036_());
        }
        $$2.m_129827_(p_136946_, true);
        p_136945_.m_81354_(Component.m_237110_("commands.difficulty.success", p_136946_.m_19033_()), true);
        return 0;
    }
}

