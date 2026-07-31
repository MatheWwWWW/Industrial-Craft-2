/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;

public class Marker
extends Entity {
    private static final String f_147247_ = "data";
    private CompoundTag f_147248_ = new CompoundTag();

    public Marker(EntityType<?> p_147250_, Level p_147251_) {
        super(p_147250_, p_147251_);
        this.f_19794_ = true;
    }

    @Override
    public void m_8119_() {
    }

    @Override
    protected void m_8097_() {
    }

    @Override
    protected void m_7378_(CompoundTag p_147254_) {
        this.f_147248_ = p_147254_.m_128469_(f_147247_);
    }

    @Override
    protected void m_7380_(CompoundTag p_147257_) {
        p_147257_.m_128365_(f_147247_, this.f_147248_.m_6426_());
    }

    @Override
    public Packet<?> m_5654_() {
        throw new IllegalStateException("Markers should never be sent");
    }

    @Override
    protected void m_20348_(Entity p_147260_) {
        p_147260_.m_8127_();
    }

    @Override
    public PushReaction m_7752_() {
        return PushReaction.IGNORE;
    }
}

