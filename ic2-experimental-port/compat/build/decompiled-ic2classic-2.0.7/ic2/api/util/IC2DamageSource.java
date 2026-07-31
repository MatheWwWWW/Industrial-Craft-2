/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 */
package ic2.api.util;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public final class IC2DamageSource
extends DamageSource {
    public static final IC2DamageSource ELECTRICITY = new IC2DamageSource("electricity");
    public static final IC2DamageSource NUKE = new IC2DamageSource("nuke");
    public static final IC2DamageSource RADIATION = new IC2DamageSource("radiation");
    Entity source;

    private IC2DamageSource(String damageTypeIn) {
        super(damageTypeIn);
    }

    public static IC2DamageSource newShockDamage(Entity entity) {
        IC2DamageSource source = new IC2DamageSource(ELECTRICITY.m_19385_());
        source.source = entity;
        return source;
    }

    public Entity m_7639_() {
        return this.source;
    }
}

