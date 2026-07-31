/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.entity;

import net.minecraft.world.entity.Entity;

public interface EntityInLevelCallback {
    public static final EntityInLevelCallback f_156799_ = new EntityInLevelCallback(){

        @Override
        public void m_142044_() {
        }

        @Override
        public void m_142472_(Entity.RemovalReason p_156805_) {
        }
    };

    public void m_142044_();

    public void m_142472_(Entity.RemovalReason var1);
}

