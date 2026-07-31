/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer.block.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.MultiVariant;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.renderer.block.model.multipart.MultiPart;
import net.minecraft.client.renderer.block.model.multipart.Selector;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class BlockModelDefinition {
    private final Map<String, MultiVariant> f_111532_ = Maps.newLinkedHashMap();
    private MultiPart f_111533_;

    public static BlockModelDefinition m_111540_(Context p_111541_, Reader p_111542_) {
        return GsonHelper.m_13776_(p_111541_.f_111548_, p_111542_, BlockModelDefinition.class);
    }

    public BlockModelDefinition(Map<String, MultiVariant> p_111537_, MultiPart p_111538_) {
        this.f_111533_ = p_111538_;
        this.f_111532_.putAll(p_111537_);
    }

    public BlockModelDefinition(List<BlockModelDefinition> p_111535_) {
        BlockModelDefinition $$1 = null;
        for (BlockModelDefinition $$2 : p_111535_) {
            if ($$2.m_111543_()) {
                this.f_111532_.clear();
                $$1 = $$2;
            }
            this.f_111532_.putAll($$2.f_111532_);
        }
        if ($$1 != null) {
            this.f_111533_ = $$1.f_111533_;
        }
    }

    @VisibleForTesting
    public boolean m_173425_(String p_173426_) {
        return this.f_111532_.get(p_173426_) != null;
    }

    @VisibleForTesting
    public MultiVariant m_173428_(String p_173429_) {
        MultiVariant $$1 = this.f_111532_.get(p_173429_);
        if ($$1 == null) {
            throw new MissingVariantException();
        }
        return $$1;
    }

    public boolean equals(Object p_111546_) {
        if (this == p_111546_) {
            return true;
        }
        if (p_111546_ instanceof BlockModelDefinition) {
            BlockModelDefinition $$1 = (BlockModelDefinition)p_111546_;
            if (this.f_111532_.equals($$1.f_111532_)) {
                return this.m_111543_() ? this.f_111533_.equals($$1.f_111533_) : !$$1.m_111543_();
            }
        }
        return false;
    }

    public int hashCode() {
        return 31 * this.f_111532_.hashCode() + (this.m_111543_() ? this.f_111533_.hashCode() : 0);
    }

    public Map<String, MultiVariant> m_111539_() {
        return this.f_111532_;
    }

    @VisibleForTesting
    public Set<MultiVariant> m_173427_() {
        HashSet $$0 = Sets.newHashSet(this.f_111532_.values());
        if (this.m_111543_()) {
            $$0.addAll(this.f_111533_.m_111982_());
        }
        return $$0;
    }

    public boolean m_111543_() {
        return this.f_111533_ != null;
    }

    public MultiPart m_111544_() {
        return this.f_111533_;
    }

    public static final class Context {
        protected final Gson f_111548_ = new GsonBuilder().registerTypeAdapter(BlockModelDefinition.class, (Object)new Deserializer()).registerTypeAdapter(Variant.class, (Object)new Variant.Deserializer()).registerTypeAdapter(MultiVariant.class, (Object)new MultiVariant.Deserializer()).registerTypeAdapter(MultiPart.class, (Object)new MultiPart.Deserializer(this)).registerTypeAdapter(Selector.class, (Object)new Selector.Deserializer()).create();
        private StateDefinition<Block, BlockState> f_111549_;

        public StateDefinition<Block, BlockState> m_111551_() {
            return this.f_111549_;
        }

        public void m_111552_(StateDefinition<Block, BlockState> p_111553_) {
            this.f_111549_ = p_111553_;
        }
    }

    protected class MissingVariantException
    extends RuntimeException {
        protected MissingVariantException() {
        }
    }

    public static class Deserializer
    implements JsonDeserializer<BlockModelDefinition> {
        public BlockModelDefinition deserialize(JsonElement p_111559_, Type p_111560_, JsonDeserializationContext p_111561_) throws JsonParseException {
            JsonObject $$3 = p_111559_.getAsJsonObject();
            Map<String, MultiVariant> $$4 = this.m_111555_(p_111561_, $$3);
            MultiPart $$5 = this.m_111562_(p_111561_, $$3);
            if ($$4.isEmpty() && ($$5 == null || $$5.m_111982_().isEmpty())) {
                throw new JsonParseException("Neither 'variants' nor 'multipart' found");
            }
            return new BlockModelDefinition($$4, $$5);
        }

        protected Map<String, MultiVariant> m_111555_(JsonDeserializationContext p_111556_, JsonObject p_111557_) {
            HashMap $$2 = Maps.newHashMap();
            if (p_111557_.has("variants")) {
                JsonObject $$3 = GsonHelper.m_13930_(p_111557_, "variants");
                for (Map.Entry $$4 : $$3.entrySet()) {
                    $$2.put((String)$$4.getKey(), (MultiVariant)p_111556_.deserialize((JsonElement)$$4.getValue(), MultiVariant.class));
                }
            }
            return $$2;
        }

        @Nullable
        protected MultiPart m_111562_(JsonDeserializationContext p_111563_, JsonObject p_111564_) {
            if (!p_111564_.has("multipart")) {
                return null;
            }
            JsonArray $$2 = GsonHelper.m_13933_(p_111564_, "multipart");
            return (MultiPart)p_111563_.deserialize((JsonElement)$$2, MultiPart.class);
        }

        public /* synthetic */ Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

