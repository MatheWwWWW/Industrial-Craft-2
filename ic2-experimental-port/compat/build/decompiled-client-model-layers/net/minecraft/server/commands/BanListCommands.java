/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 */
package net.minecraft.server.commands;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.players.BanListEntry;
import net.minecraft.server.players.PlayerList;

public class BanListCommands {
    public static void m_136543_(CommandDispatcher<CommandSourceStack> p_136544_) {
        p_136544_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("banlist").requires(p_136548_ -> p_136548_.m_6761_(3))).executes(p_136555_ -> {
            PlayerList $$1 = ((CommandSourceStack)p_136555_.getSource()).m_81377_().m_6846_();
            return BanListCommands.m_136549_((CommandSourceStack)p_136555_.getSource(), Lists.newArrayList((Iterable)Iterables.concat($$1.m_11295_().m_11395_(), $$1.m_11299_().m_11395_())));
        })).then(Commands.m_82127_("ips").executes(p_136553_ -> BanListCommands.m_136549_((CommandSourceStack)p_136553_.getSource(), ((CommandSourceStack)p_136553_.getSource()).m_81377_().m_6846_().m_11299_().m_11395_())))).then(Commands.m_82127_("players").executes(p_136546_ -> BanListCommands.m_136549_((CommandSourceStack)p_136546_.getSource(), ((CommandSourceStack)p_136546_.getSource()).m_81377_().m_6846_().m_11295_().m_11395_()))));
    }

    private static int m_136549_(CommandSourceStack p_136550_, Collection<? extends BanListEntry<?>> p_136551_) {
        if (p_136551_.isEmpty()) {
            p_136550_.m_81354_(Component.m_237115_("commands.banlist.none"), false);
        } else {
            p_136550_.m_81354_(Component.m_237110_("commands.banlist.list", p_136551_.size()), false);
            for (BanListEntry<?> $$2 : p_136551_) {
                p_136550_.m_81354_(Component.m_237110_("commands.banlist.entry", $$2.m_8003_(), $$2.m_10960_(), $$2.m_10962_()), false);
            }
        }
        return p_136551_.size();
    }
}

