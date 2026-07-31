/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.level.Level
 */
package ic2.core.entity.block;

import ic2.api.entity.block.ExplosiveEntity;
import ic2.core.ref.Ic2Blocks;
import ic2.core.ref.Ic2Entities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class ITntEntity
extends ExplosiveEntity {
    public ITntEntity(Level level, double d, double d2, double d3) {
        super(Ic2Entities.ITNT, level, d, d2, d3, 60, 5.5f, 0.9f, 0.3f, Ic2Blocks.ITNT.m_49966_(), 0);
    }

    public ITntEntity(EntityType<? extends ITntEntity> entityType, Level level) {
        this(level, 0.0, 0.0, 0.0);
    }
}

