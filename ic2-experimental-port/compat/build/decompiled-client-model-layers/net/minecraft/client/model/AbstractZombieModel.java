/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.monster.Monster;

public abstract class AbstractZombieModel<T extends Monster>
extends HumanoidModel<T> {
    protected AbstractZombieModel(ModelPart p_170337_) {
        super(p_170337_);
    }

    @Override
    public void m_6973_(T p_102001_, float p_102002_, float p_102003_, float p_102004_, float p_102005_, float p_102006_) {
        super.m_6973_(p_102001_, p_102002_, p_102003_, p_102004_, p_102005_, p_102006_);
        AnimationUtils.m_102102_(this.f_102812_, this.f_102811_, this.m_7134_(p_102001_), this.f_102608_, p_102004_);
    }

    public abstract boolean m_7134_(T var1);
}

