/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.material.FluidState
 */
package ic2.core.entity.boat;

import ic2.api.entity.boat.AbstractBoatEntity;
import ic2.api.entity.boat.BoatType;
import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.ref.Ic2BoatTypes;
import ic2.core.ref.Ic2Items;
import ic2.core.util.StackUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;

public class ElectricBoatEntity
extends AbstractBoatEntity {
    private static final double euConsume = 4.0;
    private boolean accelerated = false;

    public ElectricBoatEntity(EntityType<? extends AbstractBoatEntity> entityType, Level level) {
        super(entityType, level);
    }

    public ElectricBoatEntity(EntityType<? extends AbstractBoatEntity> entityType, Level level, double d, double d2, double d3) {
        super(entityType, level, d, d2, d3);
    }

    public Item m_38369_() {
        return Ic2Items.ELECTRIC_BOAT;
    }

    @Override
    public BoatType getOverrideBoatType() {
        return Ic2BoatTypes.ELECTRIC;
    }

    @Override
    public boolean brokenByFalling() {
        return false;
    }

    public boolean m_5825_() {
        return true;
    }

    public boolean m_6060_() {
        return false;
    }

    @Override
    public boolean canFloatOn(FluidState fluidState) {
        return super.canFloatOn(fluidState);
    }

    protected double getAccelerationFactor() {
        return this.accelerated ? 1.5 : 0.25;
    }

    protected float m_6041_() {
        return (float)((double)super.m_6041_() * this.getAccelerationFactor());
    }

    @Override
    public void m_8119_() {
        this.accelerated = false;
        Entity entity = this.m_6688_();
        if (entity instanceof Player && IC2.keyboard.isForwardKeyDown((Player)entity)) {
            for (ItemStack itemStack : ((Player)entity).m_150109_().f_35975_) {
                if (StackUtil.isEmpty(itemStack) || ElectricItem.manager.discharge(itemStack, 4.0, Integer.MAX_VALUE, true, true, true) != 4.0) continue;
                ElectricItem.manager.discharge(itemStack, 4.0, Integer.MAX_VALUE, true, true, false);
                this.accelerated = true;
                break;
            }
        }
        super.m_8119_();
    }
}

