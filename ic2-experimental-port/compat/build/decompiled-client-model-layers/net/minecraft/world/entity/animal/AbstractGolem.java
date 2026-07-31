/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal;

import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public abstract class AbstractGolem
extends PathfinderMob {
    protected AbstractGolem(EntityType<? extends AbstractGolem> p_27508_, Level p_27509_) {
        super((EntityType<? extends PathfinderMob>)p_27508_, p_27509_);
    }

    @Override
    public boolean m_142535_(float p_148711_, float p_148712_, DamageSource p_148713_) {
        return false;
    }

    @Override
    @Nullable
    protected SoundEvent m_7515_() {
        return null;
    }

    @Override
    @Nullable
    protected SoundEvent m_7975_(DamageSource p_27517_) {
        return null;
    }

    @Override
    @Nullable
    protected SoundEvent m_5592_() {
        return null;
    }

    @Override
    public int m_8100_() {
        return 120;
    }

    @Override
    public boolean m_6785_(double p_27519_) {
        return false;
    }
}

