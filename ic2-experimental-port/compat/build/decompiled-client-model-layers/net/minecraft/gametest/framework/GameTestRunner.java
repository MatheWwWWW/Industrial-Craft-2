/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Streams
 *  org.apache.commons.lang3.mutable.MutableInt
 */
package net.minecraft.gametest.framework;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Streams;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestBatch;
import net.minecraft.gametest.framework.GameTestBatchRunner;
import net.minecraft.gametest.framework.GameTestInfo;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraft.gametest.framework.GameTestTicker;
import net.minecraft.gametest.framework.ReportGameListener;
import net.minecraft.gametest.framework.StructureUtils;
import net.minecraft.gametest.framework.TestFunction;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.apache.commons.lang3.mutable.MutableInt;

public class GameTestRunner {
    private static final int f_177525_ = 100;
    public static final int f_177521_ = 2;
    public static final int f_177522_ = 5;
    public static final int f_177523_ = 6;
    public static final int f_177524_ = 8;

    public static void m_127742_(GameTestInfo p_127743_, BlockPos p_127744_, GameTestTicker p_127745_) {
        p_127743_.m_127616_();
        p_127745_.m_127788_(p_127743_);
        p_127743_.m_127624_(new ReportGameListener(p_127743_, p_127745_, p_127744_));
        p_127743_.m_127619_(p_127744_, 2);
    }

    public static Collection<GameTestInfo> m_127726_(Collection<GameTestBatch> p_127727_, BlockPos p_127728_, Rotation p_127729_, ServerLevel p_127730_, GameTestTicker p_127731_, int p_127732_) {
        GameTestBatchRunner $$6 = new GameTestBatchRunner(p_127727_, p_127728_, p_127729_, p_127730_, p_127731_, p_127732_);
        $$6.m_127583_();
        return $$6.m_127569_();
    }

    public static Collection<GameTestInfo> m_127752_(Collection<TestFunction> p_127753_, BlockPos p_127754_, Rotation p_127755_, ServerLevel p_127756_, GameTestTicker p_127757_, int p_127758_) {
        return GameTestRunner.m_127726_(GameTestRunner.m_127724_(p_127753_), p_127754_, p_127755_, p_127756_, p_127757_, p_127758_);
    }

    public static Collection<GameTestBatch> m_127724_(Collection<TestFunction> p_127725_) {
        Map<String, List<TestFunction>> $$1 = p_127725_.stream().collect(Collectors.groupingBy(TestFunction::m_128081_));
        return (Collection)$$1.entrySet().stream().flatMap(p_177537_ -> {
            String $$1 = (String)p_177537_.getKey();
            Consumer<ServerLevel> $$2 = GameTestRegistry.m_127676_($$1);
            Consumer<ServerLevel> $$3 = GameTestRegistry.m_177517_($$1);
            MutableInt $$4 = new MutableInt();
            Collection $$5 = (Collection)p_177537_.getValue();
            return Streams.stream((Iterable)Iterables.partition((Iterable)$$5, (int)100)).map(p_177535_ -> new GameTestBatch($$1 + ":" + $$4.incrementAndGet(), (Collection<TestFunction>)ImmutableList.copyOf((Collection)p_177535_), $$2, $$3));
        }).collect(ImmutableList.toImmutableList());
    }

    public static void m_127694_(ServerLevel p_127695_, BlockPos p_127696_, GameTestTicker p_127697_, int p_127698_) {
        p_127697_.m_127787_();
        BlockPos $$4 = p_127696_.m_7918_(-p_127698_, 0, -p_127698_);
        BlockPos $$5 = p_127696_.m_7918_(p_127698_, 0, p_127698_);
        BlockPos.m_121990_($$4, $$5).filter(p_177540_ -> p_127695_.m_8055_((BlockPos)p_177540_).m_60713_(Blocks.f_50677_)).forEach(p_177529_ -> {
            StructureBlockEntity $$2 = (StructureBlockEntity)p_127695_.m_7702_((BlockPos)p_177529_);
            BlockPos $$3 = $$2.m_58899_();
            BoundingBox $$4 = StructureUtils.m_127904_($$2);
            StructureUtils.m_127849_($$4, $$3.m_123342_(), p_127695_);
        });
    }

    public static void m_127685_(ServerLevel p_127686_) {
        DebugPackets.m_133674_(p_127686_);
    }
}

