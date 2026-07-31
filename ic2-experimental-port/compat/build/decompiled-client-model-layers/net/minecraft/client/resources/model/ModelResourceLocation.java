/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 */
package net.minecraft.client.resources.model;

import com.google.common.annotations.VisibleForTesting;
import java.util.Locale;
import net.minecraft.resources.ResourceLocation;

public class ModelResourceLocation
extends ResourceLocation {
    @VisibleForTesting
    static final char f_174906_ = '#';
    private final String f_119435_;

    protected ModelResourceLocation(String[] p_119445_) {
        super(p_119445_);
        this.f_119435_ = p_119445_[2].toLowerCase(Locale.ROOT);
    }

    public ModelResourceLocation(String p_174908_, String p_174909_, String p_174910_) {
        this(new String[]{p_174908_, p_174909_, p_174910_});
    }

    public ModelResourceLocation(String p_119437_) {
        this(ModelResourceLocation.m_119446_(p_119437_));
    }

    public ModelResourceLocation(ResourceLocation p_119442_, String p_119443_) {
        this(p_119442_.toString(), p_119443_);
    }

    public ModelResourceLocation(String p_119439_, String p_119440_) {
        this(ModelResourceLocation.m_119446_(p_119439_ + "#" + p_119440_));
    }

    protected static String[] m_119446_(String p_119447_) {
        String[] $$1 = new String[]{null, p_119447_, ""};
        int $$2 = p_119447_.indexOf(35);
        String $$3 = p_119447_;
        if ($$2 >= 0) {
            $$1[2] = p_119447_.substring($$2 + 1, p_119447_.length());
            if ($$2 > 1) {
                $$3 = p_119447_.substring(0, $$2);
            }
        }
        System.arraycopy(ResourceLocation.m_135832_($$3, ':'), 0, $$1, 0, 2);
        return $$1;
    }

    public String m_119448_() {
        return this.f_119435_;
    }

    @Override
    public boolean equals(Object p_119450_) {
        if (this == p_119450_) {
            return true;
        }
        if (p_119450_ instanceof ModelResourceLocation && super.equals(p_119450_)) {
            ModelResourceLocation $$1 = (ModelResourceLocation)p_119450_;
            return this.f_119435_.equals($$1.f_119435_);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + this.f_119435_.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + "#" + this.f_119435_;
    }
}

