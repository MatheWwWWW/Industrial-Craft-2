/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.animal;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public record CatVariant(ResourceLocation f_218151_) {
    public static final CatVariant f_218140_ = CatVariant.m_218156_("tabby", "textures/entity/cat/tabby.png");
    public static final CatVariant f_218141_ = CatVariant.m_218156_("black", "textures/entity/cat/black.png");
    public static final CatVariant f_218142_ = CatVariant.m_218156_("red", "textures/entity/cat/red.png");
    public static final CatVariant f_218143_ = CatVariant.m_218156_("siamese", "textures/entity/cat/siamese.png");
    public static final CatVariant f_218144_ = CatVariant.m_218156_("british_shorthair", "textures/entity/cat/british_shorthair.png");
    public static final CatVariant f_218145_ = CatVariant.m_218156_("calico", "textures/entity/cat/calico.png");
    public static final CatVariant f_218146_ = CatVariant.m_218156_("persian", "textures/entity/cat/persian.png");
    public static final CatVariant f_218147_ = CatVariant.m_218156_("ragdoll", "textures/entity/cat/ragdoll.png");
    public static final CatVariant f_218148_ = CatVariant.m_218156_("white", "textures/entity/cat/white.png");
    public static final CatVariant f_218149_ = CatVariant.m_218156_("jellie", "textures/entity/cat/jellie.png");
    public static final CatVariant f_218150_ = CatVariant.m_218156_("all_black", "textures/entity/cat/all_black.png");

    private static CatVariant m_218156_(String p_218157_, String p_218158_) {
        return Registry.m_122961_(Registry.f_235732_, p_218157_, new CatVariant(new ResourceLocation(p_218158_)));
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{CatVariant.class, "texture", "f_218151_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{CatVariant.class, "texture", "f_218151_"}, this);
    }

    @Override
    public final boolean equals(Object p_218160_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{CatVariant.class, "texture", "f_218151_"}, this, p_218160_);
    }
}

