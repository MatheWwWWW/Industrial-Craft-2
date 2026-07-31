/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.animal;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public record FrogVariant(ResourceLocation f_218188_) {
    public static final FrogVariant f_218185_ = FrogVariant.m_218193_("temperate", "textures/entity/frog/temperate_frog.png");
    public static final FrogVariant f_218186_ = FrogVariant.m_218193_("warm", "textures/entity/frog/warm_frog.png");
    public static final FrogVariant f_218187_ = FrogVariant.m_218193_("cold", "textures/entity/frog/cold_frog.png");

    private static FrogVariant m_218193_(String p_218194_, String p_218195_) {
        return Registry.m_122961_(Registry.f_235734_, p_218194_, new FrogVariant(new ResourceLocation(p_218195_)));
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{FrogVariant.class, "texture", "f_218188_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{FrogVariant.class, "texture", "f_218188_"}, this);
    }

    @Override
    public final boolean equals(Object p_218197_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{FrogVariant.class, "texture", "f_218188_"}, this, p_218197_);
    }
}

