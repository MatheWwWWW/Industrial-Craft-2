/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParseException
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer.block.model.multipart;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.BlockModelDefinition;
import net.minecraft.client.renderer.block.model.MultiVariant;
import net.minecraft.client.renderer.block.model.multipart.Selector;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.MultiPartBakedModel;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class MultiPart
implements UnbakedModel {
    private final StateDefinition<Block, BlockState> f_111962_;
    private final List<Selector> f_111963_;

    public MultiPart(StateDefinition<Block, BlockState> p_111965_, List<Selector> p_111966_) {
        this.f_111962_ = p_111965_;
        this.f_111963_ = p_111966_;
    }

    public List<Selector> m_111967_() {
        return this.f_111963_;
    }

    public Set<MultiVariant> m_111982_() {
        HashSet $$0 = Sets.newHashSet();
        for (Selector $$1 : this.f_111963_) {
            $$0.add($$1.m_112020_());
        }
        return $$0;
    }

    public boolean equals(Object p_111984_) {
        if (this == p_111984_) {
            return true;
        }
        if (p_111984_ instanceof MultiPart) {
            MultiPart $$1 = (MultiPart)p_111984_;
            return Objects.equals(this.f_111962_, $$1.f_111962_) && Objects.equals(this.f_111963_, $$1.f_111963_);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.f_111962_, this.f_111963_);
    }

    @Override
    public Collection<ResourceLocation> m_7970_() {
        return this.m_111967_().stream().flatMap(p_111969_ -> p_111969_.m_112020_().m_7970_().stream()).collect(Collectors.toSet());
    }

    @Override
    public Collection<Material> m_5500_(Function<ResourceLocation, UnbakedModel> p_111976_, Set<Pair<String, String>> p_111977_) {
        return this.m_111967_().stream().flatMap(p_111981_ -> p_111981_.m_112020_().m_5500_(p_111976_, p_111977_).stream()).collect(Collectors.toSet());
    }

    @Override
    @Nullable
    public BakedModel m_7611_(ModelBakery p_111971_, Function<Material, TextureAtlasSprite> p_111972_, ModelState p_111973_, ResourceLocation p_111974_) {
        MultiPartBakedModel.Builder $$4 = new MultiPartBakedModel.Builder();
        for (Selector $$5 : this.m_111967_()) {
            BakedModel $$6 = $$5.m_112020_().m_7611_(p_111971_, p_111972_, p_111973_, p_111974_);
            if ($$6 == null) continue;
            $$4.m_119477_($$5.m_112021_(this.f_111962_), $$6);
        }
        return $$4.m_119476_();
    }

    public static class Deserializer
    implements JsonDeserializer<MultiPart> {
        private final BlockModelDefinition.Context f_111987_;

        public Deserializer(BlockModelDefinition.Context p_111989_) {
            this.f_111987_ = p_111989_;
        }

        public MultiPart deserialize(JsonElement p_111994_, Type p_111995_, JsonDeserializationContext p_111996_) throws JsonParseException {
            return new MultiPart(this.f_111987_.m_111551_(), this.m_111990_(p_111996_, p_111994_.getAsJsonArray()));
        }

        private List<Selector> m_111990_(JsonDeserializationContext p_111991_, JsonArray p_111992_) {
            ArrayList $$2 = Lists.newArrayList();
            for (JsonElement $$3 : p_111992_) {
                $$2.add((Selector)p_111991_.deserialize($$3, Selector.class));
            }
            return $$2;
        }

        public /* synthetic */ Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

