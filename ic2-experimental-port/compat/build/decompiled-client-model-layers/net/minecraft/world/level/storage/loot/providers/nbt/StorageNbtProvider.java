/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.storage.loot.providers.nbt;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.providers.nbt.LootNbtProviderType;
import net.minecraft.world.level.storage.loot.providers.nbt.NbtProvider;
import net.minecraft.world.level.storage.loot.providers.nbt.NbtProviders;

public class StorageNbtProvider
implements NbtProvider {
    final ResourceLocation f_165631_;

    StorageNbtProvider(ResourceLocation p_165633_) {
        this.f_165631_ = p_165633_;
    }

    @Override
    public LootNbtProviderType m_142624_() {
        return NbtProviders.f_165623_;
    }

    @Override
    @Nullable
    public Tag m_142301_(LootContext p_165636_) {
        return p_165636_.m_78952_().m_7654_().m_129897_().m_78044_(this.f_165631_);
    }

    @Override
    public Set<LootContextParam<?>> m_142677_() {
        return ImmutableSet.of();
    }

    public static class Serializer
    implements net.minecraft.world.level.storage.loot.Serializer<StorageNbtProvider> {
        @Override
        public void m_6170_(JsonObject p_165643_, StorageNbtProvider p_165644_, JsonSerializationContext p_165645_) {
            p_165643_.addProperty("source", p_165644_.f_165631_.toString());
        }

        @Override
        public StorageNbtProvider m_7561_(JsonObject p_165651_, JsonDeserializationContext p_165652_) {
            String $$2 = GsonHelper.m_13906_(p_165651_, "source");
            return new StorageNbtProvider(new ResourceLocation($$2));
        }

        @Override
        public /* synthetic */ Object m_7561_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_7561_(jsonObject, jsonDeserializationContext);
        }
    }
}

