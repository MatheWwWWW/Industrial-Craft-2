/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  it.unimi.dsi.fastutil.longs.LongSet
 */
package net.minecraft.server.commands;

import com.google.common.base.Joiner;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.ColumnPosArgument;
import net.minecraft.core.SectionPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ColumnPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

public class ForceLoadCommand {
    private static final int f_180227_ = 256;
    private static final Dynamic2CommandExceptionType f_137668_ = new Dynamic2CommandExceptionType((p_137698_, p_137699_) -> Component.m_237110_("commands.forceload.toobig", p_137698_, p_137699_));
    private static final Dynamic2CommandExceptionType f_137669_ = new Dynamic2CommandExceptionType((p_137691_, p_137692_) -> Component.m_237110_("commands.forceload.query.failure", p_137691_, p_137692_));
    private static final SimpleCommandExceptionType f_137670_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.forceload.added.failure"));
    private static final SimpleCommandExceptionType f_137671_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.forceload.removed.failure"));

    public static void m_137676_(CommandDispatcher<CommandSourceStack> p_137677_) {
        p_137677_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("forceload").requires(p_137703_ -> p_137703_.m_6761_(2))).then(Commands.m_82127_("add").then(((RequiredArgumentBuilder)Commands.m_82129_("from", ColumnPosArgument.m_118989_()).executes(p_137711_ -> ForceLoadCommand.m_137685_((CommandSourceStack)p_137711_.getSource(), ColumnPosArgument.m_118992_((CommandContext<CommandSourceStack>)p_137711_, "from"), ColumnPosArgument.m_118992_((CommandContext<CommandSourceStack>)p_137711_, "from"), true))).then(Commands.m_82129_("to", ColumnPosArgument.m_118989_()).executes(p_137709_ -> ForceLoadCommand.m_137685_((CommandSourceStack)p_137709_.getSource(), ColumnPosArgument.m_118992_((CommandContext<CommandSourceStack>)p_137709_, "from"), ColumnPosArgument.m_118992_((CommandContext<CommandSourceStack>)p_137709_, "to"), true)))))).then(((LiteralArgumentBuilder)Commands.m_82127_("remove").then(((RequiredArgumentBuilder)Commands.m_82129_("from", ColumnPosArgument.m_118989_()).executes(p_137707_ -> ForceLoadCommand.m_137685_((CommandSourceStack)p_137707_.getSource(), ColumnPosArgument.m_118992_((CommandContext<CommandSourceStack>)p_137707_, "from"), ColumnPosArgument.m_118992_((CommandContext<CommandSourceStack>)p_137707_, "from"), false))).then(Commands.m_82129_("to", ColumnPosArgument.m_118989_()).executes(p_137705_ -> ForceLoadCommand.m_137685_((CommandSourceStack)p_137705_.getSource(), ColumnPosArgument.m_118992_((CommandContext<CommandSourceStack>)p_137705_, "from"), ColumnPosArgument.m_118992_((CommandContext<CommandSourceStack>)p_137705_, "to"), false))))).then(Commands.m_82127_("all").executes(p_137701_ -> ForceLoadCommand.m_137695_((CommandSourceStack)p_137701_.getSource()))))).then(((LiteralArgumentBuilder)Commands.m_82127_("query").executes(p_137694_ -> ForceLoadCommand.m_137680_((CommandSourceStack)p_137694_.getSource()))).then(Commands.m_82129_("pos", ColumnPosArgument.m_118989_()).executes(p_137679_ -> ForceLoadCommand.m_137682_((CommandSourceStack)p_137679_.getSource(), ColumnPosArgument.m_118992_((CommandContext<CommandSourceStack>)p_137679_, "pos"))))));
    }

    private static int m_137682_(CommandSourceStack p_137683_, ColumnPos p_137684_) throws CommandSyntaxException {
        ChunkPos $$2 = p_137684_.m_143196_();
        ServerLevel $$3 = p_137683_.m_81372_();
        ResourceKey<Level> $$4 = $$3.m_46472_();
        boolean $$5 = $$3.m_8902_().contains($$2.m_45588_());
        if ($$5) {
            p_137683_.m_81354_(Component.m_237110_("commands.forceload.query.success", $$2, $$4.m_135782_()), false);
            return 1;
        }
        throw f_137669_.create((Object)$$2, (Object)$$4.m_135782_());
    }

    private static int m_137680_(CommandSourceStack p_137681_) {
        ServerLevel $$1 = p_137681_.m_81372_();
        ResourceKey<Level> $$2 = $$1.m_46472_();
        LongSet $$3 = $$1.m_8902_();
        int $$4 = $$3.size();
        if ($$4 > 0) {
            String $$5 = Joiner.on((String)", ").join($$3.stream().sorted().map(ChunkPos::new).map(ChunkPos::toString).iterator());
            if ($$4 == 1) {
                p_137681_.m_81354_(Component.m_237110_("commands.forceload.list.single", $$2.m_135782_(), $$5), false);
            } else {
                p_137681_.m_81354_(Component.m_237110_("commands.forceload.list.multiple", $$4, $$2.m_135782_(), $$5), false);
            }
        } else {
            p_137681_.m_81352_(Component.m_237110_("commands.forceload.added.none", $$2.m_135782_()));
        }
        return $$4;
    }

    private static int m_137695_(CommandSourceStack p_137696_) {
        ServerLevel $$1 = p_137696_.m_81372_();
        ResourceKey<Level> $$2 = $$1.m_46472_();
        LongSet $$3 = $$1.m_8902_();
        $$3.forEach(p_137675_ -> $$1.m_8602_(ChunkPos.m_45592_(p_137675_), ChunkPos.m_45602_(p_137675_), false));
        p_137696_.m_81354_(Component.m_237110_("commands.forceload.removed.all", $$2.m_135782_()), true);
        return 0;
    }

    private static int m_137685_(CommandSourceStack p_137686_, ColumnPos p_137687_, ColumnPos p_137688_, boolean p_137689_) throws CommandSyntaxException {
        int $$11;
        int $$4 = Math.min(p_137687_.f_140723_(), p_137688_.f_140723_());
        int $$5 = Math.min(p_137687_.f_140724_(), p_137688_.f_140724_());
        int $$6 = Math.max(p_137687_.f_140723_(), p_137688_.f_140723_());
        int $$7 = Math.max(p_137687_.f_140724_(), p_137688_.f_140724_());
        if ($$4 < -30000000 || $$5 < -30000000 || $$6 >= 30000000 || $$7 >= 30000000) {
            throw BlockPosArgument.f_118235_.create();
        }
        int $$8 = SectionPos.m_123171_($$4);
        int $$9 = SectionPos.m_123171_($$5);
        int $$10 = SectionPos.m_123171_($$6);
        long $$12 = ((long)($$10 - $$8) + 1L) * ((long)(($$11 = SectionPos.m_123171_($$7)) - $$9) + 1L);
        if ($$12 > 256L) {
            throw f_137668_.create((Object)256, (Object)$$12);
        }
        ServerLevel $$13 = p_137686_.m_81372_();
        ResourceKey<Level> $$14 = $$13.m_46472_();
        ChunkPos $$15 = null;
        int $$16 = 0;
        for (int $$17 = $$8; $$17 <= $$10; ++$$17) {
            for (int $$18 = $$9; $$18 <= $$11; ++$$18) {
                boolean $$19 = $$13.m_8602_($$17, $$18, p_137689_);
                if (!$$19) continue;
                ++$$16;
                if ($$15 != null) continue;
                $$15 = new ChunkPos($$17, $$18);
            }
        }
        if ($$16 == 0) {
            throw (p_137689_ ? f_137670_ : f_137671_).create();
        }
        if ($$16 == 1) {
            p_137686_.m_81354_(Component.m_237110_("commands.forceload." + (p_137689_ ? "added" : "removed") + ".single", $$15, $$14.m_135782_()), true);
        } else {
            ChunkPos $$20 = new ChunkPos($$8, $$9);
            ChunkPos $$21 = new ChunkPos($$10, $$11);
            p_137686_.m_81354_(Component.m_237110_("commands.forceload." + (p_137689_ ? "added" : "removed") + ".multiple", $$16, $$14.m_135782_(), $$20, $$21), true);
        }
        return $$16;
    }
}

