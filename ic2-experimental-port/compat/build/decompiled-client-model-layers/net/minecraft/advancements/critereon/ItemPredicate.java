/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.ImmutableSet$Builder
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonNull
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements.critereon;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;

public class ItemPredicate {
    public static final ItemPredicate f_45028_ = new ItemPredicate();
    @Nullable
    private final TagKey<Item> f_45029_;
    @Nullable
    private final Set<Item> f_151427_;
    private final MinMaxBounds.Ints f_45031_;
    private final MinMaxBounds.Ints f_45032_;
    private final EnchantmentPredicate[] f_45033_;
    private final EnchantmentPredicate[] f_45034_;
    @Nullable
    private final Potion f_45035_;
    private final NbtPredicate f_45036_;

    public ItemPredicate() {
        this.f_45029_ = null;
        this.f_151427_ = null;
        this.f_45035_ = null;
        this.f_45031_ = MinMaxBounds.Ints.f_55364_;
        this.f_45032_ = MinMaxBounds.Ints.f_55364_;
        this.f_45033_ = EnchantmentPredicate.f_30465_;
        this.f_45034_ = EnchantmentPredicate.f_30465_;
        this.f_45036_ = NbtPredicate.f_57471_;
    }

    public ItemPredicate(@Nullable TagKey<Item> p_204137_, @Nullable Set<Item> p_204138_, MinMaxBounds.Ints p_204139_, MinMaxBounds.Ints p_204140_, EnchantmentPredicate[] p_204141_, EnchantmentPredicate[] p_204142_, @Nullable Potion p_204143_, NbtPredicate p_204144_) {
        this.f_45029_ = p_204137_;
        this.f_151427_ = p_204138_;
        this.f_45031_ = p_204139_;
        this.f_45032_ = p_204140_;
        this.f_45033_ = p_204141_;
        this.f_45034_ = p_204142_;
        this.f_45035_ = p_204143_;
        this.f_45036_ = p_204144_;
    }

    public boolean m_45049_(ItemStack p_45050_) {
        if (this == f_45028_) {
            return true;
        }
        if (this.f_45029_ != null && !p_45050_.m_204117_(this.f_45029_)) {
            return false;
        }
        if (this.f_151427_ != null && !this.f_151427_.contains(p_45050_.m_41720_())) {
            return false;
        }
        if (!this.f_45031_.m_55390_(p_45050_.m_41613_())) {
            return false;
        }
        if (!this.f_45032_.m_55327_() && !p_45050_.m_41763_()) {
            return false;
        }
        if (!this.f_45032_.m_55390_(p_45050_.m_41776_() - p_45050_.m_41773_())) {
            return false;
        }
        if (!this.f_45036_.m_57479_(p_45050_)) {
            return false;
        }
        if (this.f_45033_.length > 0) {
            Map<Enchantment, Integer> $$1 = EnchantmentHelper.m_44882_(p_45050_.m_41785_());
            for (EnchantmentPredicate $$2 : this.f_45033_) {
                if ($$2.m_30476_($$1)) continue;
                return false;
            }
        }
        if (this.f_45034_.length > 0) {
            Map<Enchantment, Integer> $$3 = EnchantmentHelper.m_44882_(EnchantedBookItem.m_41163_(p_45050_));
            for (EnchantmentPredicate $$4 : this.f_45034_) {
                if ($$4.m_30476_($$3)) continue;
                return false;
            }
        }
        Potion $$5 = PotionUtils.m_43579_(p_45050_);
        return this.f_45035_ == null || this.f_45035_ == $$5;
    }

    public static ItemPredicate m_45051_(@Nullable JsonElement p_45052_) {
        if (p_45052_ == null || p_45052_.isJsonNull()) {
            return f_45028_;
        }
        JsonObject $$1 = GsonHelper.m_13918_(p_45052_, "item");
        MinMaxBounds.Ints $$2 = MinMaxBounds.Ints.m_55373_($$1.get("count"));
        MinMaxBounds.Ints $$3 = MinMaxBounds.Ints.m_55373_($$1.get("durability"));
        if ($$1.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        }
        NbtPredicate $$4 = NbtPredicate.m_57481_($$1.get("nbt"));
        ImmutableSet $$5 = null;
        JsonArray $$6 = GsonHelper.m_13832_($$1, "items", null);
        if ($$6 != null) {
            ImmutableSet.Builder $$7 = ImmutableSet.builder();
            for (JsonElement $$8 : $$6) {
                ResourceLocation $$9 = new ResourceLocation(GsonHelper.m_13805_($$8, "item"));
                $$7.add((Object)Registry.f_122827_.m_6612_($$9).orElseThrow(() -> new JsonSyntaxException("Unknown item id '" + $$9 + "'")));
            }
            $$5 = $$7.build();
        }
        TagKey<Item> $$10 = null;
        if ($$1.has("tag")) {
            ResourceLocation $$11 = new ResourceLocation(GsonHelper.m_13906_($$1, "tag"));
            $$10 = TagKey.m_203882_(Registry.f_122904_, $$11);
        }
        Potion $$12 = null;
        if ($$1.has("potion")) {
            ResourceLocation $$13 = new ResourceLocation(GsonHelper.m_13906_($$1, "potion"));
            $$12 = Registry.f_122828_.m_6612_($$13).orElseThrow(() -> new JsonSyntaxException("Unknown potion '" + $$13 + "'"));
        }
        EnchantmentPredicate[] $$14 = EnchantmentPredicate.m_30480_($$1.get("enchantments"));
        EnchantmentPredicate[] $$15 = EnchantmentPredicate.m_30480_($$1.get("stored_enchantments"));
        return new ItemPredicate($$10, (Set<Item>)$$5, $$2, $$3, $$14, $$15, $$12, $$4);
    }

