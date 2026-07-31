/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  org.apache.commons.lang3.exception.ExceptionUtils
 */
package net.minecraft.gametest.framework;

import com.google.common.base.MoreObjects;
import java.util.Arrays;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.ExhaustedAttemptsException;
import net.minecraft.gametest.framework.GameTestAssertPosException;
import net.minecraft.gametest.framework.GameTestInfo;
import net.minecraft.gametest.framework.GameTestListener;
import net.minecraft.gametest.framework.GameTestTicker;
import net.minecraft.gametest.framework.GlobalTestReporter;
import net.minecraft.gametest.framework.TestFunction;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.apache.commons.lang3.exception.ExceptionUtils;

class ReportGameListener
implements GameTestListener {
    private final GameTestInfo f_177688_;
    private final GameTestTicker f_177689_;
    private final BlockPos f_177690_;
    int f_177686_;
    int f_177687_;

    public ReportGameListener(GameTestInfo p_177692_, GameTestTicker p_177693_, BlockPos p_177694_) {
        this.f_177688_ = p_177692_;
        this.f_177689_ = p_177693_;
        this.f_177690_ = p_177694_;
        this.f_177686_ = 0;
        this.f_177687_ = 0;
    }

    @Override
    public void m_8073_(GameTestInfo p_177718_) {
        ReportGameListener.m_177719_(this.f_177688_, Blocks.f_50208_);
        ++this.f_177686_;
    }

    @Override
    public void m_142378_(GameTestInfo p_177729_) {
        ++this.f_177687_;
        if (!p_177729_.m_177491_()) {
            ReportGameListener.m_177722_(p_177729_, p_177729_.m_127633_() + " passed! (" + p_177729_.m_177485_() + "ms)");
            return;
        }
        if (this.f_177687_ >= p_177729_.m_177493_()) {
            ReportGameListener.m_177722_(p_177729_, p_177729_ + " passed " + this.f_177687_ + " times of " + this.f_177686_ + " attempts.");
        } else {
            ReportGameListener.m_177700_(this.f_177688_.m_127637_(), ChatFormatting.GREEN, "Flaky test " + this.f_177688_ + " succeeded, attempt: " + this.f_177686_ + " successes: " + this.f_177687_);
            this.m_177695_();
        }
    }

    @Override
    public void m_8066_(GameTestInfo p_177737_) {
        if (!p_177737_.m_177491_()) {
            ReportGameListener.m_177725_(p_177737_, p_177737_.m_127642_());
            return;
        }
        TestFunction $$1 = this.f_177688_.m_127648_();
        String $$2 = "Flaky test " + this.f_177688_ + " failed, attempt: " + this.f_177686_ + "/" + $$1.m_177829_();
        if ($$1.m_177830_() > 1) {
            $$2 = $$2 + ", successes: " + this.f_177687_ + " (" + $$1.m_177830_() + " required)";
        }
        ReportGameListener.m_177700_(this.f_177688_.m_127637_(), ChatFormatting.YELLOW, $$2);
        if (p_177737_.m_177492_() - this.f_177686_ + this.f_177687_ >= p_177737_.m_177493_()) {
            this.m_177695_();
        } else {
            ReportGameListener.m_177725_(p_177737_, new ExhaustedAttemptsException(this.f_177686_, this.f_177687_, p_177737_));
        }
    }

    public static void m_177722_(GameTestInfo p_177723_, String p_177724_) {
        ReportGameListener.m_177719_(p_177723_, Blocks.f_50205_);
        ReportGameListener.m_177730_(p_177723_, p_177724_);
    }

    private static void m_177730_(GameTestInfo p_177731_, String p_177732_) {
        ReportGameListener.m_177700_(p_177731_.m_127637_(), ChatFormatting.GREEN, p_177732_);
        GlobalTestReporter.m_177657_(p_177731_);
    }

    protected static void m_177725_(GameTestInfo p_177726_, Throwable p_177727_) {
        ReportGameListener.m_177719_(p_177726_, p_177726_.m_127643_() ? Blocks.f_50214_ : Blocks.f_50148_);
        ReportGameListener.m_177738_(p_177726_, Util.m_137575_(p_177727_));
        ReportGameListener.m_177733_(p_177726_, p_177727_);
    }

    protected static void m_177733_(GameTestInfo p_177734_, Throwable p_177735_) {
        String $$2 = p_177735_.getMessage() + (String)(p_177735_.getCause() == null ? "" : " cause: " + Util.m_137575_(p_177735_.getCause()));
        String $$3 = (p_177734_.m_127643_() ? "" : "(optional) ") + p_177734_.m_127633_() + " failed! " + $$2;
        ReportGameListener.m_177700_(p_177734_.m_127637_(), p_177734_.m_127643_() ? ChatFormatting.RED : ChatFormatting.YELLOW, $$3);
        Throwable $$4 = (Throwable)MoreObjects.firstNonNull((Object)ExceptionUtils.getRootCause((Throwable)p_177735_), (Object)p_177735_);
        if ($$4 instanceof GameTestAssertPosException) {
            GameTestAssertPosException $$5 = (GameTestAssertPosException)$$4;
            ReportGameListener.m_177696_(p_177734_.m_127637_(), $$5.m_127497_(), $$5.m_127496_());
        }
        GlobalTestReporter.m_177653_(p_177734_);
    }

    private void m_177695_() {
        this.f_177688_.m_177487_();
        GameTestInfo $$0 = new GameTestInfo(this.f_177688_.m_127648_(), this.f_177688_.m_127646_(), this.f_177688_.m_127637_());
        $$0.m_127616_();
        this.f_177689_.m_127788_($$0);
        $$0.m_127624_(this);
        $$0.m_127619_(this.f_177690_, 2);
    }

    protected static void m_177719_(GameTestInfo p_177720_, Block p_177721_) {
        ServerLevel $$2 = p_177720_.m_127637_();
        BlockPos $$3 = p_177720_.m_127636_();
        BlockPos $$4 = new BlockPos(-1, -1, -1);
        BlockPos $$5 = StructureTemplate.m_74593_($$3.m_121955_($$4), Mirror.NONE, p_177720_.m_127646_(), $$3);
        $$2.m_46597_($$5, Blocks.f_50273_.m_49966_().m_60717_(p_177720_.m_127646_()));
        BlockPos $$6 = $$5.m_7918_(0, 1, 0);
        $$2.m_46597_($$6, p_177721_.m_49966_());
        for (int $$7 = -1; $$7 <= 1; ++$$7) {
            for (int $$8 = -1; $$8 <= 1; ++$$8) {
                BlockPos $$9 = $$5.m_7918_($$7, -1, $$8);
                $$2.m_46597_($$9, Blocks.f_50075_.m_49966_());
            }
        }
    }

    private static void m_177738_(GameTestInfo p_177739_, String p_177740_) {
        ServerLevel $$2 = p_177739_.m_127637_();
        BlockPos $$3 = p_177739_.m_127636_();
        BlockPos $$4 = new BlockPos(-1, 1, -1);
        BlockPos $$5 = StructureTemplate.m_74593_($$3.m_121955_($$4), Mirror.NONE, p_177739_.m_127646_(), $$3);
        $$2.m_46597_($$5, Blocks.f_50624_.m_49966_().m_60717_(p_177739_.m_127646_()));
        BlockState $$6 = $$2.m_8055_($$5);
        ItemStack $$7 = ReportGameListener.m_177710_(p_177739_.m_127633_(), p_177739_.m_127643_(), p_177740_);
        LecternBlock.m_153566_(null, $$2, $$5, $$6, $$7);
    }

    private static ItemStack m_177710_(String p_177711_, boolean p_177712_, String p_177713_) {
        ItemStack $$3 = new ItemStack(Items.f_42614_);
        ListTag $$4 = new ListTag();
        StringBuffer $$5 = new StringBuffer();
        Arrays.stream(p_177711_.split("\\.")).forEach(p_177716_ -> $$5.append((String)p_177716_).append('\n'));
        if (!p_177712_) {
            $$5.append("(optional)\n");
        }
        $$5.append("-------------------\n");
        $$4.add(StringTag.m_129297_($$5 + p_177713_));
        $$3.m_41700_("pages", $$4);
        return $$3;
    }

    protected static void m_177700_(ServerLevel p_177701_, ChatFormatting p_177702_, String p_177703_) {
        p_177701_.m_8795_(p_177705_ -> true).forEach(p_177709_ -> p_177709_.m_213846_(Component.m_237113_(p_177703_).m_130940_(p_177702_)));
    }

    private static void m_177696_(ServerLevel p_177697_, BlockPos p_177698_, String p_177699_) {
        DebugPackets.m_133682_(p_177697_, p_177698_, p_177699_, -2130771968, Integer.MAX_VALUE);
    }
}

