/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.dimension;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.dimension.DimensionType;

public class BuiltinDimensionTypes {
    public static final ResourceKey<DimensionType> f_223538_ = BuiltinDimensionTypes.m_223547_("overworld");
    public static final ResourceKey<DimensionType> f_223539_ = BuiltinDimensionTypes.m_223547_("the_nether");
    public static final ResourceKey<DimensionType> f_223540_ = BuiltinDimensionTypes.m_223547_("the_end");
    public static final ResourceKey<DimensionType> f_223541_ = BuiltinDimensionTypes.m_223547_("overworld_caves");
    public static final ResourceLocation f_223542_ = new ResourceLocation("overworld");
    public static final ResourceLocation f_223543_ = new ResourceLocation("the_nether");
    public static final ResourceLocation f_223544_ = new ResourceLocation("the_end");

    private static ResourceKey<DimensionType> m_223547_(String p_223548_) {
        return ResourceKey.m_135785_(Registry.f_122818_, new ResourceLocation(p_223548_));
    }
}

