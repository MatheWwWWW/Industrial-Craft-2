/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.Level
 */
package ic2.core.entity.boat;

import ic2.api.entity.boat.AbstractBoatEntity;
import ic2.api.entity.boat.BoatType;
import ic2.core.ref.Ic2BoatTypes;
import ic2.core.ref.Ic2Items;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class CarbonBoatEntity
extends AbstractBoatEntity {
    public CarbonBoatEntity(EntityType<? extends AbstractBoatEntity> entityType, Level level) {
        super(entityType, level);
    }

    public CarbonBoatEntity(EntityType<? extends AbstractBoatEntity> entityType, Level level, double d, double d2, double d3) {
        super(entityType, level, d, d2, d3);
    }

    @Override
    public boolean brokenByFalling() {
        return false;
    }

    public Item m_38369_() {
        return Ic2Items.CARBON_BOAT;
    }

    @Override
    public BoatType getOverrideBoatType() {
        return Ic2BoatTypes.CARBON;
    }
}

