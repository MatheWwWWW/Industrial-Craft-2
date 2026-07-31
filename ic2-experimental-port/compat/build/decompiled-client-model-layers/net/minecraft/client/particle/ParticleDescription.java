/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Streams
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonObject
 *  javax.annotation.Nullable
 */
package net.minecraft.client.particle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class ParticleDescription {
    @Nullable
    private final List<ResourceLocation> f_107279_;

    private ParticleDescription(@Nullable List<ResourceLocation> p_107281_) {
        this.f_107279_ = p_107281_;
    }

    @Nullable
    public List<ResourceLocation> m_107282_() {
        return this.f_107279_;
    }

    public static ParticleDescription m_107285_(JsonObject p_107286_) {
        List<ResourceLocation> $$3;
        JsonArray $$1 = GsonHelper.m_13832_(p_107286_, "textures", null);
        if ($$1 != null) {
            List $$2 = (List)Streams.stream((Iterable)$$1).map(p_107284_ -> GsonHelper.m_13805_(p_107284_, "texture")).map(ResourceLocation::new).collect(ImmutableList.toImmutableList());
        } else {
            $$3 = null;
        }
        return new ParticleDescription($$3);
    }
}

