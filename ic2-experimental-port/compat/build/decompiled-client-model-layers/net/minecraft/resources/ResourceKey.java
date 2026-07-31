/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.serialization.Codec
 */
package net.minecraft.resources;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class ResourceKey<T> {
    private static final Map<String, ResourceKey<?>> f_135775_ = Collections.synchronizedMap(Maps.newIdentityHashMap());
    private final ResourceLocation f_135776_;
    private final ResourceLocation f_135777_;

    public static <T> Codec<ResourceKey<T>> m_195966_(ResourceKey<? extends Registry<T>> p_195967_) {
        return ResourceLocation.f_135803_.xmap(p_195979_ -> ResourceKey.m_135785_(p_195967_, p_195979_), ResourceKey::m_135782_);
    }

    public static <T> ResourceKey<T> m_135785_(ResourceKey<? extends Registry<T>> p_135786_, ResourceLocation p_135787_) {
        return ResourceKey.m_135790_(p_135786_.f_135777_, p_135787_);
    }

    public static <T> ResourceKey<Registry<T>> m_135788_(ResourceLocation p_135789_) {
        return ResourceKey.m_135790_(Registry.f_122895_, p_135789_);
    }

    private static <T> ResourceKey<T> m_135790_(ResourceLocation p_135791_, ResourceLocation p_135792_) {
        String $$2 = (p_135791_ + ":" + p_135792_).intern();
        return f_135775_.computeIfAbsent($$2, p_195971_ -> new ResourceKey(p_135791_, p_135792_));
    }

    private ResourceKey(ResourceLocation p_135780_, ResourceLocation p_135781_) {
        this.f_135776_ = p_135780_;
        this.f_135777_ = p_135781_;
    }

    public String toString() {
        return "ResourceKey[" + this.f_135776_ + " / " + this.f_135777_ + "]";
    }

    public boolean m_135783_(ResourceKey<? extends Registry<?>> p_135784_) {
        return this.f_135776_.equals(p_135784_.m_135782_());
    }

    public <E> Optional<ResourceKey<E>> m_195975_(ResourceKey<? extends Registry<E>> p_195976_) {
        return this.m_135783_(p_195976_) ? Optional.of(this) : Optional.empty();
    }

    public ResourceLocation m_135782_() {
        return this.f_135777_;
    }

    public ResourceLocation m_211136_() {
        return this.f_135776_;
    }
}

