/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.damagesource;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class IndirectEntityDamageSource
extends EntityDamageSource {
    @Nullable
    private final Entity f_19404_;

    public IndirectEntityDamageSource(String p_19406_, Entity p_19407_, @Nullable Entity p_19408_) {
        super(p_19406_, p_19407_);
        this.f_19404_ = p_19408_;
    }

    @Override
    @Nullable
    public Entity m_7640_() {
        return this.f_19391_;
    }

    @Override
    @Nullable
    public Entity m_7639_() {
        return this.f_19404_;
    }

    @Override
    public Component m_6157_(LivingEntity p_19410_) {
        Component $$1 = this.f_19404_ == null ? this.f_19391_.m_5446_() : this.f_19404_.m_5446_();
        ItemStack $$2 = this.f_19404_ instanceof LivingEntity ? ((LivingEntity)this.f_19404_).m_21205_() : ItemStack.f_41583_;
        String $$3 = "death.attack." + this.f_19326_;
        String $$4 = $$3 + ".item";
        if (!$$2.m_41619_() && $$2.m_41788_()) {
            return Component.m_237110_($$4, p_19410_.m_5446_(), $$1, $$2.m_41611_());
        }
        return Component.m_237110_($$3, p_19410_.m_5446_(), $$1);
    }
}

