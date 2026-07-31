/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Registry
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.core.ref;

import ic2.core.IC2;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public final class Ic2FluidTags {
    public static final TagKey<Fluid> STEAM = Ic2FluidTags.create("c:steam", "forge:steam");

    public static void init() {
    }

    private static TagKey<Fluid> create(String string, String string2) {
        ResourceLocation resourceLocation = new ResourceLocation(IC2.envProxy.isFabricEnv() ? string : string2);
        return TagKey.m_203882_((ResourceKey)Registry.f_122899_, (ResourceLocation)resourceLocation);
    }
}

