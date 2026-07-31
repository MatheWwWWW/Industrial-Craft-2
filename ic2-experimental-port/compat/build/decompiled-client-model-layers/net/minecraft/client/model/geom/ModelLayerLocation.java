/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model.geom;

import net.minecraft.resources.ResourceLocation;

public final class ModelLayerLocation {
    private final ResourceLocation f_171118_;
    private final String f_171119_;

    public ModelLayerLocation(ResourceLocation p_171121_, String p_171122_) {
        this.f_171118_ = p_171121_;
        this.f_171119_ = p_171122_;
    }

    public ResourceLocation m_171123_() {
        return this.f_171118_;
    }

    public String m_171124_() {
        return this.f_171119_;
    }

    public boolean equals(Object p_171126_) {
        if (this == p_171126_) {
            return true;
        }
        if (p_171126_ instanceof ModelLayerLocation) {
            ModelLayerLocation $$1 = (ModelLayerLocation)p_171126_;
            return this.f_171118_.equals($$1.f_171118_) && this.f_171119_.equals($$1.f_171119_);
        }
        return false;
    }

    public int hashCode() {
        int $$0 = this.f_171118_.hashCode();
        $$0 = 31 * $$0 + this.f_171119_.hashCode();
        return $$0;
    }

    public String toString() {
        return this.f_171118_ + "#" + this.f_171119_;
    }
}

