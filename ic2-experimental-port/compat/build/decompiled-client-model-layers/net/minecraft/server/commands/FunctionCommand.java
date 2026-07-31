/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.suggestion.SuggestionProvider
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import net.minecraft.commands.CommandFunction;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.item.FunctionArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.ServerFunctionManager;

public class FunctionCommand {
    public static final SuggestionProvider<CommandSourceStack> f_137712_ = (p_137719_, p_137720_) -> {
        ServerFunctionManager $$2 = ((CommandSourceStack)p_137719_.getSource()).m_81377_().m_129890_();
        SharedSuggestionProvider.m_82929_($$2.m_136131_(), p_137720_, "#");
        return SharedSuggestionProvider.m_82926_($$2.m_136130_(), p_137720_);
    };

    public static void m_137714_(CommandDispatcher<CommandSourceStack> p_137715_) {
        p_137715_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("function").requires(p_137722_ -> p_137722_.m_6761_(2))).then(Commands.m_82129_("name", FunctionArgument.m_120907_()).suggests(f_137712_).executes(p_137717_ -> FunctionCommand.m_137723_((CommandSourceStack)p_137717_.getSource(), FunctionArgument.m_120910_((CommandContext<CommandSourceStack>)p_137717_, "name")))));
    }

    private static int m_137723_(CommandSourceStack p_137724_, Collection<CommandFunction> p_137725_) {
        int $$2 = 0;
        for (CommandFunction $$3 : p_137725_) {
            $$2 += p_137724_.m_81377_().m_129890_().m_136112_($$3, p_137724_.m_81324_().m_81358_(2));
        }
        if (p_137725_.size() == 1) {
            p_137724_.m_81354_(Component.m_237110_("commands.function.success.single", $$2, p_137725_.iterator().next().m_77981_()), true);
        } else {
            p_137724_.m_81354_(Component.m_237110_("commands.function.success.multiple", $$2, p_137725_.size()), true);
        }
        return $$2;
    }
}

