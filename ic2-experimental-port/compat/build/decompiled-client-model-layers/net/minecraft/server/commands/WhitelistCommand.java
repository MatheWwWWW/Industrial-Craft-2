/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 */
package net.minecraft.server.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.UserWhiteList;
import net.minecraft.server.players.UserWhiteListEntry;

public class WhitelistCommand {
    private static final SimpleCommandExceptionType f_139191_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.whitelist.alreadyOn"));
    private static final SimpleCommandExceptionType f_139192_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.whitelist.alreadyOff"));
    private static final SimpleCommandExceptionType f_139193_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.whitelist.add.failed"));
    private static final SimpleCommandExceptionType f_139194_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.whitelist.remove.failed"));

    public static void m_139201_(CommandDispatcher<CommandSourceStack> p_139202_) {
        p_139202_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("whitelist").requires(p_139234_ -> p_139234_.m_6761_(3))).then(Commands.m_82127_("on").executes(p_139236_ -> WhitelistCommand.m_139218_((CommandSourceStack)p_139236_.getSource())))).then(Commands.m_82127_("off").executes(p_139232_ -> WhitelistCommand.m_139225_((CommandSourceStack)p_139232_.getSource())))).then(Commands.m_82127_("list").executes(p_139228_ -> WhitelistCommand.m_139229_((CommandSourceStack)p_139228_.getSource())))).then(Commands.m_82127_("add").then(Commands.m_82129_("targets", GameProfileArgument.m_94584_()).suggests((p_139216_, p_139217_) -> {
            PlayerList $$2 = ((CommandSourceStack)p_139216_.getSource()).m_81377_().m_6846_();
            return SharedSuggestionProvider.m_82981_($$2.m_11314_().stream().filter(p_142794_ -> !$$2.m_11305_().m_11453_(p_142794_.m_36316_())).map(p_142791_ -> p_142791_.m_36316_().getName()), p_139217_);
        }).executes(p_139224_ -> WhitelistCommand.m_139210_((CommandSourceStack)p_139224_.getSource(), GameProfileArgument.m_94590_((CommandContext<CommandSourceStack>)p_139224_, "targets")))))).then(Commands.m_82127_("remove").then(Commands.m_82129_("targets", GameProfileArgument.m_94584_()).suggests((p_139206_, p_139207_) -> SharedSuggestionProvider.m_82967_(((CommandSourceStack)p_139206_.getSource()).m_81377_().m_6846_().m_11306_(), p_139207_)).executes(p_139214_ -> WhitelistCommand.m_139220_((CommandSourceStack)p_139214_.getSource(), GameProfileArgument.m_94590_((CommandContext<CommandSourceStack>)p_139214_, "targets")))))).then(Commands.m_82127_("reload").executes(p_139204_ -> WhitelistCommand.m_139208_((CommandSourceStack)p_139204_.getSource()))));
    }

    private static int m_139208_(CommandSourceStack p_139209_) {
        p_139209_.m_81377_().m_6846_().m_7542_();
        p_139209_.m_81354_(Component.m_237115_("commands.whitelist.reloaded"), true);
        p_139209_.m_81377_().m_129849_(p_139209_);
        return 1;
    }

    private static int m_139210_(CommandSourceStack p_139211_, Collection<GameProfile> p_139212_) throws CommandSyntaxException {
        UserWhiteList $$2 = p_139211_.m_81377_().m_6846_().m_11305_();
        int $$3 = 0;
        for (GameProfile $$4 : p_139212_) {
            if ($$2.m_11453_($$4)) continue;
            UserWhiteListEntry $$5 = new UserWhiteListEntry($$4);
            $$2.m_11381_($$5);
            p_139211_.m_81354_(Component.m_237110_("commands.whitelist.add.success", ComponentUtils.m_130727_($$4)), true);
            ++$$3;
        }
        if ($$3 == 0) {
            throw f_139193_.create();
        }
        return $$3;
    }

    private static int m_139220_(CommandSourceStack p_139221_, Collection<GameProfile> p_139222_) throws CommandSyntaxException {
        UserWhiteList $$2 = p_139221_.m_81377_().m_6846_().m_11305_();
        int $$3 = 0;
        for (GameProfile $$4 : p_139222_) {
            if (!$$2.m_11453_($$4)) continue;
            UserWhiteListEntry $$5 = new UserWhiteListEntry($$4);
            $$2.m_11386_($$5);
            p_139221_.m_81354_(Component.m_237110_("commands.whitelist.remove.success", ComponentUtils.m_130727_($$4)), true);
            ++$$3;
        }
        if ($$3 == 0) {
            throw f_139194_.create();
        }
        p_139221_.m_81377_().m_129849_(p_139221_);
        return $$3;
    }

    private static int m_139218_(CommandSourceStack p_139219_) throws CommandSyntaxException {
        PlayerList $$1 = p_139219_.m_81377_().m_6846_();
        if ($$1.m_11311_()) {
            throw f_139191_.create();
        }
        $$1.m_6628_(true);
        p_139219_.m_81354_(Component.m_237115_("commands.whitelist.enabled"), true);
        p_139219_.m_81377_().m_129849_(p_139219_);
        return 1;
    }

    private static int m_139225_(CommandSourceStack p_139226_) throws CommandSyntaxException {
        PlayerList $$1 = p_139226_.m_81377_().m_6846_();
        if (!$$1.m_11311_()) {
            throw f_139192_.create();
        }
        $$1.m_6628_(false);
        p_139226_.m_81354_(Component.m_237115_("commands.whitelist.disabled"), true);
        return 1;
    }

    private static int m_139229_(CommandSourceStack p_139230_) {
        CharSequence[] $$1 = p_139230_.m_81377_().m_6846_().m_11306_();
        if ($$1.length == 0) {
            p_139230_.m_81354_(Component.m_237115_("commands.whitelist.none"), false);
        } else {
            p_139230_.m_81354_(Component.m_237110_("commands.whitelist.list", $$1.length, String.join((CharSequence)", ", $$1)), false);
        }
        return $$1.length;
    }
}

