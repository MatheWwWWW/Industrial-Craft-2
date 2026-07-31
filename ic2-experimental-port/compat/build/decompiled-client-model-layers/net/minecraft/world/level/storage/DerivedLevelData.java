/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.storage;

import java.util.UUID;
import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.level.timers.TimerQueue;

public class DerivedLevelData
implements ServerLevelData {
    private final WorldData f_78076_;
    private final ServerLevelData f_78077_;

    public DerivedLevelData(WorldData p_78079_, ServerLevelData p_78080_) {
        this.f_78076_ = p_78079_;
        this.f_78077_ = p_78080_;
    }

    @Override
    public int m_6789_() {
        return this.f_78077_.m_6789_();
    }

    @Override
    public int m_6527_() {
        return this.f_78077_.m_6527_();
    }

    @Override
    public int m_6526_() {
        return this.f_78077_.m_6526_();
    }

    @Override
    public float m_6790_() {
        return this.f_78077_.m_6790_();
    }

    @Override
    public long m_6793_() {
        return this.f_78077_.m_6793_();
    }

    @Override
    public long m_6792_() {
        return this.f_78077_.m_6792_();
    }

    @Override
    public String m_5462_() {
        return this.f_78076_.m_5462_();
    }

    @Override
    public int m_6537_() {
        return this.f_78077_.m_6537_();
    }

    @Override
    public void m_6393_(int p_78085_) {
    }

    @Override
    public boolean m_6534_() {
        return this.f_78077_.m_6534_();
    }

    @Override
    public int m_6558_() {
        return this.f_78077_.m_6558_();
    }

    @Override
    public boolean m_6533_() {
        return this.f_78077_.m_6533_();
    }

    @Override
    public int m_6531_() {
        return this.f_78077_.m_6531_();
    }

    @Override
    public GameType m_5464_() {
        return this.f_78076_.m_5464_();
    }

    @Override
    public void m_6395_(int p_78103_) {
    }

    @Override
    public void m_6397_(int p_78110_) {
    }

    @Override
    public void m_6400_(int p_78115_) {
    }

    @Override
    public void m_7113_(float p_78083_) {
    }

    @Override
    public void m_6253_(long p_78087_) {
    }

    @Override
    public void m_6247_(long p_78105_) {
    }

    @Override
    public void m_7250_(BlockPos p_78093_, float p_78094_) {
    }

    @Override
    public void m_5557_(boolean p_78100_) {
    }

    @Override
    public void m_6398_(int p_78118_) {
    }

    @Override
    public void m_5565_(boolean p_78107_) {
    }

    @Override
    public void m_6399_(int p_78121_) {
    }

    @Override
    public void m_5458_(GameType p_78089_) {
    }

    @Override
    public boolean m_5466_() {
        return this.f_78076_.m_5466_();
    }

    @Override
    public boolean m_5468_() {
        return this.f_78076_.m_5468_();
    }

    @Override
    public boolean m_6535_() {
        return this.f_78077_.m_6535_();
    }

    @Override
    public void m_5555_(boolean p_78112_) {
    }

    @Override
    public GameRules m_5470_() {
        return this.f_78076_.m_5470_();
    }

    @Override
    public WorldBorder.Settings m_5813_() {
        return this.f_78077_.m_5813_();
    }

    @Override
    public void m_7831_(WorldBorder.Settings p_78091_) {
    }

    @Override
    public Difficulty m_5472_() {
        return this.f_78076_.m_5472_();
    }

    @Override
    public boolean m_5474_() {
        return this.f_78076_.m_5474_();
    }

    @Override
    public TimerQueue<MinecraftServer> m_7540_() {
        return this.f_78077_.m_7540_();
    }

    @Override
    public int m_6530_() {
        return 0;
    }

    @Override
    public void m_6391_(int p_78124_) {
    }

    @Override
    public int m_6528_() {
        return 0;
    }

    @Override
    public void m_6387_(int p_78127_) {
    }

    @Override
    public UUID m_142403_() {
        return null;
    }

    @Override
    public void m_8115_(UUID p_78096_) {
    }

    @Override
    public void m_142471_(CrashReportCategory p_164852_, LevelHeightAccessor p_164853_) {
        p_164852_.m_128159_("Derived", true);
        this.f_78077_.m_142471_(p_164852_, p_164853_);
    }
}