    public JsonElement m_45048_() {
        if (this == f_45028_) {
            return JsonNull.INSTANCE;
        }
        JsonObject $$0 = new JsonObject();
        if (this.f_151427_ != null) {
            JsonArray $$1 = new JsonArray();
            for (Item $$2 : this.f_151427_) {
                $$1.add(Registry.f_122827_.m_7981_($$2).toString());
            }
            $$0.add("items", (JsonElement)$$1);
        }
        if (this.f_45029_ != null) {
            $$0.addProperty("tag", this.f_45029_.f_203868_().toString());
        }
        $$0.add("count", this.f_45031_.m_55328_());
        $$0.add("durability", this.f_45032_.m_55328_());
        $$0.add("nbt", this.f_45036_.m_57476_());
        if (this.f_45033_.length > 0) {
            JsonArray $$3 = new JsonArray();
            for (EnchantmentPredicate $$4 : this.f_45033_) {
                $$3.add($$4.m_30473_());
            }
            $$0.add("enchantments", (JsonElement)$$3);
        }
        if (this.f_45034_.length > 0) {
            JsonArray $$5 = new JsonArray();
            for (EnchantmentPredicate $$6 : this.f_45034_) {
                $$5.add($$6.m_30473_());
            }
            $$0.add("stored_enchantments", (JsonElement)$$5);
        }
        if (this.f_45035_ != null) {
            $$0.addProperty("potion", Registry.f_122828_.m_7981_(this.f_45035_).toString());
        }
        return $$0;
    }

    public static ItemPredicate[] m_45055_(@Nullable JsonElement p_45056_) {
        if (p_45056_ == null || p_45056_.isJsonNull()) {
            return new ItemPredicate[0];
        }
        JsonArray $$1 = GsonHelper.m_13924_(p_45056_, "items");
        ItemPredicate[] $$2 = new ItemPredicate[$$1.size()];
        for (int $$3 = 0; $$3 < $$2.length; ++$$3) {
            $$2[$$3] = ItemPredicate.m_45051_($$1.get($$3));
        }
        return $$2;
    }

    public static class Builder {
        private final List<EnchantmentPredicate> f_45059_ = Lists.newArrayList();
        private final List<EnchantmentPredicate> f_45060_ = Lists.newArrayList();
        @Nullable
        private Set<Item> f_151440_;
        @Nullable
        private TagKey<Item> f_45062_;
        private MinMaxBounds.Ints f_45063_ = MinMaxBounds.Ints.f_55364_;
        private MinMaxBounds.Ints f_45064_ = MinMaxBounds.Ints.f_55364_;
        @Nullable
        private Potion f_45065_;
        private NbtPredicate f_45066_ = NbtPredicate.f_57471_;

        private Builder() {
        }

        public static Builder m_45068_() {
            return new Builder();
        }

        public Builder m_151445_(ItemLike ... p_151446_) {
            this.f_151440_ = (Set)Stream.of(p_151446_).map(ItemLike::m_5456_).collect(ImmutableSet.toImmutableSet());
            return this;
        }

        public Builder m_204145_(TagKey<Item> p_204146_) {
            this.f_45062_ = p_204146_;
            return this;
        }

        public Builder m_151443_(MinMaxBounds.Ints p_151444_) {
            this.f_45063_ = p_151444_;
            return this;
        }

        public Builder m_151449_(MinMaxBounds.Ints p_151450_) {
            this.f_45064_ = p_151450_;
            return this;
        }

        public Builder m_151441_(Potion p_151442_) {
            this.f_45065_ = p_151442_;
            return this;
        }

        public Builder m_45075_(CompoundTag p_45076_) {
            this.f_45066_ = new NbtPredicate(p_45076_);
            return this;
        }

        public Builder m_45071_(EnchantmentPredicate p_45072_) {
            this.f_45059_.add(p_45072_);
            return this;
        }

        public Builder m_151447_(EnchantmentPredicate p_151448_) {
            this.f_45060_.add(p_151448_);
            return this;
        }

        public ItemPredicate m_45077_() {
            return new ItemPredicate(this.f_45062_, this.f_151440_, this.f_45063_, this.f_45064_, this.f_45059_.toArray(EnchantmentPredicate.f_30465_), this.f_45060_.toArray(EnchantmentPredicate.f_30465_), this.f_45065_, this.f_45066_);
        }
    }
}

