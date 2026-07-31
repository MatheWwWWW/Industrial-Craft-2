/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.storage;

import java.util.Locale;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.CrashReportCategory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.level.timers.TimerQueue;

public interface ServerLevelData
extends WritableLevelData {
    public String m_5462_();

    public void m_5557_(boolean var1);

    public int m_6531_();

    public void m_6399_(int var1);

    public void m_6398_(int var1);

    public int m_6558_();

    @Override
    default public void m_142471_(CrashReportCategory p_164976_, LevelHeightAccessor p_164977_) {
        WritableLevelData.super.m_142471_(p_164976_, p_164977_);
        p_164976_.m_128165_("Level name", this::m_5462_);
        p_164976_.m_128165_("Level game mode", () -> String.format(Locale.ROOT, "Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", this.m_5464_().m_46405_(), this.m_5464_().m_46392_(), this.m_5466_(), this.m_5468_()));
        p_164976_.m_128165_("Level weather", () -> String.format(Locale.ROOT, "Rain time: %d (now: %b), thunder time: %d (now: %b)", this.m_6531_(), this.m_6533_(), this.m_6558_(), this.m_6534_()));
    }

    public int m_6537_();

    public void m_6393_(int var1);

    public int m_6530_();

    public void m_6391_(int var1);

    public int m_6528_();

    public void m_6387_(int var1);

    @Nullable
    public UUID m_142403_();

    public void m_8115_(UUID var1);

    public GameType m_5464_();

    public void m_7831_(WorldBorder.Settings var1);

    public WorldBorder.Settings m_5813_();

    public boolean m_6535_();

    public void m_5555_(boolean var1);

    public boolean m_5468_();

    public void m_5458_(GameType var1);

    public TimerQueue<MinecraftServer> m_7540_();

    public void m_6253_(long var1);

    public void m_6247_(long var1);
}

