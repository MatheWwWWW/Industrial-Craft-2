/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.tree.CommandNode
 *  com.mojang.brigadier.tree.LiteralCommandNode
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.OutgoingPlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.Entity;

public class MsgCommand {
    public static void m_138060_(CommandDispatcher<CommandSourceStack> p_138061_) {
        LiteralCommandNode $$1 = p_138061_.register((LiteralArgumentBuilder)Commands.m_82127_("msg").then(Commands.m_82129_("targets", EntityArgument.m_91470_()).then(Commands.m_82129_("message", MessageArgument.m_96832_()).executes(p_138063_ -> {
            MessageArgument.ChatMessage $$1 = MessageArgument.m_232163_((CommandContext<CommandSourceStack>)p_138063_, "message");
            try {
                return MsgCommand.m_214522_((CommandSourceStack)p_138063_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_138063_, "targets"), $$1);
            }
            catch (Exception $$2) {
                $$1.m_241074_((CommandSourceStack)p_138063_.getSource());
                throw $$2;
            }
        }))));
        p_138061_.register((LiteralArgumentBuilder)Commands.m_82127_("tell").redirect((CommandNode)$$1));
        p_138061_.register((LiteralArgumentBuilder)Commands.m_82127_("w").redirect((CommandNode)$$1));
    }

    private static int m_214522_(CommandSourceStack p_214523_, Collection<ServerPlayer> p_214524_, MessageArgument.ChatMessage p_214525_) {
        ChatType.Bound $$3 = ChatType.m_241073_(ChatType.f_240674_, p_214523_);
        p_214525_.m_241987_(p_214523_, p_243178_ -> {
            OutgoingPlayerChatMessage $$4 = OutgoingPlayerChatMessage.m_242676_(p_243178_);
            boolean $$5 = p_243178_.m_243059_();
            Entity $$6 = p_214523_.m_81373_();
            boolean $$7 = false;
            for (ServerPlayer $$8 : p_214524_) {
                ChatType.Bound $$9 = ChatType.m_241073_(ChatType.f_240668_, p_214523_).m_241018_($$8.m_5446_());
                p_214523_.m_243079_($$4, false, $$9);
                boolean $$10 = p_214523_.m_243061_($$8);
                $$8.m_243093_($$4, $$10, $$3);
                $$7 |= $$5 && $$10 && $$8 != $$6;
            }
            if ($$7) {
                p_214523_.m_243053_(PlayerList.f_243017_);
            }
            $$4.m_241051_(p_214523_.m_81377_().m_6846_());
        });
        return p_214524_.size();
    }
}

