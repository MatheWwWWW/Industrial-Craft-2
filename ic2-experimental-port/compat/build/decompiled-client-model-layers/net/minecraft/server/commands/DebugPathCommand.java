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
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.Path;

public class DebugPathCommand {
    private static final SimpleCommandExceptionType f_180118_ = new SimpleCommandExceptionType((Message)Component.m_237113_("Source is not a mob"));
    private static final SimpleCommandExceptionType f_180119_ = new SimpleCommandExceptionType((Message)Component.m_237113_("Path not found"));
    private static final SimpleCommandExceptionType f_180120_ = new SimpleCommandExceptionType((Message)Component.m_237113_("Target not reached"));

    public static void m_180123_(CommandDispatcher<CommandSourceStack> p_180124_) {
        p_180124_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("debugpath").requires(p_180128_ -> p_180128_.m_6761_(2))).then(Commands.m_82129_("to", BlockPosArgument.m_118239_()).executes(p_180126_ -> DebugPathCommand.m_180129_((CommandSourceStack)p_180126_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_180126_, "to")))));
    }

    private static int m_180129_(CommandSourceStack p_180130_, BlockPos p_180131_) throws CommandSyntaxException {
        Entity $$2 = p_180130_.m_81373_();
        if (!($$2 instanceof Mob)) {
            throw f_180118_.create();
        }
        Mob $$3 = (Mob)$$2;
        GroundPathNavigation $$4 = new GroundPathNavigation($$3, p_180130_.m_81372_());
        Path $$5 = ((PathNavigation)$$4).m_7864_(p_180131_, 0);
        DebugPackets.m_133703_(p_180130_.m_81372_(), $$3, $$5, $$4.m_148228_());
        if ($$5 == null) {
            throw f_180119_.create();
        }
        if (!$$5.m_77403_()) {
            throw f_180120_.create();
        }
        p_180130_.m_81354_(Component.m_237113_("Made path"), true);
        return 1;
    }
}

