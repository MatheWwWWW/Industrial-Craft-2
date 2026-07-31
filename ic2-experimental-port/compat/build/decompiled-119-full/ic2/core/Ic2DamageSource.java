/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.EntityDamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 */
package ic2.core;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class Ic2DamageSource
extends DamageSource {
    public static Ic2DamageSource electricity = new Ic2DamageSource("electricity");
    public static Ic2DamageSource nuke = (Ic2DamageSource)new Ic2DamageSource("nuke").m_19375_();
    public static Ic2DamageSource radiation = (Ic2DamageSource)new Ic2DamageSource("radiation").m_19380_();

    public Ic2DamageSource(String string) {
        super(string);
    }

    public static DamageSource getNukeSource(LivingEntity livingEntity) {
        return livingEntity != null ? new EntityDamageSource("nuke.player", (Entity)livingEntity).m_19375_() : nuke;
    }
}

