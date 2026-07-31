/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.damagesource;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class EntityDamageSource
extends DamageSource {
    protected final Entity f_19391_;
    private boolean f_19392_;

    public EntityDamageSource(String p_19394_, Entity p_19395_) {
        super(p_19394_);
        this.f_19391_ = p_19395_;
    }

    public EntityDamageSource m_19402_() {
        this.f_19392_ = true;
        return this;
    }

    public boolean m_19403_() {
        return this.f_19392_;
    }

    @Override
    public Entity m_7639_() {
        return this.f_19391_;
    }

    @Override
    public Component m_6157_(LivingEntity p_19397_) {
        ItemStack $$1 = this.f_19391_ instanceof LivingEntity ? ((LivingEntity)this.f_19391_).m_21205_() : ItemStack.f_41583_;
        String $$2 = "death.attack." + this.f_19326_;
        if (!$$1.m_41619_() && $$1.m_41788_()) {
            return Component.m_237110_($$2 + ".item", p_19397_.m_5446_(), this.f_19391_.m_5446_(), $$1.m_41611_());
        }
        return Component.m_237110_($$2, p_19397_.m_5446_(), this.f_19391_.m_5446_());
    }

    @Override
    public boolean m_7986_() {
        return this.f_19391_ instanceof LivingEntity && !(this.f_19391_ instanceof Player);
    }

    @Override
    @Nullable
    public Vec3 m_7270_() {
        return this.f_19391_.m_20182_();
    }

    @Override
    public String toString() {
        return "EntityDamageSource (" + this.f_19391_ + ")";
    }
}

