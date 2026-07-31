/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.animal;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;

public abstract class ShoulderRidingEntity
extends TamableAnimal {
    private static final int f_149046_ = 100;
    private int f_29891_;

    protected ShoulderRidingEntity(EntityType<? extends ShoulderRidingEntity> p_29893_, Level p_29894_) {
        super((EntityType<? extends TamableAnimal>)p_29893_, p_29894_);
    }

    public boolean m_29895_(ServerPlayer p_29896_) {
        CompoundTag $$1 = new CompoundTag();
        $$1.m_128359_("id", this.m_20078_());
        this.m_20240_($$1);
        if (p_29896_.m_36360_($$1)) {
            this.m_146870_();
            return true;
        }
        return false;
    }

    @Override
    public void m_8119_() {
        ++this.f_29891_;
        super.m_8119_();
    }

    public boolean m_29897_() {
        return this.f_29891_ > 100;
    }
}

