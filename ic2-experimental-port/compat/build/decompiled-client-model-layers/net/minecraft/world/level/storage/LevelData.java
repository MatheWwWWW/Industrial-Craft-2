/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.storage;

import java.util.Locale;
import net.minecraft.CrashReportCategory;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelHeightAccessor;

public interface LevelData {
    public int m_6789_();

    public int m_6527_();

    public int m_6526_();

    public float m_6790_();

    public long m_6793_();

    public long m_6792_();

    public boolean m_6534_();

    public boolean m_6533_();

    public void m_5565_(boolean var1);

    public boolean m_5466_();

    public GameRules m_5470_();

    public Difficulty m_5472_();

    public boolean m_5474_();

    default public void m_142471_(CrashReportCategory p_164873_, LevelHeightAccessor p_164874_) {
        p_164873_.m_128165_("Level spawn location", () -> CrashReportCategory.m_178942_(p_164874_, this.m_6789_(), this.m_6527_(), this.m_6526_()));
        p_164873_.m_128165_("Level time", () -> String.format(Locale.ROOT, "%d game time, %d day time", this.m_6793_(), this.m_6792_()));
    }
}

