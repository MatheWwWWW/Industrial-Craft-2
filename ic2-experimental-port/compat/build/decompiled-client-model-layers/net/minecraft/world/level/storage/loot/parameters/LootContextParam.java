/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.storage.loot.parameters;

import net.minecraft.resources.ResourceLocation;

public class LootContextParam<T> {
    private final ResourceLocation f_81281_;

    public LootContextParam(ResourceLocation p_81283_) {
        this.f_81281_ = p_81283_;
    }

    public ResourceLocation m_81284_() {
        return this.f_81281_;
    }

    public String toString() {
        return "<parameter " + this.f_81281_ + ">";
    }
}

