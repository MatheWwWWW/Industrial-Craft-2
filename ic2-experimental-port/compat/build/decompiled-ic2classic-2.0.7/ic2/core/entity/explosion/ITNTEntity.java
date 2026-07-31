/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.level.Level
 */
package ic2.core.entity.explosion;

import ic2.core.entity.explosion.IC2ExplosiveEntity;
import ic2.core.platform.registries.IC2Blocks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class ITNTEntity
extends IC2ExplosiveEntity {
    public ITNTEntity(EntityType<?> entityTypeIn, Level worldIn) {
        this(entityTypeIn, worldIn, 0.0, 0.0, 0.0);
    }

    public ITNTEntity(EntityType<?> entityTypeIn, Level worldIn, double x, double y, double z) {
        super(entityTypeIn, worldIn, x, y, z, 60, 5.5f, 0.9f, IC2Blocks.ITNT.m_49966_(), null);
    }
}

