/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.tree.CommandNode
 *  com.mojang.brigadier.tree.LiteralCommandNode
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.OutgoingPlayerChatMessage;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.PlayerTeam;

public class TeamMsgCommand {
    private static final Style f_138996_ = Style.f_131099_.m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, Component.m_237115_("chat.type.team.hover"))).m_131142_(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/teammsg "));
    private static final SimpleCommandExceptionType f_138997_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.teammsg.failed.noteam"));

    public static void m_138999_(CommandDispatcher<CommandSourceStack> p_139000_) {
        LiteralCommandNode $$1 = p_139000_.register((LiteralArgumentBuilder)Commands.m_82127_("teammsg").then(Commands.m_82129_("message", MessageArgument.m_96832_()).executes(p_139002_ -> {
            MessageArgument.ChatMessage $$1 = MessageArgument.m_232163_((CommandContext<CommandSourceStack>)p_139002_, "message");
            try {
                return TeamMsgCommand.m_214762_((CommandSourceStack)p_139002_.getSource(), $$1);
            }
            catch (Exception $$2) {
                $$1.m_241074_((CommandSourceStack)p_139002_.getSource());
                throw $$2;
            }
        })));
        p_139000_.register((LiteralArgumentBuilder)Commands.m_82127_("tm").redirect((CommandNode)$$1));
    }

    private static int m_214762_(CommandSourceStack p_214763_, MessageArgument.ChatMessage p_214764_) throws CommandSyntaxException {
        Entity $$2 = p_214763_.m_81374_();
        PlayerTeam $$3 = (PlayerTeam)$$2.m_5647_();
        if ($$3 == null) {
            throw f_138997_.create();
        }
        MutableComponent $$4 = $$3.m_83367_().m_130948_(f_138996_);
        ChatType.Bound $$5 = ChatType.m_241073_(ChatType.f_241694_, p_214763_).m_241018_($$4);
        ChatType.Bound $$6 = ChatType.m_241073_(ChatType.f_241626_, p_214763_).m_241018_($$4);
        List<ServerPlayer> $$7 = p_214763_.m_81377_().m_6846_().m_11314_().stream().filter(p_242725_ -> p_242725_ == $$2 || p_242725_.m_5647_() == $$3).toList();
        p_214764_.m_241987_(p_214763_, p_243187_ -> {
            OutgoingPlayerChatMessage $$6 = OutgoingPlayerChatMessage.m_242676_(p_243187_);
            boolean $$7 = p_243187_.m_243059_();
            boolean $$8 = false;
            for (ServerPlayer $$9 : $$7) {
                ChatType.Bound $$10 = $$9 == $$2 ? $$6 : $$5;
                boolean $$11 = p_214763_.m_243061_($$9);
                $$9.m_243093_($$6, $$11, $$10);
                $$8 |= $$7 && $$11 && $$9 != $$2;
            }
            if ($$8) {
                p_214763_.m_243053_(PlayerList.f_243017_);
            }
            $$6.m_241051_(p_214763_.m_81377_().m_6846_());
        });
        return $$7.size();
    }
}

