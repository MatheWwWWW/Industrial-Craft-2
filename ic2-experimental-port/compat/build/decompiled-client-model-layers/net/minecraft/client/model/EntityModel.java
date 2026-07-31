/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model;

import java.util.function.Function;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public abstract class EntityModel<T extends Entity>
extends Model {
    public float f_102608_;
    public boolean f_102609_;
    public boolean f_102610_ = true;

    protected EntityModel() {
        this(RenderType::m_110458_);
    }

    protected EntityModel(Function<ResourceLocation, RenderType> p_102613_) {
        super(p_102613_);
    }

    public abstract void m_6973_(T var1, float var2, float var3, float var4, float var5, float var6);

    public void m_6839_(T p_102614_, float p_102615_, float p_102616_, float p_102617_) {
    }

    public void m_102624_(EntityModel<T> p_102625_) {
        p_102625_.f_102608_ = this.f_102608_;
        p_102625_.f_102609_ = this.f_102609_;
        p_102625_.f_102610_ = this.f_102610_;
    }
}

