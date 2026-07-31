/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.boss.enderdragon.phases;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonChargePlayerPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonDeathPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonHoldingPatternPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonHoverPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonLandingApproachPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonLandingPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonSittingAttackingPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonSittingFlamingPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonSittingScanningPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonStrafePlayerPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonTakeoffPhase;

public class EnderDragonPhase<T extends DragonPhaseInstance> {
    private static EnderDragonPhase<?>[] f_31388_ = new EnderDragonPhase[0];
    public static final EnderDragonPhase<DragonHoldingPatternPhase> f_31377_ = EnderDragonPhase.m_31402_(DragonHoldingPatternPhase.class, "HoldingPattern");
    public static final EnderDragonPhase<DragonStrafePlayerPhase> f_31378_ = EnderDragonPhase.m_31402_(DragonStrafePlayerPhase.class, "StrafePlayer");
    public static final EnderDragonPhase<DragonLandingApproachPhase> f_31379_ = EnderDragonPhase.m_31402_(DragonLandingApproachPhase.class, "LandingApproach");
    public static final EnderDragonPhase<DragonLandingPhase> f_31380_ = EnderDragonPhase.m_31402_(DragonLandingPhase.class, "Landing");
    public static final EnderDragonPhase<DragonTakeoffPhase> f_31381_ = EnderDragonPhase.m_31402_(DragonTakeoffPhase.class, "Takeoff");
    public static final EnderDragonPhase<DragonSittingFlamingPhase> f_31382_ = EnderDragonPhase.m_31402_(DragonSittingFlamingPhase.class, "SittingFlaming");
    public static final EnderDragonPhase<DragonSittingScanningPhase> f_31383_ = EnderDragonPhase.m_31402_(DragonSittingScanningPhase.class, "SittingScanning");
    public static final EnderDragonPhase<DragonSittingAttackingPhase> f_31384_ = EnderDragonPhase.m_31402_(DragonSittingAttackingPhase.class, "SittingAttacking");
    public static final EnderDragonPhase<DragonChargePlayerPhase> f_31385_ = EnderDragonPhase.m_31402_(DragonChargePlayerPhase.class, "ChargingPlayer");
    public static final EnderDragonPhase<DragonDeathPhase> f_31386_ = EnderDragonPhase.m_31402_(DragonDeathPhase.class, "Dying");
    public static final EnderDragonPhase<DragonHoverPhase> f_31387_ = EnderDragonPhase.m_31402_(DragonHoverPhase.class, "Hover");
    private final Class<? extends DragonPhaseInstance> f_31389_;
    private final int f_31390_;
    private final String f_31391_;

    private EnderDragonPhase(int p_31394_, Class<? extends DragonPhaseInstance> p_31395_, String p_31396_) {
        this.f_31390_ = p_31394_;
        this.f_31389_ = p_31395_;
        this.f_31391_ = p_31396_;
    }

    public DragonPhaseInstance m_31400_(EnderDragon p_31401_) {
        try {
            Constructor<DragonPhaseInstance> $$1 = this.m_31397_();
            return $$1.newInstance(p_31401_);
        }
        catch (Exception $$2) {
            throw new Error($$2);
        }
    }

    protected Constructor<? extends DragonPhaseInstance> m_31397_() throws NoSuchMethodException {
        return this.f_31389_.getConstructor(EnderDragon.class);
    }

    public int m_31405_() {
        return this.f_31390_;
    }

    public String toString() {
        return this.f_31391_ + " (#" + this.f_31390_ + ")";
    }

    public static EnderDragonPhase<?> m_31398_(int p_31399_) {
        if (p_31399_ < 0 || p_31399_ >= f_31388_.length) {
            return f_31377_;
        }
        return f_31388_[p_31399_];
    }

    public static int m_31406_() {
        return f_31388_.length;
    }

    private static <T extends DragonPhaseInstance> EnderDragonPhase<T> m_31402_(Class<T> p_31403_, String p_31404_) {
        EnderDragonPhase<T> $$2 = new EnderDragonPhase<T>(f_31388_.length, p_31403_, p_31404_);
        f_31388_ = Arrays.copyOf(f_31388_, f_31388_.length + 1);
        EnderDragonPhase.f_31388_[$$2.m_31405_()] = $$2;
        return $$2;
    }
}

