/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.BoolArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.datafixers.util.Unit
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.server.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.datafixers.util.Unit;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.ImposterProtoChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

public class ResetChunksCommand {
    private static final Logger f_183662_ = LogUtils.getLogger();

    public static void m_183666_(CommandDispatcher<CommandSourceStack> p_183667_) {
        p_183667_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("resetchunks").requires(p_183683_ -> p_183683_.m_6761_(2))).executes(p_183693_ -> ResetChunksCommand.m_183684_((CommandSourceStack)p_183693_.getSource(), 0, true))).then(((RequiredArgumentBuilder)Commands.m_82129_("range", IntegerArgumentType.integer((int)0, (int)5)).executes(p_183689_ -> ResetChunksCommand.m_183684_((CommandSourceStack)p_183689_.getSource(), IntegerArgumentType.getInteger((CommandContext)p_183689_, (String)"range"), true))).then(Commands.m_82129_("skipOldChunks", BoolArgumentType.bool()).executes(p_183669_ -> ResetChunksCommand.m_183684_((CommandSourceStack)p_183669_.getSource(), IntegerArgumentType.getInteger((CommandContext)p_183669_, (String)"range"), BoolArgumentType.getBool((CommandContext)p_183669_, (String)"skipOldChunks"))))));
    }

    private static int m_183684_(CommandSourceStack p_183685_, int p_183686_, boolean p_183687_) {
        ServerLevel $$3 = p_183685_.m_81372_();
        ServerChunkCache $$4 = $$3.m_7726_();
        $$4.f_8325_.m_183825_();
        Vec3 $$5 = p_183685_.m_81371_();
        ChunkPos $$6 = new ChunkPos(new BlockPos($$5));
        int $$7 = $$6.f_45579_ - p_183686_;
        int $$8 = $$6.f_45579_ + p_183686_;
        int $$9 = $$6.f_45578_ - p_183686_;
        int $$10 = $$6.f_45578_ + p_183686_;
        for (int $$11 = $$7; $$11 <= $$8; ++$$11) {
            for (int $$12 = $$9; $$12 <= $$10; ++$$12) {
                ChunkPos $$13 = new ChunkPos($$12, $$11);
                LevelChunk $$14 = $$4.m_62227_($$12, $$11, false);
                if ($$14 == null || p_183687_ && $$14.m_187675_()) continue;
                for (BlockPos $$15 : BlockPos.m_121976_($$13.m_45604_(), $$3.m_141937_(), $$13.m_45605_(), $$13.m_45608_(), $$3.m_151558_() - 1, $$13.m_45609_())) {
                    $$3.m_7731_($$15, Blocks.f_50016_.m_49966_(), 16);
                }
            }
        }
        ProcessorMailbox<Runnable> $$16 = ProcessorMailbox.m_18751_(Util.m_183991_(), "worldgen-resetchunks");
        long $$17 = System.currentTimeMillis();
        int $$18 = (p_183686_ * 2 + 1) * (p_183686_ * 2 + 1);
        for (ChunkStatus $$19 : ImmutableList.of((Object)ChunkStatus.f_62317_, (Object)ChunkStatus.f_62318_, (Object)ChunkStatus.f_62319_, (Object)ChunkStatus.f_62320_, (Object)ChunkStatus.f_62321_, (Object)ChunkStatus.f_62322_)) {
            long $$20 = System.currentTimeMillis();
            CompletionStage<Object> $$21 = CompletableFuture.supplyAsync(() -> Unit.INSTANCE, $$16::m_6937_);
            for (int $$22 = $$6.f_45579_ - p_183686_; $$22 <= $$6.f_45579_ + p_183686_; ++$$22) {
                for (int $$23 = $$6.f_45578_ - p_183686_; $$23 <= $$6.f_45578_ + p_183686_; ++$$23) {
                    ChunkPos $$24 = new ChunkPos($$23, $$22);
                    LevelChunk $$25 = $$4.m_62227_($$23, $$22, false);
                    if ($$25 == null || p_183687_ && $$25.m_187675_()) continue;
                    ArrayList $$26 = Lists.newArrayList();
                    int $$27 = Math.max(1, $$19.m_62488_());
                    for (int $$28 = $$24.f_45579_ - $$27; $$28 <= $$24.f_45579_ + $$27; ++$$28) {
                        for (int $$29 = $$24.f_45578_ - $$27; $$29 <= $$24.f_45578_ + $$27; ++$$29) {
                            ChunkAccess $$33;
                            ChunkAccess $$30 = $$4.m_7587_($$29, $$28, $$19.m_62482_(), true);
                            if ($$30 instanceof ImposterProtoChunk) {
                                ImposterProtoChunk $$31 = new ImposterProtoChunk(((ImposterProtoChunk)$$30).m_62768_(), true);
                            } else if ($$30 instanceof LevelChunk) {
                                ImposterProtoChunk $$32 = new ImposterProtoChunk((LevelChunk)$$30, true);
                            } else {
                                $$33 = $$30;
                            }
                            $$26.add($$33);
                        }
                    }
                    $$21 = $$21.thenComposeAsync(p_183678_ -> $$19.m_223279_($$16::m_6937_, $$3, $$4.m_8481_(), $$3.m_215082_(), $$4.m_7827_(), p_183691_ -> {
                        throw new UnsupportedOperationException("Not creating full chunks here");
                    }, $$26, true).thenApply(p_183681_ -> {
                        if ($$19 == ChunkStatus.f_62318_) {
                            p_183681_.left().ifPresent(p_183671_ -> Heightmap.m_64256_(p_183671_, ChunkStatus.f_62328_));
                        }
                        return Unit.INSTANCE;
                    }), $$16::m_6937_);
                }
            }
            p_183685_.m_81377_().m_18701_(() -> $$21.isDone());
            f_183662_.debug($$19.m_62467_() + " took " + (System.currentTimeMillis() - $$20) + " ms");
        }
        long $$34 = System.currentTimeMillis();
        for (int $$35 = $$6.f_45579_ - p_183686_; $$35 <= $$6.f_45579_ + p_183686_; ++$$35) {
            for (int $$36 = $$6.f_45578_ - p_183686_; $$36 <= $$6.f_45578_ + p_183686_; ++$$36) {
                ChunkPos $$37 = new ChunkPos($$36, $$35);
                LevelChunk $$38 = $$4.m_62227_($$36, $$35, false);
                if ($$38 == null || p_183687_ && $$38.m_187675_()) continue;
                for (BlockPos $$39 : BlockPos.m_121976_($$37.m_45604_(), $$3.m_141937_(), $$37.m_45605_(), $$37.m_45608_(), $$3.m_151558_() - 1, $$37.m_45609_())) {
                    $$4.m_8450_($$39);
                }
            }
        }
        f_183662_.debug("blockChanged took " + (System.currentTimeMillis() - $$34) + " ms");
        long $$40 = System.currentTimeMillis() - $$17;
        p_183685_.m_81354_(Component.m_237113_(String.format(Locale.ROOT, "%d chunks have been reset. This took %d ms for %d chunks, or %02f ms per chunk", $$18, $$40, $$18, Float.valueOf((float)$$40 / (float)$$18))), true);
        return 1;
    }
}

