/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public interface DragonPhaseInstance {
    public boolean m_7080_();

    public void m_6991_();

    public void m_6989_();

    public void m_8059_(EndCrystal var1, BlockPos var2, DamageSource var3, @Nullable Player var4);

    public void m_7083_();

    public void m_7081_();

    public float m_7072_();

    public float m_7089_();

    public EnderDragonPhase<? extends DragonPhaseInstance> m_7309_();

    @Nullable
    public Vec3 m_5535_();

    public float m_7584_(DamageSource var1, float var2);
}

