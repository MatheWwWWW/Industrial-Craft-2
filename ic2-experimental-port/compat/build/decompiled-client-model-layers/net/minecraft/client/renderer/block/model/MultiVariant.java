/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParseException
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.client.resources.model.WeightedBakedModel;
import net.minecraft.resources.ResourceLocation;

public class MultiVariant
implements UnbakedModel {
    private final List<Variant> f_111845_;

    public MultiVariant(List<Variant> p_111847_) {
        this.f_111845_ = p_111847_;
    }

    public List<Variant> m_111848_() {
        return this.f_111845_;
    }

    public boolean equals(Object p_111862_) {
        if (this == p_111862_) {
            return true;
        }
        if (p_111862_ instanceof MultiVariant) {
            MultiVariant $$1 = (MultiVariant)p_111862_;
            return this.f_111845_.equals($$1.f_111845_);
        }
        return false;
    }

    public int hashCode() {
        return this.f_111845_.hashCode();
    }

    @Override
    public Collection<ResourceLocation> m_7970_() {
        return this.m_111848_().stream().map(Variant::m_111883_).collect(Collectors.toSet());
    }

    @Override
    public Collection<Material> m_5500_(Function<ResourceLocation, UnbakedModel> p_111855_, Set<Pair<String, String>> p_111856_) {
        return this.m_111848_().stream().map(Variant::m_111883_).distinct().flatMap(p_111860_ -> ((UnbakedModel)p_111855_.apply((ResourceLocation)p_111860_)).m_5500_(p_111855_, p_111856_).stream()).collect(Collectors.toSet());
    }

    @Override
    @Nullable
    public BakedModel m_7611_(ModelBakery p_111850_, Function<Material, TextureAtlasSprite> p_111851_, ModelState p_111852_, ResourceLocation p_111853_) {
        if (this.m_111848_().isEmpty()) {
            return null;
        }
        WeightedBakedModel.Builder $$4 = new WeightedBakedModel.Builder();
        for (Variant $$5 : this.m_111848_()) {
            BakedModel $$6 = p_111850_.m_119349_($$5.m_111883_(), $$5);
            $$4.m_119559_($$6, $$5.m_111886_());
        }
        return $$4.m_119558_();
    }

    public static class Deserializer
    implements JsonDeserializer<MultiVariant> {
        public MultiVariant deserialize(JsonElement p_111867_, Type p_111868_, JsonDeserializationContext p_111869_) throws JsonParseException {
            ArrayList $$3 = Lists.newArrayList();
            if (p_111867_.isJsonArray()) {
                JsonArray $$4 = p_111867_.getAsJsonArray();
                if ($$4.size() == 0) {
                    throw new JsonParseException("Empty variant array");
                }
                for (JsonElement $$5 : $$4) {
                    $$3.add((Variant)p_111869_.deserialize($$5, Variant.class));
                }
            } else {
                $$3.add((Variant)p_111869_.deserialize(p_111867_, Variant.class));
            }
            return new MultiVariant($$3);
        }

        public /* synthetic */ Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

