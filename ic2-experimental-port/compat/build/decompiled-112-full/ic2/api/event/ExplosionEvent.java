/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 *  net.minecraftforge.event.world.WorldEvent
 *  net.minecraftforge.fml.common.eventhandler.Cancelable
 */
package ic2.api.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class ExplosionEvent
extends WorldEvent {
    public final Entity entity;
    public final Vec3d pos;
    public final double power;
    public final EntityLivingBase igniter;
    public final int radiationRange;
    public final double rangeLimit;

    public ExplosionEvent(World world, Entity entity, Vec3d pos, double power, EntityLivingBase igniter, int radiationRange, double rangeLimit) {
        super(world);
        this.entity = entity;
        this.pos = pos;
        this.power = power;
        this.igniter = igniter;
        this.radiationRange = radiationRange;
        this.rangeLimit = rangeLimit;
    }
}

