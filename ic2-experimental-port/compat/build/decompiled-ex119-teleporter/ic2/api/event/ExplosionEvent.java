/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.event.level.LevelEvent
 *  net.minecraftforge.eventbus.api.Cancelable
 */
package ic2.api.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class ExplosionEvent
extends LevelEvent {
    public final Entity entity;
    public final Vec3 pos;
    public final double power;
    public final LivingEntity igniter;
    public final int radiationRange;
    public final double rangeLimit;

    public ExplosionEvent(Level level, Entity entity, Vec3 vec3, double d, LivingEntity livingEntity, int n, double d2) {
        super((LevelAccessor)level);
        this.entity = entity;
        this.pos = vec3;
        this.power = d;
        this.igniter = livingEntity;
        this.radiationRange = n;
        this.rangeLimit = d2;
    }
}

