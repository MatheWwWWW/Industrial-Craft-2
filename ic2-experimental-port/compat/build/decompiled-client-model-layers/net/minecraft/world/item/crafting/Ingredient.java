/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonSyntaxException
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntComparators
 *  it.unimi.dsi.fastutil.ints.IntList
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item.crafting;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.ItemLike;

public final class Ingredient
implements Predicate<ItemStack> {
    public static final Ingredient f_43901_ = new Ingredient(Stream.empty());
    private final Value[] f_43902_;
    @Nullable
    private ItemStack[] f_43903_;
    @Nullable
    private IntList f_43904_;

    private Ingredient(Stream<? extends Value> p_43907_) {
        this.f_43902_ = (Value[])p_43907_.toArray(Value[]::new);
    }

    public ItemStack[] m_43908_() {
        this.m_43948_();
        return this.f_43903_;
    }

    private void m_43948_() {
        if (this.f_43903_ == null) {
            this.f_43903_ = (ItemStack[])Arrays.stream(this.f_43902_).flatMap(p_43916_ -> p_43916_.m_6223_().stream()).distinct().toArray(ItemStack[]::new);
        }
    }

    @Override
    public boolean test(@Nullable ItemStack p_43914_) {
        if (p_43914_ == null) {
            return false;
        }
        this.m_43948_();
        if (this.f_43903_.length == 0) {
            return p_43914_.m_41619_();
        }
        for (ItemStack $$1 : this.f_43903_) {
            if (!$$1.m_150930_(p_43914_.m_41720_())) continue;
            return true;
        }
        return false;
    }

    public IntList m_43931_() {
        if (this.f_43904_ == null) {
            this.m_43948_();
            this.f_43904_ = new IntArrayList(this.f_43903_.length);
            for (ItemStack $$0 : this.f_43903_) {
                this.f_43904_.add(StackedContents.m_36496_($$0));
            }
            this.f_43904_.sort(IntComparators.NATURAL_COMPARATOR);
        }
        return this.f_43904_;
    }

    public void m_43923_(FriendlyByteBuf p_43924_) {
        this.m_43948_();
        p_43924_.m_236828_(Arrays.asList(this.f_43903_), FriendlyByteBuf::m_130055_);
    }

    public JsonElement m_43942_() {
        if (this.f_43902_.length == 1) {
            return this.f_43902_[0].m_6544_();
        }
        JsonArray $$0 = new JsonArray();
        for (Value $$1 : this.f_43902_) {
            $$0.add((JsonElement)$$1.m_6544_());
        }
        return $$0;
    }

    public boolean m_43947_() {
        return !(this.f_43902_.length != 0 || this.f_43903_ != null && this.f_43903_.length != 0 || this.f_43904_ != null && !this.f_43904_.isEmpty());
    }

    private static Ingredient m_43938_(Stream<? extends Value> p_43939_) {
        Ingredient $$1 = new Ingredient(p_43939_);
        return $$1.f_43902_.length == 0 ? f_43901_ : $$1;
    }

    public static Ingredient m_151265_() {
        return f_43901_;
    }

    public static Ingredient m_43929_(ItemLike ... p_43930_) {
        return Ingredient.m_43921_(Arrays.stream(p_43930_).map(ItemStack::new));
    }

    public static Ingredient m_43927_(ItemStack ... p_43928_) {
        return Ingredient.m_43921_(Arrays.stream(p_43928_));
    }

    public static Ingredient m_43921_(Stream<ItemStack> p_43922_) {
        return Ingredient.m_43938_(p_43922_.filter(p_43944_ -> !p_43944_.m_41619_()).map(ItemValue::new));
    }

    public static Ingredient m_204132_(TagKey<Item> p_204133_) {
        return Ingredient.m_43938_(Stream.of(new TagValue(p_204133_)));
    }

    public static Ingredient m_43940_(FriendlyByteBuf p_43941_) {
        return Ingredient.m_43938_(p_43941_.m_236845_(FriendlyByteBuf::m_130267_).stream().map(ItemValue::new));
    }

    public static Ingredient m_43917_(@Nullable JsonElement p_43918_) {
        if (p_43918_ == null || p_43918_.isJsonNull()) {
            throw new JsonSyntaxException("Item cannot be null");
        }
        if (p_43918_.isJsonObject()) {
            return Ingredient.m_43938_(Stream.of(Ingredient.m_43919_(p_43918_.getAsJsonObject())));
        }
        if (p_43918_.isJsonArray()) {
            JsonArray $$1 = p_43918_.getAsJsonArray();
            if ($$1.size() == 0) {
                throw new JsonSyntaxException("Item array cannot be empty, at least one item must be defined");
            }
            return Ingredient.m_43938_(StreamSupport.stream($$1.spliterator(), false).map(p_151264_ -> Ingredient.m_43919_(GsonHelper.m_13918_(p_151264_, "item"))));
        }
        throw new JsonSyntaxException("Expected item to be object or array of objects");
    }

    private static Value m_43919_(JsonObject p_43920_) {
        if (p_43920_.has("item") && p_43920_.has("tag")) {
            throw new JsonParseException("An ingredient entry is either a tag or an item, not both");
        }
        if (p_43920_.has("item")) {
            Item $$1 = ShapedRecipe.m_151278_(p_43920_);
            return new ItemValue(new ItemStack($$1));
        }
        if (p_43920_.has("tag")) {
            ResourceLocation $$2 = new ResourceLocation(GsonHelper.m_13906_(p_43920_, "tag"));
            TagKey<Item> $$3 = TagKey.m_203882_(Registry.f_122904_, $$2);
            return new TagValue($$3);
        }
        throw new JsonParseException("An ingredient entry needs either a tag or an item");
    }

    @Override
    public /* synthetic */ boolean test(@Nullable Object object) {
        return this.test((ItemStack)object);
    }

    static interface Value {
        public Collection<ItemStack> m_6223_();

        public JsonObject m_6544_();
    }

    static class TagValue
    implements Value {
        private final TagKey<Item> f_43959_;

        TagValue(TagKey<Item> p_204135_) {
            this.f_43959_ = p_204135_;
        }

        @Override
        public Collection<ItemStack> m_6223_() {
            ArrayList $$0 = Lists.newArrayList();
            for (Holder<Item> $$1 : Registry.f_122827_.m_206058_(this.f_43959_)) {
                $$0.add(new ItemStack($$1));
            }
            return $$0;
        }

        @Override
        public JsonObject m_6544_() {
            JsonObject $$0 = new JsonObject();
            $$0.addProperty("tag", this.f_43959_.f_203868_().toString());
            return $$0;
        }
    }

    static class ItemValue
    implements Value {
        private final ItemStack f_43951_;

        ItemValue(ItemStack p_43953_) {
            this.f_43951_ = p_43953_;
        }

        @Override
        public Collection<ItemStack> m_6223_() {
            return Collections.singleton(this.f_43951_);
        }

        @Override
        public JsonObject m_6544_() {
            JsonObject $$0 = new JsonObject();
            $$0.addProperty("item", Registry.f_122827_.m_7981_(this.f_43951_.m_41720_()).toString());
            return $$0;
        }
    }
}

