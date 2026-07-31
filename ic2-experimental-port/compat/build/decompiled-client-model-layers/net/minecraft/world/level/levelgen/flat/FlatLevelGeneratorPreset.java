/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.flat;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;

public record FlatLevelGeneratorPreset(Holder<Item> f_226245_, FlatLevelGeneratorSettings f_226246_) {
    public static final Codec<FlatLevelGeneratorPreset> f_226243_ = RecordCodecBuilder.create(p_226253_ -> p_226253_.group((App)RegistryFixedCodec.m_206740_(Registry.f_122904_).fieldOf("display").forGetter(p_226258_ -> p_226258_.f_226245_), (App)FlatLevelGeneratorSettings.f_70347_.fieldOf("settings").forGetter(p_226255_ -> p_226255_.f_226246_)).apply((Applicative)p_226253_, FlatLevelGeneratorPreset::new));
    public static final Codec<Holder<FlatLevelGeneratorPreset>> f_226244_ = RegistryFileCodec.m_135589_(Registry.f_235727_, f_226243_);

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{FlatLevelGeneratorPreset.class, "displayItem;settings", "f_226245_", "f_226246_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{FlatLevelGeneratorPreset.class, "displayItem;settings", "f_226245_", "f_226246_"}, this);
    }

    @Override
    public final boolean equals(Object p_226260_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{FlatLevelGeneratorPreset.class, "displayItem;settings", "f_226245_", "f_226246_"}, this, p_226260_);
    }
}

