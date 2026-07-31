/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.models.model;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModelLocationUtils {
    @Deprecated
    public static ResourceLocation m_125581_(String p_125582_) {
        return new ResourceLocation("minecraft", "block/" + p_125582_);
    }

    public static ResourceLocation m_125583_(String p_125584_) {
        return new ResourceLocation("minecraft", "item/" + p_125584_);
    }

    public static ResourceLocation m_125578_(Block p_125579_, String p_125580_) {
        ResourceLocation $$2 = Registry.f_122824_.m_7981_(p_125579_);
        return new ResourceLocation($$2.m_135827_(), "block/" + $$2.m_135815_() + p_125580_);
    }

    public static ResourceLocation m_125576_(Block p_125577_) {
        ResourceLocation $$1 = Registry.f_122824_.m_7981_(p_125577_);
        return new ResourceLocation($$1.m_135827_(), "block/" + $$1.m_135815_());
    }

    public static ResourceLocation m_125571_(Item p_125572_) {
        ResourceLocation $$1 = Registry.f_122827_.m_7981_(p_125572_);
        return new ResourceLocation($$1.m_135827_(), "item/" + $$1.m_135815_());
    }

    public static ResourceLocation m_125573_(Item p_125574_, String p_125575_) {
        ResourceLocation $$2 = Registry.f_122827_.m_7981_(p_125574_);
        return new ResourceLocation($$2.m_135827_(), "item/" + $$2.m_135815_() + p_125575_);
    }
}

