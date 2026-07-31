/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 */
package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.Util;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.progress.StoringChunkProgressListener;
import net.minecraft.util.Mth;
import net.minecraft.world.level.chunk.ChunkStatus;

public class LevelLoadingScreen
extends Screen {
    private static final long f_169309_ = 2000L;
    private final StoringChunkProgressListener f_96138_;
    private long f_96139_ = -1L;
    private boolean f_169310_;
    private static final Object2IntMap<ChunkStatus> f_96140_ = (Object2IntMap)Util.m_137469_(new Object2IntOpenHashMap(), p_96157_ -> {
        p_96157_.defaultReturnValue(0);
        p_96157_.put((Object)ChunkStatus.f_62314_, 0x545454);
        p_96157_.put((Object)ChunkStatus.f_62315_, 0x999999);
        p_96157_.put((Object)ChunkStatus.f_62316_, 6250897);
        p_96157_.put((Object)ChunkStatus.f_62317_, 8434258);
        p_96157_.put((Object)ChunkStatus.f_62318_, 0xD1D1D1);
        p_96157_.put((Object)ChunkStatus.f_62319_, 7497737);
        p_96157_.put((Object)ChunkStatus.f_62320_, 7169628);
        p_96157_.put((Object)ChunkStatus.f_62321_, 3159410);
        p_96157_.put((Object)ChunkStatus.f_62322_, 2213376);
        p_96157_.put((Object)ChunkStatus.f_62323_, 0xCCCCCC);
        p_96157_.put((Object)ChunkStatus.f_62324_, 15884384);
        p_96157_.put((Object)ChunkStatus.f_62325_, 0xEEEEEE);
        p_96157_.put((Object)ChunkStatus.f_62326_, 0xFFFFFF);
    });

    public LevelLoadingScreen(StoringChunkProgressListener p_96143_) {
        super(GameNarrator.f_93310_);
        this.f_96138_ = p_96143_;
    }

    @Override
    public boolean m_6913_() {
        return false;
    }

    @Override
    public void m_7861_() {
        this.f_169310_ = true;
        this.m_169407_(true);
    }

    @Override
    protected void m_142227_(NarrationElementOutput p_169312_) {
        if (this.f_169310_) {
            p_169312_.m_169146_(NarratedElementType.TITLE, Component.m_237115_("narrator.loading.done"));
        } else {
            String $$1 = this.m_169313_();
            p_169312_.m_169143_(NarratedElementType.TITLE, $$1);
        }
    }

    private String m_169313_() {
        return Mth.m_14045_(this.f_96138_.m_9674_(), 0, 100) + "%";
    }

    @Override
    public void m_6305_(PoseStack p_96145_, int p_96146_, int p_96147_, float p_96148_) {
        this.m_7333_(p_96145_);
        long $$4 = Util.m_137550_();
        if ($$4 - this.f_96139_ > 2000L) {
            this.f_96139_ = $$4;
            this.m_169407_(true);
        }
        int $$5 = this.f_96543_ / 2;
        int $$6 = this.f_96544_ / 2;
        int $$7 = 30;
        LevelLoadingScreen.m_96149_(p_96145_, this.f_96138_, $$5, $$6 + 30, 2, 0);
        LevelLoadingScreen.m_93208_(p_96145_, this.f_96547_, this.m_169313_(), $$5, $$6 - this.f_96547_.f_92710_ / 2 - 30, 0xFFFFFF);
    }

    public static void m_96149_(PoseStack p_96150_, StoringChunkProgressListener p_96151_, int p_96152_, int p_96153_, int p_96154_, int p_96155_) {
        int $$6 = p_96154_ + p_96155_;
        int $$7 = p_96151_.m_9672_();
        int $$8 = $$7 * $$6 - p_96155_;
        int $$9 = p_96151_.m_9673_();
        int $$10 = $$9 * $$6 - p_96155_;
        int $$11 = p_96152_ - $$10 / 2;
        int $$12 = p_96153_ - $$10 / 2;
        int $$13 = $$8 / 2 + 1;
        int $$14 = -16772609;
        if (p_96155_ != 0) {
            LevelLoadingScreen.m_93172_(p_96150_, p_96152_ - $$13, p_96153_ - $$13, p_96152_ - $$13 + 1, p_96153_ + $$13, -16772609);
            LevelLoadingScreen.m_93172_(p_96150_, p_96152_ + $$13 - 1, p_96153_ - $$13, p_96152_ + $$13, p_96153_ + $$13, -16772609);
            LevelLoadingScreen.m_93172_(p_96150_, p_96152_ - $$13, p_96153_ - $$13, p_96152_ + $$13, p_96153_ - $$13 + 1, -16772609);
            LevelLoadingScreen.m_93172_(p_96150_, p_96152_ - $$13, p_96153_ + $$13 - 1, p_96152_ + $$13, p_96153_ + $$13, -16772609);
        }
        for (int $$15 = 0; $$15 < $$9; ++$$15) {
            for (int $$16 = 0; $$16 < $$9; ++$$16) {
                ChunkStatus $$17 = p_96151_.m_9663_($$15, $$16);
                int $$18 = $$11 + $$15 * $$6;
                int $$19 = $$12 + $$16 * $$6;
                LevelLoadingScreen.m_93172_(p_96150_, $$18, $$19, $$18 + p_96154_, $$19 + p_96154_, f_96140_.getInt((Object)$$17) | 0xFF000000);
            }
        }
    }
}

